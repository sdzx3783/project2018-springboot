package com.hotent.core.web.security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;

import com.makshi.framework.mainframe.config.properties.SecurityProperties;

public class SecurityMetadataSource {
	@Autowired
	private SecurityProperties securityProperties;
	private  HashSet<String> anonymousUrls=new HashSet<String>();
	private  AntPathMatcher pathMatcher=new AntPathMatcher();
	private  List<String> staticResoures=null;
	public HashSet<String> getAnonymousUrls() {
		return anonymousUrls;
	}

	public void setAnonymousUrls(HashSet<String> anonymousUrls) {
		this.anonymousUrls = anonymousUrls;
	}
	
	public boolean isanonymousUrl(String url){
		if(anonymousUrls.contains(url))
			return true;
		return false;
	}
	
	public boolean isstaticRes(String url){
		if(staticResoures==null){
			staticResoures=(StringUtils.isEmpty(securityProperties.getStaticResPattern()))
			? null: Arrays.asList(securityProperties.getStaticResPattern().split(","));
		}
		boolean f=false;
		if(staticResoures==null || staticResoures.size()<=0){
			return f;
		}
		for (String s : staticResoures) {
			if(pathMatcher.match(s, url)){
				f=true;
			}
		}
		return f;
	}
	
}
