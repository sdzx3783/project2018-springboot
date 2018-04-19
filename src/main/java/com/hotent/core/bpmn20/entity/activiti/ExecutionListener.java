package com.hotent.core.bpmn20.entity.activiti;

import com.hotent.core.bpmn20.entity.activiti.Field;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"field"})
@XmlRootElement(name = "executionListener")
public class ExecutionListener {
	protected List<Field> field;
	@XmlAttribute(name = "class")
	protected String clazz;
	@XmlAttribute
	protected String expression;
	@XmlAttribute
	protected String delegateExpression;
	@XmlAttribute
	protected String event;

	public List<Field> getField() {
		if (this.field == null) {
			this.field = new ArrayList();
		}

		return this.field;
	}

	public String getClazz() {
		return this.clazz;
	}

	public void setClazz(String value) {
		this.clazz = value;
	}

	public String getExpression() {
		return this.expression;
	}

	public void setExpression(String value) {
		this.expression = value;
	}

	public String getDelegateExpression() {
		return this.delegateExpression;
	}

	public void setDelegateExpression(String value) {
		this.delegateExpression = value;
	}

	public String getEvent() {
		return this.event;
	}

	public void setEvent(String value) {
		this.event = value;
	}
}