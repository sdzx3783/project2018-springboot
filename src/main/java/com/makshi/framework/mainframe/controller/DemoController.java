package com.makshi.framework.mainframe.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class DemoController {

    @RequestMapping("/demo")
    public String demo(){
        return "hi";
    }
    
    @RequestMapping("/login.ht")
	public ModelAndView edit() {
		ModelAndView mv = new ModelAndView("/view/login.jsp");
		return mv;
	}
}
