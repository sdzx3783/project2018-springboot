package com.makshi.framework.mainframe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class BeanConfig {
	@Bean(value = "sessionLocaleResolver")
    public SessionLocaleResolver getSessionLocaleResolver()
    {
        return new SessionLocaleResolver();
    }
}
