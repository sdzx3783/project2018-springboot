package com.hotent.core.web.query.entity;

import com.hotent.core.web.query.entity.FieldTable;

public class FieldSort extends FieldTable {
	private String orderBy = "ASC";

	public FieldSort() {
	}

	public FieldSort(String tableName, String fieldName, String orderBy) {
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.orderBy = orderBy;
	}

	public String getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}