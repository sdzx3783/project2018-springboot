package com.hotent.core.table.impl;

import com.hotent.core.db.datasource.JdbcTemplateUtil;
import com.hotent.core.page.PageBean;
import com.hotent.core.table.BaseDbView;
import com.hotent.core.table.ColumnModel;
import com.hotent.core.table.IDbView;
import com.hotent.core.table.TableModel;
import com.hotent.core.table.colmap.DB2ColumnMap;
import com.hotent.core.table.impl.Db2DbView.1;
import com.hotent.core.table.impl.Db2DbView.2;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class Db2DbView extends BaseDbView implements IDbView {
	@Resource
	private JdbcTemplate jdbcTemplate;
	private static final String SQL_GET_ALL_VIEW = "SELECT VIEWNAME FROM SYSCAT.VIEWS WHERE  VIEWSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) ";
	private static final String SQL_GET_COLUMNS = "SELECT TABNAME TAB_NAME, COLNAME COL_NAME, TYPENAME COL_TYPE, REMARKS COL_COMMENT, NULLS IS_NULLABLE, LENGTH LENGTH, SCALE SCALE, KEYSEQ  FROM  SYSCAT.COLUMNS WHERE  TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) AND UPPER(TABNAME) = UPPER(\'%s\') ";
	private final String SQL_GET_COLUMNS_BATCH = "SELECT TABNAME TAB_NAME, COLNAME COL_NAME, TYPENAME COL_TYPE, REMARKS COL_COMMENT, NULLS IS_NULLABLE, LENGTH LENGTH, SCALE SCALE, KEYSEQ  FROM  SYSCAT.COLUMNS WHERE  TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) ";
	RowMapper<TableModel> tableModelRowMapper = new 2(this);

	public List<String> getViews(String viewName) throws SQLException {
		String sql = "SELECT VIEWNAME FROM SYSCAT.VIEWS WHERE  VIEWSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) ";
		if (StringUtil.isNotEmpty(viewName)) {
			sql = sql + " AND UPPER(VIEWNAME) like \'" + viewName.toUpperCase() + "%\'";
		}

		return this.jdbcTemplate.queryForList(sql, String.class);
	}

	public String getType(String type) {
		String dbtype = type.toLowerCase();
		return dbtype.endsWith("bigint")
				? "number"
				: (dbtype.endsWith("blob")
						? "clob"
						: (dbtype.endsWith("character")
								? "varchar"
								: (dbtype.endsWith("clob")
										? "clob"
										: (dbtype.endsWith("date")
												? "date"
												: (dbtype.endsWith("dbclob")
														? "clob"
														: (dbtype.endsWith("decimal")
																? "number"
																: (dbtype.endsWith("double")
																		? "number"
																		: (dbtype.endsWith("graphic")
																				? "clob"
																				: (dbtype.endsWith("integer")
																						? "number"
																						: (dbtype.endsWith(
																								"long varchar")
																										? "varchar"
																										: (dbtype
																												.endsWith(
																														"long vargraphic")
																																? "clob"
																																: (dbtype
																																		.endsWith(
																																				"real")
																																						? "number"
																																						: (dbtype
																																								.endsWith(
																																										"smallint")
																																												? "number"
																																												: (dbtype
																																														.endsWith(
																																																"time")
																																																		? "date"
																																																		: (dbtype
																																																				.endsWith(
																																																						"timestamp")
																																																								? "date"
																																																								: (dbtype
																																																										.endsWith(
																																																												"varchar")
																																																														? "varchar"
																																																														: (dbtype
																																																																.endsWith(
																																																																		"vargraphic")
																																																																				? "clob"
																																																																				: (dbtype
																																																																						.endsWith(
																																																																								"xml")
																																																																										? "clob"
																																																																										: "varchar"))))))))))))))))));
	}

	public List<String> getViews(String viewName, PageBean pageBean) throws Exception {
      String sql = "SELECT VIEWNAME FROM SYSCAT.VIEWS WHERE  VIEWSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) ";
      if(StringUtil.isNotEmpty(viewName)) {
         sql = sql + " AND UPPER(VIEWNAME) LIKE \'%" + viewName.toUpperCase() + "%\'";
      }

      1 rowMapper = new 1(this);
      return this.getForList(sql, pageBean, rowMapper, "db2");
   }

	public TableModel getModelByViewName(String viewName) throws SQLException {
		String sql = "SELECT VIEWNAME FROM SYSCAT.VIEWS WHERE  VIEWSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) ";
		sql = sql + " AND UPPER(VIEWNAME) = \'" + viewName.toUpperCase() + "\'";
		TableModel tableModel = null;
		List tableModels = this.jdbcTemplate.query(sql, this.tableModelRowMapper);
		if (BeanUtils.isEmpty(tableModels)) {
			return null;
		} else {
			tableModel = (TableModel) tableModels.get(0);
			List columnList = this.getColumnsByTableName(viewName);
			tableModel.setColumnList(columnList);
			return tableModel;
		}
	}

	public List<TableModel> getViewsByName(String viewName, PageBean pageBean) throws Exception {
		String sql = "SELECT VIEWNAME FROM SYSCAT.VIEWS WHERE  VIEWSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) ";
		if (StringUtil.isNotEmpty(viewName)) {
			sql = sql + " AND UPPER(VIEWNAME) LIKE \'%" + viewName.toUpperCase() + "%\'";
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

	public void createOrRep(String viewName, String sql) throws Exception {
	}
}