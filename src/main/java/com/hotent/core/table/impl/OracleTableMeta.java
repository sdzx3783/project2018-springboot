/*   1:    */ package com.hotent.core.table.impl;
/*   2:    */ 
/*   3:    */ import com.hotent.core.db.datasource.JdbcTemplateUtil;
/*   4:    */ import com.hotent.core.page.PageBean;
/*   5:    */ import com.hotent.core.table.BaseTableMeta;
/*   6:    */ import com.hotent.core.table.ColumnModel;
/*   7:    */ import com.hotent.core.table.TableModel;
/*   8:    */ import com.hotent.core.table.colmap.OracleColumnMap;
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
/*  22:    */ import org.springframework.jdbc.core.JdbcTemplate;
/*  23:    */ import org.springframework.jdbc.core.RowMapper;
/*  24:    */ import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
/*  25:    */ import org.springframework.stereotype.Component;
/*  26:    */ 
/*  27:    */ @Component
/*  28:    */ public class OracleTableMeta
/*  29:    */   extends BaseTableMeta
/*  30:    */ {
/*  31:    */   @Resource
/*  32:    */   private JdbcTemplate jdbcTemplate;
/*  33: 42 */   private String sqlPk = "select column_name from user_constraints c,user_cons_columns col where c.constraint_name=col.constraint_name and c.constraint_type='P'and c.table_name='%s'";
/*  34: 47 */   private String sqlTableComment = "select TABLE_NAME,DECODE(COMMENTS,null,TABLE_NAME,comments) comments from user_tab_comments  where table_type='TABLE' AND table_name ='%s'";
/*  35: 52 */   private final String SQL_GET_COLUMNS = "SELECT  \tA.TABLE_NAME TABLE_NAME,  \tA.COLUMN_NAME NAME,  \tA.DATA_TYPE TYPENAME,  \tA.DATA_LENGTH LENGTH,   \tA.DATA_PRECISION PRECISION,  \tA.DATA_SCALE SCALE,  \tA.DATA_DEFAULT,  \tA.NULLABLE,   \tDECODE(B.COMMENTS,NULL,A.COLUMN_NAME,B.COMMENTS) DESCRIPTION,  \t(    \t  SELECT    \t    COUNT(*)    \t  FROM     \t    USER_CONSTRAINTS CONS,     \t   USER_CONS_COLUMNS CONS_C      \t WHERE      \t   CONS.CONSTRAINT_NAME=CONS_C.CONSTRAINT_NAME     \t   AND CONS.CONSTRAINT_TYPE='P'     \t   AND CONS.TABLE_NAME=B.TABLE_NAME      \t  AND CONS_C.COLUMN_NAME=A.COLUMN_NAME   \t ) AS IS_PK  FROM   \t USER_TAB_COLUMNS A,  \tUSER_COL_COMMENTS B   WHERE   \tA.COLUMN_NAME=B.COLUMN_NAME  \tAND A.TABLE_NAME = B.TABLE_NAME  \tAND A.TABLE_NAME='%s'  ORDER BY   \tA.COLUMN_ID";
/*  36: 86 */   private final String SQL_GET_COLUMNS_BATCH = "SELECT  \tA.TABLE_NAME TABLE_NAME,  \tA.COLUMN_NAME NAME,  \tA.DATA_TYPE TYPENAME,  \tA.DATA_LENGTH LENGTH,   \tA.DATA_PRECISION PRECISION,  \tA.DATA_SCALE SCALE,  \tA.DATA_DEFAULT,  \tA.NULLABLE,   \tDECODE(B.COMMENTS,NULL,A.COLUMN_NAME,B.COMMENTS) DESCRIPTION,  \t(    \t  SELECT    \t    COUNT(*)    \t  FROM     \t    USER_CONSTRAINTS CONS,     \t   USER_CONS_COLUMNS CONS_C      \t WHERE      \t   CONS.CONSTRAINT_NAME=CONS_C.CONSTRAINT_NAME     \t   AND CONS.CONSTRAINT_TYPE='P'     \t   AND CONS.TABLE_NAME=B.TABLE_NAME      \t  AND CONS_C.COLUMN_NAME=A.COLUMN_NAME   \t ) AS IS_PK  FROM   \tUSER_TAB_COLUMNS A,  \tUSER_COL_COMMENTS B   WHERE   \tA.COLUMN_NAME=B.COLUMN_NAME  \tAND A.TABLE_NAME = B.TABLE_NAME ";
/*  37:118 */   private String sqlAllTables = "select a.TABLE_NAME,DECODE(b.COMMENTS,null,a.TABLE_NAME,b.comments) comments from user_tables a,user_tab_comments b where a.table_name=b.table_name and b.table_type='TABLE'  ";
/*  38:    */   
/*  39:    */   public Map<String, String> getTablesByName(String tableName)
/*  40:    */   {
/*  41:126 */     String sql = this.sqlAllTables;
/*  42:127 */     if (StringUtil.isNotEmpty(tableName)) {
/*  43:128 */       sql = this.sqlAllTables + " and  lower(a.table_name) like '%" + tableName.toLowerCase() + "%'";
/*  44:    */     }
/*  45:131 */     Map parameter = new HashMap();
/*  46:132 */     List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, parameter, new RowMapper()
/*  47:    */     {
/*  48:    */       public Map<String, String> mapRow(ResultSet rs, int row)
/*  49:    */         throws SQLException
/*  50:    */       {
/*  51:136 */         String tableName = rs.getString("table_name");
/*  52:137 */         String comments = rs.getString("comments");
/*  53:138 */         Map<String, String> map = new HashMap();
/*  54:139 */         map.put("name", tableName);
/*  55:140 */         map.put("comments", comments);
/*  56:141 */         return map;
/*  57:    */       }
/*  58:143 */     });
/*  59:144 */     Map<String, String> map = new LinkedHashMap();
/*  60:145 */     for (int i = 0; i < list.size(); i++)
/*  61:    */     {
/*  62:146 */       Map<String, String> tmp = (Map)list.get(i);
/*  63:147 */       String name = (String)tmp.get("name");
/*  64:148 */       String comments = (String)tmp.get("comments");
/*  65:149 */       map.put(name, comments);
/*  66:    */     }
/*  67:152 */     return map;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Map<String, String> getTablesByName(List<String> names)
/*  71:    */   {
/*  72:157 */     StringBuffer sb = new StringBuffer();
/*  73:158 */     for (String name : names)
/*  74:    */     {
/*  75:159 */       sb.append("'");
/*  76:160 */       sb.append(name);
/*  77:161 */       sb.append("',");
/*  78:    */     }
/*  79:163 */     sb.deleteCharAt(sb.length() - 1);
/*  80:164 */     String sql = this.sqlAllTables + " and  lower(table_name) in (" + sb.toString().toLowerCase() + ")";
/*  81:    */     
/*  82:    */ 
/*  83:    */ 
/*  84:168 */     Map parameter = new HashMap();
/*  85:169 */     List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, parameter, new RowMapper()
/*  86:    */     {
/*  87:    */       public Map<String, String> mapRow(ResultSet rs, int row)
/*  88:    */         throws SQLException
/*  89:    */       {
/*  90:173 */         String tableName = rs.getString("TABLE_NAME");
/*  91:174 */         String comments = rs.getString("COMMENTS");
/*  92:175 */         Map<String, String> map = new HashMap();
/*  93:176 */         map.put("NAME", tableName);
/*  94:177 */         map.put("COMMENTS", comments);
/*  95:178 */         return map;
/*  96:    */       }
/*  97:180 */     });
/*  98:181 */     Map<String, String> map = new LinkedHashMap();
/*  99:182 */     for (int i = 0; i < list.size(); i++)
/* 100:    */     {
/* 101:183 */       Map<String, String> tmp = (Map)list.get(i);
/* 102:184 */       String name = (String)tmp.get("NAME");
/* 103:185 */       String comments = (String)tmp.get("COMMENTS");
/* 104:186 */       map.put(name, comments);
/* 105:    */     }
/* 106:189 */     return map;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public TableModel getTableByName(String tableName)
/* 110:    */   {
/* 111:197 */     tableName = tableName.toUpperCase();
/* 112:198 */     TableModel model = getTableModel(tableName);
/* 113:    */     
/* 114:200 */     List<ColumnModel> columnList = getColumnsByTableName(tableName);
/* 115:201 */     model.setColumnList(columnList);
/* 116:202 */     return model;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public List<TableModel> getTablesByName(String tableName, PageBean pageBean)
/* 120:    */     throws Exception
/* 121:    */   {
/* 122:208 */     String sql = this.sqlAllTables;
/* 123:210 */     if (StringUtil.isNotEmpty(tableName)) {
/* 124:211 */       sql = sql + " AND  LOWER(table_name) LIKE '%" + tableName.toLowerCase() + "%'";
/* 125:    */     }
/* 126:213 */     RowMapper<TableModel> rowMapper = new RowMapper()
/* 127:    */     {
/* 128:    */       public TableModel mapRow(ResultSet rs, int row)
/* 129:    */         throws SQLException
/* 130:    */       {
/* 131:217 */         TableModel tableModel = new TableModel();
/* 132:218 */         tableModel.setName(rs.getString("TABLE_NAME"));
/* 133:219 */         tableModel.setComment(rs.getString("COMMENTS"));
/* 134:220 */         return tableModel;
/* 135:    */       }
/* 136:222 */     };
/* 137:223 */     List<TableModel> tableModels = getForList(sql, pageBean, rowMapper, "oracle");
/* 138:224 */     List<String> tableNames = new ArrayList();
/* 139:226 */     for (TableModel model : tableModels) {
/* 140:227 */       tableNames.add(model.getName());
/* 141:    */     }
/* 142:230 */     Map<String, List<ColumnModel>> tableColumnsMap = getColumnsByTableName(tableNames);
/* 143:232 */     for (Iterator i$ = tableColumnsMap.entrySet().iterator(); i$.hasNext();)
/* 144:    */     {
/* 145:232 */       entry = (Map.Entry)i$.next();
/* 146:234 */       for (TableModel model : tableModels) {
/* 147:235 */         if (model.getName().equalsIgnoreCase((String)entry.getKey())) {
/* 148:236 */           model.setColumnList((List)entry.getValue());
/* 149:    */         }
/* 150:    */       }
/* 151:    */     }
/* 152:    */     Map.Entry<String, List<ColumnModel>> entry;
/* 153:241 */     return tableModels;
/* 154:    */   }
/* 155:    */   
/* 156:    */   private String getPkColumn(String tableName)
/* 157:    */   {
/* 158:251 */     tableName = tableName.toUpperCase();
/* 159:    */     
/* 160:    */ 
/* 161:254 */     String sql = String.format(this.sqlPk, new Object[] { tableName });
/* 162:255 */     Object rtn = this.jdbcTemplate.queryForObject(sql, null, new RowMapper()
/* 163:    */     {
/* 164:    */       public String mapRow(ResultSet rs, int row)
/* 165:    */         throws SQLException
/* 166:    */       {
/* 167:259 */         return rs.getString("COLUMN_NAME");
/* 168:    */       }
/* 169:    */     });
/* 170:262 */     if (rtn == null) {
/* 171:263 */       return "";
/* 172:    */     }
/* 173:265 */     return rtn.toString();
/* 174:    */   }
/* 175:    */   
/* 176:    */   private List<String> getPkColumns(String tableName)
/* 177:    */   {
/* 178:276 */     tableName = tableName.toUpperCase();
/* 179:    */     
/* 180:    */ 
/* 181:279 */     String sql = String.format(this.sqlPk, new Object[] { tableName });
/* 182:280 */     List<String> rtn = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, new HashMap(), new RowMapper()
/* 183:    */     {
/* 184:    */       public String mapRow(ResultSet rs, int rowNum)
/* 185:    */         throws SQLException
/* 186:    */       {
/* 187:283 */         return rs.getString("column_name");
/* 188:    */       }
/* 189:285 */     });
/* 190:286 */     return rtn;
/* 191:    */   }
/* 192:    */   
/* 193:    */   private TableModel getTableModel(final String tableName)
/* 194:    */   {
/* 195:298 */     String sql = String.format(this.sqlTableComment, new Object[] { tableName });
/* 196:299 */     TableModel tableModel = (TableModel)this.jdbcTemplate.queryForObject(sql, null, new RowMapper()
/* 197:    */     {
/* 198:    */       public TableModel mapRow(ResultSet rs, int row)
/* 199:    */         throws SQLException
/* 200:    */       {
/* 201:304 */         TableModel tableModel = new TableModel();
/* 202:305 */         tableModel.setName(tableName);
/* 203:306 */         tableModel.setComment(rs.getString("comments"));
/* 204:307 */         return tableModel;
/* 205:    */       }
/* 206:    */     });
/* 207:310 */     if (BeanUtils.isEmpty(tableModel)) {
/* 208:311 */       tableModel = new TableModel();
/* 209:    */     }
/* 210:313 */     return tableModel;
/* 211:    */   }
/* 212:    */   
/* 213:    */   private List<ColumnModel> getColumnsByTableName(String tableName)
/* 214:    */   {
/* 215:323 */     String sql = String.format("SELECT  \tA.TABLE_NAME TABLE_NAME,  \tA.COLUMN_NAME NAME,  \tA.DATA_TYPE TYPENAME,  \tA.DATA_LENGTH LENGTH,   \tA.DATA_PRECISION PRECISION,  \tA.DATA_SCALE SCALE,  \tA.DATA_DEFAULT,  \tA.NULLABLE,   \tDECODE(B.COMMENTS,NULL,A.COLUMN_NAME,B.COMMENTS) DESCRIPTION,  \t(    \t  SELECT    \t    COUNT(*)    \t  FROM     \t    USER_CONSTRAINTS CONS,     \t   USER_CONS_COLUMNS CONS_C      \t WHERE      \t   CONS.CONSTRAINT_NAME=CONS_C.CONSTRAINT_NAME     \t   AND CONS.CONSTRAINT_TYPE='P'     \t   AND CONS.TABLE_NAME=B.TABLE_NAME      \t  AND CONS_C.COLUMN_NAME=A.COLUMN_NAME   \t ) AS IS_PK  FROM   \t USER_TAB_COLUMNS A,  \tUSER_COL_COMMENTS B   WHERE   \tA.COLUMN_NAME=B.COLUMN_NAME  \tAND A.TABLE_NAME = B.TABLE_NAME  \tAND A.TABLE_NAME='%s'  ORDER BY   \tA.COLUMN_ID", new Object[] { tableName });
/* 216:    */     
/* 217:    */ 
/* 218:326 */     Map<String, Object> map = new HashMap();
/* 219:327 */     List<ColumnModel> columnList = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, map, new OracleColumnMap());
/* 220:328 */     return columnList;
/* 221:    */   }
/* 222:    */   
/* 223:    */   private Map<String, List<ColumnModel>> getColumnsByTableName(List<String> tableNames)
/* 224:    */   {
/* 225:338 */     String sql = "SELECT  \tA.TABLE_NAME TABLE_NAME,  \tA.COLUMN_NAME NAME,  \tA.DATA_TYPE TYPENAME,  \tA.DATA_LENGTH LENGTH,   \tA.DATA_PRECISION PRECISION,  \tA.DATA_SCALE SCALE,  \tA.DATA_DEFAULT,  \tA.NULLABLE,   \tDECODE(B.COMMENTS,NULL,A.COLUMN_NAME,B.COMMENTS) DESCRIPTION,  \t(    \t  SELECT    \t    COUNT(*)    \t  FROM     \t    USER_CONSTRAINTS CONS,     \t   USER_CONS_COLUMNS CONS_C      \t WHERE      \t   CONS.CONSTRAINT_NAME=CONS_C.CONSTRAINT_NAME     \t   AND CONS.CONSTRAINT_TYPE='P'     \t   AND CONS.TABLE_NAME=B.TABLE_NAME      \t  AND CONS_C.COLUMN_NAME=A.COLUMN_NAME   \t ) AS IS_PK  FROM   \tUSER_TAB_COLUMNS A,  \tUSER_COL_COMMENTS B   WHERE   \tA.COLUMN_NAME=B.COLUMN_NAME  \tAND A.TABLE_NAME = B.TABLE_NAME ";
/* 226:339 */     Map<String, List<ColumnModel>> map = new HashMap();
/* 227:340 */     if ((tableNames != null) && (tableNames.size() == 0)) {
/* 228:341 */       return map;
/* 229:    */     }
/* 230:343 */     StringBuffer buf = new StringBuffer();
/* 231:344 */     for (String str : tableNames) {
/* 232:345 */       buf.append("'" + str + "',");
/* 233:    */     }
/* 234:347 */     buf.deleteCharAt(buf.length() - 1);
/* 235:348 */     sql = sql + " AND A.TABLE_NAME IN (" + buf.toString() + ") ";
/* 236:    */     
/* 237:    */ 
/* 238:    */ 
/* 239:    */ 
/* 240:    */ 
/* 241:354 */     Long b = Long.valueOf(System.currentTimeMillis());
/* 242:355 */     List<ColumnModel> columnModels = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, new HashMap(), new OracleColumnMap());
/* 243:357 */     for (ColumnModel columnModel : columnModels)
/* 244:    */     {
/* 245:358 */       String tableName = columnModel.getTableName();
/* 246:359 */       if (map.containsKey(tableName))
/* 247:    */       {
/* 248:360 */         ((List)map.get(tableName)).add(columnModel);
/* 249:    */       }
/* 250:    */       else
/* 251:    */       {
/* 252:362 */         List<ColumnModel> cols = new ArrayList();
/* 253:363 */         cols.add(columnModel);
/* 254:364 */         map.put(tableName, cols);
/* 255:    */       }
/* 256:    */     }
/* 257:367 */     return map;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public String getAllTableSql()
/* 261:    */   {
/* 262:372 */     return this.sqlAllTables;
/* 263:    */   }
/* 264:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.impl.OracleTableMeta
 * JD-Core Version:    0.7.0.1
 */