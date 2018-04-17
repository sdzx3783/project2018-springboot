/*   1:    */ package com.hotent.core.table;
/*   2:    */ 
/*   3:    */ import com.hotent.core.util.StringUtil;
/*   4:    */ 
/*   5:    */ public class ColumnModel
/*   6:    */ {
/*   7:    */   public static final String COLUMNTYPE_INT = "int";
/*   8:    */   public static final String COLUMNTYPE_VARCHAR = "varchar";
/*   9:    */   public static final String COLUMNTYPE_CLOB = "clob";
/*  10:    */   public static final String COLUMNTYPE_NUMBER = "number";
/*  11:    */   public static final String COLUMNTYPE_DATE = "date";
/*  12:    */   public static final String COLUMNTYPE_TEXT = "text";
/*  13: 17 */   private String name = "";
/*  14: 19 */   private String comment = "";
/*  15: 21 */   private boolean isPk = false;
/*  16: 23 */   private boolean isFk = false;
/*  17: 25 */   private boolean isNull = true;
/*  18:    */   private String columnType;
/*  19: 29 */   private int charLen = 0;
/*  20: 31 */   private int decimalLen = 0;
/*  21: 33 */   private int intLen = 0;
/*  22: 35 */   private String fkRefTable = "";
/*  23: 37 */   private String fkRefColumn = "";
/*  24: 39 */   private String defaultValue = "";
/*  25: 41 */   private String tableName = "";
/*  26:    */   private String label;
/*  27:    */   private int index;
/*  28:    */   
/*  29:    */   public String getName()
/*  30:    */   {
/*  31: 48 */     return this.name;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setName(String name)
/*  35:    */   {
/*  36: 52 */     this.name = name;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getComment()
/*  40:    */   {
/*  41: 56 */     if (StringUtil.isNotEmpty(this.comment)) {
/*  42: 57 */       this.comment = this.comment.replace("'", "''");
/*  43:    */     }
/*  44: 59 */     return this.comment;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setComment(String comment)
/*  48:    */   {
/*  49: 63 */     this.comment = comment;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean getIsPk()
/*  53:    */   {
/*  54: 67 */     return this.isPk;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setIsPk(boolean isPk)
/*  58:    */   {
/*  59: 71 */     this.isPk = isPk;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean getIsNull()
/*  63:    */   {
/*  64: 75 */     return this.isNull;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setIsNull(boolean isNull)
/*  68:    */   {
/*  69: 79 */     this.isNull = isNull;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String getColumnType()
/*  73:    */   {
/*  74: 83 */     return this.columnType;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setColumnType(String columnType)
/*  78:    */   {
/*  79: 87 */     this.columnType = columnType;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int getCharLen()
/*  83:    */   {
/*  84: 91 */     return this.charLen;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setCharLen(int charLen)
/*  88:    */   {
/*  89: 95 */     this.charLen = charLen;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int getDecimalLen()
/*  93:    */   {
/*  94: 99 */     return this.decimalLen;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setDecimalLen(int decimalLen)
/*  98:    */   {
/*  99:103 */     this.decimalLen = decimalLen;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int getIntLen()
/* 103:    */   {
/* 104:107 */     return this.intLen;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setIntLen(int intLen)
/* 108:    */   {
/* 109:111 */     this.intLen = intLen;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean getIsFk()
/* 113:    */   {
/* 114:115 */     return this.isFk;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setIsFk(boolean isFk)
/* 118:    */   {
/* 119:119 */     this.isFk = isFk;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String getFkRefTable()
/* 123:    */   {
/* 124:123 */     return this.fkRefTable;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setFkRefTable(String fkRefTable)
/* 128:    */   {
/* 129:127 */     this.fkRefTable = fkRefTable;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String getFkRefColumn()
/* 133:    */   {
/* 134:131 */     return this.fkRefColumn;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setFkRefColumn(String fkRefColumn)
/* 138:    */   {
/* 139:135 */     this.fkRefColumn = fkRefColumn;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String getDefaultValue()
/* 143:    */   {
/* 144:139 */     return this.defaultValue;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setDefaultValue(String defaultValue)
/* 148:    */   {
/* 149:143 */     this.defaultValue = defaultValue;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String getTableName()
/* 153:    */   {
/* 154:147 */     return this.tableName;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setTableName(String tableName)
/* 158:    */   {
/* 159:151 */     this.tableName = tableName;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String getLabel()
/* 163:    */   {
/* 164:155 */     return this.label;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setLabel(String label)
/* 168:    */   {
/* 169:159 */     this.label = label;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public int getIndex()
/* 173:    */   {
/* 174:163 */     return this.index;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setIndex(int index)
/* 178:    */   {
/* 179:167 */     this.index = index;
/* 180:    */   }
/* 181:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.ColumnModel
 * JD-Core Version:    0.7.0.1
 */