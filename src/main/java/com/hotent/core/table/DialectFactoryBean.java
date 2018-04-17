/*   1:    */ package com.hotent.core.table;
/*   2:    */ 
/*   3:    */ import com.hotent.core.mybatis.Dialect;
/*   4:    */ import com.hotent.core.mybatis.dialect.DB2Dialect;
/*   5:    */ import com.hotent.core.mybatis.dialect.DmDialect;
/*   6:    */ import com.hotent.core.mybatis.dialect.H2Dialect;
/*   7:    */ import com.hotent.core.mybatis.dialect.MySQLDialect;
/*   8:    */ import com.hotent.core.mybatis.dialect.OracleDialect;
/*   9:    */ import com.hotent.core.mybatis.dialect.SQLServer2005Dialect;
/*  10:    */ import org.springframework.beans.factory.FactoryBean;
/*  11:    */ 
/*  12:    */ public class DialectFactoryBean
/*  13:    */   implements FactoryBean<Dialect>
/*  14:    */ {
/*  15:    */   private Dialect dialect;
/*  16:    */   
/*  17:    */   public void setDbType(String dbType)
/*  18:    */   {
/*  19: 37 */     this.dbType = dbType;
/*  20:    */   }
/*  21:    */   
/*  22: 40 */   private String dbType = "mysql";
/*  23:    */   
/*  24:    */   public Dialect getObject()
/*  25:    */     throws Exception
/*  26:    */   {
/*  27: 44 */     this.dialect = getDialect(this.dbType);
/*  28: 45 */     return this.dialect;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static Dialect getDialect(String dbType)
/*  32:    */     throws Exception
/*  33:    */   {
/*  34: 56 */     Dialect dialect = null;
/*  35: 57 */     if (dbType.equals("oracle")) {
/*  36: 58 */       dialect = new OracleDialect();
/*  37: 59 */     } else if (dbType.equals("mssql")) {
/*  38: 60 */       dialect = new SQLServer2005Dialect();
/*  39: 61 */     } else if (dbType.equals("db2")) {
/*  40: 62 */       dialect = new DB2Dialect();
/*  41: 63 */     } else if (dbType.equals("mysql")) {
/*  42: 64 */       dialect = new MySQLDialect();
/*  43: 65 */     } else if (dbType.equals("h2")) {
/*  44: 66 */       dialect = new H2Dialect();
/*  45: 67 */     } else if (dbType.equals("dm")) {
/*  46: 68 */       dialect = new DmDialect();
/*  47:    */     } else {
/*  48: 70 */       throw new Exception("没有设置合适的数据库类型");
/*  49:    */     }
/*  50: 72 */     return dialect;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static Dialect getDialectByDriverClassName(String driverClassName)
/*  54:    */     throws Exception
/*  55:    */   {
/*  56: 83 */     Dialect dialect = null;
/*  57: 84 */     if (driverClassName.contains("oracle")) {
/*  58: 85 */       dialect = new OracleDialect();
/*  59: 86 */     } else if (driverClassName.equals("sqlserver")) {
/*  60: 87 */       dialect = new SQLServer2005Dialect();
/*  61: 88 */     } else if (driverClassName.equals("db2")) {
/*  62: 89 */       dialect = new DB2Dialect();
/*  63: 90 */     } else if (driverClassName.equals("mysql")) {
/*  64: 91 */       dialect = new MySQLDialect();
/*  65: 92 */     } else if (driverClassName.equals("h2")) {
/*  66: 93 */       dialect = new H2Dialect();
/*  67: 94 */     } else if (driverClassName.equals("dm")) {
/*  68: 95 */       dialect = new DmDialect();
/*  69:    */     } else {
/*  70: 97 */       throw new Exception("没有设置合适的数据库类型");
/*  71:    */     }
/*  72: 99 */     return dialect;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Class<?> getObjectType()
/*  76:    */   {
/*  77:104 */     return Dialect.class;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean isSingleton()
/*  81:    */   {
/*  82:109 */     return true;
/*  83:    */   }
/*  84:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.DialectFactoryBean
 * JD-Core Version:    0.7.0.1
 */