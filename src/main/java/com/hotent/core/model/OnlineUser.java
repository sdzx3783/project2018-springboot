/*  1:   */ package com.hotent.core.model;
/*  2:   */ 
/*  3:   */ public class OnlineUser
/*  4:   */ {
/*  5:   */   private String sessionId;
/*  6:   */   private Long userId;
/*  7:   */   private String username;
/*  8:   */   private String fullname;
/*  9:   */   private Long orgId;
/* 10:   */   protected String orgName;
/* 11:   */   private Short title;
/* 12:   */   
/* 13:   */   public String getSessionId()
/* 14:   */   {
/* 15:41 */     return this.sessionId;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setSessionId(String sessionId)
/* 19:   */   {
/* 20:45 */     this.sessionId = sessionId;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Long getUserId()
/* 24:   */   {
/* 25:49 */     return this.userId;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setUserId(Long userId)
/* 29:   */   {
/* 30:53 */     this.userId = userId;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String getUsername()
/* 34:   */   {
/* 35:57 */     return this.username;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void setUsername(String username)
/* 39:   */   {
/* 40:61 */     this.username = username;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String getFullname()
/* 44:   */   {
/* 45:65 */     return this.fullname;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void setFullname(String fullname)
/* 49:   */   {
/* 50:69 */     this.fullname = fullname;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public Long getOrgId()
/* 54:   */   {
/* 55:73 */     return this.orgId;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public void setOrgId(Long orgId)
/* 59:   */   {
/* 60:77 */     this.orgId = orgId;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public String getOrgName()
/* 64:   */   {
/* 65:81 */     return this.orgName;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public void setOrgName(String orgName)
/* 69:   */   {
/* 70:85 */     this.orgName = orgName;
/* 71:   */   }
/* 72:   */   
/* 73:   */   public Short getTitle()
/* 74:   */   {
/* 75:89 */     return this.title;
/* 76:   */   }
/* 77:   */   
/* 78:   */   public void setTitle(Short title)
/* 79:   */   {
/* 80:93 */     this.title = title;
/* 81:   */   }
/* 82:   */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.model.OnlineUser
 * JD-Core Version:    0.7.0.1
 */