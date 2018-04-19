package com.hotent.core.excel.reader;

public class ExcelReaderConfig {
	private String[] columns;
	private int currPosittion = 0;
	private int colStartPosittion = 0;

	public String[] getColumns() {
		return this.columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	public int getCurrPosittion() {
		return this.currPosittion;
	}

	public void setCurrPosittion(int currPosittion) {
		this.currPosittion = currPosittion - 1;
	}

	public int getColStartPosittion() {
		return this.colStartPosittion;
	}

	public void setColStartPosittion(int colStartPosittion) {
		this.colStartPosittion = colStartPosittion;
	}
}