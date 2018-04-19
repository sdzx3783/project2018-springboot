package com.makshi.framework.mainframe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

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
}
