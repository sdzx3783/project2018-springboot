package com.hotent.core.table.impl;

import com.hotent.core.model.TableIndex;
import com.hotent.core.page.PageBean;
import com.hotent.core.table.AbstractTableOperator;
import com.hotent.core.table.ColumnModel;
import com.hotent.core.table.TableModel;
import com.hotent.core.table.impl.MysqlTableOperator.1;
import com.hotent.core.table.impl.MysqlTableOperator.2;
import com.hotent.core.table.impl.MysqlTableOperator.3;
import com.hotent.core.table.impl.MysqlTableOperator.4;
import com.hotent.core.table.impl.MysqlTableOperator.5;
import com.hotent.core.util.StringUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MysqlTableOperator extends AbstractTableOperator {
	private int BATCHSIZE = 100;

	public void createTable(TableModel model) throws SQLException {
		List columnList = model.getColumnList();
		StringBuffer sb = new StringBuffer();
		String pkColumn = null;
		sb.append("CREATE TABLE " + model.getName() + " (\n");

		for (int i = 0; i < columnList.size(); ++i) {
			ColumnModel cm = (ColumnModel) columnList.get(i);
			sb.append(cm.getName()).append(" ");
			sb.append(this.getColumnType(cm.getColumnType(), cm.getCharLen(), cm.getIntLen(), cm.getDecimalLen()));
			sb.append(" ");
			String defaultValue = cm.getDefaultValue();
			if (StringUtil.isNotEmpty(defaultValue)) {
				sb.append(" default " + defaultValue);
			}

			if (cm.getIsPk()) {
				if (pkColumn == null) {
					pkColumn = cm.getName();
				} else {
					pkColumn = pkColumn + "," + cm.getName();
				}
			}

			if (cm.getComment() != null && cm.getComment().length() > 0) {
				sb.append(" COMMENT \'" + cm.getComment() + "\'");
			}

			sb.append(",\n");
		}

		if (pkColumn != null) {
			sb.append(" PRIMARY KEY (" + pkColumn + ")");
		}

		sb.append("\n)");
		if (model.getComment() != null && model.getComment().length() > 0) {
			sb.append(" COMMENT=\'" + model.getComment() + "\'");
		}

		sb.append(";");
		this.jdbcTemplate.execute(sb.toString());
	}

	public void updateTableComment(String tableName, String comment) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("ALTER TABLE ");
		sb.append(tableName);
		sb.append(" COMMENT \'");
		sb.append(comment);
		sb.append("\';\n");
		this.jdbcTemplate.execute(sb.toString());
	}

	public void addColumn(String tableName, ColumnModel model) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("ALTER TABLE ").append(tableName);
		sb.append(" ADD (");
		sb.append(model.getName()).append(" ");
		sb.append(this.getColumnType(model.getColumnType(), model.getCharLen(), model.getIntLen(),
				model.getDecimalLen()));
		if (!model.getIsNull()) {
			sb.append(" NOT NULL ");
		}

		if (model.getComment() != null && model.getComment().length() > 0) {
			sb.append(" COMMENT \'" + model.getComment() + "\'");
		}

		sb.append(");");
		this.jdbcTemplate.execute(sb.toString());
	}

	public void updateColumn(String tableName, String columnName, ColumnModel model) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("ALTER TABLE ").append(tableName);
		sb.append(" CHANGE " + columnName + " " + model.getName()).append(" ");
		sb.append(this.getColumnType(model.getColumnType(), model.getCharLen(), model.getIntLen(),
				model.getDecimalLen()));
		if (!model.getIsNull()) {
			sb.append(" NOT NULL ");
		}

		if (model.getComment() != null && model.getComment().length() > 0) {
			sb.append(" COMMENT \'" + model.getComment() + "\'");
		}

		sb.append(";");
		this.jdbcTemplate.execute(sb.toString());
	}

	private String getColumnType(String columnType, int charLen, int intLen, int decimalLen) {
		return "varchar".equals(columnType)
				? "VARCHAR(" + charLen + ')'
				: ("number".equals(columnType)
						? "DECIMAL(" + (intLen + decimalLen) + "," + decimalLen + ")"
						: ("date".equals(columnType)
								? "DATETIME"
								: ("int".equals(columnType)
										? "BIGINT(" + intLen + ")"
										: ("clob".equals(columnType) ? "TEXT" : ""))));
	}

	public void dropTable(String tableName) {
		String sql = "drop table if exists " + tableName;
		this.jdbcTemplate.execute(sql);
	}

	public void addForeignKey(String pkTableName, String fkTableName, String pkField, String fkField) {
		String sql = "ALTER TABLE " + fkTableName + " ADD CONSTRAINT fk_" + fkTableName + " FOREIGN KEY (" + fkField
				+ ") REFERENCES " + pkTableName + " (" + pkField + ") ON DELETE CASCADE";
		this.jdbcTemplate.execute(sql);
	}

	public void dropForeignKey(String tableName, String keyName) {
		String sql = "ALTER TABLE " + tableName + " DROP FOREIGN KEY " + keyName;
		this.jdbcTemplate.execute(sql);
	}

	public String getDbType() {
		return this.dbType;
	}

	public void createIndex(TableIndex index) {
		String sql = this.generateIndexDDL(index);
		this.jdbcTemplate.execute(sql);
	}

	public void dropIndex(String tableName, String indexName) {
		String sql = "drop index " + indexName;
		sql = sql + " on " + tableName;
		this.jdbcTemplate.execute(sql);
	}

	public TableIndex getIndex(String tableName, String indexName) {
		if (this.getIndexesByFuzzyMatching(tableName, indexName, Boolean.valueOf(true)).size() > 0) {
			TableIndex index = (TableIndex) this.getIndexesByFuzzyMatching(tableName, indexName, Boolean.valueOf(true))
					.get(0);

			try {
				index = this.dedicatePKIndex(index);
				return index;
			} catch (SQLException arg4) {
				arg4.printStackTrace();
			}
		}

		return null;
	}

	public List<TableIndex> getIndexesByTable(String tableName) {
		List indexList = this.getIndexesByFuzzyMatching(tableName, (String) null, Boolean.valueOf(true));
		return this.dedicatePKIndex(indexList);
	}

	public List<TableIndex> getIndexesByFuzzyMatching(String tableName, String indexName, Boolean getDDL) {
      String schema = this.getSchema();
      String sql = "SELECT TABLE_NAME,INDEX_NAME,COLUMN_NAME,NULLABLE,INDEX_TYPE,NON_UNIQUE FROM  INFORMATION_SCHEMA.STATISTICS WHERE 1=1";
      if(!StringUtil.isEmpty(schema)) {
         sql = sql + " AND TABLE_SCHEMA=\'" + schema + "\'";
      }

      if(!StringUtil.isEmpty(tableName)) {
         sql = sql + " AND UPPER(TABLE_NAME) LIKE UPPER(\'%" + tableName + "%\')";
      }

      if(!StringUtil.isEmpty(indexName)) {
         sql = sql + " AND UPPER(INDEX_NAME) like UPPER(\'%" + indexName + "%\')";
      }

      List indexes = this.jdbcTemplate.query(sql, new 1(this));
      ArrayList indexList = new ArrayList();
      Iterator i$ = indexes.iterator();

      TableIndex index;
      while(i$.hasNext()) {
         index = (TableIndex)i$.next();
         boolean found = false;
         Iterator i$1 = indexList.iterator();

         while(i$1.hasNext()) {
            TableIndex index1 = (TableIndex)i$1.next();
            if(index.getIndexName().equals(index1.getIndexName()) && index.getIndexTable().equals(index1.getIndexTable())) {
               index1.getIndexFields().add(index.getIndexFields().get(0));
               found = true;
               break;
            }
         }

         if(!found) {
            indexList.add(index);
         }
      }

      if(getDDL.booleanValue()) {
         i$ = indexList.iterator();

         while(i$.hasNext()) {
            index = (TableIndex)i$.next();
            index.setIndexDdl(this.generateIndexDDL(index));
         }
      }

      this.dedicatePKIndex((List)indexList);
      return indexList;
   }

	private List<TableIndex> dedicatePKIndex(List<TableIndex> indexList) {
		ArrayList tableNames = new ArrayList();
		Iterator tablePKColsMaps = indexList.iterator();

		while (tablePKColsMaps.hasNext()) {
			TableIndex i$ = (TableIndex) tablePKColsMaps.next();
			if (!tableNames.contains(i$.getIndexTable())) {
				tableNames.add(i$.getIndexTable());
			}
		}

		Map tablePKColsMaps1 = this.getTablesPKColsByNames(tableNames);
		Iterator i$1 = indexList.iterator();

		while (i$1.hasNext()) {
			TableIndex index = (TableIndex) i$1.next();
			if (this.isListEqual(index.getIndexFields(), (List) tablePKColsMaps1.get(index.getIndexTable()))) {
				index.setPkIndex(true);
			} else {
				index.setPkIndex(false);
			}
		}

		return indexList;
	}

	private TableIndex dedicatePKIndex(TableIndex index) throws SQLException {
		List pkCols = this.getPKColumns(index.getIndexName());
		if (this.isListEqual(index.getIndexFields(), pkCols)) {
			index.setPkIndex(true);
		} else {
			index.setPkIndex(false);
		}

		return index;
	}

	public List<TableIndex> getIndexesByFuzzyMatching(String tableName, String indexName, Boolean getDDL, PageBean pageBean) {
      String schema = this.getSchema();
      String sql = "SELECT TABLE_NAME,INDEX_NAME,COLUMN_NAME,NULLABLE,INDEX_TYPE,NON_UNIQUE FROM  INFORMATION_SCHEMA.STATISTICS WHERE 1=1";
      if(!StringUtil.isEmpty(schema)) {
         sql = sql + " AND TABLE_SCHEMA=\'" + schema + "\'";
      }

      if(!StringUtil.isEmpty(tableName)) {
         sql = sql + " AND UPPER(TABLE_NAME) LIKE UPPER(\'%" + tableName + "%\')";
      }

      if(!StringUtil.isEmpty(indexName)) {
         sql = sql + " AND UPPER(INDEX_NAME) like UPPER(\'%" + indexName + "%\')";
      }

      if(pageBean != null) {
         int indexes = pageBean.getCurrentPage();
         int indexList = pageBean.getPageSize();
         int i$ = (indexes - 1) * indexList;
         String index = this.dialect.getCountSql(sql);
         int found = this.jdbcTemplate.queryForInt(index);
         sql = this.dialect.getLimitString(sql, i$, indexList);
         pageBean.setTotalCount(found);
      }

      List indexes1 = this.jdbcTemplate.query(sql, new 2(this));
      ArrayList indexList1 = new ArrayList();
      Iterator i$2 = indexes1.iterator();

      TableIndex index1;
      while(i$2.hasNext()) {
         index1 = (TableIndex)i$2.next();
         boolean found1 = false;
         Iterator i$1 = indexList1.iterator();

         while(i$1.hasNext()) {
            TableIndex index1 = (TableIndex)i$1.next();
            if(index1.getIndexName().equals(index1.getIndexName()) && index1.getIndexTable().equals(index1.getIndexTable())) {
               index1.getIndexFields().add(index1.getIndexFields().get(0));
               found1 = true;
               break;
            }
         }

         if(!found1) {
            indexList1.add(index1);
         }
      }

      if(getDDL.booleanValue()) {
         i$2 = indexList1.iterator();

         while(i$2.hasNext()) {
            index1 = (TableIndex)i$2.next();
            index1.setIndexDdl(this.generateIndexDDL(index1));
         }
      }

      this.dedicatePKIndex((List)indexList1);
      return indexList1;
   }

	public void rebuildIndex(String tableName, String indexName) {
      String sql = "SHOW CREATE TABLE " + tableName;
      List ddls = this.jdbcTemplate.query(sql, new 3(this));
      String ddl = (String)ddls.get(0);
      Pattern pattern = Pattern.compile("ENGINE\\s*=\\s*\\S+", 2);
      Matcher matcher = pattern.matcher(ddl);
      if(matcher.find()) {
         String str = matcher.group();
         String sql_ = "ALTER TABLE " + tableName + " " + str;
         this.jdbcTemplate.execute(sql_);
      }

   }

	public List<String> getPKColumns(String tableName) throws SQLException {
      String schema = this.getSchema();
      String sql = "SELECT k.column_name FROM information_schema.table_constraints t JOIN information_schema.key_column_usage k USING(constraint_name,table_schema,table_name) WHERE t.constraint_type=\'PRIMARY KEY\' AND t.table_schema=\'" + schema + "\' " + "AND t.table_name=\'" + tableName + "\'";
      List columns = this.jdbcTemplate.query(sql, new 4(this));
      return columns;
   }

	public Map<String, List<String>> getPKColumns(List<String> tableNames) throws SQLException {
      StringBuffer sb = new StringBuffer();
      Iterator schema = tableNames.iterator();

      String sql;
      while(schema.hasNext()) {
         sql = (String)schema.next();
         sb.append("\'");
         sb.append(sql);
         sb.append("\',");
      }

      sb.deleteCharAt(sb.length() - 1);
      String schema1 = this.getSchema();
      sql = "SELECT t.table_name,k.column_name FROM information_schema.table_constraints t JOIN information_schema.key_column_usage k USING(constraint_name,table_schema,table_name) WHERE t.constraint_type=\'PRIMARY KEY\' AND t.table_schema=\'" + schema1 + "\' " + "AND t.table_name in (" + sb.toString().toUpperCase() + ")";
      HashMap columnsMap = new HashMap();
      List maps = this.jdbcTemplate.query(sql, new 5(this));
      Iterator i$ = maps.iterator();

      while(i$.hasNext()) {
         Map map = (Map)i$.next();
         if(columnsMap.containsKey(map.get("name"))) {
            ((List)columnsMap.get(map.get("name"))).add(map.get("column"));
         } else {
            ArrayList cols = new ArrayList();
            cols.add(map.get("column"));
            columnsMap.put(map.get("name"), cols);
         }
      }

      return columnsMap;
   }

	private String getSchema() {
		String schema = null;

		try {
			schema = this.jdbcTemplate.getDataSource().getConnection().getCatalog();
		} catch (SQLException arg2) {
			arg2.printStackTrace();
		}

		return schema;
	}

	private String generateIndexDDL(TableIndex index) {
		StringBuffer ddl = new StringBuffer();
		ddl.append("CREATE");
		if (index.isUnique()) {
			ddl.append(" UNIQUE");
		}

		ddl.append(" INDEX");
		ddl.append(" " + index.getIndexName());
		ddl.append(" USING");
		ddl.append(" " + index.getIndexType());
		ddl.append(" ON " + index.getIndexTable());
		Iterator i$ = index.getIndexFields().iterator();

		while (i$.hasNext()) {
			String column = (String) i$.next();
			ddl.append(column + ",");
		}

		if (!StringUtil.isEmpty(index.getIndexComment())) {
			ddl.append("COMMENT \'" + index.getIndexComment() + "\'");
		}

		ddl.replace(ddl.length() - 1, ddl.length(), ")");
		return ddl.toString();
	}

	private boolean isIndexExist(String tableName, String indexName) {
		String schema = this.getSchema();
		String sql = "SELECT COUNT(*) FROM  INFORMATION_SCHEMA.STATISTICS WHERE 1=1";
		if (!StringUtil.isEmpty(schema)) {
			sql = sql + " AND TABLE_SCHEMA=\'" + schema + "\'";
		}

		if (!StringUtil.isEmpty(tableName)) {
			sql = sql + " AND UPPER(TABLE_NAME) = UPPER(\'" + tableName + "\')";
		}

		if (!StringUtil.isEmpty(indexName)) {
			sql = sql + " AND UPPER(INDEX_NAME) = UPPER(\'" + indexName + "\')";
		}

		int count = this.jdbcTemplate.queryForInt(sql);
		return count > 0;
	}

	private Map<String, List<String>> getTablesPKColsByNames(List<String> tableNames) {
		HashMap tableMaps = new HashMap();
		ArrayList names = new ArrayList();

		for (int i = 1; i <= tableNames.size(); ++i) {
			names.add(tableNames.get(i - 1));
			if (i % this.BATCHSIZE == 0 || i == tableNames.size()) {
				try {
					Map map = this.getPKColumns((List) names);
					tableMaps.putAll(map);
					names.clear();
				} catch (SQLException arg6) {
					arg6.printStackTrace();
				}
			}
		}

		return tableMaps;
	}

	private boolean isListEqual(List list1, List list2) {
		return list1 == null
				? list2 == null
				: (list2 == null ? false : (list1.size() != list2.size() ? false : list1.containsAll(list2)));
	}

	public boolean isTableExist(String tableName) {
		String schema = this.getSchema();
		String sql = "select count(1) from information_schema.TABLES t where t.TABLE_SCHEMA=\'" + schema
				+ "\' and table_name =\'" + tableName.toUpperCase() + "\'";
		return this.jdbcTemplate.queryForInt(sql) > 0;
	}
}