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

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.hotent.core.db.datasource.JdbcTemplateUtil;
import com.hotent.core.page.PageBean;
import com.hotent.core.table.BaseTableMeta;
import com.hotent.core.table.ColumnModel;
import com.hotent.core.table.TableModel;
import com.hotent.core.table.colmap.MySqlColumnMap;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;

@Component
public class MySqlTableMeta extends BaseTableMeta {
	@Resource
	private JdbcTemplate jdbcTemplate;
	private final String SQL_GET_COLUMNS = "SELECT TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_OCTET_LENGTH LENGTH, NUMERIC_PRECISION PRECISIONS,NUMERIC_SCALE SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM  INFORMATION_SCHEMA.COLUMNS  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME=\'%s\' ";
	private final String SQL_GET_COLUMNS_BATCH = "SELECT TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_OCTET_LENGTH LENGTH, NUMERIC_PRECISION PRECISIONS,NUMERIC_SCALE SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM  INFORMATION_SCHEMA.COLUMNS  WHERE TABLE_SCHEMA=DATABASE() ";
	private final String sqlComment = "select table_name,table_comment  from information_schema.tables t where t.table_schema=DATABASE() and table_name=\'%s\' ";
	private final String sqlAllTable = "select table_name,table_comment from information_schema.tables t where t.table_type=\'BASE TABLE\' AND t.table_schema=DATABASE()";
	private final String sqlPk = "SELECT k.column_name name FROM information_schema.table_constraints t JOIN information_schema.key_column_usage k USING(constraint_name,table_schema,table_name) WHERE t.constraint_type=\'PRIMARY KEY\' AND t.table_schema=DATABASE() AND t.table_name=\'%s\'";

	public TableModel getTableByName(String tableName) {
		TableModel model = this.getTableModel(tableName);
		List columnList = this.getColumnsByTableName(tableName);
		model.setColumnList(columnList);
		return model;
	}

	private String getPkColumn(String tableName) {
      String sql = String.format("SELECT k.column_name name FROM information_schema.table_constraints t JOIN information_schema.key_column_usage k USING(constraint_name,table_schema,table_name) WHERE t.constraint_type=\'PRIMARY KEY\' AND t.table_schema=DATABASE() AND t.table_name=\'%s\'", new Object[]{tableName});
      Object rtn = this.jdbcTemplate.queryForObject(sql, (Object[])null, new RowMapper<String>() {
    	  public String mapRow(ResultSet rs, int row) throws SQLException {
    	        return rs.getString("name");
    	    }
      });
      return rtn == null?"":rtn.toString();
   }

	private List<ColumnModel> getColumnsByTableName(String tableName) {
		String sql = String.format(
				"SELECT TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_OCTET_LENGTH LENGTH, NUMERIC_PRECISION PRECISIONS,NUMERIC_SCALE SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM  INFORMATION_SCHEMA.COLUMNS  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME=\'%s\' ",
				new Object[]{tableName});
		HashMap map = new HashMap();
		List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, map,
				new MySqlColumnMap());
		Iterator i$ = list.iterator();

		while (i$.hasNext()) {
			ColumnModel model = (ColumnModel) i$.next();
			model.setTableName(tableName);
		}

