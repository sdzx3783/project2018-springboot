/*   1:    */ package com.hotent.core.table;
/*   2:    */ 
/*   3:    */ import com.hotent.core.util.AppConfigUtil;
/*   4:    */ import com.hotent.core.util.StringUtil;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ 
/*   8:    */ public class TableModel
/*   9:    */ {
/*  10:    */   public static final String PK_COLUMN_NAME = "ID";
/*  11:    */   public static final String FK_COLUMN_NAME = "REFID";
/*  12: 25 */   public static String CUSTOMER_COLUMN_PREFIX = "F_";
/*  13: 29 */   public static String CUSTOMER_TABLE_PREFIX = "W_";
/*  14:    */   public static final String CUSTOMER_INDEX_PREFIX = "IDX_";
/*  15:    */   public static final String CUSTOMER_TABLE_HIS_SUFFIX = "_HISTORY";
/*  16:    */   public static final String CUSTOMER_TABLE_COMM_PREFIX = "TT_";
/*  17:    */   public static final String CUSTOMER_COLUMN_CURRENTUSERID = "curentUserId_";
/*  18:    */   public static final String CUSTOMER_COLUMN_CURRENTORGID = "curentOrgId_";
/*  19:    */   public static final String CUSTOMER_COLUMN_FLOWRUNID = "flowRunId_";
/*  20:    */   public static final String CUSTOMER_COLUMN_DEFID = "defId_";
/*  21:    */   public static final String CUSTOMER_COLUMN_CREATETIME = "CREATETIME";
/*  22: 69 */   private String name = "";
/*  23: 71 */   private String comment = "";
/*  24: 73 */   private List<ColumnModel> columnList = new ArrayList();
/*  25:    */   
/*  26:    */   static
/*  27:    */   {
/*  28: 76 */     String customerTablePrefix = AppConfigUtil.get("CUSTOMER_TABLE_PREFIX");
/*  29: 77 */     CUSTOMER_TABLE_PREFIX = StringUtil.isEmpty(customerTablePrefix) ? CUSTOMER_TABLE_PREFIX : customerTablePrefix;
/*  30:    */     
/*  31: 79 */     String customerColumnPrefix = AppConfigUtil.get("CUSTOMER_COLUMN_PREFIX");
/*  32: 80 */     CUSTOMER_COLUMN_PREFIX = StringUtil.isEmpty(customerColumnPrefix) ? CUSTOMER_COLUMN_PREFIX : customerColumnPrefix;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String getName()
/*  36:    */   {
/*  37: 83 */     return this.name;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setName(String name)
/*  41:    */   {
/*  42: 86 */     this.name = name;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String getComment()
/*  46:    */   {
/*  47: 89 */     if (StringUtil.isNotEmpty(this.comment)) {
/*  48: 90 */       this.comment = this.comment.replace("'", "''");
/*  49:    */     }
/*  50: 92 */     return this.comment;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setComment(String comment)
/*  54:    */   {
/*  55: 95 */     this.comment = comment;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void addColumnModel(ColumnModel model)
/*  59:    */   {
/*  60:102 */     this.columnList.add(model);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public List<ColumnModel> getColumnList()
/*  64:    */   {
/*  65:106 */     return this.columnList;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setColumnList(List<ColumnModel> columnList)
/*  69:    */   {
/*  70:109 */     this.columnList = columnList;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public List<ColumnModel> getPrimayKey()
/*  74:    */   {
/*  75:114 */     List<ColumnModel> pks = new ArrayList();
/*  76:115 */     for (ColumnModel column : this.columnList) {
/*  77:116 */       if (column.getIsPk()) {
/*  78:117 */         pks.add(column);
/*  79:    */       }
/*  80:    */     }
/*  81:120 */     return pks;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String toString()
/*  85:    */   {
/*  86:124 */     return "TableModel [name=" + this.name + ", comment=" + this.comment + ", columnList=" + this.columnList + "]";
/*  87:    */   }
/*  88:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.TableModel
 * JD-Core Version:    0.7.0.1
 */