package com.minsheng.oa.main.resource.fastdfs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@CrossOrigin
@RequestMapping("/upload")
public class UploadController {


    @Autowired
    private FastDFSClientWrapper dfsClient;

    @GetMapping("/")
    public String index() {
        return "upload/upload";
    }

    @RequestMapping("/fdfsupload")
    @ResponseBody
    public String fdfsUpload(@RequestParam("file") MultipartFile file
                            ) throws IOException {
        if (file.isEmpty()) {
            return ("Please select a file to upload");
        }
            String fileUrl = dfsClient.uploadFile(file);

        System.out.println(fileUrl);
            return fileUrl;
        }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "upload/uploadStatus";
    }

}
