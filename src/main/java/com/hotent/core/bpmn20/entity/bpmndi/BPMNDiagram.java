package com.hotent.core.bpmn20.entity.bpmndi;

import com.hotent.core.bpmn20.entity.bpmndi.BPMNLabelStyle;
import com.hotent.core.bpmn20.entity.bpmndi.BPMNPlane;
import com.hotent.core.bpmn20.entity.omgdi.Diagram;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BPMNDiagram", propOrder = {"bpmnPlane", "bpmnLabelStyle"})
public class BPMNDiagram extends Diagram {
	@XmlElement(name = "BPMNPlane", required = true)
	protected BPMNPlane bpmnPlane;
	@XmlElement(name = "BPMNLabelStyle")
	protected List<BPMNLabelStyle> bpmnLabelStyle;

	public BPMNPlane getBPMNPlane() {
		return this.bpmnPlane;
	}

	public void setBPMNPlane(BPMNPlane value) {
		this.bpmnPlane = value;
	}

	public List<BPMNLabelStyle> getBPMNLabelStyle() {
		if (this.bpmnLabelStyle == null) {
			this.bpmnLabelStyle = new ArrayList();
		}

		return this.bpmnLabelStyle;
	}
}