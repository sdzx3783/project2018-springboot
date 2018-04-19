package com.hotent.core.bpmn20.entity.bpmndi;

import com.hotent.core.bpmn20.entity.bpmndi.BPMNLabel;
import com.hotent.core.bpmn20.entity.bpmndi.MessageVisibleKind;
import com.hotent.core.bpmn20.entity.omgdi.LabeledEdge;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BPMNEdge", propOrder = {"bpmnLabel"})
public class BPMNEdge extends LabeledEdge {
	@XmlElement(name = "BPMNLabel")
	protected BPMNLabel bpmnLabel;
	@XmlAttribute
	protected QName bpmnElement;
	@XmlAttribute
	protected QName sourceElement;
	@XmlAttribute
	protected QName targetElement;
	@XmlAttribute
	protected MessageVisibleKind messageVisibleKind;

	public BPMNLabel getBPMNLabel() {
		return this.bpmnLabel;
	}

	public void setBPMNLabel(BPMNLabel value) {
		this.bpmnLabel = value;
	}

	public QName getBpmnElement() {
		return this.bpmnElement;
	}

	public void setBpmnElement(QName value) {
		this.bpmnElement = value;
	}

	public QName getSourceElement() {
		return this.sourceElement;
	}

	public void setSourceElement(QName value) {
		this.sourceElement = value;
	}

	public QName getTargetElement() {
		return this.targetElement;
	}

	public void setTargetElement(QName value) {
		this.targetElement = value;
	}

	public MessageVisibleKind getMessageVisibleKind() {
		return this.messageVisibleKind;
	}

	public void setMessageVisibleKind(MessageVisibleKind value) {
		this.messageVisibleKind = value;
	}
}