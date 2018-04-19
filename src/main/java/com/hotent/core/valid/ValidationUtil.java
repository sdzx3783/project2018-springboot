package com.hotent.core.valid;

import com.hotent.core.engine.FreemarkEngine;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.ResourceUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.valid.Rule;
import com.hotent.core.valid.ValidEnum;
import com.hotent.core.valid.ValidField;
import com.hotent.core.valid.ValidForm;
import com.hotent.core.valid.ValidationUtil.1;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.validator.Arg;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.ValidatorResources;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springmodules.validation.commons.ValidatorFactory;

public class ValidationUtil {
	private static Map<String, ValidEnum> map = new HashMap();

	private static ValidForm getForm(String formName, Locale local) {
		ValidForm form = new ValidForm();
		form.setFormName(formName);
		ApplicationContext ctx = AppUtil.getContext();
		ValidatorFactory factory = (ValidatorFactory) BeanFactoryUtils.beanOfTypeIncludingAncestors(ctx,
				ValidatorFactory.class, true, true);
		ValidatorResources resources = factory.getValidatorResources();
		Form frm = resources.getForm(local, formName);
		if (frm == null) {
			return null;
		} else {
			List list = frm.getFields();
			Iterator i$ = list.iterator();

			while (i$.hasNext()) {
				Field fld = (Field) i$.next();
				Arg arg = fld.getArg(0);
				String displayName = ResourceUtil.getText(arg.getKey(), (Object[]) null, local);
				ValidField vFld = new ValidField();
				vFld.setDisplayName(displayName);
				vFld.setFormName(fld.getProperty());
				getRuleByField(fld, vFld, local);
				form.addField(vFld);
			}

			return form;
		}
	}

	private static void getRuleByField(Field field, ValidField vFld, Locale local) {
		List list = field.getDependencyList();
		Iterator i$ = list.iterator();

		while (i$.hasNext()) {
			String str = (String) i$.next();
			Rule rule = new Rule();
			rule.setName(str);
			vFld.addRule(rule);
			handRule(field, rule, local);
		}

	}

