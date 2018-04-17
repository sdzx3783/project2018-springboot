/*  1:   */ package com.hotent.core.table;
/*  2:   */ 
/*  3:   */ public class SqlTypeConst
/*  4:   */ {
/*  5:   */   public static final String ORACLE = "oracle";
/*  6:   */   public static final String MYSQL = "mysql";
/*  7:   */   public static final String SQLSERVER = "mssql";
/*  8:   */   public static final String DB2 = "db2";
/*  9:   */   public static final String DERBY = "derby";
/* 10:   */   public static final String HBASE = "hbase";
/* 11:   */   public static final String HIVE = "hive";
/* 12:   */   public static final String H2 = "h2";
/* 13:   */   public static final String JTDS = "jtds";
/* 14:   */   public static final String MOCK = "mock";
/* 15:   */   public static final String HSQL = "hsql";
/* 16:   */   public static final String POSTGRESQL = "postgresql";
/* 17:   */   public static final String SYBASE = "sybase";
/* 18:   */   public static final String SQLITE = "sqlite";
/* 19:   */   public static final String MCKOI = "mckoi";
/* 20:   */   public static final String CLOUDSCAPE = "cloudscape";
/* 21:   */   public static final String INFORMIX = "informix";
/* 22:   */   public static final String TIMESTEN = "timesten";
/* 23:   */   public static final String AS400 = "as400";
/* 24:   */   public static final String SAPDB = "sapdb";
/* 25:   */   public static final String JSQLCONNECT = "JSQLConnect";
/* 26:   */   public static final String JTURBO = "JTurbo";
/* 27:   */   public static final String FIREBIRDSQL = "firebirdsql";
/* 28:   */   public static final String INTERBASE = "interbase";
/* 29:   */   public static final String POINTBASE = "pointbase";
/* 30:   */   public static final String EDBC = "edbc";
/* 31:   */   public static final String MIMER = "mimer";
/* 32:   */   public static final String DM = "dm";
/* 33:   */   private static final String INGRES = "ingres";
/* 34:   */   
/* 35:   */   public static String getDbType(String rawUrl)
/* 36:   */   {
/* 37:41 */     if (rawUrl == null) {
/* 38:42 */       return null;
/* 39:   */     }
/* 40:44 */     if (rawUrl.startsWith("jdbc:derby:")) {
/* 41:45 */       return "derby";
/* 42:   */     }
/* 43:46 */     if (rawUrl.startsWith("jdbc:mysql:")) {
/* 44:47 */       return "mysql";
/* 45:   */     }
/* 46:48 */     if (rawUrl.startsWith("jdbc:oracle:")) {
/* 47:49 */       return "oracle";
/* 48:   */     }
/* 49:50 */     if ((rawUrl.startsWith("jdbc:microsoft:")) || (rawUrl.startsWith("jdbc:sqlserver:"))) {
/* 50:51 */       return "mssql";
/* 51:   */     }
/* 52:52 */     if (rawUrl.startsWith("jdbc:sybase:Tds:")) {
/* 53:53 */       return "sybase";
/* 54:   */     }
/* 55:54 */     if (rawUrl.startsWith("jdbc:jtds:")) {
/* 56:55 */       return "jtds";
/* 57:   */     }
/* 58:56 */     if ((rawUrl.startsWith("jdbc:fake:")) || (rawUrl.startsWith("jdbc:mock:"))) {
/* 59:57 */       return "mock";
/* 60:   */     }
/* 61:58 */     if (rawUrl.startsWith("jdbc:postgresql:")) {
/* 62:59 */       return "postgresql";
/* 63:   */     }
/* 64:60 */     if (rawUrl.startsWith("jdbc:hsqldb:")) {
/* 65:61 */       return "hsql";
/* 66:   */     }
/* 67:62 */     if (rawUrl.startsWith("jdbc:db2:")) {
/* 68:63 */       return "db2";
/* 69:   */     }
/* 70:64 */     if (rawUrl.startsWith("jdbc:sqlite:")) {
/* 71:65 */       return "sqlite";
/* 72:   */     }
/* 73:66 */     if (rawUrl.startsWith("jdbc:ingres:")) {
/* 74:67 */       return "ingres";
/* 75:   */     }
/* 76:68 */     if (rawUrl.startsWith("jdbc:h2:")) {
/* 77:69 */       return "h2";
/* 78:   */     }
/* 79:70 */     if (rawUrl.startsWith("jdbc:mckoi:")) {
/* 80:71 */       return "mckoi";
/* 81:   */     }
/* 82:72 */     if (rawUrl.startsWith("jdbc:cloudscape:")) {
/* 83:73 */       return "cloudscape";
/* 84:   */     }
/* 85:74 */     if (rawUrl.startsWith("jdbc:informix-sqli:")) {
/* 86:75 */       return "informix";
/* 87:   */     }
/* 88:76 */     if (rawUrl.startsWith("jdbc:timesten:")) {
/* 89:77 */       return "timesten";
/* 90:   */     }
/* 91:78 */     if (rawUrl.startsWith("jdbc:as400:")) {
/* 92:79 */       return "as400";
/* 93:   */     }
/* 94:80 */     if (rawUrl.startsWith("jdbc:sapdb:")) {
/* 95:81 */       return "sapdb";
/* 96:   */     }
/* 97:82 */     if (rawUrl.startsWith("jdbc:JSQLConnect:")) {
/* 98:83 */       return "JSQLConnect";
/* 99:   */     }
/* :0:84 */     if (rawUrl.startsWith("jdbc:JTurbo:")) {
/* :1:85 */       return "JTurbo";
/* :2:   */     }
/* :3:86 */     if (rawUrl.startsWith("jdbc:firebirdsql:")) {
/* :4:87 */       return "firebirdsql";
/* :5:   */     }
/* :6:88 */     if (rawUrl.startsWith("jdbc:interbase:")) {
/* :7:89 */       return "interbase";
/* :8:   */     }
/* :9:90 */     if (rawUrl.startsWith("jdbc:pointbase:")) {
/* ;0:91 */       return "pointbase";
/* ;1:   */     }
/* ;2:92 */     if (rawUrl.startsWith("jdbc:edbc:")) {
/* ;3:93 */       return "edbc";
/* ;4:   */     }
/* ;5:94 */     if (rawUrl.startsWith("jdbc:mimer:multi1:")) {
/* ;6:95 */       return "mimer";
/* ;7:   */     }
/* ;8:96 */     if (rawUrl.startsWith("jdbc:dm:")) {
/* ;9:97 */       return "dm";
/* <0:   */     }
/* <1:99 */     return null;
/* <2:   */   }
/* <3:   */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.SqlTypeConst
 * JD-Core Version:    0.7.0.1
 */