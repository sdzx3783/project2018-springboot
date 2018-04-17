/*   1:    */ package com.hotent.core.table.impl;
/*   2:    */ 
/*   3:    */ import com.hotent.core.db.datasource.JdbcTemplateUtil;
/*   4:    */ import com.hotent.core.page.PageBean;
/*   5:    */ import com.hotent.core.table.BaseDbView;
/*   6:    */ import com.hotent.core.table.ColumnModel;
/*   7:    */ import com.hotent.core.table.IDbView;
/*   8:    */ import com.hotent.core.table.TableModel;
/*   9:    */ import com.hotent.core.table.colmap.H2ColumnMap;
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
/*  28:    */ public class H2DbView
/*  29:    */   extends BaseDbView
/*  30:    */   implements IDbView
/*  31:    */ {
/*  32:    */   @Resource
/*  33:    */   private JdbcTemplate jdbcTemplate;
/*  34:    */   private static final String SQL_GET_ALL_VIEW = "SELECT TABLE_NAME ,REMARKS  FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'VIEW' AND TABLE_SCHEMA=SCHEMA() ";
/*  35:    */   private static final String SQL_GET_COLUMNS = "SELECT A.TABLE_NAME, A.COLUMN_NAME, A.IS_NULLABLE, A.DATA_TYPE, A.CHARACTER_OCTET_LENGTH LENGTH, A.NUMERIC_PRECISION PRECISIONS, A.NUMERIC_SCALE SCALE, B.COLUMN_LIST, A.REMARKS FROM INFORMATION_SCHEMA.COLUMNS A  JOIN INFORMATION_SCHEMA.CONSTRAINTS B ON A.TABLE_NAME=B.TABLE_NAME WHERE  A.TABLE_SCHEMA=SCHEMA() AND UPPER(A.TABLE_NAME)='%s' ";
/*  36:    */   static final String SQL_GET_COLUMNS_BATCH = "SELECT A.TABLE_NAME, A.COLUMN_NAME, A.IS_NULLABLE, A.DATA_TYPE, A.CHARACTER_OCTET_LENGTH LENGTH, A.NUMERIC_PRECISION PRECISIONS, A.NUMERIC_SCALE SCALE, B.COLUMN_LIST, A.REMARKS FROM INFORMATION_SCHEMA.COLUMNS A  JOIN INFORMATION_SCHEMA.CONSTRAINTS B ON A.TABLE_NAME=B.TABLE_NAME WHERE  A.TABLE_SCHEMA=SCHEMA() ";
/*  37:    */   
/*  38:    */   public List<String> getViews(String viewName)
/*  39:    */     throws SQLException
/*  40:    */   {
/*  41: 87 */     String sql = "SELECT TABLE_NAME ,REMARKS  FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'VIEW' AND TABLE_SCHEMA=SCHEMA() ";
/*  42: 88 */     if (StringUtil.isNotEmpty(viewName)) {
/*  43: 89 */       sql = sql + " AND TABLE_NAME LIKE '%" + viewName + "%'";
/*  44:    */     }
/*  45: 92 */     RowMapper<String> rowMapper = new RowMapper()
/*  46:    */     {
/*  47:    */       public String mapRow(ResultSet rs, int rowNum)
/*  48:    */         throws SQLException
/*  49:    */       {
/*  50: 95 */         String name = rs.getString("TABLE_NAME");
/*  51: 96 */         return name;
/*  52:    */       }
/*  53: 98 */     };
/*  54: 99 */     return this.jdbcTemplate.query(sql, rowMapper);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public List<String> getViews(String viewName, PageBean pageBean)
/*  58:    */     throws Exception
/*  59:    */   {
/*  60:108 */     String sql = "SELECT TABLE_NAME ,REMARKS  FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'VIEW' AND TABLE_SCHEMA=SCHEMA() ";
/*  61:109 */     if (StringUtil.isNotEmpty(viewName)) {
/*  62:110 */       sql = sql + " AND TABLE_NAME LIKE '%" + viewName + "%'";
/*  63:    */     }
/*  64:112 */     RowMapper<String> rowMapper = new RowMapper()
/*  65:    */     {
/*  66:    */       public String mapRow(ResultSet rs, int rowNum)
/*  67:    */         throws SQLException
/*  68:    */       {
/*  69:115 */         String name = rs.getString("TABLE_NAME");
/*  70:116 */         return name;
/*  71:    */       }
/*  72:118 */     };
/*  73:119 */     return getForList(sql, pageBean, rowMapper, "h2");
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getType(String dbtype)
/*  77:    */   {
/*  78:122 */     dbtype = dbtype.toUpperCase();
/*  79:123 */     if (dbtype.equals("BIGINT")) {
/*  80:124 */       return "number";
/*  81:    */     }
/*  82:125 */     if (dbtype.equals("INT8")) {
/*  83:126 */       return "number";
/*  84:    */     }
/*  85:127 */     if (dbtype.equals("INT")) {
/*  86:128 */       return "number";
/*  87:    */     }
/*  88:129 */     if (dbtype.equals("INTEGER")) {
/*  89:130 */       return "number";
/*  90:    */     }
/*  91:131 */     if (dbtype.equals("MEDIUMINT")) {
/*  92:132 */       return "number";
/*  93:    */     }
/*  94:133 */     if (dbtype.equals("INT4")) {
/*  95:134 */       return "number";
/*  96:    */     }
/*  97:135 */     if (dbtype.equals("SIGNED")) {
/*  98:136 */       return "number";
/*  99:    */     }
/* 100:137 */     if (dbtype.equals("TINYINT")) {
/* 101:138 */       return "number";
/* 102:    */     }
/* 103:139 */     if (dbtype.equals("SMALLINT")) {
/* 104:140 */       return "number";
/* 105:    */     }
/* 106:141 */     if (dbtype.equals("INT2")) {
/* 107:142 */       return "number";
/* 108:    */     }
/* 109:143 */     if (dbtype.equals("YEAR")) {
/* 110:144 */       return "number";
/* 111:    */     }
/* 112:145 */     if (dbtype.equals("IDENTITY")) {
/* 113:146 */       return "number";
/* 114:    */     }
/* 115:147 */     if (dbtype.equals("DECIMAL")) {
/* 116:148 */       return "number";
/* 117:    */     }
/* 118:149 */     if (dbtype.equals("BOOLEAN")) {
/* 119:150 */       return "varchar";
/* 120:    */     }
/* 121:151 */     if (dbtype.equals("BIT")) {
/* 122:152 */       return "varchar";
/* 123:    */     }
/* 124:153 */     if (dbtype.equals("BOOL")) {
/* 125:154 */       return "varchar";
/* 126:    */     }
/* 127:155 */     if (dbtype.equals("SIGNED")) {
/* 128:156 */       return "number";
/* 129:    */     }
/* 130:158 */     if (dbtype.equals("DOUBLE")) {
/* 131:159 */       return "number";
/* 132:    */     }
/* 133:161 */     if (dbtype.equals("REAL")) {
/* 134:162 */       return "number";
/* 135:    */     }
/* 136:164 */     if (dbtype.equals("TIME")) {
/* 137:165 */       return "date";
/* 138:    */     }
/* 139:167 */     if (dbtype.equals("TIMESTAMP")) {
/* 140:168 */       return "date";
/* 141:    */     }
/* 142:170 */     if (dbtype.equals("BINARY")) {
/* 143:171 */       return "clob";
/* 144:    */     }
/* 145:173 */     if (dbtype.equals("BLOB")) {
/* 146:174 */       return "clob";
/* 147:    */     }
/* 148:176 */     if (dbtype.equals("CLOB")) {
/* 149:177 */       return "clob";
/* 150:    */     }
/* 151:179 */     if (dbtype.equals("VARCHAR")) {
/* 152:180 */       return "varchar";
/* 153:    */     }
/* 154:182 */     if (dbtype.equals("CHAR")) {
/* 155:183 */       return "varchar";
/* 156:    */     }
/* 157:185 */     if (dbtype.equals("UUID")) {
/* 158:186 */       return "varchar";
/* 159:    */     }
/* 160:188 */     if (dbtype.equals("ARRAY")) {
/* 161:189 */       return "varchar";
/* 162:    */     }
/* 163:192 */     return "varchar";
/* 164:    */   }
/* 165:    */   
/* 166:    */   public TableModel getModelByViewName(String viewName)
/* 167:    */     throws SQLException
/* 168:    */   {
/* 169:198 */     String sql = "SELECT TABLE_NAME ,REMARKS  FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'VIEW' AND TABLE_SCHEMA=SCHEMA() ";
/* 170:199 */     sql = sql + " AND UPPER(TABLE_NAME) = '" + viewName.toUpperCase() + "'";
/* 171:    */     
/* 172:201 */     TableModel tableModel = null;
/* 173:202 */     List<TableModel> tableModels = this.jdbcTemplate.query(sql, this.tableRowMapper);
/* 174:203 */     if (BeanUtils.isEmpty(tableModels)) {
/* 175:204 */       return null;
/* 176:    */     }
/* 177:206 */     tableModel = (TableModel)tableModels.get(0);
/* 178:    */     
/* 179:    */ 
/* 180:209 */     List<ColumnModel> columnList = getColumnsByTableName(viewName);
/* 181:210 */     tableModel.setColumnList(columnList);
/* 182:211 */     return tableModel;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public List<TableModel> getViewsByName(String viewName, PageBean pageBean)
/* 186:    */     throws Exception
/* 187:    */   {
/* 188:217 */     String sql = "SELECT TABLE_NAME ,REMARKS  FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'VIEW' AND TABLE_SCHEMA=SCHEMA() ";
/* 189:218 */     if (StringUtil.isNotEmpty(viewName)) {
/* 190:219 */       sql = sql + " AND TABLE_NAME LIKE '%" + viewName + "%'";
/* 191:    */     }
/* 192:221 */     RowMapper<TableModel> rowMapper = new RowMapper()
/* 193:    */     {
/* 194:    */       public TableModel mapRow(ResultSet rs, int row)
/* 195:    */         throws SQLException
/* 196:    */       {
/* 197:224 */         TableModel tableModel = new TableModel();
/* 198:225 */         tableModel.setName(rs.getString("table_name"));
/* 199:226 */         tableModel.setComment(tableModel.getName());
/* 200:227 */         return tableModel;
/* 201:    */       }
/* 202:229 */     };
/* 203:230 */     List<TableModel> tableModels = getForList(sql, pageBean, rowMapper, "h2");
/* 204:231 */     List<String> tableNames = new ArrayList();
/* 205:233 */     for (TableModel model : tableModels) {
/* 206:234 */       tableNames.add(model.getName());
/* 207:    */     }
/* 208:237 */     Map<String, List<ColumnModel>> tableColumnsMap = getColumnsByTableName(tableNames);
/* 209:239 */     for (Iterator i$ = tableColumnsMap.entrySet().iterator(); i$.hasNext();)
/* 210:    */     {
/* 211:239 */       entry = (Map.Entry)i$.next();
/* 212:241 */       for (TableModel model : tableModels) {
/* 213:242 */         if (model.getName().equalsIgnoreCase((String)entry.getKey())) {
/* 214:243 */           model.setColumnList((List)entry.getValue());
/* 215:    */         }
/* 216:    */       }
/* 217:    */     }
/* 218:    */     Map.Entry<String, List<ColumnModel>> entry;
/* 219:247 */     return tableModels;
/* 220:    */   }
/* 221:    */   
/* 222:    */   private List<ColumnModel> getColumnsByTableName(String tableName)
/* 223:    */   {
/* 224:257 */     String sql = String.format("SELECT A.TABLE_NAME, A.COLUMN_NAME, A.IS_NULLABLE, A.DATA_TYPE, A.CHARACTER_OCTET_LENGTH LENGTH, A.NUMERIC_PRECISION PRECISIONS, A.NUMERIC_SCALE SCALE, B.COLUMN_LIST, A.REMARKS FROM INFORMATION_SCHEMA.COLUMNS A  JOIN INFORMATION_SCHEMA.CONSTRAINTS B ON A.TABLE_NAME=B.TABLE_NAME WHERE  A.TABLE_SCHEMA=SCHEMA() AND UPPER(A.TABLE_NAME)='%s' ", new Object[] { tableName });
/* 225:    */     
/* 226:    */ 
/* 227:260 */     Map<String, Object> map = new HashMap();
/* 228:    */     
/* 229:262 */     List<ColumnModel> list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, map, new H2ColumnMap());
/* 230:263 */     for (ColumnModel model : list) {
/* 231:264 */       model.setTableName(tableName);
/* 232:    */     }
/* 233:266 */     return list;
/* 234:    */   }
/* 235:    */   
/* 236:    */   private Map<String, List<ColumnModel>> getColumnsByTableName(List<String> tableNames)
/* 237:    */   {
/* 238:276 */     String sql = "SELECT A.TABLE_NAME, A.COLUMN_NAME, A.IS_NULLABLE, A.DATA_TYPE, A.CHARACTER_OCTET_LENGTH LENGTH, A.NUMERIC_PRECISION PRECISIONS, A.NUMERIC_SCALE SCALE, B.COLUMN_LIST, A.REMARKS FROM INFORMATION_SCHEMA.COLUMNS A  JOIN INFORMATION_SCHEMA.CONSTRAINTS B ON A.TABLE_NAME=B.TABLE_NAME WHERE  A.TABLE_SCHEMA=SCHEMA() ";
/* 239:277 */     Map<String, List<ColumnModel>> map = new HashMap();
/* 240:278 */     if ((tableNames != null) && (tableNames.size() == 0)) {
/* 241:279 */       return map;
/* 242:    */     }
/* 243:281 */     StringBuffer buf = new StringBuffer();
/* 244:282 */     for (String str : tableNames) {
/* 245:283 */       buf.append("'" + str + "',");
/* 246:    */     }
/* 247:285 */     buf.deleteCharAt(buf.length() - 1);
/* 248:286 */     sql = sql + " AND A.TABLE_NAME IN (" + buf.toString() + ") ";
/* 249:    */     
/* 250:    */ 
/* 251:    */ 
/* 252:290 */     List<ColumnModel> columnModels = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, new HashMap(), new H2ColumnMap());
/* 253:291 */     for (ColumnModel columnModel : columnModels)
/* 254:    */     {
/* 255:292 */       String tableName = columnModel.getTableName();
/* 256:293 */       if (map.containsKey(tableName))
/* 257:    */       {
/* 258:294 */         ((List)map.get(tableName)).add(columnModel);
/* 259:    */       }
/* 260:    */       else
/* 261:    */       {
/* 262:296 */         List<ColumnModel> cols = new ArrayList();
/* 263:297 */         cols.add(columnModel);
/* 264:298 */         map.put(tableName, cols);
/* 265:    */       }
/* 266:    */     }
/* 267:301 */     return map;
/* 268:    */   }
/* 269:    */   
/* 270:304 */   RowMapper<TableModel> tableRowMapper = new RowMapper()
/* 271:    */   {
/* 272:    */     public TableModel mapRow(ResultSet rs, int row)
/* 273:    */       throws SQLException
/* 274:    */     {
/* 275:307 */       TableModel tableModel = new TableModel();
/* 276:308 */       String tabName = rs.getString("TABLE_NAME");
/* 277:309 */       String tabComment = rs.getString("REMARKS");
/* 278:310 */       tableModel.setName(tabName);
/* 279:311 */       tableModel.setComment(tabComment);
/* 280:312 */       return tableModel;
/* 281:    */     }
/* 282:    */   };
/* 283:    */   
/* 284:    */   public void createOrRep(String viewName, String sql)
/* 285:    */     throws Exception
/* 286:    */   {}
/* 287:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.impl.H2DbView
 * JD-Core Version:    0.7.0.1
 */