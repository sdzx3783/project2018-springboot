package com.hotent.core.bpm;

public enum DataType {
	STRING("string", "字符串"), NUMBER("number", "数字"), DATE("date", "日期");

	private String key = "";
	private String value = "";

	private DataType(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return this.key;
	}

	public static DataType fromKey(String key) {
		DataType[] arr$ = values();
		int len$ = arr$.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			DataType c = arr$[i$];
			if (c.getKey().equalsIgnoreCase(key)) {
				return c;
			}
		}

		throw new IllegalArgumentException(key);
	}
}