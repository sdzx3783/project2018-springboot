package com.hotent.core.table.impl;

import com.hotent.core.db.datasource.JdbcTemplateUtil;
import com.hotent.core.page.PageBean;
import com.hotent.core.table.BaseTableMeta;
import com.hotent.core.table.ColumnModel;
import com.hotent.core.table.TableModel;
import com.hotent.core.table.colmap.SqlServerColumnMap;
import com.hotent.core.table.impl.SqlServerTableMeta.1;
import com.hotent.core.table.impl.SqlServerTableMeta.2;
import com.hotent.core.table.impl.SqlServerTableMeta.3;
import com.hotent.core.table.impl.SqlServerTableMeta.4;
import com.hotent.core.table.impl.SqlServerTableMeta.5;
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
public class SqlServerTableMeta extends BaseTableMeta {
	@Resource
	private JdbcTemplate jdbcTemplate;
	private String sqlPk = "SELECT c.COLUMN_NAME COLUMN_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS pk ,INFORMATION_SCHEMA.KEY_COLUMN_USAGE c WHERE \tpk.TABLE_NAME LIKE \'%s\' and\tCONSTRAINT_TYPE = \'PRIMARY KEY\' and\tc.TABLE_NAME = pk.TABLE_NAME and\tc.CONSTRAINT_NAME = pk.CONSTRAINT_NAME ";
	private String sqlTableComment = "select comment from (select a.name name, cast(b.value as varchar) comment from sys.tables a, sys.extended_properties b where a.type=\'U\' and a.object_id=b.major_id and b.minor_id=0 union(select name,name comment from sys.tables where type=\'U\' and object_id not in (select DISTINCT major_id from sys.extended_properties where minor_id=0))) t where 1=1 and t.name=\'%s\'";
	private String SQL_GET_COLUMNS = "SELECT B.NAME TABLE_NAME,A.NAME NAME, C.NAME TYPENAME, A.MAX_LENGTH LENGTH, A.IS_NULLABLE IS_NULLABLE,A.PRECISION PRECISION,A.SCALE SCALE,   (  \t\tSELECT COUNT(*)  \t\tFROM   \t\tSYS.IDENTITY_COLUMNS   \t\tWHERE SYS.IDENTITY_COLUMNS.OBJECT_ID = A.OBJECT_ID AND A.COLUMN_ID = SYS.IDENTITY_COLUMNS.COLUMN_ID\t) AS AUTOGEN,  \t(  \t\tSELECT CAST(VALUE AS VARCHAR)  \t\tFROM SYS.EXTENDED_PROPERTIES   \t\tWHERE SYS.EXTENDED_PROPERTIES.MAJOR_ID = A.OBJECT_ID AND SYS.EXTENDED_PROPERTIES.MINOR_ID = A.COLUMN_ID  \t) AS DESCRIPTION,  \t(  \t\tSELECT COUNT(*)  \t\tFROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS pk ,INFORMATION_SCHEMA.KEY_COLUMN_USAGE kcu  \t\tWHERE \tpk.TABLE_NAME = B.NAME  \t\t\t AND\tCONSTRAINT_TYPE = \'PRIMARY KEY\'   \t\t\t AND\tKCU.TABLE_NAME = PK.TABLE_NAME   \t\t\t AND\tKCU.CONSTRAINT_NAME = PK.CONSTRAINT_NAME  \t\t\t AND \tKCU.COLUMN_NAME =A.NAME  \t) AS IS_PK  FROM SYS.COLUMNS A, SYS.TABLES B, SYS.TYPES C   WHERE A.OBJECT_ID = B.OBJECT_ID AND A.SYSTEM_TYPE_ID=C.SYSTEM_TYPE_ID AND B.NAME=\'%s\'  \t\tAND C.NAME<>\'SYSNAME\' \t\tORDER BY A.COLUMN_ID ";
	private String SQL_GET_COLUMNS_BATCH = "SELECT B.NAME TABLE_NAME,A.NAME NAME, C.NAME TYPENAME, A.MAX_LENGTH LENGTH, A.IS_NULLABLE IS_NULLABLE,A.PRECISION PRECISION,A.SCALE SCALE,  (  \tSELECT COUNT(*)  \tFROM   \tSYS.IDENTITY_COLUMNS   WHERE SYS.IDENTITY_COLUMNS.OBJECT_ID = A.OBJECT_ID AND A.COLUMN_ID = SYS.IDENTITY_COLUMNS.COLUMN_ID) AS AUTOGEN,  \t(  \t\t\tSELECT CAST(VALUE AS VARCHAR)  \t\t\tFROM SYS.EXTENDED_PROPERTIES   \t\tWHERE SYS.EXTENDED_PROPERTIES.MAJOR_ID = A.OBJECT_ID AND SYS.EXTENDED_PROPERTIES.MINOR_ID = A.COLUMN_ID  \t) AS DESCRIPTION,  \t(  \t\tSELECT COUNT(*)  \t\tFROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS pk ,INFORMATION_SCHEMA.KEY_COLUMN_USAGE kcu  \t\tWHERE \tpk.TABLE_NAME = B.NAME  \t\t\t AND\tCONSTRAINT_TYPE = \'PRIMARY KEY\'   \t\t\t AND\tKCU.TABLE_NAME = PK.TABLE_NAME   \t\t\t AND\tKCU.CONSTRAINT_NAME = PK.CONSTRAINT_NAME  \t\t\t AND \tKCU.COLUMN_NAME =A.NAME  \t) AS IS_PK  FROM SYS.COLUMNS A, SYS.TABLES B, SYS.TYPES C   WHERE A.OBJECT_ID = B.OBJECT_ID AND A.SYSTEM_TYPE_ID=C.SYSTEM_TYPE_ID  \t\tAND C.NAME<>\'SYSNAME\' ";
	private String sqlAllTables = "select * from (select a.name name, cast(b.value as varchar) comment from sys.tables a, sys.extended_properties b where a.type=\'U\' and a.object_id=b.major_id and b.minor_id=0 union(select name,name comment from sys.tables where type=\'U\' and object_id not in (select DISTINCT major_id from sys.extended_properties where minor_id=0))) t where 1=1";

