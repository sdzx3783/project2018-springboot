package com.hotent.core.table.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.hotent.core.db.datasource.JdbcTemplateUtil;
import com.hotent.core.page.PageBean;
import com.hotent.core.table.BaseTableMeta;
import com.hotent.core.table.ColumnModel;
import com.hotent.core.table.TableModel;
import com.hotent.core.table.colmap.DmColumnMap;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;

@Component
public class DmTableMeta extends BaseTableMeta {
	@Resource
	private JdbcTemplate jdbcTemplate;
	protected Logger logger = LoggerFactory.getLogger(DmTableMeta.class);
	private String sqlPk = "SELECT  CONS_C.COLUMN_NAME FROM \"SYS\".\"USER_CONSTRAINTS\" CONS, \"SYS\".\"USER_CONS_COLUMNS\" CONS_C    WHERE  CONS.CONSTRAINT_NAME=CONS_C.CONSTRAINT_NAME  AND CONS.CONSTRAINT_TYPE=\'P\'  AND CONS_C.POSITION=1  AND   CONS.TABLE_NAME=\'%s\'";
	private String sqlTableComment = "SELECT TABLE_NAME,COMMENTS FROM (SELECT A.TABLE_NAME AS TABLE_NAME,DECODE(B.COMMENT$,NULL, A.TABLE_NAME,B.COMMENT$) AS COMMENTS FROM \"SYS\".\"USER_TABLES\" A LEFT JOIN \"SYS\".\"SYSTABLECOMMENTS\" B ON  A.TABLE_NAME=B.TVNAME) WHERE  TABLE_NAME =\'%s\'";
	private final String SQL_GET_COLUMNS = "SELECT T.TABLE_NAME TABLE_NAME, T.NAME NAME,T.TYPENAME TYPENAME, T.LENGTH LENGTH,  T.PRECISION PRECISION,T.SCALE SCALE,T.DATA_DEFAULT DATA_DEFAULT,T.NULLABLE NULLABLE,T.DESCRIPTION DESCRIPTION,  (SELECT  COUNT(*)   FROM    \"SYS\".\"USER_CONSTRAINTS\" CONS, \"SYS\".\"USER_CONS_COLUMNS\" CONS_C    WHERE  CONS.CONSTRAINT_NAME=CONS_C.CONSTRAINT_NAME  AND CONS.CONSTRAINT_TYPE=\'P\'  AND CONS_C.POSITION=1  AND   CONS.TABLE_NAME=T.TABLE_NAME  AND CONS_C.COLUMN_NAME= T.NAME) AS  IS_PK FROM (SELECT A.COLUMN_ID COLUMN_ID, A.TABLE_NAME TABLE_NAME, A.COLUMN_NAME NAME, A.DATA_TYPE TYPENAME, A.DATA_LENGTH LENGTH, A.DATA_PRECISION PRECISION, A.DATA_SCALE SCALE, A.DATA_DEFAULT, A.NULLABLE, DECODE(B.COMMENT$,NULL, A.TABLE_NAME,B.COMMENT$) AS DESCRIPTION  FROM \"SYS\".\"USER_TAB_COLUMNS\" A LEFT JOIN \"SYS\".\"SYSCOLUMNCOMMENTS\" B ON  A.COLUMN_NAME=B.COLNAME AND  A.TABLE_NAME=B.TVNAME  AND B.SCHNAME=user() ) T  WHERE TABLE_NAME=\'%S\'  ORDER BY COLUMN_ID ";
	private final String SQL_GET_COLUMNS_BATCH = "SELECT T.TABLE_NAME TABLE_NAME, T.NAME NAME,T.TYPENAME TYPENAME, T.LENGTH LENGTH,  T.PRECISION PRECISION,T.SCALE SCALE,T.DATA_DEFAULT DATA_DEFAULT,T.NULLABLE NULLABLE,T.DESCRIPTION DESCRIPTION,  (SELECT  COUNT(*)   FROM    \"SYS\".\"USER_CONSTRAINTS\" CONS, \"SYS\".\"USER_CONS_COLUMNS\" CONS_C    WHERE  CONS.CONSTRAINT_NAME=CONS_C.CONSTRAINT_NAME  AND CONS.CONSTRAINT_TYPE=\'P\'  AND CONS_C.POSITION=1  AND   CONS.TABLE_NAME=T.TABLE_NAME  AND CONS_C.COLUMN_NAME= T.NAME) AS  IS_PK FROM (SELECT A.COLUMN_ID COLUMN_ID, A.TABLE_NAME TABLE_NAME, A.COLUMN_NAME NAME, A.DATA_TYPE TYPENAME, A.DATA_LENGTH LENGTH, A.DATA_PRECISION PRECISION, A.DATA_SCALE SCALE, A.DATA_DEFAULT, A.NULLABLE, DECODE(B.COMMENT$,NULL, A.TABLE_NAME,B.COMMENT$) AS DESCRIPTION  FROM \"SYS\".\"USER_TAB_COLUMNS\" A LEFT JOIN \"SYS\".\"SYSCOLUMNCOMMENTS\" B ON  A.COLUMN_NAME=B.COLNAME AND  A.TABLE_NAME=B.TVNAME  AND B.SCHNAME=user() ) T WHERE 1=1 ";
	private String sqlAllTables = "SELECT TABLE_NAME,COMMENTS FROM (SELECT A.TABLE_NAME AS TABLE_NAME,DECODE(B.COMMENT$,NULL, A.TABLE_NAME,B.COMMENT$) AS COMMENTS FROM \"SYS\".\"USER_TABLES\" A LEFT JOIN \"SYS\".\"SYSTABLECOMMENTS\" B ON  A.TABLE_NAME=B.TVNAME) WHERE 1=1";

