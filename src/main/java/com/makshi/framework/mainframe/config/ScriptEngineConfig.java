package com.makshi.framework.mainframe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hotent.core.engine.GroovyScriptEngine;
@Configuration
public class ScriptEngineConfig {
	@Bean(value = "scriptEngine")
    public GroovyScriptEngine getGroovyScriptEngine()
    {
        return new GroovyScriptEngine();
    }
}
