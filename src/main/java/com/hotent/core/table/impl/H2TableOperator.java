package com.hotent.core.table.impl;

import com.hotent.core.model.TableIndex;
import com.hotent.core.page.PageBean;
import com.hotent.core.table.AbstractTableOperator;
import com.hotent.core.table.ColumnModel;
import com.hotent.core.table.TableModel;
import com.hotent.core.table.impl.H2TableOperator.1;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;

public class H2TableOperator extends AbstractTableOperator {
	protected int BATCHSIZE = 100;
	private final String SQL_GET_ALL_INDEX = "SELECT A.TABLE_NAME  , A.INDEX_NAME  , A.NON_UNIQUE  , A.COLUMN_NAME  , A.INDEX_TYPE_NAME  , A.REMARKS , A.SQL FROM INFORMATION_SCHEMA.INDEXES  A WHERE 1=1 ";
	private RowMapper<TableIndex> indexRowMapper = new 1(this);

	public void createTable(TableModel model) throws SQLException {
		List columnList = model.getColumnList();
		StringBuffer sb = new StringBuffer();
		String pkColumn = null;
		ArrayList columnCommentList = new ArrayList();
		sb.append("CREATE TABLE " + model.getName() + " (\n");

		for (int i$ = 0; i$ < columnList.size(); ++i$) {
			ColumnModel columnComment = (ColumnModel) columnList.get(i$);
			sb.append("    ").append(columnComment.getName()).append("    ");
			sb.append(this.getColumnType(columnComment.getColumnType(), columnComment.getCharLen(),
					columnComment.getIntLen(), columnComment.getDecimalLen()));
			sb.append(" ");
			if (columnComment.getIsPk()) {
				if (pkColumn == null) {
					pkColumn = columnComment.getName();
				} else {
					pkColumn = pkColumn + "," + columnComment.getName();
				}
			}

			String defVal = this.getDefaultValueSQL(columnComment);
			if (StringUtil.isNotEmpty(defVal)) {
				sb.append(defVal);
			}

			if (!columnComment.getIsNull() || columnComment.getIsPk()) {
				sb.append(" NOT NULL ");
			}

			if (columnComment.getComment() != null && columnComment.getComment().length() > 0) {
				columnCommentList.add("COMMENT ON COLUMN " + model.getName() + "." + columnComment.getName() + " IS \'"
						+ columnComment.getComment() + "\'\n");
			}

			sb.append(",\n");
		}

		if (pkColumn != null) {
			sb.append("    CONSTRAINT PK_").append(model.getName()).append(" PRIMARY KEY (").append(pkColumn)
					.append(")");
		} else {
			sb = new StringBuffer(sb.substring(0, sb.length() - ",\n".length()));
		}

		sb.append("\n)");
		this.jdbcTemplate.execute(sb.toString());
		if (model.getComment() != null && model.getComment().length() > 0) {
			this.jdbcTemplate.execute("COMMENT ON TABLE " + model.getName() + " IS \'" + model.getComment() + "\'\n");
		}

		Iterator arg8 = columnCommentList.iterator();

		while (arg8.hasNext()) {
			String arg9 = (String) arg8.next();
			this.jdbcTemplate.execute(arg9);
		}

	}

