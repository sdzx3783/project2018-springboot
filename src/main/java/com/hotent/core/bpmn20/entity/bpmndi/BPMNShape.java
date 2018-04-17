package com.hotent.core.bpmn20.entity.bpmndi;

import com.hotent.core.bpmn20.entity.bpmndi.BPMNLabel;
import com.hotent.core.bpmn20.entity.bpmndi.ParticipantBandKind;
import com.hotent.core.bpmn20.entity.omgdi.LabeledShape;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BPMNShape", propOrder = {"bpmnLabel"})
public class BPMNShape extends LabeledShape {
	@XmlElement(name = "BPMNLabel")
	protected BPMNLabel bpmnLabel;
	@XmlAttribute
	protected QName bpmnElement;
	@XmlAttribute
	protected Boolean isHorizontal;
	@XmlAttribute
	protected Boolean isExpanded;
	@XmlAttribute
	protected Boolean isMarkerVisible;
	@XmlAttribute
	protected Boolean isMessageVisible;
	@XmlAttribute
	protected ParticipantBandKind participantBandKind;
	@XmlAttribute
	protected QName choreographyActivityShape;

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

	public Boolean isIsHorizontal() {
		return this.isHorizontal;
	}

	public void setIsHorizontal(Boolean value) {
		this.isHorizontal = value;
	}

	public Boolean isIsExpanded() {
		return this.isExpanded;
	}

	public void setIsExpanded(Boolean value) {
		this.isExpanded = value;
	}

	public Boolean isIsMarkerVisible() {
		return this.isMarkerVisible;
	}

	public void setIsMarkerVisible(Boolean value) {
		this.isMarkerVisible = value;
	}

	public Boolean isIsMessageVisible() {
		return this.isMessageVisible;
	}

	public void setIsMessageVisible(Boolean value) {
		this.isMessageVisible = value;
	}

	public ParticipantBandKind getParticipantBandKind() {
		return this.participantBandKind;
	}

	public void setParticipantBandKind(ParticipantBandKind value) {
		this.participantBandKind = value;
	}

	public QName getChoreographyActivityShape() {
		return this.choreographyActivityShape;
	}

	public void setChoreographyActivityShape(QName value) {
		this.choreographyActivityShape = value;
	}
}