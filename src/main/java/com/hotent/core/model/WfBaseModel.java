/*  1:   */ package com.hotent.core.model;
/*  2:   */ 
/*  3:   */ public class WfBaseModel
/*  4:   */   extends BaseModel
/*  5:   */ {
/*  6:12 */   private Long taskId = Long.valueOf(0L);
/*  7:16 */   private Long runId = Long.valueOf(0L);
/*  8:20 */   private Long actInstId = Long.valueOf(0L);
/*  9:24 */   private String nodeId = "";
/* 10:28 */   private String nodeName = "";
/* 11:   */   
/* 12:   */   public Long getTaskId()
/* 13:   */   {
/* 14:31 */     return this.taskId;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void setTaskId(Long taskId)
/* 18:   */   {
/* 19:35 */     this.taskId = taskId;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Long getRunId()
/* 23:   */   {
/* 24:39 */     return this.runId;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void setRunId(Long runId)
/* 28:   */   {
/* 29:43 */     this.runId = runId;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Long getActInstId()
/* 33:   */   {
/* 34:47 */     return this.actInstId;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void setActInstId(Long actInstId)
/* 38:   */   {
/* 39:51 */     this.actInstId = actInstId;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public String getNodeId()
/* 43:   */   {
/* 44:55 */     return this.nodeId;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void setNodeId(String nodeId)
/* 48:   */   {
/* 49:59 */     this.nodeId = nodeId;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public String getNodeName()
/* 53:   */   {
/* 54:63 */     return this.nodeName;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void setNodeName(String nodeName)
/* 58:   */   {
/* 59:67 */     this.nodeName = nodeName;
/* 60:   */   }
/* 61:   */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.model.WfBaseModel
 * JD-Core Version:    0.7.0.1
 */