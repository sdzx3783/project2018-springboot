/*  1:   */ package com.hotent.core.model;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Date;
/*  5:   */ 
/*  6:   */ public class BaseModel
/*  7:   */   implements Serializable
/*  8:   */ {
/*  9:   */   protected Long createBy;
/* 10:   */   protected Date createtime;
/* 11:   */   protected Date updatetime;
/* 12:   */   protected Long updateBy;
/* 13:   */   
/* 14:   */   public Long getCreateBy()
/* 15:   */   {
/* 16:43 */     return this.createBy;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setCreateBy(Long createBy)
/* 20:   */   {
/* 21:48 */     this.createBy = createBy;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Date getCreatetime()
/* 25:   */   {
/* 26:53 */     return this.createtime;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void setCreatetime(Date createtime)
/* 30:   */   {
/* 31:58 */     this.createtime = createtime;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Date getUpdatetime()
/* 35:   */   {
/* 36:63 */     return this.updatetime;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void setUpdatetime(Date updatetime)
/* 40:   */   {
/* 41:68 */     this.updatetime = updatetime;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public Long getUpdateBy()
/* 45:   */   {
/* 46:73 */     return this.updateBy;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void setUpdateBy(Long updateBy)
/* 50:   */   {
/* 51:78 */     this.updateBy = updateBy;
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.model.BaseModel
 * JD-Core Version:    0.7.0.1
 */