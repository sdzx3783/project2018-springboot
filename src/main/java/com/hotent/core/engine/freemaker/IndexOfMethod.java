package com.hotent.core.engine.freemaker;

import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import java.util.List;

public class IndexOfMethod implements TemplateMethodModel {
	public TemplateModel exec(List args) throws TemplateModelException {
		if (args.size() != 2) {
			throw new TemplateModelException("此访问参数必须为两个.");
		} else {
			String str1 = (String) args.get(1);
			String str2 = (String) args.get(0);
			return new SimpleNumber(str1.indexOf(str2));
		}
	}
}