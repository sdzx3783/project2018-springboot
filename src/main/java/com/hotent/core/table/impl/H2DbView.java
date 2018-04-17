package com.hotent.core.table.impl;

import com.hotent.core.db.datasource.JdbcTemplateUtil;
import com.hotent.core.page.PageBean;
import com.hotent.core.table.BaseDbView;
import com.hotent.core.table.ColumnModel;
import com.hotent.core.table.IDbView;
import com.hotent.core.table.TableModel;
import com.hotent.core.table.colmap.H2ColumnMap;
import com.hotent.core.table.impl.H2DbView.1;
import com.hotent.core.table.impl.H2DbView.2;
import com.hotent.core.table.impl.H2DbView.3;
import com.hotent.core.table.impl.H2DbView.4;
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
public class H2DbView extends BaseDbView implements IDbView {
	@Resource
	private JdbcTemplate jdbcTemplate;
	private static final String SQL_GET_ALL_VIEW = "SELECT TABLE_NAME ,REMARKS  FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = \'VIEW\' AND TABLE_SCHEMA=SCHEMA() ";
	private static final String SQL_GET_COLUMNS = "SELECT A.TABLE_NAME, A.COLUMN_NAME, A.IS_NULLABLE, A.DATA_TYPE, A.CHARACTER_OCTET_LENGTH LENGTH, A.NUMERIC_PRECISION PRECISIONS, A.NUMERIC_SCALE SCALE, B.COLUMN_LIST, A.REMARKS FROM INFORMATION_SCHEMA.COLUMNS A  JOIN INFORMATION_SCHEMA.CONSTRAINTS B ON A.TABLE_NAME=B.TABLE_NAME WHERE  A.TABLE_SCHEMA=SCHEMA() AND UPPER(A.TABLE_NAME)=\'%s\' ";
	static final String SQL_GET_COLUMNS_BATCH = "SELECT A.TABLE_NAME, A.COLUMN_NAME, A.IS_NULLABLE, A.DATA_TYPE, A.CHARACTER_OCTET_LENGTH LENGTH, A.NUMERIC_PRECISION PRECISIONS, A.NUMERIC_SCALE SCALE, B.COLUMN_LIST, A.REMARKS FROM INFORMATION_SCHEMA.COLUMNS A  JOIN INFORMATION_SCHEMA.CONSTRAINTS B ON A.TABLE_NAME=B.TABLE_NAME WHERE  A.TABLE_SCHEMA=SCHEMA() ";
	RowMapper<TableModel> tableRowMapper = new 4(this);

	public List<String> getViews(String viewName) throws SQLException {
      String sql = "SELECT TABLE_NAME ,REMARKS  FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = \'VIEW\' AND TABLE_SCHEMA=SCHEMA() ";
      if(StringUtil.isNotEmpty(viewName)) {
         sql = sql + " AND TABLE_NAME LIKE \'%" + viewName + "%\'";
      }

      1 rowMapper = new 1(this);
      return this.jdbcTemplate.query(sql, rowMapper);
   }

	public List<String> getViews(String viewName, PageBean pageBean) throws Exception {
      String sql = "SELECT TABLE_NAME ,REMARKS  FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = \'VIEW\' AND TABLE_SCHEMA=SCHEMA() ";
      if(StringUtil.isNotEmpty(viewName)) {
         sql = sql + " AND TABLE_NAME LIKE \'%" + viewName + "%\'";
      }

      2 rowMapper = new 2(this);
      return this.getForList(sql, pageBean, rowMapper, "h2");
   }

	public String getType(String dbtype) {
		dbtype = dbtype.toUpperCase();
		return dbtype.equals("BIGINT")
				? "number"
				: (dbtype.equals("INT8")
						? "number"
						: (dbtype.equals("INT")
								? "number"
								: (dbtype.equals("INTEGER")
										? "number"
										: (dbtype.equals("MEDIUMINT")
												? "number"
												: (dbtype.equals("INT4")
														? "number"
														: (dbtype.equals("SIGNED")
																? "number"
																: (dbtype.equals("TINYINT")
																		? "number"
																		: (dbtype.equals("SMALLINT")
																				? "number"
																				: (dbtype.equals("INT2")
																						? "number"
																						: (dbtype.equals("YEAR")
																								? "number"
																								: (dbtype.equals(
																										"IDENTITY")
																												? "number"
																												: (dbtype
																														.equals("DECIMAL")
																																? "number"
																																: (dbtype
																																		.equals("BOOLEAN")
																																				? "varchar"
																																				: (dbtype
																																						.equals("BIT")
																																								? "varchar"
																																								: (dbtype
																																										.equals("BOOL")
																																												? "varchar"
																																												: (dbtype
																																														.equals("SIGNED")
																																																? "number"
																																																: (dbtype
																																																		.equals("DOUBLE")
																																																				? "number"
																																																				: (dbtype
																																																						.equals("REAL")
																																																								? "number"
																																																								: (dbtype
																																																										.equals("TIME")
																																																												? "date"
																																																												: (dbtype
																																																														.equals("TIMESTAMP")
																																																																? "date"
																																																																: (dbtype
																																																																		.equals("BINARY")
																																																																				? "clob"
																																																																				: (dbtype
																																																																						.equals("BLOB")
																																																																								? "clob"
																																																																								: (dbtype
																																																																										.equals("CLOB")
																																																																												? "clob"
																																																																												: (dbtype
																																																																														.equals("VARCHAR")
																																																																																? "varchar"
																																																																																: (dbtype
																																																																																		.equals("CHAR")
																																																																																				? "varchar"
																																																																																				: (dbtype
																																																																																						.equals("UUID")
																																																																																								? "varchar"
																																																																																								: (dbtype
																																																																																										.equals("ARRAY")
																																																																																												? "varchar"
																																																																																												: "varchar")))))))))))))))))))))))))));
	}

