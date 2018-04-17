package com.hotent.core.table.impl;

import com.hotent.core.db.datasource.JdbcTemplateUtil;
import com.hotent.core.page.PageBean;
import com.hotent.core.table.BaseTableMeta;
import com.hotent.core.table.ColumnModel;
import com.hotent.core.table.TableModel;
import com.hotent.core.table.colmap.OracleColumnMap;
import com.hotent.core.table.impl.OracleTableMeta.1;
import com.hotent.core.table.impl.OracleTableMeta.2;
import com.hotent.core.table.impl.OracleTableMeta.3;
import com.hotent.core.table.impl.OracleTableMeta.4;
import com.hotent.core.table.impl.OracleTableMeta.5;
import com.hotent.core.table.impl.OracleTableMeta.6;
import com.hotent.core.util.BeanUtils;
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
import org.springframework.stereotype.Component;

@Component
public class OracleTableMeta extends BaseTableMeta {
	@Resource
	private JdbcTemplate jdbcTemplate;
	private String sqlPk = "select column_name from user_constraints c,user_cons_columns col where c.constraint_name=col.constraint_name and c.constraint_type=\'P\'and c.table_name=\'%s\'";
	private String sqlTableComment = "select TABLE_NAME,DECODE(COMMENTS,null,TABLE_NAME,comments) comments from user_tab_comments  where table_type=\'TABLE\' AND table_name =\'%s\'";
	private final String SQL_GET_COLUMNS = "SELECT  \tA.TABLE_NAME TABLE_NAME,  \tA.COLUMN_NAME NAME,  \tA.DATA_TYPE TYPENAME,  \tA.DATA_LENGTH LENGTH,   \tA.DATA_PRECISION PRECISION,  \tA.DATA_SCALE SCALE,  \tA.DATA_DEFAULT,  \tA.NULLABLE,   \tDECODE(B.COMMENTS,NULL,A.COLUMN_NAME,B.COMMENTS) DESCRIPTION,  \t(    \t  SELECT    \t    COUNT(*)    \t  FROM     \t    USER_CONSTRAINTS CONS,     \t   USER_CONS_COLUMNS CONS_C      \t WHERE      \t   CONS.CONSTRAINT_NAME=CONS_C.CONSTRAINT_NAME     \t   AND CONS.CONSTRAINT_TYPE=\'P\'     \t   AND CONS.TABLE_NAME=B.TABLE_NAME      \t  AND CONS_C.COLUMN_NAME=A.COLUMN_NAME   \t ) AS IS_PK  FROM   \t USER_TAB_COLUMNS A,  \tUSER_COL_COMMENTS B   WHERE   \tA.COLUMN_NAME=B.COLUMN_NAME  \tAND A.TABLE_NAME = B.TABLE_NAME  \tAND A.TABLE_NAME=\'%s\'  ORDER BY   \tA.COLUMN_ID";
	private final String SQL_GET_COLUMNS_BATCH = "SELECT  \tA.TABLE_NAME TABLE_NAME,  \tA.COLUMN_NAME NAME,  \tA.DATA_TYPE TYPENAME,  \tA.DATA_LENGTH LENGTH,   \tA.DATA_PRECISION PRECISION,  \tA.DATA_SCALE SCALE,  \tA.DATA_DEFAULT,  \tA.NULLABLE,   \tDECODE(B.COMMENTS,NULL,A.COLUMN_NAME,B.COMMENTS) DESCRIPTION,  \t(    \t  SELECT    \t    COUNT(*)    \t  FROM     \t    USER_CONSTRAINTS CONS,     \t   USER_CONS_COLUMNS CONS_C      \t WHERE      \t   CONS.CONSTRAINT_NAME=CONS_C.CONSTRAINT_NAME     \t   AND CONS.CONSTRAINT_TYPE=\'P\'     \t   AND CONS.TABLE_NAME=B.TABLE_NAME      \t  AND CONS_C.COLUMN_NAME=A.COLUMN_NAME   \t ) AS IS_PK  FROM   \tUSER_TAB_COLUMNS A,  \tUSER_COL_COMMENTS B   WHERE   \tA.COLUMN_NAME=B.COLUMN_NAME  \tAND A.TABLE_NAME = B.TABLE_NAME ";
	private String sqlAllTables = "select a.TABLE_NAME,DECODE(b.COMMENTS,null,a.TABLE_NAME,b.comments) comments from user_tables a,user_tab_comments b where a.table_name=b.table_name and b.table_type=\'TABLE\'  ";

