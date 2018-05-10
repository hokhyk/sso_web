/*     */ package com.kingdee.shr.sso.client.util;
/*     */ 
/*     */ import com.kingdee.shr.sso.client.ltpa.LtpaToken;
/*     */ import com.kingdee.shr.sso.client.ltpa.LtpaTokenManager;
/*     */ import com.kingdee.shr.sso.client.user.IUserNameBuilder;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SSOUtil
/*     */ {
/*     */   private static final String EMPTY = "";
/*     */   private static final String SSO_CONFIG = "/WEB-INF/shr-ssoClient.properties";
/*  25 */   private static Properties properties = null;
/*     */   
/*     */   public static String generateUrl(HttpServletRequest request) {
/*  28 */     return generateUrl(request, null);
/*     */   }
/*     */   
/*     */   public static String generateUrl(HttpServletRequest request, String serverUrl)
/*     */   {
/*  33 */     Map parameters = new HashMap();
/*  34 */     assembleLoginParameters(request, parameters);
/*  35 */     System.out.println("----username--" + parameters.get("username"));
/*  36 */     if ((parameters.get("username") == null) || ("null".equals((String)parameters.get("username"))) || ("".equals((String)parameters.get("username")))) {
/*  37 */       return "userNameIsNull";
/*     */     }
/*  39 */     String loginUrl = getLoginUrl(request, serverUrl);
/*  40 */     return UrlUtil.assembleUrl(loginUrl, parameters);
/*     */   }
/*     */   
/*     */   public static Map assembleLoginParameters(HttpServletRequest request, Map parameters)
/*     */   {
/*  45 */     if (parameters == null) {
/*  46 */       parameters = new HashMap();
/*     */     }
/*     */     
/*  49 */     Properties properties = getConfig(request);
/*  50 */     if (properties != null) {
/*  51 */       String userName = (String)parameters.get("username");
/*  52 */       if (userName == null) {
/*  53 */         userName = getUserName(request, properties);
/*     */       }
/*  55 */       parameters.put("username", userName);
/*  56 */       if (userName != null) {
/*  57 */         String authPattern = properties.getProperty("auth.pattern");
/*  58 */         String password = LtpaTokenManager.generate(userName, LtpaTokenManager.getDefaultLtpaConfig(), authPattern).toString();
/*  59 */         parameters.put("password", password);
/*     */       } else {
/*  61 */         System.out.println("用户名为空");
/*     */       }
/*     */     }
/*     */     
/*  65 */     String redirectTo = request.getParameter("redirectTo");
/*  66 */     if ((redirectTo != null) && (redirectTo.trim() != null)) {
/*  67 */       parameters.put("redirectTo", redirectTo);
/*     */     }
/*  69 */     String callback = request.getParameter("callback");
/*  70 */     if ((callback != null) && (callback.trim() != null)) {
/*  71 */       parameters.put("callback", callback);
/*     */     }
/*     */     
/*  74 */     return parameters;
/*     */   }
/*     */   
/*     */   public static Properties getConfig(HttpServletRequest request) {
/*  78 */     if (properties != null) {
/*  79 */       return properties;
/*     */     }
/*     */     
/*  82 */     ServletContext servletContext = request.getSession().getServletContext();
/*  83 */     InputStream inputStream = servletContext.getResourceAsStream("/WEB-INF/shr-ssoClient.properties");
/*  84 */     properties = new Properties();
/*     */     try {
/*  86 */       properties.load(inputStream);
/*     */     } catch (IOException e) {
/*  88 */       e.printStackTrace();
/*  89 */       return null;
/*     */     }
/*  91 */     return properties;
/*     */   }
/*     */   
/*     */   private static String getUserName(HttpServletRequest request, Properties properties)
/*     */   {
/*  96 */     String userNameBuilder = properties.getProperty("userNameBuilder");
/*  97 */     if (userNameBuilder == null) {
/*  98 */       return null;
/*     */     }
/*     */     try
/*     */     {
/* 102 */       Class clazz = Class.forName(userNameBuilder);
/* 103 */       Object obj = clazz.newInstance();
/* 104 */       if ((obj instanceof IUserNameBuilder)) {
/* 105 */         return ((IUserNameBuilder)obj).getUserName(request);
/*     */       }
/*     */     } catch (ClassNotFoundException e) {
/* 108 */       e.printStackTrace();
/*     */     } catch (InstantiationException e) {
/* 110 */       e.printStackTrace();
/*     */     } catch (IllegalAccessException e) {
/* 112 */       e.printStackTrace();
/*     */     }
/*     */     
/* 115 */     return null;
/*     */   }
/*     */   
/*     */   public static String getLoginUrl(HttpServletRequest request, String serverUrl) {
/* 119 */     Properties properties = getConfig(request);
/* 120 */     if (properties == null) {
/* 121 */       return serverUrl;
/*     */     }
/*     */     
/* 124 */     if (serverUrl == null) {
/* 125 */       serverUrl = properties.getProperty("server.url");
/*     */     }
/* 127 */     String serverPath = properties.getProperty("server.path");
/* 128 */     return serverUrl + serverPath;
/*     */   }
/*     */   
/*     */   public static String getServerUrl(HttpServletRequest request) {
/* 132 */     Properties properties = getConfig(request);
/* 133 */     String serverUrl = null;
/* 134 */     if (properties != null) {
/* 135 */       serverUrl = properties.getProperty("server.url");
/*     */     }
/* 137 */     return serverUrl;
/*     */   }
/*     */ }


/* Location:              F:\1 人力资源开发服务公司\53 集团人力系统管理员管维方案\SHR二开资料\单点登录-8.2及以上版本等\demo示例\第三方应用集成s-HR\sso_web\WEB-INF\lib\shr_sso_client_820.jar!\com\kingdee\shr\sso\client\util\SSOUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */