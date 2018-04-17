package com.hotent.core.wsdl;

import com.hotent.core.wsdl.OperationInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.ws.commons.schema.XmlSchemaType;

public class ServiceInfo {
	private String name;
	private String wsdlUrl;
	private String httpAddress;
	private Map<String, OperationInfo> operations = new TreeMap();
	private List<XmlSchemaType> complexTypes = new ArrayList();
	private String targetNamespace;

	public String getTargetNamespace() {
		return this.targetNamespace;
	}

	public void setTargetNamespace(String targetNamespace) {
		this.targetNamespace = targetNamespace;
	}

	public void addComplexTypes(XmlSchemaType complexType) {
		this.complexTypes.add(complexType);
	}

	public List<XmlSchemaType> getComplexTypes() {
		return this.complexTypes;
	}

	public void setComplexTypes(List<XmlSchemaType> complexTypes) {
		this.complexTypes = complexTypes;
	}

	public String getWsdlUrl() {
		return this.wsdlUrl;
	}

	public void setWsdlUrl(String wsdlUrl) {
		this.wsdlUrl = wsdlUrl;
	}

	public String toString() {
		return this.getName();
	}

	public Map<String, OperationInfo> getOperations() {
		return this.operations;
	}

	public void setOperations(Map<String, OperationInfo> operations) {
		this.operations = operations;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHttpAddress() {
		return this.httpAddress;
	}

	public void setHttpAddress(String httpAddress) {
		this.httpAddress = httpAddress;
	}
}