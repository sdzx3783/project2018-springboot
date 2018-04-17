package com.hotent.core.table.impl;

import com.hotent.core.db.datasource.JdbcTemplateUtil;
import com.hotent.core.page.PageBean;
import com.hotent.core.table.BaseTableMeta;
import com.hotent.core.table.ColumnModel;
import com.hotent.core.table.TableModel;
import com.hotent.core.table.colmap.H2ColumnMap;
import com.hotent.core.table.impl.H2TableMeta.1;
import com.hotent.core.table.impl.H2TableMeta.2;
import com.hotent.core.table.impl.H2TableMeta.3;
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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class H2TableMeta extends BaseTableMeta {
	@Resource
	private JdbcTemplate jdbcTemplate;
	private final String SQL_GET_COLUMNS = "SELECT A.TABLE_NAME, A.COLUMN_NAME, A.IS_NULLABLE, A.TYPE_NAME, A.CHARACTER_OCTET_LENGTH LENGTH, A.NUMERIC_PRECISION PRECISIONS, A.NUMERIC_SCALE SCALE, B.COLUMN_LIST, A.REMARKS FROM INFORMATION_SCHEMA.COLUMNS A  JOIN INFORMATION_SCHEMA.CONSTRAINTS B ON A.TABLE_NAME=B.TABLE_NAME WHERE  A.TABLE_SCHEMA=SCHEMA() AND B.CONSTRAINT_TYPE=\'PRIMARY KEY\' AND UPPER(A.TABLE_NAME)=UPPER(\'%s\') ";
	private final String SQL_GET_COLUMNS_BATCH = "SELECT A.TABLE_NAME, A.COLUMN_NAME, A.IS_NULLABLE, A.TYPE_NAME, A.CHARACTER_OCTET_LENGTH LENGTH, A.NUMERIC_PRECISION PRECISIONS, A.NUMERIC_SCALE SCALE, B.COLUMN_LIST, A.REMARKS FROM INFORMATION_SCHEMA.COLUMNS A  JOIN INFORMATION_SCHEMA.CONSTRAINTS B ON A.TABLE_NAME=B.TABLE_NAME WHERE  A.TABLE_SCHEMA=SCHEMA() AND B.CONSTRAINT_TYPE=\'PRIMARY KEY\' ";
	private final String SQL_GET_ALL_TABLE = "SELECT TABLE_NAME, REMARKS FROM INFORMATION_SCHEMA.TABLES T WHERE T.TABLE_TYPE=\'TABLE\' AND T.TABLE_SCHEMA=SCHEMA() ";
	RowMapper<TableModel> tableRowMapper = new 2(this);
	RowMapper<Map<String, Object>> tableMapRowMapper = new 3(this);

	public TableModel getTableByName(String tableName) {
		TableModel model = this.getTableModel(tableName);
		List columnList = this.getColumnsByTableName(tableName);
		model.setColumnList(columnList);
		return model;
	}

	private List<ColumnModel> getColumnsByTableName(String tableName) {
		String sql = String.format(
				"SELECT A.TABLE_NAME, A.COLUMN_NAME, A.IS_NULLABLE, A.TYPE_NAME, A.CHARACTER_OCTET_LENGTH LENGTH, A.NUMERIC_PRECISION PRECISIONS, A.NUMERIC_SCALE SCALE, B.COLUMN_LIST, A.REMARKS FROM INFORMATION_SCHEMA.COLUMNS A  JOIN INFORMATION_SCHEMA.CONSTRAINTS B ON A.TABLE_NAME=B.TABLE_NAME WHERE  A.TABLE_SCHEMA=SCHEMA() AND B.CONSTRAINT_TYPE=\'PRIMARY KEY\' AND UPPER(A.TABLE_NAME)=UPPER(\'%s\') ",
				new Object[]{tableName.toUpperCase()});
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
		String sql = "SELECT A.TABLE_NAME, A.COLUMN_NAME, A.IS_NULLABLE, A.TYPE_NAME, A.CHARACTER_OCTET_LENGTH LENGTH, A.NUMERIC_PRECISION PRECISIONS, A.NUMERIC_SCALE SCALE, B.COLUMN_LIST, A.REMARKS FROM INFORMATION_SCHEMA.COLUMNS A  JOIN INFORMATION_SCHEMA.CONSTRAINTS B ON A.TABLE_NAME=B.TABLE_NAME WHERE  A.TABLE_SCHEMA=SCHEMA() AND B.CONSTRAINT_TYPE=\'PRIMARY KEY\' ";
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
			sql = sql + " AND A.TABLE_NAME IN (" + columnModels.toString().toUpperCase() + ") ";
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

	private TableModel getTableModel(String tableName) {
		String sql = "SELECT TABLE_NAME, REMARKS FROM INFORMATION_SCHEMA.TABLES T WHERE T.TABLE_TYPE=\'TABLE\' AND T.TABLE_SCHEMA=SCHEMA()  AND UPPER(TABLE_NAME) = \'"
				+ tableName.toUpperCase() + "\'";
		TableModel tableModel = (TableModel) this.jdbcTemplate.queryForObject(sql, (Object[]) null,
				this.tableRowMapper);
		if (BeanUtils.isEmpty(tableModel)) {
			tableModel = new TableModel();
		}

		return tableModel;
	}

	public Map<String, String> getTablesByName(String tableName) {
		String sql = "SELECT TABLE_NAME, REMARKS FROM INFORMATION_SCHEMA.TABLES T WHERE T.TABLE_TYPE=\'TABLE\' AND T.TABLE_SCHEMA=SCHEMA() ";
		if (StringUtil.isNotEmpty(tableName)) {
			sql = sql + " AND UPPER(TABLE_NAME) LIKE \'%" + tableName.toUpperCase() + "%\'";
		}

		HashMap parameter = new HashMap();
		List list = this.jdbcTemplate.queryForList(sql, new Object[]{parameter, this.tableMapRowMapper});
		LinkedHashMap map = new LinkedHashMap();

		for (int i = 0; i < list.size(); ++i) {
			Map tmp = (Map) list.get(i);
			String name = tmp.get("name").toString();
			String comments = tmp.get("comment").toString();
			map.put(name, comments);
		}

		return map;
	}

	public Map<String, String> getTablesByName(List<String> names) {
		StringBuffer sb = new StringBuffer();
		Iterator sql = names.iterator();

		while (sql.hasNext()) {
			String parameter = (String) sql.next();
			sb.append("\'");
			sb.append(parameter);
			sb.append("\',");
		}

		sb.deleteCharAt(sb.length() - 1);
		String arg10 = "SELECT TABLE_NAME, REMARKS FROM INFORMATION_SCHEMA.TABLES T WHERE T.TABLE_TYPE=\'TABLE\' AND T.TABLE_SCHEMA=SCHEMA()  AND  UPPER(TABLE_NAME) IN ("
				+ sb.toString().toUpperCase() + ")";
		HashMap arg11 = new HashMap();
		List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(arg10, arg11,
				this.tableMapRowMapper);
		LinkedHashMap map = new LinkedHashMap();

		for (int i = 0; i < list.size(); ++i) {
			Map tmp = (Map) list.get(i);
			String name = tmp.get("name").toString();
			String comments = tmp.get("comment").toString();
			map.put(name, comments);
		}

		return map;
	}

	public List<TableModel> getTablesByName(String tableName, PageBean pageBean) throws Exception {
      String sql = "SELECT TABLE_NAME, REMARKS FROM INFORMATION_SCHEMA.TABLES T WHERE T.TABLE_TYPE=\'TABLE\' AND T.TABLE_SCHEMA=SCHEMA() ";
      if(StringUtil.isNotEmpty(tableName)) {
         sql = sql + " AND UPPER(TABLE_NAME) LIKE \'%" + tableName.toUpperCase() + "%\'";
      }

      1 rowMapper = new 1(this);
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

	public String getAllTableSql() {
		return "SELECT TABLE_NAME, REMARKS FROM INFORMATION_SCHEMA.TABLES T WHERE T.TABLE_TYPE=\'TABLE\' AND T.TABLE_SCHEMA=SCHEMA() ";
	}
}