package com.hotent.core.web.controller;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.json.SmartDateEditor;
import com.hotent.core.page.PageList;
import com.hotent.core.util.ConfigUtil;
import com.hotent.core.web.ResultMessage;

public class BaseController extends GenericController {
	public static final String Message = "message";
//	@Resource
//	protected ConfigurableBeanValidator confValidator;

	public void addMessage(ResultMessage message, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("message", message);
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		this.logger.debug("init binder ....");
		binder.registerCustomEditor(Integer.class, (String) null,
				new CustomNumberEditor(Integer.class, (NumberFormat) null, true));
		binder.registerCustomEditor(Long.class, (String) null,
				new CustomNumberEditor(Long.class, (NumberFormat) null, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new SmartDateEditor());
	}

	protected ResultMessage validForm(String form, Object obj, BindingResult result, HttpServletRequest request) {
		ResultMessage resObj = new ResultMessage(1, "");
//		this.confValidator.setFormName(form);
//		this.confValidator.validate(obj, result);
//		if (result.hasErrors()) {
//			resObj.setResult(0);
//			List list = result.getFieldErrors();
//			String errMsg = "";
//
//			String msg;
//			for (Iterator i$ = list.iterator(); i$.hasNext(); errMsg = errMsg + msg + "\r\n") {
//				FieldError err = (FieldError) i$.next();
//				msg = this.getText(err.getDefaultMessage(), err.getArguments(), request);
//			}
//
//			resObj.setMessage(errMsg);
//		}

		return resObj;
	}

	public ModelAndView getView(String category, String id) {
		String view = ConfigUtil.getVal(category, id);
		return new ModelAndView(view);
	}

	protected Map<String, Object> getMapByPageList(PageList pageList) {
		HashMap map = new HashMap();
		map.put("rows", pageList);
		map.put("total", Integer.valueOf(pageList.getTotalCount()));
		return map;
	}

	protected Map<String, Object> getMapByPageListJq(PageList pageList) {
		HashMap map = new HashMap();
		map.put("rows", pageList);
		map.put("records", Integer.valueOf(pageList.getTotalCount()));
		map.put("page", Integer.valueOf(pageList.getPageBean().getCurrentPage()));
		map.put("total", Integer.valueOf(pageList.getTotalPage()));
		return map;
	}
}