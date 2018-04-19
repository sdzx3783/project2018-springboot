package com.hotent.core.table.impl;

import com.hotent.core.db.datasource.JdbcTemplateUtil;
import com.hotent.core.page.PageBean;
import com.hotent.core.table.BaseDbView;
import com.hotent.core.table.ColumnModel;
import com.hotent.core.table.IDbView;
import com.hotent.core.table.TableModel;
import com.hotent.core.table.colmap.OracleColumnMap;
import com.hotent.core.table.impl.OracleDbView.1;
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
import org.springframework.stereotype.Component;

@Component
public class OracleDbView extends BaseDbView implements IDbView {
	@Resource
	private JdbcTemplate jdbcTemplate;
	private static final String sqlAllView = "select view_name from user_views ";
	private static final String SQL_GET_COLUMNS = "SELECT A.TABLE_NAME TABLE_NAME,A.COLUMN_NAME NAME,A.DATA_TYPE TYPENAME,  A.DATA_LENGTH LENGTH,A.DATA_PRECISION PRECISION, A.DATA_SCALE SCALE,A.DATA_DEFAULT, A.NULLABLE NULLABLE, DECODE(B.COMMENTS,NULL,A.COLUMN_NAME,B.COMMENTS) DESCRIPTION, 0 AS IS_PK FROM  USER_TAB_COLUMNS A,USER_COL_COMMENTS B WHERE A.COLUMN_NAME=B.COLUMN_NAME  AND  A.TABLE_NAME = B.TABLE_NAME  AND  UPPER(A.TABLE_NAME) =UPPER(\'%s\') ORDER BY A.COLUMN_ID";
	private final String SQL_GET_COLUMNS_BATCH = "SELECT  A.TABLE_NAME TABLE_NAME,A.COLUMN_NAME NAME,A.DATA_TYPE TYPENAME,A.DATA_LENGTH LENGTH, A.DATA_PRECISION PRECISION,A.DATA_SCALE SCALE,A.DATA_DEFAULT,A.NULLABLE, DECODE(B.COMMENTS,NULL,A.COLUMN_NAME,B.COMMENTS) DESCRIPTION,  0 AS IS_PK  FROM USER_TAB_COLUMNS A,USER_COL_COMMENTS B WHERE A.COLUMN_NAME=B.COLUMN_NAME AND    A.TABLE_NAME = B.TABLE_NAME ";

	public List<String> getViews(String viewName) throws SQLException {
		String sql = "select view_name from user_views ";
		if (StringUtil.isNotEmpty(viewName)) {
			sql = sql + " where lower(view_name) like \'" + viewName.toLowerCase() + "%\'";
		}

		return this.jdbcTemplate.queryForList(sql, String.class);
	}

	public String getType(String type) {
		type = type.toLowerCase();
		return type.indexOf("number") > -1
				? "number"
				: (type.indexOf("date") > -1 ? "date" : (type.indexOf("char") > -1 ? "varchar" : "varchar"));
	}

	public List<String> getViews(String viewName, PageBean pageBean) throws Exception {
		String sql = "select view_name from user_views ";
		if (StringUtil.isNotEmpty(viewName)) {
			sql = sql + " where lower(view_name) like \'" + viewName.toLowerCase() + "%\'";
		}

		return this.getForList(sql, pageBean, String.class, "oracle");
	}

	public List<TableModel> getViewsByName(String viewName, PageBean pageBean) throws Exception {
      String sql = "select view_name from user_views ";
      if(StringUtil.isNotEmpty(viewName)) {
         sql = sql + " WHERE UPPER(VIEW_NAME) LIKE \'%" + viewName.toUpperCase() + "%\'";
      }

      1 rowMapper = new 1(this);
      List tableModels = this.getForList(sql, pageBean, rowMapper, "oracle");
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
				"SELECT A.TABLE_NAME TABLE_NAME,A.COLUMN_NAME NAME,A.DATA_TYPE TYPENAME,  A.DATA_LENGTH LENGTH,A.DATA_PRECISION PRECISION, A.DATA_SCALE SCALE,A.DATA_DEFAULT, A.NULLABLE NULLABLE, DECODE(B.COMMENTS,NULL,A.COLUMN_NAME,B.COMMENTS) DESCRIPTION, 0 AS IS_PK FROM  USER_TAB_COLUMNS A,USER_COL_COMMENTS B WHERE A.COLUMN_NAME=B.COLUMN_NAME  AND  A.TABLE_NAME = B.TABLE_NAME  AND  UPPER(A.TABLE_NAME) =UPPER(\'%s\') ORDER BY A.COLUMN_ID",
				new Object[]{tableName});
		HashMap map = new HashMap();
		List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, map,
				new OracleColumnMap());
		Iterator i$ = list.iterator();

		while (i$.hasNext()) {
			ColumnModel model = (ColumnModel) i$.next();
			model.setTableName(tableName);
		}

		return list;
	}

	private Map<String, List<ColumnModel>> getColumnsByTableName(List<String> tableNames) {
		String sql = "SELECT  A.TABLE_NAME TABLE_NAME,A.COLUMN_NAME NAME,A.DATA_TYPE TYPENAME,A.DATA_LENGTH LENGTH, A.DATA_PRECISION PRECISION,A.DATA_SCALE SCALE,A.DATA_DEFAULT,A.NULLABLE, DECODE(B.COMMENTS,NULL,A.COLUMN_NAME,B.COMMENTS) DESCRIPTION,  0 AS IS_PK  FROM USER_TAB_COLUMNS A,USER_COL_COMMENTS B WHERE A.COLUMN_NAME=B.COLUMN_NAME AND    A.TABLE_NAME = B.TABLE_NAME ";
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
					new HashMap(), new OracleColumnMap());
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
		String getSql = "CREATE OR REPLACE VIEW " + viewName + " as (" + sql + ")";
		this.jdbcTemplate.execute(getSql);
	}
}