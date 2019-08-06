package com.minsheng.oa.main.ueditor.richText.controller;

import com.minsheng.oa.main.ueditor.richText.model.RichText;
import com.minsheng.oa.main.ueditor.richText.service.RichTextService;
import com.minsheng.oa.utils.resultMap.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import java.util.List;
import java.util.Map;

import static com.minsheng.oa.utils.DateUtils.getNowTime;

@Path("richText")
public class richTextController {

    @Autowired
    RichTextService richTextService;

    @Autowired
    ResultMap resultMap;


    @Path("/saveRichText")//  保存富文本
    @POST
    @Produces("application/json")
    public Map<String, Object> saveMatter(@BeanParam RichText richText) {
        richText.setCreateTime(getNowTime());

        richTextService.save(richText);
        return resultMap.resutSuccess("保存成功");
    }

    @Path("/getByTextId") //  根据id查询单个富文本
    @GET
    @Produces("application/json")
    public Map<String, Object> getTextById(@QueryParam("textId") Integer textId) {
        RichText richText = richTextService.findByTextId(textId);
        return resultMap.resutSuccessDate(richText);
    }

    @Path("/getByUserId") //  根据用户id查询用户下所有文本
    @GET
    @Produces("application/json")
    public Map<String, Object> getByUserId(@QueryParam("userId") Integer userId) {
        List<RichText> richTextList = richTextService.findByUserId(userId);
        return resultMap.resutSuccessDate(richTextList);
    }
}


