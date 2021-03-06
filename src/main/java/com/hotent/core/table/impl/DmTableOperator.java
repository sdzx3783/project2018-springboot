package com.hotent.core.table.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.hotent.core.model.TableIndex;
import com.hotent.core.mybatis.Dialect;
import com.hotent.core.page.PageBean;
import com.hotent.core.table.AbstractTableOperator;
import com.hotent.core.table.ColumnModel;
import com.hotent.core.table.TableModel;
import com.hotent.core.util.StringUtil;

public class DmTableOperator extends AbstractTableOperator {
	protected int BATCHSIZE = 100;

	public void createTable(TableModel model) throws SQLException {
		List columnList = model.getColumnList();
		StringBuffer sb = new StringBuffer();
		String pkColumn = null;
		ArrayList columnCommentList = new ArrayList();
		sb.append("CREATE TABLE " + model.getName() + " (\n");

		for (int i$ = 0; i$ < columnList.size(); ++i$) {
			ColumnModel columnComment = (ColumnModel) columnList.get(i$);
			sb.append("    ").append(columnComment.getName()).append("    ");
			sb.append(this.getColumnType(columnComment.getColumnType(), columnComment.getCharLen(),
					columnComment.getIntLen(), columnComment.getDecimalLen()));
			sb.append(" ");
			if (columnComment.getIsPk()) {
				if (pkColumn == null) {
					pkColumn = columnComment.getName();
				} else {
					pkColumn = pkColumn + "," + columnComment.getName();
				}
			}

			if (StringUtil.isNotEmpty(columnComment.getDefaultValue())) {
				sb.append(" DEFAULT " + columnComment.getDefaultValue());
			}

			if (columnComment.getComment() != null && columnComment.getComment().length() > 0) {
				columnCommentList.add("COMMENT ON COLUMN " + model.getName() + "." + columnComment.getName() + " IS \'"
						+ columnComment.getComment() + "\'\n");
			}

			sb.append(",\n");
		}

		if (pkColumn != null) {
			sb.append("    CONSTRAINT PK_").append(model.getName()).append(" PRIMARY KEY (").append(pkColumn)
					.append(")");
		}

		sb.append("\n)");
		this.jdbcTemplate.execute(sb.toString());
		if (model.getComment() != null && model.getComment().length() > 0) {
			this.jdbcTemplate.execute("COMMENT ON TABLE " + model.getName() + " IS \'" + model.getComment() + "\'\n");
		}

		Iterator arg7 = columnCommentList.iterator();

		while (arg7.hasNext()) {
			String arg8 = (String) arg7.next();
			this.jdbcTemplate.execute(arg8);
		}

	}

