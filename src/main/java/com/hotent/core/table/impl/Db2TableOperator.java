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
/*  19:    */ public class Db2TableOperator
/*  20:    */   extends AbstractTableOperator
/*  21:    */ {
/*  22: 26 */   protected int BATCHSIZE = 100;
/*  23: 29 */   private final String SQL_GET_ALL_INDEX = "SELECT A.TABNAME, A.INDNAME, B.COLNAME, A.UNIQUERULE, A.COLCOUNT, A.INDEXTYPE, A.REMARKS FROM SYSCAT.INDEXES A JOIN SYSCAT.INDEXCOLUSE B ON A.INDNAME=B.INDNAME WHERE 1=1 ";
/*  24:    */   
/*  25:    */   public void createTable(TableModel model)
/*  26:    */     throws SQLException
/*  27:    */   {
/*  28: 47 */     List<ColumnModel> columnList = model.getColumnList();
/*  29:    */     
/*  30: 49 */     StringBuffer sb = new StringBuffer();
/*  31:    */     
/*  32: 51 */     String pkColumn = null;
/*  33:    */     
/*  34: 53 */     List<String> columnCommentList = new ArrayList();
/*  35:    */     
/*  36: 55 */     sb.append("CREATE TABLE " + model.getName() + " (\n");
/*  37: 56 */     for (int i = 0; i < columnList.size(); i++)
/*  38:    */     {
/*  39: 58 */       ColumnModel cm = (ColumnModel)columnList.get(i);
/*  40: 59 */       sb.append("    ").append(cm.getName()).append("    ");
/*  41: 60 */       sb.append(getColumnType(cm.getColumnType(), cm.getCharLen(), cm.getIntLen(), cm.getDecimalLen()));
/*  42: 61 */       sb.append(" ");
/*  43: 63 */       if (cm.getIsPk()) {
/*  44: 64 */         if (pkColumn == null) {
/*  45: 65 */           pkColumn = cm.getName();
/*  46:    */         } else {
/*  47: 67 */           pkColumn = pkColumn + "," + cm.getName();
/*  48:    */         }
/*  49:    */       }
/*  50: 71 */       String defVal = getDefaultValueSQL(cm);
/*  51: 72 */       if (StringUtil.isNotEmpty(defVal)) {
/*  52: 73 */         sb.append(defVal);
/*  53:    */       }
/*  54: 77 */       if ((!cm.getIsNull()) || (cm.getIsPk())) {
/*  55: 78 */         sb.append(" NOT NULL ");
/*  56:    */       }
/*  57: 82 */       if ((cm.getComment() != null) && (cm.getComment().length() > 0)) {
/*  58: 84 */         columnCommentList.add("COMMENT ON COLUMN " + model.getName() + "." + cm.getName() + " IS '" + cm.getComment() + "'\n");
/*  59:    */       }
/*  60: 86 */       sb.append(",\n");
/*  61:    */     }
/*  62: 89 */     if (pkColumn != null) {
/*  63: 90 */       sb.append("    CONSTRAINT PK_").append(model.getName()).append(" PRIMARY KEY (").append(pkColumn).append(")");
/*  64:    */     } else {
/*  65: 92 */       sb = new StringBuffer(sb.substring(0, sb.length() - ",\n".length()));
/*  66:    */     }
/*  67: 96 */     sb.append("\n)");
/*  68:    */     
/*  69: 98 */     this.jdbcTemplate.execute(sb.toString());
/*  70: 99 */     if ((model.getComment() != null) && (model.getComment().length() > 0)) {
/*  71:100 */       this.jdbcTemplate.execute("COMMENT ON TABLE " + model.getName() + " IS '" + model.getComment() + "'\n");
/*  72:    */     }
/*  73:102 */     for (String columnComment : columnCommentList) {
/*  74:103 */       this.jdbcTemplate.execute(columnComment);
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void updateTableComment(String tableName, String comment)
/*  79:    */     throws SQLException
/*  80:    */   {
/*  81:109 */     StringBuffer sb = new StringBuffer();
/*  82:110 */     sb.append("COMMENT ON TABLE ");
/*  83:111 */     sb.append(tableName);
/*  84:112 */     sb.append(" IS '");
/*  85:113 */     sb.append(comment);
/*  86:114 */     sb.append("'\n");
/*  87:115 */     this.jdbcTemplate.execute(sb.toString());
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void addColumn(String tableName, ColumnModel model)
/*  91:    */     throws SQLException
/*  92:    */   {
/*  93:120 */     StringBuffer sb = new StringBuffer();
/*  94:121 */     sb.append("ALTER TABLE ").append(tableName);
/*  95:122 */     sb.append(" ADD ");
/*  96:123 */     sb.append(model.getName()).append(" ");
/*  97:124 */     sb.append(getColumnType(model.getColumnType(), model.getCharLen(), model.getIntLen(), model.getDecimalLen()));
/*  98:    */     
/*  99:    */ 
/* 100:    */ 
/* 101:128 */     String defVal = getDefaultValueSQL(model);
/* 102:129 */     if (StringUtil.isNotEmpty(defVal)) {
/* 103:130 */       sb.append(defVal);
/* 104:    */     }
/* 105:136 */     sb.append("\n");
/* 106:137 */     this.jdbcTemplate.execute(sb.toString());
/* 107:138 */     if ((model.getComment() != null) && (model.getComment().length() > 0)) {
/* 108:139 */       this.jdbcTemplate.execute("COMMENT ON COLUMN " + tableName + "." + model.getName() + " IS '" + model.getComment() + "'");
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void updateColumn(String tableName, String columnName, ColumnModel model)
/* 113:    */     throws SQLException
/* 114:    */   {
/* 115:151 */     if (!columnName.equalsIgnoreCase(model.getName()))
/* 116:    */     {
/* 117:153 */       StringBuffer addColumn = new StringBuffer();
/* 118:154 */       addColumn.append("alter table ");
/* 119:155 */       addColumn.append(tableName);
/* 120:156 */       addColumn.append(" add column ");
/* 121:157 */       addColumn.append("    ").append(model.getName()).append("    ");
/* 122:158 */       addColumn.append(getColumnType(model.getColumnType(), model.getCharLen(), model.getIntLen(), model.getDecimalLen()));
/* 123:159 */       addColumn.append(" ");
/* 124:    */       
/* 125:161 */       String defVal = getDefaultValueSQL(model);
/* 126:162 */       if (StringUtil.isNotEmpty(defVal)) {
/* 127:163 */         addColumn.append(defVal);
/* 128:    */       }
/* 129:165 */       this.jdbcTemplate.execute(addColumn.toString());
/* 130:    */       
/* 131:167 */       String copyValue = "update table " + tableName + " set " + model.getName() + "=" + columnName;
/* 132:168 */       this.jdbcTemplate.execute(copyValue);
/* 133:    */       
/* 134:170 */       String dropColumn = "alter table " + tableName + " drop column " + columnName;
/* 135:171 */       this.jdbcTemplate.execute(dropColumn);
/* 136:    */     }
/* 137:176 */     StringBuffer sb = new StringBuffer();
/* 138:    */     
/* 139:178 */     sb.append("ALTER TABLE ").append(tableName);
/* 140:179 */     sb.append("  ALTER " + model.getName()).append(" ");
/* 141:180 */     sb.append(" SET\tDATA TYPE ");
/* 142:181 */     sb.append(getColumnType(model.getColumnType(), model.getCharLen(), model.getIntLen(), model.getDecimalLen()));
/* 143:    */     
/* 144:183 */     this.jdbcTemplate.execute(sb.toString());
/* 145:186 */     if (!model.getIsNull())
/* 146:    */     {
/* 147:187 */       String nullable = "ALTER TABLE " + tableName + " ALTER " + model.getName() + " DROP NOT NULL";
/* 148:188 */       this.jdbcTemplate.execute(nullable);
/* 149:    */     }
/* 150:    */     else
/* 151:    */     {
/* 152:190 */       String notnull = "ALTER TABLE " + tableName + " ALTER " + model.getName() + " SET NOT NULL";
/* 153:191 */       this.jdbcTemplate.execute(notnull);
/* 154:    */     }
/* 155:195 */     if ((model.getComment() != null) && (model.getComment().length() > 0)) {
/* 156:196 */       this.jdbcTemplate.execute("COMMENT ON COLUMN " + tableName + "." + model.getName() + " IS'" + model.getComment() + "'");
/* 157:    */     }
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void dropTable(String tableName)
/* 161:    */   {
/* 162:202 */     String selSql = "SELECT COUNT(*) AMOUNT FROM SYSCAT.TABLES  WHERE TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) AND TABNAME = UPPER('" + tableName + "')";
/* 163:    */     
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:    */ 
/* 168:208 */     int rtn = this.jdbcTemplate.queryForInt(selSql);
/* 169:209 */     if (rtn > 0)
/* 170:    */     {
/* 171:210 */       String sql = "drop table " + tableName;
/* 172:211 */       this.jdbcTemplate.execute(sql);
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void addForeignKey(String pkTableName, String fkTableName, String pkField, String fkField)
/* 177:    */   {
/* 178:217 */     String sql = " ALTER TABLE " + fkTableName + " ADD CONSTRAINT FK_" + fkTableName + " FOREIGN KEY (" + fkField + ") REFERENCES " + pkTableName + " (" + pkField + ") ON DELETE CASCADE";
/* 179:218 */     this.jdbcTemplate.execute(sql);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void dropForeignKey(String tableName, String keyName)
/* 183:    */   {
/* 184:223 */     String sql = "ALTER   TABLE   " + tableName + "   DROP   CONSTRAINT  " + keyName;
/* 185:224 */     this.jdbcTemplate.execute(sql);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void dropIndex(String tableName, String indexName)
/* 189:    */   {
/* 190:230 */     String sql = "DROP INDEX " + indexName;
/* 191:231 */     this.jdbcTemplate.execute(sql);
/* 192:    */   }
/* 193:    */   
/* 194:    */   public TableIndex getIndex(String tableName, String indexName)
/* 195:    */   {
/* 196:236 */     String sql = "SELECT A.TABNAME, A.INDNAME, B.COLNAME, A.UNIQUERULE, A.COLCOUNT, A.INDEXTYPE, A.REMARKS FROM SYSCAT.INDEXES A JOIN SYSCAT.INDEXCOLUSE B ON A.INDNAME=B.INDNAME WHERE 1=1 ";
/* 197:237 */     sql = sql + " AND A.INDNAME = '" + indexName + "' ";
/* 198:238 */     List<TableIndex> indexes = this.jdbcTemplate.query(sql, this.indexRowMapper);
/* 199:239 */     List<TableIndex> indexList = mergeIndex(indexes);
/* 200:240 */     if (BeanUtils.isEmpty(indexList)) {
/* 201:241 */       return null;
/* 202:    */     }
/* 203:243 */     TableIndex index = (TableIndex)indexList.get(0);
/* 204:244 */     index.setIndexDdl(generateIndexDDL(index));
/* 205:245 */     return index;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public List<TableIndex> getIndexesByTable(String tableName)
/* 209:    */   {
/* 210:251 */     String sql = "SELECT A.TABNAME, A.INDNAME, B.COLNAME, A.UNIQUERULE, A.COLCOUNT, A.INDEXTYPE, A.REMARKS FROM SYSCAT.INDEXES A JOIN SYSCAT.INDEXCOLUSE B ON A.INDNAME=B.INDNAME WHERE 1=1 ";
/* 211:252 */     sql = sql + " AND UPPER(A.TABNAME) = UPPER('" + tableName + "')";
/* 212:253 */     List<TableIndex> indexes = this.jdbcTemplate.query(sql, this.indexRowMapper);
/* 213:    */     
/* 214:255 */     List<TableIndex> indexList = mergeIndex(indexes);
/* 215:256 */     for (TableIndex index : indexList) {
/* 216:257 */       index.setIndexDdl(generateIndexDDL(index));
/* 217:    */     }
/* 218:259 */     return indexList;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public List<TableIndex> getIndexesByFuzzyMatching(String tableName, String indexName, Boolean getDDL)
/* 222:    */   {
/* 223:264 */     return getIndexesByFuzzyMatching(tableName, indexName, getDDL, null);
/* 224:    */   }
/* 225:    */   
/* 226:    */   public List<TableIndex> getIndexesByFuzzyMatching(String tableName, String indexName, Boolean getDDL, PageBean pageBean)
/* 227:    */   {
/* 228:269 */     String sql = "SELECT A.TABNAME, A.INDNAME, B.COLNAME, A.UNIQUERULE, A.COLCOUNT, A.INDEXTYPE, A.REMARKS FROM SYSCAT.INDEXES A JOIN SYSCAT.INDEXCOLUSE B ON A.INDNAME=B.INDNAME WHERE 1=1 ";
/* 229:271 */     if (!StringUtil.isEmpty(tableName)) {
/* 230:272 */       sql = sql + " AND UPPER(A.TABNAME) LIKE UPPER('%" + tableName + "%')";
/* 231:    */     }
/* 232:275 */     if (!StringUtil.isEmpty(indexName)) {
/* 233:276 */       sql = sql + " AND UPPER(A.INDNAME) like UPPER('%" + indexName + "%')";
/* 234:    */     }
/* 235:279 */     if (pageBean != null)
/* 236:    */     {
/* 237:280 */       int currentPage = pageBean.getCurrentPage();
/* 238:281 */       int pageSize = pageBean.getPageSize();
/* 239:282 */       int offset = (currentPage - 1) * pageSize;
/* 240:283 */       String totalSql = this.dialect.getCountSql(sql);
/* 241:284 */       int total = this.jdbcTemplate.queryForInt(totalSql);
/* 242:285 */       sql = this.dialect.getLimitString(sql, offset, pageSize);
/* 243:286 */       pageBean.setTotalCount(total);
/* 244:    */     }
/* 245:288 */     this.logger.debug(sql);
/* 246:289 */     List<TableIndex> indexes = this.jdbcTemplate.query(sql, this.indexRowMapper);
/* 247:    */     
/* 248:291 */     List<TableIndex> indexList = mergeIndex(indexes);
/* 249:293 */     for (TableIndex index : indexList) {
/* 250:294 */       index.setIndexDdl(generateIndexDDL(index));
/* 251:    */     }
/* 252:296 */     return indexList;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void rebuildIndex(String tableName, String indexName)
/* 256:    */   {
/* 257:307 */     throw new UnsupportedOperationException("DB2 不支持通过JDBC进行索引重建！");
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void createIndex(TableIndex index)
/* 261:    */     throws SQLException
/* 262:    */   {
/* 263:312 */     String sql = generateIndexDDL(index);
/* 264:313 */     this.jdbcTemplate.execute(sql);
/* 265:    */   }
/* 266:    */   
/* 267:    */   private List<TableIndex> mergeIndex(List<TableIndex> indexes)
/* 268:    */   {
/* 269:317 */     List<TableIndex> indexList = new ArrayList();
/* 270:318 */     for (TableIndex index : indexes)
/* 271:    */     {
/* 272:319 */       boolean found = false;
/* 273:320 */       for (TableIndex index1 : indexList) {
/* 274:321 */         if ((index.getIndexName().equals(index1.getIndexName())) && (index.getIndexTable().equals(index1.getIndexTable())))
/* 275:    */         {
/* 276:323 */           index1.getIndexFields().add(index.getIndexFields().get(0));
/* 277:324 */           found = true;
/* 278:325 */           break;
/* 279:    */         }
/* 280:    */       }
/* 281:328 */       if (!found) {
/* 282:329 */         indexList.add(index);
/* 283:    */       }
/* 284:    */     }
/* 285:332 */     return indexList;
/* 286:    */   }
/* 287:    */   
/* 288:    */   private String generateIndexDDL(TableIndex index)
/* 289:    */   {
/* 290:341 */     StringBuffer sql = new StringBuffer();
/* 291:342 */     sql.append("CREATE ");
/* 292:343 */     sql.append("INDEX ");
/* 293:344 */     sql.append(index.getIndexName());
/* 294:345 */     sql.append(" ON ");
/* 295:346 */     sql.append(index.getIndexTable());
/* 296:347 */     sql.append("(");
/* 297:348 */     for (String field : index.getIndexFields())
/* 298:    */     {
/* 299:349 */       sql.append(field);
/* 300:350 */       sql.append(",");
/* 301:    */     }
/* 302:352 */     sql.deleteCharAt(sql.length() - 1);
/* 303:353 */     sql.append(")");
/* 304:355 */     if ((!StringUtil.isEmpty(index.getIndexType())) && 
/* 305:356 */       (TableIndex.INDEX_TYPE_CLUSTERED.equalsIgnoreCase(index.getIndexType()))) {
/* 306:357 */       sql.append(" CLUSTER ");
/* 307:    */     }
/* 308:361 */     return sql.toString();
/* 309:    */   }
/* 310:    */   
/* 311:    */   private String getColumnType(String columnType, int charLen, int intLen, int decimalLen)
/* 312:    */   {
/* 313:378 */     if ("varchar".equals(columnType)) {
/* 314:379 */       return "VARCHAR(" + charLen + ')';
/* 315:    */     }
/* 316:380 */     if ("number".equals(columnType)) {
/* 317:381 */       return "DECIMAL(" + (intLen + decimalLen) + "," + decimalLen + ")";
/* 318:    */     }
/* 319:382 */     if ("date".equals(columnType)) {
/* 320:383 */       return "DATE";
/* 321:    */     }
/* 322:384 */     if ("int".equals(columnType))
/* 323:    */     {
/* 324:385 */       if ((intLen > 0) && (intLen <= 5)) {
/* 325:386 */         return "SMALLINT";
/* 326:    */       }
/* 327:387 */       if ((intLen > 5) && (intLen <= 10)) {
/* 328:388 */         return "INTEGER";
/* 329:    */       }
/* 330:390 */       return "BIGINT";
/* 331:    */     }
/* 332:392 */     if ("clob".equals(columnType)) {
/* 333:393 */       return "CLOB";
/* 334:    */     }
/* 335:395 */     return "VARCHAR(50)";
/* 336:    */   }
/* 337:    */   
/* 338:    */   private String getDefaultValueSQL(ColumnModel cm)
/* 339:    */   {
/* 340:400 */     String sql = null;
/* 341:401 */     if (StringUtil.isNotEmpty(cm.getDefaultValue())) {
/* 342:402 */       if ("int".equalsIgnoreCase(cm.getColumnType())) {
/* 343:403 */         sql = " DEFAULT " + cm.getDefaultValue() + " ";
/* 344:404 */       } else if ("number".equalsIgnoreCase(cm.getColumnType())) {
/* 345:405 */         sql = " DEFAULT " + cm.getDefaultValue() + " ";
/* 346:406 */       } else if ("varchar".equalsIgnoreCase(cm.getColumnType())) {
/* 347:407 */         sql = " DEFAULT '" + cm.getDefaultValue() + "' ";
/* 348:408 */       } else if ("clob".equalsIgnoreCase(cm.getColumnType())) {
/* 349:409 */         sql = " DEFAULT '" + cm.getDefaultValue() + "' ";
/* 350:410 */       } else if ("date".equalsIgnoreCase(cm.getColumnType())) {
/* 351:411 */         sql = " DEFAULT " + cm.getDefaultValue() + " ";
/* 352:    */       } else {
/* 353:413 */         sql = " DEFAULT " + cm.getDefaultValue() + " ";
/* 354:    */       }
/* 355:    */     }
/* 356:416 */     return sql;
/* 357:    */   }
/* 358:    */   
/* 359:419 */   private RowMapper<TableIndex> indexRowMapper = new RowMapper()
/* 360:    */   {
/* 361:    */     public TableIndex mapRow(ResultSet rs, int rowNum)
/* 362:    */       throws SQLException
/* 363:    */     {
/* 364:422 */       TableIndex index = new TableIndex();
/* 365:423 */       index.setIndexTable(rs.getString("TABNAME"));
/* 366:424 */       index.setTableType(TableIndex.TABLE_TYPE_TABLE);
/* 367:425 */       index.setIndexName(rs.getString("INDNAME"));
/* 368:    */       
/* 369:427 */       String uniqueRule = rs.getString("UNIQUERULE").trim();
/* 370:428 */       if (("U".equalsIgnoreCase(uniqueRule)) || ("P".equalsIgnoreCase(uniqueRule))) {
/* 371:429 */         index.setUnique(true);
/* 372:    */       }
/* 373:432 */       if ("P".equalsIgnoreCase(uniqueRule)) {
/* 374:433 */         index.setPkIndex(true);
/* 375:    */       }
/* 376:436 */       String indexType = rs.getString("INDEXTYPE").trim();
/* 377:437 */       if ("CLUS".equalsIgnoreCase(indexType)) {
/* 378:438 */         index.setIndexType(TableIndex.INDEX_TYPE_CLUSTERED);
/* 379:439 */       } else if ("REG".equalsIgnoreCase(indexType)) {
/* 380:440 */         index.setIndexType(TableIndex.INDEX_TYPE_REG);
/* 381:441 */       } else if ("DIM".equalsIgnoreCase(indexType)) {
/* 382:442 */         index.setIndexType(TableIndex.INDEX_TYPE_DIM);
/* 383:443 */       } else if ("BLOK".equalsIgnoreCase(indexType)) {
/* 384:444 */         index.setIndexType(TableIndex.INDEX_TYPE_BLOK);
/* 385:    */       }
/* 386:446 */       index.setIndexComment(rs.getString("REMARKS"));
/* 387:447 */       List<String> indexFields = new ArrayList();
/* 388:448 */       indexFields.add(rs.getString("COLNAME"));
/* 389:449 */       index.setIndexFields(indexFields);
/* 390:    */       
/* 391:451 */       index.setIndexDdl(Db2TableOperator.this.generateIndexDDL(index));
/* 392:452 */       return index;
/* 393:    */     }
/* 394:    */   };
/* 395:    */   
/* 396:    */   public boolean isTableExist(String tableName)
/* 397:    */   {
/* 398:461 */     String selSql = "SELECT COUNT(*) AMOUNT FROM SYSCAT.TABLES  WHERE TABSCHEMA IN (SELECT CURRENT SQLID FROM SYSIBM.DUAL) AND TABNAME = UPPER('" + tableName + "')";
/* 399:    */     
/* 400:    */ 
/* 401:    */ 
/* 402:    */ 
/* 403:    */ 
/* 404:467 */     int rtn = this.jdbcTemplate.queryForInt(selSql);
/* 405:468 */     return rtn > 0;
/* 406:    */   }
/* 407:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.impl.Db2TableOperator
 * JD-Core Version:    0.7.0.1
 */