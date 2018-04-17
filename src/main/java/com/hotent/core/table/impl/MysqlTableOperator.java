/*   1:    */ package com.hotent.core.table.impl;
/*   2:    */ 
/*   3:    */ import com.hotent.core.model.TableIndex;
/*   4:    */ import com.hotent.core.mybatis.Dialect;
/*   5:    */ import com.hotent.core.page.PageBean;
/*   6:    */ import com.hotent.core.table.AbstractTableOperator;
/*   7:    */ import com.hotent.core.table.ColumnModel;
/*   8:    */ import com.hotent.core.table.TableModel;
/*   9:    */ import com.hotent.core.util.StringUtil;
/*  10:    */ import java.sql.Connection;
/*  11:    */ import java.sql.ResultSet;
/*  12:    */ import java.sql.SQLException;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.HashMap;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import java.util.regex.Matcher;
/*  18:    */ import java.util.regex.Pattern;
/*  19:    */ import javax.sql.DataSource;
/*  20:    */ import org.springframework.jdbc.core.JdbcTemplate;
/*  21:    */ import org.springframework.jdbc.core.RowMapper;
/*  22:    */ 
/*  23:    */ public class MysqlTableOperator
/*  24:    */   extends AbstractTableOperator
/*  25:    */ {
/*  26: 32 */   private int BATCHSIZE = 100;
/*  27:    */   
/*  28:    */   public void createTable(TableModel model)
/*  29:    */     throws SQLException
/*  30:    */   {
/*  31: 41 */     List<ColumnModel> columnList = model.getColumnList();
/*  32:    */     
/*  33:    */ 
/*  34: 44 */     StringBuffer sb = new StringBuffer();
/*  35:    */     
/*  36: 46 */     String pkColumn = null;
/*  37:    */     
/*  38: 48 */     sb.append("CREATE TABLE " + model.getName() + " (\n");
/*  39: 49 */     for (int i = 0; i < columnList.size(); i++)
/*  40:    */     {
/*  41: 52 */       ColumnModel cm = (ColumnModel)columnList.get(i);
/*  42: 53 */       sb.append(cm.getName()).append(" ");
/*  43: 54 */       sb.append(getColumnType(cm.getColumnType(), cm.getCharLen(), cm.getIntLen(), cm.getDecimalLen()));
/*  44: 55 */       sb.append(" ");
/*  45:    */       
/*  46: 57 */       String defaultValue = cm.getDefaultValue();
/*  47: 60 */       if (StringUtil.isNotEmpty(defaultValue)) {
/*  48: 61 */         sb.append(" default " + defaultValue);
/*  49:    */       }
/*  50: 69 */       if (cm.getIsPk()) {
/*  51: 70 */         if (pkColumn == null) {
/*  52: 71 */           pkColumn = cm.getName();
/*  53:    */         } else {
/*  54: 73 */           pkColumn = pkColumn + "," + cm.getName();
/*  55:    */         }
/*  56:    */       }
/*  57: 77 */       if ((cm.getComment() != null) && (cm.getComment().length() > 0)) {
/*  58: 78 */         sb.append(" COMMENT '" + cm.getComment() + "'");
/*  59:    */       }
/*  60: 80 */       sb.append(",\n");
/*  61:    */     }
/*  62: 83 */     if (pkColumn != null) {
/*  63: 84 */       sb.append(" PRIMARY KEY (" + pkColumn + ")");
/*  64:    */     }
/*  65: 87 */     sb.append("\n)");
/*  66: 88 */     if ((model.getComment() != null) && (model.getComment().length() > 0)) {
/*  67: 89 */       sb.append(" COMMENT='" + model.getComment() + "'");
/*  68:    */     }
/*  69: 92 */     sb.append(";");
/*  70:    */     
/*  71: 94 */     this.jdbcTemplate.execute(sb.toString());
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void updateTableComment(String tableName, String comment)
/*  75:    */     throws SQLException
/*  76:    */   {
/*  77:101 */     StringBuffer sb = new StringBuffer();
/*  78:102 */     sb.append("ALTER TABLE ");
/*  79:103 */     sb.append(tableName);
/*  80:104 */     sb.append(" COMMENT '");
/*  81:105 */     sb.append(comment);
/*  82:106 */     sb.append("';\n");
/*  83:    */     
/*  84:108 */     this.jdbcTemplate.execute(sb.toString());
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void addColumn(String tableName, ColumnModel model)
/*  88:    */     throws SQLException
/*  89:    */   {
/*  90:115 */     StringBuffer sb = new StringBuffer();
/*  91:116 */     sb.append("ALTER TABLE ").append(tableName);
/*  92:117 */     sb.append(" ADD (");
/*  93:118 */     sb.append(model.getName()).append(" ");
/*  94:119 */     sb.append(getColumnType(model.getColumnType(), model.getCharLen(), model.getIntLen(), model.getDecimalLen()));
/*  95:124 */     if (!model.getIsNull()) {
/*  96:126 */       sb.append(" NOT NULL ");
/*  97:    */     }
/*  98:128 */     if ((model.getComment() != null) && (model.getComment().length() > 0)) {
/*  99:130 */       sb.append(" COMMENT '" + model.getComment() + "'");
/* 100:    */     }
/* 101:132 */     sb.append(");");
/* 102:    */     
/* 103:134 */     this.jdbcTemplate.execute(sb.toString());
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void updateColumn(String tableName, String columnName, ColumnModel model)
/* 107:    */     throws SQLException
/* 108:    */   {
/* 109:141 */     StringBuffer sb = new StringBuffer();
/* 110:142 */     sb.append("ALTER TABLE ").append(tableName);
/* 111:143 */     sb.append(" CHANGE " + columnName + " " + model.getName()).append(" ");
/* 112:144 */     sb.append(getColumnType(model.getColumnType(), model.getCharLen(), model.getIntLen(), model.getDecimalLen()));
/* 113:146 */     if (!model.getIsNull()) {
/* 114:148 */       sb.append(" NOT NULL ");
/* 115:    */     }
/* 116:150 */     if ((model.getComment() != null) && (model.getComment().length() > 0)) {
/* 117:152 */       sb.append(" COMMENT '" + model.getComment() + "'");
/* 118:    */     }
/* 119:154 */     sb.append(";");
/* 120:    */     
/* 121:156 */     this.jdbcTemplate.execute(sb.toString());
/* 122:    */   }
/* 123:    */   
/* 124:    */   private String getColumnType(String columnType, int charLen, int intLen, int decimalLen)
/* 125:    */   {
/* 126:161 */     if ("varchar".equals(columnType)) {
/* 127:162 */       return "VARCHAR(" + charLen + ')';
/* 128:    */     }
/* 129:164 */     if ("number".equals(columnType)) {
/* 130:165 */       return "DECIMAL(" + (intLen + decimalLen) + "," + decimalLen + ")";
/* 131:    */     }
/* 132:167 */     if ("date".equals(columnType)) {
/* 133:168 */       return "DATETIME";
/* 134:    */     }
/* 135:169 */     if ("int".equals(columnType)) {
/* 136:171 */       return "BIGINT(" + intLen + ")";
/* 137:    */     }
/* 138:173 */     if ("clob".equals(columnType)) {
/* 139:175 */       return "TEXT";
/* 140:    */     }
/* 141:178 */     return "";
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void dropTable(String tableName)
/* 145:    */   {
/* 146:185 */     String sql = "drop table if exists " + tableName;
/* 147:186 */     this.jdbcTemplate.execute(sql);
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void addForeignKey(String pkTableName, String fkTableName, String pkField, String fkField)
/* 151:    */   {
/* 152:192 */     String sql = "ALTER TABLE " + fkTableName + " ADD CONSTRAINT fk_" + fkTableName + " FOREIGN KEY (" + fkField + ") REFERENCES " + pkTableName + " (" + pkField + ") ON DELETE CASCADE";
/* 153:193 */     this.jdbcTemplate.execute(sql);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void dropForeignKey(String tableName, String keyName)
/* 157:    */   {
/* 158:198 */     String sql = "ALTER TABLE " + tableName + " DROP FOREIGN KEY " + keyName;
/* 159:    */     
/* 160:200 */     this.jdbcTemplate.execute(sql);
/* 161:    */   }
/* 162:    */   
/* 163:    */   public String getDbType()
/* 164:    */   {
/* 165:206 */     return this.dbType;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void createIndex(TableIndex index)
/* 169:    */   {
/* 170:216 */     String sql = generateIndexDDL(index);
/* 171:217 */     this.jdbcTemplate.execute(sql);
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void dropIndex(String tableName, String indexName)
/* 175:    */   {
/* 176:222 */     String sql = "drop index " + indexName;
/* 177:223 */     sql = sql + " on " + tableName;
/* 178:224 */     this.jdbcTemplate.execute(sql);
/* 179:    */   }
/* 180:    */   
/* 181:    */   public TableIndex getIndex(String tableName, String indexName)
/* 182:    */   {
/* 183:229 */     if (getIndexesByFuzzyMatching(tableName, indexName, Boolean.valueOf(true)).size() > 0)
/* 184:    */     {
/* 185:230 */       TableIndex index = (TableIndex)getIndexesByFuzzyMatching(tableName, indexName, Boolean.valueOf(true)).get(0);
/* 186:    */       try
/* 187:    */       {
/* 188:232 */         return dedicatePKIndex(index);
/* 189:    */       }
/* 190:    */       catch (SQLException e)
/* 191:    */       {
/* 192:235 */         e.printStackTrace();
/* 193:    */       }
/* 194:    */     }
/* 195:238 */     return null;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public List<TableIndex> getIndexesByTable(String tableName)
/* 199:    */   {
/* 200:244 */     List<TableIndex> indexList = getIndexesByFuzzyMatching(tableName, null, Boolean.valueOf(true));
/* 201:245 */     return dedicatePKIndex(indexList);
/* 202:    */   }
/* 203:    */   
/* 204:    */   public List<TableIndex> getIndexesByFuzzyMatching(String tableName, String indexName, Boolean getDDL)
/* 205:    */   {
/* 206:251 */     String schema = getSchema();
/* 207:    */     
/* 208:253 */     String sql = "SELECT TABLE_NAME,INDEX_NAME,COLUMN_NAME,NULLABLE,INDEX_TYPE,NON_UNIQUE FROM  INFORMATION_SCHEMA.STATISTICS WHERE 1=1";
/* 209:256 */     if (!StringUtil.isEmpty(schema)) {
/* 210:257 */       sql = sql + " AND TABLE_SCHEMA='" + schema + "'";
/* 211:    */     }
/* 212:259 */     if (!StringUtil.isEmpty(tableName)) {
/* 213:260 */       sql = sql + " AND UPPER(TABLE_NAME) LIKE UPPER('%" + tableName + "%')";
/* 214:    */     }
/* 215:263 */     if (!StringUtil.isEmpty(indexName)) {
/* 216:264 */       sql = sql + " AND UPPER(INDEX_NAME) like UPPER('%" + indexName + "%')";
/* 217:    */     }
/* 218:267 */     List<TableIndex> indexes = this.jdbcTemplate.query(sql, new RowMapper()
/* 219:    */     {
/* 220:    */       public TableIndex mapRow(ResultSet rs, int rowNum)
/* 221:    */         throws SQLException
/* 222:    */       {
/* 223:271 */         TableIndex index = new TableIndex();
/* 224:272 */         List<String> columns = new ArrayList();
/* 225:273 */         index.setIndexTable(rs.getString("TABLE_NAME"));
/* 226:274 */         index.setIndexName(rs.getString("INDEX_NAME"));
/* 227:275 */         index.setIndexType(rs.getString("INDEX_TYPE"));
/* 228:276 */         index.setUnique(rs.getInt("NON_UNIQUE") == 0);
/* 229:    */         
/* 230:278 */         columns.add(rs.getString("COLUMN_NAME"));
/* 231:279 */         index.setIndexFields(columns);
/* 232:280 */         return index;
/* 233:    */       }
/* 234:284 */     });
/* 235:285 */     List<TableIndex> indexList = new ArrayList();
/* 236:286 */     for (TableIndex index : indexes)
/* 237:    */     {
/* 238:287 */       boolean found = false;
/* 239:288 */       for (TableIndex index1 : indexList) {
/* 240:289 */         if ((index.getIndexName().equals(index1.getIndexName())) && (index.getIndexTable().equals(index1.getIndexTable())))
/* 241:    */         {
/* 242:291 */           index1.getIndexFields().add(index.getIndexFields().get(0));
/* 243:292 */           found = true;
/* 244:293 */           break;
/* 245:    */         }
/* 246:    */       }
/* 247:296 */       if (!found) {
/* 248:297 */         indexList.add(index);
/* 249:    */       }
/* 250:    */     }
/* 251:301 */     if (getDDL.booleanValue()) {
/* 252:302 */       for (TableIndex index : indexList) {
/* 253:303 */         index.setIndexDdl(generateIndexDDL(index));
/* 254:    */       }
/* 255:    */     }
/* 256:307 */     dedicatePKIndex(indexList);
/* 257:308 */     return indexList;
/* 258:    */   }
/* 259:    */   
/* 260:    */   private List<TableIndex> dedicatePKIndex(List<TableIndex> indexList)
/* 261:    */   {
/* 262:313 */     List<String> tableNames = new ArrayList();
/* 263:314 */     for (TableIndex index : indexList) {
/* 264:316 */       if (!tableNames.contains(index.getIndexTable())) {
/* 265:317 */         tableNames.add(index.getIndexTable());
/* 266:    */       }
/* 267:    */     }
/* 268:320 */     Map<String, List<String>> tablePKColsMaps = getTablesPKColsByNames(tableNames);
/* 269:321 */     for (TableIndex index : indexList) {
/* 270:322 */       if (isListEqual(index.getIndexFields(), (List)tablePKColsMaps.get(index.getIndexTable()))) {
/* 271:323 */         index.setPkIndex(true);
/* 272:    */       } else {
/* 273:325 */         index.setPkIndex(false);
/* 274:    */       }
/* 275:    */     }
/* 276:329 */     return indexList;
/* 277:    */   }
/* 278:    */   
/* 279:    */   private TableIndex dedicatePKIndex(TableIndex index)
/* 280:    */     throws SQLException
/* 281:    */   {
/* 282:333 */     List<String> pkCols = getPKColumns(index.getIndexName());
/* 283:334 */     if (isListEqual(index.getIndexFields(), pkCols)) {
/* 284:335 */       index.setPkIndex(true);
/* 285:    */     } else {
/* 286:337 */       index.setPkIndex(false);
/* 287:    */     }
/* 288:339 */     return index;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public List<TableIndex> getIndexesByFuzzyMatching(String tableName, String indexName, Boolean getDDL, PageBean pageBean)
/* 292:    */   {
/* 293:345 */     String schema = getSchema();
/* 294:    */     
/* 295:347 */     String sql = "SELECT TABLE_NAME,INDEX_NAME,COLUMN_NAME,NULLABLE,INDEX_TYPE,NON_UNIQUE FROM  INFORMATION_SCHEMA.STATISTICS WHERE 1=1";
/* 296:350 */     if (!StringUtil.isEmpty(schema)) {
/* 297:351 */       sql = sql + " AND TABLE_SCHEMA='" + schema + "'";
/* 298:    */     }
/* 299:353 */     if (!StringUtil.isEmpty(tableName)) {
/* 300:354 */       sql = sql + " AND UPPER(TABLE_NAME) LIKE UPPER('%" + tableName + "%')";
/* 301:    */     }
/* 302:357 */     if (!StringUtil.isEmpty(indexName)) {
/* 303:358 */       sql = sql + " AND UPPER(INDEX_NAME) like UPPER('%" + indexName + "%')";
/* 304:    */     }
/* 305:362 */     if (pageBean != null)
/* 306:    */     {
/* 307:363 */       int currentPage = pageBean.getCurrentPage();
/* 308:364 */       int pageSize = pageBean.getPageSize();
/* 309:365 */       int offset = (currentPage - 1) * pageSize;
/* 310:366 */       String totalSql = this.dialect.getCountSql(sql);
/* 311:367 */       int total = this.jdbcTemplate.queryForInt(totalSql);
/* 312:368 */       sql = this.dialect.getLimitString(sql, offset, pageSize);
/* 313:369 */       pageBean.setTotalCount(total);
/* 314:    */     }
/* 315:372 */     List<TableIndex> indexes = this.jdbcTemplate.query(sql, new RowMapper()
/* 316:    */     {
/* 317:    */       public TableIndex mapRow(ResultSet rs, int rowNum)
/* 318:    */         throws SQLException
/* 319:    */       {
/* 320:376 */         TableIndex index = new TableIndex();
/* 321:377 */         List<String> columns = new ArrayList();
/* 322:378 */         index.setIndexTable(rs.getString("TABLE_NAME"));
/* 323:379 */         index.setIndexName(rs.getString("INDEX_NAME"));
/* 324:380 */         index.setIndexType(rs.getString("INDEX_TYPE"));
/* 325:381 */         index.setUnique(rs.getInt("NON_UNIQUE") == 0);
/* 326:    */         
/* 327:383 */         columns.add(rs.getString("COLUMN_NAME"));
/* 328:384 */         index.setIndexFields(columns);
/* 329:385 */         return index;
/* 330:    */       }
/* 331:388 */     });
/* 332:389 */     List<TableIndex> indexList = new ArrayList();
/* 333:390 */     for (TableIndex index : indexes)
/* 334:    */     {
/* 335:391 */       boolean found = false;
/* 336:392 */       for (TableIndex index1 : indexList) {
/* 337:393 */         if ((index.getIndexName().equals(index1.getIndexName())) && (index.getIndexTable().equals(index1.getIndexTable())))
/* 338:    */         {
/* 339:395 */           index1.getIndexFields().add(index.getIndexFields().get(0));
/* 340:396 */           found = true;
/* 341:397 */           break;
/* 342:    */         }
/* 343:    */       }
/* 344:400 */       if (!found) {
/* 345:401 */         indexList.add(index);
/* 346:    */       }
/* 347:    */     }
/* 348:405 */     if (getDDL.booleanValue()) {
/* 349:406 */       for (TableIndex index : indexList) {
/* 350:407 */         index.setIndexDdl(generateIndexDDL(index));
/* 351:    */       }
/* 352:    */     }
/* 353:410 */     dedicatePKIndex(indexList);
/* 354:411 */     return indexList;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public void rebuildIndex(String tableName, String indexName)
/* 358:    */   {
/* 359:416 */     String sql = "SHOW CREATE TABLE " + tableName;
/* 360:    */     
/* 361:418 */     List<String> ddls = this.jdbcTemplate.query(sql, new RowMapper()
/* 362:    */     {
/* 363:    */       public String mapRow(ResultSet rs, int rowNum)
/* 364:    */         throws SQLException
/* 365:    */       {
/* 366:421 */         return rs.getString("Create Table");
/* 367:    */       }
/* 368:424 */     });
/* 369:425 */     String ddl = (String)ddls.get(0);
/* 370:    */     
/* 371:427 */     Pattern pattern = Pattern.compile("ENGINE\\s*=\\s*\\S+", 2);
/* 372:428 */     Matcher matcher = pattern.matcher(ddl);
/* 373:429 */     if (matcher.find())
/* 374:    */     {
/* 375:430 */       String str = matcher.group();
/* 376:431 */       String sql_ = "ALTER TABLE " + tableName + " " + str;
/* 377:432 */       this.jdbcTemplate.execute(sql_);
/* 378:    */     }
/* 379:    */   }
/* 380:    */   
/* 381:    */   public List<String> getPKColumns(String tableName)
/* 382:    */     throws SQLException
/* 383:    */   {
/* 384:440 */     String schema = getSchema();
/* 385:441 */     String sql = "SELECT k.column_name FROM information_schema.table_constraints t JOIN information_schema.key_column_usage k USING(constraint_name,table_schema,table_name) WHERE t.constraint_type='PRIMARY KEY' AND t.table_schema='" + schema + "' " + "AND t.table_name='" + tableName + "'";
/* 386:    */     
/* 387:    */ 
/* 388:    */ 
/* 389:    */ 
/* 390:    */ 
/* 391:    */ 
/* 392:448 */     List<String> columns = this.jdbcTemplate.query(sql, new RowMapper()
/* 393:    */     {
/* 394:    */       public String mapRow(ResultSet rs, int rowNum)
/* 395:    */         throws SQLException
/* 396:    */       {
/* 397:453 */         String column = rs.getString(1);
/* 398:454 */         return column;
/* 399:    */       }
/* 400:456 */     });
/* 401:457 */     return columns;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public Map<String, List<String>> getPKColumns(List<String> tableNames)
/* 405:    */     throws SQLException
/* 406:    */   {
/* 407:462 */     StringBuffer sb = new StringBuffer();
/* 408:463 */     for (String name : tableNames)
/* 409:    */     {
/* 410:464 */       sb.append("'");
/* 411:465 */       sb.append(name);
/* 412:466 */       sb.append("',");
/* 413:    */     }
/* 414:468 */     sb.deleteCharAt(sb.length() - 1);
/* 415:    */     
/* 416:470 */     String schema = getSchema();
/* 417:471 */     String sql = "SELECT t.table_name,k.column_name FROM information_schema.table_constraints t JOIN information_schema.key_column_usage k USING(constraint_name,table_schema,table_name) WHERE t.constraint_type='PRIMARY KEY' AND t.table_schema='" + schema + "' " + "AND t.table_name in (" + sb.toString().toUpperCase() + ")";
/* 418:    */     
/* 419:    */ 
/* 420:    */ 
/* 421:    */ 
/* 422:    */ 
/* 423:    */ 
/* 424:    */ 
/* 425:479 */     Map<String, List<String>> columnsMap = new HashMap();
/* 426:    */     
/* 427:481 */     List<Map<String, String>> maps = this.jdbcTemplate.query(sql, new RowMapper()
/* 428:    */     {
/* 429:    */       public Map<String, String> mapRow(ResultSet rs, int rowNum)
/* 430:    */         throws SQLException
/* 431:    */       {
/* 432:485 */         String table = rs.getString(1);
/* 433:486 */         String column = rs.getString(2);
/* 434:487 */         Map<String, String> map = new HashMap();
/* 435:488 */         map.put("name", table);
/* 436:489 */         map.put("column", column);
/* 437:490 */         return map;
/* 438:    */       }
/* 439:    */     });
/* 440:494 */     for (Map<String, String> map : maps) {
/* 441:495 */       if (columnsMap.containsKey(map.get("name")))
/* 442:    */       {
/* 443:496 */         ((List)columnsMap.get(map.get("name"))).add(map.get("column"));
/* 444:    */       }
/* 445:    */       else
/* 446:    */       {
/* 447:498 */         List<String> cols = new ArrayList();
/* 448:499 */         cols.add(map.get("column"));
/* 449:500 */         columnsMap.put(map.get("name"), cols);
/* 450:    */       }
/* 451:    */     }
/* 452:504 */     return columnsMap;
/* 453:    */   }
/* 454:    */   
/* 455:    */   private String getSchema()
/* 456:    */   {
/* 457:512 */     String schema = null;
/* 458:    */     try
/* 459:    */     {
/* 460:514 */       schema = this.jdbcTemplate.getDataSource().getConnection().getCatalog();
/* 461:    */     }
/* 462:    */     catch (SQLException e)
/* 463:    */     {
/* 464:517 */       e.printStackTrace();
/* 465:    */     }
/* 466:519 */     return schema;
/* 467:    */   }
/* 468:    */   
/* 469:    */   private String generateIndexDDL(TableIndex index)
/* 470:    */   {
/* 471:528 */     StringBuffer ddl = new StringBuffer();
/* 472:529 */     ddl.append("CREATE");
/* 473:530 */     if (index.isUnique()) {
/* 474:531 */       ddl.append(" UNIQUE");
/* 475:    */     }
/* 476:533 */     ddl.append(" INDEX");
/* 477:534 */     ddl.append(" " + index.getIndexName());
/* 478:535 */     ddl.append(" USING");
/* 479:536 */     ddl.append(" " + index.getIndexType());
/* 480:537 */     ddl.append(" ON " + index.getIndexTable());
/* 481:539 */     for (String column : index.getIndexFields()) {
/* 482:540 */       ddl.append(column + ",");
/* 483:    */     }
/* 484:542 */     if (!StringUtil.isEmpty(index.getIndexComment())) {
/* 485:543 */       ddl.append("COMMENT '" + index.getIndexComment() + "'");
/* 486:    */     }
/* 487:545 */     ddl.replace(ddl.length() - 1, ddl.length(), ")");
/* 488:546 */     return ddl.toString();
/* 489:    */   }
/* 490:    */   
/* 491:    */   private boolean isIndexExist(String tableName, String indexName)
/* 492:    */   {
/* 493:555 */     String schema = getSchema();
/* 494:    */     
/* 495:557 */     String sql = "SELECT COUNT(*) FROM  INFORMATION_SCHEMA.STATISTICS WHERE 1=1";
/* 496:560 */     if (!StringUtil.isEmpty(schema)) {
/* 497:561 */       sql = sql + " AND TABLE_SCHEMA='" + schema + "'";
/* 498:    */     }
/* 499:563 */     if (!StringUtil.isEmpty(tableName)) {
/* 500:564 */       sql = sql + " AND UPPER(TABLE_NAME) = UPPER('" + tableName + "')";
/* 501:    */     }
/* 502:567 */     if (!StringUtil.isEmpty(indexName)) {
/* 503:568 */       sql = sql + " AND UPPER(INDEX_NAME) = UPPER('" + indexName + "')";
/* 504:    */     }
/* 505:570 */     int count = this.jdbcTemplate.queryForInt(sql);
/* 506:571 */     if (count > 0) {
/* 507:572 */       return true;
/* 508:    */     }
/* 509:574 */     return false;
/* 510:    */   }
/* 511:    */   
/* 512:    */   private Map<String, List<String>> getTablesPKColsByNames(List<String> tableNames)
/* 513:    */   {
/* 514:584 */     Map<String, List<String>> tableMaps = new HashMap();
/* 515:585 */     List<String> names = new ArrayList();
/* 516:586 */     for (int i = 1; i <= tableNames.size(); i++)
/* 517:    */     {
/* 518:587 */       names.add(tableNames.get(i - 1));
/* 519:588 */       if ((i % this.BATCHSIZE == 0) || (i == tableNames.size())) {
/* 520:    */         try
/* 521:    */         {
/* 522:591 */           Map<String, List<String>> map = getPKColumns(names);
/* 523:592 */           tableMaps.putAll(map);
/* 524:593 */           names.clear();
/* 525:    */         }
/* 526:    */         catch (SQLException e)
/* 527:    */         {
/* 528:596 */           e.printStackTrace();
/* 529:    */         }
/* 530:    */       }
/* 531:    */     }
/* 532:600 */     return tableMaps;
/* 533:    */   }
/* 534:    */   
/* 535:    */   private boolean isListEqual(List list1, List list2)
/* 536:    */   {
/* 537:610 */     if (list1 == null)
/* 538:    */     {
/* 539:611 */       if (list2 == null) {
/* 540:612 */         return true;
/* 541:    */       }
/* 542:614 */       return false;
/* 543:    */     }
/* 544:616 */     if (list2 == null) {
/* 545:617 */       return false;
/* 546:    */     }
/* 547:619 */     if (list1.size() != list2.size()) {
/* 548:620 */       return false;
/* 549:    */     }
/* 550:622 */     if (list1.containsAll(list2)) {
/* 551:623 */       return true;
/* 552:    */     }
/* 553:625 */     return false;
/* 554:    */   }
/* 555:    */   
/* 556:    */   public boolean isTableExist(String tableName)
/* 557:    */   {
/* 558:632 */     String schema = getSchema();
/* 559:633 */     String sql = "select count(1) from information_schema.TABLES t where t.TABLE_SCHEMA='" + schema + "' and table_name ='" + tableName.toUpperCase() + "'";
/* 560:634 */     return this.jdbcTemplate.queryForInt(sql) > 0;
/* 561:    */   }
/* 562:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.impl.MysqlTableOperator
 * JD-Core Version:    0.7.0.1
 */