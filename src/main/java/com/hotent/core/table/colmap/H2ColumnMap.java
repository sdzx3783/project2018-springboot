/*   1:    */ package com.hotent.core.table.colmap;
/*   2:    */ 
/*   3:    */ import com.hotent.core.table.ColumnModel;
/*   4:    */ import com.hotent.core.util.StringUtil;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import org.springframework.jdbc.core.RowMapper;
/*   8:    */ 
/*   9:    */ public class H2ColumnMap
/*  10:    */   implements RowMapper<ColumnModel>
/*  11:    */ {
/*  12:    */   public ColumnModel mapRow(ResultSet rs, int row)
/*  13:    */     throws SQLException
/*  14:    */   {
/*  15: 21 */     ColumnModel column = new ColumnModel();
/*  16:    */     
/*  17: 23 */     String name = rs.getString("COLUMN_NAME");
/*  18: 24 */     String is_nullable = rs.getString("IS_NULLABLE");
/*  19: 25 */     String data_type = rs.getString("TYPE_NAME");
/*  20: 26 */     String length = rs.getString("LENGTH");
/*  21: 27 */     String precisions = rs.getString("PRECISIONS");
/*  22: 28 */     String scale = rs.getString("SCALE");
/*  23: 29 */     String column_list = rs.getString("COLUMN_LIST");
/*  24: 30 */     String column_comment = rs.getString("REMARKS");
/*  25: 31 */     String table_name = rs.getString("TABLE_NAME");
/*  26:    */     int iLength;
/*  27:    */     try
/*  28:    */     {
/*  29: 34 */       iLength = StringUtil.isEmpty(length) ? 0 : Integer.parseInt(length);
/*  30:    */     }
/*  31:    */     catch (NumberFormatException e)
/*  32:    */     {
/*  33: 36 */       iLength = 0;
/*  34:    */     }
/*  35: 38 */     int iPrecisions = StringUtil.isEmpty(precisions) ? 0 : Integer.parseInt(precisions);
/*  36: 39 */     int iScale = StringUtil.isEmpty(scale) ? 0 : Integer.parseInt(scale);
/*  37:    */     
/*  38: 41 */     column.setName(name);
/*  39: 42 */     column.setTableName(table_name);
/*  40: 43 */     column.setComment(column_comment);
/*  41:    */     
/*  42: 45 */     boolean isPkColumn = false;
/*  43: 46 */     if (StringUtil.isNotEmpty(column_list))
/*  44:    */     {
/*  45: 47 */       String[] pkColumns = column_list.split(",");
/*  46: 48 */       for (String pkColumn : pkColumns) {
/*  47: 49 */         if (name.trim().equalsIgnoreCase(pkColumn.trim()))
/*  48:    */         {
/*  49: 50 */           isPkColumn = true;
/*  50: 51 */           break;
/*  51:    */         }
/*  52:    */       }
/*  53:    */     }
/*  54: 55 */     column.setIsPk(isPkColumn);
/*  55:    */     
/*  56: 57 */     boolean isNull = is_nullable.equals("YES");
/*  57: 58 */     column.setIsNull(isNull);
/*  58:    */     
/*  59: 60 */     setType(data_type, iLength, iPrecisions, iScale, column);
/*  60:    */     
/*  61: 62 */     return column;
/*  62:    */   }
/*  63:    */   
/*  64:    */   private void setType(String dbtype, int length, int precision, int scale, ColumnModel columnModel)
/*  65:    */   {
/*  66: 75 */     dbtype = dbtype.toUpperCase();
/*  67: 76 */     if (dbtype.equals("BIGINT"))
/*  68:    */     {
/*  69: 77 */       columnModel.setColumnType("int");
/*  70: 78 */       columnModel.setIntLen(19);
/*  71: 79 */       columnModel.setDecimalLen(0);
/*  72: 80 */       return;
/*  73:    */     }
/*  74: 81 */     if (dbtype.equals("INT8"))
/*  75:    */     {
/*  76: 82 */       columnModel.setColumnType("int");
/*  77: 83 */       columnModel.setIntLen(19);
/*  78: 84 */       columnModel.setDecimalLen(0);
/*  79: 85 */       return;
/*  80:    */     }
/*  81: 86 */     if (dbtype.equals("INT"))
/*  82:    */     {
/*  83: 87 */       columnModel.setColumnType("int");
/*  84: 88 */       columnModel.setIntLen(10);
/*  85: 89 */       columnModel.setDecimalLen(0);
/*  86: 90 */       return;
/*  87:    */     }
/*  88: 91 */     if (dbtype.equals("INTEGER"))
/*  89:    */     {
/*  90: 92 */       columnModel.setColumnType("int");
/*  91: 93 */       columnModel.setIntLen(10);
/*  92: 94 */       columnModel.setDecimalLen(0);
/*  93: 95 */       return;
/*  94:    */     }
/*  95: 96 */     if (dbtype.equals("MEDIUMINT"))
/*  96:    */     {
/*  97: 97 */       columnModel.setColumnType("int");
/*  98: 98 */       columnModel.setIntLen(7);
/*  99: 99 */       columnModel.setDecimalLen(0);
/* 100:100 */       return;
/* 101:    */     }
/* 102:101 */     if (dbtype.equals("INT4"))
/* 103:    */     {
/* 104:102 */       columnModel.setColumnType("int");
/* 105:103 */       columnModel.setIntLen(5);
/* 106:104 */       columnModel.setDecimalLen(0);
/* 107:105 */       return;
/* 108:    */     }
/* 109:106 */     if (dbtype.equals("SIGNED"))
/* 110:    */     {
/* 111:107 */       columnModel.setColumnType("int");
/* 112:108 */       columnModel.setIntLen(3);
/* 113:109 */       columnModel.setDecimalLen(0);
/* 114:110 */       return;
/* 115:    */     }
/* 116:111 */     if (dbtype.equals("TINYINT"))
/* 117:    */     {
/* 118:112 */       columnModel.setColumnType("int");
/* 119:113 */       columnModel.setIntLen(2);
/* 120:114 */       columnModel.setDecimalLen(0);
/* 121:115 */       return;
/* 122:    */     }
/* 123:116 */     if (dbtype.equals("SMALLINT"))
/* 124:    */     {
/* 125:117 */       columnModel.setColumnType("int");
/* 126:118 */       columnModel.setIntLen(5);
/* 127:119 */       columnModel.setDecimalLen(0);
/* 128:120 */       return;
/* 129:    */     }
/* 130:121 */     if (dbtype.equals("INT2"))
/* 131:    */     {
/* 132:122 */       columnModel.setColumnType("int");
/* 133:123 */       columnModel.setIntLen(5);
/* 134:124 */       columnModel.setDecimalLen(0);
/* 135:125 */       return;
/* 136:    */     }
/* 137:126 */     if (dbtype.equals("YEAR"))
/* 138:    */     {
/* 139:127 */       columnModel.setColumnType("int");
/* 140:128 */       columnModel.setIntLen(5);
/* 141:129 */       columnModel.setDecimalLen(0);
/* 142:130 */       return;
/* 143:    */     }
/* 144:131 */     if (dbtype.equals("IDENTITY"))
/* 145:    */     {
/* 146:132 */       columnModel.setColumnType("int");
/* 147:133 */       columnModel.setIntLen(5);
/* 148:134 */       columnModel.setDecimalLen(0);
/* 149:135 */       return;
/* 150:    */     }
/* 151:136 */     if (dbtype.equals("DECIMAL"))
/* 152:    */     {
/* 153:137 */       columnModel.setColumnType("number");
/* 154:138 */       columnModel.setIntLen(precision - scale);
/* 155:139 */       columnModel.setDecimalLen(scale);
/* 156:140 */       return;
/* 157:    */     }
/* 158:141 */     if (dbtype.equals("DOUBLE"))
/* 159:    */     {
/* 160:142 */       columnModel.setColumnType("number");
/* 161:143 */       return;
/* 162:    */     }
/* 163:144 */     if (dbtype.equals("FLOAT"))
/* 164:    */     {
/* 165:145 */       columnModel.setColumnType("number");
/* 166:146 */       return;
/* 167:    */     }
/* 168:147 */     if (dbtype.equals("FLOAT4"))
/* 169:    */     {
/* 170:148 */       columnModel.setColumnType("number");
/* 171:149 */       return;
/* 172:    */     }
/* 173:150 */     if (dbtype.equals("FLOAT8"))
/* 174:    */     {
/* 175:151 */       columnModel.setColumnType("number");
/* 176:152 */       return;
/* 177:    */     }
/* 178:153 */     if (dbtype.equals("REAL"))
/* 179:    */     {
/* 180:154 */       columnModel.setColumnType("number");
/* 181:155 */       return;
/* 182:    */     }
/* 183:156 */     if (dbtype.equals("TIME"))
/* 184:    */     {
/* 185:157 */       columnModel.setColumnType("date");
/* 186:158 */       return;
/* 187:    */     }
/* 188:159 */     if (dbtype.equals("DATE"))
/* 189:    */     {
/* 190:160 */       columnModel.setColumnType("date");
/* 191:161 */       return;
/* 192:    */     }
/* 193:162 */     if (dbtype.equals("DATETIME"))
/* 194:    */     {
/* 195:163 */       columnModel.setColumnType("date");
/* 196:164 */       return;
/* 197:    */     }
/* 198:165 */     if (dbtype.equals("SMALLDATETIME"))
/* 199:    */     {
/* 200:166 */       columnModel.setColumnType("date");
/* 201:167 */       return;
/* 202:    */     }
/* 203:168 */     if (dbtype.equals("TIMESTAMP"))
/* 204:    */     {
/* 205:169 */       columnModel.setColumnType("date");
/* 206:170 */       return;
/* 207:    */     }
/* 208:171 */     if (dbtype.equals("BINARY"))
/* 209:    */     {
/* 210:172 */       columnModel.setColumnType("clob");
/* 211:173 */       return;
/* 212:    */     }
/* 213:174 */     if (dbtype.equals("VARBINARY"))
/* 214:    */     {
/* 215:175 */       columnModel.setColumnType("clob");
/* 216:176 */       return;
/* 217:    */     }
/* 218:177 */     if (dbtype.equals("LONGVARBINARY"))
/* 219:    */     {
/* 220:178 */       columnModel.setColumnType("clob");
/* 221:179 */       return;
/* 222:    */     }
/* 223:180 */     if (dbtype.equals("RAW"))
/* 224:    */     {
/* 225:181 */       columnModel.setColumnType("clob");
/* 226:182 */       return;
/* 227:    */     }
/* 228:183 */     if (dbtype.equals("BYTEA"))
/* 229:    */     {
/* 230:184 */       columnModel.setColumnType("clob");
/* 231:185 */       return;
/* 232:    */     }
/* 233:186 */     if (dbtype.equals("TINYBLOB"))
/* 234:    */     {
/* 235:187 */       columnModel.setColumnType("clob");
/* 236:188 */       return;
/* 237:    */     }
/* 238:189 */     if (dbtype.equals("MEDIUMBLOB"))
/* 239:    */     {
/* 240:190 */       columnModel.setColumnType("clob");
/* 241:191 */       return;
/* 242:    */     }
/* 243:192 */     if (dbtype.equals("LONGBLOB"))
/* 244:    */     {
/* 245:193 */       columnModel.setColumnType("clob");
/* 246:194 */       return;
/* 247:    */     }
/* 248:195 */     if (dbtype.equals("IMAGE"))
/* 249:    */     {
/* 250:196 */       columnModel.setColumnType("clob");
/* 251:197 */       return;
/* 252:    */     }
/* 253:198 */     if (dbtype.equals("OID"))
/* 254:    */     {
/* 255:199 */       columnModel.setColumnType("clob");
/* 256:200 */       return;
/* 257:    */     }
/* 258:201 */     if (dbtype.equals("CLOB"))
/* 259:    */     {
/* 260:202 */       columnModel.setColumnType("clob");
/* 261:203 */       return;
/* 262:    */     }
/* 263:204 */     if (dbtype.equals("TINYTEXT"))
/* 264:    */     {
/* 265:205 */       columnModel.setColumnType("clob");
/* 266:206 */       return;
/* 267:    */     }
/* 268:207 */     if (dbtype.equals("TEXT"))
/* 269:    */     {
/* 270:208 */       columnModel.setColumnType("clob");
/* 271:209 */       return;
/* 272:    */     }
/* 273:210 */     if (dbtype.equals("MEDIUMTEXT"))
/* 274:    */     {
/* 275:211 */       columnModel.setColumnType("clob");
/* 276:212 */       return;
/* 277:    */     }
/* 278:213 */     if (dbtype.equals("LONGTEXT"))
/* 279:    */     {
/* 280:214 */       columnModel.setColumnType("clob");
/* 281:215 */       return;
/* 282:    */     }
/* 283:216 */     if (dbtype.equals("NTEXT"))
/* 284:    */     {
/* 285:217 */       columnModel.setColumnType("clob");
/* 286:218 */       return;
/* 287:    */     }
/* 288:219 */     if (dbtype.equals("NCLOB"))
/* 289:    */     {
/* 290:220 */       columnModel.setColumnType("clob");
/* 291:221 */       return;
/* 292:    */     }
/* 293:223 */     columnModel.setColumnType("varchar");
/* 294:224 */     columnModel.setCharLen(length);
/* 295:    */   }
/* 296:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.colmap.H2ColumnMap
 * JD-Core Version:    0.7.0.1
 */