	public TableModel getModelByViewName(String viewName) throws SQLException {
		String sql = "SELECT TABLE_NAME ,REMARKS  FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = \'VIEW\' AND TABLE_SCHEMA=SCHEMA() ";
		sql = sql + " AND UPPER(TABLE_NAME) = \'" + viewName.toUpperCase() + "\'";
		TableModel tableModel = null;
		List tableModels = this.jdbcTemplate.query(sql, this.tableRowMapper);
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
      String sql = "SELECT TABLE_NAME ,REMARKS  FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = \'VIEW\' AND TABLE_SCHEMA=SCHEMA() ";
      if(StringUtil.isNotEmpty(viewName)) {
         sql = sql + " AND TABLE_NAME LIKE \'%" + viewName + "%\'";
      }

      3 rowMapper = new 3(this);
      List tableModels = this.getForList(sql, pageBean, rowMapper, "h2");
      ArrayList tableNames = new ArrayList();
      Iterator tableColumnsMap = tableModels.iterator();

      while(tableColumnsMap.hasNext()) {
         TableModel i$ = (TableModel)tableColumnsMap.next();
         tableNames.add(i$.getName());
      }

      Map tableColumnsMap1 = this.getColumnsByTableName((List)tableNames);
      Iterator i$2 = tableColumnsMap1.entrySet().iterator();

      while(i$2.hasNext()) {
         Entry entry = (Entry)i$2.next();
         Iterator i$1 = tableModels.iterator();

         while(i$1.hasNext()) {
            TableModel model = (TableModel)i$1.next();
            if(model.getName().equalsIgnoreCase((String)entry.getKey())) {
               model.setColumnList((List)entry.getValue());
            }
         }
      }

      return tableModels;
   }

	private List<ColumnModel> getColumnsByTableName(String tableName) {
		String sql = String.format(
				"SELECT A.TABLE_NAME, A.COLUMN_NAME, A.IS_NULLABLE, A.DATA_TYPE, A.CHARACTER_OCTET_LENGTH LENGTH, A.NUMERIC_PRECISION PRECISIONS, A.NUMERIC_SCALE SCALE, B.COLUMN_LIST, A.REMARKS FROM INFORMATION_SCHEMA.COLUMNS A  JOIN INFORMATION_SCHEMA.CONSTRAINTS B ON A.TABLE_NAME=B.TABLE_NAME WHERE  A.TABLE_SCHEMA=SCHEMA() AND UPPER(A.TABLE_NAME)=\'%s\' ",
				new Object[]{tableName});
		HashMap map = new HashMap();
		List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, map,
				new H2ColumnMap());
		Iterator i$ = list.iterator();

		while (i$.hasNext()) {
			ColumnModel model = (ColumnModel) i$.next();
			model.setTableName(tableName);
		}

		return list;
	}

	private Map<String, List<ColumnModel>> getColumnsByTableName(List<String> tableNames) {
		String sql = "SELECT A.TABLE_NAME, A.COLUMN_NAME, A.IS_NULLABLE, A.DATA_TYPE, A.CHARACTER_OCTET_LENGTH LENGTH, A.NUMERIC_PRECISION PRECISIONS, A.NUMERIC_SCALE SCALE, B.COLUMN_LIST, A.REMARKS FROM INFORMATION_SCHEMA.COLUMNS A  JOIN INFORMATION_SCHEMA.CONSTRAINTS B ON A.TABLE_NAME=B.TABLE_NAME WHERE  A.TABLE_SCHEMA=SCHEMA() ";
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
			sql = sql + " AND A.TABLE_NAME IN (" + columnModels.toString() + ") ";
			List columnModels1 = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql,
					new HashMap(), new H2ColumnMap());
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