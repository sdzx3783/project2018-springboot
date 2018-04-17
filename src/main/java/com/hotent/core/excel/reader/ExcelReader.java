package com.hotent.core.excel.reader;

import com.hotent.core.excel.reader.DataEntity;
import com.hotent.core.excel.reader.ExcelReaderConfig;
import com.hotent.core.excel.reader.FieldEntity;
import com.hotent.core.excel.reader.TableEntity;
import com.hotent.core.excel.util.ExcelUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelReader {
	private ExcelReaderConfig excelReaderConfig;
	private BufferedReader reader = null;
	private InputStream is = null;
	private int currSheet;
	private int currPosittion;
	private int numOfSheets;
	private HSSFWorkbook workbook = null;

	private void initExcelReader(InputStream inputFile) throws IOException, Exception {
		if (inputFile == null) {
			throw new IOException("文件输入流为空");
		} else {
			this.currPosittion = 0;
			this.currSheet = 0;
			this.is = inputFile;
			this.workbook = new HSSFWorkbook(this.is);
			this.numOfSheets = this.workbook.getNumberOfSheets();
		}
	}

	private String[] readExcelTitle() {
		HSSFSheet sheet = this.getCurrSheet();
		HSSFRow row = sheet.getRow(0);
		if (row == null) {
			return null;
		} else {
			short colNum = row.getLastCellNum();
			String[] title = new String[colNum];

			for (int i = 0; i < colNum; ++i) {
				title[i] = ExcelUtil.getCellFormatValue(row.getCell(i));
			}

			return title;
		}
	}

	private List<DataEntity> readSheet() {
		HSSFSheet sheet = this.getCurrSheet();
		int lastRowNum = sheet.getLastRowNum();
		ArrayList dataEntityList = new ArrayList();

		for (int i = 1; i <= lastRowNum; ++i) {
			DataEntity dataEntity = new DataEntity();
			List fieldEntityList = this.getLine(sheet, i);
			if (fieldEntityList != null) {
				FieldEntity fieldEntity = this.getFieldEntityKey(fieldEntityList);
				dataEntity.setPkName(fieldEntity.getName());
				dataEntity.setPkVal(fieldEntity.getValue());
				dataEntity.setFieldEntityList(fieldEntityList);
			}

			dataEntityList.add(dataEntity);
		}

		return dataEntityList;
	}

	private FieldEntity getFieldEntityKey(List<FieldEntity> fieldEntityList) {
		Iterator i$ = fieldEntityList.iterator();

		FieldEntity fieldEntity;
		do {
			if (!i$.hasNext()) {
				return null;
			}

			fieldEntity = (FieldEntity) i$.next();
		} while (fieldEntity.getIsKey().shortValue() != FieldEntity.IS_KEY.shortValue());

		return fieldEntity;
	}

	private List<FieldEntity> getLine(HSSFSheet sheet, int row) {
		HSSFRow rowline = sheet.getRow(row);
		if (rowline == null) {
			return null;
		} else {
			short filledColumns = rowline.getLastCellNum();
			HSSFCell cell = null;
			int colStart = this.excelReaderConfig.getColStartPosittion();
			ArrayList list = new ArrayList();

			for (int i = colStart; i < filledColumns; ++i) {
				cell = rowline.getCell(i);
				String column = this.excelReaderConfig.getColumns()[i - colStart];
				String cellValue = ExcelUtil.getCellFormatValue(cell);
				FieldEntity fieldEntity = new FieldEntity();
				fieldEntity.setName(column);
				fieldEntity.setValue(cellValue);
				fieldEntity.setIsKey(i == colStart ? FieldEntity.IS_KEY : FieldEntity.NOT_KEY);
				list.add(fieldEntity);
			}

			return list;
		}
	}

	public void close() {
		if (this.is != null) {
			try {
				this.is.close();
			} catch (IOException arg2) {
				this.is = null;
				arg2.printStackTrace();
			}
		}

		if (this.reader != null) {
			try {
				this.reader.close();
			} catch (IOException arg1) {
				this.reader = null;
				arg1.printStackTrace();
			}
		}

	}

	private HSSFSheet getCurrSheet() {
		return this.workbook.getSheetAt(this.currSheet);
	}

	public ExcelReaderConfig getExcelReaderConfig() {
		return this.excelReaderConfig;
	}

	public void setExcelReaderConfig(ExcelReaderConfig excelReaderConfig) {
		this.excelReaderConfig = excelReaderConfig;
	}

	public int getNumOfSheets() {
		return this.numOfSheets;
	}

	public void setNumOfSheets(int numOfSheets) {
		this.numOfSheets = numOfSheets;
	}

	public void setCurrSheet(int currSheet) {
		this.currSheet = currSheet;
	}

	public int getCurrPosittion() {
		return this.currPosittion;
	}

	public void setCurrPosittion(int currPosittion) {
		this.currPosittion = currPosittion;
	}

	private TableEntity getTableEntity(ExcelReaderConfig config, Short isMain) {
		HSSFSheet sheet = this.getCurrSheet();
		String[] columns = this.readExcelTitle();
		if (columns == null) {
			return null;
		} else {
			config.setColumns(columns);
			this.setExcelReaderConfig(config);
			List dataEntityList = this.readSheet();
			TableEntity tableEntity = new TableEntity();
			tableEntity.setName(sheet.getSheetName());
			tableEntity.setIsMain(isMain);
			tableEntity.setDataEntityList(dataEntityList);
			return tableEntity;
		}
	}

	public TableEntity readFile(InputStream input) throws Exception {
		this.initExcelReader(input);
		ExcelReaderConfig config = new ExcelReaderConfig();
		TableEntity tableEntity = this.getTableEntity(config, TableEntity.IS_MAIN);
		int numOfSheets = this.getNumOfSheets();
		if (numOfSheets > 0) {
			ArrayList subList = new ArrayList();

			for (int i = 1; i < numOfSheets; ++i) {
				this.setCurrSheet(i);
				TableEntity table = this.getTableEntity(config, TableEntity.NOT_MAIN);
				subList.add(table);
			}

			if (subList.size() > 0) {
				tableEntity.setSubTableEntityList(subList);
			}
		}

		this.close();
		return tableEntity;
	}

	public static void main(String[] args) throws Exception {
		ExcelReader excel = new ExcelReader();
		File file = new File("d:\\test2.xls");
		FileInputStream input = new FileInputStream(file);
		TableEntity excelEntity = excel.readFile(input);
		Logger logger = LoggerFactory.getLogger(ExcelReader.class);
		logger.info(excelEntity.toString());
	}
}