package com.hotent.core.bpmn20.entity.bpmndi;

import com.hotent.core.bpmn20.entity.omgdc.Font;
import com.hotent.core.bpmn20.entity.omgdi.Style;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BPMNLabelStyle", propOrder = {"font"})
public class BPMNLabelStyle extends Style {
	@XmlElement(name = "Font", namespace = "http://www.omg.org/spec/DD/20100524/DC", required = true)
	protected Font font;

	public Font getFont() {
		return this.font;
	}

	public void setFont(Font value) {
		this.font = value;
	}
}