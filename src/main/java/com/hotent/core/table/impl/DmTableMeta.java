/*   1:    */ package com.hotent.core.table.impl;
/*   2:    */ 
/*   3:    */ import com.hotent.core.db.datasource.JdbcTemplateUtil;
/*   4:    */ import com.hotent.core.page.PageBean;
/*   5:    */ import com.hotent.core.table.BaseTableMeta;
/*   6:    */ import com.hotent.core.table.ColumnModel;
/*   7:    */ import com.hotent.core.table.TableModel;
/*   8:    */ import com.hotent.core.table.colmap.DmColumnMap;
/*   9:    */ import com.hotent.core.util.BeanUtils;
/*  10:    */ import com.hotent.core.util.StringUtil;
/*  11:    */ import java.sql.ResultSet;
/*  12:    */ import java.sql.SQLException;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.HashMap;
/*  15:    */ import java.util.Iterator;
/*  16:    */ import java.util.LinkedHashMap;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import java.util.Map.Entry;
/*  20:    */ import java.util.Set;
/*  21:    */ import javax.annotation.Resource;
/*  22:    */ import org.slf4j.Logger;
/*  23:    */ import org.slf4j.LoggerFactory;
/*  24:    */ import org.springframework.jdbc.core.JdbcTemplate;
/*  25:    */ import org.springframework.jdbc.core.RowMapper;
/*  26:    */ import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
/*  27:    */ import org.springframework.stereotype.Component;
/*  28:    */ 
/*  29:    */ @Component
/*  30:    */ public class DmTableMeta
/*  31:    */   extends BaseTableMeta
/*  32:    */ {
/*  33:    */   @Resource
/*  34:    */   private JdbcTemplate jdbcTemplate;
/*  35: 41 */   protected Logger logger = LoggerFactory.getLogger(DmTableMeta.class);
/*  36: 45 */   private String sqlPk = "SELECT  CONS_C.COLUMN_NAME FROM \"SYS\".\"USER_CONSTRAINTS\" CONS, \"SYS\".\"USER_CONS_COLUMNS\" CONS_C    WHERE  CONS.CONSTRAINT_NAME=CONS_C.CONSTRAINT_NAME  AND CONS.CONSTRAINT_TYPE='P'  AND CONS_C.POSITION=1  AND   CONS.TABLE_NAME='%s'";
/*  37: 50 */   private String sqlTableComment = "SELECT TABLE_NAME,COMMENTS FROM (SELECT A.TABLE_NAME AS TABLE_NAME,DECODE(B.COMMENT$,NULL, A.TABLE_NAME,B.COMMENT$) AS COMMENTS FROM \"SYS\".\"USER_TABLES\" A LEFT JOIN \"SYS\".\"SYSTABLECOMMENTS\" B ON  A.TABLE_NAME=B.TVNAME) WHERE  TABLE_NAME ='%s'";
/*  38: 55 */   private final String SQL_GET_COLUMNS = "SELECT T.TABLE_NAME TABLE_NAME, T.NAME NAME,T.TYPENAME TYPENAME, T.LENGTH LENGTH,  T.PRECISION PRECISION,T.SCALE SCALE,T.DATA_DEFAULT DATA_DEFAULT,T.NULLABLE NULLABLE,T.DESCRIPTION DESCRIPTION,  (SELECT  COUNT(*)   FROM    \"SYS\".\"USER_CONSTRAINTS\" CONS, \"SYS\".\"USER_CONS_COLUMNS\" CONS_C    WHERE  CONS.CONSTRAINT_NAME=CONS_C.CONSTRAINT_NAME  AND CONS.CONSTRAINT_TYPE='P'  AND CONS_C.POSITION=1  AND   CONS.TABLE_NAME=T.TABLE_NAME  AND CONS_C.COLUMN_NAME= T.NAME) AS  IS_PK FROM (SELECT A.COLUMN_ID COLUMN_ID, A.TABLE_NAME TABLE_NAME, A.COLUMN_NAME NAME, A.DATA_TYPE TYPENAME, A.DATA_LENGTH LENGTH, A.DATA_PRECISION PRECISION, A.DATA_SCALE SCALE, A.DATA_DEFAULT, A.NULLABLE, DECODE(B.COMMENT$,NULL, A.TABLE_NAME,B.COMMENT$) AS DESCRIPTION  FROM \"SYS\".\"USER_TAB_COLUMNS\" A LEFT JOIN \"SYS\".\"SYSCOLUMNCOMMENTS\" B ON  A.COLUMN_NAME=B.COLNAME AND  A.TABLE_NAME=B.TVNAME  AND B.SCHNAME=user() ) T  WHERE TABLE_NAME='%S'  ORDER BY COLUMN_ID ";
/*  39: 68 */   private final String SQL_GET_COLUMNS_BATCH = "SELECT T.TABLE_NAME TABLE_NAME, T.NAME NAME,T.TYPENAME TYPENAME, T.LENGTH LENGTH,  T.PRECISION PRECISION,T.SCALE SCALE,T.DATA_DEFAULT DATA_DEFAULT,T.NULLABLE NULLABLE,T.DESCRIPTION DESCRIPTION,  (SELECT  COUNT(*)   FROM    \"SYS\".\"USER_CONSTRAINTS\" CONS, \"SYS\".\"USER_CONS_COLUMNS\" CONS_C    WHERE  CONS.CONSTRAINT_NAME=CONS_C.CONSTRAINT_NAME  AND CONS.CONSTRAINT_TYPE='P'  AND CONS_C.POSITION=1  AND   CONS.TABLE_NAME=T.TABLE_NAME  AND CONS_C.COLUMN_NAME= T.NAME) AS  IS_PK FROM (SELECT A.COLUMN_ID COLUMN_ID, A.TABLE_NAME TABLE_NAME, A.COLUMN_NAME NAME, A.DATA_TYPE TYPENAME, A.DATA_LENGTH LENGTH, A.DATA_PRECISION PRECISION, A.DATA_SCALE SCALE, A.DATA_DEFAULT, A.NULLABLE, DECODE(B.COMMENT$,NULL, A.TABLE_NAME,B.COMMENT$) AS DESCRIPTION  FROM \"SYS\".\"USER_TAB_COLUMNS\" A LEFT JOIN \"SYS\".\"SYSCOLUMNCOMMENTS\" B ON  A.COLUMN_NAME=B.COLNAME AND  A.TABLE_NAME=B.TVNAME  AND B.SCHNAME=user() ) T WHERE 1=1 ";
/*  40: 81 */   private String sqlAllTables = "SELECT TABLE_NAME,COMMENTS FROM (SELECT A.TABLE_NAME AS TABLE_NAME,DECODE(B.COMMENT$,NULL, A.TABLE_NAME,B.COMMENT$) AS COMMENTS FROM \"SYS\".\"USER_TABLES\" A LEFT JOIN \"SYS\".\"SYSTABLECOMMENTS\" B ON  A.TABLE_NAME=B.TVNAME) WHERE 1=1";
/*  41:    */   
/*  42:    */   public Map<String, String> getTablesByName(String tableName)
/*  43:    */   {
/*  44: 90 */     String sql = this.sqlAllTables;
/*  45: 91 */     if (StringUtil.isNotEmpty(tableName)) {
/*  46: 92 */       sql = this.sqlAllTables + " and  lower(TABLE_NAME) like '%" + tableName.toLowerCase() + "%'";
/*  47:    */     }
/*  48: 95 */     Map parameter = new HashMap();
/*  49: 96 */     List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, parameter, new RowMapper()
/*  50:    */     {
/*  51:    */       public Map<String, String> mapRow(ResultSet rs, int row)
/*  52:    */         throws SQLException
/*  53:    */       {
/*  54:100 */         String tableName = rs.getString("table_name");
/*  55:101 */         String comments = rs.getString("comments");
/*  56:102 */         Map<String, String> map = new HashMap();
/*  57:103 */         map.put("name", tableName);
/*  58:104 */         map.put("comments", comments);
/*  59:105 */         return map;
/*  60:    */       }
/*  61:107 */     });
/*  62:108 */     Map<String, String> map = new LinkedHashMap();
/*  63:109 */     for (int i = 0; i < list.size(); i++)
/*  64:    */     {
/*  65:110 */       Map<String, String> tmp = (Map)list.get(i);
/*  66:111 */       String name = (String)tmp.get("name");
/*  67:112 */       String comments = (String)tmp.get("comments");
/*  68:113 */       map.put(name, comments);
/*  69:    */     }
/*  70:116 */     return map;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Map<String, String> getTablesByName(List<String> names)
/*  74:    */   {
/*  75:121 */     StringBuffer sb = new StringBuffer();
/*  76:122 */     for (String name : names)
/*  77:    */     {
/*  78:123 */       sb.append("'");
/*  79:124 */       sb.append(name);
/*  80:125 */       sb.append("',");
/*  81:    */     }
/*  82:127 */     sb.deleteCharAt(sb.length() - 1);
/*  83:128 */     String sql = this.sqlAllTables + " and  lower(TABLE_NAME) in (" + sb.toString().toLowerCase() + ")";
/*  84:    */     
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:133 */     Map parameter = new HashMap();
/*  89:134 */     List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, parameter, new RowMapper()
/*  90:    */     {
/*  91:    */       public Map<String, String> mapRow(ResultSet rs, int row)
/*  92:    */         throws SQLException
/*  93:    */       {
/*  94:138 */         String tableName = rs.getString("TABLE_NAME");
/*  95:139 */         String comments = rs.getString("COMMENTS");
/*  96:140 */         Map<String, String> map = new HashMap();
/*  97:141 */         map.put("NAME", tableName);
/*  98:142 */         map.put("COMMENTS", comments);
/*  99:143 */         return map;
/* 100:    */       }
/* 101:145 */     });
/* 102:146 */     Map<String, String> map = new LinkedHashMap();
/* 103:147 */     for (int i = 0; i < list.size(); i++)
/* 104:    */     {
/* 105:148 */       Map<String, String> tmp = (Map)list.get(i);
/* 106:149 */       String name = (String)tmp.get("NAME");
/* 107:150 */       String comments = (String)tmp.get("COMMENTS");
/* 108:151 */       map.put(name, comments);
/* 109:    */     }
/* 110:154 */     return map;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public TableModel getTableByName(String tableName)
/* 114:    */   {
/* 115:162 */     tableName = tableName.toUpperCase();
/* 116:163 */     TableModel model = getTableModel(tableName);
/* 117:    */     
/* 118:165 */     List<ColumnModel> columnList = getColumnsByTableName(tableName);
/* 119:166 */     model.setColumnList(columnList);
/* 120:167 */     return model;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public List<TableModel> getTablesByName(String tableName, PageBean pageBean)
/* 124:    */     throws Exception
/* 125:    */   {
/* 126:173 */     String sql = this.sqlAllTables;
/* 127:175 */     if (StringUtil.isNotEmpty(tableName)) {
/* 128:176 */       sql = sql + " AND  LOWER(TABLE_NAME) LIKE '%" + tableName.toLowerCase() + "%'";
/* 129:    */     }
/* 130:178 */     RowMapper<TableModel> rowMapper = new RowMapper()
/* 131:    */     {
/* 132:    */       public TableModel mapRow(ResultSet rs, int row)
/* 133:    */         throws SQLException
/* 134:    */       {
/* 135:182 */         TableModel tableModel = new TableModel();
/* 136:183 */         tableModel.setName(rs.getString("TABLE_NAME"));
/* 137:184 */         tableModel.setComment(rs.getString("COMMENTS"));
/* 138:185 */         return tableModel;
/* 139:    */       }
/* 140:187 */     };
/* 141:188 */     List<TableModel> tableModels = getForList(sql, pageBean, rowMapper, "dm");
/* 142:189 */     List<String> tableNames = new ArrayList();
/* 143:191 */     for (TableModel model : tableModels) {
/* 144:192 */       tableNames.add(model.getName());
/* 145:    */     }
/* 146:195 */     Map<String, List<ColumnModel>> tableColumnsMap = getColumnsByTableName(tableNames);
/* 147:197 */     for (Iterator i$ = tableColumnsMap.entrySet().iterator(); i$.hasNext();)
/* 148:    */     {
/* 149:197 */       entry = (Map.Entry)i$.next();
/* 150:199 */       for (TableModel model : tableModels) {
/* 151:200 */         if (model.getName().equalsIgnoreCase((String)entry.getKey())) {
/* 152:201 */           model.setColumnList((List)entry.getValue());
/* 153:    */         }
/* 154:    */       }
/* 155:    */     }
/* 156:    */     Map.Entry<String, List<ColumnModel>> entry;
/* 157:206 */     return tableModels;
/* 158:    */   }
/* 159:    */   
/* 160:    */   private String getPkColumn(String tableName)
/* 161:    */   {
/* 162:216 */     tableName = tableName.toUpperCase();
/* 163:    */     
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:221 */     String sql = String.format(this.sqlPk, new Object[] { tableName });
/* 168:222 */     Object rtn = this.jdbcTemplate.queryForObject(sql, null, new RowMapper()
/* 169:    */     {
/* 170:    */       public String mapRow(ResultSet rs, int row)
/* 171:    */         throws SQLException
/* 172:    */       {
/* 173:226 */         return rs.getString("COLUMN_NAME");
/* 174:    */       }
/* 175:    */     });
/* 176:229 */     if (rtn == null) {
/* 177:230 */       return "";
/* 178:    */     }
/* 179:232 */     return rtn.toString();
/* 180:    */   }
/* 181:    */   
/* 182:    */   private List<String> getPkColumns(String tableName)
/* 183:    */   {
/* 184:243 */     tableName = tableName.toUpperCase();
/* 185:    */     
/* 186:    */ 
/* 187:    */ 
/* 188:    */ 
/* 189:248 */     String sql = String.format(this.sqlPk, new Object[] { tableName });
/* 190:249 */     List<String> rtn = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, new HashMap(), new RowMapper()
/* 191:    */     {
/* 192:    */       public String mapRow(ResultSet rs, int rowNum)
/* 193:    */         throws SQLException
/* 194:    */       {
/* 195:252 */         return rs.getString("column_name");
/* 196:    */       }
/* 197:254 */     });
/* 198:255 */     return rtn;
/* 199:    */   }
/* 200:    */   
/* 201:    */   private TableModel getTableModel(final String tableName)
/* 202:    */   {
/* 203:269 */     String sql = String.format(this.sqlTableComment, new Object[] { tableName });
/* 204:270 */     TableModel tableModel = (TableModel)this.jdbcTemplate.queryForObject(sql, null, new RowMapper()
/* 205:    */     {
/* 206:    */       public TableModel mapRow(ResultSet rs, int row)
/* 207:    */         throws SQLException
/* 208:    */       {
/* 209:275 */         TableModel tableModel = new TableModel();
/* 210:276 */         tableModel.setName(tableName);
/* 211:277 */         tableModel.setComment(rs.getString("comments"));
/* 212:278 */         return tableModel;
/* 213:    */       }
/* 214:    */     });
/* 215:281 */     if (BeanUtils.isEmpty(tableModel)) {
/* 216:282 */       tableModel = new TableModel();
/* 217:    */     }
/* 218:284 */     return tableModel;
/* 219:    */   }
/* 220:    */   
/* 221:    */   private List<ColumnModel> getColumnsByTableName(String tableName)
/* 222:    */   {
/* 223:294 */     String sql = String.format("SELECT T.TABLE_NAME TABLE_NAME, T.NAME NAME,T.TYPENAME TYPENAME, T.LENGTH LENGTH,  T.PRECISION PRECISION,T.SCALE SCALE,T.DATA_DEFAULT DATA_DEFAULT,T.NULLABLE NULLABLE,T.DESCRIPTION DESCRIPTION,  (SELECT  COUNT(*)   FROM    \"SYS\".\"USER_CONSTRAINTS\" CONS, \"SYS\".\"USER_CONS_COLUMNS\" CONS_C    WHERE  CONS.CONSTRAINT_NAME=CONS_C.CONSTRAINT_NAME  AND CONS.CONSTRAINT_TYPE='P'  AND CONS_C.POSITION=1  AND   CONS.TABLE_NAME=T.TABLE_NAME  AND CONS_C.COLUMN_NAME= T.NAME) AS  IS_PK FROM (SELECT A.COLUMN_ID COLUMN_ID, A.TABLE_NAME TABLE_NAME, A.COLUMN_NAME NAME, A.DATA_TYPE TYPENAME, A.DATA_LENGTH LENGTH, A.DATA_PRECISION PRECISION, A.DATA_SCALE SCALE, A.DATA_DEFAULT, A.NULLABLE, DECODE(B.COMMENT$,NULL, A.TABLE_NAME,B.COMMENT$) AS DESCRIPTION  FROM \"SYS\".\"USER_TAB_COLUMNS\" A LEFT JOIN \"SYS\".\"SYSCOLUMNCOMMENTS\" B ON  A.COLUMN_NAME=B.COLNAME AND  A.TABLE_NAME=B.TVNAME  AND B.SCHNAME=user() ) T  WHERE TABLE_NAME='%S'  ORDER BY COLUMN_ID ", new Object[] { tableName });
/* 224:    */     
/* 225:    */ 
/* 226:    */ 
/* 227:    */ 
/* 228:299 */     Map<String, Object> map = new HashMap();
/* 229:300 */     List<ColumnModel> columnList = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, map, new DmColumnMap());
/* 230:301 */     return columnList;
/* 231:    */   }
/* 232:    */   
/* 233:    */   private Map<String, List<ColumnModel>> getColumnsByTableName(List<String> tableNames)
/* 234:    */   {
/* 235:311 */     String sql = "SELECT T.TABLE_NAME TABLE_NAME, T.NAME NAME,T.TYPENAME TYPENAME, T.LENGTH LENGTH,  T.PRECISION PRECISION,T.SCALE SCALE,T.DATA_DEFAULT DATA_DEFAULT,T.NULLABLE NULLABLE,T.DESCRIPTION DESCRIPTION,  (SELECT  COUNT(*)   FROM    \"SYS\".\"USER_CONSTRAINTS\" CONS, \"SYS\".\"USER_CONS_COLUMNS\" CONS_C    WHERE  CONS.CONSTRAINT_NAME=CONS_C.CONSTRAINT_NAME  AND CONS.CONSTRAINT_TYPE='P'  AND CONS_C.POSITION=1  AND   CONS.TABLE_NAME=T.TABLE_NAME  AND CONS_C.COLUMN_NAME= T.NAME) AS  IS_PK FROM (SELECT A.COLUMN_ID COLUMN_ID, A.TABLE_NAME TABLE_NAME, A.COLUMN_NAME NAME, A.DATA_TYPE TYPENAME, A.DATA_LENGTH LENGTH, A.DATA_PRECISION PRECISION, A.DATA_SCALE SCALE, A.DATA_DEFAULT, A.NULLABLE, DECODE(B.COMMENT$,NULL, A.TABLE_NAME,B.COMMENT$) AS DESCRIPTION  FROM \"SYS\".\"USER_TAB_COLUMNS\" A LEFT JOIN \"SYS\".\"SYSCOLUMNCOMMENTS\" B ON  A.COLUMN_NAME=B.COLNAME AND  A.TABLE_NAME=B.TVNAME  AND B.SCHNAME=user() ) T WHERE 1=1 ";
/* 236:312 */     Map<String, List<ColumnModel>> map = new HashMap();
/* 237:313 */     if ((tableNames != null) && (tableNames.size() == 0)) {
/* 238:314 */       return map;
/* 239:    */     }
/* 240:316 */     StringBuffer buf = new StringBuffer();
/* 241:317 */     for (String str : tableNames) {
/* 242:318 */       buf.append("'" + str + "',");
/* 243:    */     }
/* 244:320 */     buf.deleteCharAt(buf.length() - 1);
/* 245:321 */     sql = sql + " AND T.TABLE_NAME IN (" + buf.toString() + ") ";
/* 246:    */     
/* 247:    */ 
/* 248:    */ 
/* 249:    */ 
/* 250:326 */     Long b = Long.valueOf(System.currentTimeMillis());
/* 251:327 */     List<ColumnModel> columnModels = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, new HashMap(), new DmColumnMap());
/* 252:328 */     this.logger.info(String.valueOf(System.currentTimeMillis() - b.longValue()));
/* 253:329 */     for (ColumnModel columnModel : columnModels)
/* 254:    */     {
/* 255:330 */       String tableName = columnModel.getTableName();
/* 256:331 */       if (map.containsKey(tableName))
/* 257:    */       {
/* 258:332 */         ((List)map.get(tableName)).add(columnModel);
/* 259:    */       }
/* 260:    */       else
/* 261:    */       {
/* 262:334 */         List<ColumnModel> cols = new ArrayList();
/* 263:335 */         cols.add(columnModel);
/* 264:336 */         map.put(tableName, cols);
/* 265:    */       }
/* 266:    */     }
/* 267:339 */     return map;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public String getAllTableSql()
/* 271:    */   {
/* 272:344 */     return this.sqlAllTables;
/* 273:    */   }
/* 274:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.impl.DmTableMeta
 * JD-Core Version:    0.7.0.1
 */