package com.minsheng.oa.main.resource.fastdfs;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
public class FastDFSClientWrapper {  // 上传并且接收返回路径storePath.getFullPath()= group1/M00/00/00/wKhuwV0TNHWARTOPAACFMhdCzI435.docx

    @Autowired
    private FastFileStorageClient storageClient;

    @Value("${fdfs.resHost}")
    private String resHost;         //配置文件中获取服务器id

    @Value("${fdfs.storagePort}") //配置文件中获取端口号
    private String storagePort;

    public String uploadFile(MultipartFile file) throws IOException {  //上传
        StorePath storePath = storageClient.uploadFile((InputStream)file.getInputStream(),file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
        return getResAccessUrl(storePath);
    }

    /**
     * 封装文件完整URL地址
     * @param storePath
     * @return
     */
    private String getResAccessUrl(StorePath storePath) {
        String fileUrl = "http://" + resHost + ":" + storagePort + "/" + storePath.getFullPath();
        System.out.println("getFullPath=="+storePath.getFullPath()+"----getPath="+storePath.getPath());
        return fileUrl;
    }
}
