package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.DataState;
import com.hotent.core.bpmn20.entity.RootElement;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tDataStore", propOrder = {"dataState"})
public class DataStore extends RootElement {
	protected DataState dataState;
	@XmlAttribute
	protected String name;
	@XmlAttribute
	protected BigInteger capacity;
	@XmlAttribute
	protected Boolean isUnlimited;
	@XmlAttribute
	protected QName itemSubjectRef;

	public DataState getDataState() {
		return this.dataState;
	}

	public void setDataState(DataState value) {
		this.dataState = value;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public BigInteger getCapacity() {
		return this.capacity;
	}

	public void setCapacity(BigInteger value) {
		this.capacity = value;
	}

	public boolean isIsUnlimited() {
		return this.isUnlimited == null ? true : this.isUnlimited.booleanValue();
	}

	public void setIsUnlimited(Boolean value) {
		this.isUnlimited = value;
	}

	public QName getItemSubjectRef() {
		return this.itemSubjectRef;
	}

	public void setItemSubjectRef(QName value) {
		this.itemSubjectRef = value;
	}
}