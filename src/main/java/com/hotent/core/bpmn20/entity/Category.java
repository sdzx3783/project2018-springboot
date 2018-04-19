package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.CategoryValue;
import com.hotent.core.bpmn20.entity.RootElement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCategory", propOrder = {"categoryValue"})
public class Category extends RootElement {
	protected List<CategoryValue> categoryValue;
	@XmlAttribute
	protected String name;

	public List<CategoryValue> getCategoryValue() {
		if (this.categoryValue == null) {
			this.categoryValue = new ArrayList();
		}

		return this.categoryValue;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}
}