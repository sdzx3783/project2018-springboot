package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.ItemKind;
import com.hotent.core.bpmn20.entity.RootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tItemDefinition")
public class ItemDefinition extends RootElement {
	@XmlAttribute
	protected QName structureRef;
	@XmlAttribute
	protected Boolean isCollection;
	@XmlAttribute
	protected ItemKind itemKind;

	public QName getStructureRef() {
		return this.structureRef;
	}

	public void setStructureRef(QName value) {
		this.structureRef = value;
	}

	public boolean isIsCollection() {
		return this.isCollection == null ? false : this.isCollection.booleanValue();
	}

	public void setIsCollection(Boolean value) {
		this.isCollection = value;
	}

	public ItemKind getItemKind() {
		return this.itemKind == null ? ItemKind.INFORMATION : this.itemKind;
	}

	public void setItemKind(ItemKind value) {
		this.itemKind = value;
	}
}