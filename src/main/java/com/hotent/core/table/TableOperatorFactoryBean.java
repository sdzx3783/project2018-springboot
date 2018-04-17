/*   1:    */ package com.hotent.core.table;
/*   2:    */ 
/*   3:    */ import com.hotent.core.mybatis.Dialect;
/*   4:    */ import com.hotent.core.table.impl.Db2TableOperator;
/*   5:    */ import com.hotent.core.table.impl.DmTableOperator;
/*   6:    */ import com.hotent.core.table.impl.H2TableOperator;
/*   7:    */ import com.hotent.core.table.impl.MysqlTableOperator;
/*   8:    */ import com.hotent.core.table.impl.OracleTableOperator;
/*   9:    */ import com.hotent.core.table.impl.SqlserverTableOperator;
/*  10:    */ import org.springframework.beans.factory.FactoryBean;
/*  11:    */ import org.springframework.jdbc.core.JdbcTemplate;
/*  12:    */ 
/*  13:    */ public class TableOperatorFactoryBean
/*  14:    */   implements FactoryBean<ITableOperator>
/*  15:    */ {
/*  16:    */   private ITableOperator tableOperator;
/*  17: 33 */   private String dbType = "mysql";
/*  18:    */   private JdbcTemplate jdbcTemplate;
/*  19:    */   private Dialect dialect;
/*  20:    */   
/*  21:    */   public ITableOperator getObject()
/*  22:    */     throws Exception
/*  23:    */   {
/*  24: 41 */     if (this.dbType.equals("oracle")) {
/*  25: 42 */       this.tableOperator = new OracleTableOperator();
/*  26: 44 */     } else if (this.dbType.equals("mssql")) {
/*  27: 45 */       this.tableOperator = new SqlserverTableOperator();
/*  28: 47 */     } else if (this.dbType.equals("db2")) {
/*  29: 48 */       this.tableOperator = new Db2TableOperator();
/*  30: 50 */     } else if (this.dbType.equals("mysql")) {
/*  31: 51 */       this.tableOperator = new MysqlTableOperator();
/*  32: 53 */     } else if (this.dbType.equals("h2")) {
/*  33: 54 */       this.tableOperator = new H2TableOperator();
/*  34: 56 */     } else if (this.dbType.equals("dm")) {
/*  35: 57 */       this.tableOperator = new DmTableOperator();
/*  36:    */     } else {
/*  37: 59 */       throw new Exception("没有设置合适的数据库类型");
/*  38:    */     }
/*  39: 62 */     this.tableOperator.setDbType(this.dbType);
/*  40: 63 */     this.tableOperator.setJdbcTemplate(this.jdbcTemplate);
/*  41: 64 */     this.tableOperator.setDialect(this.dialect);
/*  42: 65 */     return this.tableOperator;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setDbType(String dbType)
/*  46:    */   {
/*  47: 75 */     this.dbType = dbType;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
/*  51:    */   {
/*  52: 84 */     this.jdbcTemplate = jdbcTemplate;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setDialect(Dialect dialect)
/*  56:    */   {
/*  57: 89 */     this.dialect = dialect;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Class<?> getObjectType()
/*  61:    */   {
/*  62: 97 */     return ITableOperator.class;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean isSingleton()
/*  66:    */   {
/*  67:103 */     return true;
/*  68:    */   }
/*  69:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.TableOperatorFactoryBean
 * JD-Core Version:    0.7.0.1
 */