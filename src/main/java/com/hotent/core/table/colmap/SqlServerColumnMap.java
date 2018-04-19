package com.hotent.core.table.colmap;

import com.hotent.core.table.ColumnModel;
import com.hotent.core.util.StringUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class SqlServerColumnMap implements RowMapper<ColumnModel> {
	public ColumnModel mapRow(ResultSet rs, int row) throws SQLException {
		ColumnModel column = new ColumnModel();
		String name = rs.getString("NAME");
		String is_nullable = rs.getString("IS_NULLABLE");
		String data_type = rs.getString("TYPENAME");
		String length = rs.getString("LENGTH");
		String precisions = rs.getString("PRECISION");
		String scale = rs.getString("SCALE");
		String tableName = rs.getString("TABLE_NAME");
		String comments = rs.getString("DESCRIPTION");
		int isPK = rs.getInt("IS_PK");
		int iLength = StringUtil.isEmpty(length) ? 0 : Integer.parseInt(length);
		int iPrecisions = StringUtil.isEmpty(precisions) ? 0 : Integer.parseInt(precisions);
		int iScale = StringUtil.isEmpty(scale) ? 0 : Integer.parseInt(scale);
		column.setName(name);
		boolean isNull = is_nullable.equals("1");
		column.setIsNull(isNull);
		column.setTableName(tableName);
		column.setComment(comments);
		column.setIsPk(isPK == 1);
		this.setType(data_type, iLength, iPrecisions, iScale, column);
		return column;
	}

	private void setType(String dbtype, int length, int precision, int scale, ColumnModel columnModel) {
		if (dbtype.equals("int")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(precision);
			columnModel.setDecimalLen(scale);
		} else if (dbtype.equals("bigint")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(precision);
			columnModel.setDecimalLen(scale);
		} else if (dbtype.equals("smallint")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(precision);
			columnModel.setDecimalLen(scale);
		} else if (dbtype.equals("tinyint")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(precision);
			columnModel.setDecimalLen(scale);
		} else if (dbtype.equals("bit")) {
			columnModel.setColumnType("number");
		} else if (dbtype.equals("decimal")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(precision);
			columnModel.setDecimalLen(scale);
		} else if (dbtype.equals("numeric")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(precision);
			columnModel.setDecimalLen(scale);
		} else if (dbtype.equals("real")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(precision);
		} else if (dbtype.equals("float")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(precision);
		} else if (dbtype.equals("varchar")) {
			columnModel.setColumnType("varchar");
			columnModel.setCharLen(length);
		} else if (dbtype.equals("char")) {
			columnModel.setColumnType("varchar");
			columnModel.setCharLen(length);
		} else if (dbtype.equals("varchar")) {
			columnModel.setColumnType("varchar");
			columnModel.setCharLen(length);
		} else if (dbtype.equals("nchar")) {
			columnModel.setColumnType("varchar");
			columnModel.setCharLen(length);
		} else if (dbtype.equals("nvarchar")) {
			columnModel.setColumnType("varchar");
			columnModel.setCharLen(length);
		} else if (dbtype.startsWith("datetime")) {
			columnModel.setColumnType("date");
		} else if (dbtype.endsWith("money")) {
			columnModel.setColumnType("number");
			columnModel.setIntLen(precision);
			columnModel.setDecimalLen(scale);
		} else if (dbtype.endsWith("smallmoney")) {
			columnModel.setColumnType("clob");
			columnModel.setIntLen(precision);
			columnModel.setDecimalLen(scale);
		} else if (dbtype.endsWith("text")) {
			columnModel.setColumnType("clob");
			columnModel.setCharLen(length);
		} else if (dbtype.endsWith("ntext")) {
			columnModel.setColumnType("clob");
			columnModel.setCharLen(length);
		}
	}
}