/*   1:    */ package com.hotent.core.table.impl;
/*   2:    */ 
/*   3:    */ import com.hotent.core.db.datasource.JdbcTemplateUtil;
/*   4:    */ import com.hotent.core.page.PageBean;
/*   5:    */ import com.hotent.core.table.BaseTableMeta;
/*   6:    */ import com.hotent.core.table.ColumnModel;
/*   7:    */ import com.hotent.core.table.TableModel;
/*   8:    */ import com.hotent.core.table.colmap.SqlServerColumnMap;
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
/*  28:    */ public class SqlServerTableMeta
/*  29:    */   extends BaseTableMeta
/*  30:    */ {
/*  31:    */   @Resource
/*  32:    */   private JdbcTemplate jdbcTemplate;
/*  33: 42 */   private String sqlPk = "SELECT c.COLUMN_NAME COLUMN_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS pk ,INFORMATION_SCHEMA.KEY_COLUMN_USAGE c WHERE \tpk.TABLE_NAME LIKE '%s' and\tCONSTRAINT_TYPE = 'PRIMARY KEY' and\tc.TABLE_NAME = pk.TABLE_NAME and\tc.CONSTRAINT_NAME = pk.CONSTRAINT_NAME ";
/*  34: 53 */   private String sqlTableComment = "select comment from (select a.name name, cast(b.value as varchar) comment from sys.tables a, sys.extended_properties b where a.type='U' and a.object_id=b.major_id and b.minor_id=0 union(select name,name comment from sys.tables where type='U' and object_id not in (select DISTINCT major_id from sys.extended_properties where minor_id=0))) t where 1=1 and t.name='%s'";
/*  35: 58 */   private String SQL_GET_COLUMNS = "SELECT B.NAME TABLE_NAME,A.NAME NAME, C.NAME TYPENAME, A.MAX_LENGTH LENGTH, A.IS_NULLABLE IS_NULLABLE,A.PRECISION PRECISION,A.SCALE SCALE,   (  \t\tSELECT COUNT(*)  \t\tFROM   \t\tSYS.IDENTITY_COLUMNS   \t\tWHERE SYS.IDENTITY_COLUMNS.OBJECT_ID = A.OBJECT_ID AND A.COLUMN_ID = SYS.IDENTITY_COLUMNS.COLUMN_ID\t) AS AUTOGEN,  \t(  \t\tSELECT CAST(VALUE AS VARCHAR)  \t\tFROM SYS.EXTENDED_PROPERTIES   \t\tWHERE SYS.EXTENDED_PROPERTIES.MAJOR_ID = A.OBJECT_ID AND SYS.EXTENDED_PROPERTIES.MINOR_ID = A.COLUMN_ID  \t) AS DESCRIPTION,  \t(  \t\tSELECT COUNT(*)  \t\tFROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS pk ,INFORMATION_SCHEMA.KEY_COLUMN_USAGE kcu  \t\tWHERE \tpk.TABLE_NAME = B.NAME  \t\t\t AND\tCONSTRAINT_TYPE = 'PRIMARY KEY'   \t\t\t AND\tKCU.TABLE_NAME = PK.TABLE_NAME   \t\t\t AND\tKCU.CONSTRAINT_NAME = PK.CONSTRAINT_NAME  \t\t\t AND \tKCU.COLUMN_NAME =A.NAME  \t) AS IS_PK  FROM SYS.COLUMNS A, SYS.TABLES B, SYS.TYPES C   WHERE A.OBJECT_ID = B.OBJECT_ID AND A.SYSTEM_TYPE_ID=C.SYSTEM_TYPE_ID AND B.NAME='%s'  \t\tAND C.NAME<>'SYSNAME' \t\tORDER BY A.COLUMN_ID ";
/*  36: 84 */   private String SQL_GET_COLUMNS_BATCH = "SELECT B.NAME TABLE_NAME,A.NAME NAME, C.NAME TYPENAME, A.MAX_LENGTH LENGTH, A.IS_NULLABLE IS_NULLABLE,A.PRECISION PRECISION,A.SCALE SCALE,  (  \tSELECT COUNT(*)  \tFROM   \tSYS.IDENTITY_COLUMNS   WHERE SYS.IDENTITY_COLUMNS.OBJECT_ID = A.OBJECT_ID AND A.COLUMN_ID = SYS.IDENTITY_COLUMNS.COLUMN_ID) AS AUTOGEN,  \t(  \t\t\tSELECT CAST(VALUE AS VARCHAR)  \t\t\tFROM SYS.EXTENDED_PROPERTIES   \t\tWHERE SYS.EXTENDED_PROPERTIES.MAJOR_ID = A.OBJECT_ID AND SYS.EXTENDED_PROPERTIES.MINOR_ID = A.COLUMN_ID  \t) AS DESCRIPTION,  \t(  \t\tSELECT COUNT(*)  \t\tFROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS pk ,INFORMATION_SCHEMA.KEY_COLUMN_USAGE kcu  \t\tWHERE \tpk.TABLE_NAME = B.NAME  \t\t\t AND\tCONSTRAINT_TYPE = 'PRIMARY KEY'   \t\t\t AND\tKCU.TABLE_NAME = PK.TABLE_NAME   \t\t\t AND\tKCU.CONSTRAINT_NAME = PK.CONSTRAINT_NAME  \t\t\t AND \tKCU.COLUMN_NAME =A.NAME  \t) AS IS_PK  FROM SYS.COLUMNS A, SYS.TABLES B, SYS.TYPES C   WHERE A.OBJECT_ID = B.OBJECT_ID AND A.SYSTEM_TYPE_ID=C.SYSTEM_TYPE_ID  \t\tAND C.NAME<>'SYSNAME' ";
/*  37:113 */   private String sqlAllTables = "select * from (select a.name name, cast(b.value as varchar) comment from sys.tables a, sys.extended_properties b where a.type='U' and a.object_id=b.major_id and b.minor_id=0 union(select name,name comment from sys.tables where type='U' and object_id not in (select DISTINCT major_id from sys.extended_properties where minor_id=0))) t where 1=1";
/*  38:    */   
/*  39:    */   public TableModel getTableByName(String tableName)
/*  40:    */   {
/*  41:120 */     TableModel model = getTableModel(tableName);
/*  42:    */     
/*  43:122 */     List<ColumnModel> columnList = getColumnsByTableName(tableName);
/*  44:123 */     model.setColumnList(columnList);
/*  45:124 */     return model;
/*  46:    */   }
/*  47:    */   
/*  48:    */   private String getPkColumn(String tableName)
/*  49:    */   {
/*  50:136 */     String sql = String.format(this.sqlPk, new Object[] { tableName });
/*  51:137 */     Object rtn = this.jdbcTemplate.queryForObject(sql, null, new RowMapper()
/*  52:    */     {
/*  53:    */       public String mapRow(ResultSet rs, int row)
/*  54:    */         throws SQLException
/*  55:    */       {
/*  56:142 */         return rs.getString("COLUMN_NAME");
/*  57:    */       }
/*  58:    */     });
/*  59:145 */     if (rtn == null) {
/*  60:146 */       return "";
/*  61:    */     }
/*  62:148 */     return rtn.toString();
/*  63:    */   }
/*  64:    */   
/*  65:    */   private TableModel getTableModel(final String tableName)
/*  66:    */   {
/*  67:161 */     String sql = String.format(this.sqlTableComment, new Object[] { tableName });
/*  68:162 */     TableModel tableModel = (TableModel)this.jdbcTemplate.queryForObject(sql, null, new RowMapper()
/*  69:    */     {
/*  70:    */       public TableModel mapRow(ResultSet rs, int row)
/*  71:    */         throws SQLException
/*  72:    */       {
/*  73:168 */         TableModel tableModel = new TableModel();
/*  74:169 */         tableModel.setName(tableName);
/*  75:170 */         tableModel.setComment(rs.getString("comment"));
/*  76:171 */         return tableModel;
/*  77:    */       }
/*  78:    */     });
/*  79:174 */     if (BeanUtils.isEmpty(tableModel)) {
/*  80:175 */       tableModel = new TableModel();
/*  81:    */     }
/*  82:177 */     tableModel.setName(tableName);
/*  83:    */     
/*  84:179 */     return tableModel;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Map<String, String> getTablesByName(String tableName)
/*  88:    */   {
/*  89:188 */     String sql = this.sqlAllTables;
/*  90:189 */     if (StringUtil.isNotEmpty(tableName)) {
/*  91:190 */       sql = sql + " and  lower(t.name) like '%" + tableName.toLowerCase() + "%'";
/*  92:    */     }
/*  93:196 */     Map parameter = new HashMap();
/*  94:197 */     List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, parameter, new RowMapper()
/*  95:    */     {
/*  96:    */       public Map<String, String> mapRow(ResultSet rs, int row)
/*  97:    */         throws SQLException
/*  98:    */       {
/*  99:202 */         String tableName = rs.getString("name");
/* 100:203 */         String comments = rs.getString("comment");
/* 101:204 */         Map<String, String> map = new HashMap();
/* 102:205 */         map.put("name", tableName);
/* 103:206 */         map.put("comments", comments);
/* 104:207 */         return map;
/* 105:    */       }
/* 106:209 */     });
/* 107:210 */     Map<String, String> map = new LinkedHashMap();
/* 108:211 */     for (int i = 0; i < list.size(); i++)
/* 109:    */     {
/* 110:212 */       Map<String, String> tmp = (Map)list.get(i);
/* 111:213 */       String name = (String)tmp.get("name");
/* 112:214 */       String comments = (String)tmp.get("comments");
/* 113:215 */       map.put(name, comments);
/* 114:    */     }
/* 115:218 */     return map;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Map<String, String> getTablesByName(List<String> names)
/* 119:    */   {
/* 120:224 */     StringBuffer sb = new StringBuffer();
/* 121:225 */     for (String name : names)
/* 122:    */     {
/* 123:226 */       sb.append("'");
/* 124:227 */       sb.append(name);
/* 125:228 */       sb.append("',");
/* 126:    */     }
/* 127:230 */     sb.deleteCharAt(sb.length() - 1);
/* 128:231 */     String sql = this.sqlAllTables + " and  t.name in (" + sb.toString().toLowerCase() + ")";
/* 129:    */     
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:236 */     Map parameter = new HashMap();
/* 134:237 */     List list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, parameter, new RowMapper()
/* 135:    */     {
/* 136:    */       public Map<String, String> mapRow(ResultSet rs, int row)
/* 137:    */         throws SQLException
/* 138:    */       {
/* 139:241 */         String tableName = rs.getString("name");
/* 140:242 */         String comments = rs.getString("comment");
/* 141:243 */         Map<String, String> map = new HashMap();
/* 142:244 */         map.put("name", tableName);
/* 143:245 */         map.put("comments", comments);
/* 144:246 */         return map;
/* 145:    */       }
/* 146:248 */     });
/* 147:249 */     Map<String, String> map = new LinkedHashMap();
/* 148:250 */     for (int i = 0; i < list.size(); i++)
/* 149:    */     {
/* 150:251 */       Map<String, String> tmp = (Map)list.get(i);
/* 151:252 */       String name = (String)tmp.get("name");
/* 152:253 */       String comments = (String)tmp.get("comments");
/* 153:254 */       map.put(name, comments);
/* 154:    */     }
/* 155:257 */     return map;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public List<TableModel> getTablesByName(String tableName, PageBean pageBean)
/* 159:    */     throws Exception
/* 160:    */   {
/* 161:263 */     String sql = this.sqlAllTables;
/* 162:264 */     if (StringUtil.isNotEmpty(tableName)) {
/* 163:265 */       sql = sql + " AND  LOWER(t.name) LIKE '%" + tableName.toLowerCase() + "%'";
/* 164:    */     }
/* 165:267 */     RowMapper<TableModel> rowMapper = new RowMapper()
/* 166:    */     {
/* 167:    */       public TableModel mapRow(ResultSet rs, int row)
/* 168:    */         throws SQLException
/* 169:    */       {
/* 170:271 */         TableModel tableModel = new TableModel();
/* 171:272 */         tableModel.setName(rs.getString("NAME"));
/* 172:273 */         tableModel.setComment(rs.getString("COMMENT"));
/* 173:274 */         return tableModel;
/* 174:    */       }
/* 175:276 */     };
/* 176:277 */     List<TableModel> tableModels = getForList(sql, pageBean, rowMapper, "oracle");
/* 177:    */     
/* 178:279 */     List<String> tableNames = new ArrayList();
/* 179:281 */     for (TableModel model : tableModels) {
/* 180:282 */       tableNames.add(model.getName());
/* 181:    */     }
/* 182:285 */     Map<String, List<ColumnModel>> tableColumnsMap = getColumnsByTableName(tableNames);
/* 183:287 */     for (Iterator i$ = tableColumnsMap.entrySet().iterator(); i$.hasNext();)
/* 184:    */     {
/* 185:287 */       entry = (Map.Entry)i$.next();
/* 186:289 */       for (TableModel model : tableModels) {
/* 187:290 */         if (model.getName().equalsIgnoreCase((String)entry.getKey())) {
/* 188:291 */           model.setColumnList((List)entry.getValue());
/* 189:    */         }
/* 190:    */       }
/* 191:    */     }
/* 192:    */     Map.Entry<String, List<ColumnModel>> entry;
/* 193:296 */     return tableModels;
/* 194:    */   }
/* 195:    */   
/* 196:    */   private List<ColumnModel> getColumnsByTableName(String tableName)
/* 197:    */   {
/* 198:307 */     String sql = String.format(this.SQL_GET_COLUMNS, new Object[] { tableName });
/* 199:    */     
/* 200:    */ 
/* 201:310 */     Map map = new HashMap();
/* 202:311 */     List<ColumnModel> list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, map, new SqlServerColumnMap());
/* 203:312 */     for (ColumnModel model : list) {
/* 204:313 */       model.setTableName(tableName);
/* 205:    */     }
/* 206:315 */     return list;
/* 207:    */   }
/* 208:    */   
/* 209:    */   private Map<String, List<ColumnModel>> getColumnsByTableName(List<String> tableNames)
/* 210:    */   {
/* 211:325 */     String sql = this.SQL_GET_COLUMNS_BATCH;
/* 212:326 */     Map<String, List<ColumnModel>> map = new HashMap();
/* 213:327 */     if ((tableNames != null) && (tableNames.size() == 0)) {
/* 214:328 */       return map;
/* 215:    */     }
/* 216:330 */     StringBuffer buf = new StringBuffer();
/* 217:331 */     for (String str : tableNames) {
/* 218:332 */       buf.append("'" + str + "',");
/* 219:    */     }
/* 220:334 */     buf.deleteCharAt(buf.length() - 1);
/* 221:335 */     sql = sql + " AND B.NAME IN (" + buf.toString() + ") ";
/* 222:    */     
/* 223:    */ 
/* 224:    */ 
/* 225:339 */     List<ColumnModel> columnModels = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, new HashMap(), new SqlServerColumnMap());
/* 226:341 */     for (ColumnModel columnModel : columnModels)
/* 227:    */     {
/* 228:342 */       String tableName = columnModel.getTableName();
/* 229:343 */       if (map.containsKey(tableName))
/* 230:    */       {
/* 231:344 */         ((List)map.get(tableName)).add(columnModel);
/* 232:    */       }
/* 233:    */       else
/* 234:    */       {
/* 235:346 */         List<ColumnModel> cols = new ArrayList();
/* 236:347 */         cols.add(columnModel);
/* 237:348 */         map.put(tableName, cols);
/* 238:    */       }
/* 239:    */     }
/* 240:351 */     return map;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public String getAllTableSql()
/* 244:    */   {
/* 245:356 */     return this.sqlAllTables;
/* 246:    */   }
/* 247:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.impl.SqlServerTableMeta
 * JD-Core Version:    0.7.0.1
 */