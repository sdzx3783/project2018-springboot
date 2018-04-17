/*   1:    */ package com.hotent.core.table;
/*   2:    */ 
/*   3:    */ import com.hotent.core.mybatis.Dialect;
/*   4:    */ import com.hotent.core.page.PageBean;
/*   5:    */ import com.hotent.core.util.DialectUtil;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.annotation.Resource;
/*   9:    */ import org.springframework.jdbc.core.JdbcTemplate;
/*  10:    */ import org.springframework.jdbc.core.RowMapper;
/*  11:    */ import org.springframework.stereotype.Component;
/*  12:    */ 
/*  13:    */ @Component
/*  14:    */ public abstract class BaseTableMeta
/*  15:    */ {
/*  16:    */   @Resource
/*  17:    */   private JdbcTemplate jdbcTemplate;
/*  18:    */   
/*  19:    */   public abstract TableModel getTableByName(String paramString);
/*  20:    */   
/*  21:    */   public abstract Map<String, String> getTablesByName(String paramString);
/*  22:    */   
/*  23:    */   public abstract List<TableModel> getTablesByName(String paramString, PageBean paramPageBean)
/*  24:    */     throws Exception;
/*  25:    */   
/*  26:    */   public abstract Map<String, String> getTablesByName(List<String> paramList);
/*  27:    */   
/*  28:    */   public abstract String getAllTableSql();
/*  29:    */   
/*  30:    */   protected <T> List<T> getForList(String sql, PageBean pageBean, RowMapper<T> rowMapper, String dbType)
/*  31:    */     throws Exception
/*  32:    */   {
/*  33:104 */     if (pageBean != null)
/*  34:    */     {
/*  35:105 */       int pageSize = pageBean.getPageSize();
/*  36:106 */       int offset = pageBean.getFirst();
/*  37:107 */       Dialect dialect = DialectUtil.getDialect(dbType);
/*  38:108 */       String pageSql = dialect.getLimitString(sql, offset, pageSize);
/*  39:109 */       String totalSql = dialect.getCountSql(sql);
/*  40:    */       
/*  41:111 */       List<T> list = this.jdbcTemplate.query(pageSql, rowMapper);
/*  42:112 */       int total = this.jdbcTemplate.queryForInt(totalSql);
/*  43:113 */       pageBean.setTotalCount(total);
/*  44:114 */       return list;
/*  45:    */     }
/*  46:116 */     return this.jdbcTemplate.query(sql, rowMapper);
/*  47:    */   }
/*  48:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.BaseTableMeta
 * JD-Core Version:    0.7.0.1
 */