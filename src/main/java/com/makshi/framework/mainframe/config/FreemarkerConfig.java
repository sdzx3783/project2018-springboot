package com.makshi.framework.mainframe.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import com.hotent.core.engine.FreemarkEngine;

@Configuration
public class FreemarkerConfig {

    @Bean(value = "freemarkerConfiguration")
    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfigurationFactoryBean()
    {
        FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean = new FreeMarkerConfigurationFactoryBean();
        freeMarkerConfigurationFactoryBean.setTemplateLoaderPath("classpath:template/");
        freeMarkerConfigurationFactoryBean.setDefaultEncoding("UTF-8");
        return freeMarkerConfigurationFactoryBean;
    }
    
    
    @Bean(value = "freemarkEngine")
    public FreemarkEngine getFreemarkEngine(@Qualifier("freemarkerConfiguration") FreeMarkerConfigurationFactoryBean freemarkerConfiguration)
    {
    	FreemarkEngine freemarkEngine= new FreemarkEngine();
    	freemarkEngine.setConfiguration();
    	
        return freemarkEngine;
    }
    
}
