package com.hotent.core.table.impl;

import com.hotent.core.db.datasource.JdbcTemplateUtil;
import com.hotent.core.page.PageBean;
import com.hotent.core.table.BaseDbView;
import com.hotent.core.table.ColumnModel;
import com.hotent.core.table.IDbView;
import com.hotent.core.table.TableModel;
import com.hotent.core.table.colmap.SqlServerColumnMap;
import com.hotent.core.table.impl.SqlserverDbView.1;
import com.hotent.core.util.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SqlserverDbView extends BaseDbView implements IDbView {
	@Resource
	private JdbcTemplate jdbcTemplate;
	private final String sqlAllView = "select name from sysobjects where xtype=\'V\'";
	private final String SQL_GET_COLUMNS = "SELECT B.NAME TABLE_NAME,A.NAME NAME, C.NAME TYPENAME, A.MAX_LENGTH LENGTH, A.IS_NULLABLE IS_NULLABLE,A.PRECISION PRECISION,A.SCALE SCALE,  (SELECT COUNT(*) FROM SYS.IDENTITY_COLUMNS WHERE SYS.IDENTITY_COLUMNS.OBJECT_ID = A.OBJECT_ID AND A.COLUMN_ID = SYS.IDENTITY_COLUMNS.COLUMN_ID) AS AUTOGEN, (SELECT CAST(VALUE AS VARCHAR) FROM SYS.EXTENDED_PROPERTIES WHERE SYS.EXTENDED_PROPERTIES.MAJOR_ID = A.OBJECT_ID AND SYS.EXTENDED_PROPERTIES.MINOR_ID = A.COLUMN_ID) AS DESCRIPTION,  0 AS IS_PK FROM  SYS.COLUMNS A, SYS.VIEWS B, SYS.TYPES C WHERE  A.OBJECT_ID = B.OBJECT_ID  AND A.SYSTEM_TYPE_ID=C.SYSTEM_TYPE_ID AND B.NAME=\'%s\' AND C.NAME<>\'SYSNAME\' ORDER BY A.COLUMN_ID";
	private final String SQL_GET_COLUMNS_BATCH = "SELECT B.NAME TABLE_NAME,A.NAME NAME, C.NAME TYPENAME, A.MAX_LENGTH LENGTH, A.IS_NULLABLE IS_NULLABLE,A.PRECISION PRECISION,A.SCALE SCALE,  (SELECT COUNT(*) FROM SYS.IDENTITY_COLUMNS WHERE SYS.IDENTITY_COLUMNS.OBJECT_ID = A.OBJECT_ID AND A.COLUMN_ID = SYS.IDENTITY_COLUMNS.COLUMN_ID) AS AUTOGEN, (SELECT CAST(VALUE AS VARCHAR) FROM SYS.EXTENDED_PROPERTIES WHERE SYS.EXTENDED_PROPERTIES.MAJOR_ID = A.OBJECT_ID AND SYS.EXTENDED_PROPERTIES.MINOR_ID = A.COLUMN_ID) AS DESCRIPTION,  0 AS IS_PK FROM  SYS.COLUMNS A, SYS.VIEWS B, SYS.TYPES C WHERE  A.OBJECT_ID = B.OBJECT_ID  AND A.SYSTEM_TYPE_ID=C.SYSTEM_TYPE_ID AND C.NAME<>\'SYSNAME\' ";

	public List<String> getViews(String viewName) {
		String sql = "select name from sysobjects where xtype=\'V\'";
		if (StringUtil.isNotEmpty(viewName)) {
			sql = sql + " and name like \'" + viewName + "%\'";
		}

		return this.jdbcTemplate.queryForList(sql, String.class);
	}

	public String getType(String type) {
		return type.indexOf("int") <= -1 && !type.equals("real") && !type.equals("numeric")
				&& type.indexOf("money") <= -1 ? (type.indexOf("date") > -1 ? "date" : "varchar") : "number";
	}

	public List<String> getViews(String viewName, PageBean pageBean) throws Exception {
		String sql = "select name from sysobjects where xtype=\'V\'";
		if (StringUtil.isNotEmpty(viewName)) {
			sql = sql + " AND NAME LIKE \'" + viewName + "%\'";
		}

		return this.getForList(sql, pageBean, String.class, "mssql");
	}

	public List<TableModel> getViewsByName(String viewName, PageBean pageBean) throws Exception {
      String sql = "select name from sysobjects where xtype=\'V\'";
      if(StringUtil.isNotEmpty(viewName)) {
         sql = sql + " AND NAME LIKE \'" + viewName + "%\'";
      }

      1 rowMapper = new 1(this);
      List tableModels = this.getForList(sql, pageBean, rowMapper, "mssql");
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
				"SELECT B.NAME TABLE_NAME,A.NAME NAME, C.NAME TYPENAME, A.MAX_LENGTH LENGTH, A.IS_NULLABLE IS_NULLABLE,A.PRECISION PRECISION,A.SCALE SCALE,  (SELECT COUNT(*) FROM SYS.IDENTITY_COLUMNS WHERE SYS.IDENTITY_COLUMNS.OBJECT_ID = A.OBJECT_ID AND A.COLUMN_ID = SYS.IDENTITY_COLUMNS.COLUMN_ID) AS AUTOGEN, (SELECT CAST(VALUE AS VARCHAR) FROM SYS.EXTENDED_PROPERTIES WHERE SYS.EXTENDED_PROPERTIES.MAJOR_ID = A.OBJECT_ID AND SYS.EXTENDED_PROPERTIES.MINOR_ID = A.COLUMN_ID) AS DESCRIPTION,  0 AS IS_PK FROM  SYS.COLUMNS A, SYS.VIEWS B, SYS.TYPES C WHERE  A.OBJECT_ID = B.OBJECT_ID  AND A.SYSTEM_TYPE_ID=C.SYSTEM_TYPE_ID AND B.NAME=\'%s\' AND C.NAME<>\'SYSNAME\' ORDER BY A.COLUMN_ID",
				new Object[]{tableName});
		HashMap map = new HashMap();
		List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, map,
				new SqlServerColumnMap());
		Iterator i$ = list.iterator();

		while (i$.hasNext()) {
			ColumnModel model = (ColumnModel) i$.next();
			model.setTableName(tableName);
		}

		return list;
	}

	private Map<String, List<ColumnModel>> getColumnsByTableName(List<String> tableNames) {
		String sql = "SELECT B.NAME TABLE_NAME,A.NAME NAME, C.NAME TYPENAME, A.MAX_LENGTH LENGTH, A.IS_NULLABLE IS_NULLABLE,A.PRECISION PRECISION,A.SCALE SCALE,  (SELECT COUNT(*) FROM SYS.IDENTITY_COLUMNS WHERE SYS.IDENTITY_COLUMNS.OBJECT_ID = A.OBJECT_ID AND A.COLUMN_ID = SYS.IDENTITY_COLUMNS.COLUMN_ID) AS AUTOGEN, (SELECT CAST(VALUE AS VARCHAR) FROM SYS.EXTENDED_PROPERTIES WHERE SYS.EXTENDED_PROPERTIES.MAJOR_ID = A.OBJECT_ID AND SYS.EXTENDED_PROPERTIES.MINOR_ID = A.COLUMN_ID) AS DESCRIPTION,  0 AS IS_PK FROM  SYS.COLUMNS A, SYS.VIEWS B, SYS.TYPES C WHERE  A.OBJECT_ID = B.OBJECT_ID  AND A.SYSTEM_TYPE_ID=C.SYSTEM_TYPE_ID AND C.NAME<>\'SYSNAME\' ";
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
			sql = sql + " AND B.NAME IN (" + columnModels.toString() + ") ";
			List columnModels1 = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql,
					new HashMap(), new SqlServerColumnMap());
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
		String sql_drop_view = "IF EXISTS (SELECT * FROM sysobjects WHERE xtype=\'V\' AND name = \'" + viewName + "\')"
				+ " DROP VIEW " + viewName;
		String viewSql = "CREATE VIEW " + viewName + " AS " + sql;
		this.jdbcTemplate.execute(sql_drop_view);
		this.jdbcTemplate.execute(viewSql);
	}
}