package com.hotent.core.db.entity;

public class SQLClause {
	public static short VALUEFROM_INPUT = 1;
	public static short VALUEFROM_FIX = 2;
	public static short VALUEFROM_SCRIPT = 3;
	public static short VALUEFROM_FORM = 4;
	private String name;
	private String qualifiedName;
	private String label;
	private String comment;
	private String type;
	private String joinType;
	private String operate;
	private Object value;
	private int valueFrom;
	private String controlType;
	private String queryType;
	private String datefmt;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQualifiedName() {
		return this.qualifiedName;
	}

	public void setQualifiedName(String qualifiedName) {
		this.qualifiedName = qualifiedName;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getJoinType() {
		return this.joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	public String getOperate() {
		return this.operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public int getValueFrom() {
		return this.valueFrom;
	}

	public void setValueFrom(int valueFrom) {
		this.valueFrom = valueFrom;
	}

	public String getControlType() {
		return this.controlType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public String getQueryType() {
		return this.queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getDatefmt() {
		return this.datefmt;
	}

	public void setDatefmt(String datefmt) {
		this.datefmt = datefmt;
	}
}