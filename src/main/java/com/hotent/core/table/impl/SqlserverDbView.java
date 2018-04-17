/*   1:    */ package com.hotent.core.table.impl;
/*   2:    */ 
/*   3:    */ import com.hotent.core.db.datasource.JdbcTemplateUtil;
/*   4:    */ import com.hotent.core.page.PageBean;
/*   5:    */ import com.hotent.core.table.BaseDbView;
/*   6:    */ import com.hotent.core.table.ColumnModel;
/*   7:    */ import com.hotent.core.table.IDbView;
/*   8:    */ import com.hotent.core.table.TableModel;
/*   9:    */ import com.hotent.core.table.colmap.SqlServerColumnMap;
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
/*  27:    */ public class SqlserverDbView
/*  28:    */   extends BaseDbView
/*  29:    */   implements IDbView
/*  30:    */ {
/*  31:    */   @Resource
/*  32:    */   private JdbcTemplate jdbcTemplate;
/*  33: 38 */   private final String sqlAllView = "select name from sysobjects where xtype='V'";
/*  34: 39 */   private final String SQL_GET_COLUMNS = "SELECT B.NAME TABLE_NAME,A.NAME NAME, C.NAME TYPENAME, A.MAX_LENGTH LENGTH, A.IS_NULLABLE IS_NULLABLE,A.PRECISION PRECISION,A.SCALE SCALE,  (SELECT COUNT(*) FROM SYS.IDENTITY_COLUMNS WHERE SYS.IDENTITY_COLUMNS.OBJECT_ID = A.OBJECT_ID AND A.COLUMN_ID = SYS.IDENTITY_COLUMNS.COLUMN_ID) AS AUTOGEN, (SELECT CAST(VALUE AS VARCHAR) FROM SYS.EXTENDED_PROPERTIES WHERE SYS.EXTENDED_PROPERTIES.MAJOR_ID = A.OBJECT_ID AND SYS.EXTENDED_PROPERTIES.MINOR_ID = A.COLUMN_ID) AS DESCRIPTION,  0 AS IS_PK FROM  SYS.COLUMNS A, SYS.VIEWS B, SYS.TYPES C WHERE  A.OBJECT_ID = B.OBJECT_ID  AND A.SYSTEM_TYPE_ID=C.SYSTEM_TYPE_ID AND B.NAME='%s' AND C.NAME<>'SYSNAME' ORDER BY A.COLUMN_ID";
/*  35: 53 */   private final String SQL_GET_COLUMNS_BATCH = "SELECT B.NAME TABLE_NAME,A.NAME NAME, C.NAME TYPENAME, A.MAX_LENGTH LENGTH, A.IS_NULLABLE IS_NULLABLE,A.PRECISION PRECISION,A.SCALE SCALE,  (SELECT COUNT(*) FROM SYS.IDENTITY_COLUMNS WHERE SYS.IDENTITY_COLUMNS.OBJECT_ID = A.OBJECT_ID AND A.COLUMN_ID = SYS.IDENTITY_COLUMNS.COLUMN_ID) AS AUTOGEN, (SELECT CAST(VALUE AS VARCHAR) FROM SYS.EXTENDED_PROPERTIES WHERE SYS.EXTENDED_PROPERTIES.MAJOR_ID = A.OBJECT_ID AND SYS.EXTENDED_PROPERTIES.MINOR_ID = A.COLUMN_ID) AS DESCRIPTION,  0 AS IS_PK FROM  SYS.COLUMNS A, SYS.VIEWS B, SYS.TYPES C WHERE  A.OBJECT_ID = B.OBJECT_ID  AND A.SYSTEM_TYPE_ID=C.SYSTEM_TYPE_ID AND C.NAME<>'SYSNAME' ";
/*  36:    */   
/*  37:    */   public List<String> getViews(String viewName)
/*  38:    */   {
/*  39: 67 */     String sql = "select name from sysobjects where xtype='V'";
/*  40: 68 */     if (StringUtil.isNotEmpty(viewName)) {
/*  41: 69 */       sql = sql + " and name like '" + viewName + "%'";
/*  42:    */     }
/*  43: 71 */     return this.jdbcTemplate.queryForList(sql, String.class);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getType(String type)
/*  47:    */   {
/*  48: 79 */     if ((type.indexOf("int") > -1) || (type.equals("real")) || (type.equals("numeric")) || (type.indexOf("money") > -1)) {
/*  49: 80 */       return "number";
/*  50:    */     }
/*  51: 81 */     if (type.indexOf("date") > -1) {
/*  52: 82 */       return "date";
/*  53:    */     }
/*  54: 84 */     return "varchar";
/*  55:    */   }
/*  56:    */   
/*  57:    */   public List<String> getViews(String viewName, PageBean pageBean)
/*  58:    */     throws Exception
/*  59:    */   {
/*  60: 91 */     String sql = "select name from sysobjects where xtype='V'";
/*  61: 92 */     if (StringUtil.isNotEmpty(viewName)) {
/*  62: 93 */       sql = sql + " AND NAME LIKE '" + viewName + "%'";
/*  63:    */     }
/*  64: 95 */     return getForList(sql, pageBean, String.class, "mssql");
/*  65:    */   }
/*  66:    */   
/*  67:    */   public List<TableModel> getViewsByName(String viewName, PageBean pageBean)
/*  68:    */     throws Exception
/*  69:    */   {
/*  70:103 */     String sql = "select name from sysobjects where xtype='V'";
/*  71:104 */     if (StringUtil.isNotEmpty(viewName)) {
/*  72:105 */       sql = sql + " AND NAME LIKE '" + viewName + "%'";
/*  73:    */     }
/*  74:108 */     RowMapper<TableModel> rowMapper = new RowMapper()
/*  75:    */     {
/*  76:    */       public TableModel mapRow(ResultSet rs, int row)
/*  77:    */         throws SQLException
/*  78:    */       {
/*  79:112 */         TableModel tableModel = new TableModel();
/*  80:113 */         tableModel.setName(rs.getString("NAME"));
/*  81:114 */         tableModel.setComment(tableModel.getName());
/*  82:115 */         return tableModel;
/*  83:    */       }
/*  84:117 */     };
/*  85:118 */     List<TableModel> tableModels = getForList(sql, pageBean, rowMapper, "mssql");
/*  86:    */     
/*  87:120 */     List<String> tableNames = new ArrayList();
/*  88:122 */     for (TableModel model : tableModels) {
/*  89:123 */       tableNames.add(model.getName());
/*  90:    */     }
/*  91:126 */     Map<String, List<ColumnModel>> tableColumnsMap = getColumnsByTableName(tableNames);
/*  92:128 */     for (Iterator i$ = tableColumnsMap.entrySet().iterator(); i$.hasNext();)
/*  93:    */     {
/*  94:128 */       entry = (Map.Entry)i$.next();
/*  95:130 */       for (TableModel model : tableModels) {
/*  96:131 */         if (model.getName().equalsIgnoreCase((String)entry.getKey())) {
/*  97:132 */           model.setColumnList((List)entry.getValue());
/*  98:    */         }
/*  99:    */       }
/* 100:    */     }
/* 101:    */     Map.Entry<String, List<ColumnModel>> entry;
/* 102:136 */     return tableModels;
/* 103:    */   }
/* 104:    */   
/* 105:    */   private List<ColumnModel> getColumnsByTableName(String tableName)
/* 106:    */   {
/* 107:147 */     String sql = String.format("SELECT B.NAME TABLE_NAME,A.NAME NAME, C.NAME TYPENAME, A.MAX_LENGTH LENGTH, A.IS_NULLABLE IS_NULLABLE,A.PRECISION PRECISION,A.SCALE SCALE,  (SELECT COUNT(*) FROM SYS.IDENTITY_COLUMNS WHERE SYS.IDENTITY_COLUMNS.OBJECT_ID = A.OBJECT_ID AND A.COLUMN_ID = SYS.IDENTITY_COLUMNS.COLUMN_ID) AS AUTOGEN, (SELECT CAST(VALUE AS VARCHAR) FROM SYS.EXTENDED_PROPERTIES WHERE SYS.EXTENDED_PROPERTIES.MAJOR_ID = A.OBJECT_ID AND SYS.EXTENDED_PROPERTIES.MINOR_ID = A.COLUMN_ID) AS DESCRIPTION,  0 AS IS_PK FROM  SYS.COLUMNS A, SYS.VIEWS B, SYS.TYPES C WHERE  A.OBJECT_ID = B.OBJECT_ID  AND A.SYSTEM_TYPE_ID=C.SYSTEM_TYPE_ID AND B.NAME='%s' AND C.NAME<>'SYSNAME' ORDER BY A.COLUMN_ID", new Object[] { tableName });
/* 108:    */     
/* 109:    */ 
/* 110:150 */     Map map = new HashMap();
/* 111:151 */     List<ColumnModel> list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, map, new SqlServerColumnMap());
/* 112:152 */     for (ColumnModel model : list) {
/* 113:153 */       model.setTableName(tableName);
/* 114:    */     }
/* 115:155 */     return list;
/* 116:    */   }
/* 117:    */   
/* 118:    */   private Map<String, List<ColumnModel>> getColumnsByTableName(List<String> tableNames)
/* 119:    */   {
/* 120:165 */     String sql = "SELECT B.NAME TABLE_NAME,A.NAME NAME, C.NAME TYPENAME, A.MAX_LENGTH LENGTH, A.IS_NULLABLE IS_NULLABLE,A.PRECISION PRECISION,A.SCALE SCALE,  (SELECT COUNT(*) FROM SYS.IDENTITY_COLUMNS WHERE SYS.IDENTITY_COLUMNS.OBJECT_ID = A.OBJECT_ID AND A.COLUMN_ID = SYS.IDENTITY_COLUMNS.COLUMN_ID) AS AUTOGEN, (SELECT CAST(VALUE AS VARCHAR) FROM SYS.EXTENDED_PROPERTIES WHERE SYS.EXTENDED_PROPERTIES.MAJOR_ID = A.OBJECT_ID AND SYS.EXTENDED_PROPERTIES.MINOR_ID = A.COLUMN_ID) AS DESCRIPTION,  0 AS IS_PK FROM  SYS.COLUMNS A, SYS.VIEWS B, SYS.TYPES C WHERE  A.OBJECT_ID = B.OBJECT_ID  AND A.SYSTEM_TYPE_ID=C.SYSTEM_TYPE_ID AND C.NAME<>'SYSNAME' ";
/* 121:166 */     Map<String, List<ColumnModel>> map = new HashMap();
/* 122:167 */     if ((tableNames != null) && (tableNames.size() == 0)) {
/* 123:168 */       return map;
/* 124:    */     }
/* 125:170 */     StringBuffer buf = new StringBuffer();
/* 126:171 */     for (String str : tableNames) {
/* 127:172 */       buf.append("'" + str + "',");
/* 128:    */     }
/* 129:174 */     buf.deleteCharAt(buf.length() - 1);
/* 130:175 */     sql = sql + " AND B.NAME IN (" + buf.toString() + ") ";
/* 131:    */     
/* 132:    */ 
/* 133:    */ 
/* 134:179 */     List<ColumnModel> columnModels = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, new HashMap(), new SqlServerColumnMap());
/* 135:180 */     for (ColumnModel columnModel : columnModels)
/* 136:    */     {
/* 137:181 */       String tableName = columnModel.getTableName();
/* 138:182 */       if (map.containsKey(tableName))
/* 139:    */       {
/* 140:183 */         ((List)map.get(tableName)).add(columnModel);
/* 141:    */       }
/* 142:    */       else
/* 143:    */       {
/* 144:185 */         List<ColumnModel> cols = new ArrayList();
/* 145:186 */         cols.add(columnModel);
/* 146:187 */         map.put(tableName, cols);
/* 147:    */       }
/* 148:    */     }
/* 149:190 */     return map;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void createOrRep(String viewName, String sql)
/* 153:    */     throws Exception
/* 154:    */   {
/* 155:197 */     String sql_drop_view = "IF EXISTS (SELECT * FROM sysobjects WHERE xtype='V' AND name = '" + viewName + "')" + " DROP VIEW " + viewName;
/* 156:    */     
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:203 */     String viewSql = "CREATE VIEW " + viewName + " AS " + sql;
/* 162:204 */     this.jdbcTemplate.execute(sql_drop_view);
/* 163:205 */     this.jdbcTemplate.execute(viewSql);
/* 164:    */   }
/* 165:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.impl.SqlserverDbView
 * JD-Core Version:    0.7.0.1
 */