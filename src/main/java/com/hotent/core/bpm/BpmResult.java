package com.hotent.core.bpm;

import com.hotent.core.bpm.DataType;
import java.util.HashMap;
import java.util.Map;

public class BpmResult {
	private String businessKey = "";
	private String tableName = "";
	private DataType dataType;
	private Map<String, Object> vars;

	public BpmResult() {
		this.dataType = DataType.NUMBER;
		this.vars = new HashMap();
	}

	public String getBusinessKey() {
		return this.businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public DataType getDataType() {
		return this.dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public Map<String, Object> getVars() {
		return this.vars;
	}

	public void setVars(Map<String, Object> vars) {
		this.vars = vars;
	}

	public void addVariable(String name, Object value) {
		this.vars.put(name, value);
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}