/*   1:    */ package com.hotent.core.table.impl;
/*   2:    */ 
/*   3:    */ import com.hotent.core.db.datasource.JdbcTemplateUtil;
/*   4:    */ import com.hotent.core.page.PageBean;
/*   5:    */ import com.hotent.core.table.BaseDbView;
/*   6:    */ import com.hotent.core.table.ColumnModel;
/*   7:    */ import com.hotent.core.table.IDbView;
/*   8:    */ import com.hotent.core.table.TableModel;
/*   9:    */ import com.hotent.core.table.colmap.DB2ColumnMap;
/*  10:    */ import com.hotent.core.util.BeanUtils;
/*  11:    */ import com.hotent.core.util.StringUtil;
/*  12:    */ import java.sql.ResultSet;
/*  13:    */ import java.sql.SQLException;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.Iterator;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import java.util.Map.Entry;
/*  20:    */ import java.util.Set;
/*  21:    */ import javax.annotation.Resource;
/*  22:    */ import org.springframework.jdbc.core.JdbcTemplate;
/*  23:    */ import org.springframework.jdbc.core.RowMapper;
/*  24:    */ import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
/*  25:    */ import org.springframework.stereotype.Component;
/*  26:    */ 
/*  27:    */ @Component
/*  28:    */ public class Db2DbView
/*  29:    */   extends BaseDbView
/*  30:    */   implements IDbView
/*  31:    */ {
/*  32:    */   @Resource
/*  33:    */   private JdbcTemplate jdbcTemplate;
/*  34:    */   private static final String SQL_GET_ALL_VIEW = "SELECT VIEWNAME FROM SYSCAT.VIEWS WHERE  VIEWSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) ";
/*  35:    */   private static final String SQL_GET_COLUMNS = "SELECT TABNAME TAB_NAME, COLNAME COL_NAME, TYPENAME COL_TYPE, REMARKS COL_COMMENT, NULLS IS_NULLABLE, LENGTH LENGTH, SCALE SCALE, KEYSEQ  FROM  SYSCAT.COLUMNS WHERE  TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) AND UPPER(TABNAME) = UPPER('%s') ";
/*  36: 62 */   private final String SQL_GET_COLUMNS_BATCH = "SELECT TABNAME TAB_NAME, COLNAME COL_NAME, TYPENAME COL_TYPE, REMARKS COL_COMMENT, NULLS IS_NULLABLE, LENGTH LENGTH, SCALE SCALE, KEYSEQ  FROM  SYSCAT.COLUMNS WHERE  TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) ";
/*  37:    */   
/*  38:    */   public List<String> getViews(String viewName)
/*  39:    */     throws SQLException
/*  40:    */   {
/*  41: 81 */     String sql = "SELECT VIEWNAME FROM SYSCAT.VIEWS WHERE  VIEWSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) ";
/*  42: 82 */     if (StringUtil.isNotEmpty(viewName)) {
/*  43: 83 */       sql = sql + " AND UPPER(VIEWNAME) like '" + viewName.toUpperCase() + "%'";
/*  44:    */     }
/*  45: 85 */     return this.jdbcTemplate.queryForList(sql, String.class);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String getType(String type)
/*  49:    */   {
/*  50: 93 */     String dbtype = type.toLowerCase();
/*  51: 94 */     if (dbtype.endsWith("bigint")) {
/*  52: 95 */       return "number";
/*  53:    */     }
/*  54: 96 */     if (dbtype.endsWith("blob")) {
/*  55: 97 */       return "clob";
/*  56:    */     }
/*  57: 98 */     if (dbtype.endsWith("character")) {
/*  58: 99 */       return "varchar";
/*  59:    */     }
/*  60:100 */     if (dbtype.endsWith("clob")) {
/*  61:101 */       return "clob";
/*  62:    */     }
/*  63:102 */     if (dbtype.endsWith("date")) {
/*  64:103 */       return "date";
/*  65:    */     }
/*  66:104 */     if (dbtype.endsWith("dbclob")) {
/*  67:105 */       return "clob";
/*  68:    */     }
/*  69:106 */     if (dbtype.endsWith("decimal")) {
/*  70:107 */       return "number";
/*  71:    */     }
/*  72:108 */     if (dbtype.endsWith("double")) {
/*  73:109 */       return "number";
/*  74:    */     }
/*  75:110 */     if (dbtype.endsWith("graphic")) {
/*  76:111 */       return "clob";
/*  77:    */     }
/*  78:112 */     if (dbtype.endsWith("integer")) {
/*  79:113 */       return "number";
/*  80:    */     }
/*  81:114 */     if (dbtype.endsWith("long varchar")) {
/*  82:115 */       return "varchar";
/*  83:    */     }
/*  84:116 */     if (dbtype.endsWith("long vargraphic")) {
/*  85:117 */       return "clob";
/*  86:    */     }
/*  87:118 */     if (dbtype.endsWith("real")) {
/*  88:119 */       return "number";
/*  89:    */     }
/*  90:120 */     if (dbtype.endsWith("smallint")) {
/*  91:121 */       return "number";
/*  92:    */     }
/*  93:122 */     if (dbtype.endsWith("time")) {
/*  94:123 */       return "date";
/*  95:    */     }
/*  96:124 */     if (dbtype.endsWith("timestamp")) {
/*  97:125 */       return "date";
/*  98:    */     }
/*  99:126 */     if (dbtype.endsWith("varchar")) {
/* 100:127 */       return "varchar";
/* 101:    */     }
/* 102:128 */     if (dbtype.endsWith("vargraphic")) {
/* 103:129 */       return "clob";
/* 104:    */     }
/* 105:130 */     if (dbtype.endsWith("xml")) {
/* 106:131 */       return "clob";
/* 107:    */     }
/* 108:133 */     return "varchar";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public List<String> getViews(String viewName, PageBean pageBean)
/* 112:    */     throws Exception
/* 113:    */   {
/* 114:140 */     String sql = "SELECT VIEWNAME FROM SYSCAT.VIEWS WHERE  VIEWSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) ";
/* 115:141 */     if (StringUtil.isNotEmpty(viewName)) {
/* 116:142 */       sql = sql + " AND UPPER(VIEWNAME) LIKE '%" + viewName.toUpperCase() + "%'";
/* 117:    */     }
/* 118:144 */     RowMapper<String> rowMapper = new RowMapper()
/* 119:    */     {
/* 120:    */       public String mapRow(ResultSet rs, int rowNum)
/* 121:    */         throws SQLException
/* 122:    */       {
/* 123:147 */         return rs.getString("VIEWNAME");
/* 124:    */       }
/* 125:149 */     };
/* 126:150 */     return getForList(sql, pageBean, rowMapper, "db2");
/* 127:    */   }
/* 128:    */   
/* 129:    */   public TableModel getModelByViewName(String viewName)
/* 130:    */     throws SQLException
/* 131:    */   {
/* 132:155 */     String sql = "SELECT VIEWNAME FROM SYSCAT.VIEWS WHERE  VIEWSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) ";
/* 133:156 */     sql = sql + " AND UPPER(VIEWNAME) = '" + viewName.toUpperCase() + "'";
/* 134:    */     
/* 135:158 */     TableModel tableModel = null;
/* 136:159 */     List<TableModel> tableModels = this.jdbcTemplate.query(sql, this.tableModelRowMapper);
/* 137:160 */     if (BeanUtils.isEmpty(tableModels)) {
/* 138:161 */       return null;
/* 139:    */     }
/* 140:163 */     tableModel = (TableModel)tableModels.get(0);
/* 141:    */     
/* 142:    */ 
/* 143:166 */     List<ColumnModel> columnList = getColumnsByTableName(viewName);
/* 144:167 */     tableModel.setColumnList(columnList);
/* 145:168 */     return tableModel;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public List<TableModel> getViewsByName(String viewName, PageBean pageBean)
/* 149:    */     throws Exception
/* 150:    */   {
/* 151:173 */     String sql = "SELECT VIEWNAME FROM SYSCAT.VIEWS WHERE  VIEWSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) ";
/* 152:174 */     if (StringUtil.isNotEmpty(viewName)) {
/* 153:175 */       sql = sql + " AND UPPER(VIEWNAME) LIKE '%" + viewName.toUpperCase() + "%'";
/* 154:    */     }
/* 155:178 */     List<TableModel> tableModels = getForList(sql, pageBean, this.tableModelRowMapper, "db2");
/* 156:    */     
/* 157:180 */     List<String> tableNames = new ArrayList();
/* 158:182 */     for (TableModel model : tableModels) {
/* 159:183 */       tableNames.add(model.getName());
/* 160:    */     }
/* 161:186 */     Map<String, List<ColumnModel>> tableColumnsMap = getColumnsByTableName(tableNames);
/* 162:188 */     for (Iterator i$ = tableColumnsMap.entrySet().iterator(); i$.hasNext();)
/* 163:    */     {
/* 164:188 */       entry = (Map.Entry)i$.next();
/* 165:190 */       for (TableModel model : tableModels) {
/* 166:191 */         if (model.getName().equalsIgnoreCase((String)entry.getKey())) {
/* 167:192 */           model.setColumnList((List)entry.getValue());
/* 168:    */         }
/* 169:    */       }
/* 170:    */     }
/* 171:    */     Map.Entry<String, List<ColumnModel>> entry;
/* 172:196 */     return tableModels;
/* 173:    */   }
/* 174:    */   
/* 175:    */   private List<ColumnModel> getColumnsByTableName(String tableName)
/* 176:    */   {
/* 177:207 */     String sql = String.format("SELECT TABNAME TAB_NAME, COLNAME COL_NAME, TYPENAME COL_TYPE, REMARKS COL_COMMENT, NULLS IS_NULLABLE, LENGTH LENGTH, SCALE SCALE, KEYSEQ  FROM  SYSCAT.COLUMNS WHERE  TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) AND UPPER(TABNAME) = UPPER('%s') ", new Object[] { tableName });
/* 178:208 */     Map<String, Object> map = new HashMap();
/* 179:    */     
/* 180:210 */     List<ColumnModel> list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, map, new DB2ColumnMap());
/* 181:211 */     return list;
/* 182:    */   }
/* 183:    */   
/* 184:    */   private Map<String, List<ColumnModel>> getColumnsByTableName(List<String> tableNames)
/* 185:    */   {
/* 186:221 */     String sql = "SELECT TABNAME TAB_NAME, COLNAME COL_NAME, TYPENAME COL_TYPE, REMARKS COL_COMMENT, NULLS IS_NULLABLE, LENGTH LENGTH, SCALE SCALE, KEYSEQ  FROM  SYSCAT.COLUMNS WHERE  TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) ";
/* 187:222 */     Map<String, List<ColumnModel>> map = new HashMap();
/* 188:223 */     if ((tableNames != null) && (tableNames.size() == 0)) {
/* 189:224 */       return map;
/* 190:    */     }
/* 191:226 */     StringBuffer buf = new StringBuffer();
/* 192:227 */     for (String str : tableNames) {
/* 193:228 */       buf.append("'" + str + "',");
/* 194:    */     }
/* 195:230 */     buf.deleteCharAt(buf.length() - 1);
/* 196:231 */     sql = sql + " AND UPPER(TABNAME) IN (" + buf.toString().toUpperCase() + ") ";
/* 197:    */     
/* 198:    */ 
/* 199:    */ 
/* 200:    */ 
/* 201:236 */     List<ColumnModel> columnModels = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, new HashMap(), new DB2ColumnMap());
/* 202:237 */     for (ColumnModel columnModel : columnModels)
/* 203:    */     {
/* 204:238 */       String tableName = columnModel.getTableName();
/* 205:239 */       if (map.containsKey(tableName))
/* 206:    */       {
/* 207:240 */         ((List)map.get(tableName)).add(columnModel);
/* 208:    */       }
/* 209:    */       else
/* 210:    */       {
/* 211:242 */         List<ColumnModel> cols = new ArrayList();
/* 212:243 */         cols.add(columnModel);
/* 213:244 */         map.put(tableName, cols);
/* 214:    */       }
/* 215:    */     }
/* 216:247 */     return map;
/* 217:    */   }
/* 218:    */   
/* 219:250 */   RowMapper<TableModel> tableModelRowMapper = new RowMapper()
/* 220:    */   {
/* 221:    */     public TableModel mapRow(ResultSet rs, int row)
/* 222:    */       throws SQLException
/* 223:    */     {
/* 224:253 */       TableModel tableModel = new TableModel();
/* 225:254 */       String tabName = rs.getString("VIEWNAME");
/* 226:255 */       tableModel.setName(tabName);
/* 227:256 */       tableModel.setComment(tabName);
/* 228:257 */       return tableModel;
/* 229:    */     }
/* 230:    */   };
/* 231:    */   
/* 232:    */   public void createOrRep(String viewName, String sql)
/* 233:    */     throws Exception
/* 234:    */   {}
/* 235:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.impl.Db2DbView
 * JD-Core Version:    0.7.0.1
 */