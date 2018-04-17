package com.hotent.core.web;

import com.hotent.core.web.NoSuchViewResolverException;
import java.util.Locale;
import java.util.Map;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class MixedViewResolver implements ViewResolver {
	private Map<String, ViewResolver> resolvers;

	public void setResolvers(Map<String, ViewResolver> resolvers) {
		this.resolvers = resolvers;
	}

	public View resolveViewName(String viewName, Locale locale) throws Exception {
		int n = viewName.lastIndexOf(46);
		if (n == -1) {
			throw new NoSuchViewResolverException();
		} else {
			String suffix = viewName.substring(n + 1);
			ViewResolver resolver = (ViewResolver) this.resolvers.get(suffix);
			if (resolver != null) {
				return resolver.resolveViewName(viewName, locale);
			} else {
				throw new NoSuchViewResolverException("No ViewResolver for " + suffix);
			}
		}
	}
}