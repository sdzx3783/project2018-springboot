package com.hotent.core.bpmn20.entity.activiti;

import com.hotent.core.bpmn20.entity.activiti.FormProperty.Value;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"value"})
@XmlRootElement(name = "formProperty")
public class FormProperty {
	protected List<Value> value;
	@XmlAttribute(required = true)
	protected String id;
	@XmlAttribute
	protected String name;
	@XmlAttribute
	protected String type;
	@XmlAttribute
	protected String readable;
	@XmlAttribute
	protected String writable;
	@XmlAttribute
	protected String required;
	@XmlAttribute
	protected String variable;
	@XmlAttribute
	protected String expression;
	@XmlAttribute
	protected String datePattern;
	@XmlAttribute(name = "value")
	protected String attrValue;

	public List<Value> getValue() {
		if (this.value == null) {
			this.value = new ArrayList();
		}

		return this.value;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String value) {
		this.id = value;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String value) {
		this.type = value;
	}

	public String getReadable() {
		return this.readable;
	}

	public void setReadable(String value) {
		this.readable = value;
	}

	public String getWritable() {
		return this.writable;
	}

	public void setWritable(String value) {
		this.writable = value;
	}

	public String getRequired() {
		return this.required;
	}

	public void setRequired(String value) {
		this.required = value;
	}

	public String getVariable() {
		return this.variable;
	}

	public void setVariable(String value) {
		this.variable = value;
	}

	public String getExpression() {
		return this.expression;
	}

	public void setExpression(String value) {
		this.expression = value;
	}

	public String getDatePattern() {
		return this.datePattern;
	}

	public void setDatePattern(String value) {
		this.datePattern = value;
	}

	public String getAttrValue() {
		return this.attrValue;
	}

	public void setAttrValue(String value) {
		this.attrValue = value;
	}
}