package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Auditing;
import com.hotent.core.bpmn20.entity.BaseElement;
import com.hotent.core.bpmn20.entity.DataObject;
import com.hotent.core.bpmn20.entity.DataObjectReference;
import com.hotent.core.bpmn20.entity.DataStoreReference;
import com.hotent.core.bpmn20.entity.FlowNode;
import com.hotent.core.bpmn20.entity.Monitoring;
import com.hotent.core.bpmn20.entity.SequenceFlow;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tFlowElement", propOrder = {"auditing", "monitoring", "categoryValueRef"})
@XmlSeeAlso({SequenceFlow.class, DataObject.class, DataObjectReference.class, DataStoreReference.class, FlowNode.class})
public abstract class FlowElement extends BaseElement {
	protected Auditing auditing;
	protected Monitoring monitoring;
	protected List<QName> categoryValueRef;
	@XmlAttribute
	protected String name;

	public Auditing getAuditing() {
		return this.auditing;
	}

	public void setAuditing(Auditing value) {
		this.auditing = value;
	}

	public Monitoring getMonitoring() {
		return this.monitoring;
	}

	public void setMonitoring(Monitoring value) {
		this.monitoring = value;
	}

	public List<QName> getCategoryValueRef() {
		if (this.categoryValueRef == null) {
			this.categoryValueRef = new ArrayList();
		}

		return this.categoryValueRef;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}
}