package com.hotent.core.web.servlet;

import com.hotent.core.util.StringUtil;
import com.hotent.core.valid.ValidationUtil;
import com.hotent.core.web.util.RequestUtil;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidJs extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/javascript;charset=utf-8");
		String form = RequestUtil.getString(request, "form");
		Locale local = RequestUtil.getLocal(request);
		String str = "";
		if (StringUtil.isNotEmpty(form)) {
			try {
				str = ValidationUtil.getJs(form, local);
			} catch (TemplateException arg6) {
				str = "";
			}
		}

		response.getWriter().print(str);
	}
}