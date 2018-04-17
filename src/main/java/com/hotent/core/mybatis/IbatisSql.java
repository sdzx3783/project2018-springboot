/*  1:   */ package com.hotent.core.mybatis;
/*  2:   */ 
/*  3:   */ import com.hotent.core.table.ITableOperator;
/*  4:   */ import com.hotent.core.util.AppUtil;
/*  5:   */ import java.util.regex.Matcher;
/*  6:   */ import java.util.regex.Pattern;
/*  7:   */ 
/*  8:   */ public class IbatisSql
/*  9:   */ {
/* 10:   */   private String sql;
/* 11:   */   private Object[] parameters;
/* 12:   */   private Class resultClass;
/* 13:   */   
/* 14:   */   public Class getResultClass()
/* 15:   */   {
/* 16:16 */     return this.resultClass;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setResultClass(Class resultClass)
/* 20:   */   {
/* 21:20 */     this.resultClass = resultClass;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void setSql(String sql)
/* 25:   */   {
/* 26:24 */     this.sql = sql;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String getSql()
/* 30:   */   {
/* 31:28 */     return this.sql;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void setParameters(Object[] parameters)
/* 35:   */   {
/* 36:32 */     this.parameters = parameters;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public Object[] getParameters()
/* 40:   */   {
/* 41:36 */     return this.parameters;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public String getCountSql()
/* 45:   */   {
/* 46:45 */     String sqlCount = this.sql;
/* 47:46 */     ITableOperator tableOperator = (ITableOperator)AppUtil.getBean(ITableOperator.class);
/* 48:48 */     if (tableOperator.getDbType().equals("mssql"))
/* 49:   */     {
/* 50:49 */       sqlCount = sqlCount.trim();
/* 51:50 */       Pattern pattern = Pattern.compile("^SELECT(\\s+(ALL|DISTINCT))?", 2);
/* 52:   */       
/* 53:52 */       Matcher matcher = pattern.matcher(sqlCount);
/* 54:53 */       if (matcher.find())
/* 55:   */       {
/* 56:54 */         String matStr = matcher.group();
/* 57:55 */         sqlCount = sqlCount.toUpperCase().replaceFirst(matStr.toUpperCase(), matStr.toUpperCase() + " TOP 100 PERCENT");
/* 58:   */       }
/* 59:   */       else
/* 60:   */       {
/* 61:58 */         throw new UnsupportedOperationException("SQL语句拼接出现错误！");
/* 62:   */       }
/* 63:   */     }
/* 64:61 */     sqlCount = "select count(*) amount from (" + sqlCount + ") A";
/* 65:62 */     return sqlCount;
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.mybatis.IbatisSql
 * JD-Core Version:    0.7.0.1
 */