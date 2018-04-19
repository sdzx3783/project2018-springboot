package com.hotent.core.db.helper;

public class ColumnModel {
	private boolean pk = false;
	private String propery = "";
	private String columnName = "";
	private boolean canUpd = true;

	public boolean getPk() {
		return this.pk;
	}

	public void setPk(boolean pk) {
		this.pk = pk;
	}

	public String getPropery() {
		return this.propery;
	}

	public void setPropery(String propery) {
		this.propery = propery;
	}

	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public boolean getCanUpd() {
		return this.canUpd;
	}

	public void setCanUpd(boolean canUpd) {
		this.canUpd = canUpd;
	}
}