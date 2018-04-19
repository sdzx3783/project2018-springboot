package com.hotent.core.valid;

import com.hotent.core.util.DateUtil;
import com.hotent.core.util.StringUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.springframework.validation.Errors;
import org.springmodules.validation.commons.FieldChecks;

public class CustomFieldChecks extends FieldChecks {
	public static boolean validateEqual(Object bean, ValidatorAction va, Field field, Errors errors) {
		String value = extractValue(bean, field);
		String sProperty2 = field.getVarValue("equalTo");
		String value2 = ValidatorUtils.getValueAsString(bean, sProperty2);
		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!value.equals(value2)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception arg7) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}

		return true;
	}

	public static boolean validateDateTime(Object bean, ValidatorAction va, Field field, Errors errors) {
		return false;
	}

	public static boolean validateRegx(Object bean, ValidatorAction va, Field field, Errors errors) {
		String mask = field.getVarValue("regex");
		String value = extractValue(bean, field);

		try {
			if (!GenericValidator.isBlankOrNull(value) && !StringUtil.validByRegex(mask, value)) {
				rejectValue(errors, field, va);
				return false;
			} else {
				return true;
			}
		} catch (Exception arg6) {
			FieldChecks.rejectValue(errors, field, va);
			return false;
		}
	}

	public static boolean validateIsNumber(Object bean, ValidatorAction va, Field field, Errors errors) {
		String value = extractValue(bean, field);
		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!StringUtil.isNumberic(value)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception arg5) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}

		return true;
	}

	public static boolean validateIsDigits(Object bean, ValidatorAction va, Field field, Errors errors) {
		String value = extractValue(bean, field);
		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!StringUtil.isInteger(value)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception arg5) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}

		return true;
	}

	public static boolean validateEmail(Object bean, ValidatorAction va, Field field, Errors errors) {
		String value = extractValue(bean, field);
		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!StringUtil.isEmail(value)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception arg5) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}

		return true;
	}

	public static boolean validateMax(Object bean, ValidatorAction va, Field field, Errors errors) {
		String value = extractValue(bean, field);
		String max = field.getVarValue("max");
		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				long e = Long.parseLong(max);
				long lValue = Long.parseLong(value);
				if (lValue >= e) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception arg9) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}

		return true;
	}

	public static boolean validateMin(Object bean, ValidatorAction va, Field field, Errors errors) {
		String value = extractValue(bean, field);
		String min = field.getVarValue("min");
		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				long e = Long.parseLong(min);
				long lValue = Long.parseLong(value);
				if (lValue <= e) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception arg9) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}

		return true;
	}

	public static boolean validateRangelength(Object bean, ValidatorAction va, Field field, Errors errors) {
		String value = extractValue(bean, field);
		int len = value.length();
		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				int e = Integer.parseInt(field.getVarValue("minlength"));
				int maxlength = Integer.parseInt(field.getVarValue("maxlength"));
				if (len < e || len > maxlength) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception arg7) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}

		return true;
	}

	public static boolean validateUrl(Object bean, ValidatorAction va, Field field, Errors errors) {
		String value = extractValue(bean, field);
		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!StringUtil.isUrl(value)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception arg5) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}

		return true;
	}

	public static boolean validateMobile(Object bean, ValidatorAction va, Field field, Errors errors) {
		String value = extractValue(bean, field);
		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!StringUtil.isMobile(value)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception arg5) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}

		return true;
	}

	public static boolean validatePhone(Object bean, ValidatorAction va, Field field, Errors errors) {
		String value = extractValue(bean, field);
		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!StringUtil.isPhone(value)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception arg5) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}

		return true;
	}

	public static boolean validateZip(Object bean, ValidatorAction va, Field field, Errors errors) {
		String value = extractValue(bean, field);
		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!StringUtil.isZip(value)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception arg5) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}

		return true;
	}

	public static boolean validateQq(Object bean, ValidatorAction va, Field field, Errors errors) {
		String value = extractValue(bean, field);
		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!StringUtil.isQq(value)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception arg5) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}

		return true;
	}

	public static boolean validateIp(Object bean, ValidatorAction va, Field field, Errors errors) {
		String value = extractValue(bean, field);
		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!StringUtil.isIp(value)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception arg5) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}

		return true;
	}

	public static boolean validateChinese(Object bean, ValidatorAction va, Field field, Errors errors) {
		String value = extractValue(bean, field);
		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!StringUtil.isChinese(value)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception arg5) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}

		return true;
	}

	public static boolean validateChrnum(Object bean, ValidatorAction va, Field field, Errors errors) {
		String value = extractValue(bean, field);
		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!StringUtil.isChrNum(value)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception arg5) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}

		return true;
	}

	public static boolean compStartEndTime(Object bean, ValidatorAction va, Field field, Errors errors) {
		String sTimevalue = extractValue(bean, field);
		String sProperty2 = field.getVarValue("compStartEndTime");
		String eTimevalue = ValidatorUtils.getValueAsString(bean, sProperty2);

		try {
			SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd");
			Date sTime = null;
			Date eTime = null;
			if (sProperty2.toLowerCase().indexOf("end") != -1) {
				sTime = e.parse(DateUtil.timeStrToDateStr(sTimevalue));
				eTime = e.parse(DateUtil.timeStrToDateStr(eTimevalue));
			} else {
				sTime = e.parse(DateUtil.timeStrToDateStr(eTimevalue));
				eTime = e.parse(DateUtil.timeStrToDateStr(sTimevalue));
			}

			if (!sTime.before(eTime)) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			} else {
				return true;
			}
		} catch (Exception arg9) {
			FieldChecks.rejectValue(errors, field, va);
			return false;
		}
	}

	public static boolean digitsSum(Object bean, ValidatorAction va, Field field, Errors errors) {
		int sum = 0;
		int valLimit = 0;

		try {
			Map e = field.getVars();
			valLimit = Integer.parseInt(e.toString().split("  ")[1].split("=")[1]);
			String key = field.getKey();
			String methodName = "get" + key.substring(0, 1).toUpperCase() + key.substring(1, key.length());
			Method method = bean.getClass().getMethod(methodName, new Class[0]);
			String value = String.valueOf(method.invoke(bean, new Object[0]));
			String[] arrVal = value.split("[,]");
			String[] arr$ = arrVal;
			int len$ = arrVal.length;

			for (int i$ = 0; i$ < len$; ++i$) {
				String val = arr$[i$];
				sum += Integer.parseInt(val);
			}
		} catch (SecurityException arg15) {
			arg15.printStackTrace();
		} catch (IllegalArgumentException arg16) {
			arg16.printStackTrace();
		} catch (NoSuchMethodException arg17) {
			arg17.printStackTrace();
		} catch (IllegalAccessException arg18) {
			arg18.printStackTrace();
		} catch (InvocationTargetException arg19) {
			arg19.printStackTrace();
		}

		return sum <= valLimit;
	}
}