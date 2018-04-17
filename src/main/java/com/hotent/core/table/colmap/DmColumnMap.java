/*  1:   */ package com.hotent.core.table.colmap;
/*  2:   */ 
/*  3:   */ import com.hotent.core.table.ColumnModel;
/*  4:   */ import java.sql.ResultSet;
/*  5:   */ import java.sql.SQLException;
/*  6:   */ import org.springframework.jdbc.core.RowMapper;
/*  7:   */ 
/*  8:   */ public class DmColumnMap
/*  9:   */   implements RowMapper<ColumnModel>
/* 10:   */ {
/* 11:   */   public ColumnModel mapRow(ResultSet rs, int row)
/* 12:   */     throws SQLException
/* 13:   */   {
/* 14:20 */     ColumnModel column = new ColumnModel();
/* 15:21 */     String name = rs.getString("NAME");
/* 16:22 */     String typeName = rs.getString("TYPENAME");
/* 17:23 */     int length = rs.getInt("LENGTH");
/* 18:24 */     int precision = rs.getInt("PRECISION");
/* 19:25 */     int scale = rs.getInt("SCALE");
/* 20:26 */     boolean isNull = rs.getString("NULLABLE").equals("Y");
/* 21:27 */     String comments = rs.getString("DESCRIPTION");
/* 22:28 */     String tableName = rs.getString("TABLE_NAME");
/* 23:29 */     int isPK = rs.getInt("IS_PK");
/* 24:   */     
/* 25:31 */     column.setName(name);
/* 26:32 */     column.setComment(comments);
/* 27:33 */     column.setIsNull(isNull);
/* 28:34 */     column.setTableName(tableName);
/* 29:35 */     column.setIsPk(isPK == 1);
/* 30:36 */     setType(typeName, length, precision, scale, column);
/* 31:37 */     return column;
/* 32:   */   }
/* 33:   */   
/* 34:   */   private void setType(String dbtype, int length, int precision, int scale, ColumnModel column)
/* 35:   */   {
/* 36:50 */     if (dbtype.indexOf("CHAR") > -1)
/* 37:   */     {
/* 38:51 */       column.setColumnType("varchar");
/* 39:52 */       column.setCharLen(length);
/* 40:53 */       return;
/* 41:   */     }
/* 42:55 */     if (dbtype.equals("NUMBER"))
/* 43:   */     {
/* 44:56 */       column.setColumnType("number");
/* 45:57 */       column.setIntLen(precision - scale);
/* 46:58 */       column.setDecimalLen(scale);
/* 47:59 */       return;
/* 48:   */     }
/* 49:61 */     if (dbtype.equals("DATE"))
/* 50:   */     {
/* 51:62 */       column.setColumnType("date");
/* 52:63 */       return;
/* 53:   */     }
/* 54:65 */     if (dbtype.equals("CLOB"))
/* 55:   */     {
/* 56:66 */       column.setColumnType("clob");
/* 57:67 */       return;
/* 58:   */     }
/* 59:   */   }
/* 60:   */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.colmap.DmColumnMap
 * JD-Core Version:    0.7.0.1
 */