	public Map<String, String> getTablesByName(String tableName) {
      String sql = this.sqlAllTables;
      if(StringUtil.isNotEmpty(tableName)) {
         sql = this.sqlAllTables + " and  lower(a.table_name) like \'%" + tableName.toLowerCase() + "%\'";
      }

      HashMap parameter = new HashMap();
      List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, parameter, new 1(this));
      LinkedHashMap map = new LinkedHashMap();

      for(int i = 0; i < list.size(); ++i) {
         Map tmp = (Map)list.get(i);
         String name = (String)tmp.get("name");
         String comments = (String)tmp.get("comments");
         map.put(name, comments);
      }

      return map;
   }

	public Map<String, String> getTablesByName(List<String> names) {
      StringBuffer sb = new StringBuffer();
      Iterator sql = names.iterator();

      while(sql.hasNext()) {
         String parameter = (String)sql.next();
         sb.append("\'");
         sb.append(parameter);
         sb.append("\',");
      }

      sb.deleteCharAt(sb.length() - 1);
      String arg10 = this.sqlAllTables + " and  lower(table_name) in (" + sb.toString().toLowerCase() + ")";
      HashMap arg11 = new HashMap();
      List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(arg10, arg11, new 2(this));
      LinkedHashMap map = new LinkedHashMap();

      for(int i = 0; i < list.size(); ++i) {
         Map tmp = (Map)list.get(i);
         String name = (String)tmp.get("NAME");
         String comments = (String)tmp.get("COMMENTS");
         map.put(name, comments);
      }

      return map;
   }

	public TableModel getTableByName(String tableName) {
		tableName = tableName.toUpperCase();
		TableModel model = this.getTableModel(tableName);
		List columnList = this.getColumnsByTableName(tableName);
		model.setColumnList(columnList);
		return model;
	}

	public List<TableModel> getTablesByName(String tableName, PageBean pageBean) throws Exception {
      String sql = this.sqlAllTables;
      if(StringUtil.isNotEmpty(tableName)) {
         sql = sql + " AND  LOWER(table_name) LIKE \'%" + tableName.toLowerCase() + "%\'";
      }

      3 rowMapper = new 3(this);
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

	private String getPkColumn(String tableName) {
      tableName = tableName.toUpperCase();
      String sql = String.format(this.sqlPk, new Object[]{tableName});
      Object rtn = this.jdbcTemplate.queryForObject(sql, (Object[])null, new 4(this));
      return rtn == null?"":rtn.toString();
   }

	private List<String> getPkColumns(String tableName) {
      tableName = tableName.toUpperCase();
      String sql = String.format(this.sqlPk, new Object[]{tableName});
      List rtn = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, new HashMap(), new 5(this));
      return rtn;
   }

	private TableModel getTableModel(String tableName) {
      String sql = String.format(this.sqlTableComment, new Object[]{tableName});
      TableModel tableModel = (TableModel)this.jdbcTemplate.queryForObject(sql, (Object[])null, new 6(this, tableName));
      if(BeanUtils.isEmpty(tableModel)) {
         tableModel = new TableModel();
      }

      return tableModel;
   }

	private List<ColumnModel> getColumnsByTableName(String tableName) {
		String sql = String.format(
				"SELECT  \tA.TABLE_NAME TABLE_NAME,  \tA.COLUMN_NAME NAME,  \tA.DATA_TYPE TYPENAME,  \tA.DATA_LENGTH LENGTH,   \tA.DATA_PRECISION PRECISION,  \tA.DATA_SCALE SCALE,  \tA.DATA_DEFAULT,  \tA.NULLABLE,   \tDECODE(B.COMMENTS,NULL,A.COLUMN_NAME,B.COMMENTS) DESCRIPTION,  \t(    \t  SELECT    \t    COUNT(*)    \t  FROM     \t    USER_CONSTRAINTS CONS,     \t   USER_CONS_COLUMNS CONS_C      \t WHERE      \t   CONS.CONSTRAINT_NAME=CONS_C.CONSTRAINT_NAME     \t   AND CONS.CONSTRAINT_TYPE=\'P\'     \t   AND CONS.TABLE_NAME=B.TABLE_NAME      \t  AND CONS_C.COLUMN_NAME=A.COLUMN_NAME   \t ) AS IS_PK  FROM   \t USER_TAB_COLUMNS A,  \tUSER_COL_COMMENTS B   WHERE   \tA.COLUMN_NAME=B.COLUMN_NAME  \tAND A.TABLE_NAME = B.TABLE_NAME  \tAND A.TABLE_NAME=\'%s\'  ORDER BY   \tA.COLUMN_ID",
				new Object[]{tableName});
		HashMap map = new HashMap();
		List columnList = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, map,
				new OracleColumnMap());
		return columnList;
	}

	private Map<String, List<ColumnModel>> getColumnsByTableName(List<String> tableNames) {
		String sql = "SELECT  \tA.TABLE_NAME TABLE_NAME,  \tA.COLUMN_NAME NAME,  \tA.DATA_TYPE TYPENAME,  \tA.DATA_LENGTH LENGTH,   \tA.DATA_PRECISION PRECISION,  \tA.DATA_SCALE SCALE,  \tA.DATA_DEFAULT,  \tA.NULLABLE,   \tDECODE(B.COMMENTS,NULL,A.COLUMN_NAME,B.COMMENTS) DESCRIPTION,  \t(    \t  SELECT    \t    COUNT(*)    \t  FROM     \t    USER_CONSTRAINTS CONS,     \t   USER_CONS_COLUMNS CONS_C      \t WHERE      \t   CONS.CONSTRAINT_NAME=CONS_C.CONSTRAINT_NAME     \t   AND CONS.CONSTRAINT_TYPE=\'P\'     \t   AND CONS.TABLE_NAME=B.TABLE_NAME      \t  AND CONS_C.COLUMN_NAME=A.COLUMN_NAME   \t ) AS IS_PK  FROM   \tUSER_TAB_COLUMNS A,  \tUSER_COL_COMMENTS B   WHERE   \tA.COLUMN_NAME=B.COLUMN_NAME  \tAND A.TABLE_NAME = B.TABLE_NAME ";
		HashMap map = new HashMap();
		if (tableNames != null && tableNames.size() == 0) {
			return map;
		} else {
			StringBuffer b = new StringBuffer();
			Iterator columnModels = tableNames.iterator();

			while (columnModels.hasNext()) {
				String i$ = (String) columnModels.next();
				b.append("\'" + i$ + "\',");
			}

			b.deleteCharAt(b.length() - 1);
			sql = sql + " AND A.TABLE_NAME IN (" + b.toString() + ") ";
			Long b1 = Long.valueOf(System.currentTimeMillis());
			List columnModels1 = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql,
					new HashMap(), new OracleColumnMap());
			Iterator i$1 = columnModels1.iterator();

			while (i$1.hasNext()) {
				ColumnModel columnModel = (ColumnModel) i$1.next();
				String tableName = columnModel.getTableName();
				if (map.containsKey(tableName)) {
					((List) map.get(tableName)).add(columnModel);
				} else {
					ArrayList cols = new ArrayList();
					cols.add(columnModel);
					map.put(tableName, cols);
				}
			}

			return map;
		}
	}

	public String getAllTableSql() {
		return this.sqlAllTables;
	}
}