/*   1:    */ package com.hotent.core.table.colmap;
/*   2:    */ 
/*   3:    */ import com.hotent.core.table.ColumnModel;
/*   4:    */ import com.hotent.core.util.StringUtil;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import org.springframework.jdbc.core.RowMapper;
/*   8:    */ 
/*   9:    */ public class SqlServerColumnMap
/*  10:    */   implements RowMapper<ColumnModel>
/*  11:    */ {
/*  12:    */   public ColumnModel mapRow(ResultSet rs, int row)
/*  13:    */     throws SQLException
/*  14:    */   {
/*  15: 21 */     ColumnModel column = new ColumnModel();
/*  16:    */     
/*  17: 23 */     String name = rs.getString("NAME");
/*  18: 24 */     String is_nullable = rs.getString("IS_NULLABLE");
/*  19: 25 */     String data_type = rs.getString("TYPENAME");
/*  20: 26 */     String length = rs.getString("LENGTH");
/*  21: 27 */     String precisions = rs.getString("PRECISION");
/*  22: 28 */     String scale = rs.getString("SCALE");
/*  23: 29 */     String tableName = rs.getString("TABLE_NAME");
/*  24: 30 */     String comments = rs.getString("DESCRIPTION");
/*  25: 31 */     int isPK = rs.getInt("IS_PK");
/*  26:    */     
/*  27: 33 */     int iLength = StringUtil.isEmpty(length) ? 0 : Integer.parseInt(length);
/*  28: 34 */     int iPrecisions = StringUtil.isEmpty(precisions) ? 0 : Integer.parseInt(precisions);
/*  29: 35 */     int iScale = StringUtil.isEmpty(scale) ? 0 : Integer.parseInt(scale);
/*  30:    */     
/*  31: 37 */     column.setName(name);
/*  32: 38 */     boolean isNull = is_nullable.equals("1");
/*  33: 39 */     column.setIsNull(isNull);
/*  34: 40 */     column.setTableName(tableName);
/*  35: 41 */     column.setComment(comments);
/*  36: 42 */     column.setIsPk(isPK == 1);
/*  37: 43 */     setType(data_type, iLength, iPrecisions, iScale, column);
/*  38:    */     
/*  39: 45 */     return column;
/*  40:    */   }
/*  41:    */   
/*  42:    */   private void setType(String dbtype, int length, int precision, int scale, ColumnModel columnModel)
/*  43:    */   {
/*  44: 61 */     if (dbtype.equals("int"))
/*  45:    */     {
/*  46: 63 */       columnModel.setColumnType("number");
/*  47: 64 */       columnModel.setIntLen(precision);
/*  48: 65 */       columnModel.setDecimalLen(scale);
/*  49: 66 */       return;
/*  50:    */     }
/*  51: 69 */     if (dbtype.equals("bigint"))
/*  52:    */     {
/*  53: 71 */       columnModel.setColumnType("number");
/*  54: 72 */       columnModel.setIntLen(precision);
/*  55: 73 */       columnModel.setDecimalLen(scale);
/*  56: 74 */       return;
/*  57:    */     }
/*  58: 78 */     if (dbtype.equals("smallint"))
/*  59:    */     {
/*  60: 80 */       columnModel.setColumnType("number");
/*  61: 81 */       columnModel.setIntLen(precision);
/*  62: 82 */       columnModel.setDecimalLen(scale);
/*  63: 83 */       return;
/*  64:    */     }
/*  65: 86 */     if (dbtype.equals("tinyint"))
/*  66:    */     {
/*  67: 88 */       columnModel.setColumnType("number");
/*  68: 89 */       columnModel.setIntLen(precision);
/*  69: 90 */       columnModel.setDecimalLen(scale);
/*  70: 91 */       return;
/*  71:    */     }
/*  72: 94 */     if (dbtype.equals("bit"))
/*  73:    */     {
/*  74: 96 */       columnModel.setColumnType("number");
/*  75: 97 */       return;
/*  76:    */     }
/*  77:100 */     if (dbtype.equals("decimal"))
/*  78:    */     {
/*  79:102 */       columnModel.setColumnType("number");
/*  80:103 */       columnModel.setIntLen(precision);
/*  81:104 */       columnModel.setDecimalLen(scale);
/*  82:105 */       return;
/*  83:    */     }
/*  84:108 */     if (dbtype.equals("numeric"))
/*  85:    */     {
/*  86:110 */       columnModel.setColumnType("number");
/*  87:111 */       columnModel.setIntLen(precision);
/*  88:112 */       columnModel.setDecimalLen(scale);
/*  89:113 */       return;
/*  90:    */     }
/*  91:116 */     if (dbtype.equals("real"))
/*  92:    */     {
/*  93:118 */       columnModel.setColumnType("number");
/*  94:119 */       columnModel.setIntLen(precision);
/*  95:120 */       return;
/*  96:    */     }
/*  97:123 */     if (dbtype.equals("float"))
/*  98:    */     {
/*  99:125 */       columnModel.setColumnType("number");
/* 100:126 */       columnModel.setIntLen(precision);
/* 101:127 */       return;
/* 102:    */     }
/* 103:131 */     if (dbtype.equals("varchar"))
/* 104:    */     {
/* 105:133 */       columnModel.setColumnType("varchar");
/* 106:134 */       columnModel.setCharLen(length);
/* 107:    */       
/* 108:136 */       return;
/* 109:    */     }
/* 110:139 */     if (dbtype.equals("char"))
/* 111:    */     {
/* 112:141 */       columnModel.setColumnType("varchar");
/* 113:142 */       columnModel.setCharLen(length);
/* 114:143 */       return;
/* 115:    */     }
/* 116:147 */     if (dbtype.equals("varchar"))
/* 117:    */     {
/* 118:149 */       columnModel.setColumnType("varchar");
/* 119:150 */       columnModel.setCharLen(length);
/* 120:    */       
/* 121:152 */       return;
/* 122:    */     }
/* 123:155 */     if (dbtype.equals("nchar"))
/* 124:    */     {
/* 125:157 */       columnModel.setColumnType("varchar");
/* 126:158 */       columnModel.setCharLen(length);
/* 127:159 */       return;
/* 128:    */     }
/* 129:162 */     if (dbtype.equals("nvarchar"))
/* 130:    */     {
/* 131:164 */       columnModel.setColumnType("varchar");
/* 132:165 */       columnModel.setCharLen(length);
/* 133:    */       
/* 134:167 */       return;
/* 135:    */     }
/* 136:172 */     if (dbtype.startsWith("datetime"))
/* 137:    */     {
/* 138:174 */       columnModel.setColumnType("date");
/* 139:    */       
/* 140:176 */       return;
/* 141:    */     }
/* 142:180 */     if (dbtype.endsWith("money"))
/* 143:    */     {
/* 144:182 */       columnModel.setColumnType("number");
/* 145:183 */       columnModel.setIntLen(precision);
/* 146:184 */       columnModel.setDecimalLen(scale);
/* 147:185 */       return;
/* 148:    */     }
/* 149:188 */     if (dbtype.endsWith("smallmoney"))
/* 150:    */     {
/* 151:190 */       columnModel.setColumnType("clob");
/* 152:191 */       columnModel.setIntLen(precision);
/* 153:192 */       columnModel.setDecimalLen(scale);
/* 154:193 */       return;
/* 155:    */     }
/* 156:196 */     if (dbtype.endsWith("text"))
/* 157:    */     {
/* 158:198 */       columnModel.setColumnType("clob");
/* 159:199 */       columnModel.setCharLen(length);
/* 160:200 */       return;
/* 161:    */     }
/* 162:203 */     if (dbtype.endsWith("ntext"))
/* 163:    */     {
/* 164:205 */       columnModel.setColumnType("clob");
/* 165:206 */       columnModel.setCharLen(length);
/* 166:207 */       return;
/* 167:    */     }
/* 168:    */   }
/* 169:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.colmap.SqlServerColumnMap
 * JD-Core Version:    0.7.0.1
 */