	private static void handRule(Field field, Rule rule, Locale local) {
      Arg arg = field.getArg(0);
      String key = arg.getKey();
      String displayName = ResourceUtil.getText(key, (Object[])null, local);
      String ruleName = rule.getName();
      ValidEnum e = (ValidEnum)map.get(ruleName);
      String tipInfo = "";
      Arg argE;
      String keyE;
      switch(1.$SwitchMap$com$hotent$core$valid$ValidEnum[e.ordinal()]) {
      case 1:
         tipInfo = ResourceUtil.getText("必填", displayName, local);
         String value = field.getVarValue(ruleName);
         if(value != null) {
            rule.setRuleInfo(value);
         } else {
            rule.setRuleInfo("true");
         }

         rule.setTipInfo(tipInfo);
         break;
      case 2:
         tipInfo = ResourceUtil.getText("信用卡验证失败", (Object[])null, local);
         rule.setRuleInfo("true");
         break;
      case 3:
         String datePattern = field.getVarValue("datePattern");
         Object[] aryObjDate = new Object[]{displayName, datePattern};
         tipInfo = ResourceUtil.getText("errors.date", aryObjDate, local);
         rule.setRuleInfo("true");
         break;
      case 4:
         argE = field.getArg(1);
         keyE = argE.getKey();
         String equalName = ResourceUtil.getText(keyE, (Object[])null, local);
         String equalTo = field.getVarValue("equalTo");
         if(StringUtil.isEmpty(equalName)) {
            equalName = "";
         }

         rule.setRuleInfo(equalTo);
         Object[] aryEqual = new Object[]{displayName, equalName};
         tipInfo = ResourceUtil.getText("不相等", aryEqual, local);
         break;
      case 5:
         tipInfo = ResourceUtil.getText("请输入整数", displayName, local);
         rule.setRuleInfo("true");
         break;
      case 6:
         tipInfo = ResourceUtil.getText("请输入正确的邮箱", displayName, local);
         rule.setRuleInfo("true");
         break;
      case 7:
         String max = field.getVarValue("max").replace(",", "");
         Object[] aryObj = new Object[]{displayName, max};
         tipInfo = ResourceUtil.getText("超出最大值", aryObj, local);
         rule.setRuleInfo(max);
         break;
      case 8:
         String maxlength = field.getVarValue("maxlength").replace(",", "");
         Object[] aryMaxlength = new Object[]{displayName, maxlength};
         tipInfo = ResourceUtil.getText("内容过长", aryMaxlength, local);
         rule.setRuleInfo(maxlength);
         break;
      case 9:
         String min = field.getVarValue("min");
         Object[] aryMin = new Object[]{displayName, min};
         tipInfo = ResourceUtil.getText("值过小", aryMin, local);
         rule.setRuleInfo(min);
         break;
      case 10:
         String minlength = field.getVarValue("minlength");
         Object[] aryMinlength = new Object[]{displayName, minlength};
         tipInfo = ResourceUtil.getText("内容过短", aryMinlength, local);
         rule.setRuleInfo(minlength);
         break;
      case 11:
         tipInfo = ResourceUtil.getText("请输入数字", displayName, local);
         rule.setRuleInfo("true");
         break;
      case 12:
         String rmin = field.getVarValue("min");
         String rmax = field.getVarValue("max").replace(",", "");
         Object[] aryRange = new Object[]{displayName, rmin, rmax};
         tipInfo = ResourceUtil.getText("不在数值范围内", aryRange, local);
         String ruleInfo = "[" + rmin + "," + rmax + "]";
         rule.setRuleInfo(ruleInfo);
         break;
      case 13:
         String rminlength = field.getVarValue("minlength");
         String rmaxlength = field.getVarValue("maxlength").replace(",", "");
         Object[] aryRangeLength = new Object[]{displayName, rminlength, rmaxlength};
         tipInfo = ResourceUtil.getText("不在长度范围内", aryRangeLength, local);
         rule.setRuleInfo("[" + rminlength + "," + rmaxlength + "]");
         break;
      case 14:
         String regex = field.getVarValue("regex");
         tipInfo = ResourceUtil.getText("请输入正确正则表达式", displayName, local);
         rule.setRuleInfo(regex);
         break;
      case 15:
         tipInfo = ResourceUtil.getText("请输入正确的UEL", (Object[])null, local);
         rule.setRuleInfo("true");
         break;
      case 16:
         tipInfo = ResourceUtil.getText("请输入正确的手机号码", (Object[])null, local);
         rule.setRuleInfo("true");
         break;
      case 17:
         tipInfo = ResourceUtil.getText("请输入正确的电话号码", (Object[])null, local);
         rule.setRuleInfo("true");
         break;
      case 18:
         tipInfo = ResourceUtil.getText("请选择zip包", (Object[])null, local);
         rule.setRuleInfo("true");
         break;
      case 19:
         tipInfo = ResourceUtil.getText("请输入正确的QQ号", (Object[])null, local);
         rule.setRuleInfo("true");
         break;
      case 20:
         tipInfo = ResourceUtil.getText("请输入中文", displayName, local);
         rule.setRuleInfo("true");
         break;
      case 21:
         tipInfo = ResourceUtil.getText("只能输入数字和字母", displayName, local);
         rule.setRuleInfo("true");
         break;
      case 22:
         tipInfo = ResourceUtil.getText("请输入正确的IP地址", (Object[])null, local);
         rule.setRuleInfo("true");
         break;
      case 23:
         argE = field.getArg(1);
         keyE = argE.getKey();
         String eTime = ResourceUtil.getText(keyE, (Object[])null, local);
         String varValue = field.getVarValue("compStartEndTime");
         rule.setRuleInfo(varValue);
         Object[] aryEquals = new Object[]{displayName, eTime};
         tipInfo = ResourceUtil.getText("时间不符合逻辑", aryEquals, local);
         break;
      case 24:
         String digitsSumLen = field.getVarValue("digitsSum").replace(",", "");
         Object[] aryDigitsSum = new Object[]{displayName, digitsSumLen};
         tipInfo = ResourceUtil.getText("errors.digitsSum", aryDigitsSum, local);
         rule.setRuleInfo(digitsSumLen);
      }

      rule.setTipInfo(tipInfo);
   }

	public static String getJs(String roleForm, Locale local) throws IOException, TemplateException {
		FreemarkEngine freemaker = (FreemarkEngine) AppUtil.getBean(FreemarkEngine.class);
		ValidForm form = getForm(roleForm, local);
		HashMap map = new HashMap();
		map.put("form", form);
		String str = freemaker.mergeTemplateIntoString("validJs.ftl", map);
		return str;
	}

	static {
		EnumSet stateSet = EnumSet.allOf(ValidEnum.class);
		Iterator i$ = stateSet.iterator();

		while (i$.hasNext()) {
			ValidEnum s = (ValidEnum) i$.next();
			map.put(s.name(), s);
		}

	}
}