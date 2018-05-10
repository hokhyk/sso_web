/*     */ package com.kingdee.shr.sso.client.util;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.httpclient.Cookie;
/*     */ 
/*     */ public class UrlUtil
/*     */ {
/*     */   private static final String EQUALS = "=";
/*     */   
/*     */   public static String assembleUrl(String url, Map<String, Object> params)
/*     */   {
/*  16 */     StringBuilder redirectUrl = new StringBuilder(url);
/*     */     
/*  18 */     if (redirectUrl.indexOf("?") == -1) {
/*  19 */       redirectUrl.append('?');
/*     */     } else {
/*  21 */       redirectUrl.append('&');
/*     */     }
/*     */     
/*  24 */     String key = null;String value = null;
/*  25 */     Object object = null;
/*  26 */     int index = 0;
/*  27 */     Iterator<String> paramIterator = params.keySet().iterator();
/*  28 */     while (paramIterator.hasNext()) {
/*  29 */       key = (String)paramIterator.next();
/*  30 */       object = params.get(key);
/*  31 */       if (object != null)
/*     */       {
/*     */ 
/*     */ 
/*  35 */         if (index != 0) {
/*  36 */           redirectUrl.append('&');
/*     */         }
/*  38 */         redirectUrl.append(key);
/*  39 */         redirectUrl.append("=");
/*  40 */         if ((object instanceof Map))
/*     */         {
/*  42 */           value = toJSON((Map)object);
/*     */         } else {
/*  44 */           value = object.toString();
/*     */         }
/*     */         try {
/*  47 */           redirectUrl.append(URLEncoder.encode(value, "UTF-8"));
/*     */         } catch (UnsupportedEncodingException e) {
/*  49 */           redirectUrl.append(URLEncoder.encode(value));
/*     */         }
/*  51 */         index++;
/*     */       } }
/*  53 */     return redirectUrl.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static String toJSON(Map map)
/*     */   {
/*  63 */     StringBuffer sb = new StringBuffer();
/*     */     
/*  65 */     if ((map != null) && (!map.isEmpty())) {
/*  66 */       sb.append("{");
/*  67 */       Iterator it = map.keySet().iterator();
/*  68 */       int index = 0;
/*  69 */       String key = null;
/*  70 */       Object value = null;
/*  71 */       while (it.hasNext()) {
/*  72 */         key = (String)it.next();
/*  73 */         if (index > 0) {
/*  74 */           sb.append(",");
/*     */         }
/*     */         
/*  77 */         sb.append("\"");
/*  78 */         sb.append(key);
/*  79 */         sb.append("\"");
/*  80 */         sb.append(":");
/*     */         
/*  82 */         value = map.get(key);
/*  83 */         sb.append("\"");
/*  84 */         if (value != null) {
/*  85 */           sb.append(value.toString());
/*     */         }
/*  87 */         sb.append("\"");
/*     */         
/*  89 */         index++;
/*     */       }
/*  91 */       sb.append("}");
/*     */     }
/*     */     
/*  94 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static String cookiesToString(Cookie[] cookies) {
/*  98 */     StringBuffer buf = new StringBuffer();
/*  99 */     Cookie[] arrayOfCookie = cookies;int j = cookies.length; for (int i = 0; i < j; i++) { Cookie cookie = arrayOfCookie[i];
/* 100 */       buf.append(cookie.toString()).append(";");
/*     */     }
/* 102 */     if ((buf != null) && (buf.length() > 0)) {
/* 103 */       return buf.substring(0, buf.length() - 1);
/*     */     }
/* 105 */     return null;
/*     */   }
/*     */ }


/* Location:              F:\1 人力资源开发服务公司\53 集团人力系统管理员管维方案\SHR二开资料\单点登录-8.2及以上版本等\demo示例\第三方应用集成s-HR\sso_web\WEB-INF\lib\shr_sso_client_820.jar!\com\kingdee\shr\sso\client\util\UrlUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */