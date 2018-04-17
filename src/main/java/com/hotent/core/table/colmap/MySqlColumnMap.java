/*   1:    */ package com.hotent.core.table.colmap;
/*   2:    */ 
/*   3:    */ import com.hotent.core.table.ColumnModel;
/*   4:    */ import com.hotent.core.table.impl.MySqlTableMeta;
/*   5:    */ import com.hotent.core.util.StringUtil;
/*   6:    */ import java.sql.ResultSet;
/*   7:    */ import java.sql.SQLException;
/*   8:    */ import org.springframework.jdbc.core.RowMapper;
/*   9:    */ 
/*  10:    */ public class MySqlColumnMap
/*  11:    */   implements RowMapper<ColumnModel>
/*  12:    */ {
/*  13:    */   public ColumnModel mapRow(ResultSet rs, int row)
/*  14:    */     throws SQLException
/*  15:    */   {
/*  16: 22 */     ColumnModel column = new ColumnModel();
/*  17:    */     
/*  18: 24 */     String name = rs.getString("column_name");
/*  19: 25 */     String is_nullable = rs.getString("is_nullable");
/*  20: 26 */     String data_type = rs.getString("data_type");
/*  21: 27 */     String length = rs.getString("length");
/*  22: 28 */     String precisions = rs.getString("precisions");
/*  23: 29 */     String scale = rs.getString("scale");
/*  24: 30 */     String column_key = rs.getString("column_key");
/*  25: 31 */     String column_comment = rs.getString("column_comment");
/*  26: 32 */     String table_name = rs.getString("table_name");
/*  27: 33 */     column_comment = MySqlTableMeta.getComments(column_comment, name);
/*  28:    */     int iLength;
/*  29:    */     try
/*  30:    */     {
/*  31: 36 */       iLength = StringUtil.isEmpty(length) ? 0 : Integer.parseInt(length);
/*  32:    */     }
/*  33:    */     catch (NumberFormatException e)
/*  34:    */     {
/*  35: 38 */       iLength = 0;
/*  36:    */     }
/*  37: 40 */     int iPrecisions = StringUtil.isEmpty(precisions) ? 0 : Integer.parseInt(precisions);
/*  38: 41 */     int iScale = StringUtil.isEmpty(scale) ? 0 : Integer.parseInt(scale);
/*  39:    */     
/*  40: 43 */     column.setName(name);
/*  41: 44 */     column.setTableName(table_name);
/*  42: 45 */     column.setComment(column_comment);
/*  43: 46 */     if ((StringUtil.isNotEmpty(column_key)) && ("PRI".equals(column_key))) {
/*  44: 47 */       column.setIsPk(true);
/*  45:    */     }
/*  46: 48 */     boolean isNull = is_nullable.equals("YES");
/*  47: 49 */     column.setIsNull(isNull);
/*  48:    */     
/*  49: 51 */     setType(data_type, iLength, iPrecisions, iScale, column);
/*  50:    */     
/*  51: 53 */     return column;
/*  52:    */   }
/*  53:    */   
/*  54:    */   private void setType(String dbtype, int length, int precision, int scale, ColumnModel columnModel)
/*  55:    */   {
/*  56: 67 */     if (dbtype.equals("bigint"))
/*  57:    */     {
/*  58: 69 */       columnModel.setColumnType("number");
/*  59: 70 */       columnModel.setIntLen(19);
/*  60: 71 */       columnModel.setDecimalLen(0);
/*  61: 72 */       return;
/*  62:    */     }
/*  63: 75 */     if (dbtype.equals("int"))
/*  64:    */     {
/*  65: 77 */       columnModel.setColumnType("number");
/*  66: 78 */       columnModel.setIntLen(10);
/*  67: 79 */       columnModel.setDecimalLen(0);
/*  68: 80 */       return;
/*  69:    */     }
/*  70: 83 */     if (dbtype.equals("mediumint"))
/*  71:    */     {
/*  72: 85 */       columnModel.setColumnType("number");
/*  73: 86 */       columnModel.setIntLen(7);
/*  74: 87 */       columnModel.setDecimalLen(0);
/*  75: 88 */       return;
/*  76:    */     }
/*  77: 92 */     if (dbtype.equals("smallint"))
/*  78:    */     {
/*  79: 94 */       columnModel.setColumnType("number");
/*  80: 95 */       columnModel.setIntLen(5);
/*  81: 96 */       columnModel.setDecimalLen(0);
/*  82: 97 */       return;
/*  83:    */     }
/*  84:100 */     if (dbtype.equals("tinyint"))
/*  85:    */     {
/*  86:102 */       columnModel.setColumnType("number");
/*  87:103 */       columnModel.setIntLen(3);
/*  88:104 */       columnModel.setDecimalLen(0);
/*  89:105 */       return;
/*  90:    */     }
/*  91:108 */     if (dbtype.equals("decimal"))
/*  92:    */     {
/*  93:110 */       columnModel.setColumnType("number");
/*  94:111 */       columnModel.setIntLen(precision - scale);
/*  95:112 */       columnModel.setDecimalLen(scale);
/*  96:113 */       return;
/*  97:    */     }
/*  98:116 */     if (dbtype.equals("double"))
/*  99:    */     {
/* 100:118 */       columnModel.setColumnType("number");
/* 101:119 */       columnModel.setIntLen(18);
/* 102:120 */       columnModel.setDecimalLen(4);
/* 103:121 */       return;
/* 104:    */     }
/* 105:124 */     if (dbtype.equals("float"))
/* 106:    */     {
/* 107:126 */       columnModel.setColumnType("number");
/* 108:127 */       columnModel.setIntLen(8);
/* 109:128 */       columnModel.setDecimalLen(4);
/* 110:129 */       return;
/* 111:    */     }
/* 112:133 */     if (dbtype.equals("varchar"))
/* 113:    */     {
/* 114:135 */       columnModel.setColumnType("varchar");
/* 115:136 */       columnModel.setCharLen(length);
/* 116:    */       
/* 117:138 */       return;
/* 118:    */     }
/* 119:141 */     if (dbtype.equals("char"))
/* 120:    */     {
/* 121:143 */       columnModel.setColumnType("varchar");
/* 122:144 */       columnModel.setCharLen(length);
/* 123:145 */       return;
/* 124:    */     }
/* 125:150 */     if (dbtype.startsWith("date"))
/* 126:    */     {
/* 127:152 */       columnModel.setColumnType("date");
/* 128:    */       
/* 129:154 */       return;
/* 130:    */     }
/* 131:157 */     if (dbtype.startsWith("time"))
/* 132:    */     {
/* 133:159 */       columnModel.setColumnType("date");
/* 134:    */       
/* 135:161 */       return;
/* 136:    */     }
/* 137:165 */     if (dbtype.endsWith("text"))
/* 138:    */     {
/* 139:167 */       columnModel.setColumnType("clob");
/* 140:168 */       columnModel.setCharLen(65535);
/* 141:169 */       return;
/* 142:    */     }
/* 143:171 */     if (dbtype.endsWith("blob"))
/* 144:    */     {
/* 145:173 */       columnModel.setColumnType("clob");
/* 146:174 */       columnModel.setCharLen(65535);
/* 147:175 */       return;
/* 148:    */     }
/* 149:177 */     if (dbtype.endsWith("clob"))
/* 150:    */     {
/* 151:179 */       columnModel.setColumnType("clob");
/* 152:180 */       columnModel.setCharLen(65535);
/* 153:181 */       return;
/* 154:    */     }
/* 155:    */   }
/* 156:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.colmap.MySqlColumnMap
 * JD-Core Version:    0.7.0.1
 */