	public Map<String, String> getTablesByName(String tableName) {
      String sql = this.sqlAllTables;
      if(StringUtil.isNotEmpty(tableName)) {
         sql = this.sqlAllTables + " and  lower(TABLE_NAME) like \'%" + tableName.toLowerCase() + "%\'";
      }

      HashMap parameter = new HashMap();
      List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, parameter, new RowMapper<Map<String, String>>(){
    	  public Map<String, String> mapRow(ResultSet rs, int row) throws SQLException {
    	        String tableName = rs.getString("table_name");
    	        String comments = rs.getString("comments");
    	        Map<String, String> map = new HashMap();
    	        map.put("name", tableName);
    	        map.put("comments", comments);
    	        return map;
    	    }
      });
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
      String arg10 = this.sqlAllTables + " and  lower(TABLE_NAME) in (" + sb.toString().toLowerCase() + ")";
      HashMap arg11 = new HashMap();
      List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(arg10, arg11, new RowMapper<Map<String, String>>(){
    	  public Map<String, String> mapRow(ResultSet rs, int row) throws SQLException {
    	        String tableName = rs.getString("TABLE_NAME");
    	        String comments = rs.getString("COMMENTS");
    	        Map<String, String> map = new HashMap();
    	        map.put("NAME", tableName);
    	        map.put("COMMENTS", comments);
    	        return map;
    	    }
      });
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
         sql = sql + " AND  LOWER(TABLE_NAME) LIKE \'%" + tableName.toLowerCase() + "%\'";
      }

		RowMapper<TableModel> rowMapper = new RowMapper<TableModel>() {
			public TableModel mapRow(ResultSet rs, int row) throws SQLException {
				TableModel tableModel = new TableModel();
				tableModel.setName(rs.getString("TABLE_NAME"));
				tableModel.setComment(rs.getString("COMMENTS"));
				return tableModel;
			}
		};
      List tableModels = this.getForList(sql, pageBean, rowMapper, "dm");
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
      Object rtn = this.jdbcTemplate.queryForObject(sql, (Object[])null, new RowMapper<String>() {
    	  public String mapRow(ResultSet rs, int row) throws SQLException {
    	        return rs.getString("COLUMN_NAME");
    	    }
      });
      return rtn == null?"":rtn.toString();
   }

	private List<String> getPkColumns(String tableName) {
      tableName = tableName.toUpperCase();
      String sql = String.format(this.sqlPk, new Object[]{tableName});
      List rtn = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, new HashMap(), new RowMapper<String>() {
    	  public String mapRow(ResultSet rs, int rowNum) throws SQLException {
    	        return rs.getString("column_name");
    	    }
      });
      return rtn;
   }

	private TableModel getTableModel(final String tableName) {
      String sql = String.format(this.sqlTableComment, new Object[]{tableName});
      TableModel tableModel = (TableModel)this.jdbcTemplate.queryForObject(sql, (Object[])null, new RowMapper<TableModel>() {
    	  public TableModel mapRow(ResultSet rs, int row) throws SQLException {
    	        TableModel tableModel = new TableModel();
    	        tableModel.setName(tableName);
    	        tableModel.setComment(rs.getString("comments"));
    	        return tableModel;
    	    }
      });
      if(BeanUtils.isEmpty(tableModel)) {
         tableModel = new TableModel();
      }

      return tableModel;
   }

	private List<ColumnModel> getColumnsByTableName(String tableName) {
		String sql = String.format(
				"SELECT T.TABLE_NAME TABLE_NAME, T.NAME NAME,T.TYPENAME TYPENAME, T.LENGTH LENGTH,  T.PRECISION PRECISION,T.SCALE SCALE,T.DATA_DEFAULT DATA_DEFAULT,T.NULLABLE NULLABLE,T.DESCRIPTION DESCRIPTION,  (SELECT  COUNT(*)   FROM    \"SYS\".\"USER_CONSTRAINTS\" CONS, \"SYS\".\"USER_CONS_COLUMNS\" CONS_C    WHERE  CONS.CONSTRAINT_NAME=CONS_C.CONSTRAINT_NAME  AND CONS.CONSTRAINT_TYPE=\'P\'  AND CONS_C.POSITION=1  AND   CONS.TABLE_NAME=T.TABLE_NAME  AND CONS_C.COLUMN_NAME= T.NAME) AS  IS_PK FROM (SELECT A.COLUMN_ID COLUMN_ID, A.TABLE_NAME TABLE_NAME, A.COLUMN_NAME NAME, A.DATA_TYPE TYPENAME, A.DATA_LENGTH LENGTH, A.DATA_PRECISION PRECISION, A.DATA_SCALE SCALE, A.DATA_DEFAULT, A.NULLABLE, DECODE(B.COMMENT$,NULL, A.TABLE_NAME,B.COMMENT$) AS DESCRIPTION  FROM \"SYS\".\"USER_TAB_COLUMNS\" A LEFT JOIN \"SYS\".\"SYSCOLUMNCOMMENTS\" B ON  A.COLUMN_NAME=B.COLNAME AND  A.TABLE_NAME=B.TVNAME  AND B.SCHNAME=user() ) T  WHERE TABLE_NAME=\'%S\'  ORDER BY COLUMN_ID ",
				new Object[]{tableName});
		HashMap map = new HashMap();
		List columnList = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, map,
				new DmColumnMap());
		return columnList;
	}

	private Map<String, List<ColumnModel>> getColumnsByTableName(List<String> tableNames) {
		String sql = "SELECT T.TABLE_NAME TABLE_NAME, T.NAME NAME,T.TYPENAME TYPENAME, T.LENGTH LENGTH,  T.PRECISION PRECISION,T.SCALE SCALE,T.DATA_DEFAULT DATA_DEFAULT,T.NULLABLE NULLABLE,T.DESCRIPTION DESCRIPTION,  (SELECT  COUNT(*)   FROM    \"SYS\".\"USER_CONSTRAINTS\" CONS, \"SYS\".\"USER_CONS_COLUMNS\" CONS_C    WHERE  CONS.CONSTRAINT_NAME=CONS_C.CONSTRAINT_NAME  AND CONS.CONSTRAINT_TYPE=\'P\'  AND CONS_C.POSITION=1  AND   CONS.TABLE_NAME=T.TABLE_NAME  AND CONS_C.COLUMN_NAME= T.NAME) AS  IS_PK FROM (SELECT A.COLUMN_ID COLUMN_ID, A.TABLE_NAME TABLE_NAME, A.COLUMN_NAME NAME, A.DATA_TYPE TYPENAME, A.DATA_LENGTH LENGTH, A.DATA_PRECISION PRECISION, A.DATA_SCALE SCALE, A.DATA_DEFAULT, A.NULLABLE, DECODE(B.COMMENT$,NULL, A.TABLE_NAME,B.COMMENT$) AS DESCRIPTION  FROM \"SYS\".\"USER_TAB_COLUMNS\" A LEFT JOIN \"SYS\".\"SYSCOLUMNCOMMENTS\" B ON  A.COLUMN_NAME=B.COLNAME AND  A.TABLE_NAME=B.TVNAME  AND B.SCHNAME=user() ) T WHERE 1=1 ";
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
			sql = sql + " AND T.TABLE_NAME IN (" + b.toString() + ") ";
			Long b1 = Long.valueOf(System.currentTimeMillis());
			List columnModels1 = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql,
					new HashMap(), new DmColumnMap());
			this.logger.info(String.valueOf(System.currentTimeMillis() - b1.longValue()));
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