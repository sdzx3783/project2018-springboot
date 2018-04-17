/*   1:    */ package com.hotent.core.table.colmap;
/*   2:    */ 
/*   3:    */ import com.hotent.core.table.ColumnModel;
/*   4:    */ import com.hotent.core.util.StringUtil;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import org.springframework.jdbc.core.RowMapper;
/*   8:    */ 
/*   9:    */ public class DB2ColumnMap
/*  10:    */   implements RowMapper<ColumnModel>
/*  11:    */ {
/*  12:    */   public ColumnModel mapRow(ResultSet rs, int rowNum)
/*  13:    */     throws SQLException
/*  14:    */   {
/*  15: 19 */     ColumnModel column = new ColumnModel();
/*  16: 20 */     String tabName = rs.getString("TAB_NAME");
/*  17: 21 */     String colName = rs.getString("COL_NAME");
/*  18: 22 */     String colType = rs.getString("COL_TYPE");
/*  19: 23 */     String colComment = rs.getString("COL_COMMENT");
/*  20: 24 */     String nullable = rs.getString("IS_NULLABLE");
/*  21: 25 */     String length = rs.getString("LENGTH");
/*  22: 26 */     String scale = rs.getString("SCALE");
/*  23: 27 */     String keySeq = rs.getString("KEYSEQ");
/*  24: 28 */     int iLength = string2Int(length, 0);
/*  25: 29 */     int iPrecision = iLength;
/*  26: 30 */     int iScale = string2Int(scale, 0);
/*  27: 31 */     int iKeySeq = string2Int(keySeq, 0);
/*  28:    */     
/*  29: 33 */     column.setTableName(tabName);
/*  30: 34 */     column.setName(colName);
/*  31: 35 */     column.setComment(colComment);
/*  32:    */     
/*  33: 37 */     column.setIsNull("Y".equalsIgnoreCase(nullable));
/*  34:    */     
/*  35: 39 */     column.setIsPk(iKeySeq > 0);
/*  36:    */     
/*  37: 41 */     setType(colType, iLength, iPrecision, iScale, column);
/*  38: 42 */     return column;
/*  39:    */   }
/*  40:    */   
/*  41:    */   private int string2Int(String str, int def)
/*  42:    */   {
/*  43: 52 */     int retval = def;
/*  44: 53 */     if (StringUtil.isNotEmpty(str)) {
/*  45:    */       try
/*  46:    */       {
/*  47: 55 */         retval = Integer.parseInt(str);
/*  48:    */       }
/*  49:    */       catch (Exception e)
/*  50:    */       {
/*  51: 57 */         e.printStackTrace();
/*  52:    */       }
/*  53:    */     }
/*  54: 60 */     return retval;
/*  55:    */   }
/*  56:    */   
/*  57:    */   private void setType(String type, int length, int precision, int scale, ColumnModel columnModel)
/*  58:    */   {
/*  59: 73 */     String dbtype = type.toLowerCase();
/*  60: 74 */     if (dbtype.endsWith("bigint"))
/*  61:    */     {
/*  62: 75 */       columnModel.setColumnType("number");
/*  63: 76 */       columnModel.setIntLen(19);
/*  64: 77 */       columnModel.setDecimalLen(0);
/*  65:    */     }
/*  66: 78 */     else if (dbtype.endsWith("blob"))
/*  67:    */     {
/*  68: 79 */       columnModel.setColumnType("clob");
/*  69:    */     }
/*  70: 80 */     else if (dbtype.endsWith("character"))
/*  71:    */     {
/*  72: 81 */       columnModel.setColumnType("varchar");
/*  73: 82 */       columnModel.setCharLen(length);
/*  74: 83 */       columnModel.setDecimalLen(0);
/*  75:    */     }
/*  76: 84 */     else if (dbtype.endsWith("clob"))
/*  77:    */     {
/*  78: 85 */       columnModel.setColumnType("clob");
/*  79:    */     }
/*  80: 86 */     else if (dbtype.endsWith("date"))
/*  81:    */     {
/*  82: 87 */       columnModel.setColumnType("date");
/*  83:    */     }
/*  84: 88 */     else if (dbtype.endsWith("dbclob"))
/*  85:    */     {
/*  86: 89 */       columnModel.setColumnType("clob");
/*  87:    */     }
/*  88: 90 */     else if (dbtype.endsWith("decimal"))
/*  89:    */     {
/*  90: 91 */       columnModel.setColumnType("number");
/*  91: 92 */       columnModel.setIntLen(precision - scale);
/*  92: 93 */       columnModel.setDecimalLen(scale);
/*  93:    */     }
/*  94: 94 */     else if (dbtype.endsWith("double"))
/*  95:    */     {
/*  96: 95 */       columnModel.setColumnType("number");
/*  97: 96 */       columnModel.setIntLen(precision - scale);
/*  98: 97 */       columnModel.setDecimalLen(scale);
/*  99:    */     }
/* 100: 98 */     else if (dbtype.endsWith("graphic"))
/* 101:    */     {
/* 102: 99 */       columnModel.setColumnType("clob");
/* 103:    */     }
/* 104:100 */     else if (dbtype.endsWith("integer"))
/* 105:    */     {
/* 106:101 */       columnModel.setColumnType("number");
/* 107:102 */       columnModel.setIntLen(10);
/* 108:103 */       columnModel.setDecimalLen(0);
/* 109:    */     }
/* 110:104 */     else if (dbtype.endsWith("long varchar"))
/* 111:    */     {
/* 112:105 */       columnModel.setColumnType("varchar");
/* 113:106 */       columnModel.setCharLen(length);
/* 114:    */     }
/* 115:107 */     else if (dbtype.endsWith("long vargraphic"))
/* 116:    */     {
/* 117:108 */       columnModel.setColumnType("clob");
/* 118:    */     }
/* 119:109 */     else if (dbtype.endsWith("real"))
/* 120:    */     {
/* 121:110 */       columnModel.setColumnType("number");
/* 122:111 */       columnModel.setIntLen(length);
/* 123:112 */       columnModel.setDecimalLen(scale);
/* 124:    */     }
/* 125:113 */     else if (dbtype.endsWith("smallint"))
/* 126:    */     {
/* 127:114 */       columnModel.setColumnType("number");
/* 128:115 */       columnModel.setIntLen(5);
/* 129:116 */       columnModel.setDecimalLen(0);
/* 130:    */     }
/* 131:117 */     else if (dbtype.endsWith("time"))
/* 132:    */     {
/* 133:118 */       columnModel.setColumnType("date");
/* 134:    */     }
/* 135:119 */     else if (dbtype.endsWith("timestamp"))
/* 136:    */     {
/* 137:120 */       columnModel.setColumnType("date");
/* 138:    */     }
/* 139:121 */     else if (dbtype.endsWith("varchar"))
/* 140:    */     {
/* 141:122 */       columnModel.setColumnType("varchar");
/* 142:123 */       columnModel.setCharLen(length);
/* 143:    */     }
/* 144:124 */     else if (dbtype.endsWith("vargraphic"))
/* 145:    */     {
/* 146:125 */       columnModel.setColumnType("clob");
/* 147:    */     }
/* 148:126 */     else if (dbtype.endsWith("xml"))
/* 149:    */     {
/* 150:127 */       columnModel.setColumnType("clob");
/* 151:    */     }
/* 152:    */   }
/* 153:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.colmap.DB2ColumnMap
 * JD-Core Version:    0.7.0.1
 */