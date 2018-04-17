package com.hotent.core.table.colmap;

import com.hotent.core.table.ColumnModel;
import com.hotent.core.util.StringUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class H2ColumnMap implements RowMapper<ColumnModel> {
	public ColumnModel mapRow(ResultSet rs, int row) throws SQLException {
		ColumnModel column = new ColumnModel();
		String name = rs.getString("COLUMN_NAME");
		String is_nullable = rs.getString("IS_NULLABLE");
		String data_type = rs.getString("TYPE_NAME");
		String length = rs.getString("LENGTH");
		String precisions = rs.getString("PRECISIONS");
		String scale = rs.getString("SCALE");
		String column_list = rs.getString("COLUMN_LIST");
		String column_comment = rs.getString("REMARKS");
		String table_name = rs.getString("TABLE_NAME");

		int iLength;
		try {
			iLength = StringUtil.isEmpty(length) ? 0 : Integer.parseInt(length);
		} catch (NumberFormatException arg21) {
			iLength = 0;
		}

		int iPrecisions = StringUtil.isEmpty(precisions) ? 0 : Integer.parseInt(precisions);
		int iScale = StringUtil.isEmpty(scale) ? 0 : Integer.parseInt(scale);
		column.setName(name);
		column.setTableName(table_name);
		column.setComment(column_comment);
		boolean isPkColumn = false;
		if (StringUtil.isNotEmpty(column_list)) {
			String[] isNull = column_list.split(",");
			String[] arr$ = isNull;
			int len$ = isNull.length;

			for (int i$ = 0; i$ < len$; ++i$) {
				String pkColumn = arr$[i$];
				if (name.trim().equalsIgnoreCase(pkColumn.trim())) {
					isPkColumn = true;
					break;
				}
			}
		}

		column.setIsPk(isPkColumn);
		boolean arg22 = is_nullable.equals("YES");
		column.setIsNull(arg22);
		this.setType(data_type, iLength, iPrecisions, iScale, column);
		return column;
	}

	private void setType(String dbtype, int length, int precision, int scale, ColumnModel columnModel) {
		dbtype = dbtype.toUpperCase();
		if (dbtype.equals("BIGINT")) {
			columnModel.setColumnType("int");
			columnModel.setIntLen(19);
			columnModel.setDecimalLen(0);
		} else if (dbtype.equals("INT8")) {
			columnModel.setColumnType("int");
			columnModel.setIntLen(19);
			columnModel.setDecimalLen(0);
		} else if (dbtype.equals("INT")) {
			columnModel.setColumnType("int");
			columnModel.setIntLen(10);
			columnModel.setDecimalLen(0);
		} else if (dbtype.equals("INTEGER")) {
			columnModel.setColumnType("int");
			columnModel.setIntLen(10);
			columnModel.setDecimalLen(0);
		} else if (dbtype.equals("MEDIUMINT")) {
			columnModel.setColumnType("int");
			columnModel.setIntLen(7);
			columnModel.setDecimalLen(0);
		} else if (dbtype.equals("INT4")) {
			columnModel.setColumnType("int");
			columnModel.setIntLen(5);
			columnModel.setDecimalLen(0);
		} else if (dbtype.equals("SIGNED")) {
			columnModel.setColumnType("int");
			columnModel.setIntLen(3);
			columnModel.setDecimalLen(0);
		} else if (dbtype.equals("TINYINT")) {
			columnModel.setColumnType("int");
			columnModel.setIntLen(2);
			columnModel.setDecimalLen(0);
		} else if (dbtype.equals("SMALLINT")) {
			columnModel.setColumnType("int");
			columnModel.setIntLen(5);
			columnModel.setDecimalLen(0);
		} else if (dbtype.equals("INT2")) {
			columnModel.setColumnType("int");
			columnModel.setIntLen(5);
			columnModel.setDecimalLen(0);
		} else if (dbtype.equals("YEAR")) {
			columnModel.setColumnType("int");
			columnModel.setIntLen(5);
			columnModel.setDecimalLen(0);
		} else if (dbtype.equals("IDENTITY")) {
			columnModel.setColumnType("int");
			columnModel.setIntLen(5);
			columnModel.setDecimalLen(0);
		} else if (dbtype.equals("DECIMAL")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(precision - scale);
			columnModel.setDecimalLen(scale);
		} else if (dbtype.equals("DOUBLE")) {
			columnModel.setColumnType("number");
		} else if (dbtype.equals("FLOAT")) {
			columnModel.setColumnType("number");
		} else if (dbtype.equals("FLOAT4")) {
			columnModel.setColumnType("number");
		} else if (dbtype.equals("FLOAT8")) {
			columnModel.setColumnType("number");
		} else if (dbtype.equals("REAL")) {
			columnModel.setColumnType("number");
		} else if (dbtype.equals("TIME")) {
			columnModel.setColumnType("date");
		} else if (dbtype.equals("DATE")) {
			columnModel.setColumnType("date");
		} else if (dbtype.equals("DATETIME")) {
			columnModel.setColumnType("date");
		} else if (dbtype.equals("SMALLDATETIME")) {
			columnModel.setColumnType("date");
		} else if (dbtype.equals("TIMESTAMP")) {
			columnModel.setColumnType("date");
		} else if (dbtype.equals("BINARY")) {
			columnModel.setColumnType("clob");
		} else if (dbtype.equals("VARBINARY")) {
			columnModel.setColumnType("clob");
		} else if (dbtype.equals("LONGVARBINARY")) {
			columnModel.setColumnType("clob");
		} else if (dbtype.equals("RAW")) {
			columnModel.setColumnType("clob");
		} else if (dbtype.equals("BYTEA")) {
			columnModel.setColumnType("clob");
		} else if (dbtype.equals("TINYBLOB")) {
			columnModel.setColumnType("clob");
		} else if (dbtype.equals("MEDIUMBLOB")) {
			columnModel.setColumnType("clob");
		} else if (dbtype.equals("LONGBLOB")) {
			columnModel.setColumnType("clob");
		} else if (dbtype.equals("IMAGE")) {
			columnModel.setColumnType("clob");
		} else if (dbtype.equals("OID")) {
			columnModel.setColumnType("clob");
		} else if (dbtype.equals("CLOB")) {
			columnModel.setColumnType("clob");
		} else if (dbtype.equals("TINYTEXT")) {
			columnModel.setColumnType("clob");
		} else if (dbtype.equals("TEXT")) {
			columnModel.setColumnType("clob");
		} else if (dbtype.equals("MEDIUMTEXT")) {
			columnModel.setColumnType("clob");
		} else if (dbtype.equals("LONGTEXT")) {
			columnModel.setColumnType("clob");
		} else if (dbtype.equals("NTEXT")) {
			columnModel.setColumnType("clob");
		} else if (dbtype.equals("NCLOB")) {
			columnModel.setColumnType("clob");
		} else {
			columnModel.setColumnType("varchar");
			columnModel.setCharLen(length);
		}
	}
}