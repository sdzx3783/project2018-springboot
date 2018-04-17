package com.hotent.core.bpm.graph;

public class ShapeMeta {
	private String xml = "";
	private int width = 0;
	private int height = 0;

	public ShapeMeta(int w, int h, String xml) {
		this.width = w;
		this.height = h;
		this.xml = xml;
	}

	public String getXml() {
		return this.xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}