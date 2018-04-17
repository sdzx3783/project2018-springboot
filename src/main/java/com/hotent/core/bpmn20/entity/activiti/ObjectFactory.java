package com.hotent.core.bpmn20.entity.activiti;

import com.hotent.core.bpmn20.entity.activiti.ExecutionListener;
import com.hotent.core.bpmn20.entity.activiti.Field;
import com.hotent.core.bpmn20.entity.activiti.FormProperty;
import com.hotent.core.bpmn20.entity.activiti.In;
import com.hotent.core.bpmn20.entity.activiti.Out;
import com.hotent.core.bpmn20.entity.activiti.PotentialStarter;
import com.hotent.core.bpmn20.entity.activiti.TaskListener;
import com.hotent.core.bpmn20.entity.activiti.FormProperty.Value;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
	private static final QName _PotentialStarter_QNAME = new QName("http://activiti.org/bpmn", "potentialStarter");

	public Out createOut() {
		return new Out();
	}

	public Field createField() {
		return new Field();
	}

	public Value createFormPropertyValue() {
		return new Value();
	}

	public ExecutionListener createExecutionListener() {
		return new ExecutionListener();
	}

	public FormProperty createFormProperty() {
		return new FormProperty();
	}

	public PotentialStarter createPotentialStarter() {
		return new PotentialStarter();
	}

	public In createIn() {
		return new In();
	}

	public TaskListener createTaskListener() {
		return new TaskListener();
	}

	@XmlElementDecl(namespace = "http://activiti.org/bpmn", name = "potentialStarter")
	public JAXBElement<String> createPotentialStarter(String value) {
		return new JAXBElement(_PotentialStarter_QNAME, String.class, (Class) null, value);
	}
}