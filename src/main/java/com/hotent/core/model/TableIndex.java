/*   1:    */ package com.hotent.core.model;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ 
/*   5:    */ public class TableIndex
/*   6:    */   extends BaseModel
/*   7:    */   implements Cloneable
/*   8:    */ {
/*   9:    */   private static final long serialVersionUID = 1L;
/*  10: 17 */   public static String INDEX_TYPE_BITMAP = "BITMAP";
/*  11: 18 */   public static String INDEX_TYPE_BTREE = "BTREE";
/*  12: 19 */   public static String INDEX_TYPE_FUNCTION = "FUNCTION";
/*  13: 20 */   public static String INDEX_TYPE_HEAP = "HEAP";
/*  14: 21 */   public static String INDEX_TYPE_CLUSTERED = "CLUSTERED";
/*  15: 22 */   public static String INDEX_TYPE_NONCLUSTERED = "NONCLUSTERED";
/*  16: 23 */   public static String INDEX_TYPE_XML = "XML";
/*  17: 24 */   public static String INDEX_TYPE_SPATIAL = "SPATIAL";
/*  18: 25 */   public static String INDEX_TYPE_REG = "REGULAR";
/*  19: 26 */   public static String INDEX_TYPE_DIM = "DIMENSIONBLOCK";
/*  20: 27 */   public static String INDEX_TYPE_BLOK = "BLOCK";
/*  21: 29 */   public static String TABLE_TYPE_TABLE = "TABLE";
/*  22: 30 */   public static String TABLE_TYPE_VIEW = "VIEW";
/*  23: 32 */   public static String INDEX_STATUS_VALIDATE = "VALIDATE";
/*  24: 33 */   public static String INDEX_STATUS_INVALIDATE = "INVALIDATE";
/*  25:    */   private String indexTable;
/*  26:    */   private String tableType;
/*  27:    */   private String indexName;
/*  28:    */   private String indexType;
/*  29:    */   private String indexStatus;
/*  30:    */   private List<String> indexFields;
/*  31:    */   private boolean unique;
/*  32:    */   private String indexDdl;
/*  33:    */   private String indexComment;
/*  34:    */   private boolean pkIndex;
/*  35:    */   
/*  36:    */   public String getIndexName()
/*  37:    */   {
/*  38: 46 */     return this.indexName;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setIndexName(String indexName)
/*  42:    */   {
/*  43: 49 */     this.indexName = indexName;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getIndexType()
/*  47:    */   {
/*  48: 52 */     return this.indexType;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setIndexType(String indexType)
/*  52:    */   {
/*  53: 55 */     this.indexType = indexType;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public List<String> getIndexFields()
/*  57:    */   {
/*  58: 58 */     return this.indexFields;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIndexFields(List<String> indexFields)
/*  62:    */   {
/*  63: 61 */     this.indexFields = indexFields;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String getIndexComment()
/*  67:    */   {
/*  68: 64 */     return this.indexComment;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIndexComment(String indexComment)
/*  72:    */   {
/*  73: 67 */     this.indexComment = indexComment;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getIndexTable()
/*  77:    */   {
/*  78: 70 */     return this.indexTable;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIndexTable(String indexTable)
/*  82:    */   {
/*  83: 73 */     this.indexTable = indexTable;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getIndexStatus()
/*  87:    */   {
/*  88: 76 */     return this.indexStatus;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setIndexStatus(String indexStatus)
/*  92:    */   {
/*  93: 79 */     this.indexStatus = indexStatus;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getTableType()
/*  97:    */   {
/*  98: 82 */     return this.tableType;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setTableType(String tableType)
/* 102:    */   {
/* 103: 85 */     this.tableType = tableType;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getIndexDdl()
/* 107:    */   {
/* 108: 88 */     return this.indexDdl;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setIndexDdl(String indexDdl)
/* 112:    */   {
/* 113: 91 */     this.indexDdl = indexDdl;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public boolean isUnique()
/* 117:    */   {
/* 118: 94 */     return this.unique;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setUnique(boolean unique)
/* 122:    */   {
/* 123: 97 */     this.unique = unique;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public boolean isPkIndex()
/* 127:    */   {
/* 128:100 */     return this.pkIndex;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setPkIndex(boolean pkIndex)
/* 132:    */   {
/* 133:103 */     this.pkIndex = pkIndex;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String toString()
/* 137:    */   {
/* 138:108 */     return "BpmFormTableIndex [indexTable=" + this.indexTable + ", tableType=" + this.tableType + ", indexName=" + this.indexName + ", indexType=" + this.indexType + ", indexStatus=" + this.indexStatus + ", indexFields=" + this.indexFields + ", unique=" + this.unique + ", indexDdl=" + this.indexDdl + ", indexComment=" + this.indexComment + ", pkIndex=" + this.pkIndex + "]";
/* 139:    */   }
/* 140:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.model.TableIndex
 * JD-Core Version:    0.7.0.1
 */