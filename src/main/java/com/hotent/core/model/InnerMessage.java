/*   1:    */ package com.hotent.core.model;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Date;
/*   5:    */ 
/*   6:    */ public class InnerMessage
/*   7:    */   implements Serializable
/*   8:    */ {
/*   9:    */   private String subject;
/*  10:    */   private String from;
/*  11:    */   private String fromName;
/*  12:    */   private String to;
/*  13:    */   private String toName;
/*  14:    */   private Date sendDate;
/*  15:    */   private String content;
/*  16:    */   private short canReply;
/*  17:    */   
/*  18:    */   public short getCanReply()
/*  19:    */   {
/*  20: 48 */     return this.canReply;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void setCanReply(short canReply)
/*  24:    */   {
/*  25: 52 */     this.canReply = canReply;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public String getSubject()
/*  29:    */   {
/*  30: 56 */     return this.subject;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String getFromName()
/*  34:    */   {
/*  35: 60 */     return this.fromName;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setFromName(String fromName)
/*  39:    */   {
/*  40: 64 */     this.fromName = fromName;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String getToName()
/*  44:    */   {
/*  45: 68 */     return this.toName;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setToName(String toName)
/*  49:    */   {
/*  50: 72 */     this.toName = toName;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setSubject(String subject)
/*  54:    */   {
/*  55: 76 */     this.subject = subject;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String getFrom()
/*  59:    */   {
/*  60: 80 */     return this.from;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setFrom(String from)
/*  64:    */   {
/*  65: 84 */     this.from = from;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String getTo()
/*  69:    */   {
/*  70: 88 */     return this.to;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setTo(String to)
/*  74:    */   {
/*  75: 92 */     this.to = to;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Date getSendDate()
/*  79:    */   {
/*  80: 96 */     return this.sendDate;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setSendDate(Date sendDate)
/*  84:    */   {
/*  85:100 */     this.sendDate = sendDate;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String getContent()
/*  89:    */   {
/*  90:104 */     return this.content;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setContent(String content)
/*  94:    */   {
/*  95:108 */     this.content = content;
/*  96:    */   }
/*  97:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.model.InnerMessage
 * JD-Core Version:    0.7.0.1
 */