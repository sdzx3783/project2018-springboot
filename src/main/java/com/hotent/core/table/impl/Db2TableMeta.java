/*   1:    */ package com.hotent.core.table.impl;
/*   2:    */ 
/*   3:    */ import com.hotent.core.db.datasource.JdbcTemplateUtil;
/*   4:    */ import com.hotent.core.page.PageBean;
/*   5:    */ import com.hotent.core.table.BaseTableMeta;
/*   6:    */ import com.hotent.core.table.ColumnModel;
/*   7:    */ import com.hotent.core.table.TableModel;
/*   8:    */ import com.hotent.core.table.colmap.DB2ColumnMap;
/*   9:    */ import com.hotent.core.util.StringUtil;
/*  10:    */ import java.sql.ResultSet;
/*  11:    */ import java.sql.SQLException;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import java.util.Iterator;
/*  15:    */ import java.util.LinkedHashMap;
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
/*  27:    */ public class Db2TableMeta
/*  28:    */   extends BaseTableMeta
/*  29:    */ {
/*  30:    */   @Resource
/*  31:    */   private JdbcTemplate jdbcTemplate;
/*  32: 37 */   private final String SQL_GET_COLUMNS = "SELECT TABNAME TAB_NAME, COLNAME COL_NAME, TYPENAME COL_TYPE, REMARKS COL_COMMENT, NULLS IS_NULLABLE, LENGTH LENGTH, SCALE SCALE, KEYSEQ  FROM  SYSCAT.COLUMNS WHERE  TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) AND UPPER(TABNAME) = UPPER('%s') ";
/*  33: 53 */   private final String SQL_GET_COLUMNS_BATCH = "SELECT TABNAME TAB_NAME, COLNAME COL_NAME, TYPENAME COL_TYPE, REMARKS COL_COMMENT, NULLS IS_NULLABLE, LENGTH LENGTH, SCALE SCALE, KEYSEQ  FROM  SYSCAT.COLUMNS WHERE  TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) ";
/*  34: 68 */   private final String SQL_GET_TABLE_COMMENT = "SELECT TABNAME TAB_NAME, REMARKS TAB_COMMENT FROM SYSCAT.TABLES WHERE TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) AND UPPER(TABNAME) =UPPER('%s')";
/*  35: 78 */   private final String SQL_GET_ALL_TABLE_COMMENT = "SELECT TABNAME TAB_NAME, REMARKS TAB_COMMENT FROM SYSCAT.TABLES WHERE TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) AND UPPER(TABSCHEMA) = (SELECT UPPER(CURRENT SCHEMA) FROM SYSIBM.DUAL)";
/*  36:    */   
/*  37:    */   public TableModel getTableByName(String tableName)
/*  38:    */   {
/*  39: 98 */     TableModel model = getTableModel(tableName);
/*  40: 99 */     if (model == null) {
/*  41:100 */       return null;
/*  42:    */     }
/*  43:103 */     List<ColumnModel> columnList = getColumnsByTableName(tableName);
/*  44:104 */     model.setColumnList(columnList);
/*  45:105 */     return model;
/*  46:    */   }
/*  47:    */   
/*  48:    */   private List<ColumnModel> getColumnsByTableName(String tableName)
/*  49:    */   {
/*  50:115 */     String sql = String.format("SELECT TABNAME TAB_NAME, COLNAME COL_NAME, TYPENAME COL_TYPE, REMARKS COL_COMMENT, NULLS IS_NULLABLE, LENGTH LENGTH, SCALE SCALE, KEYSEQ  FROM  SYSCAT.COLUMNS WHERE  TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) AND UPPER(TABNAME) = UPPER('%s') ", new Object[] { tableName });
/*  51:    */     
/*  52:117 */     Map<String, Object> map = new HashMap();
/*  53:118 */     List<ColumnModel> list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, map, new DB2ColumnMap());
/*  54:119 */     return list;
/*  55:    */   }
/*  56:    */   
/*  57:    */   private Map<String, List<ColumnModel>> getColumnsByTableName(List<String> tableNames)
/*  58:    */   {
/*  59:129 */     String sql = "SELECT TABNAME TAB_NAME, COLNAME COL_NAME, TYPENAME COL_TYPE, REMARKS COL_COMMENT, NULLS IS_NULLABLE, LENGTH LENGTH, SCALE SCALE, KEYSEQ  FROM  SYSCAT.COLUMNS WHERE  TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) ";
/*  60:130 */     Map<String, List<ColumnModel>> map = new HashMap();
/*  61:131 */     if ((tableNames != null) && (tableNames.size() == 0)) {
/*  62:132 */       return map;
/*  63:    */     }
/*  64:134 */     StringBuffer buf = new StringBuffer();
/*  65:135 */     for (String str : tableNames) {
/*  66:136 */       buf.append("'" + str + "',");
/*  67:    */     }
/*  68:138 */     buf.deleteCharAt(buf.length() - 1);
/*  69:139 */     sql = sql + " AND UPPER(TABNAME) IN (" + buf.toString().toUpperCase() + ") ";
/*  70:    */     
/*  71:    */ 
/*  72:    */ 
/*  73:143 */     List<ColumnModel> columnModels = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, new HashMap(), new DB2ColumnMap());
/*  74:144 */     for (ColumnModel columnModel : columnModels)
/*  75:    */     {
/*  76:145 */       String tableName = columnModel.getTableName();
/*  77:146 */       if (map.containsKey(tableName))
/*  78:    */       {
/*  79:147 */         ((List)map.get(tableName)).add(columnModel);
/*  80:    */       }
/*  81:    */       else
/*  82:    */       {
/*  83:149 */         List<ColumnModel> cols = new ArrayList();
/*  84:150 */         cols.add(columnModel);
/*  85:151 */         map.put(tableName, cols);
/*  86:    */       }
/*  87:    */     }
/*  88:154 */     return map;
/*  89:    */   }
/*  90:    */   
/*  91:    */   private TableModel getTableModel(String tableName)
/*  92:    */   {
/*  93:165 */     String sql = String.format("SELECT TABNAME TAB_NAME, REMARKS TAB_COMMENT FROM SYSCAT.TABLES WHERE TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) AND UPPER(TABNAME) =UPPER('%s')", new Object[] { tableName });
/*  94:166 */     TableModel tableModel = (TableModel)this.jdbcTemplate.queryForObject(sql, null, this.tableModelRowMapper);
/*  95:167 */     return tableModel;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Map<String, String> getTablesByName(String tableName)
/*  99:    */   {
/* 100:173 */     String sql = "SELECT TABNAME TAB_NAME, REMARKS TAB_COMMENT FROM SYSCAT.TABLES WHERE TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) AND UPPER(TABSCHEMA) = (SELECT UPPER(CURRENT SCHEMA) FROM SYSIBM.DUAL)";
/* 101:174 */     if (StringUtil.isNotEmpty(tableName)) {
/* 102:175 */       sql = sql + " AND UPPER(TABNAME) LIKE UPPER('%" + tableName + "%')";
/* 103:    */     }
/* 104:178 */     Map<String, Object> parameter = new HashMap();
/* 105:179 */     List<Map<String, String>> list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, parameter, this.tableMapRowMapper);
/* 106:180 */     Map<String, String> map = new LinkedHashMap();
/* 107:181 */     for (int i = 0; i < list.size(); i++)
/* 108:    */     {
/* 109:182 */       Map<String, String> tmp = (Map)list.get(i);
/* 110:183 */       String name = (String)tmp.get("name");
/* 111:184 */       String comments = (String)tmp.get("comments");
/* 112:185 */       map.put(name, comments);
/* 113:    */     }
/* 114:187 */     return map;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Map<String, String> getTablesByName(List<String> tableNames)
/* 118:    */   {
/* 119:194 */     Map<String, String> map = new HashMap();
/* 120:195 */     String sql = "SELECT TABNAME TAB_NAME, REMARKS TAB_COMMENT FROM SYSCAT.TABLES WHERE TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) AND UPPER(TABSCHEMA) = (SELECT UPPER(CURRENT SCHEMA) FROM SYSIBM.DUAL)";
/* 121:196 */     if ((tableNames == null) || (tableNames.size() == 0)) {
/* 122:197 */       return map;
/* 123:    */     }
/* 124:199 */     StringBuffer buf = new StringBuffer();
/* 125:200 */     for (String str : tableNames) {
/* 126:201 */       buf.append("'" + str + "',");
/* 127:    */     }
/* 128:203 */     buf.deleteCharAt(buf.length() - 1);
/* 129:204 */     sql = sql + " AND UPPER(TABNAME) IN (" + buf.toString().toUpperCase() + ") ";
/* 130:    */     
/* 131:    */ 
/* 132:    */ 
/* 133:208 */     Map<String, Object> parameter = new HashMap();
/* 134:209 */     List<Map<String, String>> list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, parameter, this.tableMapRowMapper);
/* 135:210 */     for (int i = 0; i < list.size(); i++)
/* 136:    */     {
/* 137:211 */       Map<String, String> tmp = (Map)list.get(i);
/* 138:212 */       String name = (String)tmp.get("name");
/* 139:213 */       String comments = (String)tmp.get("comments");
/* 140:214 */       map.put(name, comments);
/* 141:    */     }
/* 142:216 */     return map;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public List<TableModel> getTablesByName(String tableName, PageBean pageBean)
/* 146:    */     throws Exception
/* 147:    */   {
/* 148:223 */     String sql = "SELECT TABNAME TAB_NAME, REMARKS TAB_COMMENT FROM SYSCAT.TABLES WHERE TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) AND UPPER(TABSCHEMA) = (SELECT UPPER(CURRENT SCHEMA) FROM SYSIBM.DUAL)";
/* 149:224 */     if (StringUtil.isNotEmpty(tableName)) {
/* 150:225 */       sql = sql + " AND UPPER(TABNAME) LIKE '%" + tableName.toUpperCase() + "%'";
/* 151:    */     }
/* 152:227 */     List<TableModel> tableModels = getForList(sql, pageBean, this.tableModelRowMapper, "db2");
/* 153:    */     
/* 154:229 */     List<String> tableNames = new ArrayList();
/* 155:231 */     for (TableModel model : tableModels) {
/* 156:232 */       tableNames.add(model.getName());
/* 157:    */     }
/* 158:235 */     Map<String, List<ColumnModel>> tableColumnsMap = getColumnsByTableName(tableNames);
/* 159:237 */     for (Iterator i$ = tableColumnsMap.entrySet().iterator(); i$.hasNext();)
/* 160:    */     {
/* 161:237 */       entry = (Map.Entry)i$.next();
/* 162:239 */       for (TableModel model : tableModels) {
/* 163:240 */         if (model.getName().equalsIgnoreCase((String)entry.getKey())) {
/* 164:241 */           model.setColumnList((List)entry.getValue());
/* 165:    */         }
/* 166:    */       }
/* 167:    */     }
/* 168:    */     Map.Entry<String, List<ColumnModel>> entry;
/* 169:245 */     return tableModels;
/* 170:    */   }
/* 171:    */   
/* 172:248 */   RowMapper<TableModel> tableModelRowMapper = new RowMapper()
/* 173:    */   {
/* 174:    */     public TableModel mapRow(ResultSet rs, int row)
/* 175:    */       throws SQLException
/* 176:    */     {
/* 177:251 */       TableModel tableModel = new TableModel();
/* 178:252 */       String tabName = rs.getString("TAB_NAME");
/* 179:253 */       String tabComment = rs.getString("TAB_COMMENT");
/* 180:254 */       tableModel.setName(tabName);
/* 181:255 */       tableModel.setComment(tabComment);
/* 182:256 */       return tableModel;
/* 183:    */     }
/* 184:    */   };
/* 185:260 */   RowMapper<Map<String, String>> tableMapRowMapper = new RowMapper()
/* 186:    */   {
/* 187:    */     public Map<String, String> mapRow(ResultSet rs, int row)
/* 188:    */       throws SQLException
/* 189:    */     {
/* 190:264 */       String tableName = rs.getString("TAB_NAME");
/* 191:265 */       String comments = rs.getString("TAB_COMMENT");
/* 192:266 */       Map<String, String> map = new HashMap();
/* 193:267 */       map.put("name", tableName);
/* 194:268 */       map.put("comments", comments);
/* 195:269 */       return map;
/* 196:    */     }
/* 197:    */   };
/* 198:    */   
/* 199:    */   public String getAllTableSql()
/* 200:    */   {
/* 201:276 */     return "SELECT TABNAME TAB_NAME, REMARKS TAB_COMMENT FROM SYSCAT.TABLES WHERE TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) AND UPPER(TABSCHEMA) = (SELECT UPPER(CURRENT SCHEMA) FROM SYSIBM.DUAL)";
/* 202:    */   }
/* 203:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.impl.Db2TableMeta
 * JD-Core Version:    0.7.0.1
 */