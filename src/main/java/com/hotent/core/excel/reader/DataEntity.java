package com.hotent.core.excel.reader;

import com.hotent.core.excel.reader.FieldEntity;
import java.util.List;

public class DataEntity {
	private String pkName;
	private String pkVal;
	private List<FieldEntity> fieldEntityList;

	public String getPkName() {
		return this.pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public String getPkVal() {
		return this.pkVal;
	}

	public void setPkVal(String pkVal) {
		this.pkVal = pkVal;
	}

	public List<FieldEntity> getFieldEntityList() {
		return this.fieldEntityList;
	}

	public void setFieldEntityList(List<FieldEntity> fieldEntityList) {
		this.fieldEntityList = fieldEntityList;
	}
}