package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.GlobalTask;
import com.hotent.core.bpmn20.entity.InputOutputBinding;
import com.hotent.core.bpmn20.entity.InputOutputSpecification;
import com.hotent.core.bpmn20.entity.Process;
import com.hotent.core.bpmn20.entity.RootElement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCallableElement", propOrder = {"supportedInterfaceRef", "ioSpecification", "ioBinding"})
@XmlSeeAlso({Process.class, GlobalTask.class})
public class CallableElement extends RootElement {
	protected List<QName> supportedInterfaceRef;
	protected InputOutputSpecification ioSpecification;
	protected List<InputOutputBinding> ioBinding;
	@XmlAttribute
	protected String name;

	public List<QName> getSupportedInterfaceRef() {
		if (this.supportedInterfaceRef == null) {
			this.supportedInterfaceRef = new ArrayList();
		}

		return this.supportedInterfaceRef;
	}

	public InputOutputSpecification getIoSpecification() {
		return this.ioSpecification;
	}

	public void setIoSpecification(InputOutputSpecification value) {
		this.ioSpecification = value;
	}

	public List<InputOutputBinding> getIoBinding() {
		if (this.ioBinding == null) {
			this.ioBinding = new ArrayList();
		}

		return this.ioBinding;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}
}