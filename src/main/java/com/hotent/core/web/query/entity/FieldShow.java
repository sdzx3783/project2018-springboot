package com.hotent.core.web.query.entity;

public class FieldShow {
	protected String name;
	protected String desc;
	protected int show = 0;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getShow() {
		return this.show;
	}

	public void setShow(int show) {
		this.show = show;
	}
}