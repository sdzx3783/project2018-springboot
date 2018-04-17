/*   1:    */ package com.hotent.core.table.impl;
/*   2:    */ 
/*   3:    */ import com.hotent.core.db.datasource.JdbcTemplateUtil;
/*   4:    */ import com.hotent.core.page.PageBean;
/*   5:    */ import com.hotent.core.table.BaseDbView;
/*   6:    */ import com.hotent.core.table.ColumnModel;
/*   7:    */ import com.hotent.core.table.IDbView;
/*   8:    */ import com.hotent.core.table.TableModel;
/*   9:    */ import com.hotent.core.table.colmap.MySqlColumnMap;
/*  10:    */ import com.hotent.core.util.StringUtil;
/*  11:    */ import java.sql.ResultSet;
/*  12:    */ import java.sql.SQLException;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.HashMap;
/*  15:    */ import java.util.Iterator;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import java.util.Map.Entry;
/*  19:    */ import java.util.Set;
/*  20:    */ import javax.annotation.Resource;
/*  21:    */ import org.springframework.jdbc.core.JdbcTemplate;
/*  22:    */ import org.springframework.jdbc.core.RowMapper;
/*  23:    */ import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
/*  24:    */ import org.springframework.stereotype.Component;
/*  25:    */ 
/*  26:    */ @Component
/*  27:    */ public class MysqlDbView
/*  28:    */   extends BaseDbView
/*  29:    */   implements IDbView
/*  30:    */ {
/*  31:    */   @Resource
/*  32:    */   private JdbcTemplate jdbcTemplate;
/*  33:    */   private static final String sqlAllView = "SELECT TABLE_NAME FROM information_schema.`TABLES` WHERE TABLE_TYPE LIKE 'VIEW'";
/*  34:    */   private static final String SQL_GET_COLUMNS = "SELECT TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_OCTET_LENGTH LENGTH,NUMERIC_PRECISION PRECISIONS,NUMERIC_SCALE SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='%s' ";
/*  35:    */   static final String SQL_GET_COLUMNS_BATCH = "SELECT TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_OCTET_LENGTH LENGTH, NUMERIC_PRECISION PRECISIONS,NUMERIC_SCALE SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS  WHERE TABLE_SCHEMA=DATABASE() ";
/*  36:    */   
/*  37:    */   public List<String> getViews(String viewName)
/*  38:    */     throws SQLException
/*  39:    */   {
/*  40: 56 */     String sql = "SELECT TABLE_NAME FROM information_schema.`TABLES` WHERE TABLE_TYPE LIKE 'VIEW'";
/*  41: 57 */     if (StringUtil.isNotEmpty(viewName)) {
/*  42: 58 */       sql = sql + " AND TABLE_NAME LIKE '" + viewName + "%'";
/*  43:    */     }
/*  44: 60 */     return this.jdbcTemplate.queryForList(sql, String.class);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public List<String> getViews(String viewName, PageBean pageBean)
/*  48:    */     throws Exception
/*  49:    */   {
/*  50: 70 */     String sql = "SELECT TABLE_NAME FROM information_schema.`TABLES` WHERE TABLE_TYPE LIKE 'VIEW'";
/*  51: 71 */     if (StringUtil.isNotEmpty(viewName)) {
/*  52: 72 */       sql = sql + " AND TABLE_NAME LIKE '" + viewName + "%'";
/*  53:    */     }
/*  54: 74 */     return getForList(sql, pageBean, String.class, "mysql");
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String getType(String type)
/*  58:    */   {
/*  59: 81 */     type = type.toLowerCase();
/*  60: 82 */     if (type.indexOf("number") > -1) {
/*  61: 83 */       return "number";
/*  62:    */     }
/*  63: 84 */     if ((type.indexOf("date") > -1) || (type.indexOf("time") > -1)) {
/*  64: 85 */       return "date";
/*  65:    */     }
/*  66: 87 */     if (type.indexOf("char") > -1) {
/*  67: 88 */       return "varchar";
/*  68:    */     }
/*  69: 90 */     return "varchar";
/*  70:    */   }
/*  71:    */   
/*  72:    */   public List<TableModel> getViewsByName(String viewName, PageBean pageBean)
/*  73:    */     throws Exception
/*  74:    */   {
/*  75: 98 */     String sql = "SELECT TABLE_NAME FROM information_schema.`TABLES` WHERE TABLE_TYPE LIKE 'VIEW'";
/*  76: 99 */     if (StringUtil.isNotEmpty(viewName)) {
/*  77:100 */       sql = sql + " AND TABLE_NAME LIKE '" + viewName + "%'";
/*  78:    */     }
/*  79:103 */     RowMapper<TableModel> rowMapper = new RowMapper()
/*  80:    */     {
/*  81:    */       public TableModel mapRow(ResultSet rs, int row)
/*  82:    */         throws SQLException
/*  83:    */       {
/*  84:107 */         TableModel tableModel = new TableModel();
/*  85:108 */         tableModel.setName(rs.getString("table_name"));
/*  86:109 */         tableModel.setComment(tableModel.getName());
/*  87:110 */         return tableModel;
/*  88:    */       }
/*  89:112 */     };
/*  90:113 */     List<TableModel> tableModels = getForList(sql, pageBean, rowMapper, "mysql");
/*  91:    */     
/*  92:115 */     List<String> tableNames = new ArrayList();
/*  93:117 */     for (TableModel model : tableModels) {
/*  94:118 */       tableNames.add(model.getName());
/*  95:    */     }
/*  96:121 */     Map<String, List<ColumnModel>> tableColumnsMap = getColumnsByTableName(tableNames);
/*  97:123 */     for (Iterator i$ = tableColumnsMap.entrySet().iterator(); i$.hasNext();)
/*  98:    */     {
/*  99:123 */       entry = (Map.Entry)i$.next();
/* 100:125 */       for (TableModel model : tableModels) {
/* 101:126 */         if (model.getName().equalsIgnoreCase((String)entry.getKey())) {
/* 102:127 */           model.setColumnList((List)entry.getValue());
/* 103:    */         }
/* 104:    */       }
/* 105:    */     }
/* 106:    */     Map.Entry<String, List<ColumnModel>> entry;
/* 107:131 */     return tableModels;
/* 108:    */   }
/* 109:    */   
/* 110:    */   private List<ColumnModel> getColumnsByTableName(String tableName)
/* 111:    */   {
/* 112:141 */     String sql = String.format("SELECT TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_OCTET_LENGTH LENGTH,NUMERIC_PRECISION PRECISIONS,NUMERIC_SCALE SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='%s' ", new Object[] { tableName });
/* 113:    */     
/* 114:    */ 
/* 115:144 */     Map<String, Object> map = new HashMap();
/* 116:    */     
/* 117:146 */     List<ColumnModel> list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, map, new MySqlColumnMap());
/* 118:147 */     for (ColumnModel model : list) {
/* 119:148 */       model.setTableName(tableName);
/* 120:    */     }
/* 121:150 */     return list;
/* 122:    */   }
/* 123:    */   
/* 124:    */   private Map<String, List<ColumnModel>> getColumnsByTableName(List<String> tableNames)
/* 125:    */   {
/* 126:160 */     String sql = "SELECT TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_OCTET_LENGTH LENGTH, NUMERIC_PRECISION PRECISIONS,NUMERIC_SCALE SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS  WHERE TABLE_SCHEMA=DATABASE() ";
/* 127:161 */     Map<String, List<ColumnModel>> map = new HashMap();
/* 128:162 */     if ((tableNames != null) && (tableNames.size() == 0)) {
/* 129:163 */       return map;
/* 130:    */     }
/* 131:165 */     StringBuffer buf = new StringBuffer();
/* 132:166 */     for (String str : tableNames) {
/* 133:167 */       buf.append("'" + str + "',");
/* 134:    */     }
/* 135:169 */     buf.deleteCharAt(buf.length() - 1);
/* 136:170 */     sql = sql + " AND TABLE_NAME IN (" + buf.toString() + ") ";
/* 137:    */     
/* 138:    */ 
/* 139:    */ 
/* 140:174 */     List<ColumnModel> columnModels = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, new HashMap(), new MySqlColumnMap());
/* 141:175 */     for (ColumnModel columnModel : columnModels)
/* 142:    */     {
/* 143:176 */       String tableName = columnModel.getTableName();
/* 144:177 */       if (map.containsKey(tableName))
/* 145:    */       {
/* 146:178 */         ((List)map.get(tableName)).add(columnModel);
/* 147:    */       }
/* 148:    */       else
/* 149:    */       {
/* 150:180 */         List<ColumnModel> cols = new ArrayList();
/* 151:181 */         cols.add(columnModel);
/* 152:182 */         map.put(tableName, cols);
/* 153:    */       }
/* 154:    */     }
/* 155:185 */     return map;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void createOrRep(String viewName, String sql)
/* 159:    */     throws Exception
/* 160:    */   {
/* 161:190 */     String getSql = "CREATE OR REPLACE VIEW " + viewName + " as (" + sql + ")";
/* 162:    */     
/* 163:192 */     this.jdbcTemplate.execute(getSql);
/* 164:    */   }
/* 165:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.impl.MysqlDbView
 * JD-Core Version:    0.7.0.1
 */