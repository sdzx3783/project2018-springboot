package com.makshi.framework.mainframe.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Component
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
	private String defaultFailureUrl="/platform/login?error=true";
	private String token_key="x-auth-account";
	private String staticResPattern;
	
	public String getToken_key() {
		return token_key;
	}

	public void setToken_key(String token_key) {
		this.token_key = token_key;
	}

	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}

	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}

	public String getStaticResPattern() {
		return staticResPattern;
	}

	public void setStaticResPattern(String staticResPattern) {
		this.staticResPattern = staticResPattern;
	}
	
	
}
