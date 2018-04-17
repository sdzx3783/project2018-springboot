/*   1:    */ package com.hotent.core.table;
/*   2:    */ 
/*   3:    */ import com.hotent.core.mybatis.Dialect;
/*   4:    */ import com.hotent.core.page.PageBean;
/*   5:    */ import com.hotent.core.util.DialectUtil;
/*   6:    */ import java.sql.Connection;
/*   7:    */ import java.sql.ResultSet;
/*   8:    */ import java.sql.ResultSetMetaData;
/*   9:    */ import java.sql.SQLException;
/*  10:    */ import java.sql.Statement;
/*  11:    */ import java.util.List;
/*  12:    */ import javax.annotation.Resource;
/*  13:    */ import javax.sql.DataSource;
/*  14:    */ import org.springframework.jdbc.core.JdbcTemplate;
/*  15:    */ import org.springframework.jdbc.core.RowMapper;
/*  16:    */ import org.springframework.stereotype.Component;
/*  17:    */ 
/*  18:    */ @Component
/*  19:    */ public abstract class BaseDbView
/*  20:    */ {
/*  21:    */   @Resource
/*  22:    */   private JdbcTemplate jdbcTemplate;
/*  23:    */   protected String currentDb;
/*  24:    */   
/*  25:    */   public abstract String getType(String paramString);
/*  26:    */   
/*  27:    */   public TableModel getModelByViewName(String viewName)
/*  28:    */     throws SQLException
/*  29:    */   {
/*  30: 64 */     Connection conn = this.jdbcTemplate.getDataSource().getConnection();
/*  31:    */     
/*  32: 66 */     Statement stmt = null;
/*  33: 67 */     ResultSet rs = null;
/*  34:    */     
/*  35: 69 */     tableModel = new TableModel();
/*  36: 70 */     tableModel.setName(viewName);
/*  37: 71 */     tableModel.setComment(viewName);
/*  38:    */     try
/*  39:    */     {
/*  40: 75 */       stmt = conn.createStatement();
/*  41: 76 */       rs = stmt.executeQuery("select * from " + viewName);
/*  42: 77 */       ResultSetMetaData metadata = rs.getMetaData();
/*  43:    */       
/*  44: 79 */       int count = metadata.getColumnCount();
/*  45: 80 */       for (int i = 1; i <= count; i++)
/*  46:    */       {
/*  47: 81 */         ColumnModel columnModel = new ColumnModel();
/*  48: 82 */         String columnName = metadata.getColumnName(i);
/*  49: 83 */         String typeName = metadata.getColumnTypeName(i);
/*  50: 84 */         String dataType = getType(typeName);
/*  51: 85 */         columnModel.setName(columnName);
/*  52: 86 */         columnModel.setColumnType(dataType);
/*  53: 87 */         columnModel.setComment(columnName);
/*  54: 88 */         tableModel.addColumnModel(columnModel);
/*  55:    */       }
/*  56:108 */       return tableModel;
/*  57:    */     }
/*  58:    */     catch (SQLException e)
/*  59:    */     {
/*  60: 92 */       e.printStackTrace();
/*  61:    */     }
/*  62:    */     finally
/*  63:    */     {
/*  64:    */       try
/*  65:    */       {
/*  66: 95 */         if (rs != null) {
/*  67: 96 */           rs.close();
/*  68:    */         }
/*  69: 98 */         if (stmt != null) {
/*  70: 99 */           stmt.close();
/*  71:    */         }
/*  72:101 */         if (conn != null) {
/*  73:102 */           conn.close();
/*  74:    */         }
/*  75:    */       }
/*  76:    */       catch (SQLException e)
/*  77:    */       {
/*  78:105 */         e.printStackTrace();
/*  79:    */       }
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected <T> List<T> getForList(String sql, PageBean pageBean, Class<T> elementType, String dbType)
/*  84:    */     throws Exception
/*  85:    */   {
/*  86:114 */     if (pageBean != null)
/*  87:    */     {
/*  88:115 */       int pageSize = pageBean.getPageSize();
/*  89:116 */       int offset = pageBean.getFirst();
/*  90:117 */       Dialect dialect = DialectUtil.getDialect(dbType);
/*  91:118 */       String pageSql = dialect.getLimitString(sql, offset, pageSize);
/*  92:119 */       String totalSql = dialect.getCountSql(sql);
/*  93:120 */       List<T> list = this.jdbcTemplate.queryForList(pageSql, elementType);
/*  94:121 */       int total = this.jdbcTemplate.queryForInt(totalSql);
/*  95:122 */       pageBean.setTotalCount(total);
/*  96:123 */       return list;
/*  97:    */     }
/*  98:125 */     return this.jdbcTemplate.queryForList(sql, elementType);
/*  99:    */   }
/* 100:    */   
/* 101:    */   protected <T> List<T> getForList(String sql, PageBean pageBean, RowMapper<T> rowMapper, String dbType)
/* 102:    */     throws Exception
/* 103:    */   {
/* 104:132 */     if (pageBean != null)
/* 105:    */     {
/* 106:133 */       int pageSize = pageBean.getPageSize();
/* 107:134 */       int offset = pageBean.getFirst();
/* 108:135 */       Dialect dialect = DialectUtil.getDialect(dbType);
/* 109:136 */       String pageSql = dialect.getLimitString(sql, offset, pageSize);
/* 110:137 */       String totalSql = dialect.getCountSql(sql);
/* 111:138 */       List<T> list = this.jdbcTemplate.query(pageSql, rowMapper);
/* 112:139 */       int total = this.jdbcTemplate.queryForInt(totalSql);
/* 113:140 */       pageBean.setTotalCount(total);
/* 114:141 */       return list;
/* 115:    */     }
/* 116:143 */     return this.jdbcTemplate.query(sql, rowMapper);
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.BaseDbView
 * JD-Core Version:    0.7.0.1
 */