package com.hotent.core.web.controller;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.servlet.ModelAndView;

public class GenericController {
	protected Logger logger = LoggerFactory.getLogger(BaseController.class);
	public final String SUCCESS = "{success:true}";
	public final String FAILURE = "{success:false}";
	private MessageSourceAccessor messages;
	public static final String STEP1 = "1";
	public static final String STEP2 = "2";
	public static final String MESSAGES_KEY = "successMessages";
	public static final String ERRORS = "errors";
	@Resource
	protected Properties configproperties;

	public ModelAndView getAutoView() throws Exception {
		HttpServletRequest request = RequestContext.getHttpServletRequest();
		String requestURI = request.getRequestURI();
		this.logger.debug("requestURI:" + requestURI);
		String contextPath = request.getContextPath();
		requestURI = requestURI.replace(".ht", "");
		int cxtIndex = requestURI.indexOf(contextPath);
		if (cxtIndex != -1) {
			requestURI = requestURI.substring(cxtIndex + contextPath.length());
		}

		String[] paths = requestURI.split("[/]");
		String jspPath;
		if (paths != null && paths.length == 5) {
			jspPath = "/" + paths[1] + "/" + paths[2] + "/" + paths[3] + StringUtil.makeFirstLetterUpperCase(paths[4])
					+ ".jsp";
			return new ModelAndView(jspPath);
		} else if (paths != null && paths.length == 4) {
			jspPath = "/" + paths[1] + "/" + paths[2] + StringUtil.makeFirstLetterUpperCase(paths[3]) + ".jsp";
			return new ModelAndView(jspPath);
		} else {
			this.logger
					.error("your request url is not the right pattern, it is not allowed use this getAutoView method");
			throw new Exception("url:[" + requestURI + "] is not in this pattern:[/子系统/包名/表对应实体名/实体操作方法名.ht]");
		}
	}

	@Autowired
	public void setMessages(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}

	public void saveError(HttpServletRequest request, String msg) {
		this.saveMessage(request, "errors", msg);
	}

	public void saveMessage(HttpServletRequest request, String msg) {
		this.saveMessage(request, "successMessages", msg);
	}

	public void saveMessage(HttpServletRequest request, String key, String msg) {
		Object messages = (List) request.getSession().getAttribute(key);
		if (messages == null) {
			messages = new ArrayList();
		}

		((List) messages).add(msg);
		request.getSession().setAttribute(key, messages);
	}

	public String getText(String msgKey, Locale locale) {
		return this.messages.getMessage(msgKey, locale);
	}

	public String getText(String msgKey, String arg, Locale locale) {
		return this.getText(msgKey, new Object[]{arg}, locale);
	}

	public String getText(String msgKey, Object[] args, Locale locale) {
		return this.messages.getMessage(msgKey, args, locale);
	}

	public String getText(String msgKey, Object... args) {
		return this.messages.getMessage(msgKey, args, ContextUtil.getLocale());
	}

	public String getText(String msgKey) {
		return this.messages.getMessage(msgKey, ContextUtil.getLocale());
	}

	protected String getText(String msgKey, String arg, HttpServletRequest request) {
		Locale locale = ContextUtil.getLocale();
		return this.getText(msgKey, arg, locale);
	}

	protected String getText(String msgKey, Object[] args, HttpServletRequest request) {
		Locale locale = ContextUtil.getLocale();
		return this.getText(msgKey, args, locale);
	}

	protected void writeResultMessage(PrintWriter writer, String resultMsg, String cause, int successFail) {
		ResultMessage resultMessage = new ResultMessage(successFail, resultMsg, cause);
		this.writeResultMessage(writer, resultMessage);
	}

	protected void writeResultMessage(PrintWriter writer, String resultMsg, int successFail) {
		ResultMessage resultMessage = new ResultMessage(successFail, resultMsg);
		this.writeResultMessage(writer, resultMessage);
	}

	protected void writeResultMessage(PrintWriter writer, ResultMessage resultMessage) {
		writer.print(resultMessage);
	}

	protected void saveResultMessage(HttpSession session, String msg, int successFail) {
		ResultMessage resultMsg = new ResultMessage(successFail, msg);
		session.setAttribute("message", resultMsg);
	}

	protected void saveSuccessResultMessage(HttpSession session, String msg) {
		this.saveResultMessage(session, msg, 1);
	}

	protected void saveFailResultMessage(HttpSession session, String msg) {
		this.saveResultMessage(session, msg, 0);
	}

	public void sendJsonToWeb(Object obj, HttpServletResponse response, QueryFilter queryFilter) throws IOException {
		JSONArray json = new JSONArray();
		json.add(obj);
		json.add(queryFilter.getPageBean());
		PrintWriter out = response.getWriter();
		out.print(json.toString());
	}
}