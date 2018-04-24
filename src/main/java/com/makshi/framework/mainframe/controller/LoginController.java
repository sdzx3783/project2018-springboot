package com.makshi.framework.mainframe.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	@Resource
	private ReloadableResourceBundleMessageSource  messageSource;
	
    @RequestMapping("/login")
    public String demo(HttpServletRequest request,HttpServletResponse response){
        return "login.html";
    }
}