	public void updateTableComment(String tableName, String comment) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("COMMENT ON TABLE ");
		sb.append(tableName);
		sb.append(" IS \'");
		sb.append(comment);
		sb.append("\'\n");
		this.jdbcTemplate.execute(sb.toString());
	}

	public void addColumn(String tableName, ColumnModel model) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("ALTER TABLE ").append(tableName);
		sb.append(" ADD ");
		sb.append(model.getName()).append(" ");
		sb.append(this.getColumnType(model.getColumnType(), model.getCharLen(), model.getIntLen(),
				model.getDecimalLen()));
		String defVal = this.getDefaultValueSQL(model);
		if (StringUtil.isNotEmpty(defVal)) {
			sb.append(defVal);
		}

		sb.append("\n");
		this.jdbcTemplate.execute(sb.toString());
		if (model.getComment() != null && model.getComment().length() > 0) {
			this.jdbcTemplate.execute(
					"COMMENT ON COLUMN " + tableName + "." + model.getName() + " IS \'" + model.getComment() + "\'");
		}

	}

	public void updateColumn(String tableName, String columnName, ColumnModel model) throws SQLException {
		StringBuffer sb;
		if (!columnName.equals(model.getName())) {
			sb = (new StringBuffer("ALTER TABLE ")).append(tableName);
			sb.append(" ALTER COLUMN ").append(columnName).append(" RENAME TO ").append(model.getName());
			this.jdbcTemplate.execute(sb.toString());
		}

		sb = new StringBuffer();
		sb.append("ALTER TABLE ").append(tableName);
		sb.append(" ALTER COLUMN ").append(model.getName());
		sb.append(this.getColumnType(model.getColumnType(), model.getCharLen(), model.getIntLen(),
				model.getDecimalLen()));
		if (!model.getIsNull()) {
			sb.append(" NOT NULL ");
		}

		this.jdbcTemplate.execute(sb.toString());
		if (model.getComment() != null && model.getComment().length() > 0) {
			this.jdbcTemplate.execute(
					"COMMENT ON COLUMN " + tableName + "." + model.getName() + " IS\'" + model.getComment() + "\'");
		}

	}

	public void dropTable(String tableName) {
		String selSql = "SELECT COUNT(*) AMOUNT FROM INFORMATION_SCHEMA.TABLES  WHERE TABLE_SCHEMA = SCHEMA() AND TABLE_NAME = UPPER(\'"
				+ tableName + "\')";
		int rtn = this.jdbcTemplate.queryForInt(selSql);
		if (rtn > 0) {
			String sql = "DROP TABLE " + tableName;
			this.jdbcTemplate.execute(sql);
		}

	}

	public void addForeignKey(String pkTableName, String fkTableName, String pkField, String fkField) {
		String sql = " ALTER TABLE " + fkTableName + " ADD CONSTRAINT FK_" + fkTableName + " FOREIGN KEY (" + fkField
				+ ") REFERENCES " + pkTableName + " (" + pkField + ") ON DELETE CASCADE";
		this.jdbcTemplate.execute(sql);
	}

	public void dropForeignKey(String tableName, String keyName) {
		String sql = "ALTER   TABLE   " + tableName + "   DROP   CONSTRAINT  " + keyName;
		this.jdbcTemplate.execute(sql);
	}

	public void dropIndex(String tableName, String indexName) {
		String sql = "DROP INDEX " + indexName;
		this.jdbcTemplate.execute(sql);
	}

	public TableIndex getIndex(String tableName, String indexName) {
		String sql = "SELECT A.TABLE_NAME  , A.INDEX_NAME  , A.NON_UNIQUE  , A.COLUMN_NAME  , A.INDEX_TYPE_NAME  , A.REMARKS , A.SQL FROM INFORMATION_SCHEMA.INDEXES  A WHERE 1=1 ";
		sql = sql + " AND A.INDEX_NAME = \'" + indexName + "\' ";
		List indexes = this.jdbcTemplate.query(sql, this.indexRowMapper);
		List indexList = this.mergeIndex(indexes);
		if (BeanUtils.isEmpty(indexList)) {
			return null;
		} else {
			TableIndex index = (TableIndex) indexList.get(0);
			return index;
		}
	}

	public List<TableIndex> getIndexesByTable(String tableName) {
		String sql = "SELECT A.TABLE_NAME  , A.INDEX_NAME  , A.NON_UNIQUE  , A.COLUMN_NAME  , A.INDEX_TYPE_NAME  , A.REMARKS , A.SQL FROM INFORMATION_SCHEMA.INDEXES  A WHERE 1=1 ";
		sql = sql + " AND UPPER(A.TABLE_NAME) = UPPER(\'" + tableName + "\')";
		List indexes = this.jdbcTemplate.query(sql, this.indexRowMapper);
		List indexList = this.mergeIndex(indexes);
		Iterator i$ = indexList.iterator();

		while (i$.hasNext()) {
			TableIndex index = (TableIndex) i$.next();
			index.setIndexDdl(this.generateIndexDDL(index));
		}

		return indexList;
	}

	public List<TableIndex> getIndexesByFuzzyMatching(String tableName, String indexName, Boolean getDDL) {
		return this.getIndexesByFuzzyMatching(tableName, indexName, getDDL, (PageBean) null);
	}

	public List<TableIndex> getIndexesByFuzzyMatching(String tableName, String indexName, Boolean getDDL,
			PageBean pageBean) {
		String sql = "SELECT A.TABLE_NAME  , A.INDEX_NAME  , A.NON_UNIQUE  , A.COLUMN_NAME  , A.INDEX_TYPE_NAME  , A.REMARKS , A.SQL FROM INFORMATION_SCHEMA.INDEXES  A WHERE 1=1 ";
		if (!StringUtil.isEmpty(tableName)) {
			sql = sql + " AND UPPER(A.TABLE_NAME) LIKE UPPER(\'%" + tableName + "%\')";
		}

		if (!StringUtil.isEmpty(indexName)) {
			sql = sql + " AND UPPER(A.INDEX_NAME) like UPPER(\'%" + indexName + "%\')";
		}

		if (pageBean != null) {
			int indexes = pageBean.getCurrentPage();
			int indexList = pageBean.getPageSize();
			int i$ = (indexes - 1) * indexList;
			String index = this.dialect.getCountSql(sql);
			int total = this.jdbcTemplate.queryForInt(index);
			sql = this.dialect.getLimitString(sql, i$, indexList);
			pageBean.setTotalCount(total);
		}

		this.logger.debug(sql);
		List indexes1 = this.jdbcTemplate.query(sql, this.indexRowMapper);
		List indexList1 = this.mergeIndex(indexes1);
		Iterator i$1 = indexList1.iterator();

		while (i$1.hasNext()) {
			TableIndex index1 = (TableIndex) i$1.next();
			index1.setIndexDdl(this.generateIndexDDL(index1));
		}

		return indexList1;
	}

	public void rebuildIndex(String tableName, String indexName) {
		throw new UnsupportedOperationException("h2 不支持通过JDBC进行索引重建！");
	}

	public void createIndex(TableIndex index) throws SQLException {
		String sql = this.generateIndexDDL(index);
		this.jdbcTemplate.execute(sql);
	}

	private List<TableIndex> mergeIndex(List<TableIndex> indexes) {
		ArrayList indexList = new ArrayList();
		Iterator i$ = indexes.iterator();

		while (i$.hasNext()) {
			TableIndex index = (TableIndex) i$.next();
			boolean found = false;
			Iterator i$1 = indexList.iterator();

			while (i$1.hasNext()) {
				TableIndex index1 = (TableIndex) i$1.next();
				if (index.getIndexName().equals(index1.getIndexName())
						&& index.getIndexTable().equals(index1.getIndexTable())) {
					index1.getIndexFields().add(index.getIndexFields().get(0));
					found = true;
					break;
				}
			}

			if (!found) {
				indexList.add(index);
			}
		}

		return indexList;
	}

	private String generateIndexDDL(TableIndex index) {
		StringBuffer sql = new StringBuffer();
		sql.append("CREATE ");
		sql.append("INDEX ");
		sql.append(index.getIndexName());
		sql.append(" ON ");
		sql.append(index.getIndexTable());
		sql.append("(");
		Iterator i$ = index.getIndexFields().iterator();

		while (i$.hasNext()) {
			String field = (String) i$.next();
			sql.append(field);
			sql.append(",");
		}

		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		return sql.toString();
	}

	private String getColumnType(String columnType, int charLen, int intLen, int decimalLen) {
		return "varchar".equals(columnType)
				? "VARCHAR(" + charLen + ')'
				: ("number".equals(columnType)
						? "DECIMAL(" + (intLen + decimalLen) + "," + decimalLen + ")"
						: ("date".equals(columnType)
								? "DATE"
								: ("int".equals(columnType)
										? (intLen > 0 && intLen <= 5
												? "SMALLINT"
												: (intLen > 5 && intLen <= 10 ? "INTEGER" : "BIGINT"))
										: ("clob".equals(columnType) ? "CLOB" : "VARCHAR(50)"))));
	}

	private String getDefaultValueSQL(ColumnModel cm) {
		String sql = null;
		if (StringUtil.isNotEmpty(cm.getDefaultValue())) {
			if ("int".equalsIgnoreCase(cm.getColumnType())) {
				sql = " DEFAULT " + cm.getDefaultValue() + " ";
			} else if ("number".equalsIgnoreCase(cm.getColumnType())) {
				sql = " DEFAULT " + cm.getDefaultValue() + " ";
			} else if ("varchar".equalsIgnoreCase(cm.getColumnType())) {
				sql = " DEFAULT \'" + cm.getDefaultValue() + "\' ";
			} else if ("clob".equalsIgnoreCase(cm.getColumnType())) {
				sql = " DEFAULT \'" + cm.getDefaultValue() + "\' ";
			} else if ("date".equalsIgnoreCase(cm.getColumnType())) {
				sql = " DEFAULT " + cm.getDefaultValue() + " ";
			} else {
				sql = " DEFAULT " + cm.getDefaultValue() + " ";
			}
		}

		return sql;
	}

	public boolean isTableExist(String tableName) {
		String selSql = "SELECT COUNT(*) AMOUNT FROM INFORMATION_SCHEMA.TABLES  WHERE TABLE_SCHEMA = SCHEMA() AND TABLE_NAME = UPPER(\'"
				+ tableName + "\')";
		int rtn = this.jdbcTemplate.queryForInt(selSql);
		return rtn > 0;
	}
}