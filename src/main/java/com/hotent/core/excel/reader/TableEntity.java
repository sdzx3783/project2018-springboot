package com.hotent.core.excel.reader;

import com.hotent.core.excel.reader.DataEntity;
import java.util.List;

public class TableEntity {
	public static final Short IS_MAIN = Short.valueOf(1);
	public static final Short NOT_MAIN = Short.valueOf(0);
	private String name;
	private Short isMain;
	private List<DataEntity> dataEntityList;
	private List<TableEntity> subTableEntityList;

	public TableEntity() {
		this.isMain = IS_MAIN;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getIsMain() {
		return this.isMain;
	}

	public void setIsMain(Short isMain) {
		this.isMain = isMain;
	}

	public List<DataEntity> getDataEntityList() {
		return this.dataEntityList;
	}

	public void setDataEntityList(List<DataEntity> dataEntityList) {
		this.dataEntityList = dataEntityList;
	}

	public List<TableEntity> getSubTableEntityList() {
		return this.subTableEntityList;
	}

	public void setSubTableEntityList(List<TableEntity> subTableEntityList) {
		this.subTableEntityList = subTableEntityList;
	}
}