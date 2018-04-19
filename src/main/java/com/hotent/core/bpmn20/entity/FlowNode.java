package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Activity;
import com.hotent.core.bpmn20.entity.ChoreographyActivity;
import com.hotent.core.bpmn20.entity.Event;
import com.hotent.core.bpmn20.entity.FlowElement;
import com.hotent.core.bpmn20.entity.Gateway;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tFlowNode", propOrder = {"incoming", "outgoing"})
@XmlSeeAlso({Event.class, ChoreographyActivity.class, Gateway.class, Activity.class})
public abstract class FlowNode extends FlowElement {
	protected List<QName> incoming;
	protected List<QName> outgoing;

	public List<QName> getIncoming() {
		if (this.incoming == null) {
			this.incoming = new ArrayList();
		}

		return this.incoming;
	}

	public List<QName> getOutgoing() {
		if (this.outgoing == null) {
			this.outgoing = new ArrayList();
		}

		return this.outgoing;
	}
}