/*   1:    */ package com.hotent.core.table.impl;
/*   2:    */ 
/*   3:    */ import com.hotent.core.db.datasource.JdbcTemplateUtil;
/*   4:    */ import com.hotent.core.page.PageBean;
/*   5:    */ import com.hotent.core.table.BaseTableMeta;
/*   6:    */ import com.hotent.core.table.ColumnModel;
/*   7:    */ import com.hotent.core.table.TableModel;
/*   8:    */ import com.hotent.core.table.colmap.H2ColumnMap;
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
/*  28:    */ public class H2TableMeta
/*  29:    */   extends BaseTableMeta
/*  30:    */ {
/*  31:    */   @Resource
/*  32:    */   private JdbcTemplate jdbcTemplate;
/*  33: 39 */   private final String SQL_GET_COLUMNS = "SELECT A.TABLE_NAME, A.COLUMN_NAME, A.IS_NULLABLE, A.TYPE_NAME, A.CHARACTER_OCTET_LENGTH LENGTH, A.NUMERIC_PRECISION PRECISIONS, A.NUMERIC_SCALE SCALE, B.COLUMN_LIST, A.REMARKS FROM INFORMATION_SCHEMA.COLUMNS A  JOIN INFORMATION_SCHEMA.CONSTRAINTS B ON A.TABLE_NAME=B.TABLE_NAME WHERE  A.TABLE_SCHEMA=SCHEMA() AND B.CONSTRAINT_TYPE='PRIMARY KEY' AND UPPER(A.TABLE_NAME)=UPPER('%s') ";
/*  34: 58 */   private final String SQL_GET_COLUMNS_BATCH = "SELECT A.TABLE_NAME, A.COLUMN_NAME, A.IS_NULLABLE, A.TYPE_NAME, A.CHARACTER_OCTET_LENGTH LENGTH, A.NUMERIC_PRECISION PRECISIONS, A.NUMERIC_SCALE SCALE, B.COLUMN_LIST, A.REMARKS FROM INFORMATION_SCHEMA.COLUMNS A  JOIN INFORMATION_SCHEMA.CONSTRAINTS B ON A.TABLE_NAME=B.TABLE_NAME WHERE  A.TABLE_SCHEMA=SCHEMA() AND B.CONSTRAINT_TYPE='PRIMARY KEY' ";
/*  35: 78 */   private final String SQL_GET_ALL_TABLE = "SELECT TABLE_NAME, REMARKS FROM INFORMATION_SCHEMA.TABLES T WHERE T.TABLE_TYPE='TABLE' AND T.TABLE_SCHEMA=SCHEMA() ";
/*  36:    */   
/*  37:    */   public TableModel getTableByName(String tableName)
/*  38:    */   {
/*  39: 95 */     TableModel model = getTableModel(tableName);
/*  40:    */     
/*  41: 97 */     List<ColumnModel> columnList = getColumnsByTableName(tableName);
/*  42: 98 */     model.setColumnList(columnList);
/*  43: 99 */     return model;
/*  44:    */   }
/*  45:    */   
/*  46:    */   private List<ColumnModel> getColumnsByTableName(String tableName)
/*  47:    */   {
/*  48:110 */     String sql = String.format("SELECT A.TABLE_NAME, A.COLUMN_NAME, A.IS_NULLABLE, A.TYPE_NAME, A.CHARACTER_OCTET_LENGTH LENGTH, A.NUMERIC_PRECISION PRECISIONS, A.NUMERIC_SCALE SCALE, B.COLUMN_LIST, A.REMARKS FROM INFORMATION_SCHEMA.COLUMNS A  JOIN INFORMATION_SCHEMA.CONSTRAINTS B ON A.TABLE_NAME=B.TABLE_NAME WHERE  A.TABLE_SCHEMA=SCHEMA() AND B.CONSTRAINT_TYPE='PRIMARY KEY' AND UPPER(A.TABLE_NAME)=UPPER('%s') ", new Object[] { tableName.toUpperCase() });
/*  49:    */     
/*  50:    */ 
/*  51:113 */     Map<String, Object> map = new HashMap();
/*  52:    */     
/*  53:115 */     List<ColumnModel> list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, map, new H2ColumnMap());
/*  54:116 */     for (ColumnModel model : list) {
/*  55:117 */       model.setTableName(tableName);
/*  56:    */     }
/*  57:119 */     return list;
/*  58:    */   }
/*  59:    */   
/*  60:    */   private Map<String, List<ColumnModel>> getColumnsByTableName(List<String> tableNames)
/*  61:    */   {
/*  62:128 */     String sql = "SELECT A.TABLE_NAME, A.COLUMN_NAME, A.IS_NULLABLE, A.TYPE_NAME, A.CHARACTER_OCTET_LENGTH LENGTH, A.NUMERIC_PRECISION PRECISIONS, A.NUMERIC_SCALE SCALE, B.COLUMN_LIST, A.REMARKS FROM INFORMATION_SCHEMA.COLUMNS A  JOIN INFORMATION_SCHEMA.CONSTRAINTS B ON A.TABLE_NAME=B.TABLE_NAME WHERE  A.TABLE_SCHEMA=SCHEMA() AND B.CONSTRAINT_TYPE='PRIMARY KEY' ";
/*  63:129 */     Map<String, List<ColumnModel>> map = new HashMap();
/*  64:130 */     if ((tableNames != null) && (tableNames.size() == 0)) {
/*  65:131 */       return map;
/*  66:    */     }
/*  67:133 */     StringBuffer buf = new StringBuffer();
/*  68:134 */     for (String str : tableNames) {
/*  69:135 */       buf.append("'" + str + "',");
/*  70:    */     }
/*  71:137 */     buf.deleteCharAt(buf.length() - 1);
/*  72:138 */     sql = sql + " AND A.TABLE_NAME IN (" + buf.toString().toUpperCase() + ") ";
/*  73:    */     
/*  74:    */ 
/*  75:    */ 
/*  76:142 */     List<ColumnModel> columnModels = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, new HashMap(), new H2ColumnMap());
/*  77:143 */     for (ColumnModel columnModel : columnModels)
/*  78:    */     {
/*  79:144 */       String tableName = columnModel.getTableName();
/*  80:145 */       if (map.containsKey(tableName))
/*  81:    */       {
/*  82:146 */         ((List)map.get(tableName)).add(columnModel);
/*  83:    */       }
/*  84:    */       else
/*  85:    */       {
/*  86:148 */         List<ColumnModel> cols = new ArrayList();
/*  87:149 */         cols.add(columnModel);
/*  88:150 */         map.put(tableName, cols);
/*  89:    */       }
/*  90:    */     }
/*  91:153 */     return map;
/*  92:    */   }
/*  93:    */   
/*  94:    */   private TableModel getTableModel(String tableName)
/*  95:    */   {
/*  96:165 */     String sql = "SELECT TABLE_NAME, REMARKS FROM INFORMATION_SCHEMA.TABLES T WHERE T.TABLE_TYPE='TABLE' AND T.TABLE_SCHEMA=SCHEMA()  AND UPPER(TABLE_NAME) = '" + tableName.toUpperCase() + "'";
/*  97:166 */     TableModel tableModel = (TableModel)this.jdbcTemplate.queryForObject(sql, null, this.tableRowMapper);
/*  98:167 */     if (BeanUtils.isEmpty(tableModel)) {
/*  99:168 */       tableModel = new TableModel();
/* 100:    */     }
/* 101:169 */     return tableModel;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Map<String, String> getTablesByName(String tableName)
/* 105:    */   {
/* 106:175 */     String sql = "SELECT TABLE_NAME, REMARKS FROM INFORMATION_SCHEMA.TABLES T WHERE T.TABLE_TYPE='TABLE' AND T.TABLE_SCHEMA=SCHEMA() ";
/* 107:176 */     if (StringUtil.isNotEmpty(tableName)) {
/* 108:177 */       sql = sql + " AND UPPER(TABLE_NAME) LIKE '%" + tableName.toUpperCase() + "%'";
/* 109:    */     }
/* 110:180 */     Map<String, Object> parameter = new HashMap();
/* 111:181 */     List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql, new Object[] { parameter, this.tableMapRowMapper });
/* 112:182 */     Map<String, String> map = new LinkedHashMap();
/* 113:183 */     for (int i = 0; i < list.size(); i++)
/* 114:    */     {
/* 115:184 */       Map<String, Object> tmp = (Map)list.get(i);
/* 116:185 */       String name = tmp.get("name").toString();
/* 117:186 */       String comments = tmp.get("comment").toString();
/* 118:187 */       map.put(name, comments);
/* 119:    */     }
/* 120:189 */     return map;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Map<String, String> getTablesByName(List<String> names)
/* 124:    */   {
/* 125:197 */     StringBuffer sb = new StringBuffer();
/* 126:198 */     for (String name : names)
/* 127:    */     {
/* 128:199 */       sb.append("'");
/* 129:200 */       sb.append(name);
/* 130:201 */       sb.append("',");
/* 131:    */     }
/* 132:203 */     sb.deleteCharAt(sb.length() - 1);
/* 133:204 */     String sql = "SELECT TABLE_NAME, REMARKS FROM INFORMATION_SCHEMA.TABLES T WHERE T.TABLE_TYPE='TABLE' AND T.TABLE_SCHEMA=SCHEMA()  AND  UPPER(TABLE_NAME) IN (" + sb.toString().toUpperCase() + ")";
/* 134:    */     
/* 135:    */ 
/* 136:    */ 
/* 137:208 */     Map<String, Object> parameter = new HashMap();
/* 138:209 */     List<Map<String, Object>> list = JdbcTemplateUtil.getNamedParameterJdbcTemplate(this.jdbcTemplate).query(sql, parameter, this.tableMapRowMapper);
/* 139:210 */     Map<String, String> map = new LinkedHashMap();
/* 140:211 */     for (int i = 0; i < list.size(); i++)
/* 141:    */     {
/* 142:212 */       Map<String, Object> tmp = (Map)list.get(i);
/* 143:213 */       String name = tmp.get("name").toString();
/* 144:214 */       String comments = tmp.get("comment").toString();
/* 145:215 */       map.put(name, comments);
/* 146:    */     }
/* 147:217 */     return map;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public List<TableModel> getTablesByName(String tableName, PageBean pageBean)
/* 151:    */     throws Exception
/* 152:    */   {
/* 153:224 */     String sql = "SELECT TABLE_NAME, REMARKS FROM INFORMATION_SCHEMA.TABLES T WHERE T.TABLE_TYPE='TABLE' AND T.TABLE_SCHEMA=SCHEMA() ";
/* 154:225 */     if (StringUtil.isNotEmpty(tableName)) {
/* 155:226 */       sql = sql + " AND UPPER(TABLE_NAME) LIKE '%" + tableName.toUpperCase() + "%'";
/* 156:    */     }
/* 157:228 */     RowMapper<TableModel> rowMapper = new RowMapper()
/* 158:    */     {
/* 159:    */       public TableModel mapRow(ResultSet rs, int row)
/* 160:    */         throws SQLException
/* 161:    */       {
/* 162:232 */         TableModel tableModel = new TableModel();
/* 163:233 */         tableModel.setName(rs.getString("TABLE_NAME"));
/* 164:234 */         String comments = rs.getString("REMARKS");
/* 165:235 */         tableModel.setComment(comments);
/* 166:236 */         return tableModel;
/* 167:    */       }
/* 168:238 */     };
/* 169:239 */     List<TableModel> tableModels = getForList(sql, pageBean, rowMapper, "h2");
/* 170:    */     
/* 171:241 */     List<String> tableNames = new ArrayList();
/* 172:243 */     for (TableModel model : tableModels) {
/* 173:244 */       tableNames.add(model.getName());
/* 174:    */     }
/* 175:247 */     Map<String, List<ColumnModel>> tableColumnsMap = getColumnsByTableName(tableNames);
/* 176:249 */     for (Iterator i$ = tableColumnsMap.entrySet().iterator(); i$.hasNext();)
/* 177:    */     {
/* 178:249 */       entry = (Map.Entry)i$.next();
/* 179:251 */       for (TableModel model : tableModels) {
/* 180:252 */         if (model.getName().equalsIgnoreCase((String)entry.getKey())) {
/* 181:253 */           model.setColumnList((List)entry.getValue());
/* 182:    */         }
/* 183:    */       }
/* 184:    */     }
/* 185:    */     Map.Entry<String, List<ColumnModel>> entry;
/* 186:257 */     return tableModels;
/* 187:    */   }
/* 188:    */   
/* 189:262 */   RowMapper<TableModel> tableRowMapper = new RowMapper()
/* 190:    */   {
/* 191:    */     public TableModel mapRow(ResultSet rs, int rowNum)
/* 192:    */       throws SQLException
/* 193:    */     {
/* 194:265 */       TableModel model = new TableModel();
/* 195:266 */       String tableName = rs.getString("TABLE_NAME");
/* 196:267 */       String tableComment = rs.getString("REMARKS");
/* 197:268 */       model.setName(tableName);
/* 198:269 */       model.setComment(tableComment);
/* 199:270 */       return model;
/* 200:    */     }
/* 201:    */   };
/* 202:275 */   RowMapper<Map<String, Object>> tableMapRowMapper = new RowMapper()
/* 203:    */   {
/* 204:    */     public Map<String, Object> mapRow(ResultSet rs, int rowNum)
/* 205:    */       throws SQLException
/* 206:    */     {
/* 207:278 */       Map<String, Object> model = new HashMap();
/* 208:279 */       String tableName = rs.getString("TABLE_NAME");
/* 209:280 */       String tableComment = rs.getString("REMARKS");
/* 210:281 */       model.put("name", tableName);
/* 211:282 */       model.put("comment", tableComment);
/* 212:283 */       return model;
/* 213:    */     }
/* 214:    */   };
/* 215:    */   
/* 216:    */   public String getAllTableSql()
/* 217:    */   {
/* 218:291 */     return "SELECT TABLE_NAME, REMARKS FROM INFORMATION_SCHEMA.TABLES T WHERE T.TABLE_TYPE='TABLE' AND T.TABLE_SCHEMA=SCHEMA() ";
/* 219:    */   }
/* 220:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.impl.H2TableMeta
 * JD-Core Version:    0.7.0.1
 */