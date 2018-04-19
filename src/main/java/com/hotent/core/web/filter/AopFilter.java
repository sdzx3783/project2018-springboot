package com.hotent.core.web.filter;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.util.AppUtil;
import com.hotent.core.web.util.RequestContext;
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
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

public class AopFilter implements Filter {
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			ContextUtil.clearAll();
			RequestContext.setHttpServletRequest((HttpServletRequest) request);
			RequestContext.setHttpServletResponse((HttpServletResponse) response);
			SessionLocaleResolver sessionResolver = (SessionLocaleResolver) AppUtil
					.getBean(SessionLocaleResolver.class);
			Locale local = sessionResolver.resolveLocale((HttpServletRequest) request);
			ContextUtil.setLocale(local);
			chain.doFilter(request, response);
		} finally {
			ContextUtil.clearAll();
		}

	}

	public void destroy() {
	}
}