	public TableModel getTableByName(String tableName) {
		TableModel model = this.getTableModel(tableName);
		List columnList = this.getColumnsByTableName(tableName);
		model.setColumnList(columnList);
		return model;
	}

	private String getPkColumn(String tableName) {
      String sql = String.format(this.sqlPk, new Object[]{tableName});
      Object rtn = this.jdbcTemplate.queryForObject(sql, (Object[])null, new 1(this));
      return rtn == null?"":rtn.toString();
   }

	private TableModel getTableModel(String tableName) {
      String sql = String.format(this.sqlTableComment, new Object[]{tableName});
      TableModel tableModel = (TableModel)this.jdbcTemplate.queryForObject(sql, (Object[])null, new 2(this, tableName));
      if(BeanUtils.isEmpty(tableModel)) {
         tableModel = new TableModel();
      }

      tableModel.setName(tableName);
      return tableModel;
   }

	public Map<String, String> getTablesByName(String tableName) {
      String sql = this.sqlAllTables;
      if(StringUtil.isNotEmpty(tableName)) {
         sql = sql + " and  lower(t.name) like \'%" + tableName.toLowerCase() + "%\'";
      }

      HashMap parameter = new HashMap();
      List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, parameter, new 3(this));
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
      String arg10 = this.sqlAllTables + " and  t.name in (" + sb.toString().toLowerCase() + ")";
      HashMap arg11 = new HashMap();
      List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(arg10, arg11, new 4(this));
      LinkedHashMap map = new LinkedHashMap();

      for(int i = 0; i < list.size(); ++i) {
         Map tmp = (Map)list.get(i);
         String name = (String)tmp.get("name");
         String comments = (String)tmp.get("comments");
         map.put(name, comments);
      }

      return map;
   }

	public List<TableModel> getTablesByName(String tableName, PageBean pageBean) throws Exception {
      String sql = this.sqlAllTables;
      if(StringUtil.isNotEmpty(tableName)) {
         sql = sql + " AND  LOWER(t.name) LIKE \'%" + tableName.toLowerCase() + "%\'";
      }

      5 rowMapper = new 5(this);
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
		String sql = String.format(this.SQL_GET_COLUMNS, new Object[]{tableName});
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
		String sql = this.SQL_GET_COLUMNS_BATCH;
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

	public String getAllTableSql() {
		return this.sqlAllTables;
	}
}