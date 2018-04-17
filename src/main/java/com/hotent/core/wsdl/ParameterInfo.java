package com.hotent.core.wsdl;

import java.util.HashMap;
import java.util.Map;

public class ParameterInfo {
	public static final Short COMPLEX_YES = Short.valueOf(1);
	public static final Short COMPLEX_NO = Short.valueOf(0);
	public static final Short SHOW_YES = Short.valueOf(0);
	public static final Short SHOW_NO = Short.valueOf(1);
	private String name;
	private String type;
	private Short isComplext = Short.valueOf(0);
	private Short isShow = Short.valueOf(0);
	private String deep;
	private Boolean isList = Boolean.valueOf(false);
	private Map<String, ParameterInfo> complextParams = new HashMap();
	private Map<String, String> parentComplext = new HashMap();

	public Short getIsComplext() {
		return this.isComplext;
	}

	public void setIsComplext(Short isComplext) {
		this.isComplext = isComplext;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, ParameterInfo> getComplextParams() {
		return this.complextParams;
	}

	public void setComplextParams(Map<String, ParameterInfo> complextParams) {
		this.complextParams = complextParams;
	}

	public Map<String, String> getParentComplext() {
		return this.parentComplext;
	}

	public void setParentComplext(Map<String, String> parentComplext) {
		this.parentComplext = parentComplext;
	}

	public Short getIsShow() {
		return this.isShow;
	}

	public void setIsShow(Short isShow) {
		this.isShow = isShow;
	}

	public String getDeep() {
		return this.deep;
	}

	public void setDeep(String deep) {
		this.deep = deep;
	}

	public Boolean isList() {
		return this.isList;
	}

	public void setIsList(Boolean isList) {
		this.isList = isList;
	}
}