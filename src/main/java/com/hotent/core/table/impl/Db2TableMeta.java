package com.hotent.core.table.impl;

import com.hotent.core.db.datasource.JdbcTemplateUtil;
import com.hotent.core.page.PageBean;
import com.hotent.core.table.BaseTableMeta;
import com.hotent.core.table.ColumnModel;
import com.hotent.core.table.TableModel;
import com.hotent.core.table.colmap.DB2ColumnMap;
import com.hotent.core.table.impl.Db2TableMeta.1;
import com.hotent.core.table.impl.Db2TableMeta.2;
import com.hotent.core.util.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class Db2TableMeta extends BaseTableMeta {
	@Resource
	private JdbcTemplate jdbcTemplate;
	private final String SQL_GET_COLUMNS = "SELECT TABNAME TAB_NAME, COLNAME COL_NAME, TYPENAME COL_TYPE, REMARKS COL_COMMENT, NULLS IS_NULLABLE, LENGTH LENGTH, SCALE SCALE, KEYSEQ  FROM  SYSCAT.COLUMNS WHERE  TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) AND UPPER(TABNAME) = UPPER(\'%s\') ";
	private final String SQL_GET_COLUMNS_BATCH = "SELECT TABNAME TAB_NAME, COLNAME COL_NAME, TYPENAME COL_TYPE, REMARKS COL_COMMENT, NULLS IS_NULLABLE, LENGTH LENGTH, SCALE SCALE, KEYSEQ  FROM  SYSCAT.COLUMNS WHERE  TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) ";
	private final String SQL_GET_TABLE_COMMENT = "SELECT TABNAME TAB_NAME, REMARKS TAB_COMMENT FROM SYSCAT.TABLES WHERE TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) AND UPPER(TABNAME) =UPPER(\'%s\')";
	private final String SQL_GET_ALL_TABLE_COMMENT = "SELECT TABNAME TAB_NAME, REMARKS TAB_COMMENT FROM SYSCAT.TABLES WHERE TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) AND UPPER(TABSCHEMA) = (SELECT UPPER(CURRENT SCHEMA) FROM SYSIBM.DUAL)";
	RowMapper<TableModel> tableModelRowMapper = new 1(this);
	RowMapper<Map<String, String>> tableMapRowMapper = new 2(this);

	public TableModel getTableByName(String tableName) {
		TableModel model = this.getTableModel(tableName);
		if (model == null) {
			return null;
		} else {
			List columnList = this.getColumnsByTableName(tableName);
			model.setColumnList(columnList);
			return model;
		}
	}

	private List<ColumnModel> getColumnsByTableName(String tableName) {
		String sql = String.format(
				"SELECT TABNAME TAB_NAME, COLNAME COL_NAME, TYPENAME COL_TYPE, REMARKS COL_COMMENT, NULLS IS_NULLABLE, LENGTH LENGTH, SCALE SCALE, KEYSEQ  FROM  SYSCAT.COLUMNS WHERE  TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) AND UPPER(TABNAME) = UPPER(\'%s\') ",
				new Object[]{tableName});
		HashMap map = new HashMap();
		List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, map,
				new DB2ColumnMap());
		return list;
	}

	private Map<String, List<ColumnModel>> getColumnsByTableName(List<String> tableNames) {
		String sql = "SELECT TABNAME TAB_NAME, COLNAME COL_NAME, TYPENAME COL_TYPE, REMARKS COL_COMMENT, NULLS IS_NULLABLE, LENGTH LENGTH, SCALE SCALE, KEYSEQ  FROM  SYSCAT.COLUMNS WHERE  TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) ";
		HashMap map = new HashMap();
		if (tableNames != null && tableNames.size() == 0) {
			return map;
		} else {
			StringBuffer columnModels = new StringBuffer();
			Iterator i$ = tableNames.iterator();

			while (i$.hasNext()) {
				String columnModel = (String) i$.next();
				columnModels.append("\'" + columnModel + "\',");
			}

			columnModels.deleteCharAt(columnModels.length() - 1);
			sql = sql + " AND UPPER(TABNAME) IN (" + columnModels.toString().toUpperCase() + ") ";
			List columnModels1 = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql,
					new HashMap(), new DB2ColumnMap());
			i$ = columnModels1.iterator();

			while (i$.hasNext()) {
				ColumnModel columnModel1 = (ColumnModel) i$.next();
				String tableName = columnModel1.getTableName();
				if (map.containsKey(tableName)) {
					((List) map.get(tableName)).add(columnModel1);
				} else {
					ArrayList cols = new ArrayList();
					cols.add(columnModel1);
					map.put(tableName, cols);
				}
			}

			return map;
		}
	}

	private TableModel getTableModel(String tableName) {
		String sql = String.format(
				"SELECT TABNAME TAB_NAME, REMARKS TAB_COMMENT FROM SYSCAT.TABLES WHERE TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) AND UPPER(TABNAME) =UPPER(\'%s\')",
				new Object[]{tableName});
		TableModel tableModel = (TableModel) this.jdbcTemplate.queryForObject(sql, (Object[]) null,
				this.tableModelRowMapper);
		return tableModel;
	}

	public Map<String, String> getTablesByName(String tableName) {
		String sql = "SELECT TABNAME TAB_NAME, REMARKS TAB_COMMENT FROM SYSCAT.TABLES WHERE TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) AND UPPER(TABSCHEMA) = (SELECT UPPER(CURRENT SCHEMA) FROM SYSIBM.DUAL)";
		if (StringUtil.isNotEmpty(tableName)) {
			sql = sql + " AND UPPER(TABNAME) LIKE UPPER(\'%" + tableName + "%\')";
		}

		HashMap parameter = new HashMap();
		List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, parameter,
				this.tableMapRowMapper);
		LinkedHashMap map = new LinkedHashMap();

		for (int i = 0; i < list.size(); ++i) {
			Map tmp = (Map) list.get(i);
			String name = (String) tmp.get("name");
			String comments = (String) tmp.get("comments");
			map.put(name, comments);
		}

		return map;
	}

	public Map<String, String> getTablesByName(List<String> tableNames) {
		HashMap map = new HashMap();
		String sql = "SELECT TABNAME TAB_NAME, REMARKS TAB_COMMENT FROM SYSCAT.TABLES WHERE TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) AND UPPER(TABSCHEMA) = (SELECT UPPER(CURRENT SCHEMA) FROM SYSIBM.DUAL)";
		if (tableNames != null && tableNames.size() != 0) {
			StringBuffer parameter = new StringBuffer();
			Iterator list = tableNames.iterator();

			while (list.hasNext()) {
				String i = (String) list.next();
				parameter.append("\'" + i + "\',");
			}

			parameter.deleteCharAt(parameter.length() - 1);
			sql = sql + " AND UPPER(TABNAME) IN (" + parameter.toString().toUpperCase() + ") ";
			HashMap arg9 = new HashMap();
			List arg10 = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, arg9,
					this.tableMapRowMapper);

			for (int arg11 = 0; arg11 < arg10.size(); ++arg11) {
				Map tmp = (Map) arg10.get(arg11);
				String name = (String) tmp.get("name");
				String comments = (String) tmp.get("comments");
				map.put(name, comments);
			}

			return map;
		} else {
			return map;
		}
	}

	public List<TableModel> getTablesByName(String tableName, PageBean pageBean) throws Exception {
		String sql = "SELECT TABNAME TAB_NAME, REMARKS TAB_COMMENT FROM SYSCAT.TABLES WHERE TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) AND UPPER(TABSCHEMA) = (SELECT UPPER(CURRENT SCHEMA) FROM SYSIBM.DUAL)";
		if (StringUtil.isNotEmpty(tableName)) {
			sql = sql + " AND UPPER(TABNAME) LIKE \'%" + tableName.toUpperCase() + "%\'";
		}

		List tableModels = this.getForList(sql, pageBean, this.tableModelRowMapper, "db2");
		ArrayList tableNames = new ArrayList();
		Iterator tableColumnsMap = tableModels.iterator();

		while (tableColumnsMap.hasNext()) {
			TableModel i$ = (TableModel) tableColumnsMap.next();
			tableNames.add(i$.getName());
		}

		Map tableColumnsMap1 = this.getColumnsByTableName((List) tableNames);
		Iterator i$2 = tableColumnsMap1.entrySet().iterator();

		while (i$2.hasNext()) {
			Entry entry = (Entry) i$2.next();
			Iterator i$1 = tableModels.iterator();

			while (i$1.hasNext()) {
				TableModel model = (TableModel) i$1.next();
				if (model.getName().equalsIgnoreCase((String) entry.getKey())) {
					model.setColumnList((List) entry.getValue());
				}
			}
		}

		return tableModels;
	}

	public String getAllTableSql() {
		return "SELECT TABNAME TAB_NAME, REMARKS TAB_COMMENT FROM SYSCAT.TABLES WHERE TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) AND UPPER(TABSCHEMA) = (SELECT UPPER(CURRENT SCHEMA) FROM SYSIBM.DUAL)";
	}
}