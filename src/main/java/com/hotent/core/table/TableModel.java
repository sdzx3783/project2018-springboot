package com.hotent.core.table;

import com.hotent.core.table.ColumnModel;
import com.hotent.core.util.AppConfigUtil;
import com.hotent.core.util.StringUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TableModel {
	public static final String PK_COLUMN_NAME = "ID";
	public static final String FK_COLUMN_NAME = "REFID";
	public static String CUSTOMER_COLUMN_PREFIX = "F_";
	public static String CUSTOMER_TABLE_PREFIX = "W_";
	public static final String CUSTOMER_INDEX_PREFIX = "IDX_";
	public static final String CUSTOMER_TABLE_HIS_SUFFIX = "_HISTORY";
	public static final String CUSTOMER_TABLE_COMM_PREFIX = "TT_";
	public static final String CUSTOMER_COLUMN_CURRENTUSERID = "curentUserId_";
	public static final String CUSTOMER_COLUMN_CURRENTORGID = "curentOrgId_";
	public static final String CUSTOMER_COLUMN_FLOWRUNID = "flowRunId_";
	public static final String CUSTOMER_COLUMN_DEFID = "defId_";
	public static final String CUSTOMER_COLUMN_CREATETIME = "CREATETIME";
	private String name = "";
	private String comment = "";
	private List<ColumnModel> columnList = new ArrayList();

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		if (StringUtil.isNotEmpty(this.comment)) {
			this.comment = this.comment.replace("\'", "\'\'");
		}

		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void addColumnModel(ColumnModel model) {
		this.columnList.add(model);
	}

	public List<ColumnModel> getColumnList() {
		return this.columnList;
	}

	public void setColumnList(List<ColumnModel> columnList) {
		this.columnList = columnList;
	}

	public List<ColumnModel> getPrimayKey() {
		ArrayList pks = new ArrayList();
		Iterator i$ = this.columnList.iterator();

		while (i$.hasNext()) {
			ColumnModel column = (ColumnModel) i$.next();
			if (column.getIsPk()) {
				pks.add(column);
			}
		}

		return pks;
	}

	public String toString() {
		return "TableModel [name=" + this.name + ", comment=" + this.comment + ", columnList=" + this.columnList + "]";
	}

	static {
		String customerTablePrefix = AppConfigUtil.get("CUSTOMER_TABLE_PREFIX");
		CUSTOMER_TABLE_PREFIX = StringUtil.isEmpty(customerTablePrefix) ? CUSTOMER_TABLE_PREFIX : customerTablePrefix;
		String customerColumnPrefix = AppConfigUtil.get("CUSTOMER_COLUMN_PREFIX");
		CUSTOMER_COLUMN_PREFIX = StringUtil.isEmpty(customerColumnPrefix)
				? CUSTOMER_COLUMN_PREFIX
				: customerColumnPrefix;
	}
}