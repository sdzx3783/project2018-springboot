package com.hotent.core.excel;

import com.hotent.core.excel.DefaultExcelStyle;
import com.hotent.core.excel.Excel;
import com.hotent.core.excel.editor.listener.CellValueListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public final class ExcelContext {
	private Map<Integer, HSSFCellStyle> styleCache = new HashMap();
	private Map<Integer, HSSFFont> fontCache = new HashMap();
	private Map<HSSFSheet, HSSFPatriarch> patriarchCache = new HashMap();
	private HSSFWorkbook workBook;
	private HSSFCellStyle tempCellStyle;
	private HSSFFont tempFont;
	private Excel excel;
	private HSSFSheet workingSheet;
	private DefaultExcelStyle defaultStyle;
	private int workingSheetIndex = 0;
	private Map<Integer, List<CellValueListener>> cellValueListener;

	protected ExcelContext(Excel excel, HSSFWorkbook workBook) {
		this.workBook = workBook;
		short numStyle = workBook.getNumCellStyles();

		short numFont;
		for (numFont = 0; numFont < numStyle; ++numFont) {
			HSSFCellStyle i = workBook.getCellStyleAt(numFont);
			if (i != this.tempCellStyle) {
				this.styleCache.put(Integer.valueOf(i.hashCode() - i.getIndex()), i);
			}
		}

		numFont = workBook.getNumberOfFonts();

		for (short arg6 = 0; arg6 < numFont; ++arg6) {
			HSSFFont font = workBook.getFontAt(arg6);
			if (font != this.tempFont) {
				this.fontCache.put(Integer.valueOf(font.hashCode() - font.getIndex()), font);
			}
		}

	}

	public HSSFWorkbook getWorkBook() {
		return this.workBook;
	}

	public void setWorkBook(HSSFWorkbook workBook) {
		this.workBook = workBook;
	}

	public HSSFCellStyle getTempCellStyle() {
		return this.tempCellStyle;
	}

	public void setTempCellStyle(HSSFCellStyle tempCellStyle) {
		this.tempCellStyle = tempCellStyle;
	}

	public HSSFFont getTempFont() {
		return this.tempFont;
	}

	public void setTempFont(HSSFFont tempFont) {
		this.tempFont = tempFont;
	}

	public HSSFSheet getWorkingSheet() {
		return this.workingSheet;
	}

	public void setWorkingSheet(HSSFSheet workingSheet) {
		this.workingSheet = workingSheet;
		this.workingSheetIndex = this.workBook.getSheetIndex(workingSheet);
	}

	public HSSFPatriarch getHSSFPatriarch(HSSFSheet sheet) {
		HSSFPatriarch patr = null;

		try {
			patr = (HSSFPatriarch) this.patriarchCache.get(sheet);
			if (patr == null) {
				patr = sheet.createDrawingPatriarch();
				this.patriarchCache.put(sheet, patr);
			}
		} catch (Exception arg3) {
			patr = sheet.createDrawingPatriarch();
		}

		return patr;
	}

	public void setDefaultStyle(DefaultExcelStyle defaultStyle) {
		this.defaultStyle = defaultStyle;
	}

	public DefaultExcelStyle getDefaultStyle() {
		return this.defaultStyle;
	}

	public int getWorkingSheetIndex() {
		return this.workingSheetIndex;
	}

	public void setStyleCache(Map<Integer, HSSFCellStyle> styleCache) {
		this.styleCache = styleCache;
	}

	public Map<Integer, HSSFCellStyle> getStyleCache() {
		return this.styleCache;
	}

	public void setFontCache(Map<Integer, HSSFFont> fontCache) {
		this.fontCache = fontCache;
	}

	public Map<Integer, HSSFFont> getFontCache() {
		return this.fontCache;
	}

	private Map<Integer, List<CellValueListener>> getCellValueListener() {
		if (this.cellValueListener == null) {
			this.cellValueListener = new HashMap();
		}

		return this.cellValueListener;
	}

	public List<CellValueListener> getListenerList(int sheetIndex) {
		Map map = this.getCellValueListener();
		Object listenerList = (List) map.get(Integer.valueOf(sheetIndex));
		if (listenerList == null) {
			listenerList = new ArrayList();
			map.put(Integer.valueOf(sheetIndex), listenerList);
		}

		return (List) listenerList;
	}

	public Excel getExcel() {
		return this.excel;
	}
}