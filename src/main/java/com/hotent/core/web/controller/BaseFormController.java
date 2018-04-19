package com.hotent.core.web.controller;

import com.hotent.core.json.SmartDateEditor;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.GenericController;
import java.text.NumberFormat;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springmodules.validation.commons.ConfigurableBeanValidator;

public class BaseFormController extends GenericController {
	public Logger logger = LoggerFactory.getLogger(BaseFormController.class);
	@Resource
	protected ConfigurableBeanValidator confValidator;

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		this.logger.debug("init binder ....");
		binder.registerCustomEditor(Integer.class, (String) null,
				new CustomNumberEditor(Integer.class, (NumberFormat) null, true));
		binder.registerCustomEditor(Long.class, (String) null,
				new CustomNumberEditor(Long.class, (NumberFormat) null, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
		binder.registerCustomEditor(Date.class, new SmartDateEditor());
	}

	protected ResultMessage validForm(String form, Object obj, BindingResult result, HttpServletRequest request) {
		ResultMessage resObj = new ResultMessage(1, "");
		return resObj;
	}
}