package com.hotent.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tDocumentation", propOrder = {"content"})
public class Documentation {
	@XmlMixed
	@XmlAnyElement(lax = true)
	protected List<Object> content;
	@XmlAttribute
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;
	@XmlAttribute
	protected String textFormat;

	public List<Object> getContent() {
		if (this.content == null) {
			this.content = new ArrayList();
		}

		return this.content;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String value) {
		this.id = value;
	}

	public String getTextFormat() {
		return this.textFormat == null ? "text/plain" : this.textFormat;
	}

	public void setTextFormat(String value) {
		this.textFormat = value;
	}
}