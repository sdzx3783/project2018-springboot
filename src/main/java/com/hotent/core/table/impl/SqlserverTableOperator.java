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
/*  13:    */ import java.util.List;
/*  14:    */ import org.springframework.jdbc.core.JdbcTemplate;
/*  15:    */ import org.springframework.jdbc.core.RowMapper;
/*  16:    */ 
/*  17:    */ public class SqlserverTableOperator
/*  18:    */   extends AbstractTableOperator
/*  19:    */ {
/*  20:    */   public void createTable(TableModel model)
/*  21:    */     throws SQLException
/*  22:    */   {
/*  23: 26 */     List<ColumnModel> columnList = model.getColumnList();
/*  24:    */     
/*  25:    */ 
/*  26: 29 */     StringBuffer createTableSql = new StringBuffer();
/*  27:    */     
/*  28: 31 */     String pkColumn = null;
/*  29:    */     
/*  30:    */ 
/*  31:    */ 
/*  32: 35 */     List<String> columnCommentList = new ArrayList();
/*  33:    */     
/*  34: 37 */     createTableSql.append("CREATE TABLE " + model.getName() + " (\n");
/*  35: 38 */     for (int i = 0; i < columnList.size(); i++)
/*  36:    */     {
/*  37: 40 */       ColumnModel cm = (ColumnModel)columnList.get(i);
/*  38: 41 */       createTableSql.append("    ").append(cm.getName()).append("    ");
/*  39: 42 */       createTableSql.append(getColumnType(cm.getColumnType(), cm.getCharLen(), cm.getIntLen(), cm.getDecimalLen()));
/*  40:    */       
/*  41: 44 */       createTableSql.append(" ");
/*  42: 47 */       if (StringUtil.isNotEmpty(cm.getDefaultValue())) {
/*  43: 48 */         createTableSql.append(" DEFAULT " + cm.getDefaultValue());
/*  44:    */       }
/*  45: 52 */       if (!cm.getIsNull()) {
/*  46: 53 */         createTableSql.append(" NOT NULL ");
/*  47:    */       }
/*  48: 56 */       if (cm.getIsPk()) {
/*  49: 57 */         if (pkColumn == null) {
/*  50: 58 */           pkColumn = cm.getName();
/*  51:    */         } else {
/*  52: 60 */           pkColumn = pkColumn + "," + cm.getName();
/*  53:    */         }
/*  54:    */       }
/*  55: 69 */       if ((cm.getComment() != null) && (cm.getComment().length() > 0))
/*  56:    */       {
/*  57: 70 */         StringBuffer comment = new StringBuffer("EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'");
/*  58:    */         
/*  59: 72 */         comment.append(cm.getComment()).append("' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'").append(model.getName()).append("', @level2type=N'COLUMN', @level2name=N'").append(cm.getName()).append("'");
/*  60:    */         
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64: 77 */         columnCommentList.add(comment.toString());
/*  65:    */       }
/*  66: 79 */       createTableSql.append(",\n");
/*  67:    */     }
/*  68: 82 */     if (pkColumn != null) {
/*  69: 83 */       createTableSql.append("    CONSTRAINT PK_").append(model.getName()).append(" PRIMARY KEY (").append(pkColumn).append(")");
/*  70:    */     }
/*  71: 92 */     createTableSql.append("\n)");
/*  72: 93 */     this.jdbcTemplate.execute(createTableSql.toString());
/*  73: 96 */     if ((model.getComment() != null) && (model.getComment().length() > 0))
/*  74:    */     {
/*  75: 97 */       StringBuffer tableComment = new StringBuffer("EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'");
/*  76:    */       
/*  77: 99 */       tableComment.append(model.getComment()).append("' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'").append(model.getName()).append("'");
/*  78:    */       
/*  79:    */ 
/*  80:    */ 
/*  81:103 */       this.jdbcTemplate.execute(tableComment.toString());
/*  82:    */     }
/*  83:105 */     for (String columnComment : columnCommentList) {
/*  84:106 */       this.jdbcTemplate.execute(columnComment);
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void updateTableComment(String tableName, String comment)
/*  89:    */     throws SQLException
/*  90:    */   {
/*  91:115 */     StringBuffer commentSql = new StringBuffer("EXEC sys.sp_updateextendedproperty @name=N'MS_Description', @value=N'");
/*  92:    */     
/*  93:117 */     commentSql.append(comment).append("' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'").append(tableName).append("'");
/*  94:    */     
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:122 */     this.jdbcTemplate.execute(commentSql.toString());
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void addColumn(String tableName, ColumnModel model)
/* 102:    */     throws SQLException
/* 103:    */   {
/* 104:129 */     StringBuffer alterSql = new StringBuffer();
/* 105:130 */     alterSql.append("ALTER TABLE ").append(tableName);
/* 106:131 */     alterSql.append(" ADD ");
/* 107:132 */     alterSql.append(model.getName()).append(" ");
/* 108:133 */     alterSql.append(getColumnType(model.getColumnType(), model.getCharLen(), model.getIntLen(), model.getDecimalLen()));
/* 109:136 */     if (StringUtil.isNotEmpty(model.getDefaultValue())) {
/* 110:137 */       alterSql.append(" DEFAULT " + model.getDefaultValue());
/* 111:    */     }
/* 112:143 */     alterSql.append("\n");
/* 113:144 */     this.jdbcTemplate.execute(alterSql.toString());
/* 114:145 */     if ((model.getComment() != null) && (model.getComment().length() > 0))
/* 115:    */     {
/* 116:146 */       StringBuffer comment = new StringBuffer("EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'");
/* 117:    */       
/* 118:148 */       comment.append(model.getComment()).append("' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'").append(tableName).append("', @level2type=N'COLUMN', @level2name=N'").append(model.getName()).append("'");
/* 119:    */       
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:153 */       this.jdbcTemplate.execute(comment.toString());
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void updateColumn(String tableName, String columnName, ColumnModel model)
/* 128:    */     throws SQLException
/* 129:    */   {
/* 130:162 */     if (!columnName.equals(model.getName()))
/* 131:    */     {
/* 132:163 */       StringBuffer modifyName = new StringBuffer("EXEC sp_rename '");
/* 133:164 */       modifyName.append(tableName).append(".[").append(columnName).append("]','").append(model.getName()).append("', 'COLUMN'");
/* 134:    */       
/* 135:    */ 
/* 136:167 */       this.jdbcTemplate.execute(modifyName.toString());
/* 137:    */     }
/* 138:171 */     StringBuffer alterSql = new StringBuffer();
/* 139:172 */     alterSql.append("ALTER TABLE ").append(tableName);
/* 140:173 */     alterSql.append(" ALTER COLUMN " + model.getName()).append(" ");
/* 141:174 */     alterSql.append(getColumnType(model.getColumnType(), model.getCharLen(), model.getIntLen(), model.getDecimalLen()));
/* 142:176 */     if (!model.getIsNull()) {
/* 143:177 */       alterSql.append(" NOT NULL ");
/* 144:    */     }
/* 145:180 */     this.jdbcTemplate.execute(alterSql.toString());
/* 146:183 */     if ((model.getComment() != null) && (model.getComment().length() > 0))
/* 147:    */     {
/* 148:185 */       StringBuffer comment = new StringBuffer("EXEC sys.sp_updateextendedproperty @name=N'MS_Description', @value=N'");
/* 149:    */       
/* 150:187 */       comment.append(model.getComment()).append("' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'").append(tableName).append("', @level2type=N'COLUMN', @level2name=N'").append(model.getName()).append("'");
/* 151:    */       
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:192 */       this.jdbcTemplate.execute(comment.toString());
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   private String getColumnType(String columnType, int charLen, int intLen, int decimalLen)
/* 160:    */   {
/* 161:198 */     if ("varchar".equals(columnType)) {
/* 162:199 */       return "VARCHAR(" + charLen + ')';
/* 163:    */     }
/* 164:200 */     if ("number".equals(columnType)) {
/* 165:201 */       return "NUMERIC(" + (intLen + decimalLen) + "," + decimalLen + ")";
/* 166:    */     }
/* 167:202 */     if ("date".equals(columnType)) {
/* 168:203 */       return "DATETIME";
/* 169:    */     }
/* 170:204 */     if ("int".equals(columnType)) {
/* 171:205 */       return "NUMERIC(" + intLen + ")";
/* 172:    */     }
/* 173:206 */     if ("clob".equals(columnType)) {
/* 174:207 */       return "TEXT";
/* 175:    */     }
/* 176:209 */     return "";
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void dropTable(String tableName)
/* 180:    */   {
/* 181:215 */     String sql = "IF OBJECT_ID(N'" + tableName + "', N'U') IS NOT NULL  DROP TABLE " + tableName;
/* 182:    */     
/* 183:217 */     this.jdbcTemplate.execute(sql);
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void addForeignKey(String pkTableName, String fkTableName, String pkField, String fkField)
/* 187:    */   {
/* 188:225 */     String sql = "  ALTER TABLE " + fkTableName + " ADD CONSTRAINT fk_" + fkTableName + " FOREIGN KEY (" + fkField + ") REFERENCES " + pkTableName + " (" + pkField + ")   ON DELETE CASCADE";
/* 189:    */     
/* 190:    */ 
/* 191:228 */     this.jdbcTemplate.execute(sql);
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void dropForeignKey(String tableName, String keyName)
/* 195:    */   {
/* 196:233 */     String sql = "ALTER   TABLE   " + tableName + "   DROP   CONSTRAINT  " + keyName;
/* 197:    */     
/* 198:235 */     this.jdbcTemplate.execute(sql);
/* 199:    */   }
/* 200:    */   
/* 201:    */   public String getDbType()
/* 202:    */   {
/* 203:241 */     return this.dbType;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void createIndex(TableIndex index)
/* 207:    */     throws SQLException
/* 208:    */   {
/* 209:246 */     String sql = generateIndexDDL(index);
/* 210:247 */     this.jdbcTemplate.execute(sql);
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void dropIndex(String tableName, String indexName)
/* 214:    */   {
/* 215:252 */     String sql = "DROP INDEX " + indexName + " ON " + tableName;
/* 216:253 */     this.jdbcTemplate.execute(sql);
/* 217:    */   }
/* 218:    */   
/* 219:    */   public TableIndex getIndex(String tableName, String indexName)
/* 220:    */   {
/* 221:258 */     String sql = "SELECT IDX.NAME AS INDEX_NAME,IDX.TYPE AS INDEX_TYPE,OBJ.NAME AS TABLE_NAME,OBJ.TYPE AS TABLE_TYPE, IDX.IS_DISABLED AS IS_DISABLED,IDX.IS_UNIQUE AS IS_UNIQUE, IDX.IS_PRIMARY_KEY AS IS_PK_INDEX, COL.NAME AS COLUMN_NAME FROM  SYS.INDEXES  IDX  JOIN SYS.OBJECTS OBJ ON IDX.OBJECT_ID=OBJ.OBJECT_ID  JOIN SYS.INDEX_COLUMNS IDC ON OBJ.OBJECT_ID=IDC.OBJECT_ID AND IDX.INDEX_ID=IDC.INDEX_ID JOIN SYS.COLUMNS COL ON COL.OBJECT_ID=IDC.OBJECT_ID AND COL.COLUMN_ID = IDC.COLUMN_ID WHERE OBJ.NAME ='" + tableName + "' " + "AND IDX.NAME ='" + indexName + "'";
/* 222:    */     
/* 223:    */ 
/* 224:    */ 
/* 225:    */ 
/* 226:    */ 
/* 227:    */ 
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:    */ 
/* 232:269 */     List<TableIndex> indexes = this.jdbcTemplate.query(sql, new RowMapper()
/* 233:    */     {
/* 234:    */       public TableIndex mapRow(ResultSet rs, int rowNum)
/* 235:    */         throws SQLException
/* 236:    */       {
/* 237:275 */         TableIndex index = new TableIndex();
/* 238:276 */         List<String> columns = new ArrayList();
/* 239:277 */         index.setIndexName(rs.getString("INDEX_NAME"));
/* 240:278 */         index.setIndexType(SqlserverTableOperator.this.mapIndexType(rs.getInt("INDEX_TYPE")));
/* 241:279 */         index.setIndexTable(rs.getString("TABLE_NAME"));
/* 242:280 */         index.setTableType(SqlserverTableOperator.this.mapTableType(rs.getString("TABLE_TYPE")));
/* 243:281 */         index.setUnique(SqlserverTableOperator.this.mapIndexUnique(rs.getInt("IS_UNIQUE")));
/* 244:282 */         index.setPkIndex(SqlserverTableOperator.this.mapPKIndex(rs.getInt("IS_PK_INDEX")));
/* 245:283 */         columns.add(rs.getString("COLUMN_NAME"));
/* 246:284 */         index.setIndexStatus(SqlserverTableOperator.this.mapIndexStatus(rs.getInt("IS_DISABLED")));
/* 247:285 */         index.setIndexFields(columns);
/* 248:286 */         return index;
/* 249:    */       }
/* 250:290 */     });
/* 251:291 */     List<TableIndex> indexList = new ArrayList();
/* 252:292 */     for (TableIndex index : indexes)
/* 253:    */     {
/* 254:293 */       for (TableIndex index1 : indexList) {
/* 255:294 */         if ((index.getIndexName().equals(index1.getIndexName())) && (index.getIndexTable().equals(index1.getIndexTable()))) {
/* 256:296 */           index1.getIndexFields().add(index.getIndexFields().get(0));
/* 257:    */         }
/* 258:    */       }
/* 259:300 */       indexList.add(index);
/* 260:    */     }
/* 261:303 */     for (TableIndex index : indexList) {
/* 262:304 */       index.setIndexDdl(generateIndexDDL(index));
/* 263:    */     }
/* 264:307 */     if (indexList.size() > 0) {
/* 265:308 */       return (TableIndex)indexList.get(0);
/* 266:    */     }
/* 267:310 */     return null;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public List<TableIndex> getIndexesByTable(String tableName)
/* 271:    */   {
/* 272:316 */     String sql = "SELECT IDX.NAME AS INDEX_NAME,IDX.TYPE AS INDEX_TYPE,OBJ.NAME AS TABLE_NAME,OBJ.TYPE AS TABLE_TYPE, IDX.IS_DISABLED AS IS_DISABLED,IDX.IS_UNIQUE AS IS_UNIQUE, IDX.IS_PRIMARY_KEY AS IS_PK_INDEX, COL.NAME AS COLUMN_NAME FROM  SYS.INDEXES  IDX  JOIN SYS.OBJECTS OBJ ON IDX.OBJECT_ID=OBJ.OBJECT_ID  JOIN SYS.INDEX_COLUMNS IDC ON OBJ.OBJECT_ID=IDC.OBJECT_ID AND IDX.INDEX_ID=IDC.INDEX_ID JOIN SYS.COLUMNS COL ON COL.OBJECT_ID=IDC.OBJECT_ID AND COL.COLUMN_ID = IDC.COLUMN_ID WHERE OBJ.NAME ='" + tableName + "'";
/* 273:    */     
/* 274:    */ 
/* 275:    */ 
/* 276:    */ 
/* 277:    */ 
/* 278:    */ 
/* 279:    */ 
/* 280:    */ 
/* 281:    */ 
/* 282:326 */     List<TableIndex> indexes = this.jdbcTemplate.query(sql, new RowMapper()
/* 283:    */     {
/* 284:    */       public TableIndex mapRow(ResultSet rs, int rowNum)
/* 285:    */         throws SQLException
/* 286:    */       {
/* 287:332 */         TableIndex index = new TableIndex();
/* 288:333 */         List<String> columns = new ArrayList();
/* 289:334 */         index.setIndexName(rs.getString("INDEX_NAME"));
/* 290:335 */         index.setIndexType(SqlserverTableOperator.this.mapIndexType(rs.getInt("INDEX_TYPE")));
/* 291:336 */         index.setIndexTable(rs.getString("TABLE_NAME"));
/* 292:337 */         index.setTableType(SqlserverTableOperator.this.mapTableType(rs.getString("TABLE_TYPE")));
/* 293:338 */         index.setUnique(SqlserverTableOperator.this.mapIndexUnique(rs.getInt("IS_UNIQUE")));
/* 294:339 */         index.setPkIndex(SqlserverTableOperator.this.mapPKIndex(rs.getInt("IS_PK_INDEX")));
/* 295:340 */         columns.add(rs.getString("COLUMN_NAME"));
/* 296:341 */         index.setIndexStatus(SqlserverTableOperator.this.mapIndexStatus(rs.getInt("IS_DISABLED")));
/* 297:342 */         index.setIndexFields(columns);
/* 298:343 */         return index;
/* 299:    */       }
/* 300:347 */     });
/* 301:348 */     List<TableIndex> indexList = new ArrayList();
/* 302:349 */     for (TableIndex index : indexes)
/* 303:    */     {
/* 304:350 */       boolean found = false;
/* 305:351 */       for (TableIndex index1 : indexList) {
/* 306:352 */         if ((index.getIndexName().equals(index1.getIndexName())) && (index.getIndexTable().equals(index1.getIndexTable())))
/* 307:    */         {
/* 308:354 */           index1.getIndexFields().add(index.getIndexFields().get(0));
/* 309:355 */           found = true;
/* 310:356 */           break;
/* 311:    */         }
/* 312:    */       }
/* 313:359 */       if (!found) {
/* 314:360 */         indexList.add(index);
/* 315:    */       }
/* 316:    */     }
/* 317:364 */     for (TableIndex index : indexList) {
/* 318:365 */       index.setIndexDdl(generateIndexDDL(index));
/* 319:    */     }
/* 320:368 */     return indexList;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public List<TableIndex> getIndexesByFuzzyMatching(String tableName, String indexName, Boolean getDDL)
/* 324:    */   {
/* 325:374 */     return getIndexesByFuzzyMatching(tableName, indexName, getDDL, null);
/* 326:    */   }
/* 327:    */   
/* 328:    */   public List<TableIndex> getIndexesByFuzzyMatching(String tableName, String indexName, Boolean getDDL, PageBean pageBean)
/* 329:    */   {
/* 330:380 */     String sql = "SELECT IDX.NAME AS INDEX_NAME,IDX.TYPE AS INDEX_TYPE,OBJ.NAME AS TABLE_NAME,OBJ.TYPE AS TABLE_TYPE, IDX.IS_DISABLED AS IS_DISABLED,IDX.IS_UNIQUE AS IS_UNIQUE, IDX.IS_PRIMARY_KEY AS IS_PK_INDEX, COL.NAME AS COLUMN_NAME FROM  SYS.INDEXES  IDX  JOIN SYS.OBJECTS OBJ ON IDX.OBJECT_ID=OBJ.OBJECT_ID  JOIN SYS.INDEX_COLUMNS IDC ON OBJ.OBJECT_ID=IDC.OBJECT_ID AND IDX.INDEX_ID=IDC.INDEX_ID JOIN SYS.COLUMNS COL ON COL.OBJECT_ID=IDC.OBJECT_ID AND COL.COLUMN_ID = IDC.COLUMN_ID WHERE 1=1";
/* 331:390 */     if (!StringUtil.isEmpty(indexName)) {
/* 332:391 */       sql = sql + " AND IDX.NAME LIKE '%" + indexName + "%'";
/* 333:    */     }
/* 334:393 */     if (!StringUtil.isEmpty(tableName)) {
/* 335:394 */       sql = sql + " AND OBJ.NAME LIKE '%" + tableName + "%' ";
/* 336:    */     }
/* 337:397 */     if (pageBean != null)
/* 338:    */     {
/* 339:398 */       int currentPage = pageBean.getCurrentPage();
/* 340:399 */       int pageSize = pageBean.getPageSize();
/* 341:400 */       int offset = (currentPage - 1) * pageSize;
/* 342:401 */       String totalSql = this.dialect.getCountSql(sql);
/* 343:402 */       int total = this.jdbcTemplate.queryForInt(totalSql);
/* 344:403 */       sql = this.dialect.getLimitString(sql, offset, pageSize);
/* 345:404 */       pageBean.setTotalCount(total);
/* 346:    */     }
/* 347:407 */     List<TableIndex> indexes = this.jdbcTemplate.query(sql, new RowMapper()
/* 348:    */     {
/* 349:    */       public TableIndex mapRow(ResultSet rs, int rowNum)
/* 350:    */         throws SQLException
/* 351:    */       {
/* 352:412 */         TableIndex index = new TableIndex();
/* 353:413 */         List<String> columns = new ArrayList();
/* 354:414 */         index.setIndexName(rs.getString("INDEX_NAME"));
/* 355:415 */         index.setIndexType(SqlserverTableOperator.this.mapIndexType(rs.getInt("INDEX_TYPE")));
/* 356:416 */         index.setIndexTable(rs.getString("TABLE_NAME"));
/* 357:417 */         index.setTableType(SqlserverTableOperator.this.mapTableType(rs.getString("TABLE_TYPE")));
/* 358:    */         
/* 359:419 */         index.setUnique(SqlserverTableOperator.this.mapIndexUnique(rs.getInt("IS_UNIQUE")));
/* 360:420 */         index.setPkIndex(SqlserverTableOperator.this.mapPKIndex(rs.getInt("IS_PK_INDEX")));
/* 361:421 */         columns.add(rs.getString("COLUMN_NAME"));
/* 362:422 */         index.setIndexFields(columns);
/* 363:423 */         index.setIndexStatus(SqlserverTableOperator.this.mapIndexStatus(rs.getInt("IS_DISABLED")));
/* 364:424 */         return index;
/* 365:    */       }
/* 366:428 */     });
/* 367:429 */     List<TableIndex> indexList = new ArrayList();
/* 368:430 */     for (TableIndex index : indexes)
/* 369:    */     {
/* 370:431 */       for (TableIndex index1 : indexList) {
/* 371:432 */         if ((index.getIndexName().equals(index1.getIndexName())) && (index.getIndexTable().equals(index1.getIndexTable()))) {
/* 372:434 */           index1.getIndexFields().add(index.getIndexFields().get(0));
/* 373:    */         }
/* 374:    */       }
/* 375:438 */       indexList.add(index);
/* 376:    */     }
/* 377:440 */     if (getDDL.booleanValue()) {
/* 378:441 */       for (TableIndex index : indexList) {
/* 379:442 */         index.setIndexDdl(generateIndexDDL(index));
/* 380:    */       }
/* 381:    */     }
/* 382:445 */     return indexList;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public void rebuildIndex(String tableName, String indexName)
/* 386:    */   {
/* 387:450 */     String sql = "DBCC DBREINDEX ('" + tableName + "','" + indexName + "',80)";
/* 388:451 */     this.jdbcTemplate.execute(sql);
/* 389:    */   }
/* 390:    */   
/* 391:    */   public List<String> getPKColumns(String tableName)
/* 392:    */     throws SQLException
/* 393:    */   {
/* 394:457 */     String sql = "SELECT C.COLUMN_NAME COLUMN_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS PK ,INFORMATION_SCHEMA.KEY_COLUMN_USAGE C WHERE \tPK.TABLE_NAME = '%S' AND\tCONSTRAINT_TYPE = 'PRIMARY KEY' AND\tC.TABLE_NAME = PK.TABLE_NAME AND\tC.CONSTRAINT_NAME = PK.CONSTRAINT_NAME ";
/* 395:    */     
/* 396:    */ 
/* 397:    */ 
/* 398:    */ 
/* 399:462 */     sql = String.format(sql, new Object[] { tableName });
/* 400:    */     
/* 401:464 */     List<String> columns = this.jdbcTemplate.query(sql, new RowMapper()
/* 402:    */     {
/* 403:    */       public String mapRow(ResultSet rs, int rowNum)
/* 404:    */         throws SQLException
/* 405:    */       {
/* 406:468 */         String column = rs.getString(1);
/* 407:469 */         return column;
/* 408:    */       }
/* 409:471 */     });
/* 410:472 */     return columns;
/* 411:    */   }
/* 412:    */   
/* 413:    */   private String generateIndexDDL(TableIndex index)
/* 414:    */   {
/* 415:482 */     StringBuffer sql = new StringBuffer();
/* 416:483 */     sql.append("CREATE ");
/* 417:485 */     if (index.isUnique()) {
/* 418:486 */       sql.append(" UNIQUE ");
/* 419:    */     }
/* 420:489 */     if ((!StringUtil.isEmpty(index.getIndexType())) && 
/* 421:490 */       (index.getIndexType().equalsIgnoreCase("CLUSTERED"))) {
/* 422:491 */       sql.append(" CLUSTERED ");
/* 423:    */     }
/* 424:494 */     sql.append(" INDEX ");
/* 425:    */     
/* 426:496 */     sql.append(index.getIndexName());
/* 427:    */     
/* 428:498 */     sql.append(" ON ");
/* 429:499 */     sql.append(index.getIndexTable());
/* 430:    */     
/* 431:501 */     sql.append(" (");
/* 432:502 */     for (String field : index.getIndexFields())
/* 433:    */     {
/* 434:503 */       sql.append(field);
/* 435:504 */       sql.append(",");
/* 436:    */     }
/* 437:506 */     sql.deleteCharAt(sql.length() - 1);
/* 438:507 */     sql.append(")");
/* 439:    */     
/* 440:509 */     return sql.toString();
/* 441:    */   }
/* 442:    */   
/* 443:    */   private String mapTableType(String type)
/* 444:    */   {
/* 445:513 */     type = type.trim();
/* 446:514 */     String tableType = null;
/* 447:515 */     if (type.equalsIgnoreCase("U")) {
/* 448:516 */       tableType = TableIndex.TABLE_TYPE_TABLE;
/* 449:517 */     } else if (type.equalsIgnoreCase("V")) {
/* 450:518 */       tableType = TableIndex.TABLE_TYPE_VIEW;
/* 451:    */     }
/* 452:520 */     return tableType;
/* 453:    */   }
/* 454:    */   
/* 455:    */   private String mapIndexType(int type)
/* 456:    */   {
/* 457:531 */     String indexType = null;
/* 458:532 */     switch (type)
/* 459:    */     {
/* 460:    */     case 0: 
/* 461:534 */       indexType = TableIndex.INDEX_TYPE_HEAP;
/* 462:535 */       break;
/* 463:    */     case 1: 
/* 464:537 */       indexType = TableIndex.INDEX_TYPE_CLUSTERED;
/* 465:538 */       break;
/* 466:    */     case 2: 
/* 467:540 */       indexType = TableIndex.INDEX_TYPE_NONCLUSTERED;
/* 468:541 */       break;
/* 469:    */     case 3: 
/* 470:543 */       indexType = TableIndex.INDEX_TYPE_XML;
/* 471:544 */       break;
/* 472:    */     case 4: 
/* 473:546 */       indexType = TableIndex.INDEX_TYPE_SPATIAL;
/* 474:547 */       break;
/* 475:    */     }
/* 476:551 */     return indexType;
/* 477:    */   }
/* 478:    */   
/* 479:    */   private boolean mapIndexUnique(int type)
/* 480:    */   {
/* 481:555 */     boolean indexUnique = false;
/* 482:556 */     switch (type)
/* 483:    */     {
/* 484:    */     case 0: 
/* 485:558 */       indexUnique = false;
/* 486:559 */       break;
/* 487:    */     case 1: 
/* 488:561 */       indexUnique = true;
/* 489:562 */       break;
/* 490:    */     }
/* 491:566 */     return indexUnique;
/* 492:    */   }
/* 493:    */   
/* 494:    */   private boolean mapPKIndex(int type)
/* 495:    */   {
/* 496:570 */     boolean pkIndex = false;
/* 497:571 */     switch (type)
/* 498:    */     {
/* 499:    */     case 0: 
/* 500:573 */       pkIndex = false;
/* 501:574 */       break;
/* 502:    */     case 1: 
/* 503:576 */       pkIndex = true;
/* 504:577 */       break;
/* 505:    */     }
/* 506:581 */     return pkIndex;
/* 507:    */   }
/* 508:    */   
/* 509:    */   private String mapIndexStatus(int type)
/* 510:    */   {
/* 511:585 */     String tableType = null;
/* 512:586 */     switch (type)
/* 513:    */     {
/* 514:    */     case 0: 
/* 515:588 */       tableType = TableIndex.INDEX_STATUS_VALIDATE;
/* 516:589 */       break;
/* 517:    */     case 1: 
/* 518:591 */       tableType = TableIndex.INDEX_STATUS_INVALIDATE;
/* 519:    */     }
/* 520:594 */     return tableType;
/* 521:    */   }
/* 522:    */   
/* 523:    */   public boolean isTableExist(String tableName)
/* 524:    */   {
/* 525:602 */     String sql = "select count(1) from sysobjects where name='" + tableName.toUpperCase() + "'";
/* 526:603 */     return this.jdbcTemplate.queryForInt(sql) > 0;
/* 527:    */   }
/* 528:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.impl.SqlserverTableOperator
 * JD-Core Version:    0.7.0.1
 */