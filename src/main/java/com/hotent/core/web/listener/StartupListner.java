package com.hotent.core.web.listener;

import com.hotent.core.util.ClassLoadUtil;
import com.hotent.core.web.filter.AopFilter;

import javax.servlet.ServletContextEvent;
import org.springframework.web.context.ContextLoaderListener;

public class StartupListner extends ContextLoaderListener {
	public void contextInitialized(ServletContextEvent event) {
		//super.contextInitialized(event);
		ClassLoadUtil.contextInitialized(event);
	}
}