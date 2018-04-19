/*  1:   */ package com.hotent.core.util;
/*  2:   */ 
/*  3:   */ import org.apache.commons.lang.ArrayUtils;
/*  4:   */ 

/*  5:   */ public class ArrayUtil
/*  6:   */ {
/*  7:   */   public static Long[] convertArray(String[] aryStr)
/*  8:   */   {
/*  9:21 */     if (ArrayUtils.isEmpty(aryStr)) {
/* 10:22 */       return ArrayUtils.EMPTY_LONG_OBJECT_ARRAY;
/* 11:   */     }
/* 12:23 */     Long[] aryLong = new Long[aryStr.length];
/* 13:24 */     for (int i = 0; i < aryStr.length; i++) {
/* 14:25 */       aryLong[i] = Long.valueOf(Long.parseLong(aryStr[i]));
/* 15:   */     }
/* 16:27 */     return aryLong;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static String[] convertArray(Long[] aryLong)
/* 20:   */   {
/* 21:37 */     if (ArrayUtils.isEmpty(aryLong)) {
/* 22:38 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/* 23:   */     }
/* 24:39 */     String[] aryStr = new String[aryLong.length];
/* 25:40 */     for (int i = 0; i < aryStr.length; i++) {
/* 26:41 */       aryStr[i] = String.valueOf(aryStr[i]);
/* 27:   */     }
/* 28:43 */     return aryStr;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static String contact(String[] aryStr, String separator)
/* 32:   */   {
/* 33:53 */     if (aryStr.length == 1) {
/* 34:53 */       return aryStr[0];
/* 35:   */     }
/* 36:54 */     String out = "";
/* 37:55 */     for (int i = 0; i < aryStr.length; i++) {
/* 38:56 */       if (i == 0) {
/* 39:57 */         out = out + aryStr[i];
/* 40:   */       } else {
/* 41:60 */         out = out + separator + aryStr[i];
/* 42:   */       }
/* 43:   */     }
/* 44:63 */     return out;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public static String addQuote(String val)
/* 48:   */   {
/* 49:73 */     String[] aryVal = val.split(",");
/* 50:75 */     if (aryVal.length == 1) {
/* 51:75 */       return "'" + val + "'";
/* 52:   */     }
/* 53:77 */     String tmp = "";
/* 54:78 */     for (int i = 0; i < aryVal.length; i++) {
/* 55:79 */       if (i == 0) {
/* 56:80 */         tmp = tmp + "'" + aryVal[i] + "'";
/* 57:   */       } else {
/* 58:83 */         tmp = tmp + ",'" + aryVal[i] + "'";
/* 59:   */       }
/* 60:   */     }
/* 61:86 */     return tmp;
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.util.ArrayUtil
 * JD-Core Version:    0.7.0.1
 */