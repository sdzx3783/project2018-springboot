package com.hotent.core.excel;

import com.hotent.core.excel.DefaultExcelStyle;
import com.hotent.core.excel.ExcelContext;
import com.hotent.core.excel.Excel.1;
import com.hotent.core.excel.editor.CellEditor;
import com.hotent.core.excel.editor.ColumnEditor;
import com.hotent.core.excel.editor.RegionEditor;
import com.hotent.core.excel.editor.RowEditor;
import com.hotent.core.excel.editor.SheetEditor;
import com.hotent.core.excel.style.Align;
import com.hotent.core.excel.style.BorderStyle;
import com.hotent.core.excel.style.Color;
import com.hotent.core.excel.util.ExcelUtil;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFName;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.util.CellRangeAddress;

public class Excel {
	private ExcelContext ctx;

	public Excel() {
		this(new DefaultExcelStyle());
	}

	public Excel(DefaultExcelStyle defaultStyle) {
		this((String) null, defaultStyle);
	}

	public Excel(String excelPath) {
		this(excelPath, new DefaultExcelStyle());
	}

	public Excel(String excelPath, DefaultExcelStyle defaultStyle) {
		HSSFWorkbook workBook;
		if (excelPath != null && !excelPath.trim().equals("")) {
			workBook = this.readExcel(excelPath);
			if (workBook == null) {
				workBook = new HSSFWorkbook();
			}
		} else {
			workBook = new HSSFWorkbook();
		}

		this.ctx = new ExcelContext(this, workBook);
		this.ctx.setDefaultStyle(defaultStyle);
		this.setWorkingSheet(0);
		HSSFCellStyle tempCellStyle = workBook.createCellStyle();
		this.ctx.setTempCellStyle(tempCellStyle);
		HSSFFont tempFont = workBook.createFont();
		this.ctx.setTempFont(tempFont);
		HSSFCell cell = ExcelUtil.getHSSFCell(this.ctx.getWorkingSheet(), 0, 0);
		HSSFCellStyle cellStyle = cell.getCellStyle();
		cellStyle.setFillForegroundColor(defaultStyle.getBackgroundColor().getIndex());
		cellStyle.setFillPattern(defaultStyle.getFillPattern().getFillPattern());
		cellStyle.setAlignment(defaultStyle.getAlign().getAlignment());
		cellStyle.setVerticalAlignment(defaultStyle.getVAlign().getAlignment());
		cellStyle.setBorderBottom(defaultStyle.getBorderStyle().getBorderType());
		cellStyle.setBorderLeft(defaultStyle.getBorderStyle().getBorderType());
		cellStyle.setBorderRight(defaultStyle.getBorderStyle().getBorderType());
		cellStyle.setBorderTop(defaultStyle.getBorderStyle().getBorderType());
		cellStyle.setBottomBorderColor(defaultStyle.getBorderColor().getIndex());
		cellStyle.setTopBorderColor(defaultStyle.getBorderColor().getIndex());
		cellStyle.setLeftBorderColor(defaultStyle.getBorderColor().getIndex());
		cellStyle.setRightBorderColor(defaultStyle.getBorderColor().getIndex());
		HSSFFont font = cellStyle.getFont(workBook);
		font.setFontHeightInPoints(defaultStyle.getFontSize());
		font.setFontName(defaultStyle.getFontName());
		font.setColor(defaultStyle.getFontColor().getIndex());
	}

	private HSSFWorkbook readExcel(String excelPath) {
		HSSFWorkbook result = null;

		POIFSFileSystem fs;
		try {
			fs = new POIFSFileSystem(new FileInputStream(excelPath));
			result = new HSSFWorkbook(fs);
		} catch (Exception arg10) {
			try {
				fs = new POIFSFileSystem(this.getClass().getResourceAsStream(excelPath));
				result = new HSSFWorkbook(fs);
			} catch (Exception arg9) {
				try {
					InputStream e = null;
					StackTraceElement[] st = (new Throwable()).getStackTrace();

					for (int i = 2; i < st.length; ++i) {
						e = Class.forName(st[i].getClassName()).getResourceAsStream(excelPath);
						if (e != null) {
							fs = new POIFSFileSystem(e);
							result = new HSSFWorkbook(fs);
							break;
						}
					}
				} catch (Exception arg8) {
					arg8.printStackTrace();
				}
			}
		}

		return result;
	}

