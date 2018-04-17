/*   1:    */ package com.hotent.core.table.impl;
/*   2:    */ 
/*   3:    */ import com.hotent.core.db.datasource.JdbcTemplateUtil;
/*   4:    */ import com.hotent.core.page.PageBean;
/*   5:    */ import com.hotent.core.table.BaseTableMeta;
/*   6:    */ import com.hotent.core.table.ColumnModel;
/*   7:    */ import com.hotent.core.table.TableModel;
/*   8:    */ import com.hotent.core.table.colmap.MySqlColumnMap;
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
/*  28:    */ public class MySqlTableMeta
/*  29:    */   extends BaseTableMeta
/*  30:    */ {
/*  31:    */   @Resource
/*  32:    */   private JdbcTemplate jdbcTemplate;
/*  33: 39 */   private final String SQL_GET_COLUMNS = "SELECT TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_OCTET_LENGTH LENGTH, NUMERIC_PRECISION PRECISIONS,NUMERIC_SCALE SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM  INFORMATION_SCHEMA.COLUMNS  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='%s' ";
/*  34: 46 */   private final String SQL_GET_COLUMNS_BATCH = "SELECT TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_OCTET_LENGTH LENGTH, NUMERIC_PRECISION PRECISIONS,NUMERIC_SCALE SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM  INFORMATION_SCHEMA.COLUMNS  WHERE TABLE_SCHEMA=DATABASE() ";
/*  35: 53 */   private final String sqlComment = "select table_name,table_comment  from information_schema.tables t where t.table_schema=DATABASE() and table_name='%s' ";
/*  36: 55 */   private final String sqlAllTable = "select table_name,table_comment from information_schema.tables t where t.table_type='BASE TABLE' AND t.table_schema=DATABASE()";
/*  37: 57 */   private final String sqlPk = "SELECT k.column_name name FROM information_schema.table_constraints t JOIN information_schema.key_column_usage k USING(constraint_name,table_schema,table_name) WHERE t.constraint_type='PRIMARY KEY' AND t.table_schema=DATABASE() AND t.table_name='%s'";
/*  38:    */   
/*  39:    */   public TableModel getTableByName(String tableName)
/*  40:    */   {
/*  41: 72 */     TableModel model = getTableModel(tableName);
/*  42:    */     
/*  43: 74 */     List<ColumnModel> columnList = getColumnsByTableName(tableName);
/*  44: 75 */     model.setColumnList(columnList);
/*  45: 76 */     return model;
/*  46:    */   }
/*  47:    */   
/*  48:    */   private String getPkColumn(String tableName)
/*  49:    */   {
/*  50: 87 */     String sql = String.format("SELECT k.column_name name FROM information_schema.table_constraints t JOIN information_schema.key_column_usage k USING(constraint_name,table_schema,table_name) WHERE t.constraint_type='PRIMARY KEY' AND t.table_schema=DATABASE() AND t.table_name='%s'", new Object[] { tableName });
/*  51: 88 */     Object rtn = this.jdbcTemplate.queryForObject(sql, null, new RowMapper()
/*  52:    */     {
/*  53:    */       public String mapRow(ResultSet rs, int row)
/*  54:    */         throws SQLException
/*  55:    */       {
/*  56: 92 */         return rs.getString("name");
/*  57:    */       }
/*  58:    */     });
/*  59: 95 */     if (rtn == null) {
/*  60: 96 */       return "";
/*  61:    */     }
/*  62: 98 */     return rtn.toString();
/*  63:    */   }
/*  64:    */   
/*  65:    */   private List<ColumnModel> getColumnsByTableName(String tableName)
/*  66:    */   {
/*  67:108 */     String sql = String.format("SELECT TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_OCTET_LENGTH LENGTH, NUMERIC_PRECISION PRECISIONS,NUMERIC_SCALE SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM  INFORMATION_SCHEMA.COLUMNS  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='%s' ", new Object[] { tableName });
/*  68:    */     
/*  69:    */ 
/*  70:111 */     Map<String, Object> map = new HashMap();
/*  71:    */     
/*  72:113 */     List<ColumnModel> list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, map, new MySqlColumnMap());
/*  73:114 */     for (ColumnModel model : list) {
/*  74:115 */       model.setTableName(tableName);
/*  75:    */     }
/*  76:117 */     return list;
/*  77:    */   }
/*  78:    */   
/*  79:    */   private Map<String, List<ColumnModel>> getColumnsByTableName(List<String> tableNames)
/*  80:    */   {
/*  81:126 */     String sql = "SELECT TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_OCTET_LENGTH LENGTH, NUMERIC_PRECISION PRECISIONS,NUMERIC_SCALE SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM  INFORMATION_SCHEMA.COLUMNS  WHERE TABLE_SCHEMA=DATABASE() ";
/*  82:127 */     Map<String, List<ColumnModel>> map = new HashMap();
/*  83:128 */     if ((tableNames != null) && (tableNames.size() == 0)) {
/*  84:129 */       return map;
/*  85:    */     }
/*  86:131 */     StringBuffer buf = new StringBuffer();
/*  87:132 */     for (String str : tableNames) {
/*  88:133 */       buf.append("'" + str + "',");
/*  89:    */     }
/*  90:135 */     buf.deleteCharAt(buf.length() - 1);
/*  91:136 */     sql = sql + " AND TABLE_NAME IN (" + buf.toString() + ") ";
/*  92:    */     
/*  93:    */ 
/*  94:    */ 
/*  95:140 */     List<ColumnModel> columnModels = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, new HashMap(), new MySqlColumnMap());
/*  96:141 */     for (ColumnModel columnModel : columnModels)
/*  97:    */     {
/*  98:142 */       String tableName = columnModel.getTableName();
/*  99:143 */       if (map.containsKey(tableName))
/* 100:    */       {
/* 101:144 */         ((List)map.get(tableName)).add(columnModel);
/* 102:    */       }
/* 103:    */       else
/* 104:    */       {
/* 105:146 */         List<ColumnModel> cols = new ArrayList();
/* 106:147 */         cols.add(columnModel);
/* 107:148 */         map.put(tableName, cols);
/* 108:    */       }
/* 109:    */     }
/* 110:151 */     return map;
/* 111:    */   }
/* 112:    */   
/* 113:    */   private TableModel getTableModel(final String tableName)
/* 114:    */   {
/* 115:163 */     String sql = String.format("select table_name,table_comment  from information_schema.tables t where t.table_schema=DATABASE() and table_name='%s' ", new Object[] { tableName });
/* 116:164 */     TableModel tableModel = (TableModel)this.jdbcTemplate.queryForObject(sql, null, new RowMapper()
/* 117:    */     {
/* 118:    */       public TableModel mapRow(ResultSet rs, int row)
/* 119:    */         throws SQLException
/* 120:    */       {
/* 121:169 */         TableModel tableModel = new TableModel();
/* 122:170 */         String comments = rs.getString("table_comment");
/* 123:171 */         comments = MySqlTableMeta.getComments(comments, tableName);
/* 124:172 */         tableModel.setName(tableName);
/* 125:173 */         tableModel.setComment(comments);
/* 126:174 */         return tableModel;
/* 127:    */       }
/* 128:    */     });
/* 129:177 */     if (BeanUtils.isEmpty(tableModel)) {
/* 130:178 */       tableModel = new TableModel();
/* 131:    */     }
/* 132:180 */     return tableModel;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public Map<String, String> getTablesByName(String tableName)
/* 136:    */   {
/* 137:185 */     String sql = "select table_name,table_comment from information_schema.tables t where t.table_type='BASE TABLE' AND t.table_schema=DATABASE()";
/* 138:186 */     if (StringUtil.isNotEmpty(tableName)) {
/* 139:187 */       sql = sql + " AND TABLE_NAME LIKE '%" + tableName + "%'";
/* 140:    */     }
/* 141:190 */     Map parameter = new HashMap();
/* 142:191 */     List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, parameter, new RowMapper()
/* 143:    */     {
/* 144:    */       public Map<String, String> mapRow(ResultSet rs, int row)
/* 145:    */         throws SQLException
/* 146:    */       {
/* 147:195 */         String tableName = rs.getString("table_name");
/* 148:196 */         String comments = rs.getString("table_comment");
/* 149:197 */         Map<String, String> map = new HashMap();
/* 150:198 */         map.put("name", tableName);
/* 151:199 */         map.put("comments", comments);
/* 152:200 */         return map;
/* 153:    */       }
/* 154:202 */     });
/* 155:203 */     Map<String, String> map = new LinkedHashMap();
/* 156:204 */     for (int i = 0; i < list.size(); i++)
/* 157:    */     {
/* 158:205 */       Map<String, String> tmp = (Map)list.get(i);
/* 159:206 */       String name = (String)tmp.get("name");
/* 160:207 */       String comments = (String)tmp.get("comments");
/* 161:208 */       comments = getComments(comments, name);
/* 162:209 */       map.put(name, comments);
/* 163:    */     }
/* 164:212 */     return map;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public static String getComments(String comments, String defaultValue)
/* 168:    */   {
/* 169:223 */     if (StringUtil.isEmpty(comments)) {
/* 170:223 */       return defaultValue;
/* 171:    */     }
/* 172:224 */     int idx = comments.indexOf("InnoDB free");
/* 173:225 */     if (idx > -1) {
/* 174:226 */       comments = StringUtil.trimSufffix(comments.substring(0, idx).trim(), ";");
/* 175:    */     }
/* 176:228 */     if (StringUtil.isEmpty(comments)) {
/* 177:229 */       comments = defaultValue;
/* 178:    */     }
/* 179:231 */     return comments;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public Map<String, String> getTablesByName(List<String> names)
/* 183:    */   {
/* 184:236 */     StringBuffer sb = new StringBuffer();
/* 185:237 */     for (String name : names)
/* 186:    */     {
/* 187:238 */       sb.append("'");
/* 188:239 */       sb.append(name);
/* 189:240 */       sb.append("',");
/* 190:    */     }
/* 191:242 */     sb.deleteCharAt(sb.length() - 1);
/* 192:243 */     String sql = "select table_name,table_comment from information_schema.tables t where t.table_type='BASE TABLE' AND t.table_schema=DATABASE() and  lower(table_name) in (" + sb.toString().toLowerCase() + ")";
/* 193:    */     
/* 194:    */ 
/* 195:    */ 
/* 196:247 */     Map parameter = new HashMap();
/* 197:248 */     List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, parameter, new RowMapper()
/* 198:    */     {
/* 199:    */       public Map<String, String> mapRow(ResultSet rs, int row)
/* 200:    */         throws SQLException
/* 201:    */       {
/* 202:252 */         String tableName = rs.getString("table_name");
/* 203:253 */         String comments = rs.getString("table_comment");
/* 204:254 */         Map<String, String> map = new HashMap();
/* 205:255 */         map.put("tableName", tableName);
/* 206:256 */         map.put("tableComment", comments);
/* 207:257 */         return map;
/* 208:    */       }
/* 209:259 */     });
/* 210:260 */     Map<String, String> map = new LinkedHashMap();
/* 211:261 */     for (int i = 0; i < list.size(); i++)
/* 212:    */     {
/* 213:262 */       Map<String, String> tmp = (Map)list.get(i);
/* 214:263 */       String name = (String)tmp.get("tableName");
/* 215:264 */       String comments = (String)tmp.get("tableComment");
/* 216:265 */       map.put(name, comments);
/* 217:    */     }
/* 218:268 */     return map;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public List<TableModel> getTablesByName(String tableName, PageBean pageBean)
/* 222:    */     throws Exception
/* 223:    */   {
/* 224:275 */     String sql = "select table_name,table_comment from information_schema.tables t where t.table_type='BASE TABLE' AND t.table_schema=DATABASE()";
/* 225:276 */     if (StringUtil.isNotEmpty(tableName)) {
/* 226:277 */       sql = sql + " AND TABLE_NAME LIKE '%" + tableName + "%'";
/* 227:    */     }
/* 228:279 */     RowMapper<TableModel> rowMapper = new RowMapper()
/* 229:    */     {
/* 230:    */       public TableModel mapRow(ResultSet rs, int row)
/* 231:    */         throws SQLException
/* 232:    */       {
/* 233:283 */         TableModel tableModel = new TableModel();
/* 234:284 */         tableModel.setName(rs.getString("TABLE_NAME"));
/* 235:285 */         String comments = rs.getString("TABLE_COMMENT");
/* 236:286 */         comments = MySqlTableMeta.getComments(comments, tableModel.getName());
/* 237:287 */         tableModel.setComment(comments);
/* 238:288 */         return tableModel;
/* 239:    */       }
/* 240:290 */     };
/* 241:291 */     List<TableModel> tableModels = getForList(sql, pageBean, rowMapper, "mysql");
/* 242:    */     
/* 243:293 */     List<String> tableNames = new ArrayList();
/* 244:295 */     for (TableModel model : tableModels) {
/* 245:296 */       tableNames.add(model.getName());
/* 246:    */     }
/* 247:299 */     Map<String, List<ColumnModel>> tableColumnsMap = getColumnsByTableName(tableNames);
/* 248:301 */     for (Iterator i$ = tableColumnsMap.entrySet().iterator(); i$.hasNext();)
/* 249:    */     {
/* 250:301 */       entry = (Map.Entry)i$.next();
/* 251:303 */       for (TableModel model : tableModels) {
/* 252:304 */         if (model.getName().equalsIgnoreCase((String)entry.getKey())) {
/* 253:305 */           model.setColumnList((List)entry.getValue());
/* 254:    */         }
/* 255:    */       }
/* 256:    */     }
/* 257:    */     Map.Entry<String, List<ColumnModel>> entry;
/* 258:309 */     return tableModels;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public String getAllTableSql()
/* 262:    */   {
/* 263:315 */     return "select table_name,table_comment from information_schema.tables t where t.table_type='BASE TABLE' AND t.table_schema=DATABASE()";
/* 264:    */   }
/* 265:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.impl.MySqlTableMeta
 * JD-Core Version:    0.7.0.1
 */