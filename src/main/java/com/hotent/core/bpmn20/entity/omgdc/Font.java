package com.hotent.core.bpmn20.entity.omgdc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Font")
public class Font {
	@XmlAttribute
	protected String name;
	@XmlAttribute
	protected Double size;
	@XmlAttribute
	protected Boolean isBold;
	@XmlAttribute
	protected Boolean isItalic;
	@XmlAttribute
	protected Boolean isUnderline;
	@XmlAttribute
	protected Boolean isStrikeThrough;

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public Double getSize() {
		return this.size;
	}

	public void setSize(Double value) {
		this.size = value;
	}

	public Boolean isIsBold() {
		return this.isBold;
	}

	public void setIsBold(Boolean value) {
		this.isBold = value;
	}

	public Boolean isIsItalic() {
		return this.isItalic;
	}

	public void setIsItalic(Boolean value) {
		this.isItalic = value;
	}

	public Boolean isIsUnderline() {
		return this.isUnderline;
	}

	public void setIsUnderline(Boolean value) {
		this.isUnderline = value;
	}

	public Boolean isIsStrikeThrough() {
		return this.isStrikeThrough;
	}

	public void setIsStrikeThrough(Boolean value) {
		this.isStrikeThrough = value;
	}
}