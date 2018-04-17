package com.hotent.core.table.colmap;

import com.hotent.core.table.ColumnModel;
import com.hotent.core.util.StringUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class DB2ColumnMap implements RowMapper<ColumnModel> {
	public ColumnModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		ColumnModel column = new ColumnModel();
		String tabName = rs.getString("TAB_NAME");
		String colName = rs.getString("COL_NAME");
		String colType = rs.getString("COL_TYPE");
		String colComment = rs.getString("COL_COMMENT");
		String nullable = rs.getString("IS_NULLABLE");
		String length = rs.getString("LENGTH");
		String scale = rs.getString("SCALE");
		String keySeq = rs.getString("KEYSEQ");
		int iLength = this.string2Int(length, 0);
		int iScale = this.string2Int(scale, 0);
		int iKeySeq = this.string2Int(keySeq, 0);
		column.setTableName(tabName);
		column.setName(colName);
		column.setComment(colComment);
		column.setIsNull("Y".equalsIgnoreCase(nullable));
		column.setIsPk(iKeySeq > 0);
		this.setType(colType, iLength, iLength, iScale, column);
		return column;
	}

	private int string2Int(String str, int def) {
		int retval = def;
		if (StringUtil.isNotEmpty(str)) {
			try {
				retval = Integer.parseInt(str);
			} catch (Exception arg4) {
				arg4.printStackTrace();
			}
		}

		return retval;
	}

	private void setType(String type, int length, int precision, int scale, ColumnModel columnModel) {
		String dbtype = type.toLowerCase();
		if (dbtype.endsWith("bigint")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(19);
			columnModel.setDecimalLen(0);
		} else if (dbtype.endsWith("blob")) {
			columnModel.setColumnType("clob");
		} else if (dbtype.endsWith("character")) {
			columnModel.setColumnType("varchar");
			columnModel.setCharLen(length);
			columnModel.setDecimalLen(0);
		} else if (dbtype.endsWith("clob")) {
			columnModel.setColumnType("clob");
		} else if (dbtype.endsWith("date")) {
			columnModel.setColumnType("date");
		} else if (dbtype.endsWith("dbclob")) {
			columnModel.setColumnType("clob");
		} else if (dbtype.endsWith("decimal")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(precision - scale);
			columnModel.setDecimalLen(scale);
		} else if (dbtype.endsWith("double")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(precision - scale);
			columnModel.setDecimalLen(scale);
		} else if (dbtype.endsWith("graphic")) {
			columnModel.setColumnType("clob");
		} else if (dbtype.endsWith("integer")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(10);
			columnModel.setDecimalLen(0);
		} else if (dbtype.endsWith("long varchar")) {
			columnModel.setColumnType("varchar");
			columnModel.setCharLen(length);
		} else if (dbtype.endsWith("long vargraphic")) {
			columnModel.setColumnType("clob");
		} else if (dbtype.endsWith("real")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(length);
			columnModel.setDecimalLen(scale);
		} else if (dbtype.endsWith("smallint")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(5);
			columnModel.setDecimalLen(0);
		} else if (dbtype.endsWith("time")) {
			columnModel.setColumnType("date");
		} else if (dbtype.endsWith("timestamp")) {
			columnModel.setColumnType("date");
		} else if (dbtype.endsWith("varchar")) {
			columnModel.setColumnType("varchar");
			columnModel.setCharLen(length);
		} else if (dbtype.endsWith("vargraphic")) {
			columnModel.setColumnType("clob");
		} else if (dbtype.endsWith("xml")) {
			columnModel.setColumnType("clob");
		}

	}
}