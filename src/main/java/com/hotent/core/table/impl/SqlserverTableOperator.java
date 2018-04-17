package com.hotent.core.table.impl;

import com.hotent.core.model.TableIndex;
import com.hotent.core.page.PageBean;
import com.hotent.core.table.AbstractTableOperator;
import com.hotent.core.table.ColumnModel;
import com.hotent.core.table.TableModel;
import com.hotent.core.table.impl.SqlserverTableOperator.1;
import com.hotent.core.table.impl.SqlserverTableOperator.2;
import com.hotent.core.table.impl.SqlserverTableOperator.3;
import com.hotent.core.table.impl.SqlserverTableOperator.4;
import com.hotent.core.util.StringUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SqlserverTableOperator extends AbstractTableOperator {
	public void createTable(TableModel model) throws SQLException {
		List columnList = model.getColumnList();
		StringBuffer createTableSql = new StringBuffer();
		String pkColumn = null;
		ArrayList columnCommentList = new ArrayList();
		createTableSql.append("CREATE TABLE " + model.getName() + " (\n");

		for (int i$ = 0; i$ < columnList.size(); ++i$) {
			ColumnModel columnComment = (ColumnModel) columnList.get(i$);
			createTableSql.append("    ").append(columnComment.getName()).append("    ");
			createTableSql.append(this.getColumnType(columnComment.getColumnType(), columnComment.getCharLen(),
					columnComment.getIntLen(), columnComment.getDecimalLen()));
			createTableSql.append(" ");
			if (StringUtil.isNotEmpty(columnComment.getDefaultValue())) {
				createTableSql.append(" DEFAULT " + columnComment.getDefaultValue());
			}

			if (!columnComment.getIsNull()) {
				createTableSql.append(" NOT NULL ");
			}

			if (columnComment.getIsPk()) {
				if (pkColumn == null) {
					pkColumn = columnComment.getName();
				} else {
					pkColumn = pkColumn + "," + columnComment.getName();
				}
			}

			if (columnComment.getComment() != null && columnComment.getComment().length() > 0) {
				StringBuffer comment = new StringBuffer(
						"EXEC sys.sp_addextendedproperty @name=N\'MS_Description\', @value=N\'");
				comment.append(columnComment.getComment()).append(
						"\' ,@level0type=N\'SCHEMA\', @level0name=N\'dbo\', @level1type=N\'TABLE\', @level1name=N\'")
						.append(model.getName()).append("\', @level2type=N\'COLUMN\', @level2name=N\'")
						.append(columnComment.getName()).append("\'");
				columnCommentList.add(comment.toString());
			}

			createTableSql.append(",\n");
		}

		if (pkColumn != null) {
			createTableSql.append("    CONSTRAINT PK_").append(model.getName()).append(" PRIMARY KEY (")
					.append(pkColumn).append(")");
		}

		createTableSql.append("\n)");
		this.jdbcTemplate.execute(createTableSql.toString());
		if (model.getComment() != null && model.getComment().length() > 0) {
			StringBuffer arg8 = new StringBuffer(
					"EXEC sys.sp_addextendedproperty @name=N\'MS_Description\', @value=N\'");
			arg8.append(model.getComment()).append(
					"\' ,@level0type=N\'SCHEMA\', @level0name=N\'dbo\', @level1type=N\'TABLE\', @level1name=N\'")
					.append(model.getName()).append("\'");
			this.jdbcTemplate.execute(arg8.toString());
		}

		Iterator arg9 = columnCommentList.iterator();

		while (arg9.hasNext()) {
			String arg10 = (String) arg9.next();
			this.jdbcTemplate.execute(arg10);
		}

	}

	public void updateTableComment(String tableName, String comment) throws SQLException {
		StringBuffer commentSql = new StringBuffer(
				"EXEC sys.sp_updateextendedproperty @name=N\'MS_Description\', @value=N\'");
		commentSql.append(comment)
				.append("\' ,@level0type=N\'SCHEMA\', @level0name=N\'dbo\', @level1type=N\'TABLE\', @level1name=N\'")
				.append(tableName).append("\'");
		this.jdbcTemplate.execute(commentSql.toString());
	}

	public void addColumn(String tableName, ColumnModel model) throws SQLException {
		StringBuffer alterSql = new StringBuffer();
		alterSql.append("ALTER TABLE ").append(tableName);
		alterSql.append(" ADD ");
		alterSql.append(model.getName()).append(" ");
		alterSql.append(this.getColumnType(model.getColumnType(), model.getCharLen(), model.getIntLen(),
				model.getDecimalLen()));
		if (StringUtil.isNotEmpty(model.getDefaultValue())) {
			alterSql.append(" DEFAULT " + model.getDefaultValue());
		}

		alterSql.append("\n");
		this.jdbcTemplate.execute(alterSql.toString());
		if (model.getComment() != null && model.getComment().length() > 0) {
			StringBuffer comment = new StringBuffer(
					"EXEC sys.sp_addextendedproperty @name=N\'MS_Description\', @value=N\'");
			comment.append(model.getComment()).append(
					"\' ,@level0type=N\'SCHEMA\', @level0name=N\'dbo\', @level1type=N\'TABLE\', @level1name=N\'")
					.append(tableName).append("\', @level2type=N\'COLUMN\', @level2name=N\'").append(model.getName())
					.append("\'");
			this.jdbcTemplate.execute(comment.toString());
		}

	}

	public void updateColumn(String tableName, String columnName, ColumnModel model) throws SQLException {
		StringBuffer alterSql;
		if (!columnName.equals(model.getName())) {
			alterSql = new StringBuffer("EXEC sp_rename \'");
			alterSql.append(tableName).append(".[").append(columnName).append("]\',\'").append(model.getName())
					.append("\', \'COLUMN\'");
			this.jdbcTemplate.execute(alterSql.toString());
		}

		alterSql = new StringBuffer();
		alterSql.append("ALTER TABLE ").append(tableName);
		alterSql.append(" ALTER COLUMN " + model.getName()).append(" ");
		alterSql.append(this.getColumnType(model.getColumnType(), model.getCharLen(), model.getIntLen(),
				model.getDecimalLen()));
		if (!model.getIsNull()) {
			alterSql.append(" NOT NULL ");
		}

		this.jdbcTemplate.execute(alterSql.toString());
		if (model.getComment() != null && model.getComment().length() > 0) {
			StringBuffer comment = new StringBuffer(
					"EXEC sys.sp_updateextendedproperty @name=N\'MS_Description\', @value=N\'");
			comment.append(model.getComment()).append(
					"\' ,@level0type=N\'SCHEMA\', @level0name=N\'dbo\', @level1type=N\'TABLE\', @level1name=N\'")
					.append(tableName).append("\', @level2type=N\'COLUMN\', @level2name=N\'").append(model.getName())
					.append("\'");
			this.jdbcTemplate.execute(comment.toString());
		}

	}

	private String getColumnType(String columnType, int charLen, int intLen, int decimalLen) {
		return "varchar".equals(columnType)
				? "VARCHAR(" + charLen + ')'
				: ("number".equals(columnType)
						? "NUMERIC(" + (intLen + decimalLen) + "," + decimalLen + ")"
						: ("date".equals(columnType)
								? "DATETIME"
								: ("int".equals(columnType)
										? "NUMERIC(" + intLen + ")"
										: ("clob".equals(columnType) ? "TEXT" : ""))));
	}

	public void dropTable(String tableName) {
		String sql = "IF OBJECT_ID(N\'" + tableName + "\', N\'U\') IS NOT NULL  DROP TABLE " + tableName;
		this.jdbcTemplate.execute(sql);
	}

	public void addForeignKey(String pkTableName, String fkTableName, String pkField, String fkField) {
		String sql = "  ALTER TABLE " + fkTableName + " ADD CONSTRAINT fk_" + fkTableName + " FOREIGN KEY (" + fkField
				+ ") REFERENCES " + pkTableName + " (" + pkField + ")   ON DELETE CASCADE";
		this.jdbcTemplate.execute(sql);
	}

	public void dropForeignKey(String tableName, String keyName) {
		String sql = "ALTER   TABLE   " + tableName + "   DROP   CONSTRAINT  " + keyName;
		this.jdbcTemplate.execute(sql);
	}

	public String getDbType() {
		return this.dbType;
	}

	public void createIndex(TableIndex index) throws SQLException {
		String sql = this.generateIndexDDL(index);
		this.jdbcTemplate.execute(sql);
	}

	public void dropIndex(String tableName, String indexName) {
		String sql = "DROP INDEX " + indexName + " ON " + tableName;
		this.jdbcTemplate.execute(sql);
	}

	public TableIndex getIndex(String tableName, String indexName) {
      String sql = "SELECT IDX.NAME AS INDEX_NAME,IDX.TYPE AS INDEX_TYPE,OBJ.NAME AS TABLE_NAME,OBJ.TYPE AS TABLE_TYPE, IDX.IS_DISABLED AS IS_DISABLED,IDX.IS_UNIQUE AS IS_UNIQUE, IDX.IS_PRIMARY_KEY AS IS_PK_INDEX, COL.NAME AS COLUMN_NAME FROM  SYS.INDEXES  IDX  JOIN SYS.OBJECTS OBJ ON IDX.OBJECT_ID=OBJ.OBJECT_ID  JOIN SYS.INDEX_COLUMNS IDC ON OBJ.OBJECT_ID=IDC.OBJECT_ID AND IDX.INDEX_ID=IDC.INDEX_ID JOIN SYS.COLUMNS COL ON COL.OBJECT_ID=IDC.OBJECT_ID AND COL.COLUMN_ID = IDC.COLUMN_ID WHERE OBJ.NAME =\'" + tableName + "\' " + "AND IDX.NAME =\'" + indexName + "\'";
      List indexes = this.jdbcTemplate.query(sql, new 1(this));
      ArrayList indexList = new ArrayList();
      Iterator i$ = indexes.iterator();

      TableIndex index;
      while(i$.hasNext()) {
         index = (TableIndex)i$.next();
         Iterator i$1 = indexList.iterator();

         while(i$1.hasNext()) {
            TableIndex index1 = (TableIndex)i$1.next();
            if(index.getIndexName().equals(index1.getIndexName()) && index.getIndexTable().equals(index1.getIndexTable())) {
               index1.getIndexFields().add(index.getIndexFields().get(0));
            }
         }

         indexList.add(index);
      }

      i$ = indexList.iterator();

      while(i$.hasNext()) {
         index = (TableIndex)i$.next();
         index.setIndexDdl(this.generateIndexDDL(index));
      }

      if(indexList.size() > 0) {
         return (TableIndex)indexList.get(0);
      } else {
         return null;
      }
   }

	public List<TableIndex> getIndexesByTable(String tableName) {
      String sql = "SELECT IDX.NAME AS INDEX_NAME,IDX.TYPE AS INDEX_TYPE,OBJ.NAME AS TABLE_NAME,OBJ.TYPE AS TABLE_TYPE, IDX.IS_DISABLED AS IS_DISABLED,IDX.IS_UNIQUE AS IS_UNIQUE, IDX.IS_PRIMARY_KEY AS IS_PK_INDEX, COL.NAME AS COLUMN_NAME FROM  SYS.INDEXES  IDX  JOIN SYS.OBJECTS OBJ ON IDX.OBJECT_ID=OBJ.OBJECT_ID  JOIN SYS.INDEX_COLUMNS IDC ON OBJ.OBJECT_ID=IDC.OBJECT_ID AND IDX.INDEX_ID=IDC.INDEX_ID JOIN SYS.COLUMNS COL ON COL.OBJECT_ID=IDC.OBJECT_ID AND COL.COLUMN_ID = IDC.COLUMN_ID WHERE OBJ.NAME =\'" + tableName + "\'";
      List indexes = this.jdbcTemplate.query(sql, new 2(this));
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

      i$ = indexList.iterator();

      while(i$.hasNext()) {
         index = (TableIndex)i$.next();
         index.setIndexDdl(this.generateIndexDDL(index));
      }

      return indexList;
   }

	public List<TableIndex> getIndexesByFuzzyMatching(String tableName, String indexName, Boolean getDDL) {
		return this.getIndexesByFuzzyMatching(tableName, indexName, getDDL, (PageBean) null);
	}

	public List<TableIndex> getIndexesByFuzzyMatching(String tableName, String indexName, Boolean getDDL, PageBean pageBean) {
      String sql = "SELECT IDX.NAME AS INDEX_NAME,IDX.TYPE AS INDEX_TYPE,OBJ.NAME AS TABLE_NAME,OBJ.TYPE AS TABLE_TYPE, IDX.IS_DISABLED AS IS_DISABLED,IDX.IS_UNIQUE AS IS_UNIQUE, IDX.IS_PRIMARY_KEY AS IS_PK_INDEX, COL.NAME AS COLUMN_NAME FROM  SYS.INDEXES  IDX  JOIN SYS.OBJECTS OBJ ON IDX.OBJECT_ID=OBJ.OBJECT_ID  JOIN SYS.INDEX_COLUMNS IDC ON OBJ.OBJECT_ID=IDC.OBJECT_ID AND IDX.INDEX_ID=IDC.INDEX_ID JOIN SYS.COLUMNS COL ON COL.OBJECT_ID=IDC.OBJECT_ID AND COL.COLUMN_ID = IDC.COLUMN_ID WHERE 1=1";
      if(!StringUtil.isEmpty(indexName)) {
         sql = sql + " AND IDX.NAME LIKE \'%" + indexName + "%\'";
      }

      if(!StringUtil.isEmpty(tableName)) {
         sql = sql + " AND OBJ.NAME LIKE \'%" + tableName + "%\' ";
      }

      if(pageBean != null) {
         int indexes = pageBean.getCurrentPage();
         int indexList = pageBean.getPageSize();
         int i$ = (indexes - 1) * indexList;
         String index = this.dialect.getCountSql(sql);
         int i$1 = this.jdbcTemplate.queryForInt(index);
         sql = this.dialect.getLimitString(sql, i$, indexList);
         pageBean.setTotalCount(i$1);
      }

      List indexes1 = this.jdbcTemplate.query(sql, new 3(this));
      ArrayList indexList1 = new ArrayList();
      Iterator i$2 = indexes1.iterator();

      TableIndex index1;
      while(i$2.hasNext()) {
         index1 = (TableIndex)i$2.next();
         Iterator i$3 = indexList1.iterator();

         while(i$3.hasNext()) {
            TableIndex index1 = (TableIndex)i$3.next();
            if(index1.getIndexName().equals(index1.getIndexName()) && index1.getIndexTable().equals(index1.getIndexTable())) {
               index1.getIndexFields().add(index1.getIndexFields().get(0));
            }
         }

         indexList1.add(index1);
      }

      if(getDDL.booleanValue()) {
         i$2 = indexList1.iterator();

         while(i$2.hasNext()) {
            index1 = (TableIndex)i$2.next();
            index1.setIndexDdl(this.generateIndexDDL(index1));
         }
      }

      return indexList1;
   }

	public void rebuildIndex(String tableName, String indexName) {
		String sql = "DBCC DBREINDEX (\'" + tableName + "\',\'" + indexName + "\',80)";
		this.jdbcTemplate.execute(sql);
	}

	public List<String> getPKColumns(String tableName) throws SQLException {
      String sql = "SELECT C.COLUMN_NAME COLUMN_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS PK ,INFORMATION_SCHEMA.KEY_COLUMN_USAGE C WHERE \tPK.TABLE_NAME = \'%S\' AND\tCONSTRAINT_TYPE = \'PRIMARY KEY\' AND\tC.TABLE_NAME = PK.TABLE_NAME AND\tC.CONSTRAINT_NAME = PK.CONSTRAINT_NAME ";
      sql = String.format(sql, new Object[]{tableName});
      List columns = this.jdbcTemplate.query(sql, new 4(this));
      return columns;
   }

	private String generateIndexDDL(TableIndex index) {
		StringBuffer sql = new StringBuffer();
		sql.append("CREATE ");
		if (index.isUnique()) {
			sql.append(" UNIQUE ");
		}

		if (!StringUtil.isEmpty(index.getIndexType()) && index.getIndexType().equalsIgnoreCase("CLUSTERED")) {
			sql.append(" CLUSTERED ");
		}

		sql.append(" INDEX ");
		sql.append(index.getIndexName());
		sql.append(" ON ");
		sql.append(index.getIndexTable());
		sql.append(" (");
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

	private String mapTableType(String type) {
		type = type.trim();
		String tableType = null;
		if (type.equalsIgnoreCase("U")) {
			tableType = TableIndex.TABLE_TYPE_TABLE;
		} else if (type.equalsIgnoreCase("V")) {
			tableType = TableIndex.TABLE_TYPE_VIEW;
		}

		return tableType;
	}

	private String mapIndexType(int type) {
		String indexType = null;
		switch (type) {
			case 0 :
				indexType = TableIndex.INDEX_TYPE_HEAP;
				break;
			case 1 :
				indexType = TableIndex.INDEX_TYPE_CLUSTERED;
				break;
			case 2 :
				indexType = TableIndex.INDEX_TYPE_NONCLUSTERED;
				break;
			case 3 :
				indexType = TableIndex.INDEX_TYPE_XML;
				break;
			case 4 :
				indexType = TableIndex.INDEX_TYPE_SPATIAL;
		}

		return indexType;
	}

	private boolean mapIndexUnique(int type) {
		boolean indexUnique = false;
		switch (type) {
			case 0 :
				indexUnique = false;
				break;
			case 1 :
				indexUnique = true;
		}

		return indexUnique;
	}

	private boolean mapPKIndex(int type) {
		boolean pkIndex = false;
		switch (type) {
			case 0 :
				pkIndex = false;
				break;
			case 1 :
				pkIndex = true;
		}

		return pkIndex;
	}

	private String mapIndexStatus(int type) {
		String tableType = null;
		switch (type) {
			case 0 :
				tableType = TableIndex.INDEX_STATUS_VALIDATE;
				break;
			case 1 :
				tableType = TableIndex.INDEX_STATUS_INVALIDATE;
		}

		return tableType;
	}

	public boolean isTableExist(String tableName) {
		String sql = "select count(1) from sysobjects where name=\'" + tableName.toUpperCase() + "\'";
		return this.jdbcTemplate.queryForInt(sql) > 0;
	}
}