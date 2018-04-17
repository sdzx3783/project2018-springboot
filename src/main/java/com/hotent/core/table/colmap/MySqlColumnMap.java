package com.hotent.core.table.colmap;

import com.hotent.core.table.ColumnModel;
import com.hotent.core.table.impl.MySqlTableMeta;
import com.hotent.core.util.StringUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class MySqlColumnMap implements RowMapper<ColumnModel> {
	public ColumnModel mapRow(ResultSet rs, int row) throws SQLException {
		ColumnModel column = new ColumnModel();
		String name = rs.getString("column_name");
		String is_nullable = rs.getString("is_nullable");
		String data_type = rs.getString("data_type");
		String length = rs.getString("length");
		String precisions = rs.getString("precisions");
		String scale = rs.getString("scale");
		String column_key = rs.getString("column_key");
		String column_comment = rs.getString("column_comment");
		String table_name = rs.getString("table_name");
		column_comment = MySqlTableMeta.getComments(column_comment, name);

		int iLength;
		try {
			iLength = StringUtil.isEmpty(length) ? 0 : Integer.parseInt(length);
		} catch (NumberFormatException arg16) {
			iLength = 0;
		}

		int iPrecisions = StringUtil.isEmpty(precisions) ? 0 : Integer.parseInt(precisions);
		int iScale = StringUtil.isEmpty(scale) ? 0 : Integer.parseInt(scale);
		column.setName(name);
		column.setTableName(table_name);
		column.setComment(column_comment);
		if (StringUtil.isNotEmpty(column_key) && "PRI".equals(column_key)) {
			column.setIsPk(true);
		}

		boolean isNull = is_nullable.equals("YES");
		column.setIsNull(isNull);
		this.setType(data_type, iLength, iPrecisions, iScale, column);
		return column;
	}

	private void setType(String dbtype, int length, int precision, int scale, ColumnModel columnModel) {
		if (dbtype.equals("bigint")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(19);
			columnModel.setDecimalLen(0);
		} else if (dbtype.equals("int")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(10);
			columnModel.setDecimalLen(0);
		} else if (dbtype.equals("mediumint")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(7);
			columnModel.setDecimalLen(0);
		} else if (dbtype.equals("smallint")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(5);
			columnModel.setDecimalLen(0);
		} else if (dbtype.equals("tinyint")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(3);
			columnModel.setDecimalLen(0);
		} else if (dbtype.equals("decimal")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(precision - scale);
			columnModel.setDecimalLen(scale);
		} else if (dbtype.equals("double")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(18);
			columnModel.setDecimalLen(4);
		} else if (dbtype.equals("float")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(8);
			columnModel.setDecimalLen(4);
		} else if (dbtype.equals("varchar")) {
			columnModel.setColumnType("varchar");
			columnModel.setCharLen(length);
		} else if (dbtype.equals("char")) {
			columnModel.setColumnType("varchar");
			columnModel.setCharLen(length);
		} else if (dbtype.startsWith("date")) {
			columnModel.setColumnType("date");
		} else if (dbtype.startsWith("time")) {
			columnModel.setColumnType("date");
		} else if (dbtype.endsWith("text")) {
			columnModel.setColumnType("clob");
			columnModel.setCharLen('￿');
		} else if (dbtype.endsWith("blob")) {
			columnModel.setColumnType("clob");
			columnModel.setCharLen('￿');
		} else if (dbtype.endsWith("clob")) {
			columnModel.setColumnType("clob");
			columnModel.setCharLen('￿');
		}
	}
}