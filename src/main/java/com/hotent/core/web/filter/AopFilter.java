package com.hotent.core.web.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.util.AppUtil;
import com.hotent.core.web.exception.AuthenticationException;
import com.hotent.core.web.security.AuthenticationFailHandler;
import com.hotent.core.web.security.SecurityMetadataSource;
import com.hotent.core.web.util.RequestContext;
import com.hotent.platform.dao.system.SysUserDao;
import com.hotent.platform.model.system.SysUser;
import com.makshi.framework.mainframe.config.properties.SecurityProperties;

public class AopFilter implements Filter {
	private static String token_key;
	private static AuthenticationFailHandler failAuthenticateHandler;
	private static SecurityMetadataSource securityMetadataSource;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String requestURI = "";
		try {
			
			requestURI=((HttpServletRequest) request).getRequestURI();
			if(!securityMetadataSource.isanonymousUrl(requestURI)
					&& !securityMetadataSource.isstaticRes(requestURI)
					&& !authentication(request)){
				throw new AuthenticationException("用户授权失败!");
			}
			ContextUtil.clearAll();
			RequestContext.setHttpServletRequest((HttpServletRequest) request);
			RequestContext.setHttpServletResponse((HttpServletResponse) response);
			SessionLocaleResolver sessionResolver = (SessionLocaleResolver) AppUtil
					.getBean(SessionLocaleResolver.class);
			Locale local = sessionResolver.resolveLocale((HttpServletRequest) request);
			ContextUtil.setLocale(local);
			chain.doFilter(request, response);
		} catch (AuthenticationException e) {
			failAuthenticateHandler.onAuthenticationFailure((HttpServletRequest)request, (HttpServletResponse) response, e);
		} finally {
			ContextUtil.clearAll();
		}

	}
	public static void init(){
		failAuthenticateHandler = AppUtil.getBean(AuthenticationFailHandler.class);
		securityMetadataSource=AppUtil.getBean(SecurityMetadataSource.class);
		SecurityProperties securityProperties = AppUtil.getBean(SecurityProperties.class);
		token_key=securityProperties.getToken_key();
	}

	private boolean authentication(ServletRequest request) {
		boolean isAuthenticationed=false;
		Object attribute = request.getAttribute(token_key);
		SysUserDao sysUserDao = AppUtil.getBean(SysUserDao.class);
		if(attribute!=null){
			String account=(String) attribute;
			if(StringUtils.isNoneEmpty(account)){
				SysUser byAccount = sysUserDao.getByAccount(account);
				if(byAccount!=null){
					ContextUtil.setCurrentUser(byAccount);
					isAuthenticationed=true;
				}
			}
		}
		return isAuthenticationed;
	}

	public void destroy() {
	}
}