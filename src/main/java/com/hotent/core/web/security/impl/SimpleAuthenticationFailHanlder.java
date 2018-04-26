package com.hotent.core.web.security.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;

import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.exception.AuthenticationException;
import com.hotent.core.web.security.AuthenticationFailHandler;
import com.makshi.framework.mainframe.config.properties.SecurityProperties;

@Component
public class SimpleAuthenticationFailHanlder implements AuthenticationFailHandler {
	
	@Autowired
	private SecurityProperties securityProperties;
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		if (("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With")))
				|| ("true".equalsIgnoreCase(request.getParameter("isAjaxRequest")))) {
			ResultMessage resultMessage = new ResultMessage(0, "用户认证失败！");
			resultMessage.setCause("nologin");
			response.getWriter().print(resultMessage);
		}else{
			redirectStrategy.sendRedirect(request, response, securityProperties.getDefaultFailureUrl());
		}
	}

}
