package com.minsheng.oa.main.ueditor.controller;


import com.minsheng.oa.main.ueditor.ActionEnter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于处理关于ueditor插件相关的请求
 * @author Guoqing
 * @date 2017-11-29
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/ueditor")
public class UeditorController {

	@RequestMapping(value = "/exec")
	@ResponseBody
	public String exec(HttpServletRequest request) throws Exception{
		request.setCharacterEncoding("utf-8");
		String rootPath = request.getRealPath("/upload/images");
		System.out.println(rootPath);
		return new ActionEnter( request, rootPath ).exec();

	}

	@RequestMapping(value = "/aa")
	@ResponseBody
	public String aa(HttpServletRequest request) {

		return "success";

	}


}
