/*   1:    */ package com.hotent.core.table;
/*   2:    */ 
/*   3:    */ import com.hotent.core.model.TableIndex;
/*   4:    */ import com.hotent.core.mybatis.Dialect;
/*   5:    */ import com.hotent.core.page.PageBean;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import org.slf4j.Logger;
/*  10:    */ import org.slf4j.LoggerFactory;
/*  11:    */ import org.springframework.jdbc.core.JdbcTemplate;
/*  12:    */ 
/*  13:    */ public class AbstractTableOperator
/*  14:    */   implements ITableOperator
/*  15:    */ {
/*  16:    */   protected String dbType;
/*  17:    */   protected JdbcTemplate jdbcTemplate;
/*  18: 24 */   protected Logger logger = LoggerFactory.getLogger(getClass());
/*  19:    */   protected Dialect dialect;
/*  20:    */   
/*  21:    */   public void setDbType(String dbType)
/*  22:    */   {
/*  23: 33 */     this.dbType = dbType;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public String getDbType()
/*  27:    */   {
/*  28: 38 */     return this.dbType;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setJdbcTemplate(JdbcTemplate template)
/*  32:    */   {
/*  33: 43 */     this.jdbcTemplate = template;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void createTable(TableModel model)
/*  37:    */     throws SQLException
/*  38:    */   {
/*  39: 48 */     throw new UnsupportedOperationException("Current Implement not supported");
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void dropTable(String tableName)
/*  43:    */   {
/*  44: 53 */     throw new UnsupportedOperationException("Current Implement not supported");
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void updateTableComment(String tableName, String comment)
/*  48:    */     throws SQLException
/*  49:    */   {
/*  50: 59 */     throw new UnsupportedOperationException("Current Implement not supported");
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void addColumn(String tableName, ColumnModel model)
/*  54:    */     throws SQLException
/*  55:    */   {
/*  56: 65 */     throw new UnsupportedOperationException("Current Implement not supported");
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void updateColumn(String tableName, String columnName, ColumnModel model)
/*  60:    */     throws SQLException
/*  61:    */   {
/*  62: 71 */     throw new UnsupportedOperationException("Current Implement not supported");
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void addForeignKey(String pkTableName, String fkTableName, String pkField, String fkField)
/*  66:    */   {
/*  67: 77 */     throw new UnsupportedOperationException("Current Implement not supported");
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void dropForeignKey(String tableName, String keyName)
/*  71:    */   {
/*  72: 82 */     throw new UnsupportedOperationException("Current Implement not supported");
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void createIndex(TableIndex index)
/*  76:    */     throws SQLException
/*  77:    */   {
/*  78: 87 */     throw new UnsupportedOperationException("Current Implement not supported");
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void dropIndex(String tableName, String indexName)
/*  82:    */   {
/*  83: 92 */     throw new UnsupportedOperationException("Current Implement not supported");
/*  84:    */   }
/*  85:    */   
/*  86:    */   public TableIndex getIndex(String tableName, String indexName)
/*  87:    */   {
/*  88: 97 */     throw new UnsupportedOperationException("Current Implement not supported");
/*  89:    */   }
/*  90:    */   
/*  91:    */   public List<TableIndex> getIndexesByTable(String tableName)
/*  92:    */   {
/*  93:102 */     throw new UnsupportedOperationException("Current Implement not supported");
/*  94:    */   }
/*  95:    */   
/*  96:    */   public List<TableIndex> getIndexesByFuzzyMatching(String tableName, String indexName, Boolean getDDL)
/*  97:    */   {
/*  98:108 */     throw new UnsupportedOperationException("Current Implement not supported");
/*  99:    */   }
/* 100:    */   
/* 101:    */   public List<TableIndex> getIndexesByFuzzyMatching(String tableName, String indexName, Boolean getDDL, PageBean pageBean)
/* 102:    */   {
/* 103:114 */     throw new UnsupportedOperationException("Current Implement not supported");
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void rebuildIndex(String tableName, String indexName)
/* 107:    */   {
/* 108:119 */     throw new UnsupportedOperationException("Current Implement not supported");
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setDialect(Dialect dialect)
/* 112:    */   {
/* 113:125 */     this.dialect = dialect;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public List<String> getPKColumns(String tableName)
/* 117:    */     throws SQLException
/* 118:    */   {
/* 119:130 */     throw new UnsupportedOperationException("Current Implement not supported");
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Map<String, List<String>> getPKColumns(List<String> tableNames)
/* 123:    */     throws SQLException
/* 124:    */   {
/* 125:137 */     throw new UnsupportedOperationException("Current Implement not supported");
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void createIndex(String tableName, String fieldName)
/* 129:    */   {
/* 130:142 */     String regex = "(?im)" + TableModel.CUSTOMER_TABLE_PREFIX;
/* 131:143 */     String shortTableName = tableName.replaceFirst(regex, "");
/* 132:144 */     String sqlIndex = "create index idx_" + shortTableName + "_" + fieldName + " on " + tableName + "(" + fieldName + ")";
/* 133:145 */     this.jdbcTemplate.execute(sqlIndex);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public boolean isTableExist(String tableName)
/* 137:    */   {
/* 138:152 */     return true;
/* 139:    */   }
/* 140:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.AbstractTableOperator
 * JD-Core Version:    0.7.0.1
 */