package com.hotent.core.table;

import com.hotent.core.model.TableIndex;
import com.hotent.core.mybatis.Dialect;
import com.hotent.core.page.PageBean;
import com.hotent.core.table.ColumnModel;
import com.hotent.core.table.ITableOperator;
import com.hotent.core.table.TableModel;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class AbstractTableOperator implements ITableOperator {
	protected String dbType;
	protected JdbcTemplate jdbcTemplate;
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	protected Dialect dialect;

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDbType() {
		return this.dbType;
	}

	public void setJdbcTemplate(JdbcTemplate template) {
		this.jdbcTemplate = template;
	}

	public void createTable(TableModel model) throws SQLException {
		throw new UnsupportedOperationException("Current Implement not supported");
	}

	public void dropTable(String tableName) {
		throw new UnsupportedOperationException("Current Implement not supported");
	}

	public void updateTableComment(String tableName, String comment) throws SQLException {
		throw new UnsupportedOperationException("Current Implement not supported");
	}

	public void addColumn(String tableName, ColumnModel model) throws SQLException {
		throw new UnsupportedOperationException("Current Implement not supported");
	}

	public void updateColumn(String tableName, String columnName, ColumnModel model) throws SQLException {
		throw new UnsupportedOperationException("Current Implement not supported");
	}

	public void addForeignKey(String pkTableName, String fkTableName, String pkField, String fkField) {
		throw new UnsupportedOperationException("Current Implement not supported");
	}

	public void dropForeignKey(String tableName, String keyName) {
		throw new UnsupportedOperationException("Current Implement not supported");
	}

	public void createIndex(TableIndex index) throws SQLException {
		throw new UnsupportedOperationException("Current Implement not supported");
	}

	public void dropIndex(String tableName, String indexName) {
		throw new UnsupportedOperationException("Current Implement not supported");
	}

	public TableIndex getIndex(String tableName, String indexName) {
		throw new UnsupportedOperationException("Current Implement not supported");
	}

	public List<TableIndex> getIndexesByTable(String tableName) {
		throw new UnsupportedOperationException("Current Implement not supported");
	}

	public List<TableIndex> getIndexesByFuzzyMatching(String tableName, String indexName, Boolean getDDL) {
		throw new UnsupportedOperationException("Current Implement not supported");
	}

	public List<TableIndex> getIndexesByFuzzyMatching(String tableName, String indexName, Boolean getDDL,
			PageBean pageBean) {
		throw new UnsupportedOperationException("Current Implement not supported");
	}

	public void rebuildIndex(String tableName, String indexName) {
		throw new UnsupportedOperationException("Current Implement not supported");
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	public List<String> getPKColumns(String tableName) throws SQLException {
		throw new UnsupportedOperationException("Current Implement not supported");
	}

	public Map<String, List<String>> getPKColumns(List<String> tableNames) throws SQLException {
		throw new UnsupportedOperationException("Current Implement not supported");
	}

	public void createIndex(String tableName, String fieldName) {
		String regex = "(?im)" + TableModel.CUSTOMER_TABLE_PREFIX;
		String shortTableName = tableName.replaceFirst(regex, "");
		String sqlIndex = "create index idx_" + shortTableName + "_" + fieldName + " on " + tableName + "(" + fieldName
				+ ")";
		this.jdbcTemplate.execute(sqlIndex);
	}

	public boolean isTableExist(String tableName) {
		return true;
	}
}