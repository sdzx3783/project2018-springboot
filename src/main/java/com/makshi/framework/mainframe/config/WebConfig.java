package com.makshi.framework.mainframe.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.hotent.core.web.filter.AopFilter;
import com.hotent.core.web.filter.EncodingFilter;
import com.hotent.core.web.listener.StartupListner;

@Configuration
public class WebConfig {
	@Bean
	public FilterRegistrationBean encodingFilter(){
		FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
		EncodingFilter encodingFilter=new EncodingFilter();
		filterRegistrationBean.setFilter(encodingFilter);
		filterRegistrationBean.addInitParameter("encoding", "UTF-8");
		filterRegistrationBean.addInitParameter("contentType", "text/html;charset=UTF-8");
		List<String> urls=new ArrayList<>();
		urls.add("/*");
		filterRegistrationBean.setUrlPatterns(urls);
		return filterRegistrationBean;
	}
	@Bean
	public FilterRegistrationBean aopFilter(){
		FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
		AopFilter aopFilter=new AopFilter();
		filterRegistrationBean.setFilter(aopFilter);
		List<String> urls=new ArrayList<>();
		urls.add("/*");
		filterRegistrationBean.setUrlPatterns(urls);
		return filterRegistrationBean;
	}
	
	@SuppressWarnings("rawtypes")
	@Bean
    public ServletListenerRegistrationBean httpSessionEventPublisherRegist() {
        ServletListenerRegistrationBean srb = new ServletListenerRegistrationBean();
        srb.setListener(new HttpSessionEventPublisher());
        return srb;
    }
	@SuppressWarnings("rawtypes")
	@Bean
    public ServletListenerRegistrationBean startupListnerRegist() {
        ServletListenerRegistrationBean srb = new ServletListenerRegistrationBean();
        srb.setListener(new StartupListner());
        return srb;
    }
}
