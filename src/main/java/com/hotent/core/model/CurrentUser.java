/*  1:   */ package com.hotent.core.model;
/*  2:   */ 
/*  3:   */ public class CurrentUser
/*  4:   */ {
/*  5:12 */   private Long userId = Long.valueOf(0L);
/*  6:16 */   private String account = "";
/*  7:20 */   private String name = "";
/*  8:24 */   private Long orgId = Long.valueOf(0L);
/*  9:28 */   private Long posId = Long.valueOf(0L);
/* 10:   */   
/* 11:   */   public Long getUserId()
/* 12:   */   {
/* 13:32 */     return this.userId;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void setUserId(Long userId)
/* 17:   */   {
/* 18:35 */     this.userId = userId;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getAccount()
/* 22:   */   {
/* 23:38 */     return this.account;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void setAccount(String account)
/* 27:   */   {
/* 28:41 */     this.account = account;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String getName()
/* 32:   */   {
/* 33:44 */     return this.name;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void setName(String name)
/* 37:   */   {
/* 38:47 */     this.name = name;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public Long getOrgId()
/* 42:   */   {
/* 43:50 */     return this.orgId;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void setOrgId(Long orgId)
/* 47:   */   {
/* 48:53 */     this.orgId = orgId;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public Long getPosId()
/* 52:   */   {
/* 53:56 */     return this.posId;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void setPosId(Long posId)
/* 57:   */   {
/* 58:59 */     this.posId = posId;
/* 59:   */   }
/* 60:   */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.model.CurrentUser
 * JD-Core Version:    0.7.0.1
 */