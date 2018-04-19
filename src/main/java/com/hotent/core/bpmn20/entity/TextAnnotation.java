package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Artifact;
import com.hotent.core.bpmn20.entity.Text;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tTextAnnotation", propOrder = {"text"})
public class TextAnnotation extends Artifact {
	protected Text text;
	@XmlAttribute
	protected String textFormat;

	public Text getText() {
		return this.text;
	}

	public void setText(Text value) {
		this.text = value;
	}

	public String getTextFormat() {
		return this.textFormat == null ? "text/plain" : this.textFormat;
	}

	public void setTextFormat(String value) {
		this.textFormat = value;
	}
}