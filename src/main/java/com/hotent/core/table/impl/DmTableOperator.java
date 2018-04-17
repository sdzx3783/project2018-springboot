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
/*  20:    */ public class DmTableOperator
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
/* 163:209 */     String sql = " ALTER TABLE " + fkTableName + " ADD CONSTRAINT fk_" + fkTableName + " FOREIGN KEY (" + fkField + ") REFERENCES " + pkTableName + " (" + pkField + ") ON DELETE CASCADE";
/* 164:210 */     this.jdbcTemplate.execute(sql);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void dropForeignKey(String tableName, String keyName)
/* 168:    */   {
/* 169:218 */     String sql = "ALTER   TABLE   " + tableName + "   DROP   CONSTRAINT  " + keyName;
/* 170:219 */     this.jdbcTemplate.execute(sql);
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void createIndex(TableIndex index)
/* 174:    */   {
/* 175:227 */     String sql = generateIndexDDL(index);
/* 176:228 */     this.jdbcTemplate.execute(sql);
/* 177:    */   }
/* 178:    */   
/* 179:    */   private String generateIndexDDL(TableIndex index)
/* 180:    */   {
/* 181:237 */     StringBuffer sql = new StringBuffer();
/* 182:238 */     sql.append("CREATE ");
/* 183:239 */     if ((!StringUtil.isEmpty(index.getIndexType())) && 
/* 184:240 */       (index.getIndexType().equalsIgnoreCase("BITMAP"))) {
/* 185:241 */       sql.append("BITMAP ");
/* 186:    */     }
/* 187:244 */     sql.append("INDEX ");
/* 188:245 */     sql.append(index.getIndexName());
/* 189:246 */     sql.append(" ON ");
/* 190:247 */     sql.append(index.getIndexTable());
/* 191:248 */     sql.append("(");
/* 192:249 */     for (String field : index.getIndexFields())
/* 193:    */     {
/* 194:250 */       sql.append(field);
/* 195:251 */       sql.append(",");
/* 196:    */     }
/* 197:253 */     sql.deleteCharAt(sql.length() - 1);
/* 198:254 */     sql.append(")");
/* 199:255 */     return sql.toString();
/* 200:    */   }
/* 201:    */   
/* 202:    */   private boolean isIndexExist(String index)
/* 203:    */   {
/* 204:259 */     String sql = "SELECT COUNT(*) FROM \"SYS\".\"USER_INDEXES\" WHERE INDEX_NAME = '" + index + "'";
/* 205:260 */     int count = this.jdbcTemplate.queryForInt(sql);
/* 206:261 */     if (count > 0) {
/* 207:262 */       return true;
/* 208:    */     }
/* 209:264 */     return false;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public String getDbType()
/* 213:    */   {
/* 214:271 */     return this.dbType;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void dropIndex(String tableName, String indexName)
/* 218:    */   {
/* 219:275 */     String sql = "DROP INDEX " + indexName;
/* 220:276 */     this.jdbcTemplate.execute(sql);
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void rebuildIndex(String tableName, String indexName)
/* 224:    */   {
/* 225:280 */     String sql = "ALTER INDEX " + indexName + " REBUILD";
/* 226:281 */     this.jdbcTemplate.execute(sql);
/* 227:    */   }
/* 228:    */   
/* 229:    */   public TableIndex getIndex(String tableName, String indexName)
/* 230:    */   {
/* 231:288 */     String sql = "SELECT IDX.TABLE_NAME,IDX.TABLE_TYPE,IDX.INDEX_NAME, IDX.INDEX_TYPE,IDX.UNIQUENESS,IDX.STATUS,IDC.COLUMN_NAME,DBMS_METADATA.GET_DDL('INDEX',idx.INDEX_NAME) AS DDL FROM \"SYS\".\"USER_INDEXES\" IDX JOIN \"SYS\".\"USER_IND_COLUMNS\" IDC ON IDX.INDEX_NAME=IDC.INDEX_NAME  WHERE IDX.INDEX_NAME=UPPER('" + indexName + "')";
/* 232:    */     
/* 233:    */ 
/* 234:    */ 
/* 235:    */ 
/* 236:293 */     List<TableIndex> indexes = this.jdbcTemplate.query(sql, new RowMapper()
/* 237:    */     {
/* 238:    */       public TableIndex mapRow(ResultSet rs, int rowNum)
/* 239:    */         throws SQLException
/* 240:    */       {
/* 241:297 */         TableIndex index = new TableIndex();
/* 242:298 */         index.setIndexTable(rs.getString("TABLE_NAME"));
/* 243:299 */         index.setTableType(rs.getString("TABLE_TYPE"));
/* 244:300 */         index.setIndexName(rs.getString("INDEX_NAME"));
/* 245:301 */         index.setIndexType(rs.getString("INDEX_TYPE"));
/* 246:302 */         index.setUnique(rs.getString("UNIQUENESS").equalsIgnoreCase("UNIQUE"));
/* 247:303 */         index.setIndexStatus(rs.getString("STATUS"));
/* 248:304 */         index.setIndexDdl(rs.getString("DDL"));
/* 249:305 */         List<String> indexFields = new ArrayList();
/* 250:306 */         indexFields.add(rs.getString("COLUMN_NAME"));
/* 251:307 */         index.setIndexFields(indexFields);
/* 252:308 */         return index;
/* 253:    */       }
/* 254:313 */     });
/* 255:314 */     List<TableIndex> indexList = mergeIndex(indexes);
/* 256:315 */     if (indexList.size() > 0) {
/* 257:316 */       return dedicatePKIndex((TableIndex)indexList.get(0));
/* 258:    */     }
/* 259:318 */     return null;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public List<TableIndex> getIndexesByTable(String tableName)
/* 263:    */   {
/* 264:323 */     String sql = "SELECT IDX.TABLE_NAME,IDX.TABLE_TYPE,IDX.INDEX_NAME, IDX.INDEX_TYPE,IDX.UNIQUENESS,IDX.STATUS,IDC.COLUMN_NAME,DBMS_METADATA.GET_DDL('INDEX',idx.INDEX_NAME) AS DDL FROM \"SYS\".\"USER_INDEXES\" IDX JOIN \"SYS\".\"USER_IND_COLUMNS\" IDC ON IDX.INDEX_NAME=IDC.INDEX_NAME  WHERE IDX.TABLE_NAME=UPPER('" + tableName + "')";
/* 265:    */     
/* 266:    */ 
/* 267:    */ 
/* 268:    */ 
/* 269:328 */     List<TableIndex> indexes = this.jdbcTemplate.query(sql, new RowMapper()
/* 270:    */     {
/* 271:    */       public TableIndex mapRow(ResultSet rs, int rowNum)
/* 272:    */         throws SQLException
/* 273:    */       {
/* 274:331 */         TableIndex index = new TableIndex();
/* 275:332 */         index.setIndexTable(rs.getString("TABLE_NAME"));
/* 276:333 */         index.setTableType(rs.getString("TABLE_TYPE"));
/* 277:334 */         index.setIndexName(rs.getString("INDEX_NAME"));
/* 278:335 */         index.setIndexType(rs.getString("INDEX_TYPE"));
/* 279:336 */         index.setUnique(rs.getString("UNIQUENESS").equalsIgnoreCase("UNIQUE"));
/* 280:337 */         index.setIndexStatus(rs.getString("STATUS"));
/* 281:338 */         index.setIndexDdl(rs.getString("DDL"));
/* 282:    */         
/* 283:    */ 
/* 284:341 */         List<String> indexFields = new ArrayList();
/* 285:342 */         indexFields.add(rs.getString("COLUMN_NAME"));
/* 286:343 */         index.setIndexFields(indexFields);
/* 287:344 */         return index;
/* 288:    */       }
/* 289:349 */     });
/* 290:350 */     List<TableIndex> indexList = mergeIndex(indexes);
/* 291:    */     
/* 292:352 */     dedicateFKIndex(indexList);
/* 293:353 */     dedicatePKIndex(indexList);
/* 294:354 */     return indexList;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public List<TableIndex> getIndexesByFuzzyMatching(String tableName, String indexName, Boolean getDDL)
/* 298:    */   {
/* 299:359 */     return getIndexesByFuzzyMatching(tableName, indexName, getDDL, null);
/* 300:    */   }
/* 301:    */   
/* 302:    */   public List<TableIndex> getIndexesByFuzzyMatching(String tableName, String indexName, Boolean getDDL, PageBean pageBean)
/* 303:    */   {
/* 304:    */     String sql;
/* 305:    */     String sql;
/* 306:366 */     if (getDDL.booleanValue()) {
/* 307:367 */       sql = "SELECT IDX.TABLE_NAME,IDX.TABLE_TYPE,IDX.INDEX_NAME, IDX.INDEX_TYPE,IDX.UNIQUENESS,IDX.STATUS,IDC.COLUMN_NAME,DBMS_METADATA.GET_DDL('INDEX',idx.INDEX_NAME) AS DDL FROM \"SYS\".\"USER_INDEXES\" IDX JOIN \"SYS\".\"USER_IND_COLUMNS\" IDC ON IDX.INDEX_NAME=IDC.INDEX_NAME WHERE 1=1";
/* 308:    */     } else {
/* 309:373 */       sql = "SELECT IDX.TABLE_NAME,IDX.TABLE_TYPE,IDX.INDEX_NAME, IDX.INDEX_TYPE,IDX.UNIQUENESS,IDX.STATUS,IDC.COLUMN_NAME FROM \"SYS\".\"USER_INDEXES\" IDX JOIN \"SYS\".\"USER_IND_COLUMNS\" IDC ON IDX.INDEX_NAME=IDC.INDEX_NAME WHERE 1=1";
/* 310:    */     }
/* 311:378 */     if (!StringUtil.isEmpty(tableName)) {
/* 312:379 */       sql = sql + " AND UPPER(IDX.TABLE_NAME) LIKE UPPER('%" + tableName + "%')";
/* 313:    */     }
/* 314:382 */     if (!StringUtil.isEmpty(indexName)) {
/* 315:383 */       sql = sql + " AND UPPER(IDX.INDEX_NAME) like UPPER('%" + indexName + "%')";
/* 316:    */     }
/* 317:386 */     if (pageBean != null)
/* 318:    */     {
/* 319:387 */       int currentPage = pageBean.getCurrentPage();
/* 320:388 */       int pageSize = pageBean.getPageSize();
/* 321:389 */       int offset = (currentPage - 1) * pageSize;
/* 322:390 */       String totalSql = this.dialect.getCountSql(sql);
/* 323:391 */       int total = this.jdbcTemplate.queryForInt(totalSql);
/* 324:392 */       sql = this.dialect.getLimitString(sql, offset, pageSize);
/* 325:393 */       pageBean.setTotalCount(total);
/* 326:    */     }
/* 327:396 */     this.logger.debug(sql);
/* 328:    */     
/* 329:398 */     List<TableIndex> indexes = this.jdbcTemplate.query(sql, new RowMapper()
/* 330:    */     {
/* 331:    */       public TableIndex mapRow(ResultSet rs, int rowNum)
/* 332:    */         throws SQLException
/* 333:    */       {
/* 334:402 */         TableIndex index = new TableIndex();
/* 335:403 */         index.setIndexTable(rs.getString("TABLE_NAME"));
/* 336:404 */         index.setTableType(rs.getString("TABLE_TYPE"));
/* 337:405 */         index.setIndexName(rs.getString("INDEX_NAME"));
/* 338:406 */         index.setIndexType(rs.getString("INDEX_TYPE"));
/* 339:407 */         index.setUnique(rs.getString("UNIQUENESS").equalsIgnoreCase("UNIQUE"));
/* 340:408 */         index.setIndexStatus(rs.getString("STATUS"));
/* 341:    */         
/* 342:    */ 
/* 343:411 */         List<String> indexFields = new ArrayList();
/* 344:412 */         indexFields.add(rs.getString("COLUMN_NAME"));
/* 345:413 */         index.setIndexFields(indexFields);
/* 346:414 */         return index;
/* 347:    */       }
/* 348:419 */     });
/* 349:420 */     List<TableIndex> indexList = mergeIndex(indexes);
/* 350:    */     
/* 351:    */ 
/* 352:    */ 
/* 353:424 */     dedicatePKIndex(indexList);
/* 354:425 */     return indexList;
/* 355:    */   }
/* 356:    */   
/* 357:    */   private List<TableIndex> mergeIndex(List<TableIndex> indexes)
/* 358:    */   {
/* 359:431 */     List<TableIndex> indexList = new ArrayList();
/* 360:432 */     for (TableIndex index : indexes)
/* 361:    */     {
/* 362:433 */       boolean found = false;
/* 363:434 */       for (TableIndex index1 : indexList) {
/* 364:435 */         if ((index.getIndexName().equals(index1.getIndexName())) && (index.getIndexTable().equals(index1.getIndexTable())))
/* 365:    */         {
/* 366:437 */           index1.getIndexFields().add(index.getIndexFields().get(0));
/* 367:438 */           found = true;
/* 368:439 */           break;
/* 369:    */         }
/* 370:    */       }
/* 371:442 */       if (!found) {
/* 372:443 */         indexList.add(index);
/* 373:    */       }
/* 374:    */     }
/* 375:446 */     return indexList;
/* 376:    */   }
/* 377:    */   
/* 378:    */   public List<String> getPKColumns(String tableName)
/* 379:    */     throws SQLException
/* 380:    */   {
/* 381:451 */     String sql = "SELECT cols.column_name FROM \"SYS\".\"USER_CONSTRAINTS\" CONS, \"SYS\".\"USER_CONS_COLUMNS\" COLS WHERE UPPER(cols.table_name) = UPPER('" + tableName + "')" + " AND cons.constraint_type = 'P'  AND cols.position=1" + " AND cons.constraint_name = cols.constraint_name" + " AND cons.owner = cols.owner";
/* 382:    */     
/* 383:    */ 
/* 384:    */ 
/* 385:    */ 
/* 386:    */ 
/* 387:457 */     List<String> columns = this.jdbcTemplate.query(sql, new RowMapper()
/* 388:    */     {
/* 389:    */       public String mapRow(ResultSet rs, int rowNum)
/* 390:    */         throws SQLException
/* 391:    */       {
/* 392:460 */         String column = rs.getString(1);
/* 393:461 */         return column;
/* 394:    */       }
/* 395:463 */     });
/* 396:464 */     return columns;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public Map<String, List<String>> getPKColumns(List<String> tableNames)
/* 400:    */     throws SQLException
/* 401:    */   {
/* 402:471 */     StringBuffer sb = new StringBuffer();
/* 403:472 */     for (String name : tableNames)
/* 404:    */     {
/* 405:473 */       sb.append("'");
/* 406:474 */       sb.append(name);
/* 407:475 */       sb.append("',");
/* 408:    */     }
/* 409:477 */     sb.deleteCharAt(sb.length() - 1);
/* 410:    */     
/* 411:479 */     String sql = "SELECT cols.table_name,cols.column_name FROM \"SYS\".\"USER_CONSTRAINTS\" CONS, \"SYS\".\"USER_CONS_COLUMNS\" COLS WHERE UPPER(cols.table_name) in (" + sb.toString().toUpperCase() + ")" + " AND cons.constraint_type = 'P' AND COLS.POSITION=1" + " AND cons.constraint_name = cols.constraint_name" + " AND CONS.OWNER = COLS.OWNER";
/* 412:    */     
/* 413:    */ 
/* 414:    */ 
/* 415:    */ 
/* 416:    */ 
/* 417:    */ 
/* 418:486 */     Map<String, List<String>> columnsMap = new HashMap();
/* 419:    */     
/* 420:488 */     List<Map<String, String>> maps = this.jdbcTemplate.query(sql, new RowMapper()
/* 421:    */     {
/* 422:    */       public Map<String, String> mapRow(ResultSet rs, int rowNum)
/* 423:    */         throws SQLException
/* 424:    */       {
/* 425:492 */         String table = rs.getString(1);
/* 426:493 */         String column = rs.getString(2);
/* 427:494 */         Map<String, String> map = new HashMap();
/* 428:495 */         map.put("name", table);
/* 429:496 */         map.put("column", column);
/* 430:497 */         return map;
/* 431:    */       }
/* 432:    */     });
/* 433:501 */     for (Map<String, String> map : maps) {
/* 434:502 */       if (columnsMap.containsKey(map.get("name")))
/* 435:    */       {
/* 436:503 */         ((List)columnsMap.get(map.get("name"))).add(map.get("column"));
/* 437:    */       }
/* 438:    */       else
/* 439:    */       {
/* 440:505 */         List<String> cols = new ArrayList();
/* 441:506 */         cols.add(map.get("column"));
/* 442:507 */         columnsMap.put(map.get("name"), cols);
/* 443:    */       }
/* 444:    */     }
/* 445:511 */     return columnsMap;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public void setDialect(Dialect dialect)
/* 449:    */   {
/* 450:516 */     this.dialect = dialect;
/* 451:    */   }
/* 452:    */   
/* 453:    */   private Map<String, List<String>> getTablesPKColsByNames(List<String> tableNames)
/* 454:    */   {
/* 455:526 */     Map<String, List<String>> tableMaps = new HashMap();
/* 456:527 */     List<String> names = new ArrayList();
/* 457:528 */     for (int i = 1; i <= tableNames.size(); i++)
/* 458:    */     {
/* 459:529 */       names.add(tableNames.get(i - 1));
/* 460:530 */       if ((i % this.BATCHSIZE == 0) || (i == tableNames.size())) {
/* 461:    */         try
/* 462:    */         {
/* 463:533 */           Map<String, List<String>> map = getPKColumns(names);
/* 464:534 */           tableMaps.putAll(map);
/* 465:535 */           names.clear();
/* 466:    */         }
/* 467:    */         catch (SQLException e)
/* 468:    */         {
/* 469:537 */           e.printStackTrace();
/* 470:    */         }
/* 471:    */       }
/* 472:    */     }
/* 473:541 */     return tableMaps;
/* 474:    */   }
/* 475:    */   
/* 476:    */   private boolean isListEqual(List<?> list1, List<?> list2)
/* 477:    */   {
/* 478:551 */     if (list1 == null)
/* 479:    */     {
/* 480:552 */       if (list2 == null) {
/* 481:553 */         return true;
/* 482:    */       }
/* 483:555 */       return false;
/* 484:    */     }
/* 485:557 */     if (list2 == null) {
/* 486:558 */       return false;
/* 487:    */     }
/* 488:562 */     if (list1.size() != list2.size()) {
/* 489:563 */       return false;
/* 490:    */     }
/* 491:565 */     if (list1.containsAll(list2)) {
/* 492:566 */       return true;
/* 493:    */     }
/* 494:568 */     return false;
/* 495:    */   }
/* 496:    */   
/* 497:    */   private List<TableIndex> dedicatePKIndex(List<TableIndex> indexList)
/* 498:    */   {
/* 499:578 */     List<String> tableNames = new ArrayList();
/* 500:579 */     for (TableIndex index : indexList) {
/* 501:581 */       if (!tableNames.contains(index.getIndexTable())) {
/* 502:582 */         tableNames.add(index.getIndexTable());
/* 503:    */       }
/* 504:    */     }
/* 505:585 */     Map<String, List<String>> tablePKColsMaps = getTablesPKColsByNames(tableNames);
/* 506:586 */     for (TableIndex index : indexList) {
/* 507:587 */       if (isListEqual(index.getIndexFields(), (List)tablePKColsMaps.get(index.getIndexTable()))) {
/* 508:588 */         index.setPkIndex(true);
/* 509:    */       } else {
/* 510:590 */         index.setPkIndex(false);
/* 511:    */       }
/* 512:    */     }
/* 513:594 */     return indexList;
/* 514:    */   }
/* 515:    */   
/* 516:    */   private TableIndex dedicatePKIndex(TableIndex index)
/* 517:    */   {
/* 518:    */     try
/* 519:    */     {
/* 520:605 */       List<String> pkCols = getPKColumns(index.getIndexName());
/* 521:606 */       if (isListEqual(index.getIndexFields(), pkCols)) {
/* 522:607 */         index.setPkIndex(true);
/* 523:    */       } else {
/* 524:609 */         index.setPkIndex(false);
/* 525:    */       }
/* 526:    */     }
/* 527:    */     catch (SQLException e)
/* 528:    */     {
/* 529:612 */       e.printStackTrace();
/* 530:    */     }
/* 531:614 */     return index;
/* 532:    */   }
/* 533:    */   
/* 534:    */   private List<TableIndex> dedicateFKIndex(List<TableIndex> indexList)
/* 535:    */   {
/* 536:623 */     List<String> tableNames = new ArrayList();
/* 537:624 */     for (TableIndex index : indexList) {
/* 538:626 */       if (!tableNames.contains(index.getIndexTable())) {
/* 539:627 */         tableNames.add(index.getIndexTable());
/* 540:    */       }
/* 541:    */     }
/* 542:630 */     Map<String, List<String>> tableFKColsMaps = getTablesFKColsByNames(tableNames);
/* 543:631 */     for (TableIndex index : indexList) {
/* 544:632 */       if (isListEqual(index.getIndexFields(), (List)tableFKColsMaps.get(index.getIndexTable()))) {
/* 545:633 */         indexList.remove(index);
/* 546:    */       }
/* 547:    */     }
/* 548:636 */     return indexList;
/* 549:    */   }
/* 550:    */   
/* 551:    */   private Map<String, List<String>> getTablesFKColsByNames(List<String> tableNames)
/* 552:    */   {
/* 553:641 */     Map<String, List<String>> tableMaps = new HashMap();
/* 554:642 */     List<String> names = new ArrayList();
/* 555:643 */     for (int i = 1; i <= tableNames.size(); i++)
/* 556:    */     {
/* 557:644 */       names.add(tableNames.get(i - 1));
/* 558:645 */       if ((i % this.BATCHSIZE == 0) || (i == tableNames.size())) {
/* 559:    */         try
/* 560:    */         {
/* 561:648 */           Map<String, List<String>> map = getFKColumns(names);
/* 562:649 */           tableMaps.putAll(map);
/* 563:650 */           names.clear();
/* 564:    */         }
/* 565:    */         catch (SQLException e)
/* 566:    */         {
/* 567:652 */           e.printStackTrace();
/* 568:    */         }
/* 569:    */       }
/* 570:    */     }
/* 571:656 */     return tableMaps;
/* 572:    */   }
/* 573:    */   
/* 574:    */   private Map<String, List<String>> getFKColumns(List<String> tableNames)
/* 575:    */     throws SQLException
/* 576:    */   {
/* 577:666 */     StringBuffer sb = new StringBuffer();
/* 578:667 */     for (String name : tableNames)
/* 579:    */     {
/* 580:668 */       sb.append("'");
/* 581:669 */       sb.append(name);
/* 582:670 */       sb.append("',");
/* 583:    */     }
/* 584:672 */     sb.deleteCharAt(sb.length() - 1);
/* 585:    */     
/* 586:674 */     String sql = "SELECT cols.table_name,cols.column_name FROM \"SYS\".\"USER_CONSTRAINTS\" CONS, \"SYS\".\"USER_CONS_COLUMNS\" COLS WHERE UPPER(cols.table_name) in (" + sb.toString().toUpperCase() + ")" + " AND cons.constraint_type = 'F' AND COLS.POSITION=1" + " AND cons.constraint_name = cols.constraint_name" + " AND CONS.OWNER = COLS.OWNER";
/* 587:    */     
/* 588:    */ 
/* 589:    */ 
/* 590:    */ 
/* 591:    */ 
/* 592:    */ 
/* 593:681 */     Map<String, List<String>> columnsMap = new HashMap();
/* 594:    */     
/* 595:683 */     List<Map<String, String>> maps = this.jdbcTemplate.query(sql, new RowMapper()
/* 596:    */     {
/* 597:    */       public Map<String, String> mapRow(ResultSet rs, int rowNum)
/* 598:    */         throws SQLException
/* 599:    */       {
/* 600:686 */         String table = rs.getString(1);
/* 601:687 */         String column = rs.getString(2);
/* 602:688 */         Map<String, String> map = new HashMap();
/* 603:689 */         map.put("name", table);
/* 604:690 */         map.put("column", column);
/* 605:691 */         return map;
/* 606:    */       }
/* 607:    */     });
/* 608:695 */     for (Map<String, String> map : maps) {
/* 609:696 */       if (columnsMap.containsKey(map.get("name")))
/* 610:    */       {
/* 611:697 */         ((List)columnsMap.get(map.get("name"))).add(map.get("column"));
/* 612:    */       }
/* 613:    */       else
/* 614:    */       {
/* 615:699 */         List<String> cols = new ArrayList();
/* 616:700 */         cols.add(map.get("column"));
/* 617:701 */         columnsMap.put(map.get("name"), cols);
/* 618:    */       }
/* 619:    */     }
/* 620:705 */     return columnsMap;
/* 621:    */   }
/* 622:    */   
/* 623:    */   public boolean isTableExist(String tableName)
/* 624:    */   {
/* 625:712 */     StringBuffer sql = new StringBuffer();
/* 626:713 */     sql.append("select COUNT(1) from user_tables t where t.TABLE_NAME='").append(tableName.toUpperCase()).append("'");
/* 627:714 */     return this.jdbcTemplate.queryForInt(sql.toString()) > 0;
/* 628:    */   }
/* 629:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.impl.DmTableOperator
 * JD-Core Version:    0.7.0.1
 */