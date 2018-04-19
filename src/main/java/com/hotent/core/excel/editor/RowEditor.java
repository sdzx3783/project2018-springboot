package com.hotent.core.excel.editor;

import com.hotent.core.excel.ExcelContext;
import com.hotent.core.excel.editor.AbstractRegionEditor;
import com.hotent.core.excel.editor.CellEditor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.util.CellRangeAddress;

public class RowEditor extends AbstractRegionEditor<RowEditor> {
	private HSSFRow row;
	private int startCol;

	public RowEditor(int row, int startCol, ExcelContext context) {
		super(context);
		this.startCol = 0;
		this.row = this.getRow(row);
		this.startCol = startCol;
	}

	public RowEditor(int row, ExcelContext context) {
		this(row, 0, context);
	}

	public RowEditor value(Object[] rowData) {
		this.value(rowData, this.startCol);
		return this;
	}

	public RowEditor value(Object[] rowData, int startCol) {
		if (startCol < 0) {
			startCol = 0;
		}

		this.insertData(rowData, this.row, startCol, true);
		return this;
	}

	public RowEditor insert(Object[] rowData) {
		return this.insert(rowData, this.startCol);
	}

	public RowEditor insert(Object[] rowData, int startCol) {
		if (startCol < 0) {
			startCol = 0;
		}

		this.insertData(rowData, this.row, startCol, false);
		return this;
	}

	public RowEditor append(Object[] rowData) {
		this.insertData(rowData, this.row, this.row.getLastCellNum(), true);
		return this;
	}

	public RowEditor height(float h) {
		this.row.setHeightInPoints(h);
		return this;
	}

	public CellEditor cell(int col, int... cols) {
		CellEditor cellEditor = new CellEditor(this.row.getRowNum(), col, this.ctx);
		int[] arr$ = cols;
		int len$ = cols.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			int c = arr$[i$];
			cellEditor.add(this.row.getRowNum(), c);
		}

		return cellEditor;
	}

	public HSSFRow toHSSFRow() {
		return this.row;
	}

	public RowEditor width(int[] widths) {
		CellEditor cellEditor = this.newTopCellEditor();
		cellEditor.width(widths);
		return this;
	}

	private void insertData(Object[] rowData, HSSFRow row, int startCol, boolean overwrite) {
		if (!overwrite) {
			this.workingSheet.shiftRows(row.getRowNum(), this.workingSheet.getLastRowNum(), 1, true, false);
		}

		short i = 0;
		Object[] arr$ = rowData;
		int len$ = rowData.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			Object obj = arr$[i$];
			CellEditor cellEditor = new CellEditor(row.getRowNum(), startCol + i, this.ctx);
			cellEditor.value(obj);
			++i;
		}

	}

	protected CellEditor newCellEditor() {
		CellEditor cellEditor = new CellEditor(this.ctx);
		boolean minColIx = false;
		boolean maxColIx = false;
		short arg4 = (short) this.startCol;
		short arg5 = this.row.getLastCellNum();

		for (int i = arg4; i < arg5; ++i) {
			cellEditor.add(this.row.getRowNum(), i);
		}

		return cellEditor;
	}

	protected CellEditor newBottomCellEditor() {
		return this.newCellEditor();
	}

	protected CellEditor newLeftCellEditor() {
		CellEditor cellEditor = new CellEditor(this.ctx);
		cellEditor.add(this.row.getRowNum(), this.startCol);
		return cellEditor;
	}

	protected CellEditor newRightCellEditor() {
		CellEditor cellEditor = new CellEditor(this.ctx);
		cellEditor.add(this.row.getRowNum(), this.row.getLastCellNum());
		return cellEditor;
	}

	protected CellEditor newTopCellEditor() {
		return this.newCellEditor();
	}

	protected CellRangeAddress getCellRange() {
		return new CellRangeAddress(this.row.getRowNum(), this.row.getRowNum(), this.startCol,
				this.row.getLastCellNum() - 1);
	}

	protected HSSFRow getHSSFRow() {
		return this.row;
	}
}