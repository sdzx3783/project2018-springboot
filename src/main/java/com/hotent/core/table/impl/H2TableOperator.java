/*   1:    */ package com.hotent.core.table.impl;
/*   2:    */ 
/*   3:    */ import com.hotent.core.model.TableIndex;
/*   4:    */ import com.hotent.core.mybatis.Dialect;
/*   5:    */ import com.hotent.core.page.PageBean;
/*   6:    */ import com.hotent.core.table.AbstractTableOperator;
/*   7:    */ import com.hotent.core.table.ColumnModel;
/*   8:    */ import com.hotent.core.table.TableModel;
/*   9:    */ import com.hotent.core.util.BeanUtils;
/*  10:    */ import com.hotent.core.util.StringUtil;
/*  11:    */ import java.sql.ResultSet;
/*  12:    */ import java.sql.SQLException;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.List;
/*  15:    */ import org.slf4j.Logger;
/*  16:    */ import org.springframework.jdbc.core.JdbcTemplate;
/*  17:    */ import org.springframework.jdbc.core.RowMapper;
/*  18:    */ 
/*  19:    */ public class H2TableOperator
/*  20:    */   extends AbstractTableOperator
/*  21:    */ {
/*  22: 21 */   protected int BATCHSIZE = 100;
/*  23: 24 */   private final String SQL_GET_ALL_INDEX = "SELECT A.TABLE_NAME  , A.INDEX_NAME  , A.NON_UNIQUE  , A.COLUMN_NAME  , A.INDEX_TYPE_NAME  , A.REMARKS , A.SQL FROM INFORMATION_SCHEMA.INDEXES  A WHERE 1=1 ";
/*  24:    */   
/*  25:    */   public void createTable(TableModel model)
/*  26:    */     throws SQLException
/*  27:    */   {
/*  28: 41 */     List<ColumnModel> columnList = model.getColumnList();
/*  29:    */     
/*  30: 43 */     StringBuffer sb = new StringBuffer();
/*  31:    */     
/*  32: 45 */     String pkColumn = null;
/*  33:    */     
/*  34: 47 */     List<String> columnCommentList = new ArrayList();
/*  35:    */     
/*  36: 49 */     sb.append("CREATE TABLE " + model.getName() + " (\n");
/*  37: 50 */     for (int i = 0; i < columnList.size(); i++)
/*  38:    */     {
/*  39: 52 */       ColumnModel cm = (ColumnModel)columnList.get(i);
/*  40: 53 */       sb.append("    ").append(cm.getName()).append("    ");
/*  41: 54 */       sb.append(getColumnType(cm.getColumnType(), cm.getCharLen(), cm.getIntLen(), cm.getDecimalLen()));
/*  42: 55 */       sb.append(" ");
/*  43: 57 */       if (cm.getIsPk()) {
/*  44: 58 */         if (pkColumn == null) {
/*  45: 59 */           pkColumn = cm.getName();
/*  46:    */         } else {
/*  47: 61 */           pkColumn = pkColumn + "," + cm.getName();
/*  48:    */         }
/*  49:    */       }
/*  50: 65 */       String defVal = getDefaultValueSQL(cm);
/*  51: 66 */       if (StringUtil.isNotEmpty(defVal)) {
/*  52: 67 */         sb.append(defVal);
/*  53:    */       }
/*  54: 71 */       if ((!cm.getIsNull()) || (cm.getIsPk())) {
/*  55: 72 */         sb.append(" NOT NULL ");
/*  56:    */       }
/*  57: 76 */       if ((cm.getComment() != null) && (cm.getComment().length() > 0)) {
/*  58: 78 */         columnCommentList.add("COMMENT ON COLUMN " + model.getName() + "." + cm.getName() + " IS '" + cm.getComment() + "'\n");
/*  59:    */       }
/*  60: 80 */       sb.append(",\n");
/*  61:    */     }
/*  62: 83 */     if (pkColumn != null) {
/*  63: 84 */       sb.append("    CONSTRAINT PK_").append(model.getName()).append(" PRIMARY KEY (").append(pkColumn).append(")");
/*  64:    */     } else {
/*  65: 86 */       sb = new StringBuffer(sb.substring(0, sb.length() - ",\n".length()));
/*  66:    */     }
/*  67: 90 */     sb.append("\n)");
/*  68:    */     
/*  69: 92 */     this.jdbcTemplate.execute(sb.toString());
/*  70: 93 */     if ((model.getComment() != null) && (model.getComment().length() > 0)) {
/*  71: 94 */       this.jdbcTemplate.execute("COMMENT ON TABLE " + model.getName() + " IS '" + model.getComment() + "'\n");
/*  72:    */     }
/*  73: 96 */     for (String columnComment : columnCommentList) {
/*  74: 97 */       this.jdbcTemplate.execute(columnComment);
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void updateTableComment(String tableName, String comment)
/*  79:    */     throws SQLException
/*  80:    */   {
/*  81:103 */     StringBuffer sb = new StringBuffer();
/*  82:104 */     sb.append("COMMENT ON TABLE ");
/*  83:105 */     sb.append(tableName);
/*  84:106 */     sb.append(" IS '");
/*  85:107 */     sb.append(comment);
/*  86:108 */     sb.append("'\n");
/*  87:109 */     this.jdbcTemplate.execute(sb.toString());
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void addColumn(String tableName, ColumnModel model)
/*  91:    */     throws SQLException
/*  92:    */   {
/*  93:114 */     StringBuffer sb = new StringBuffer();
/*  94:115 */     sb.append("ALTER TABLE ").append(tableName);
/*  95:116 */     sb.append(" ADD ");
/*  96:117 */     sb.append(model.getName()).append(" ");
/*  97:118 */     sb.append(getColumnType(model.getColumnType(), model.getCharLen(), model.getIntLen(), model.getDecimalLen()));
/*  98:    */     
/*  99:    */ 
/* 100:    */ 
/* 101:122 */     String defVal = getDefaultValueSQL(model);
/* 102:123 */     if (StringUtil.isNotEmpty(defVal)) {
/* 103:124 */       sb.append(defVal);
/* 104:    */     }
/* 105:126 */     sb.append("\n");
/* 106:127 */     this.jdbcTemplate.execute(sb.toString());
/* 107:128 */     if ((model.getComment() != null) && (model.getComment().length() > 0)) {
/* 108:129 */       this.jdbcTemplate.execute("COMMENT ON COLUMN " + tableName + "." + model.getName() + " IS '" + model.getComment() + "'");
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void updateColumn(String tableName, String columnName, ColumnModel model)
/* 113:    */     throws SQLException
/* 114:    */   {
/* 115:136 */     if (!columnName.equals(model.getName()))
/* 116:    */     {
/* 117:137 */       StringBuffer modifyName = new StringBuffer("ALTER TABLE ").append(tableName);
/* 118:138 */       modifyName.append(" ALTER COLUMN ").append(columnName).append(" RENAME TO ").append(model.getName());
/* 119:139 */       this.jdbcTemplate.execute(modifyName.toString());
/* 120:    */     }
/* 121:141 */     StringBuffer sb = new StringBuffer();
/* 122:142 */     sb.append("ALTER TABLE ").append(tableName);
/* 123:143 */     sb.append(" ALTER COLUMN ").append(model.getName());
/* 124:144 */     sb.append(getColumnType(model.getColumnType(), model.getCharLen(), model.getIntLen(), model.getDecimalLen()));
/* 125:145 */     if (!model.getIsNull()) {
/* 126:146 */       sb.append(" NOT NULL ");
/* 127:    */     }
/* 128:148 */     this.jdbcTemplate.execute(sb.toString());
/* 129:150 */     if ((model.getComment() != null) && (model.getComment().length() > 0)) {
/* 130:151 */       this.jdbcTemplate.execute("COMMENT ON COLUMN " + tableName + "." + model.getName() + " IS'" + model.getComment() + "'");
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void dropTable(String tableName)
/* 135:    */   {
/* 136:157 */     String selSql = "SELECT COUNT(*) AMOUNT FROM INFORMATION_SCHEMA.TABLES  WHERE TABLE_SCHEMA = SCHEMA() AND TABLE_NAME = UPPER('" + tableName + "')";
/* 137:    */     
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:165 */     int rtn = this.jdbcTemplate.queryForInt(selSql);
/* 145:166 */     if (rtn > 0)
/* 146:    */     {
/* 147:167 */       String sql = "DROP TABLE " + tableName;
/* 148:168 */       this.jdbcTemplate.execute(sql);
/* 149:    */     }
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void addForeignKey(String pkTableName, String fkTableName, String pkField, String fkField)
/* 153:    */   {
/* 154:174 */     String sql = " ALTER TABLE " + fkTableName + " ADD CONSTRAINT FK_" + fkTableName + " FOREIGN KEY (" + fkField + ") REFERENCES " + pkTableName + " (" + pkField + ") ON DELETE CASCADE";
/* 155:175 */     this.jdbcTemplate.execute(sql);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void dropForeignKey(String tableName, String keyName)
/* 159:    */   {
/* 160:180 */     String sql = "ALTER   TABLE   " + tableName + "   DROP   CONSTRAINT  " + keyName;
/* 161:181 */     this.jdbcTemplate.execute(sql);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void dropIndex(String tableName, String indexName)
/* 165:    */   {
/* 166:187 */     String sql = "DROP INDEX " + indexName;
/* 167:188 */     this.jdbcTemplate.execute(sql);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public TableIndex getIndex(String tableName, String indexName)
/* 171:    */   {
/* 172:193 */     String sql = "SELECT A.TABLE_NAME  , A.INDEX_NAME  , A.NON_UNIQUE  , A.COLUMN_NAME  , A.INDEX_TYPE_NAME  , A.REMARKS , A.SQL FROM INFORMATION_SCHEMA.INDEXES  A WHERE 1=1 ";
/* 173:    */     
/* 174:195 */     sql = sql + " AND A.INDEX_NAME = '" + indexName + "' ";
/* 175:196 */     List<TableIndex> indexes = this.jdbcTemplate.query(sql, this.indexRowMapper);
/* 176:197 */     List<TableIndex> indexList = mergeIndex(indexes);
/* 177:198 */     if (BeanUtils.isEmpty(indexList)) {
/* 178:199 */       return null;
/* 179:    */     }
/* 180:201 */     TableIndex index = (TableIndex)indexList.get(0);
/* 181:202 */     return index;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public List<TableIndex> getIndexesByTable(String tableName)
/* 185:    */   {
/* 186:208 */     String sql = "SELECT A.TABLE_NAME  , A.INDEX_NAME  , A.NON_UNIQUE  , A.COLUMN_NAME  , A.INDEX_TYPE_NAME  , A.REMARKS , A.SQL FROM INFORMATION_SCHEMA.INDEXES  A WHERE 1=1 ";
/* 187:209 */     sql = sql + " AND UPPER(A.TABLE_NAME) = UPPER('" + tableName + "')";
/* 188:210 */     List<TableIndex> indexes = this.jdbcTemplate.query(sql, this.indexRowMapper);
/* 189:    */     
/* 190:212 */     List<TableIndex> indexList = mergeIndex(indexes);
/* 191:213 */     for (TableIndex index : indexList) {
/* 192:214 */       index.setIndexDdl(generateIndexDDL(index));
/* 193:    */     }
/* 194:216 */     return indexList;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public List<TableIndex> getIndexesByFuzzyMatching(String tableName, String indexName, Boolean getDDL)
/* 198:    */   {
/* 199:221 */     return getIndexesByFuzzyMatching(tableName, indexName, getDDL, null);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public List<TableIndex> getIndexesByFuzzyMatching(String tableName, String indexName, Boolean getDDL, PageBean pageBean)
/* 203:    */   {
/* 204:226 */     String sql = "SELECT A.TABLE_NAME  , A.INDEX_NAME  , A.NON_UNIQUE  , A.COLUMN_NAME  , A.INDEX_TYPE_NAME  , A.REMARKS , A.SQL FROM INFORMATION_SCHEMA.INDEXES  A WHERE 1=1 ";
/* 205:228 */     if (!StringUtil.isEmpty(tableName)) {
/* 206:229 */       sql = sql + " AND UPPER(A.TABLE_NAME) LIKE UPPER('%" + tableName + "%')";
/* 207:    */     }
/* 208:232 */     if (!StringUtil.isEmpty(indexName)) {
/* 209:233 */       sql = sql + " AND UPPER(A.INDEX_NAME) like UPPER('%" + indexName + "%')";
/* 210:    */     }
/* 211:236 */     if (pageBean != null)
/* 212:    */     {
/* 213:237 */       int currentPage = pageBean.getCurrentPage();
/* 214:238 */       int pageSize = pageBean.getPageSize();
/* 215:239 */       int offset = (currentPage - 1) * pageSize;
/* 216:240 */       String totalSql = this.dialect.getCountSql(sql);
/* 217:241 */       int total = this.jdbcTemplate.queryForInt(totalSql);
/* 218:242 */       sql = this.dialect.getLimitString(sql, offset, pageSize);
/* 219:243 */       pageBean.setTotalCount(total);
/* 220:    */     }
/* 221:245 */     this.logger.debug(sql);
/* 222:246 */     List<TableIndex> indexes = this.jdbcTemplate.query(sql, this.indexRowMapper);
/* 223:    */     
/* 224:248 */     List<TableIndex> indexList = mergeIndex(indexes);
/* 225:250 */     for (TableIndex index : indexList) {
/* 226:251 */       index.setIndexDdl(generateIndexDDL(index));
/* 227:    */     }
/* 228:253 */     return indexList;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void rebuildIndex(String tableName, String indexName)
/* 232:    */   {
/* 233:264 */     throw new UnsupportedOperationException("h2 不支持通过JDBC进行索引重建！");
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void createIndex(TableIndex index)
/* 237:    */     throws SQLException
/* 238:    */   {
/* 239:269 */     String sql = generateIndexDDL(index);
/* 240:270 */     this.jdbcTemplate.execute(sql);
/* 241:    */   }
/* 242:    */   
/* 243:    */   private List<TableIndex> mergeIndex(List<TableIndex> indexes)
/* 244:    */   {
/* 245:274 */     List<TableIndex> indexList = new ArrayList();
/* 246:275 */     for (TableIndex index : indexes)
/* 247:    */     {
/* 248:276 */       boolean found = false;
/* 249:277 */       for (TableIndex index1 : indexList) {
/* 250:278 */         if ((index.getIndexName().equals(index1.getIndexName())) && (index.getIndexTable().equals(index1.getIndexTable())))
/* 251:    */         {
/* 252:280 */           index1.getIndexFields().add(index.getIndexFields().get(0));
/* 253:281 */           found = true;
/* 254:282 */           break;
/* 255:    */         }
/* 256:    */       }
/* 257:285 */       if (!found) {
/* 258:286 */         indexList.add(index);
/* 259:    */       }
/* 260:    */     }
/* 261:289 */     return indexList;
/* 262:    */   }
/* 263:    */   
/* 264:    */   private String generateIndexDDL(TableIndex index)
/* 265:    */   {
/* 266:298 */     StringBuffer sql = new StringBuffer();
/* 267:299 */     sql.append("CREATE ");
/* 268:300 */     sql.append("INDEX ");
/* 269:301 */     sql.append(index.getIndexName());
/* 270:302 */     sql.append(" ON ");
/* 271:303 */     sql.append(index.getIndexTable());
/* 272:304 */     sql.append("(");
/* 273:305 */     for (String field : index.getIndexFields())
/* 274:    */     {
/* 275:306 */       sql.append(field);
/* 276:307 */       sql.append(",");
/* 277:    */     }
/* 278:309 */     sql.deleteCharAt(sql.length() - 1);
/* 279:310 */     sql.append(")");
/* 280:311 */     return sql.toString();
/* 281:    */   }
/* 282:    */   
/* 283:    */   private String getColumnType(String columnType, int charLen, int intLen, int decimalLen)
/* 284:    */   {
/* 285:328 */     if ("varchar".equals(columnType)) {
/* 286:329 */       return "VARCHAR(" + charLen + ')';
/* 287:    */     }
/* 288:330 */     if ("number".equals(columnType)) {
/* 289:331 */       return "DECIMAL(" + (intLen + decimalLen) + "," + decimalLen + ")";
/* 290:    */     }
/* 291:332 */     if ("date".equals(columnType)) {
/* 292:333 */       return "DATE";
/* 293:    */     }
/* 294:334 */     if ("int".equals(columnType))
/* 295:    */     {
/* 296:335 */       if ((intLen > 0) && (intLen <= 5)) {
/* 297:336 */         return "SMALLINT";
/* 298:    */       }
/* 299:337 */       if ((intLen > 5) && (intLen <= 10)) {
/* 300:338 */         return "INTEGER";
/* 301:    */       }
/* 302:340 */       return "BIGINT";
/* 303:    */     }
/* 304:342 */     if ("clob".equals(columnType)) {
/* 305:343 */       return "CLOB";
/* 306:    */     }
/* 307:345 */     return "VARCHAR(50)";
/* 308:    */   }
/* 309:    */   
/* 310:    */   private String getDefaultValueSQL(ColumnModel cm)
/* 311:    */   {
/* 312:350 */     String sql = null;
/* 313:351 */     if (StringUtil.isNotEmpty(cm.getDefaultValue())) {
/* 314:352 */       if ("int".equalsIgnoreCase(cm.getColumnType())) {
/* 315:353 */         sql = " DEFAULT " + cm.getDefaultValue() + " ";
/* 316:354 */       } else if ("number".equalsIgnoreCase(cm.getColumnType())) {
/* 317:355 */         sql = " DEFAULT " + cm.getDefaultValue() + " ";
/* 318:356 */       } else if ("varchar".equalsIgnoreCase(cm.getColumnType())) {
/* 319:357 */         sql = " DEFAULT '" + cm.getDefaultValue() + "' ";
/* 320:358 */       } else if ("clob".equalsIgnoreCase(cm.getColumnType())) {
/* 321:359 */         sql = " DEFAULT '" + cm.getDefaultValue() + "' ";
/* 322:360 */       } else if ("date".equalsIgnoreCase(cm.getColumnType())) {
/* 323:361 */         sql = " DEFAULT " + cm.getDefaultValue() + " ";
/* 324:    */       } else {
/* 325:363 */         sql = " DEFAULT " + cm.getDefaultValue() + " ";
/* 326:    */       }
/* 327:    */     }
/* 328:366 */     return sql;
/* 329:    */   }
/* 330:    */   
/* 331:369 */   private RowMapper<TableIndex> indexRowMapper = new RowMapper()
/* 332:    */   {
/* 333:    */     public TableIndex mapRow(ResultSet rs, int rowNum)
/* 334:    */       throws SQLException
/* 335:    */     {
/* 336:372 */       TableIndex index = new TableIndex();
/* 337:373 */       index.setIndexTable(rs.getString("TABLE_NAME"));
/* 338:374 */       index.setTableType(TableIndex.TABLE_TYPE_TABLE);
/* 339:375 */       index.setIndexName(rs.getString("INDEX_NAME"));
/* 340:    */       
/* 341:377 */       String non_unique = rs.getString("NON_UNIQUE").trim();
/* 342:378 */       String index_type_name = rs.getString("INDEX_TYPE_NAME").trim();
/* 343:379 */       if ("TRUE".equalsIgnoreCase(non_unique)) {
/* 344:380 */         index.setUnique(true);
/* 345:    */       }
/* 346:383 */       if ("PRIMARY KEY".equalsIgnoreCase(index_type_name)) {
/* 347:384 */         index.setPkIndex(true);
/* 348:    */       }
/* 349:387 */       index.setIndexType(TableIndex.INDEX_TYPE_BTREE);
/* 350:    */       
/* 351:389 */       index.setIndexComment(rs.getString("REMARKS"));
/* 352:390 */       List<String> indexFields = new ArrayList();
/* 353:391 */       indexFields.add(rs.getString("COLUMN_NAME"));
/* 354:392 */       index.setIndexFields(indexFields);
/* 355:    */       
/* 356:394 */       index.setIndexDdl(rs.getString("SQL"));
/* 357:395 */       return index;
/* 358:    */     }
/* 359:    */   };
/* 360:    */   
/* 361:    */   public boolean isTableExist(String tableName)
/* 362:    */   {
/* 363:405 */     String selSql = "SELECT COUNT(*) AMOUNT FROM INFORMATION_SCHEMA.TABLES  WHERE TABLE_SCHEMA = SCHEMA() AND TABLE_NAME = UPPER('" + tableName + "')";
/* 364:    */     
/* 365:    */ 
/* 366:    */ 
/* 367:    */ 
/* 368:    */ 
/* 369:    */ 
/* 370:    */ 
/* 371:413 */     int rtn = this.jdbcTemplate.queryForInt(selSql);
/* 372:414 */     return rtn > 0;
/* 373:    */   }
/* 374:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.impl.H2TableOperator
 * JD-Core Version:    0.7.0.1
 */