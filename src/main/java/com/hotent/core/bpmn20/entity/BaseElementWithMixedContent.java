package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Expression;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tBaseElementWithMixedContent", propOrder = {"content"})
@XmlSeeAlso({Expression.class})
public abstract class BaseElementWithMixedContent {
	@XmlElementRefs({
			@XmlElementRef(name = "documentation", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class),
			@XmlElementRef(name = "extensionElements", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)})
	@XmlMixed
	protected List<Serializable> content;
	@XmlAttribute
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;
	@XmlAnyAttribute
	private Map<QName, String> otherAttributes = new HashMap();

	public List<Serializable> getContent() {
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

	public Map<QName, String> getOtherAttributes() {
		return this.otherAttributes;
	}
}