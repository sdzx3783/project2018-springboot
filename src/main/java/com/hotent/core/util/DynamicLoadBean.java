package com.hotent.core.util;

import java.io.IOException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

public class DynamicLoadBean implements ApplicationContextAware {
	private ConfigurableApplicationContext applicationContext = null;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = (ConfigurableApplicationContext) applicationContext;
	}

	public ConfigurableApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	public void loadBean(String configLocationString) {
		XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(
				(BeanDefinitionRegistry) this.getApplicationContext().getBeanFactory());
		beanDefinitionReader.setResourceLoader(this.getApplicationContext());
		beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(this.getApplicationContext()));

		try {
			String[] e = new String[]{configLocationString};

			for (int i = 0; i < e.length; ++i) {
				beanDefinitionReader.loadBeanDefinitions(this.getApplicationContext().getResources(e[i]));
			}
		} catch (BeansException arg4) {
			arg4.printStackTrace();
		} catch (IOException arg5) {
			arg5.printStackTrace();
		}

	}
}