	public boolean saveExcel(String excelPath) {
		try {
			BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream(excelPath));
			return this.saveExcel((OutputStream) fileOut);
		} catch (FileNotFoundException arg3) {
			arg3.printStackTrace();
			return false;
		}
	}

	public boolean saveExcel(OutputStream fileOut) {
		boolean result = false;

		try {
			this.ctx.getWorkBook().write(fileOut);
			result = true;
		} catch (Exception arg11) {
			arg11.printStackTrace();
		} finally {
			try {
				fileOut.flush();
				fileOut.close();
			} catch (Exception arg10) {
				result = false;
			}

		}

		return result;
	}

	public SheetEditor setWorkingSheet(int index) {
		if (index < 0) {
			index = 0;
		}

		this.ctx.setWorkingSheet(ExcelUtil.getHSSFSheet(this.ctx.getWorkBook(), index));
		return this.sheet(index);
	}

	public CellEditor cell(int row, int col) {
		CellEditor cellEditor = new CellEditor(row, col, this.ctx);
		return cellEditor;
	}

	public RowEditor row(int row) {
		return new RowEditor(row, this.ctx);
	}

	public RowEditor row(int row, int startCol) {
		return new RowEditor(row, startCol, this.ctx);
	}

	public RowEditor row() {
		int rowNum = ExcelUtil.getLastRowNum(this.ctx.getWorkingSheet());
		if (!this.checkEmptyRow(rowNum)) {
			++rowNum;
		}

		return new RowEditor(rowNum, this.ctx);
	}

	private boolean checkEmptyRow(int rowNum) {
		HSSFRow row = this.ctx.getWorkingSheet().getRow(rowNum);
		short lastCell = row != null ? row.getLastCellNum() : 2;
		return lastCell == 1 || lastCell == -1;
	}

	public ColumnEditor column(int col) {
		ColumnEditor columnEditor = new ColumnEditor(col, this.ctx);
		return columnEditor;
	}

	public ColumnEditor column(int col, int startRow) {
		ColumnEditor columnEditor = new ColumnEditor(col, startRow, this.ctx);
		return columnEditor;
	}

	public RegionEditor region(int beginRow, int beginCol, int endRow, int endCol) {
		RegionEditor regionEditor = new RegionEditor(beginRow, beginCol, endRow, endCol, this.ctx);
		return regionEditor;
	}

	public RegionEditor region(String ref) {
		RegionEditor regionEditor = new RegionEditor(CellRangeAddress.valueOf(ref), this.ctx);
		return regionEditor;
	}

	public SheetEditor sheet(int index) {
		if (index < 0) {
			index = 0;
		}

		SheetEditor sheetEditor = new SheetEditor(ExcelUtil.getHSSFSheet(this.ctx.getWorkBook(), index), this.ctx);
		return sheetEditor;
	}

	public SheetEditor sheet() {
		return this.sheet(this.ctx.getWorkingSheetIndex());
	}

	public HSSFWorkbook getWorkBook() {
		return this.ctx.getWorkBook();
	}

	public int getWorkingSheetIndex() {
		return this.ctx.getWorkingSheetIndex();
	}

	public Name createName(String name, String formulaText) {
		HSSFName refersName = this.ctx.getWorkBook().createName();
		refersName.setNameName(name);
		refersName.setRefersToFormula(formulaText);
		return refersName;
	}

	public static void main(String[] args) {
      Object[] val = new Object[]{"插入一行数据", Integer.valueOf(123), Character.valueOf('A'), Double.valueOf(3.141592653589793D), new Date(), "hello"};
      Excel excel = new Excel();
      excel.cell(0, 0).value("Hello World!").align(Align.CENTER).bgColor(Color.LIGHT_YELLOW).height(30.0F).font(new 1());
      excel.region(0, 0, 0, 10).merge();
      excel.region("$A$2:$K$2").merge();
      ((RowEditor)excel.row(2).value(val).addWidth(2000)).borderOuter(BorderStyle.DASH_DOT_DOT, Color.CORAL);
      excel.row(4, 1).value(val).borderFull(BorderStyle.DASH_DOT, Color.RED);
      excel.row(6).value(val, 2).borderTop(BorderStyle.THIN, Color.BLUE);
      ((ColumnEditor)((ColumnEditor)excel.column(11).value(val).align(Align.CENTER)).borderFull(BorderStyle.THICK, Color.CORNFLOWER_BLUE)).autoWidth();
      excel.cell(7, 0).value("=IF(B3=123,\"等于\",\"不等于\")");
      excel.cell(7, 1).value(Double.valueOf(0.578923D)).dataFormat("0.00%");
      excel.cell(7, 2).value(Double.valueOf(0.578923D), "0.00%");
      excel.region(8, 0, 10, 1).image("http://www.jee-soft.cn/htsite/template/ht/images/index_r1_c3.jpg");
      excel.sheet().freeze(1, 0).sheetName("这是第一个表");
      excel.cell(8, 5).value("这个单元格设置了备注").comment("这是一条备注");
      excel.setWorkingSheet(1).sheetName("第二个表");
      excel.row(0).value(val);
      excel.sheet().groupColumn(0, 3);
      excel.saveExcel("D:/helloworld.xls");
   }
}