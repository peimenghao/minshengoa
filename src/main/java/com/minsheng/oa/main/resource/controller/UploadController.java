package com.minsheng.oa.main.resource.controller;


import com.minsheng.oa.main.resource.model.Resource;
import com.minsheng.oa.main.resource.service.ResourceService;
import com.minsheng.oa.utils.resultMap.ResultMap;
import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Path("/load")
@Component("GlobalProperties")
public class UploadController {

    @Autowired
    ResultMap resultMap;

    @Autowired
    ResourceService resourceService;

    @Value("${path.upload}")
    private String path;

    @Path("/upload")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Map<String, Object> upload(@FormDataParam("file") InputStream fileInputStream,
                                      @FormDataParam("file") FormDataContentDisposition fileDetail,
                                      @Context ServletContext ctx,
                                      @FormDataParam("userId") Integer userId,  //如果userId 为null ，则存储为公共文件
                                      @FormDataParam("isPublic") Integer isPublic
    )

            throws UnsupportedEncodingException {

        //修改编码格式,乱码中文文件名转格式
        String fileName = new String(fileDetail.getFileName().getBytes("ISO-8859-1"), "utf-8");
        //最终文件名字：uuid字符+ 扩展名
        String resourceName = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));

        // File upload = new File("D:\\upload",fileName); //存在d盤
        File upload = new File(path, resourceName);
        System.out.println(upload);
        Resource dbResource = resourceService.findByOriginName(fileName);
        if (dbResource != null) {
            return resultMap.resutError("文件名重复");
        }

        Resource resource = new Resource();
        resource.setResourceName(resourceName);
        resource.setOriginName(fileName);

        resource.setIsPublic(isPublic);    //为0则为私有文件文件，1为共有文件
        resource.setUserId(userId);

        resource.setUrl(path + "\\" + resourceName);
        resourceService.saveResouce(resource);  //保存图片信息到数据库
        try {

            FileUtils.copyInputStreamToFile(fileInputStream, upload);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap.resutSuccess();
    }


    @POST
    @Path("downLoad")
    public Response downLoad(@FormParam("resourceId") Integer resourceId,
                             @Context ServletContext ctx)
            throws IOException {

        Resource resource = resourceService.findByResourceId(resourceId);  //根据文件名字查询资源
        String resourceName = resource.getResourceName();                  //获得文件存储时的名字
        String originName = resource.getOriginName();
        File f = new File(path, resourceName);
        originName = java.net.URLEncoder.encode(originName, "ISO-8859-1");
        System.out.println("originName" + originName);
        if (!f.exists()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(f).header("Content-Disposition", "attachment;filename=" + originName).encoding("ISO-8859-1")
                    .header("Cache-Control", "no-cache").build();
        }
    }


    @Path("/getPublicFile")//获得所有公共文件
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getPublicFile() {
        List<Resource> resourceList = resourceService.findPublicFile();
        return resultMap.resutSuccessDate(resourceList);
    }

    @Path("/getResourceByUserId") //获得用户私有文件
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getResourceByUserId(@QueryParam("userId") Integer userId) {
        List<Resource> resourceList = resourceService.findByUserId(userId);
        return resultMap.resutSuccessDate(resourceList);
    }

    @Path("/deleteFileById")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> delFile(@QueryParam("resourceId") Integer resourceId) {
        Resource resource = resourceService.findByResourceId(resourceId);
        System.out.println(resource.getUrl());
        File file = new File(resource.getUrl());
        if (file.exists() && file.isFile()) {
            file.delete();
        }
        //文件夹删除

        resourceService.deleteBySourceId(resourceId);   //数据库删除
        return resultMap.resutSuccess("delete success");
    }

 //    @Path("/getAllResource")   //获得所有资源文件
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Map<String, Object> getAllResource() {
//        List<Resource> resourceList = resourceService.findAll();
//        return resultMap.resutSuccessDate(resourceList);
//    }


}

