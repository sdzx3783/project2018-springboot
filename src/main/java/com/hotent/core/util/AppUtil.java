package com.hotent.core.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

public class AppUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext;
	private static ServletContext servletContext;

	public static void init(ServletContext _servletContext) {
		servletContext = _servletContext;
	}

	public static ServletContext getServletContext() throws Exception {
		return servletContext;
	}

	public void setApplicationContext(ApplicationContext contex) throws BeansException {
		applicationContext = contex;
	}

	public static ApplicationContext getContext() {
		return applicationContext;
	}

	public static List<Class> getImplClass(Class clazz) throws ClassNotFoundException {
		ArrayList list = new ArrayList();
		Map map = applicationContext.getBeansOfType(clazz);
		Iterator i$ = map.values().iterator();

		while (i$.hasNext()) {
			Object obj = i$.next();
			String name = obj.getClass().getName();
			int pos = name.indexOf("$$");
			if (pos > 0) {
				name = name.substring(0, name.indexOf("$$"));
			}

			Class cls = Class.forName(name);
			list.add(cls);
		}

		return list;
	}

	public static Map<String, Object> getImplInstance(Class clazz) throws ClassNotFoundException {
		Map map = applicationContext.getBeansOfType(clazz);
		return map;
	}

	public static <C> C getBean(Class<C> cls) {
		return applicationContext.getBean(cls);
	}

	public static Object getBean(String beanId) {
		return applicationContext.getBean(beanId);
	}

	public static String getAppAbsolutePath() {
		return servletContext.getRealPath("/");
	}

	public static String getRealPath(String path) {
		return servletContext.getRealPath(path);
	}

	public static String getClasspath() {
		String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String rootPath = "";
		if ("\\".equals(File.separator)) {
			rootPath = classPath.substring(1);
			rootPath = rootPath.replace("/", "\\");
		}

		if ("/".equals(File.separator)) {
			rootPath = classPath.substring(1);
			rootPath = rootPath.replace("\\", "/");
		}

		return rootPath;
	}

	public static void publishEvent(ApplicationEvent applicationEvent) {
		applicationContext.publishEvent(applicationEvent);
	}
}