		return list;
	}

	private Map<String, List<ColumnModel>> getColumnsByTableName(List<String> tableNames) {
		String sql = "SELECT TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_OCTET_LENGTH LENGTH, NUMERIC_PRECISION PRECISIONS,NUMERIC_SCALE SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM  INFORMATION_SCHEMA.COLUMNS  WHERE TABLE_SCHEMA=DATABASE() ";
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
			sql = sql + " AND TABLE_NAME IN (" + columnModels.toString() + ") ";
			List columnModels1 = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql,
					new HashMap(), new MySqlColumnMap());
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

	private TableModel getTableModel(final String tableName) {
      String sql = String.format("select table_name,table_comment  from information_schema.tables t where t.table_schema=DATABASE() and table_name=\'%s\' ", new Object[]{tableName});
      TableModel tableModel = (TableModel)this.jdbcTemplate.queryForObject(sql, (Object[])null, new RowMapper<TableModel>() {
    	  public TableModel mapRow(ResultSet rs, int row) throws SQLException {
    	        TableModel tableModel = new TableModel();
    	        String comments = rs.getString("table_comment");
    	        comments = MySqlTableMeta.getComments(comments, tableName);
    	        tableModel.setName(tableName);
    	        tableModel.setComment(comments);
    	        return tableModel;
    	    }
      });
      if(BeanUtils.isEmpty(tableModel)) {
         tableModel = new TableModel();
      }

      return tableModel;
   }

	public Map<String, String> getTablesByName(String tableName) {
      String sql = "select table_name,table_comment from information_schema.tables t where t.table_type=\'BASE TABLE\' AND t.table_schema=DATABASE()";
      if(StringUtil.isNotEmpty(tableName)) {
         sql = sql + " AND TABLE_NAME LIKE \'%" + tableName + "%\'";
      }

      HashMap parameter = new HashMap();
      List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, parameter, new RowMapper<Map<String, String>>(){
    	  public Map<String, String> mapRow(ResultSet rs, int row) throws SQLException {
    	        String tableName = rs.getString("table_name");
    	        String comments = rs.getString("table_comment");
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
         comments = getComments(comments, name);
         map.put(name, comments);
      }

      return map;
   }

	public static String getComments(String comments, String defaultValue) {
		if (StringUtil.isEmpty(comments)) {
			return defaultValue;
		} else {
			int idx = comments.indexOf("InnoDB free");
			if (idx > -1) {
				comments = StringUtil.trimSufffix(comments.substring(0, idx).trim(), ";");
			}

			if (StringUtil.isEmpty(comments)) {
				comments = defaultValue;
			}

			return comments;
		}
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
      String arg10 = "select table_name,table_comment from information_schema.tables t where t.table_type=\'BASE TABLE\' AND t.table_schema=DATABASE() and  lower(table_name) in (" + sb.toString().toLowerCase() + ")";
      HashMap arg11 = new HashMap();
      List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(arg10, arg11, new RowMapper<Map<String, String>>(){
    	  public Map<String, String> mapRow(ResultSet rs, int row) throws SQLException {
    	        String tableName = rs.getString("table_name");
    	        String comments = rs.getString("table_comment");
    	        Map<String, String> map = new HashMap();
    	        map.put("tableName", tableName);
    	        map.put("tableComment", comments);
    	        return map;
    	    }
      });
      LinkedHashMap map = new LinkedHashMap();

      for(int i = 0; i < list.size(); ++i) {
         Map tmp = (Map)list.get(i);
         String name = (String)tmp.get("tableName");
         String comments = (String)tmp.get("tableComment");
         map.put(name, comments);
      }

      return map;
   }

	public List<TableModel> getTablesByName(String tableName, PageBean pageBean) throws Exception {
      String sql = "select table_name,table_comment from information_schema.tables t where t.table_type=\'BASE TABLE\' AND t.table_schema=DATABASE()";
      if(StringUtil.isNotEmpty(tableName)) {
         sql = sql + " AND TABLE_NAME LIKE \'%" + tableName + "%\'";
      }

      RowMapper rowMapper = new RowMapper<TableModel>() {
    	  public TableModel mapRow(ResultSet rs, int row) throws SQLException {
    	        TableModel tableModel = new TableModel();
    	        tableModel.setName(rs.getString("TABLE_NAME"));
    	        String comments = rs.getString("TABLE_COMMENT");
    	        comments = MySqlTableMeta.getComments(comments, tableModel.getName());
    	        tableModel.setComment(comments);
    	        return tableModel;
    	    }
      };
      List tableModels = this.getForList(sql, pageBean, rowMapper, "mysql");
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

	public String getAllTableSql() {
		return "select table_name,table_comment from information_schema.tables t where t.table_type=\'BASE TABLE\' AND t.table_schema=DATABASE()";
	}
}