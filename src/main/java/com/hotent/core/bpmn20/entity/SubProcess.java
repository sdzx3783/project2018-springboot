package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Activity;
import com.hotent.core.bpmn20.entity.AdHocSubProcess;
import com.hotent.core.bpmn20.entity.Artifact;
import com.hotent.core.bpmn20.entity.FlowElement;
import com.hotent.core.bpmn20.entity.LaneSet;
import com.hotent.core.bpmn20.entity.Transaction;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tSubProcess", propOrder = {"laneSet", "flowElement", "artifact"})
@XmlSeeAlso({AdHocSubProcess.class, Transaction.class})
public class SubProcess extends Activity {
	protected List<LaneSet> laneSet;
	@XmlElementRef(name = "flowElement", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
	protected List<JAXBElement<? extends FlowElement>> flowElement;
	@XmlElementRef(name = "artifact", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
	protected List<JAXBElement<? extends Artifact>> artifact;
	@XmlAttribute
	protected Boolean triggeredByEvent;

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

	public boolean isTriggeredByEvent() {
		return this.triggeredByEvent == null ? false : this.triggeredByEvent.booleanValue();
	}

	public void setTriggeredByEvent(Boolean value) {
		this.triggeredByEvent = value;
	}
}