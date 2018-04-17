package com.hotent.core.datahandler;

import com.hotent.core.util.StringUtil;

public class DataModel {
	private String pk = "";
	private String tableName = "";
	private String action = "";
	public static String ACTION_UPDATE = "upd";
	public static String ACTION_DELETE = "del";

	public DataModel() {
	}

	public DataModel(String pk, String tableName, String action) {
		this.pk = pk;
		this.tableName = tableName;
		this.action = action;
	}

	public String getPk() {
		return this.pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getAction() {
		return StringUtil.isEmpty(this.action) ? ACTION_UPDATE : this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}