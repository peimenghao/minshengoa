package com.minsheng.oa.main.upload.controller;


import com.minsheng.oa.utils.resultMap.ResultMap;
import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

@Path("/load")
@Component
public class UploadController {

    @Autowired
    ResultMap resultMap;


    @Path("/upload")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Map<String, Object> upload(@FormDataParam("file") InputStream fileInputStream,
                                      @FormDataParam("file") FormDataContentDisposition fileDetail,
                                      @Context ServletContext ctx) {

       String fileName= UUID.randomUUID().toString() + "." + fileDetail.getFileName();
       // File upload = new File("D:\\upload",fileName);
        File upload = new File("src/main/resources/static/upload",fileName);

        try {
            FileUtils.copyInputStreamToFile(fileInputStream, upload);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap.resutSuccess();

    }
}
