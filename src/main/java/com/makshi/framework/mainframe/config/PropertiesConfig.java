package com.makshi.framework.mainframe.config;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

public class PropertiesConfig {
	@Bean(value = "configproperties")
    public PropertiesFactoryBean getConfigproperties()
    {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        Resource location=new Resource[]{};
		propertiesFactoryBean.setLocation(location);
        
		return propertiesFactoryBean;
    }
}
