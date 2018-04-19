package com.hotent.core.soap.type;

import com.hotent.core.soap.type.BeanSoapType;
import com.hotent.core.soap.type.DateSoapType;
import com.hotent.core.soap.type.ListSoapType;
import com.hotent.core.soap.type.NumberSoapType;
import com.hotent.core.soap.type.SoapType;
import com.hotent.core.soap.type.StringSoapType;
import java.util.Arrays;

public enum SoapTypes {
	string("字符串(string,java.lang.String)", new StringSoapType()), number(
			"数字(number,java.lang.Integer,java.lang.Long,java.math.BigDecimal)",
			new NumberSoapType()), date("时间/日期(date,java.util.Date,dateTime)", new DateSoapType()), bean(
					"复合类型(bean,serializable)", new BeanSoapType()), list("列表类型(List)", new ListSoapType());

	private SoapType soapType;
	private String name;

	private SoapTypes(String name, SoapType soapType) {
		this.name = name;
		this.soapType = soapType;
	}

	public SoapType getSoapType() {
		return this.soapType;
	}

	public String getName() {
		return this.name;
	}

	public String toString() {
		return this.name();
	}

	public static SoapType getTypeByBean(Class<?> klass) {
		if (klass == null) {
			return bean.getSoapType();
		} else {
			SoapTypes[] arr$ = values();
			int len$ = arr$.length;

			for (int i$ = 0; i$ < len$; ++i$) {
				SoapTypes types = arr$[i$];
				if (Arrays.asList(types.getSoapType().getBeanTypes()).contains(klass)) {
					return types.getSoapType();
				}
			}

			return bean.getSoapType();
		}
	}

	public static SoapType getTypeBySoap(String type) {
		if (type == null) {
			return bean.getSoapType();
		} else if (type.matches("List\\{\\w*\\}")) {
			return new ListSoapType();
		} else {
			SoapTypes[] arr$ = values();
			int len$ = arr$.length;

			for (int i$ = 0; i$ < len$; ++i$) {
				SoapTypes types = arr$[i$];
				if (Arrays.asList(types.getSoapType().getSoapTypes()).contains(type)) {
					return types.getSoapType();
				}
			}

			return bean.getSoapType();
		}
	}
}