package com.hotent.core.bpm.graph;

import com.hotent.core.bpm.graph.Shape;

public class DivShape extends Shape {
	private int zIndex = 0;
	private String id = "";
	private String type = "";

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public DivShape(String name, float x, float y, float w, float h) {
		super(name, x, y, w, h);
	}

	public DivShape(String name, float x, float y, float w, float h, int zIndex, String id, String type) {
		super(name, x, y, w, h);
		this.zIndex = zIndex;
		this.id = id;
		this.type = type;
	}

	public int getzIndex() {
		return this.zIndex;
	}

	public void setzIndex(int zIndex) {
		this.zIndex = zIndex;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String toString() {
		return "<div class=\'flowNode\' id=\'" + this.id + "\' " + "style=\'position:absolute;z-index:" + this.zIndex
				+ "; " + "left:" + this.getX() + "px;" + "top:" + this.getY() + "px;" + "width:" + this.getW() + "px;"
				+ "height:" + this.getH() + "px;\' " + "title=\'" + this.getName() + "\' " + "type=\'" + this.getType()
				+ "\'></div>\r\n".replaceAll("\\.0px", "px");
	}
}