/*   1:    */ package com.hotent.core.util;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.net.URL;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.servlet.ServletContext;
/*   9:    */ import org.springframework.beans.BeansException;
/*  10:    */ import org.springframework.context.ApplicationContext;
/*  11:    */ import org.springframework.context.ApplicationContextAware;
/*  12:    */ import org.springframework.context.ApplicationEvent;
/*  13:    */ 
/*  14:    */ public class AppUtil
/*  15:    */   implements ApplicationContextAware
/*  16:    */ {
/*  17:    */   private static ApplicationContext applicationContext;
/*  18:    */   private static ServletContext servletContext;
/*  19:    */   
/*  20:    */   public static void init(ServletContext _servletContext)
/*  21:    */   {
/*  22: 48 */     servletContext = _servletContext;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static ServletContext getServletContext()
/*  26:    */     throws Exception
/*  27:    */   {
/*  28: 57 */     return servletContext;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setApplicationContext(ApplicationContext contex)
/*  32:    */     throws BeansException
/*  33:    */   {
/*  34: 64 */     applicationContext = contex;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static ApplicationContext getContext()
/*  38:    */   {
/*  39: 72 */     return applicationContext;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static List<Class> getImplClass(Class clazz)
/*  43:    */     throws ClassNotFoundException
/*  44:    */   {
/*  45: 83 */     List<Class> list = new ArrayList();
/*  46:    */     
/*  47: 85 */     Map map = applicationContext.getBeansOfType(clazz);
/*  48: 86 */     for (Object obj : map.values())
/*  49:    */     {
/*  50: 87 */       String name = obj.getClass().getName();
/*  51: 88 */       int pos = name.indexOf("$$");
/*  52: 89 */       if (pos > 0) {
/*  53: 90 */         name = name.substring(0, name.indexOf("$$"));
/*  54:    */       }
/*  55: 92 */       Class cls = Class.forName(name);
/*  56:    */       
/*  57: 94 */       list.add(cls);
/*  58:    */     }
/*  59: 96 */     return list;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static Map<String, Object> getImplInstance(Class clazz)
/*  63:    */     throws ClassNotFoundException
/*  64:    */   {
/*  65:108 */     Map map = applicationContext.getBeansOfType(clazz);
/*  66:    */     
/*  67:110 */     return map;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static <C> C getBean(Class<C> cls)
/*  71:    */   {
/*  72:121 */     return applicationContext.getBean(cls);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static Object getBean(String beanId)
/*  76:    */   {
/*  77:130 */     return applicationContext.getBean(beanId);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static String getAppAbsolutePath()
/*  81:    */   {
/*  82:138 */     return servletContext.getRealPath("/");
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static String getRealPath(String path)
/*  86:    */   {
/*  87:147 */     return servletContext.getRealPath(path);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static String getClasspath()
/*  91:    */   {
/*  92:159 */     String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
/*  93:160 */     String rootPath = "";
/*  94:162 */     if ("\\".equals(File.separator))
/*  95:    */     {
/*  96:163 */       rootPath = classPath.substring(1);
/*  97:164 */       rootPath = rootPath.replace("/", "\\");
/*  98:    */     }
/*  99:167 */     if ("/".equals(File.separator))
/* 100:    */     {
/* 101:168 */       rootPath = classPath.substring(1);
/* 102:169 */       rootPath = rootPath.replace("\\", "/");
/* 103:    */     }
/* 104:171 */     return rootPath;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public static void publishEvent(ApplicationEvent applicationEvent)
/* 108:    */   {
/* 109:179 */     applicationContext.publishEvent(applicationEvent);
/* 110:    */   }
/* 111:    */ }


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.util.AppUtil
 * JD-Core Version:    0.7.0.1
 */