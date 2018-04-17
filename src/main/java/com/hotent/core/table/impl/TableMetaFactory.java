/*   1:    */ package com.hotent.core.table.impl;
/*   2:    */ 
/*   3:    */ import com.hotent.core.api.system.ISysDataSourceService;
/*   4:    */ import com.hotent.core.api.system.model.ISysDataSource;
/*   5:    */ import com.hotent.core.db.datasource.DbContextHolder;
/*   6:    */ import com.hotent.core.table.BaseTableMeta;
/*   7:    */ import com.hotent.core.table.IDbView;
/*   8:    */ import com.hotent.core.util.AppConfigUtil;
/*   9:    */ import com.hotent.core.util.AppUtil;
/*  10:    */ 
/*  11:    */ public class TableMetaFactory
/*  12:    */ {
/*  13:    */   public static BaseTableMeta getMetaData(String dsName)
/*  14:    */     throws Exception
/*  15:    */   {
/*  16: 38 */     String dbType = getDbTypeBySysDataSourceAlias(dsName);
/*  17:    */     
/*  18: 40 */     BaseTableMeta meta = null;
/*  19: 41 */     if (dbType.equals("oracle")) {
/*  20: 43 */       meta = (BaseTableMeta)AppUtil.getBean(OracleTableMeta.class);
/*  21: 44 */     } else if (dbType.equals("mysql")) {
/*  22: 46 */       meta = (BaseTableMeta)AppUtil.getBean(MySqlTableMeta.class);
/*  23: 47 */     } else if (dbType.equals("mssql")) {
/*  24: 49 */       meta = (BaseTableMeta)AppUtil.getBean(SqlServerTableMeta.class);
/*  25: 50 */     } else if (dbType.equals("db2")) {
/*  26: 52 */       meta = (BaseTableMeta)AppUtil.getBean(Db2TableMeta.class);
/*  27: 53 */     } else if (dbType.equals("h2")) {
/*  28: 55 */       meta = (BaseTableMeta)AppUtil.getBean(H2TableMeta.class);
/*  29: 56 */     } else if (dbType.equals("dm")) {
/*  30: 58 */       meta = (BaseTableMeta)AppUtil.getBean(DmTableMeta.class);
/*  31:    */     } else {
/*  32: 60 */       throw new Exception("未知的数据库类型");
/*  33:    */     }
/*  34: 63 */     DbContextHolder.setDataSource(dsName);
/*  35: 64 */     return meta;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static IDbView getDbView(String dsName)
/*  39:    */     throws Exception
/*  40:    */   {
/*  41: 80 */     String dbType = getDbTypeBySysDataSourceAlias(dsName);
/*  42:    */     
/*  43: 82 */     IDbView meta = null;
/*  44: 83 */     if (dbType.equals("oracle")) {
/*  45: 85 */       meta = (IDbView)AppUtil.getBean(OracleDbView.class);
/*  46: 86 */     } else if (dbType.equals("mssql")) {
/*  47: 87 */       meta = (IDbView)AppUtil.getBean(SqlserverDbView.class);
/*  48: 88 */     } else if (dbType.equals("mysql")) {
/*  49: 90 */       meta = (IDbView)AppUtil.getBean(MysqlDbView.class);
/*  50: 91 */     } else if (dbType.equals("db2")) {
/*  51: 93 */       meta = (IDbView)AppUtil.getBean(Db2DbView.class);
/*  52: 94 */     } else if (dbType.equals("h2")) {
/*  53: 96 */       meta = (IDbView)AppUtil.getBean(H2DbView.class);
/*  54: 97 */     } else if (dbType.equals("dm")) {
/*  55: 99 */       meta = (IDbView)AppUtil.getBean(DmDbView.class);
/*  56:    */     } else {
/*  57:101 */       throw new Exception("未知的数据库类型");
/*  58:    */     }
/*  59:103 */     DbContextHolder.setDataSource(dsName);
/*  60:104 */     return meta;
/*  61:    */   }
/*  62:    */   
/*  63:    */   private static String getDbTypeBySysDataSourceAlias(String alias)
/*  64:    */   {
/*  65:119 */     ISysDataSource sysDataSource = null;
/*  66:120 */     ISysDataSourceService sysDataSourceService = (ISysDataSourceService)AppUtil.getBean(ISysDataSourceService.class);
/*  67:121 */     sysDataSource = sysDataSourceService.getByAlias(alias);
/*  68:    */     
/*  69:123 */     String dbType = AppConfigUtil.get("jdbc.dbType");
/*  70:124 */     if (sysDataSource != null) {
/*  71:125 */       dbType = sysDataSource.getDbType();
/*  72:    */     }
/*  73:128 */     return dbType;
/*  74:    */   }
/*  75:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.impl.TableMetaFactory
 * JD-Core Version:    0.7.0.1
 */