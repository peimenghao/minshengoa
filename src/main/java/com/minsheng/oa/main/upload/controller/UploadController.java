package com.minsheng.oa.main.upload.controller;


import com.minsheng.oa.utils.resultMap.ResultMap;
import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

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
                                      @Context ServletContext ctx) throws UnsupportedEncodingException {
       //修改编码格式
        String fileName=  new String(fileDetail.getFileName().getBytes("ISO-8859-1"),"utf-8");
        // File upload = new File("D:\\upload",fileName); //存在d盤
        File upload = new File("src/main/resources/static/upload", fileName);

        try {

            FileUtils.copyInputStreamToFile(fileInputStream, upload);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap.resutSuccess();

    }


    @POST
    @Path("downLoad")
    public Response showImg(@FormParam("fileName") String fileName, @Context ServletContext ctx) throws IOException {
        System.out.println(fileName);
        File f = new File("src/main/resources/static/upload", fileName);
        if (!f.exists()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(f).header("Content-Disposition", "attachment;filename=" + fileName).encoding("charset=utf-8")
                    .header("Cache-Control", "no-cache").build();
        }
    }


}
