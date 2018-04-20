package com.makshi.framework.mainframe.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
@Configuration
public class PropertiesConfig {
	@Bean(value = "configproperties")
    public YamlPropertiesFactoryBean getConfigproperties()
    {
		YamlPropertiesFactoryBean  propertiesFactoryBean = new YamlPropertiesFactoryBean ();
		propertiesFactoryBean.setResources(new ClassPathResource("application.yml"));
		return propertiesFactoryBean;
    }
}
