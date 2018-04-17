/*  1:   */ package com.hotent.core.table;
/*  2:   */ 
/*  3:   */ import com.hotent.core.table.impl.TableMetaFactory;
/*  4:   */ import org.springframework.beans.factory.FactoryBean;
/*  5:   */ 
/*  6:   */ public class TableMetaFactoryBean
/*  7:   */   implements FactoryBean<BaseTableMeta>
/*  8:   */ {
/*  9:   */   private BaseTableMeta tableMeta;
/* 10:27 */   private String dbType = "mysql";
/* 11:   */   
/* 12:   */   public BaseTableMeta getObject()
/* 13:   */     throws Exception
/* 14:   */   {
/* 15:33 */     this.tableMeta = TableMetaFactory.getMetaData("dataSource_Default");
/* 16:   */     
/* 17:35 */     return this.tableMeta;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setDbType(String dbType)
/* 21:   */   {
/* 22:45 */     this.dbType = dbType;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public Class<?> getObjectType()
/* 26:   */   {
/* 27:56 */     return BaseTableMeta.class;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean isSingleton()
/* 31:   */   {
/* 32:62 */     return true;
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.TableMetaFactoryBean
 * JD-Core Version:    0.7.0.1
 */