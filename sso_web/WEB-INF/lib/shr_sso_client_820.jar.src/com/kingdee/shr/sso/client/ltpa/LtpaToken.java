/*     */ package com.kingdee.shr.sso.client.ltpa;
/*     */ 
/*     */ import com.kingdee.shr.sso.client.util.BASE64Util;
/*     */ import com.kingdee.shr.sso.client.util.LMBCSUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLDecoder;
/*     */ import java.net.URLEncoder;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LtpaToken
/*     */ {
/*     */   private byte[] creation;
/*     */   private Date creationDate;
/*     */   private byte[] digest;
/*     */   private byte[] expires;
/*     */   private Date expiresDate;
/*     */   private byte[] hash;
/*     */   private byte[] header;
/*     */   private String tokenStr;
/*     */   private byte[] rawToken;
/*     */   private byte[] user;
/*     */   
/*     */   public LtpaToken()
/*     */   {
/*  83 */     init();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LtpaToken(String token)
/*     */   {
/*  93 */     init();
/*  94 */     this.tokenStr = token;
/*  95 */     this.rawToken = BASE64Util.decodeAsBytes(token);
/*  96 */     this.user = new byte[this.rawToken.length - 40];
/*  97 */     for (int i = 0; i < 4; i++) {
/*  98 */       this.header[i] = this.rawToken[i];
/*     */     }
/* 100 */     for (int i = 4; i < 12; i++) {
/* 101 */       this.creation[(i - 4)] = this.rawToken[i];
/*     */     }
/* 103 */     for (int i = 12; i < 20; i++) {
/* 104 */       this.expires[(i - 12)] = this.rawToken[i];
/*     */     }
/* 106 */     for (int i = 20; i < this.rawToken.length - 20; i++) {
/* 107 */       this.user[(i - 20)] = this.rawToken[i];
/*     */     }
/* 109 */     for (int i = this.rawToken.length - 20; i < this.rawToken.length; i++) {
/* 110 */       this.digest[(i - (this.rawToken.length - 20))] = this.rawToken[i];
/*     */     }
/* 112 */     this.creationDate = new Date(Long.parseLong(new String(this.creation), 16) * 1000L);
/* 113 */     this.expiresDate = new Date(Long.parseLong(new String(this.expires), 16) * 1000L);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Date getCreationDate()
/*     */   {
/* 124 */     return this.creationDate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public MessageDigest getMessageDigest()
/*     */   {
/*     */     try
/*     */     {
/* 134 */       return MessageDigest.getInstance("SHA-1");
/*     */     }
/*     */     catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {}
/*     */     
/* 138 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Date getExpiresDate()
/*     */   {
/* 147 */     return this.expiresDate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getUsername()
/*     */   {
/* 156 */     String userName = null;
/*     */     try {
/* 158 */       userName = new String(this.user, "UTF-8");
/*     */     }
/*     */     catch (UnsupportedEncodingException e) {
/* 161 */       userName = new String(this.user);
/*     */     }
/* 163 */     return userName;
/*     */   }
/*     */   
/*     */   public String getUsername(String code) {
/* 167 */     String userName = null;
/*     */     try {
/* 169 */       userName = new String(this.user, code);
/*     */     }
/*     */     catch (UnsupportedEncodingException e) {
/* 172 */       userName = new String(this.user);
/*     */     }
/* 174 */     return userName;
/*     */   }
/*     */   
/*     */   private void init() {
/* 178 */     this.creation = new byte[8];
/*     */     
/* 180 */     this.digest = new byte[20];
/* 181 */     this.expires = new byte[8];
/* 182 */     this.hash = new byte[20];
/* 183 */     this.header = new byte[4];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isValid(String secretKey)
/*     */   {
/* 193 */     boolean validDigest = false;
/* 194 */     boolean validDateRange = false;
/*     */     
/* 196 */     byte[] bytes = null;
/* 197 */     Date now = new Date();
/*     */     
/* 199 */     MessageDigest md = getMessageDigest();
/* 200 */     bytes = LMBCSUtil.concatenate(bytes, this.header);
/* 201 */     bytes = LMBCSUtil.concatenate(bytes, this.creation);
/* 202 */     bytes = LMBCSUtil.concatenate(bytes, this.expires);
/* 203 */     bytes = LMBCSUtil.concatenate(bytes, this.user);
/*     */     
/* 205 */     bytes = LMBCSUtil.concatenate(bytes, 
/* 206 */       BASE64Util.decodeAsBytes(secretKey));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 213 */     byte[] newDigest = md.digest(bytes);
/* 214 */     validDigest = MessageDigest.isEqual(this.digest, newDigest);
/* 215 */     System.out.println("valid message digest :" + validDigest);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 220 */     validDateRange = now.before(this.expiresDate);
/*     */     
/*     */ 
/* 223 */     boolean result = validDateRange;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 233 */     System.out.println("creationDate[" + this.creationDate + "]<now[" + now + "]<expiresDate[" + this.expiresDate + "],validDateRange:" + validDateRange + ",verify result:" + result);
/* 234 */     return result;
/*     */   }
/*     */   
/*     */   public static String byte2hex(byte[] b)
/*     */   {
/* 239 */     String hs = "";
/* 240 */     String stmp = "";
/* 241 */     for (int n = 0; n < b.length; n++)
/*     */     {
/* 243 */       stmp = Integer.toHexString(b[n] & 0xFF);
/* 244 */       if (stmp.length() == 1) hs = hs + "0" + stmp; else {
/* 245 */         hs = hs + stmp;
/*     */       }
/*     */     }
/* 248 */     return hs.toUpperCase();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 257 */     return encodeToken(this.tokenStr);
/*     */   }
/*     */   
/*     */   public String toPlainString() {
/* 261 */     return this.tokenStr;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public byte[] getCreation()
/*     */   {
/* 268 */     return this.creation;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setCreation(byte[] creation)
/*     */   {
/* 275 */     this.creation = creation;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public byte[] getExpires()
/*     */   {
/* 282 */     return this.expires;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setExpires(byte[] expires)
/*     */   {
/* 289 */     this.expires = expires;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public byte[] getHeader()
/*     */   {
/* 296 */     return this.header;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setHeader(byte[] header)
/*     */   {
/* 303 */     this.header = header;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setCreationDate(Date creationDate)
/*     */   {
/* 310 */     this.creationDate = creationDate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDigest(byte[] digest)
/*     */   {
/* 317 */     this.digest = digest;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setExpiresDate(Date expiresDate)
/*     */   {
/* 324 */     this.expiresDate = expiresDate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setUser(byte[] user)
/*     */   {
/* 331 */     this.user = user;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public byte[] getUser()
/*     */   {
/* 338 */     return this.user;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getTokenStr()
/*     */   {
/* 345 */     return encodeToken(this.tokenStr);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String encodeToken(String token)
/*     */   {
/*     */     try
/*     */     {
/* 357 */       return URLEncoder.encode(URLEncoder.encode(URLEncoder.encode(token, "UTF-8"), "UTF-8"), "UTF-8");
/*     */     } catch (UnsupportedEncodingException e) {
/* 359 */       System.err.println("Token encode error[UnsupportedEncodingException]!");
/* 360 */       e.printStackTrace();
/*     */     }
/*     */     
/* 363 */     return token;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String decodeToken(String token)
/*     */   {
/* 374 */     if (token.indexOf("+") == -1) {
/*     */       try
/*     */       {
/* 377 */         String decodeToken1 = URLDecoder.decode(token, "UTF-8");
/* 378 */         if (decodeToken1.indexOf("+") == -1) {
/* 379 */           String decodeToken2 = URLDecoder.decode(decodeToken1, "UTF-8");
/* 380 */           if (decodeToken2.indexOf("+") == -1) {
/* 381 */             return URLDecoder.decode(decodeToken2, "UTF-8");
/*     */           }
/* 383 */           return decodeToken2;
/*     */         }
/*     */         
/*     */ 
/* 387 */         return decodeToken1;
/*     */       }
/*     */       catch (UnsupportedEncodingException e) {
/* 390 */         System.err.println("Token decode error[UnsupportedEncodingException]!");
/* 391 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/* 395 */     return token;
/*     */   }
/*     */ }


/* Location:              F:\1 人力资源开发服务公司\53 集团人力系统管理员管维方案\SHR二开资料\单点登录-8.2及以上版本等\demo示例\第三方应用集成s-HR\sso_web\WEB-INF\lib\shr_sso_client_820.jar!\com\kingdee\shr\sso\client\ltpa\LtpaToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */