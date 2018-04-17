package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.BaseElement;
import com.hotent.core.bpmn20.entity.Lane;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tLaneSet", propOrder = {"lane"})
public class LaneSet extends BaseElement {
	protected List<Lane> lane;
	@XmlAttribute
	protected String name;

	public List<Lane> getLane() {
		if (this.lane == null) {
			this.lane = new ArrayList();
		}

		return this.lane;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}
}