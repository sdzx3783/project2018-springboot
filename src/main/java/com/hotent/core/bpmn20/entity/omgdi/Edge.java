package com.hotent.core.bpmn20.entity.omgdi;

import com.hotent.core.bpmn20.entity.omgdc.Point;
import com.hotent.core.bpmn20.entity.omgdi.DiagramElement;
import com.hotent.core.bpmn20.entity.omgdi.LabeledEdge;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Edge", propOrder = {"waypoint"})
@XmlSeeAlso({LabeledEdge.class})
public abstract class Edge extends DiagramElement {
	@XmlElement(required = true)
	protected List<Point> waypoint;

	public List<Point> getWaypoint() {
		if (this.waypoint == null) {
			this.waypoint = new ArrayList();
		}

		return this.waypoint;
	}
}