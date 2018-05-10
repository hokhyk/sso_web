/*     */ package com.kingdee.shr.sso.client.util;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Locale;
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
/*     */ public final class LMBCSUtil
/*     */ {
/*     */   private static final String ULMBCS_GRP_L1 = "0x01";
/*     */   private static final String ULMBCS_GRP_GR = "0x02";
/*     */   private static final String ULMBCS_GRP_HE = "0x03";
/*     */   private static final String ULMBCS_GRP_AR = "0x04";
/*     */   private static final String ULMBCS_GRP_RU = "0x05";
/*     */   private static final String ULMBCS_GRP_L2 = "0x06";
/*     */   private static final String ULMBCS_GRP_TR = "0x08";
/*     */   private static final String ULMBCS_GRP_TH = "0x0B";
/*     */   private static final String ULMBCS_GRP_JA = "0x10";
/*     */   private static final String ULMBCS_GRP_KO = "0x11";
/*     */   private static final String ULMBCS_GRP_TW = "0x12";
/*     */   private static final String ULMBCS_GRP_CN = "0x13";
/*     */   private static final String ULMBCS_GRP_UNICODE = "0x14";
/*  46 */   private static Hashtable groupMap = new Hashtable();
/*     */   
/*     */   static {
/*  49 */     groupMap.put(new Locale("ar", ""), "0x04");
/*  50 */     groupMap.put(new Locale("be", ""), "0x05");
/*  51 */     groupMap.put(new Locale("bg", ""), "0x06");
/*  52 */     groupMap.put(new Locale("cs", ""), "0x06");
/*  53 */     groupMap.put(new Locale("el", ""), "0x02");
/*  54 */     groupMap.put(new Locale("he", ""), "0x03");
/*  55 */     groupMap.put(new Locale("hu", ""), "0x06");
/*  56 */     groupMap.put(new Locale("iw", ""), "0x03");
/*  57 */     groupMap.put(new Locale("ja", ""), "0x10");
/*  58 */     groupMap.put(new Locale("ko", ""), "0x11");
/*  59 */     groupMap.put(new Locale("mk", ""), "0x05");
/*  60 */     groupMap.put(new Locale("pl", ""), "0x06");
/*  61 */     groupMap.put(new Locale("ro", ""), "0x06");
/*  62 */     groupMap.put(new Locale("ru", ""), "0x05");
/*  63 */     groupMap.put(new Locale("sh", ""), "0x06");
/*  64 */     groupMap.put(new Locale("sk", ""), "0x06");
/*  65 */     groupMap.put(new Locale("sl", ""), "0x06");
/*  66 */     groupMap.put(new Locale("sq", ""), "0x06");
/*  67 */     groupMap.put(new Locale("sr", ""), "0x05");
/*  68 */     groupMap.put(new Locale("th", ""), "0x0B");
/*  69 */     groupMap.put(new Locale("tr", ""), "0x08");
/*  70 */     groupMap.put(new Locale("uk", ""), "0x05");
/*  71 */     groupMap.put(new Locale("zh", "TW"), "0x12");
/*  72 */     groupMap.put(new Locale("zh", "HK"), "0x12");
/*  73 */     groupMap.put(new Locale("zh", ""), "0x13");
/*  74 */     groupMap.put(new Locale("zh", "CN"), "0x13");
/*     */   }
/*     */   
/*     */   public static byte getLMBCGroupId(Locale locale) {
/*  78 */     byte groupId = Byte.decode("0x01").byteValue();
/*  79 */     if (groupMap.get(locale) == null) {
/*  80 */       String language = locale.getLanguage();
/*  81 */       Locale l = new Locale(language, "");
/*  82 */       if (groupMap.get(l) != null) {
/*  83 */         groupId = Byte.decode(groupMap.get(l).toString()).byteValue();
/*     */       }
/*     */     } else {
/*  86 */       groupId = Byte.decode(groupMap.get(locale).toString()).byteValue();
/*     */     }
/*  88 */     return groupId;
/*     */   }
/*     */   
/*     */   public static byte getDefaultGroupId() {
/*  92 */     return getLMBCGroupId(Locale.getDefault());
/*     */   }
/*     */   
/*     */   public static byte[] getLMBCSLocalGroupBytes(String input)
/*     */   {
/*  97 */     byte[] groupId = { getDefaultGroupId() };
/*  98 */     byte[] bytes = input.getBytes();
/*  99 */     byte[] result = null;
/* 100 */     for (int i = 0; i < input.length(); i++) {
/* 101 */       char c = input.charAt(i);
/* 102 */       Character ch = new Character(c);
/* 103 */       String s = ch.toString();
/* 104 */       if (s.getBytes()[0] < 0) {
/* 105 */         result = concatenate(result, groupId);
/*     */       }
/* 107 */       result = concatenate(result, s.getBytes());
/*     */     }
/* 109 */     return result;
/*     */   }
/*     */   
/*     */   public static byte[] getLMBCSUnicodeGroupBytes(String input) {
/* 113 */     byte[] unicodeGroupId = {Byte.decode("0x14")
/* 114 */       .byteValue() };
/* 115 */     String s = null;
/* 116 */     byte[] unicodeBytes = null;
/* 117 */     byte[] LMBCSBytes = null;
/*     */     
/*     */ 
/* 120 */     for (int i = 0; i < input.length(); i++) {
/* 121 */       s = new Character(input.charAt(i)).toString();
/*     */       try {
/* 123 */         unicodeBytes = s.getBytes("Unicode");
/* 124 */         byte highByte = unicodeBytes[2];
/* 125 */         byte lowByte = unicodeBytes[3];
/*     */         
/* 127 */         if (highByte == 0) {
/* 128 */           LMBCSBytes = concatenate(LMBCSBytes, new byte[] { lowByte });
/*     */         } else {
/* 130 */           LMBCSBytes = concatenate(LMBCSBytes, unicodeGroupId);
/* 131 */           LMBCSBytes = concatenate(LMBCSBytes, 
/* 132 */             new byte[] { highByte });
/* 133 */           LMBCSBytes = concatenate(LMBCSBytes, new byte[] { lowByte });
/*     */         }
/*     */       }
/*     */       catch (UnsupportedEncodingException e) {
/* 137 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/* 141 */     return LMBCSBytes;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] concatenate(byte[] a, byte[] b)
/*     */   {
/* 151 */     if (a == null) {
/* 152 */       return b;
/*     */     }
/* 154 */     byte[] bytes = new byte[a.length + b.length];
/*     */     
/* 156 */     System.arraycopy(a, 0, bytes, 0, a.length);
/* 157 */     System.arraycopy(b, 0, bytes, a.length, b.length);
/* 158 */     return bytes;
/*     */   }
/*     */ }


/* Location:              F:\1 人力资源开发服务公司\53 集团人力系统管理员管维方案\SHR二开资料\单点登录-8.2及以上版本等\demo示例\第三方应用集成s-HR\sso_web\WEB-INF\lib\shr_sso_client_820.jar!\com\kingdee\shr\sso\client\util\LMBCSUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */