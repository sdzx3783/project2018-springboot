/*   1:    */ package com.hotent.core.table.impl;
/*   2:    */ 
/*   3:    */ import com.hotent.core.model.TableIndex;
/*   4:    */ import com.hotent.core.mybatis.Dialect;
/*   5:    */ import com.hotent.core.page.PageBean;
/*   6:    */ import com.hotent.core.table.AbstractTableOperator;
/*   7:    */ import com.hotent.core.table.ColumnModel;
/*   8:    */ import com.hotent.core.table.TableModel;
/*   9:    */ import com.hotent.core.util.StringUtil;
/*  10:    */ import java.sql.ResultSet;
/*  11:    */ import java.sql.SQLException;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import org.slf4j.Logger;
/*  17:    */ import org.springframework.jdbc.core.JdbcTemplate;
/*  18:    */ import org.springframework.jdbc.core.RowMapper;
/*  19:    */ 
/*  20:    */ public class OracleTableOperator
/*  21:    */   extends AbstractTableOperator
/*  22:    */ {
/*  23: 29 */   protected int BATCHSIZE = 100;
/*  24:    */   
/*  25:    */   public void createTable(TableModel model)
/*  26:    */     throws SQLException
/*  27:    */   {
/*  28: 36 */     List<ColumnModel> columnList = model.getColumnList();
/*  29:    */     
/*  30:    */ 
/*  31: 39 */     StringBuffer sb = new StringBuffer();
/*  32:    */     
/*  33: 41 */     String pkColumn = null;
/*  34:    */     
/*  35:    */ 
/*  36:    */ 
/*  37: 45 */     List<String> columnCommentList = new ArrayList();
/*  38:    */     
/*  39: 47 */     sb.append("CREATE TABLE " + model.getName() + " (\n");
/*  40: 48 */     for (int i = 0; i < columnList.size(); i++)
/*  41:    */     {
/*  42: 50 */       ColumnModel cm = (ColumnModel)columnList.get(i);
/*  43: 51 */       sb.append("    ").append(cm.getName()).append("    ");
/*  44: 52 */       sb.append(getColumnType(cm.getColumnType(), cm.getCharLen(), cm.getIntLen(), cm.getDecimalLen()));
/*  45: 53 */       sb.append(" ");
/*  46: 56 */       if (cm.getIsPk()) {
/*  47: 57 */         if (pkColumn == null) {
/*  48: 58 */           pkColumn = cm.getName();
/*  49:    */         } else {
/*  50: 60 */           pkColumn = pkColumn + "," + cm.getName();
/*  51:    */         }
/*  52:    */       }
/*  53: 64 */       if (StringUtil.isNotEmpty(cm.getDefaultValue())) {
/*  54: 65 */         sb.append(" DEFAULT " + cm.getDefaultValue());
/*  55:    */       }
/*  56: 74 */       if ((cm.getComment() != null) && (cm.getComment().length() > 0)) {
/*  57: 76 */         columnCommentList.add("COMMENT ON COLUMN " + model.getName() + "." + cm.getName() + " IS '" + cm.getComment() + "'\n");
/*  58:    */       }
/*  59: 78 */       sb.append(",\n");
/*  60:    */     }
/*  61: 81 */     if (pkColumn != null) {
/*  62: 82 */       sb.append("    CONSTRAINT PK_").append(model.getName()).append(" PRIMARY KEY (").append(pkColumn).append(")");
/*  63:    */     }
/*  64: 87 */     sb.append("\n)");
/*  65:    */     
/*  66: 89 */     this.jdbcTemplate.execute(sb.toString());
/*  67: 90 */     if ((model.getComment() != null) && (model.getComment().length() > 0)) {
/*  68: 91 */       this.jdbcTemplate.execute("COMMENT ON TABLE " + model.getName() + " IS '" + model.getComment() + "'\n");
/*  69:    */     }
/*  70: 93 */     for (String columnComment : columnCommentList) {
/*  71: 94 */       this.jdbcTemplate.execute(columnComment);
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void updateTableComment(String tableName, String comment)
/*  76:    */     throws SQLException
/*  77:    */   {
/*  78:100 */     StringBuffer sb = new StringBuffer();
/*  79:101 */     sb.append("COMMENT ON TABLE ");
/*  80:102 */     sb.append(tableName);
/*  81:103 */     sb.append(" IS '");
/*  82:104 */     sb.append(comment);
/*  83:105 */     sb.append("'\n");
/*  84:106 */     this.jdbcTemplate.execute(sb.toString());
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void addColumn(String tableName, ColumnModel model)
/*  88:    */     throws SQLException
/*  89:    */   {
/*  90:113 */     StringBuffer sb = new StringBuffer();
/*  91:114 */     sb.append("ALTER TABLE ").append(tableName);
/*  92:115 */     sb.append(" ADD ");
/*  93:116 */     sb.append(model.getName()).append(" ");
/*  94:117 */     sb.append(getColumnType(model.getColumnType(), model.getCharLen(), model.getIntLen(), model.getDecimalLen()));
/*  95:120 */     if (StringUtil.isNotEmpty(model.getDefaultValue())) {
/*  96:121 */       sb.append(" DEFAULT " + model.getDefaultValue());
/*  97:    */     }
/*  98:127 */     sb.append("\n");
/*  99:128 */     this.jdbcTemplate.execute(sb.toString());
/* 100:129 */     if ((model.getComment() != null) && (model.getComment().length() > 0)) {
/* 101:130 */       this.jdbcTemplate.execute("COMMENT ON COLUMN " + tableName + "." + model.getName() + " IS '" + model.getComment() + "'");
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void updateColumn(String tableName, String columnName, ColumnModel model)
/* 106:    */     throws SQLException
/* 107:    */   {
/* 108:139 */     if (!columnName.equals(model.getName()))
/* 109:    */     {
/* 110:140 */       StringBuffer modifyName = new StringBuffer("ALTER TABLE ").append(tableName);
/* 111:141 */       modifyName.append(" RENAME COLUMN ").append(columnName).append(" TO ").append(model.getName());
/* 112:142 */       this.jdbcTemplate.execute(modifyName.toString());
/* 113:    */     }
/* 114:146 */     StringBuffer sb = new StringBuffer();
/* 115:    */     
/* 116:148 */     sb.append("ALTER TABLE ").append(tableName);
/* 117:149 */     sb.append(" MODIFY(" + model.getName()).append(" ");
/* 118:150 */     sb.append(getColumnType(model.getColumnType(), model.getCharLen(), model.getIntLen(), model.getDecimalLen()));
/* 119:152 */     if (!model.getIsNull()) {
/* 120:154 */       sb.append(" NOT NULL ");
/* 121:    */     }
/* 122:156 */     sb.append(")");
/* 123:    */     
/* 124:158 */     this.jdbcTemplate.execute(sb.toString());
/* 125:161 */     if ((model.getComment() != null) && (model.getComment().length() > 0)) {
/* 126:162 */       this.jdbcTemplate.execute("COMMENT ON COLUMN " + tableName + "." + model.getName() + " IS'" + model.getComment() + "'");
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   private String getColumnType(String columnType, int charLen, int intLen, int decimalLen)
/* 131:    */   {
/* 132:175 */     if ("varchar".equals(columnType)) {
/* 133:176 */       return "VARCHAR2(" + charLen + ')';
/* 134:    */     }
/* 135:177 */     if ("number".equals(columnType)) {
/* 136:178 */       return "NUMBER(" + (intLen + decimalLen) + "," + decimalLen + ")";
/* 137:    */     }
/* 138:179 */     if ("date".equals(columnType)) {
/* 139:180 */       return "DATE";
/* 140:    */     }
/* 141:181 */     if ("int".equals(columnType)) {
/* 142:182 */       return "NUMBER(" + intLen + ")";
/* 143:    */     }
/* 144:183 */     if ("clob".equals(columnType)) {
/* 145:184 */       return "CLOB";
/* 146:    */     }
/* 147:186 */     return "VARCHAR2(50)";
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void dropTable(String tableName)
/* 151:    */   {
/* 152:195 */     String selSql = "select count(*) amount from user_objects where object_name = upper('" + tableName + "')";
/* 153:196 */     int rtn = this.jdbcTemplate.queryForInt(selSql);
/* 154:197 */     if (rtn > 0)
/* 155:    */     {
/* 156:198 */       String sql = "drop table " + tableName;
/* 157:199 */       this.jdbcTemplate.execute(sql);
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void addForeignKey(String pkTableName, String fkTableName, String pkField, String fkField)
/* 162:    */   {
/* 163:208 */     String regex = "(?im)" + TableModel.CUSTOMER_TABLE_PREFIX;
/* 164:209 */     String shortTableName = fkTableName.replaceFirst(regex, "");
/* 165:210 */     String sql = " ALTER TABLE " + fkTableName + " ADD CONSTRAINT fk_" + shortTableName + " FOREIGN KEY (" + fkField + ") REFERENCES " + pkTableName + " (" + pkField + ") ON DELETE CASCADE";
/* 166:211 */     this.jdbcTemplate.execute(sql);
/* 167:    */   }
/* 168:    */   
/* 169:    */   private Boolean isForeignKeyExist(String tableName, String keyName)
/* 170:    */   {
/* 171:216 */     String sql = "select count(1) from user_constraints t where t.table_name = upper('" + tableName + "') and t.constraint_type = 'R' and t.constraint_name = upper('" + keyName + "')";
/* 172:217 */     int result = this.jdbcTemplate.queryForInt(sql);
/* 173:218 */     return Boolean.valueOf(result > 0);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void dropForeignKey(String tableName, String keyName)
/* 177:    */   {
/* 178:223 */     if (isForeignKeyExist(tableName, keyName).booleanValue())
/* 179:    */     {
/* 180:224 */       String sql = "ALTER   TABLE   " + tableName + "   DROP   CONSTRAINT  " + keyName;
/* 181:225 */       this.jdbcTemplate.execute(sql);
/* 182:    */     }
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void createIndex(TableIndex index)
/* 186:    */   {
/* 187:231 */     String sql = generateIndexDDL(index);
/* 188:232 */     this.jdbcTemplate.execute(sql);
/* 189:    */   }
/* 190:    */   
/* 191:    */   private String generateIndexDDL(TableIndex index)
/* 192:    */   {
/* 193:241 */     StringBuffer sql = new StringBuffer();
/* 194:242 */     sql.append("CREATE ");
/* 195:    */     
/* 196:    */ 
/* 197:    */ 
/* 198:    */ 
/* 199:    */ 
/* 200:248 */     sql.append("INDEX ");
/* 201:249 */     sql.append(index.getIndexName());
/* 202:250 */     sql.append(" ON ");
/* 203:251 */     sql.append(index.getIndexTable());
/* 204:252 */     sql.append("(");
/* 205:253 */     for (String field : index.getIndexFields())
/* 206:    */     {
/* 207:254 */       sql.append(field);
/* 208:255 */       sql.append(",");
/* 209:    */     }
/* 210:257 */     sql.deleteCharAt(sql.length() - 1);
/* 211:258 */     sql.append(")");
/* 212:259 */     return sql.toString();
/* 213:    */   }
/* 214:    */   
/* 215:    */   public String getDbType()
/* 216:    */   {
/* 217:263 */     return this.dbType;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void dropIndex(String tableName, String indexName)
/* 221:    */   {
/* 222:267 */     String sql = "DROP INDEX " + indexName;
/* 223:268 */     this.jdbcTemplate.execute(sql);
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void rebuildIndex(String tableName, String indexName)
/* 227:    */   {
/* 228:272 */     String sql = "ALTER INDEX " + indexName + " REBUILD";
/* 229:273 */     this.jdbcTemplate.execute(sql);
/* 230:    */   }
/* 231:    */   
/* 232:    */   public TableIndex getIndex(String tableName, String indexName)
/* 233:    */   {
/* 234:277 */     String sql = "SELECT IDX.TABLE_NAME,IDX.TABLE_TYPE,IDX.INDEX_NAME, IDX.INDEX_TYPE,IDX.UNIQUENESS,IDX.STATUS,IDC.COLUMN_NAME,DBMS_METADATA.GET_DDL('INDEX',idx.INDEX_NAME) AS DDL FROM USER_INDEXES IDX JOIN USER_IND_COLUMNS IDC ON IDX.INDEX_NAME=IDC.INDEX_NAME  WHERE IDX.INDEX_NAME=UPPER('" + indexName + "')";
/* 235:    */     
/* 236:    */ 
/* 237:    */ 
/* 238:    */ 
/* 239:282 */     List<TableIndex> indexes = this.jdbcTemplate.query(sql, new RowMapper()
/* 240:    */     {
/* 241:    */       public TableIndex mapRow(ResultSet rs, int rowNum)
/* 242:    */         throws SQLException
/* 243:    */       {
/* 244:286 */         TableIndex index = new TableIndex();
/* 245:287 */         index.setIndexTable(rs.getString("TABLE_NAME"));
/* 246:288 */         index.setTableType(rs.getString("TABLE_TYPE"));
/* 247:289 */         index.setIndexName(rs.getString("INDEX_NAME"));
/* 248:290 */         index.setIndexType(rs.getString("INDEX_TYPE"));
/* 249:291 */         index.setUnique(rs.getString("UNIQUENESS").equalsIgnoreCase("UNIQUE"));
/* 250:292 */         index.setIndexStatus(rs.getString("STATUS"));
/* 251:293 */         index.setIndexDdl(rs.getString("DDL"));
/* 252:294 */         List<String> indexFields = new ArrayList();
/* 253:295 */         indexFields.add(rs.getString("COLUMN_NAME"));
/* 254:296 */         index.setIndexFields(indexFields);
/* 255:297 */         return index;
/* 256:    */       }
/* 257:302 */     });
/* 258:303 */     List<TableIndex> indexList = mergeIndex(indexes);
/* 259:304 */     if (indexList.size() > 0) {
/* 260:305 */       return dedicatePKIndex((TableIndex)indexList.get(0));
/* 261:    */     }
/* 262:307 */     return null;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public List<TableIndex> getIndexesByTable(String tableName)
/* 266:    */   {
/* 267:312 */     String sql = "SELECT IDX.TABLE_NAME,IDX.TABLE_TYPE,IDX.INDEX_NAME, IDX.INDEX_TYPE,IDX.UNIQUENESS,IDX.STATUS,IDC.COLUMN_NAME,DBMS_METADATA.GET_DDL('INDEX',idx.INDEX_NAME) AS DDL FROM USER_INDEXES IDX JOIN USER_IND_COLUMNS IDC ON IDX.INDEX_NAME=IDC.INDEX_NAME  WHERE IDX.TABLE_NAME=UPPER('" + tableName + "')";
/* 268:    */     
/* 269:    */ 
/* 270:    */ 
/* 271:    */ 
/* 272:317 */     List<TableIndex> indexes = this.jdbcTemplate.query(sql, new RowMapper()
/* 273:    */     {
/* 274:    */       public TableIndex mapRow(ResultSet rs, int rowNum)
/* 275:    */         throws SQLException
/* 276:    */       {
/* 277:320 */         TableIndex index = new TableIndex();
/* 278:321 */         index.setIndexTable(rs.getString("TABLE_NAME"));
/* 279:322 */         index.setTableType(rs.getString("TABLE_TYPE"));
/* 280:323 */         index.setIndexName(rs.getString("INDEX_NAME"));
/* 281:324 */         index.setIndexType(rs.getString("INDEX_TYPE"));
/* 282:325 */         index.setUnique(rs.getString("UNIQUENESS").equalsIgnoreCase("UNIQUE"));
/* 283:326 */         index.setIndexStatus(rs.getString("STATUS"));
/* 284:327 */         index.setIndexDdl(rs.getString("DDL"));
/* 285:    */         
/* 286:    */ 
/* 287:330 */         List<String> indexFields = new ArrayList();
/* 288:331 */         indexFields.add(rs.getString("COLUMN_NAME"));
/* 289:332 */         index.setIndexFields(indexFields);
/* 290:333 */         return index;
/* 291:    */       }
/* 292:338 */     });
/* 293:339 */     List<TableIndex> indexList = mergeIndex(indexes);
/* 294:    */     
/* 295:341 */     dedicatePKIndex(indexList);
/* 296:342 */     return indexList;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public List<TableIndex> getIndexesByFuzzyMatching(String tableName, String indexName, Boolean getDDL)
/* 300:    */   {
/* 301:347 */     return getIndexesByFuzzyMatching(tableName, indexName, getDDL, null);
/* 302:    */   }
/* 303:    */   
/* 304:    */   public List<TableIndex> getIndexesByFuzzyMatching(String tableName, String indexName, Boolean getDDL, PageBean pageBean)
/* 305:    */   {
/* 306:    */     String sql;
/* 307:    */     String sql;
/* 308:354 */     if (getDDL.booleanValue()) {
/* 309:355 */       sql = "SELECT IDX.TABLE_NAME,IDX.TABLE_TYPE,IDX.INDEX_NAME, IDX.INDEX_TYPE,IDX.UNIQUENESS,IDX.STATUS,IDC.COLUMN_NAME,DBMS_METADATA.GET_DDL('INDEX',idx.INDEX_NAME) AS DDL FROM USER_INDEXES IDX JOIN USER_IND_COLUMNS IDC ON IDX.INDEX_NAME=IDC.INDEX_NAME WHERE 1=1";
/* 310:    */     } else {
/* 311:361 */       sql = "SELECT IDX.TABLE_NAME,IDX.TABLE_TYPE,IDX.INDEX_NAME, IDX.INDEX_TYPE,IDX.UNIQUENESS,IDX.STATUS,IDC.COLUMN_NAME FROM USER_INDEXES IDX JOIN USER_IND_COLUMNS IDC ON IDX.INDEX_NAME=IDC.INDEX_NAME WHERE 1=1";
/* 312:    */     }
/* 313:366 */     if (!StringUtil.isEmpty(tableName)) {
/* 314:367 */       sql = sql + " AND UPPER(IDX.TABLE_NAME) LIKE UPPER('%" + tableName + "%')";
/* 315:    */     }
/* 316:370 */     if (!StringUtil.isEmpty(indexName)) {
/* 317:371 */       sql = sql + " AND UPPER(IDX.INDEX_NAME) like UPPER('%" + indexName + "%')";
/* 318:    */     }
/* 319:374 */     if (pageBean != null)
/* 320:    */     {
/* 321:375 */       int currentPage = pageBean.getCurrentPage();
/* 322:376 */       int pageSize = pageBean.getPageSize();
/* 323:377 */       int offset = (currentPage - 1) * pageSize;
/* 324:378 */       String totalSql = this.dialect.getCountSql(sql);
/* 325:379 */       int total = this.jdbcTemplate.queryForInt(totalSql);
/* 326:380 */       sql = this.dialect.getLimitString(sql, offset, pageSize);
/* 327:381 */       pageBean.setTotalCount(total);
/* 328:    */     }
/* 329:384 */     this.logger.debug(sql);
/* 330:    */     
/* 331:386 */     List<TableIndex> indexes = this.jdbcTemplate.query(sql, new RowMapper()
/* 332:    */     {
/* 333:    */       public TableIndex mapRow(ResultSet rs, int rowNum)
/* 334:    */         throws SQLException
/* 335:    */       {
/* 336:390 */         TableIndex index = new TableIndex();
/* 337:391 */         index.setIndexTable(rs.getString("TABLE_NAME"));
/* 338:392 */         index.setTableType(rs.getString("TABLE_TYPE"));
/* 339:393 */         index.setIndexName(rs.getString("INDEX_NAME"));
/* 340:394 */         index.setIndexType(rs.getString("INDEX_TYPE"));
/* 341:395 */         index.setUnique(rs.getString("UNIQUENESS").equalsIgnoreCase("UNIQUE"));
/* 342:396 */         index.setIndexStatus(rs.getString("STATUS"));
/* 343:    */         
/* 344:    */ 
/* 345:399 */         List<String> indexFields = new ArrayList();
/* 346:400 */         indexFields.add(rs.getString("COLUMN_NAME"));
/* 347:401 */         index.setIndexFields(indexFields);
/* 348:402 */         return index;
/* 349:    */       }
/* 350:407 */     });
/* 351:408 */     List<TableIndex> indexList = mergeIndex(indexes);
/* 352:    */     
/* 353:410 */     dedicatePKIndex(indexList);
/* 354:411 */     return indexList;
/* 355:    */   }
/* 356:    */   
/* 357:    */   private List<TableIndex> mergeIndex(List<TableIndex> indexes)
/* 358:    */   {
/* 359:415 */     List<TableIndex> indexList = new ArrayList();
/* 360:416 */     for (TableIndex index : indexes)
/* 361:    */     {
/* 362:417 */       boolean found = false;
/* 363:418 */       for (TableIndex index1 : indexList) {
/* 364:419 */         if ((index.getIndexName().equals(index1.getIndexName())) && (index.getIndexTable().equals(index1.getIndexTable())))
/* 365:    */         {
/* 366:421 */           index1.getIndexFields().add(index.getIndexFields().get(0));
/* 367:422 */           found = true;
/* 368:423 */           break;
/* 369:    */         }
/* 370:    */       }
/* 371:426 */       if (!found) {
/* 372:427 */         indexList.add(index);
/* 373:    */       }
/* 374:    */     }
/* 375:430 */     return indexList;
/* 376:    */   }
/* 377:    */   
/* 378:    */   public List<String> getPKColumns(String tableName)
/* 379:    */     throws SQLException
/* 380:    */   {
/* 381:435 */     String sql = "SELECT cols.column_name FROM USER_CONSTRAINTS CONS, USER_CONS_COLUMNS COLS WHERE UPPER(cols.table_name) = UPPER('" + tableName + "')" + " AND cons.constraint_type = 'P'" + " AND cons.constraint_name = cols.constraint_name" + " AND CONS.OWNER = COLS.OWNER";
/* 382:    */     
/* 383:    */ 
/* 384:    */ 
/* 385:    */ 
/* 386:    */ 
/* 387:441 */     List<String> columns = this.jdbcTemplate.query(sql, new RowMapper()
/* 388:    */     {
/* 389:    */       public String mapRow(ResultSet rs, int rowNum)
/* 390:    */         throws SQLException
/* 391:    */       {
/* 392:444 */         String column = rs.getString(1);
/* 393:445 */         return column;
/* 394:    */       }
/* 395:447 */     });
/* 396:448 */     return columns;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public Map<String, List<String>> getPKColumns(List<String> tableNames)
/* 400:    */     throws SQLException
/* 401:    */   {
/* 402:455 */     StringBuffer sb = new StringBuffer();
/* 403:456 */     for (String name : tableNames)
/* 404:    */     {
/* 405:457 */       sb.append("'");
/* 406:458 */       sb.append(name);
/* 407:459 */       sb.append("',");
/* 408:    */     }
/* 409:461 */     sb.deleteCharAt(sb.length() - 1);
/* 410:    */     
/* 411:463 */     String sql = "SELECT cols.table_name,cols.column_name FROM USER_CONSTRAINTS CONS, USER_CONS_COLUMNS COLS WHERE UPPER(cols.table_name) in (" + sb.toString().toUpperCase() + ")" + " AND cons.constraint_type = 'P'" + " AND cons.constraint_name = cols.constraint_name" + " AND CONS.OWNER = COLS.OWNER";
/* 412:    */     
/* 413:    */ 
/* 414:    */ 
/* 415:    */ 
/* 416:    */ 
/* 417:    */ 
/* 418:470 */     Map<String, List<String>> columnsMap = new HashMap();
/* 419:    */     
/* 420:472 */     List<Map<String, String>> maps = this.jdbcTemplate.query(sql, new RowMapper()
/* 421:    */     {
/* 422:    */       public Map<String, String> mapRow(ResultSet rs, int rowNum)
/* 423:    */         throws SQLException
/* 424:    */       {
/* 425:476 */         String table = rs.getString(1);
/* 426:477 */         String column = rs.getString(2);
/* 427:478 */         Map<String, String> map = new HashMap();
/* 428:479 */         map.put("name", table);
/* 429:480 */         map.put("column", column);
/* 430:481 */         return map;
/* 431:    */       }
/* 432:    */     });
/* 433:485 */     for (Map<String, String> map : maps) {
/* 434:486 */       if (columnsMap.containsKey(map.get("name")))
/* 435:    */       {
/* 436:487 */         ((List)columnsMap.get(map.get("name"))).add(map.get("column"));
/* 437:    */       }
/* 438:    */       else
/* 439:    */       {
/* 440:489 */         List<String> cols = new ArrayList();
/* 441:490 */         cols.add(map.get("column"));
/* 442:491 */         columnsMap.put(map.get("name"), cols);
/* 443:    */       }
/* 444:    */     }
/* 445:495 */     return columnsMap;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public void setDialect(Dialect dialect)
/* 449:    */   {
/* 450:500 */     this.dialect = dialect;
/* 451:    */   }
/* 452:    */   
/* 453:    */   private Map<String, List<String>> getTablesPKColsByNames(List<String> tableNames)
/* 454:    */   {
/* 455:510 */     Map<String, List<String>> tableMaps = new HashMap();
/* 456:511 */     List<String> names = new ArrayList();
/* 457:512 */     for (int i = 1; i <= tableNames.size(); i++)
/* 458:    */     {
/* 459:513 */       names.add(tableNames.get(i - 1));
/* 460:514 */       if ((i % this.BATCHSIZE == 0) || (i == tableNames.size())) {
/* 461:    */         try
/* 462:    */         {
/* 463:517 */           Map<String, List<String>> map = getPKColumns(names);
/* 464:518 */           tableMaps.putAll(map);
/* 465:519 */           names.clear();
/* 466:    */         }
/* 467:    */         catch (SQLException e)
/* 468:    */         {
/* 469:521 */           e.printStackTrace();
/* 470:    */         }
/* 471:    */       }
/* 472:    */     }
/* 473:525 */     return tableMaps;
/* 474:    */   }
/* 475:    */   
/* 476:    */   private boolean isListEqual(List list1, List list2)
/* 477:    */   {
/* 478:536 */     if (list1 == null)
/* 479:    */     {
/* 480:537 */       if (list2 == null) {
/* 481:538 */         return true;
/* 482:    */       }
/* 483:540 */       return false;
/* 484:    */     }
/* 485:542 */     if (list2 == null) {
/* 486:543 */       return false;
/* 487:    */     }
/* 488:547 */     if (list1.size() != list2.size()) {
/* 489:548 */       return false;
/* 490:    */     }
/* 491:550 */     if (list1.containsAll(list2)) {
/* 492:551 */       return true;
/* 493:    */     }
/* 494:553 */     return false;
/* 495:    */   }
/* 496:    */   
/* 497:    */   private List<TableIndex> dedicatePKIndex(List<TableIndex> indexList)
/* 498:    */   {
/* 499:563 */     List<String> tableNames = new ArrayList();
/* 500:564 */     for (TableIndex index : indexList) {
/* 501:566 */       if (!tableNames.contains(index.getIndexTable())) {
/* 502:567 */         tableNames.add(index.getIndexTable());
/* 503:    */       }
/* 504:    */     }
/* 505:570 */     Map<String, List<String>> tablePKColsMaps = getTablesPKColsByNames(tableNames);
/* 506:571 */     for (TableIndex index : indexList) {
/* 507:572 */       if (isListEqual(index.getIndexFields(), (List)tablePKColsMaps.get(index.getIndexTable()))) {
/* 508:573 */         index.setPkIndex(true);
/* 509:    */       } else {
/* 510:575 */         index.setPkIndex(false);
/* 511:    */       }
/* 512:    */     }
/* 513:579 */     return indexList;
/* 514:    */   }
/* 515:    */   
/* 516:    */   private TableIndex dedicatePKIndex(TableIndex index)
/* 517:    */   {
/* 518:    */     try
/* 519:    */     {
/* 520:590 */       List<String> pkCols = getPKColumns(index.getIndexName());
/* 521:591 */       if (isListEqual(index.getIndexFields(), pkCols)) {
/* 522:592 */         index.setPkIndex(true);
/* 523:    */       } else {
/* 524:594 */         index.setPkIndex(false);
/* 525:    */       }
/* 526:    */     }
/* 527:    */     catch (SQLException e)
/* 528:    */     {
/* 529:597 */       e.printStackTrace();
/* 530:    */     }
/* 531:599 */     return index;
/* 532:    */   }
/* 533:    */   
/* 534:    */   public boolean isTableExist(String tableName)
/* 535:    */   {
/* 536:604 */     StringBuffer sql = new StringBuffer();
/* 537:605 */     sql.append("select COUNT(1) from user_tables t where t.TABLE_NAME='").append(tableName.toUpperCase()).append("'");
/* 538:606 */     return this.jdbcTemplate.queryForInt(sql.toString()) > 0;
/* 539:    */   }
/* 540:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.impl.OracleTableOperator
 * JD-Core Version:    0.7.0.1
 */