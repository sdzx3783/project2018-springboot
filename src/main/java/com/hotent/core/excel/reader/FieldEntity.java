package com.hotent.core.excel.reader;

public class FieldEntity {
	public static final Short IS_KEY = Short.valueOf(1);
	public static final Short NOT_KEY = Short.valueOf(0);
	private String name;
	private String value;
	private Short isKey;

	public FieldEntity() {
		this.isKey = NOT_KEY;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Short getIsKey() {
		return this.isKey;
	}

	public void setIsKey(Short isKey) {
		this.isKey = isKey;
	}
}