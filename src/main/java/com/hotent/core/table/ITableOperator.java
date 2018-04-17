package com.hotent.core.table;

import com.hotent.core.model.TableIndex;
import com.hotent.core.mybatis.Dialect;
import com.hotent.core.page.PageBean;
import com.hotent.core.table.ColumnModel;
import com.hotent.core.table.TableModel;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

public interface ITableOperator {
	String getDbType();

	void setJdbcTemplate(JdbcTemplate arg0);

	void createTable(TableModel arg0) throws SQLException;

	void dropTable(String arg0);

	void updateTableComment(String arg0, String arg1) throws SQLException;

	void addColumn(String arg0, ColumnModel arg1) throws SQLException;

	void updateColumn(String arg0, String arg1, ColumnModel arg2) throws SQLException;

	void addForeignKey(String arg0, String arg1, String arg2, String arg3);

	void createIndex(String arg0, String arg1);

	void dropForeignKey(String arg0, String arg1);

	void createIndex(TableIndex arg0) throws SQLException;

	void dropIndex(String arg0, String arg1);

	TableIndex getIndex(String arg0, String arg1);

	List<TableIndex> getIndexesByTable(String arg0);

	List<TableIndex> getIndexesByFuzzyMatching(String arg0, String arg1, Boolean arg2);

	List<TableIndex> getIndexesByFuzzyMatching(String arg0, String arg1, Boolean arg2, PageBean arg3);

	void rebuildIndex(String arg0, String arg1);

	List<String> getPKColumns(String arg0) throws SQLException;

	Map<String, List<String>> getPKColumns(List<String> arg0) throws SQLException;

	void setDialect(Dialect arg0);

	void setDbType(String arg0);

	boolean isTableExist(String arg0);
}