	public void updateTableComment(String tableName, String comment) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("COMMENT ON TABLE ");
		sb.append(tableName);
		sb.append(" IS \'");
		sb.append(comment);
		sb.append("\'\n");
		this.jdbcTemplate.execute(sb.toString());
	}

	public void addColumn(String tableName, ColumnModel model) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("ALTER TABLE ").append(tableName);
		sb.append(" ADD ");
		sb.append(model.getName()).append(" ");
		sb.append(this.getColumnType(model.getColumnType(), model.getCharLen(), model.getIntLen(),
				model.getDecimalLen()));
		if (StringUtil.isNotEmpty(model.getDefaultValue())) {
			sb.append(" DEFAULT " + model.getDefaultValue());
		}

		sb.append("\n");
		this.jdbcTemplate.execute(sb.toString());
		if (model.getComment() != null && model.getComment().length() > 0) {
			this.jdbcTemplate.execute(
					"COMMENT ON COLUMN " + tableName + "." + model.getName() + " IS \'" + model.getComment() + "\'");
		}

	}

	public void updateColumn(String tableName, String columnName, ColumnModel model) throws SQLException {
		StringBuffer sb;
		if (!columnName.equals(model.getName())) {
			sb = (new StringBuffer("ALTER TABLE ")).append(tableName);
			sb.append(" RENAME COLUMN ").append(columnName).append(" TO ").append(model.getName());
			this.jdbcTemplate.execute(sb.toString());
		}

		sb = new StringBuffer();
		sb.append("ALTER TABLE ").append(tableName);
		sb.append(" MODIFY(" + model.getName()).append(" ");
		sb.append(this.getColumnType(model.getColumnType(), model.getCharLen(), model.getIntLen(),
				model.getDecimalLen()));
		if (!model.getIsNull()) {
			sb.append(" NOT NULL ");
		}

		sb.append(")");
		this.jdbcTemplate.execute(sb.toString());
		if (model.getComment() != null && model.getComment().length() > 0) {
			this.jdbcTemplate.execute(
					"COMMENT ON COLUMN " + tableName + "." + model.getName() + " IS\'" + model.getComment() + "\'");
		}

	}

	private String getColumnType(String columnType, int charLen, int intLen, int decimalLen) {
		return "varchar".equals(columnType)
				? "VARCHAR2(" + charLen + ')'
				: ("number".equals(columnType)
						? "NUMBER(" + (intLen + decimalLen) + "," + decimalLen + ")"
						: ("date".equals(columnType)
								? "DATE"
								: ("int".equals(columnType)
										? "NUMBER(" + intLen + ")"
										: ("clob".equals(columnType) ? "CLOB" : "VARCHAR2(50)"))));
	}

	public void dropTable(String tableName) {
		String selSql = "select count(*) amount from user_objects where object_name = upper(\'" + tableName + "\')";
		int rtn = this.jdbcTemplate.queryForObject(selSql,Integer.class);
		if (rtn > 0) {
			String sql = "drop table " + tableName;
			this.jdbcTemplate.execute(sql);
		}

	}

	public void addForeignKey(String pkTableName, String fkTableName, String pkField, String fkField) {
		String sql = " ALTER TABLE " + fkTableName + " ADD CONSTRAINT fk_" + fkTableName + " FOREIGN KEY (" + fkField
				+ ") REFERENCES " + pkTableName + " (" + pkField + ") ON DELETE CASCADE";
		this.jdbcTemplate.execute(sql);
	}

	public void dropForeignKey(String tableName, String keyName) {
		String sql = "ALTER   TABLE   " + tableName + "   DROP   CONSTRAINT  " + keyName;
		this.jdbcTemplate.execute(sql);
	}

	public void createIndex(TableIndex index) {
		String sql = this.generateIndexDDL(index);
		this.jdbcTemplate.execute(sql);
	}

	private String generateIndexDDL(TableIndex index) {
		StringBuffer sql = new StringBuffer();
		sql.append("CREATE ");
		if (!StringUtil.isEmpty(index.getIndexType()) && index.getIndexType().equalsIgnoreCase("BITMAP")) {
			sql.append("BITMAP ");
		}

		sql.append("INDEX ");
		sql.append(index.getIndexName());
		sql.append(" ON ");
		sql.append(index.getIndexTable());
		sql.append("(");
		Iterator i$ = index.getIndexFields().iterator();

		while (i$.hasNext()) {
			String field = (String) i$.next();
			sql.append(field);
			sql.append(",");
		}

		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		return sql.toString();
	}

	private boolean isIndexExist(String index) {
		String sql = "SELECT COUNT(*) FROM \"SYS\".\"USER_INDEXES\" WHERE INDEX_NAME = \'" + index + "\'";
		int count = this.jdbcTemplate.queryForObject(sql,Integer.class);
		return count > 0;
	}

	public String getDbType() {
		return this.dbType;
	}

	public void dropIndex(String tableName, String indexName) {
		String sql = "DROP INDEX " + indexName;
		this.jdbcTemplate.execute(sql);
	}

	public void rebuildIndex(String tableName, String indexName) {
		String sql = "ALTER INDEX " + indexName + " REBUILD";
		this.jdbcTemplate.execute(sql);
	}

	public TableIndex getIndex(String tableName, String indexName) {
      String sql = "SELECT IDX.TABLE_NAME,IDX.TABLE_TYPE,IDX.INDEX_NAME, IDX.INDEX_TYPE,IDX.UNIQUENESS,IDX.STATUS,IDC.COLUMN_NAME,DBMS_METADATA.GET_DDL(\'INDEX\',idx.INDEX_NAME) AS DDL FROM \"SYS\".\"USER_INDEXES\" IDX JOIN \"SYS\".\"USER_IND_COLUMNS\" IDC ON IDX.INDEX_NAME=IDC.INDEX_NAME  WHERE IDX.INDEX_NAME=UPPER(\'" + indexName + "\')";
      List indexes = this.jdbcTemplate.query(sql, new RowMapper<TableIndex>() {
    	  public TableIndex mapRow(ResultSet rs, int rowNum) throws SQLException {
    	        TableIndex index = new TableIndex();
    	        index.setIndexTable(rs.getString("TABLE_NAME"));
    	        index.setTableType(rs.getString("TABLE_TYPE"));
    	        index.setIndexName(rs.getString("INDEX_NAME"));
    	        index.setIndexType(rs.getString("INDEX_TYPE"));
    	        index.setUnique(rs.getString("UNIQUENESS").equalsIgnoreCase("UNIQUE"));
    	        index.setIndexStatus(rs.getString("STATUS"));
    	        index.setIndexDdl(rs.getString("DDL"));
    	        List<String> indexFields = new ArrayList();
    	        indexFields.add(rs.getString("COLUMN_NAME"));
    	        index.setIndexFields(indexFields);
    	        return index;
    	    }
      });
      List indexList = this.mergeIndex(indexes);
      return indexList.size() > 0?this.dedicatePKIndex((TableIndex)indexList.get(0)):null;
   }

	public List<TableIndex> getIndexesByTable(String tableName) {
      String sql = "SELECT IDX.TABLE_NAME,IDX.TABLE_TYPE,IDX.INDEX_NAME, IDX.INDEX_TYPE,IDX.UNIQUENESS,IDX.STATUS,IDC.COLUMN_NAME,DBMS_METADATA.GET_DDL(\'INDEX\',idx.INDEX_NAME) AS DDL FROM \"SYS\".\"USER_INDEXES\" IDX JOIN \"SYS\".\"USER_IND_COLUMNS\" IDC ON IDX.INDEX_NAME=IDC.INDEX_NAME  WHERE IDX.TABLE_NAME=UPPER(\'" + tableName + "\')";
      List indexes = this.jdbcTemplate.query(sql, new RowMapper<TableIndex>() {
    	  public TableIndex mapRow(ResultSet rs, int rowNum) throws SQLException {
    	        TableIndex index = new TableIndex();
    	        index.setIndexTable(rs.getString("TABLE_NAME"));
    	        index.setTableType(rs.getString("TABLE_TYPE"));
    	        index.setIndexName(rs.getString("INDEX_NAME"));
    	        index.setIndexType(rs.getString("INDEX_TYPE"));
    	        index.setUnique(rs.getString("UNIQUENESS").equalsIgnoreCase("UNIQUE"));
    	        index.setIndexStatus(rs.getString("STATUS"));
    	        index.setIndexDdl(rs.getString("DDL"));
    	        List<String> indexFields = new ArrayList();
    	        indexFields.add(rs.getString("COLUMN_NAME"));
    	        index.setIndexFields(indexFields);
    	        return index;
    	    }
      });
      List indexList = this.mergeIndex(indexes);
      this.dedicateFKIndex(indexList);
      this.dedicatePKIndex(indexList);
      return indexList;
   }

	public List<TableIndex> getIndexesByFuzzyMatching(String tableName, String indexName, Boolean getDDL) {
		return this.getIndexesByFuzzyMatching(tableName, indexName, getDDL, (PageBean) null);
	}

	public List<TableIndex> getIndexesByFuzzyMatching(String tableName, String indexName, Boolean getDDL, PageBean pageBean) {
      String sql;
      if(getDDL.booleanValue()) {
         sql = "SELECT IDX.TABLE_NAME,IDX.TABLE_TYPE,IDX.INDEX_NAME, IDX.INDEX_TYPE,IDX.UNIQUENESS,IDX.STATUS,IDC.COLUMN_NAME,DBMS_METADATA.GET_DDL(\'INDEX\',idx.INDEX_NAME) AS DDL FROM \"SYS\".\"USER_INDEXES\" IDX JOIN \"SYS\".\"USER_IND_COLUMNS\" IDC ON IDX.INDEX_NAME=IDC.INDEX_NAME WHERE 1=1";
      } else {
         sql = "SELECT IDX.TABLE_NAME,IDX.TABLE_TYPE,IDX.INDEX_NAME, IDX.INDEX_TYPE,IDX.UNIQUENESS,IDX.STATUS,IDC.COLUMN_NAME FROM \"SYS\".\"USER_INDEXES\" IDX JOIN \"SYS\".\"USER_IND_COLUMNS\" IDC ON IDX.INDEX_NAME=IDC.INDEX_NAME WHERE 1=1";
      }

      if(!StringUtil.isEmpty(tableName)) {
         sql = sql + " AND UPPER(IDX.TABLE_NAME) LIKE UPPER(\'%" + tableName + "%\')";
      }

      if(!StringUtil.isEmpty(indexName)) {
         sql = sql + " AND UPPER(IDX.INDEX_NAME) like UPPER(\'%" + indexName + "%\')";
      }

      if(pageBean != null) {
         int indexes = pageBean.getCurrentPage();
         int indexList = pageBean.getPageSize();
         int offset = (indexes - 1) * indexList;
         String totalSql = this.dialect.getCountSql(sql);
         int total = this.jdbcTemplate.queryForObject(totalSql,Integer.class);
         sql = this.dialect.getLimitString(sql, offset, indexList);
         pageBean.setTotalCount(total);
      }

      this.logger.debug(sql);
      List indexes1 = this.jdbcTemplate.query(sql, new RowMapper<TableIndex>() {
    	  public TableIndex mapRow(ResultSet rs, int rowNum) throws SQLException {
    	        TableIndex index = new TableIndex();
    	        index.setIndexTable(rs.getString("TABLE_NAME"));
    	        index.setTableType(rs.getString("TABLE_TYPE"));
    	        index.setIndexName(rs.getString("INDEX_NAME"));
    	        index.setIndexType(rs.getString("INDEX_TYPE"));
    	        index.setUnique(rs.getString("UNIQUENESS").equalsIgnoreCase("UNIQUE"));
    	        index.setIndexStatus(rs.getString("STATUS"));
    	        List<String> indexFields = new ArrayList();
    	        indexFields.add(rs.getString("COLUMN_NAME"));
    	        index.setIndexFields(indexFields);
    	        return index;
    	    }
      });
      List indexList1 = this.mergeIndex(indexes1);
      this.dedicatePKIndex(indexList1);
      return indexList1;
   }

	private List<TableIndex> mergeIndex(List<TableIndex> indexes) {
		ArrayList indexList = new ArrayList();
		Iterator i$ = indexes.iterator();

		while (i$.hasNext()) {
			TableIndex index = (TableIndex) i$.next();
			boolean found = false;
			Iterator i$1 = indexList.iterator();

			while (i$1.hasNext()) {
				TableIndex index1 = (TableIndex) i$1.next();
				if (index.getIndexName().equals(index1.getIndexName())
						&& index.getIndexTable().equals(index1.getIndexTable())) {
					index1.getIndexFields().add(index.getIndexFields().get(0));
					found = true;
					break;
				}
			}

			if (!found) {
				indexList.add(index);
			}
		}

		return indexList;
	}

	public List<String> getPKColumns(String tableName) throws SQLException {
      String sql = "SELECT cols.column_name FROM \"SYS\".\"USER_CONSTRAINTS\" CONS, \"SYS\".\"USER_CONS_COLUMNS\" COLS WHERE UPPER(cols.table_name) = UPPER(\'" + tableName + "\')" + " AND cons.constraint_type = \'P\'  AND cols.position=1" + " AND cons.constraint_name = cols.constraint_name" + " AND cons.owner = cols.owner";
      List columns = this.jdbcTemplate.query(sql, new RowMapper<String>() {
    	  public String mapRow(ResultSet rs, int rowNum) throws SQLException {
    	        String column = rs.getString(1);
    	        return column;
    	    }
      });
      return columns;
   }

	public Map<String, List<String>> getPKColumns(List<String> tableNames) throws SQLException {
      StringBuffer sb = new StringBuffer();
      Iterator sql = tableNames.iterator();

      while(sql.hasNext()) {
         String columnsMap = (String)sql.next();
         sb.append("\'");
         sb.append(columnsMap);
         sb.append("\',");
      }

      sb.deleteCharAt(sb.length() - 1);
      String sql1 = "SELECT cols.table_name,cols.column_name FROM \"SYS\".\"USER_CONSTRAINTS\" CONS, \"SYS\".\"USER_CONS_COLUMNS\" COLS WHERE UPPER(cols.table_name) in (" + sb.toString().toUpperCase() + ")" + " AND cons.constraint_type = \'P\' AND COLS.POSITION=1" + " AND cons.constraint_name = cols.constraint_name" + " AND CONS.OWNER = COLS.OWNER";
      HashMap columnsMap1 = new HashMap();
      List maps = this.jdbcTemplate.query(sql1, new RowMapper<Map<String, String>>(){
    	  public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
    	        String table = rs.getString(1);
    	        String column = rs.getString(2);
    	        Map<String, String> map = new HashMap();
    	        map.put("name", table);
    	        map.put("column", column);
    	        return map;
    	    }
      });
      Iterator i$ = maps.iterator();

      while(i$.hasNext()) {
         Map map = (Map)i$.next();
         if(columnsMap1.containsKey(map.get("name"))) {
            ((List)columnsMap1.get(map.get("name"))).add(map.get("column"));
         } else {
            ArrayList cols = new ArrayList();
            cols.add(map.get("column"));
            columnsMap1.put(map.get("name"), cols);
         }
      }

      return columnsMap1;
   }

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
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

	private boolean isListEqual(List<?> list1, List<?> list2) {
		return list1 == null
				? list2 == null
				: (list2 == null ? false : (list1.size() != list2.size() ? false : list1.containsAll(list2)));
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

	private TableIndex dedicatePKIndex(TableIndex index) {
		try {
			List pkCols = this.getPKColumns(index.getIndexName());
			if (this.isListEqual(index.getIndexFields(), pkCols)) {
				index.setPkIndex(true);
			} else {
				index.setPkIndex(false);
			}
		} catch (SQLException arg3) {
			arg3.printStackTrace();
		}

		return index;
	}

	private List<TableIndex> dedicateFKIndex(List<TableIndex> indexList) {
		ArrayList tableNames = new ArrayList();
		Iterator tableFKColsMaps = indexList.iterator();

		while (tableFKColsMaps.hasNext()) {
			TableIndex i$ = (TableIndex) tableFKColsMaps.next();
			if (!tableNames.contains(i$.getIndexTable())) {
				tableNames.add(i$.getIndexTable());
			}
		}

		Map tableFKColsMaps1 = this.getTablesFKColsByNames(tableNames);
		Iterator i$1 = indexList.iterator();

		while (i$1.hasNext()) {
			TableIndex index = (TableIndex) i$1.next();
			if (this.isListEqual(index.getIndexFields(), (List) tableFKColsMaps1.get(index.getIndexTable()))) {
				indexList.remove(index);
			}
		}

		return indexList;
	}

	private Map<String, List<String>> getTablesFKColsByNames(List<String> tableNames) {
		HashMap tableMaps = new HashMap();
		ArrayList names = new ArrayList();

		for (int i = 1; i <= tableNames.size(); ++i) {
			names.add(tableNames.get(i - 1));
			if (i % this.BATCHSIZE == 0 || i == tableNames.size()) {
				try {
					Map map = this.getFKColumns(names);
					tableMaps.putAll(map);
					names.clear();
				} catch (SQLException arg6) {
					arg6.printStackTrace();
				}
			}
		}

		return tableMaps;
	}

	private Map<String, List<String>> getFKColumns(List<String> tableNames) throws SQLException {
      StringBuffer sb = new StringBuffer();
      Iterator sql = tableNames.iterator();

      while(sql.hasNext()) {
         String columnsMap = (String)sql.next();
         sb.append("\'");
         sb.append(columnsMap);
         sb.append("\',");
      }

      sb.deleteCharAt(sb.length() - 1);
      String sql1 = "SELECT cols.table_name,cols.column_name FROM \"SYS\".\"USER_CONSTRAINTS\" CONS, \"SYS\".\"USER_CONS_COLUMNS\" COLS WHERE UPPER(cols.table_name) in (" + sb.toString().toUpperCase() + ")" + " AND cons.constraint_type = \'F\' AND COLS.POSITION=1" + " AND cons.constraint_name = cols.constraint_name" + " AND CONS.OWNER = COLS.OWNER";
      HashMap columnsMap1 = new HashMap();
      List maps = this.jdbcTemplate.query(sql1, new RowMapper<Map<String, String>>(){
    	  public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
    	        String table = rs.getString(1);
    	        String column = rs.getString(2);
    	        Map<String, String> map = new HashMap();
    	        map.put("name", table);
    	        map.put("column", column);
    	        return map;
    	    }
      });
      Iterator i$ = maps.iterator();

      while(i$.hasNext()) {
         Map map = (Map)i$.next();
         if(columnsMap1.containsKey(map.get("name"))) {
            ((List)columnsMap1.get(map.get("name"))).add(map.get("column"));
         } else {
            ArrayList cols = new ArrayList();
            cols.add(map.get("column"));
            columnsMap1.put(map.get("name"), cols);
         }
      }

      return columnsMap1;
   }

	public boolean isTableExist(String tableName) {
		StringBuffer sql = new StringBuffer();
		sql.append("select COUNT(1) from user_tables t where t.TABLE_NAME=\'").append(tableName.toUpperCase())
				.append("\'");
		return this.jdbcTemplate.queryForObject(sql.toString(),Integer.class) > 0;
	}
}