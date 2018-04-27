package com.makshi.framework.mainframe.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/platform")
public class LoginController {
	@Resource
	private ReloadableResourceBundleMessageSource  messageSource;
	
    @RequestMapping("/login")
    public String demo(HttpServletRequest request,HttpServletResponse response, Map<String, Object> map){
    	Cookie[] cookies = request.getCookies();
    	Cookie cookie = new Cookie("origSwitch", "mycookie");
		cookie.setPath("/");
		cookie.setMaxAge(-1);
		response.addCookie(cookie);
    	map.put("ctx", "/test");
        return "login.html";
    }
}
