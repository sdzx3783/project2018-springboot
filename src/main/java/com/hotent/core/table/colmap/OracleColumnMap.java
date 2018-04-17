package com.hotent.core.table.colmap;

import com.hotent.core.table.ColumnModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class OracleColumnMap implements RowMapper<ColumnModel> {
	public ColumnModel mapRow(ResultSet rs, int row) throws SQLException {
		ColumnModel column = new ColumnModel();
		String name = rs.getString("NAME");
		String typeName = rs.getString("TYPENAME");
		int length = rs.getInt("LENGTH");
		int precision = rs.getInt("PRECISION");
		int scale = rs.getInt("SCALE");
		boolean isNull = rs.getString("NULLABLE").equals("Y");
		String comments = rs.getString("DESCRIPTION");
		String tableName = rs.getString("TABLE_NAME");
		int isPK = rs.getInt("IS_PK");
		column.setName(name);
		column.setComment(comments);
		column.setIsNull(isNull);
		column.setTableName(tableName);
		column.setIsPk(isPK == 1);
		this.setType(typeName, length, precision, scale, column);
		return column;
	}

	private void setType(String dbtype, int length, int precision, int scale, ColumnModel column) {
		if (dbtype.indexOf("CHAR") > -1) {
			column.setColumnType("varchar");
			column.setCharLen(length);
		} else if (dbtype.equals("NUMBER")) {
			column.setColumnType("number");
			column.setIntLen(precision - scale);
			column.setDecimalLen(scale);
			int i = precision - scale;
			if (i == 0 && scale == 0) {
				column.setIntLen(38);
			}

		} else if (dbtype.equals("INTEGER")) {
			column.setColumnType("number");
			column.setIntLen(10);
			column.setDecimalLen(0);
		} else if (!dbtype.equals("DATE") && dbtype.indexOf("TIMESTAMP") == -1) {
			if (dbtype.equals("CLOB")) {
				column.setColumnType("clob");
			}
		} else {
			column.setColumnType("date");
		}
	}
}