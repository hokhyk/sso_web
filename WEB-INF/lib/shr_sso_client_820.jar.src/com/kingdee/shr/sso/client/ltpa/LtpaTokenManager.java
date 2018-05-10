/*     */ package com.kingdee.shr.sso.client.ltpa;
/*     */ 
/*     */ import com.kingdee.shr.sso.client.util.BASE64Util;
/*     */ import com.kingdee.shr.sso.client.util.LMBCSUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.servlet.http.Cookie;
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
/*     */ public class LtpaTokenManager
/*     */ {
/*     */   public static final String COOKIE_DOMAIN = "cookie.domain";
/*     */   public static final String COOKIE_NAME = "LtpaToken";
/*     */   public static final String DOMINO_SECRET = "domino.secret";
/*     */   public static final String TOKEN_EXPIRATION = "token.expiration";
/*     */   public static final String ISLMBCSENCODE = "isLMBCSEncode";
/*  38 */   private static Properties properties = null;
/*  39 */   private static boolean isConfigLoaded = false;
/*     */   
/*     */   /* Error */
/*     */   public static void loadConfig(String configFile)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: invokestatic 42	com/kingdee/shr/sso/client/ltpa/LtpaTokenManager:isConfigLoaded	()Z
/*     */     //   3: ifne +269 -> 272
/*     */     //   6: new 45	java/util/Properties
/*     */     //   9: dup
/*     */     //   10: invokespecial 47	java/util/Properties:<init>	()V
/*     */     //   13: putstatic 29	com/kingdee/shr/sso/client/ltpa/LtpaTokenManager:properties	Ljava/util/Properties;
/*     */     //   16: aconst_null
/*     */     //   17: astore_1
/*     */     //   18: new 48	java/io/File
/*     */     //   21: dup
/*     */     //   22: aload_0
/*     */     //   23: invokespecial 50	java/io/File:<init>	(Ljava/lang/String;)V
/*     */     //   26: astore_2
/*     */     //   27: aload_2
/*     */     //   28: invokevirtual 52	java/io/File:exists	()Z
/*     */     //   31: ifne +103 -> 134
/*     */     //   34: getstatic 55	java/lang/System:err	Ljava/io/PrintStream;
/*     */     //   37: ldc 61
/*     */     //   39: invokevirtual 63	java/io/PrintStream:println	(Ljava/lang/String;)V
/*     */     //   42: new 1	com/kingdee/shr/sso/client/ltpa/LtpaTokenManager
/*     */     //   45: dup
/*     */     //   46: invokespecial 68	com/kingdee/shr/sso/client/ltpa/LtpaTokenManager:<init>	()V
/*     */     //   49: invokevirtual 69	java/lang/Object:getClass	()Ljava/lang/Class;
/*     */     //   52: ldc 73
/*     */     //   54: invokevirtual 75	java/lang/Class:getResourceAsStream	(Ljava/lang/String;)Ljava/io/InputStream;
/*     */     //   57: astore_3
/*     */     //   58: getstatic 29	com/kingdee/shr/sso/client/ltpa/LtpaTokenManager:properties	Ljava/util/Properties;
/*     */     //   61: aload_3
/*     */     //   62: invokevirtual 81	java/util/Properties:load	(Ljava/io/InputStream;)V
/*     */     //   65: iconst_1
/*     */     //   66: putstatic 31	com/kingdee/shr/sso/client/ltpa/LtpaTokenManager:isConfigLoaded	Z
/*     */     //   69: goto +48 -> 117
/*     */     //   72: astore 4
/*     */     //   74: new 85	com/kingdee/shr/sso/client/ltpa/ConfigurationError
/*     */     //   77: dup
/*     */     //   78: new 87	java/lang/StringBuilder
/*     */     //   81: dup
/*     */     //   82: ldc 89
/*     */     //   84: invokespecial 91	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
/*     */     //   87: aload_0
/*     */     //   88: invokevirtual 92	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   91: invokevirtual 96	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   94: invokespecial 100	com/kingdee/shr/sso/client/ltpa/ConfigurationError:<init>	(Ljava/lang/String;)V
/*     */     //   97: athrow
/*     */     //   98: astore 5
/*     */     //   100: aload_3
/*     */     //   101: invokevirtual 101	java/io/InputStream:close	()V
/*     */     //   104: goto +10 -> 114
/*     */     //   107: astore 6
/*     */     //   109: aload 6
/*     */     //   111: invokevirtual 106	java/io/IOException:printStackTrace	()V
/*     */     //   114: aload 5
/*     */     //   116: athrow
/*     */     //   117: aload_3
/*     */     //   118: invokevirtual 101	java/io/InputStream:close	()V
/*     */     //   121: goto +94 -> 215
/*     */     //   124: astore 6
/*     */     //   126: aload 6
/*     */     //   128: invokevirtual 106	java/io/IOException:printStackTrace	()V
/*     */     //   131: goto +84 -> 215
/*     */     //   134: new 111	java/io/FileInputStream
/*     */     //   137: dup
/*     */     //   138: aload_0
/*     */     //   139: invokespecial 113	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
/*     */     //   142: astore_1
/*     */     //   143: getstatic 29	com/kingdee/shr/sso/client/ltpa/LtpaTokenManager:properties	Ljava/util/Properties;
/*     */     //   146: aload_1
/*     */     //   147: invokevirtual 81	java/util/Properties:load	(Ljava/io/InputStream;)V
/*     */     //   150: iconst_1
/*     */     //   151: putstatic 31	com/kingdee/shr/sso/client/ltpa/LtpaTokenManager:isConfigLoaded	Z
/*     */     //   154: goto +47 -> 201
/*     */     //   157: astore_3
/*     */     //   158: new 85	com/kingdee/shr/sso/client/ltpa/ConfigurationError
/*     */     //   161: dup
/*     */     //   162: new 87	java/lang/StringBuilder
/*     */     //   165: dup
/*     */     //   166: ldc 89
/*     */     //   168: invokespecial 91	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
/*     */     //   171: aload_0
/*     */     //   172: invokevirtual 92	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   175: invokevirtual 96	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   178: invokespecial 100	com/kingdee/shr/sso/client/ltpa/ConfigurationError:<init>	(Ljava/lang/String;)V
/*     */     //   181: athrow
/*     */     //   182: astore 4
/*     */     //   184: aload_1
/*     */     //   185: invokevirtual 101	java/io/InputStream:close	()V
/*     */     //   188: goto +10 -> 198
/*     */     //   191: astore 5
/*     */     //   193: aload 5
/*     */     //   195: invokevirtual 106	java/io/IOException:printStackTrace	()V
/*     */     //   198: aload 4
/*     */     //   200: athrow
/*     */     //   201: aload_1
/*     */     //   202: invokevirtual 101	java/io/InputStream:close	()V
/*     */     //   205: goto +10 -> 215
/*     */     //   208: astore 5
/*     */     //   210: aload 5
/*     */     //   212: invokevirtual 106	java/io/IOException:printStackTrace	()V
/*     */     //   215: getstatic 29	com/kingdee/shr/sso/client/ltpa/LtpaTokenManager:properties	Ljava/util/Properties;
/*     */     //   218: ldc 14
/*     */     //   220: invokevirtual 114	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
/*     */     //   223: ifnull +25 -> 248
/*     */     //   226: getstatic 29	com/kingdee/shr/sso/client/ltpa/LtpaTokenManager:properties	Ljava/util/Properties;
/*     */     //   229: ldc 8
/*     */     //   231: invokevirtual 114	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
/*     */     //   234: ifnull +14 -> 248
/*     */     //   237: getstatic 29	com/kingdee/shr/sso/client/ltpa/LtpaTokenManager:properties	Ljava/util/Properties;
/*     */     //   240: ldc 17
/*     */     //   242: invokevirtual 114	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
/*     */     //   245: ifnonnull +27 -> 272
/*     */     //   248: new 85	com/kingdee/shr/sso/client/ltpa/ConfigurationError
/*     */     //   251: dup
/*     */     //   252: new 87	java/lang/StringBuilder
/*     */     //   255: dup
/*     */     //   256: ldc 118
/*     */     //   258: invokespecial 91	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
/*     */     //   261: aload_0
/*     */     //   262: invokevirtual 92	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   265: invokevirtual 96	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   268: invokespecial 100	com/kingdee/shr/sso/client/ltpa/ConfigurationError:<init>	(Ljava/lang/String;)V
/*     */     //   271: athrow
/*     */     //   272: return
/*     */     // Line number table:
/*     */     //   Java source line #48	-> byte code offset #0
/*     */     //   Java source line #50	-> byte code offset #6
/*     */     //   Java source line #52	-> byte code offset #16
/*     */     //   Java source line #54	-> byte code offset #18
/*     */     //   Java source line #55	-> byte code offset #27
/*     */     //   Java source line #56	-> byte code offset #34
/*     */     //   Java source line #59	-> byte code offset #42
/*     */     //   Java source line #62	-> byte code offset #58
/*     */     //   Java source line #63	-> byte code offset #65
/*     */     //   Java source line #64	-> byte code offset #69
/*     */     //   Java source line #65	-> byte code offset #74
/*     */     //   Java source line #66	-> byte code offset #98
/*     */     //   Java source line #68	-> byte code offset #100
/*     */     //   Java source line #69	-> byte code offset #104
/*     */     //   Java source line #70	-> byte code offset #109
/*     */     //   Java source line #72	-> byte code offset #114
/*     */     //   Java source line #68	-> byte code offset #117
/*     */     //   Java source line #69	-> byte code offset #121
/*     */     //   Java source line #70	-> byte code offset #126
/*     */     //   Java source line #74	-> byte code offset #131
/*     */     //   Java source line #78	-> byte code offset #134
/*     */     //   Java source line #82	-> byte code offset #143
/*     */     //   Java source line #83	-> byte code offset #150
/*     */     //   Java source line #85	-> byte code offset #154
/*     */     //   Java source line #86	-> byte code offset #158
/*     */     //   Java source line #88	-> byte code offset #182
/*     */     //   Java source line #90	-> byte code offset #184
/*     */     //   Java source line #91	-> byte code offset #188
/*     */     //   Java source line #92	-> byte code offset #193
/*     */     //   Java source line #94	-> byte code offset #198
/*     */     //   Java source line #90	-> byte code offset #201
/*     */     //   Java source line #91	-> byte code offset #205
/*     */     //   Java source line #92	-> byte code offset #210
/*     */     //   Java source line #96	-> byte code offset #215
/*     */     //   Java source line #97	-> byte code offset #226
/*     */     //   Java source line #98	-> byte code offset #237
/*     */     //   Java source line #99	-> byte code offset #248
/*     */     //   Java source line #101	-> byte code offset #272
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	273	0	configFile	String
/*     */     //   17	185	1	is	java.io.InputStream
/*     */     //   26	2	2	file	java.io.File
/*     */     //   57	61	3	input	java.io.InputStream
/*     */     //   157	2	3	ioe	java.io.IOException
/*     */     //   72	127	4	e	java.io.IOException
/*     */     //   98	17	5	localObject	Object
/*     */     //   191	3	5	e	java.io.IOException
/*     */     //   208	3	5	e	java.io.IOException
/*     */     //   107	3	6	e	java.io.IOException
/*     */     //   124	3	6	e	java.io.IOException
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   58	69	72	java/io/IOException
/*     */     //   58	98	98	finally
/*     */     //   100	104	107	java/io/IOException
/*     */     //   117	121	124	java/io/IOException
/*     */     //   134	154	157	java/io/IOException
/*     */     //   134	182	182	finally
/*     */     //   184	188	191	java/io/IOException
/*     */     //   201	205	208	java/io/IOException
/*     */   }
/*     */   
/*     */   /**
/*     */    * @deprecated
/*     */    */
/*     */   public static LtpaToken generate(String canonicalUser)
/*     */   {
/* 109 */     String configFile = getDefaultConfigFile();
/* 110 */     return generate(canonicalUser, configFile);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static LtpaToken generate(String canonicalUser, String configFile)
/*     */   {
/* 120 */     initConfig(configFile);
/* 121 */     Date creationDate = new Date();
/* 122 */     Date expirationDate = new Date();
/* 123 */     int interval = Integer.parseInt(properties.getProperty("token.expiration"));
/* 124 */     expirationDate.setTime(creationDate.getTime() + 60000 * interval);
/*     */     
/* 126 */     return generate(canonicalUser, creationDate, expirationDate, configFile, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static LtpaToken generate(String canonicalUser, String configFile, String authPattern)
/*     */   {
/* 137 */     initConfig(configFile);
/* 138 */     Date creationDate = new Date();
/* 139 */     Date expirationDate = new Date();
/* 140 */     int interval = 0;
/* 141 */     if (properties.containsKey(authPattern.toLowerCase() + "." + "token.expiration")) {
/* 142 */       interval = Integer.parseInt(authPattern.toLowerCase() + "." + "token.expiration");
/*     */     } else {
/* 144 */       interval = Integer.parseInt(properties.getProperty("token.expiration"));
/*     */     }
/* 146 */     expirationDate.setTime(creationDate.getTime() + 60000 * interval);
/*     */     
/* 148 */     return generate(canonicalUser, creationDate, expirationDate, configFile, authPattern);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   /**
/*     */    * @deprecated
/*     */    */
/*     */   public static LtpaToken generate(String canonicalUser, Date tokenCreation, Date tokenExpires)
/*     */   {
/* 163 */     String configFile = getDefaultConfigFile();
/* 164 */     return generate(canonicalUser, tokenCreation, tokenExpires, configFile, null);
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   private static String getDefaultConfigFile()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: new 45	java/util/Properties
/*     */     //   3: dup
/*     */     //   4: invokespecial 47	java/util/Properties:<init>	()V
/*     */     //   7: astore_0
/*     */     //   8: ldc -65
/*     */     //   10: astore_1
/*     */     //   11: ldc -63
/*     */     //   13: astore_2
/*     */     //   14: ldc 1
/*     */     //   16: aload_2
/*     */     //   17: invokevirtual 75	java/lang/Class:getResourceAsStream	(Ljava/lang/String;)Ljava/io/InputStream;
/*     */     //   20: astore_3
/*     */     //   21: aload_0
/*     */     //   22: aload_3
/*     */     //   23: invokevirtual 81	java/util/Properties:load	(Ljava/io/InputStream;)V
/*     */     //   26: aload_0
/*     */     //   27: ldc -61
/*     */     //   29: invokevirtual 114	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
/*     */     //   32: astore_1
/*     */     //   33: goto +46 -> 79
/*     */     //   36: astore 4
/*     */     //   38: aload 4
/*     */     //   40: invokevirtual 106	java/io/IOException:printStackTrace	()V
/*     */     //   43: aload_3
/*     */     //   44: invokevirtual 101	java/io/InputStream:close	()V
/*     */     //   47: goto +46 -> 93
/*     */     //   50: astore 6
/*     */     //   52: aload 6
/*     */     //   54: invokevirtual 106	java/io/IOException:printStackTrace	()V
/*     */     //   57: goto +36 -> 93
/*     */     //   60: astore 5
/*     */     //   62: aload_3
/*     */     //   63: invokevirtual 101	java/io/InputStream:close	()V
/*     */     //   66: goto +10 -> 76
/*     */     //   69: astore 6
/*     */     //   71: aload 6
/*     */     //   73: invokevirtual 106	java/io/IOException:printStackTrace	()V
/*     */     //   76: aload 5
/*     */     //   78: athrow
/*     */     //   79: aload_3
/*     */     //   80: invokevirtual 101	java/io/InputStream:close	()V
/*     */     //   83: goto +10 -> 93
/*     */     //   86: astore 6
/*     */     //   88: aload 6
/*     */     //   90: invokevirtual 106	java/io/IOException:printStackTrace	()V
/*     */     //   93: aload_1
/*     */     //   94: areturn
/*     */     // Line number table:
/*     */     //   Java source line #174	-> byte code offset #0
/*     */     //   Java source line #175	-> byte code offset #8
/*     */     //   Java source line #176	-> byte code offset #11
/*     */     //   Java source line #177	-> byte code offset #14
/*     */     //   Java source line #179	-> byte code offset #21
/*     */     //   Java source line #180	-> byte code offset #26
/*     */     //   Java source line #181	-> byte code offset #33
/*     */     //   Java source line #183	-> byte code offset #38
/*     */     //   Java source line #187	-> byte code offset #43
/*     */     //   Java source line #188	-> byte code offset #47
/*     */     //   Java source line #189	-> byte code offset #52
/*     */     //   Java source line #185	-> byte code offset #60
/*     */     //   Java source line #187	-> byte code offset #62
/*     */     //   Java source line #188	-> byte code offset #66
/*     */     //   Java source line #189	-> byte code offset #71
/*     */     //   Java source line #191	-> byte code offset #76
/*     */     //   Java source line #187	-> byte code offset #79
/*     */     //   Java source line #188	-> byte code offset #83
/*     */     //   Java source line #189	-> byte code offset #88
/*     */     //   Java source line #193	-> byte code offset #93
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   7	20	0	prop	Properties
/*     */     //   10	84	1	configFile	String
/*     */     //   13	4	2	defaultConfigFileName	String
/*     */     //   20	60	3	is	java.io.InputStream
/*     */     //   36	3	4	e	java.io.IOException
/*     */     //   60	17	5	localObject	Object
/*     */     //   50	3	6	e	java.io.IOException
/*     */     //   69	3	6	e	java.io.IOException
/*     */     //   86	3	6	e	java.io.IOException
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   21	33	36	java/io/IOException
/*     */     //   43	47	50	java/io/IOException
/*     */     //   21	43	60	finally
/*     */     //   62	66	69	java/io/IOException
/*     */     //   79	83	86	java/io/IOException
/*     */   }
/*     */   
/*     */   public static LtpaToken generate(String canonicalUser, Date tokenCreation, Date tokenExpires, String configFile, String authPattern)
/*     */   {
/* 204 */     boolean isLMBCSEncode = false;
/* 205 */     if (properties != null) {
/* 206 */       String isLMBC = properties.getProperty("isLMBCSEncode");
/* 207 */       if ((isLMBC != null) && ("true".equals(isLMBC))) {
/* 208 */         isLMBCSEncode = true;
/*     */       }
/*     */     }
/* 211 */     return generate(canonicalUser, tokenCreation, tokenExpires, configFile, isLMBCSEncode, authPattern);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static LtpaToken generate(String canonicalUser, Date tokenCreation, Date tokenExpires, String configFile, boolean isLMBCSEncode, String authPattern)
/*     */   {
/* 223 */     initConfig(configFile);
/*     */     
/* 225 */     LtpaToken ltpa = new LtpaToken();
/*     */     
/* 227 */     Calendar calendar = Calendar.getInstance();
/* 228 */     MessageDigest md = ltpa.getMessageDigest();
/*     */     
/*     */ 
/*     */ 
/* 232 */     ltpa.setHeader(new byte[] { 0, 1, 2, 3 });
/* 233 */     if (isLMBCSEncode)
/*     */     {
/* 235 */       ltpa.setUser(LMBCSUtil.getLMBCSLocalGroupBytes(canonicalUser));
/*     */     }
/*     */     else {
/*     */       try
/*     */       {
/* 240 */         ltpa.setUser(canonicalUser.getBytes("UTF-8"));
/*     */       }
/*     */       catch (UnsupportedEncodingException e) {
/* 243 */         ltpa.setUser(canonicalUser.getBytes());
/*     */       }
/*     */     }
/*     */     
/* 247 */     byte[] token = null;
/*     */     
/* 249 */     calendar.setTime(tokenCreation);
/* 250 */     ltpa.setCreation(Long.toHexString(calendar.getTime().getTime() / 1000L)
/* 251 */       .toUpperCase().getBytes());
/* 252 */     calendar.setTime(tokenExpires);
/* 253 */     ltpa.setExpires(Long.toHexString(calendar.getTime().getTime() / 1000L)
/* 254 */       .toUpperCase().getBytes());
/*     */     
/* 256 */     token = LMBCSUtil.concatenate(token, ltpa.getHeader());
/* 257 */     token = LMBCSUtil.concatenate(token, ltpa.getCreation());
/* 258 */     token = LMBCSUtil.concatenate(token, ltpa.getExpires());
/* 259 */     token = LMBCSUtil.concatenate(token, ltpa.getUser());
/*     */     
/* 261 */     md.update(token);
/*     */     
/* 263 */     String secret = null;
/* 264 */     if ((authPattern != null) && (properties.containsKey(authPattern.toLowerCase() + "." + "domino.secret"))) {
/* 265 */       secret = properties.getProperty(authPattern.toLowerCase() + "." + "domino.secret");
/*     */     } else {
/* 267 */       secret = properties.getProperty("domino.secret");
/*     */     }
/*     */     
/*     */ 
/* 271 */     byte[] digest = md.digest(BASE64Util.decodeAsBytes(secret));
/* 272 */     String di = byte2hex(digest);
/* 273 */     ltpa.setDigest(di.getBytes());
/* 274 */     token = LMBCSUtil.concatenate(token, di.getBytes());
/*     */     
/* 276 */     String tokenStr = BASE64Util.encode(token);
/* 277 */     String result = "";
/* 278 */     StringTokenizer st = new StringTokenizer(tokenStr);
/*     */     
/*     */ 
/* 281 */     while (st.hasMoreTokens()) {
/* 282 */       result = result + st.nextToken();
/*     */     }
/* 284 */     return new LtpaToken(result);
/*     */   }
/*     */   
/*     */   public static String byte2hex(byte[] b)
/*     */   {
/* 289 */     String hs = "";
/* 290 */     String stmp = "";
/* 291 */     for (int n = 0; n < b.length; n++)
/*     */     {
/* 293 */       stmp = Integer.toHexString(b[n] & 0xFF);
/* 294 */       if (stmp.length() == 1) hs = hs + "0" + stmp; else {
/* 295 */         hs = hs + stmp;
/*     */       }
/*     */     }
/* 298 */     return hs.toUpperCase();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void initConfig(String configFile)
/*     */   {
/* 310 */     loadConfig(configFile);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isValid(String ltpaToken)
/*     */   {
/* 322 */     checkConfig();
/* 323 */     LtpaToken ltpa = new LtpaToken(LtpaToken.decodeToken(ltpaToken));
/* 324 */     boolean result = ltpa.isValid(properties.getProperty("domino.secret"));
/* 325 */     System.out.println("LTPA token isValid result:" + result);
/* 326 */     if (!result)
/* 327 */       System.out.println("LTPA token compare false, token:" + ltpaToken);
/* 328 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isValid(String ltpaToken, String authPattern)
/*     */   {
/* 340 */     checkConfig();
/* 341 */     LtpaToken ltpa = new LtpaToken(LtpaToken.decodeToken(ltpaToken));
/* 342 */     String secret = null;
/* 343 */     if (properties.containsKey(authPattern.toLowerCase() + "." + "domino.secret")) {
/* 344 */       secret = properties.getProperty(authPattern.toLowerCase() + "." + "domino.secret");
/*     */     } else {
/* 346 */       secret = properties.getProperty("domino.secret");
/*     */     }
/* 348 */     boolean result = ltpa.isValid(secret);
/* 349 */     System.out.println("LTPA token isValid result:" + result);
/* 350 */     if (!result)
/* 351 */       System.out.println("LTPA token compare false, token:" + ltpaToken);
/* 352 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Cookie toCookie(String ltpaToken)
/*     */   {
/* 361 */     checkConfig();
/* 362 */     Cookie cookie = new Cookie("LtpaToken", ltpaToken);
/* 363 */     String domain = properties.getProperty("cookie.domain");
/* 364 */     if ((domain != null) && (!"".equals(domain)))
/*     */     {
/* 366 */       cookie.setDomain(domain);
/*     */     }
/* 368 */     cookie.setPath("/");
/* 369 */     cookie.setSecure(false);
/* 370 */     cookie.setMaxAge(-1);
/* 371 */     return cookie;
/*     */   }
/*     */   
/*     */   private static void checkConfig() {
/* 375 */     if (!isConfigLoaded())
/*     */     {
/* 377 */       throw new ConfigurationError("LtpaToken properties is unloaded properly. ");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static boolean isConfigLoaded()
/*     */   {
/* 386 */     return isConfigLoaded;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getDefaultLtpaConfig()
/*     */   {
/* 394 */     return "LtpaToken.properties";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static synchronized void loadDefaultConfig()
/*     */   {
/* 401 */     loadConfig(getDefaultLtpaConfig());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean compare(String token, String userNumber)
/*     */   {
/* 411 */     boolean result = false;
/* 412 */     LtpaToken lt = new LtpaToken(LtpaToken.decodeToken(token));
/* 413 */     String username = lt.getUsername();
/* 414 */     result = username == null ? false : username.equals(userNumber);
/* 415 */     System.out.println("LTPA token compare result:" + result);
/* 416 */     if (!result)
/* 417 */       System.out.println("LTPA token compare false, token:" + token);
/* 418 */     return result;
/*     */   }
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
/*     */   public static boolean verifyToken(String path, String token, String userNumber)
/*     */   {
/* 432 */     boolean result = false;
/* 433 */     if ((path == null) || (path.trim().length() == 0)) {
/* 434 */       loadDefaultConfig();
/*     */     } else {
/* 436 */       loadConfig(path);
/*     */     }
/* 438 */     boolean isValid = isValid(LtpaToken.decodeToken(token));
/*     */     
/* 440 */     if (!isValid)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 445 */       System.out.println("LTPA token isValid false, token:" + token);
/*     */     }
/* 447 */     result = isValid;
/* 448 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */   public static boolean verifyToken(String path, String token, String userNumber, String authPattern)
/*     */   {
/* 454 */     boolean result = false;
/* 455 */     if ((path == null) || (path.trim().length() == 0)) {
/* 456 */       loadDefaultConfig();
/*     */     } else {
/* 458 */       loadConfig(path);
/*     */     }
/* 460 */     boolean isValid = isValid(LtpaToken.decodeToken(token), authPattern);
/*     */     
/* 462 */     if (isValid) {
/* 463 */       result = compare(LtpaToken.decodeToken(token), userNumber);
/* 464 */       if (!result)
/* 465 */         System.out.println("LTPA token compare false, token:" + token);
/*     */     } else {
/* 467 */       System.out.println("LTPA token isValid false, token:" + token);
/*     */     }
/*     */     
/* 470 */     return result;
/*     */   }
/*     */ }


/* Location:              F:\1 人力资源开发服务公司\53 集团人力系统管理员管维方案\SHR二开资料\单点登录-8.2及以上版本等\demo示例\第三方应用集成s-HR\sso_web\WEB-INF\lib\shr_sso_client_820.jar!\com\kingdee\shr\sso\client\ltpa\LtpaTokenManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */