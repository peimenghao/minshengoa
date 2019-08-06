package com.minsheng.oa.main.resource.dfs;

import org.apache.commons.lang3.StringUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;

/**
 *
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B>FastDFS分布式文件系统操作客户端<BR>
 * <B>概要说明：</B>FastDFS分布式文件系统操作客户端<BR>
 * @author bhz
 * @since 2013年11月10日
 */

public class FastDFSClientUtils {


    private static  String CONF_FILENAME;

    static {
        try {
            File file=path();
            System.out.println("扫描到的filePat是"+file);
            CONF_FILENAME = file.toString();
             file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //文件绝对路径
   // private static final String CONF_FILENAME = Thread.currentThread().getContextClassLoader().getResource("fdfs_client.conf").getPath();



    private static TrackerClient trackerClient;

   static TrackerServer trackerServer = null;
   static StorageServer storageServer = null;
   static StorageClient1 storageClient1 = null;
    //加载文件
    static {
        try {
            ClientGlobal.init(CONF_FILENAME);
            TrackerGroup trackerGroup = ClientGlobal.g_tracker_group;
            trackerClient = new TrackerClient(trackerGroup);
            trackerServer = trackerClient.getConnection();
            storageServer = trackerClient.getStoreStorage(trackerServer);
            storageServer = null;
            storageClient1 = new StorageClient1(trackerServer, storageServer);
        } catch (Exception e) {

        }
    }

    public FastDFSClientUtils() throws IOException {
    }

    /**
     *
     * @param filePaht  创建的临时路径，client 自动创建
     * @param fileName
     * @return
     */
    public static String upload1(String filePaht,String fileName) { //文件路径，文件名字
        System.out.println(filePaht);

        String filePath = null;
        try {
            filePath = storageClient1.upload_file1(filePaht, getFileExt(fileName), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }


    public static String upload(byte[] data, String fileName) {  //字节 ，文件名字

            NameValuePair[] meta_list = null;
        String filePath = null;
        try {
            filePath = storageClient1.upload_file1(data, getFileExt(fileName), meta_list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;


    }

    /**
     * <B>方法名称：</B>下载方法<BR>
     * <B>概要说明：</B>通过文件id进行下载<BR>

     */
    public static void download(String filePath, HttpServletResponse response)  {
        String fileSuffixName = filePath.substring(filePath.lastIndexOf("/")+1, filePath.length());
        String fileName=fileSuffixName.substring(0,fileSuffixName.lastIndexOf("."));
       // File  file=File.createTempFile(fileName,getFileExt(fileSuffixName));

        byte[] bytes = new byte[0];
        try {
            bytes = storageClient1.download_file1(filePath);
            OutputStream output = response.getOutputStream();
            InputStream input = new ByteArrayInputStream(bytes);
            int len=0;
            //  FileOutputStream downloadFile = new FileOutputStream(file);
            while ((len = input.read(bytes)) != -1) {
                output.write(bytes, 0, len);
                output.flush();
            }

            output.close();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    /**
     * <B>方法名称：</B>删除方法<BR>
     * <B>概要说明：</B>根据id来删除一个文件<BR>
     * @param filePath 文件id
     * @return 删除成功返回0，非0则操作失败，
     *         返回错误代码2 表示文件不存在
     *
     */
    public static int delete(String groupName, String filePath) {
        try {
            int result = storageClient1.delete_file1(filePath);
            return result;
        } catch (Exception ex) {

            return 0;
        }
    }

    /**修改文件
     * @param oldFileId 旧文件id
     * @param file 新文件
     * @param path 新文件路径
     * @return 上传成功返回id，失败返回null
     */
    public static String modify(String oldGroupName, String oldFileId, File file, String path) {
        String fileid = null;
        try {
            // 先上传
            fileid = upload(file, path);
            if (fileid == null) {
                return null;
            }
            // 再删除
            int delResult = delete(oldGroupName, oldFileId);
            if (delResult != 0) {
                return null;
            }
        } catch (Exception ex) {

            return null;
        }
        return fileid;
    }

    //获得文件后缀
    private static String getFileExt(String fileName) {
        if (StringUtils.isBlank(fileName) || !fileName.contains(".")) {
            return "";
        } else {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
    }



    public static String upload(File file, String fileName) {
        FileInputStream fis = null;
        try {
            NameValuePair[] meta_list = null; // new NameValuePair[0];
            fis = new FileInputStream(file);
            byte[] file_buff = null;
            if (fis != null) {
                int len = fis.available();
                file_buff = new byte[len];
                fis.read(file_buff);
            }
            String filePath = storageClient1.upload_file1(file_buff, getFileExt(fileName), meta_list);

            return filePath;
        } catch (Exception ex) {

            return null;
        }finally {
            try {
                fis.close();
//                trackerServer.close();
//                storageServer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * jar包扫不到 配合文件处理类
     * 读取resoures下的配置conf路径问题处
     *  流化资源 → 写进新建的file→ClientGlobal读取新建的file 路径 初始化配置→
     */
    public static File  path() throws IOException {
    FastDFSClientUtils fdfs=new FastDFSClientUtils();
    File file = null;
    String resource = "/fdfs_client.conf";
    URL res =fdfs.getClass().getResource(resource);
    System.out.println("res"+res);
    System.out.println("res.getProtocol()"+res.getProtocol());
    if (res.getProtocol().equals("jar")) {
        try {
            InputStream input = fdfs.getClass().getResourceAsStream(resource);
            file = File.createTempFile("fdfs_client", ".conf");
            System.out.println("创建的"+file);
            OutputStream out = new FileOutputStream(file);
            int read;
            byte[] bytes = new byte[1024];

            while ((read = input.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.close();
            return file;
        } catch (IOException ex) {
            System.out.println("--报错");
        }
    } else {
        //this will probably work in your IDE, but not from a JAR
        file = new File(res.getFile());
    }

    if (file != null && !file.exists()) {
        throw new RuntimeException("Error: File " + file + " not found!");
    }
    return file;
}
}
