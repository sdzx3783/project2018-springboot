package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Rendering;
import com.hotent.core.bpmn20.entity.Task;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tUserTask", propOrder = {"rendering"})
public class UserTask extends Task {
	protected List<Rendering> rendering;
	@XmlAttribute
	protected String implementation;

	public List<Rendering> getRendering() {
		if (this.rendering == null) {
			this.rendering = new ArrayList();
		}

		return this.rendering;
	}

	public String getImplementation() {
		return this.implementation == null ? "##unspecified" : this.implementation;
	}

	public void setImplementation(String value) {
		this.implementation = value;
	}
}