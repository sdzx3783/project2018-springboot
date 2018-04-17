package com.hotent.core.web.filter;

import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.consts.SystemConst;
import com.hotent.core.web.ResultMessage;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

public class PermissionFilter extends AbstractSecurityInterceptor implements Filter {
	private FilterInvocationSecurityMetadataSource securityMetadataSource;

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		boolean canInvokeNext = this.canInvokeNextFilter(request, response, fi);
		if (canInvokeNext) {
			this.invoke(fi);
		}

	}

	private boolean canInvokeNextFilter(ServletRequest request, ServletResponse response, FilterInvocation fi)
			throws IOException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Collection configAttributes = this.obtainSecurityMetadataSource().getAttributes(fi);
		if (!configAttributes.contains(SystemConst.ROLE_CONFIG_ANONYMOUS)
				&& ("XMLHttpRequest".equalsIgnoreCase(httpRequest.getHeader("X-Requested-With"))
						|| "true".equalsIgnoreCase(httpRequest.getParameter("isAjaxRequest")))) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null || !(authentication.getPrincipal() instanceof ISysUser)) {
				ResultMessage resultMessage = new ResultMessage(0, "登录超时，请重新登录！");
				resultMessage.setCause("nologin");
				response.getWriter().print(resultMessage);
				return false;
			}
		}

		return true;
	}

	public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public Class<? extends Object> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	public void invoke(FilterInvocation fi) throws IOException, ServletException {
		super.setRejectPublicInvocations(false);
		InterceptorStatusToken token = super.beforeInvocation(fi);

		try {
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} finally {
			super.afterInvocation(token, (Object) null);
		}

	}

	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource newSource) {
		this.securityMetadataSource = newSource;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}
}