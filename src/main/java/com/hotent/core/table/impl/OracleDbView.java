/*   1:    */ package com.hotent.core.table.impl;
/*   2:    */ 
/*   3:    */ import com.hotent.core.db.datasource.JdbcTemplateUtil;
/*   4:    */ import com.hotent.core.page.PageBean;
/*   5:    */ import com.hotent.core.table.BaseDbView;
/*   6:    */ import com.hotent.core.table.ColumnModel;
/*   7:    */ import com.hotent.core.table.IDbView;
/*   8:    */ import com.hotent.core.table.TableModel;
/*   9:    */ import com.hotent.core.table.colmap.OracleColumnMap;
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
/*  27:    */ public class OracleDbView
/*  28:    */   extends BaseDbView
/*  29:    */   implements IDbView
/*  30:    */ {
/*  31:    */   @Resource
/*  32:    */   private JdbcTemplate jdbcTemplate;
/*  33:    */   private static final String sqlAllView = "select view_name from user_views ";
/*  34:    */   private static final String SQL_GET_COLUMNS = "SELECT A.TABLE_NAME TABLE_NAME,A.COLUMN_NAME NAME,A.DATA_TYPE TYPENAME,  A.DATA_LENGTH LENGTH,A.DATA_PRECISION PRECISION, A.DATA_SCALE SCALE,A.DATA_DEFAULT, A.NULLABLE NULLABLE, DECODE(B.COMMENTS,NULL,A.COLUMN_NAME,B.COMMENTS) DESCRIPTION, 0 AS IS_PK FROM  USER_TAB_COLUMNS A,USER_COL_COMMENTS B WHERE A.COLUMN_NAME=B.COLUMN_NAME  AND  A.TABLE_NAME = B.TABLE_NAME  AND  UPPER(A.TABLE_NAME) =UPPER('%s') ORDER BY A.COLUMN_ID";
/*  35: 57 */   private final String SQL_GET_COLUMNS_BATCH = "SELECT  A.TABLE_NAME TABLE_NAME,A.COLUMN_NAME NAME,A.DATA_TYPE TYPENAME,A.DATA_LENGTH LENGTH, A.DATA_PRECISION PRECISION,A.DATA_SCALE SCALE,A.DATA_DEFAULT,A.NULLABLE, DECODE(B.COMMENTS,NULL,A.COLUMN_NAME,B.COMMENTS) DESCRIPTION,  0 AS IS_PK  FROM USER_TAB_COLUMNS A,USER_COL_COMMENTS B WHERE A.COLUMN_NAME=B.COLUMN_NAME AND    A.TABLE_NAME = B.TABLE_NAME ";
/*  36:    */   
/*  37:    */   public List<String> getViews(String viewName)
/*  38:    */     throws SQLException
/*  39:    */   {
/*  40: 73 */     String sql = "select view_name from user_views ";
/*  41: 74 */     if (StringUtil.isNotEmpty(viewName)) {
/*  42: 75 */       sql = sql + " where lower(view_name) like '" + viewName.toLowerCase() + "%'";
/*  43:    */     }
/*  44: 77 */     return this.jdbcTemplate.queryForList(sql, String.class);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String getType(String type)
/*  48:    */   {
/*  49: 84 */     type = type.toLowerCase();
/*  50: 85 */     if (type.indexOf("number") > -1) {
/*  51: 86 */       return "number";
/*  52:    */     }
/*  53: 87 */     if (type.indexOf("date") > -1) {
/*  54: 88 */       return "date";
/*  55:    */     }
/*  56: 90 */     if (type.indexOf("char") > -1) {
/*  57: 91 */       return "varchar";
/*  58:    */     }
/*  59: 93 */     return "varchar";
/*  60:    */   }
/*  61:    */   
/*  62:    */   public List<String> getViews(String viewName, PageBean pageBean)
/*  63:    */     throws Exception
/*  64:    */   {
/*  65: 99 */     String sql = "select view_name from user_views ";
/*  66:100 */     if (StringUtil.isNotEmpty(viewName)) {
/*  67:101 */       sql = sql + " where lower(view_name) like '" + viewName.toLowerCase() + "%'";
/*  68:    */     }
/*  69:103 */     return getForList(sql, pageBean, String.class, "oracle");
/*  70:    */   }
/*  71:    */   
/*  72:    */   public List<TableModel> getViewsByName(String viewName, PageBean pageBean)
/*  73:    */     throws Exception
/*  74:    */   {
/*  75:108 */     String sql = "select view_name from user_views ";
/*  76:109 */     if (StringUtil.isNotEmpty(viewName)) {
/*  77:110 */       sql = sql + " WHERE UPPER(VIEW_NAME) LIKE '%" + viewName.toUpperCase() + "%'";
/*  78:    */     }
/*  79:112 */     RowMapper<TableModel> rowMapper = new RowMapper()
/*  80:    */     {
/*  81:    */       public TableModel mapRow(ResultSet rs, int row)
/*  82:    */         throws SQLException
/*  83:    */       {
/*  84:116 */         TableModel tableModel = new TableModel();
/*  85:117 */         tableModel.setName(rs.getString("VIEW_NAME"));
/*  86:118 */         return tableModel;
/*  87:    */       }
/*  88:120 */     };
/*  89:121 */     List<TableModel> tableModels = getForList(sql, pageBean, rowMapper, "oracle");
/*  90:    */     
/*  91:123 */     List<String> tableNames = new ArrayList();
/*  92:125 */     for (TableModel model : tableModels) {
/*  93:126 */       tableNames.add(model.getName());
/*  94:    */     }
/*  95:129 */     Map<String, List<ColumnModel>> tableColumnsMap = getColumnsByTableName(tableNames);
/*  96:131 */     for (Iterator i$ = tableColumnsMap.entrySet().iterator(); i$.hasNext();)
/*  97:    */     {
/*  98:131 */       entry = (Map.Entry)i$.next();
/*  99:133 */       for (TableModel model : tableModels) {
/* 100:134 */         if (model.getName().equalsIgnoreCase((String)entry.getKey())) {
/* 101:135 */           model.setColumnList((List)entry.getValue());
/* 102:    */         }
/* 103:    */       }
/* 104:    */     }
/* 105:    */     Map.Entry<String, List<ColumnModel>> entry;
/* 106:140 */     return tableModels;
/* 107:    */   }
/* 108:    */   
/* 109:    */   private List<ColumnModel> getColumnsByTableName(String tableName)
/* 110:    */   {
/* 111:151 */     String sql = String.format("SELECT A.TABLE_NAME TABLE_NAME,A.COLUMN_NAME NAME,A.DATA_TYPE TYPENAME,  A.DATA_LENGTH LENGTH,A.DATA_PRECISION PRECISION, A.DATA_SCALE SCALE,A.DATA_DEFAULT, A.NULLABLE NULLABLE, DECODE(B.COMMENTS,NULL,A.COLUMN_NAME,B.COMMENTS) DESCRIPTION, 0 AS IS_PK FROM  USER_TAB_COLUMNS A,USER_COL_COMMENTS B WHERE A.COLUMN_NAME=B.COLUMN_NAME  AND  A.TABLE_NAME = B.TABLE_NAME  AND  UPPER(A.TABLE_NAME) =UPPER('%s') ORDER BY A.COLUMN_ID", new Object[] { tableName });
/* 112:    */     
/* 113:    */ 
/* 114:154 */     Map map = new HashMap();
/* 115:155 */     List<ColumnModel> list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, map, new OracleColumnMap());
/* 116:156 */     for (ColumnModel model : list) {
/* 117:157 */       model.setTableName(tableName);
/* 118:    */     }
/* 119:159 */     return list;
/* 120:    */   }
/* 121:    */   
/* 122:    */   private Map<String, List<ColumnModel>> getColumnsByTableName(List<String> tableNames)
/* 123:    */   {
/* 124:169 */     String sql = "SELECT  A.TABLE_NAME TABLE_NAME,A.COLUMN_NAME NAME,A.DATA_TYPE TYPENAME,A.DATA_LENGTH LENGTH, A.DATA_PRECISION PRECISION,A.DATA_SCALE SCALE,A.DATA_DEFAULT,A.NULLABLE, DECODE(B.COMMENTS,NULL,A.COLUMN_NAME,B.COMMENTS) DESCRIPTION,  0 AS IS_PK  FROM USER_TAB_COLUMNS A,USER_COL_COMMENTS B WHERE A.COLUMN_NAME=B.COLUMN_NAME AND    A.TABLE_NAME = B.TABLE_NAME ";
/* 125:170 */     Map<String, List<ColumnModel>> map = new HashMap();
/* 126:171 */     if ((tableNames != null) && (tableNames.size() == 0)) {
/* 127:172 */       return map;
/* 128:    */     }
/* 129:174 */     StringBuffer buf = new StringBuffer();
/* 130:175 */     for (String str : tableNames) {
/* 131:176 */       buf.append("'" + str + "',");
/* 132:    */     }
/* 133:178 */     buf.deleteCharAt(buf.length() - 1);
/* 134:179 */     sql = sql + " AND A.TABLE_NAME IN (" + buf.toString() + ") ";
/* 135:    */     
/* 136:    */ 
/* 137:    */ 
/* 138:183 */     List<ColumnModel> columnModels = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, new HashMap(), new OracleColumnMap());
/* 139:184 */     for (ColumnModel columnModel : columnModels)
/* 140:    */     {
/* 141:185 */       String tableName = columnModel.getTableName();
/* 142:186 */       if (map.containsKey(tableName))
/* 143:    */       {
/* 144:187 */         ((List)map.get(tableName)).add(columnModel);
/* 145:    */       }
/* 146:    */       else
/* 147:    */       {
/* 148:189 */         List<ColumnModel> cols = new ArrayList();
/* 149:190 */         cols.add(columnModel);
/* 150:191 */         map.put(tableName, cols);
/* 151:    */       }
/* 152:    */     }
/* 153:194 */     return map;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void createOrRep(String viewName, String sql)
/* 157:    */     throws Exception
/* 158:    */   {
/* 159:199 */     String getSql = "CREATE OR REPLACE VIEW " + viewName + " as (" + sql + ")";
/* 160:    */     
/* 161:201 */     this.jdbcTemplate.execute(getSql);
/* 162:    */   }
/* 163:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.impl.OracleDbView
 * JD-Core Version:    0.7.0.1
 */