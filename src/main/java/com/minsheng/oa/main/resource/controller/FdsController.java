package com.minsheng.oa.main.resource.controller;

import com.minsheng.oa.main.resource.dfs.FastDFSClientUtils;
import com.minsheng.oa.main.resource.model.Resource;
import com.minsheng.oa.main.resource.service.ResourceService;
import com.minsheng.oa.utils.FileUtil;
import com.minsheng.oa.utils.resultMap.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@CrossOrigin
@RequestMapping("/dfs")
public class FdsController {  //

    @Value("${fdfs.resHost}")
    private  String resHost;         //配置文件中获取服务器id

    @Value("${fdfs.storagePort}") //配置文件中获取端口号
    private String storagePort;

    @Autowired
    ResourceService resourceService;

    @Autowired
    ResultMap  resultMap;



    @RequestMapping("/upload")
    @ResponseBody
    public Map<String, Object> fdfsUpload(@RequestParam("file") MultipartFile multfile,
                                          @RequestParam("userId") Integer userId,
                                          @RequestParam("isPublic") Integer isPublic
    ) throws IOException {
        //格式转换multfile---File
        String fileName = multfile.getOriginalFilename();

        // 查询私有或者共享盘文件是否有这个文件名字
        Resource dbResource = resourceService.findByOriginName(fileName,isPublic,userId);
        System.out.println("dbResource"+dbResource);
        if (dbResource != null) {
            return resultMap.resutError("上传失败，文件名重复");
        }

        String suffix = fileName.substring(fileName.lastIndexOf("."));
        File file = File.createTempFile(UUID.randomUUID().toString(), suffix); //创建临时file
        multfile.transferTo(file); // 转换为file格式
        String fileSize = FileUtil.readableFileSize(file.length());//获得file大小

        String filePath = FastDFSClientUtils.upload1(file.toString(), fileName); //执行上传


        String url = resHost + ":" + storagePort + "/" + filePath;
        System.out.println(resHost + ":" + storagePort + "/" + filePath);

        FileUtil.deleteFile(file);          //刪除临时文件

        //保存到数据库
        Resource resource = new Resource();
        resource.setOriginName(fileName);
        resource.setFileSize(fileSize);
        resource.setUrl(url);
        resource.setFilePath(filePath);
        resource.setUserId(userId);
        resource.setIsPublic(isPublic);
        resourceService.saveResouce(resource);

        return resultMap.resutSuccess("上传成功");
    }


    /**
     * 根据id 查询文件路径，传给fastdfs client 进行下载           s
     *
     * @param resourceId
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/download")
    @ResponseBody
    public String download(@RequestParam("resourceId") Integer resourceId, HttpServletResponse response) throws IOException {
        System.out.println(resourceId);
        Resource resource = resourceService.findByResourceId(resourceId);//数据库查询资源
        String filePath = resource.getFilePath();  //获得资源路径
        String originName = resource.getOriginName();  //获得原始名字
        //带后缀的文件名字
        String fileSuffixName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
        System.out.println("fileSuffixName==" + fileSuffixName);

        System.out.println(originName);
        String downloadFileName = URLEncoder.encode(originName, "ISO-8859-1");

        response.setHeader("Content-Disposition", "attachment;filename=" + downloadFileName);

        try {
            FastDFSClientUtils.download(filePath, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "success";
    }


    @RequestMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestParam("resourceId") Integer resourceId) throws IOException {
        Resource resource = resourceService.findByResourceId(resourceId);
        String filePath = resource.getFilePath();
        Integer i = FastDFSClientUtils.delete("group1", filePath);
        if (i == 0) {
            resourceService.deleteBySourceId(resourceId);
            return resultMap.resutSuccess("刪除成功");
        }
        if (i == 2) {
            return resultMap.resutError("文件不存在");
        }
        return resultMap.resutError("error");
    }


    @RequestMapping("/getByUserId")  //查询该用户下的用户id
    @ResponseBody
    public Map<String, Object> getByUserId(@QueryParam("userId") Integer userId) {
        List<Resource> resourceList = resourceService.findByUserId(userId);
        return resultMap.resutSuccessDate(resourceList);
    }


    @RequestMapping("/getPublicFile")
    @ResponseBody
    public Map<String, Object> getPublicFile() {
        List<Resource> resourceList = resourceService.findPublicFile();
        return resultMap.resutSuccessDate(resourceList);
    }



}
