package com.hotent.core.excel.util;

import com.hotent.core.excel.Excel;
import com.hotent.core.excel.style.Align;
import com.hotent.core.excel.style.BorderStyle;
import com.hotent.core.excel.style.Color;
import com.hotent.core.excel.util.ExcelUtil.1;
import com.hotent.core.util.DateFormatUtil;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtil {
	public static int getLastRowNum(HSSFSheet sheet) {
		int lastRowNum = sheet.getLastRowNum();
		if (lastRowNum == 0) {
			lastRowNum = sheet.getPhysicalNumberOfRows() - 1;
		}

		return lastRowNum;
	}

	public static int getFirstCellNum(HSSFRow row) {
		return row.getFirstCellNum();
	}

	public static int getLastCellNum(HSSFRow row) {
		return row.getLastCellNum();
	}

	public static HSSFRow getHSSFRow(HSSFSheet sheet, int row) {
		if (row < 0) {
			row = 0;
		}

		HSSFRow r = sheet.getRow(row);
		if (r == null) {
			r = sheet.createRow(row);
		}

		return r;
	}

	public static HSSFCell getHSSFCell(HSSFSheet sheet, int row, int col) {
		HSSFRow r = getHSSFRow(sheet, row);
		return getHSSFCell(r, col);
	}

	public static HSSFCell getHSSFCell(HSSFRow row, int col) {
		if (col < 0) {
			col = 0;
		}

		HSSFCell c = row.getCell(col);
		c = c == null ? row.createCell(col) : c;
		return c;
	}

	public static HSSFSheet getHSSFSheet(HSSFWorkbook workbook, int index) {
		if (index < 0) {
			index = 0;
		}

		if (index > workbook.getNumberOfSheets() - 1) {
			workbook.createSheet();
			return workbook.getSheetAt(workbook.getNumberOfSheets() - 1);
		} else {
			return workbook.getSheetAt(index);
		}
	}

	public static String getCellFormatValue(HSSFCell cell) {
		if (cell == null) {
			return "";
		} else {
			String cellvalue = "";
			switch (cell.getCellType()) {
				case 0 :
				case 2 :
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						Date date = cell.getDateCellValue();
						cellvalue = DateFormatUtil.format(date);
					} else {
						cellvalue = String.valueOf(cell.getNumericCellValue());
					}
					break;
				case 1 :
					cellvalue = cell.getRichStringCellValue().getString();
					break;
				case 3 :
				default :
					cellvalue = "";
					break;
				case 4 :
					cellvalue = String.valueOf(cell.getBooleanCellValue());
			}

			return cellvalue;
		}
	}

	public static void downloadExcel(HSSFWorkbook workBook, String fileName, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/x-download");
		if (System.getProperty("file.encoding").equals("GBK")) {
			response.setHeader("Content-Disposition",
					"attachment;filename=\"" + new String(fileName.getBytes(), "ISO-8859-1") + ".xls" + "\"");
		} else {
			response.setHeader("Content-Disposition",
					"attachment;filename=\"" + URLEncoder.encode(fileName, "utf-8") + ".xls" + "\"");
		}

		ServletOutputStream os = null;

		try {
			os = response.getOutputStream();
			workBook.write(os);
			os.flush();
			os.close();
		} catch (Exception arg7) {
			arg7.printStackTrace();
		} finally {
			if (os != null) {
				os.close();
			}

		}

	}

	public static HSSFWorkbook exportExcel(String title, int rowHeight, Map<String, String> fieldMap, List data) throws Exception {
      int size = fieldMap.size();
      Excel excel = new Excel();
      if(size == 0) {
         throw new Exception("请设置列！");
      } else {
         excel.sheet().sheetName(title);
         int i = 0;

         for(Iterator rows = fieldMap.values().iterator(); rows.hasNext(); ++i) {
            String i$ = (String)rows.next();
            excel.cell(0, i).value(i$).align(Align.CENTER).bgColor(Color.GREY_25_PERCENT).fontHeightInPoint(14).width(12800).border(BorderStyle.THIN, Color.BLACK).font(new 1());
         }

         int arg15 = 1;

         for(Iterator arg16 = data.iterator(); arg16.hasNext(); ++arg15) {
            Object obj = arg16.next();
            Map rowObj = (Map)obj;
            int col = 0;

            for(Iterator i$1 = fieldMap.keySet().iterator(); i$1.hasNext(); ++col) {
               String key = (String)i$1.next();
               String val = rowObj.get(key) == null?"":rowObj.get(key).toString();
               excel.cell(arg15, col).value(val).border(BorderStyle.MEDIUM, Color.BLACK).fontHeightInPoint(14).warpText(true).align(Align.LEFT);
            }
         }

         return excel.getWorkBook();
      }
   }
}