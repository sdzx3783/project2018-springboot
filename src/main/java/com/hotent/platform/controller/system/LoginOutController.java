package com.hotent.platform.controller.system;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.log.SysAuditThreadLocalHolder;
import com.hotent.core.web.controller.BaseController;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.annotion.ActionExecOrder;
import com.hotent.platform.model.system.SysAuditModelType;

/**
 * 登录访问控制器，用于扩展Spring Security 缺省的登录处理器
 * 
 * @author csx
 * 
 */
@Controller
@RequestMapping("/logout.ht")
@Action(ownermodel = SysAuditModelType.LOGIN_MANAGEMENT)
public class LoginOutController extends BaseController {
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private String url="/login.jsp";
	@RequestMapping
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().invalidate();
		redirectStrategy.sendRedirect(request, response, url);
	}
}
