package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Documentation;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tExtension", propOrder = {"documentation"})
public class Extension {
	protected List<Documentation> documentation;
	@XmlAttribute
	protected QName definition;
	@XmlAttribute
	protected Boolean mustUnderstand;

	public List<Documentation> getDocumentation() {
		if (this.documentation == null) {
			this.documentation = new ArrayList();
		}

		return this.documentation;
	}

	public QName getDefinition() {
		return this.definition;
	}

	public void setDefinition(QName value) {
		this.definition = value;
	}

	public boolean isMustUnderstand() {
		return this.mustUnderstand == null ? false : this.mustUnderstand.booleanValue();
	}

	public void setMustUnderstand(Boolean value) {
		this.mustUnderstand = value;
	}
}