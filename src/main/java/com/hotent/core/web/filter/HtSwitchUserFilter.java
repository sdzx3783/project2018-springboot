package com.hotent.core.web.filter;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.web.util.CookieUitl;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;

public class HtSwitchUserFilter extends SwitchUserFilter {
	public static final String SwitchAccount = "origSwitch";

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if (this.requiresSwitchUser(request)) {
			this.setAccount(ContextUtil.getCurrentUser().getAccount(), request, response);
		} else if (this.requiresExitUser(request)) {
			this.removeAccount(request, response);
		}

		super.doFilter(req, res, chain);
	}

	private void setAccount(String account, HttpServletRequest req, HttpServletResponse res) {
		CookieUitl.addCookie("origSwitch", account, req, res);
	}

	private void removeAccount(HttpServletRequest req, HttpServletResponse res) {
		CookieUitl.delCookie("origSwitch", req, res);
	}
}