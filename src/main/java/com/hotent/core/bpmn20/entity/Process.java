package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Artifact;
import com.hotent.core.bpmn20.entity.Auditing;
import com.hotent.core.bpmn20.entity.CallableElement;
import com.hotent.core.bpmn20.entity.CorrelationSubscription;
import com.hotent.core.bpmn20.entity.FlowElement;
import com.hotent.core.bpmn20.entity.LaneSet;
import com.hotent.core.bpmn20.entity.Monitoring;
import com.hotent.core.bpmn20.entity.ProcessType;
import com.hotent.core.bpmn20.entity.Property;
import com.hotent.core.bpmn20.entity.ResourceRole;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tProcess", propOrder = {"auditing", "monitoring", "property", "laneSet", "flowElement", "artifact",
		"resourceRole", "correlationSubscription", "supports"})
public class Process extends CallableElement {
	protected Auditing auditing;
	protected Monitoring monitoring;
	protected List<Property> property;
	protected List<LaneSet> laneSet;
	@XmlElementRef(name = "flowElement", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
	protected List<JAXBElement<? extends FlowElement>> flowElement;
	@XmlElementRef(name = "artifact", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
	protected List<JAXBElement<? extends Artifact>> artifact;
	@XmlElementRef(name = "resourceRole", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
	protected List<JAXBElement<? extends ResourceRole>> resourceRole;
	protected List<CorrelationSubscription> correlationSubscription;
	protected List<QName> supports;
	@XmlAttribute
	protected ProcessType processType;
	@XmlAttribute
	protected Boolean isClosed;
	@XmlAttribute
	protected Boolean isExecutable;
	@XmlAttribute
	protected QName definitionalCollaborationRef;

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

	public List<Property> getProperty() {
		if (this.property == null) {
			this.property = new ArrayList();
		}

		return this.property;
	}

	public List<LaneSet> getLaneSet() {
		if (this.laneSet == null) {
			this.laneSet = new ArrayList();
		}

		return this.laneSet;
	}

	public List<JAXBElement<? extends FlowElement>> getFlowElement() {
		if (this.flowElement == null) {
			this.flowElement = new ArrayList();
		}

		return this.flowElement;
	}

	public List<JAXBElement<? extends Artifact>> getArtifact() {
		if (this.artifact == null) {
			this.artifact = new ArrayList();
		}

		return this.artifact;
	}

	public List<JAXBElement<? extends ResourceRole>> getResourceRole() {
		if (this.resourceRole == null) {
			this.resourceRole = new ArrayList();
		}

		return this.resourceRole;
	}

	public List<CorrelationSubscription> getCorrelationSubscription() {
		if (this.correlationSubscription == null) {
			this.correlationSubscription = new ArrayList();
		}

		return this.correlationSubscription;
	}

	public List<QName> getSupports() {
		if (this.supports == null) {
			this.supports = new ArrayList();
		}

		return this.supports;
	}

	public ProcessType getProcessType() {
		return this.processType == null ? ProcessType.NONE : this.processType;
	}

	public void setProcessType(ProcessType value) {
		this.processType = value;
	}

	public boolean isIsClosed() {
		return this.isClosed == null ? false : this.isClosed.booleanValue();
	}

	public void setIsClosed(Boolean value) {
		this.isClosed = value;
	}

	public Boolean isIsExecutable() {
		return this.isExecutable;
	}

	public void setIsExecutable(Boolean value) {
		this.isExecutable = value;
	}

	public QName getDefinitionalCollaborationRef() {
		return this.definitionalCollaborationRef;
	}

	public void setDefinitionalCollaborationRef(QName value) {
		this.definitionalCollaborationRef = value;
	}
}