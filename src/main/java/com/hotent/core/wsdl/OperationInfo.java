package com.hotent.core.wsdl;

import com.hotent.core.wsdl.ParameterInfo;
import java.util.ArrayList;
import java.util.List;

public class OperationInfo {
	private String operationName = null;
	private List<?> inputParams = new ArrayList();
	private ParameterInfo returnType = null;
	private List<?> outputParams = new ArrayList();
	private String inputAction = "api";

	public String getOperationName() {
		return this.operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public List<?> getInputParams() {
		return this.inputParams;
	}

	public void setInputParams(List<?> inputParams) {
		this.inputParams = inputParams;
	}

	public ParameterInfo getReturnType() {
		return this.returnType;
	}

	public void setReturnType(ParameterInfo returnType) {
		this.returnType = returnType;
	}

	public List<?> getOutputParams() {
		return this.outputParams;
	}

	public void setOutputParams(List<?> outputParams) {
		this.outputParams = outputParams;
	}

	public String getInputAction() {
		return this.inputAction;
	}

	public void setInputAction(String inputAction) {
		this.inputAction = inputAction;
	}
}