package com.makshi.framework.mainframe.config;

import java.util.Properties;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class PropertiesConfig {
	@Bean(value = "configproperties")
    public FactoryBean<Properties> getConfigproperties()
    {
        YamlPropertiesFactoryBean propertiesFactoryBean = new YamlPropertiesFactoryBean();
        
		return propertiesFactoryBean;
    }
}
