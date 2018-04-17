package com.makshi.framework.mainframe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DemoController {

    @RequestMapping("/demo")
    public String demo(){
        return "hi";
    }
    
    @RequestMapping("/login.ht")
	public ModelAndView edit() {
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}
}
