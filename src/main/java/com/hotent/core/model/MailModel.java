/*   1:    */ package com.hotent.core.model;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Date;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.apache.commons.lang.builder.ToStringBuilder;
/*   7:    */ import org.apache.commons.lang.builder.ToStringStyle;
/*   8:    */ 
/*   9:    */ public class MailModel
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = 1L;
/*  13:    */   private String subject;
/*  14:    */   private String from;
/*  15:    */   private String[] to;
/*  16:    */   private String[] cc;
/*  17:    */   private String[] bcc;
/*  18:    */   private Date sendDate;
/*  19:    */   private String content;
/*  20:    */   private String mailTemplate;
/*  21: 55 */   private Map mailData = null;
/*  22:    */   
/*  23:    */   public String getSubject()
/*  24:    */   {
/*  25: 58 */     return this.subject;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setSubject(String subject)
/*  29:    */   {
/*  30: 61 */     this.subject = subject;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String getFrom()
/*  34:    */   {
/*  35: 65 */     return this.from;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setFrom(String from)
/*  39:    */   {
/*  40: 68 */     this.from = from;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String[] getTo()
/*  44:    */   {
/*  45: 71 */     return this.to;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setTo(String[] to)
/*  49:    */   {
/*  50: 74 */     this.to = to;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String[] getCc()
/*  54:    */   {
/*  55: 78 */     return this.cc;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setCc(String[] cc)
/*  59:    */   {
/*  60: 82 */     this.cc = cc;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String[] getBcc()
/*  64:    */   {
/*  65: 86 */     return this.bcc;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setBcc(String[] bcc)
/*  69:    */   {
/*  70: 90 */     this.bcc = bcc;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Date getSendDate()
/*  74:    */   {
/*  75: 93 */     return this.sendDate;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setSendDate(Date sendDate)
/*  79:    */   {
/*  80: 96 */     this.sendDate = sendDate;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String getContent()
/*  84:    */   {
/*  85: 99 */     return this.content;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setContent(String content)
/*  89:    */   {
/*  90:102 */     this.content = content;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getMailTemplate()
/*  94:    */   {
/*  95:109 */     return this.mailTemplate;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setMailTemplate(String mailTemplate)
/*  99:    */   {
/* 100:112 */     this.mailTemplate = mailTemplate;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Map getMailData()
/* 104:    */   {
/* 105:115 */     return this.mailData;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setMailData(Map mailData)
/* 109:    */   {
/* 110:118 */     this.mailData = mailData;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String toString()
/* 114:    */   {
/* 115:126 */     return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
/* 116:    */   }
/* 117:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.model.MailModel
 * JD-Core Version:    0.7.0.1
 */