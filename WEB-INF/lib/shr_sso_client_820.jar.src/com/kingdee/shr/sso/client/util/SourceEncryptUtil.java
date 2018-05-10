/*     */ package com.kingdee.shr.sso.client.util;
/*     */ 
/*     */ import com.kingdee.shr.CryptException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import javax.crypto.BadPaddingException;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.IllegalBlockSizeException;
/*     */ import javax.crypto.NoSuchPaddingException;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SourceEncryptUtil
/*     */ {
/*  17 */   private static final byte[] pwd = { -82, -101, Byte.MAX_VALUE, 52, -8, -108, 2, 93 };
/*     */   private static final String ALGORITHM = "DES";
/*     */   
/*     */   public String encrypt(String key, String srcStr) throws CryptException {
/*  21 */     SecretKey deskey = new SecretKeySpec(pwd, "DES");
/*     */     
/*     */     try
/*     */     {
/*  25 */       Cipher cipher = Cipher.getInstance("DES");
/*  26 */       cipher.init(1, deskey);
/*     */       
/*  28 */       byte[] resByte = cipher.doFinal(srcStr.getBytes());
/*  29 */       return SourcePassBase64Encoder.byteArrayToBase64(resByte);
/*     */ 
/*     */ 
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/*     */ 
/*     */ 
/*  37 */       throw new CryptException("NoSuchAlgorithmException", e);
/*     */ 
/*     */     }
/*     */     catch (NoSuchPaddingException e)
/*     */     {
/*  42 */       throw new CryptException("NoSuchPaddingException", e);
/*     */ 
/*     */     }
/*     */     catch (InvalidKeyException e)
/*     */     {
/*  47 */       throw new CryptException("InvalidKeyException", e);
/*     */ 
/*     */     }
/*     */     catch (IllegalStateException e)
/*     */     {
/*  52 */       throw new CryptException("IllegalStateException", e);
/*     */ 
/*     */     }
/*     */     catch (IllegalBlockSizeException e)
/*     */     {
/*  57 */       throw new CryptException("IllegalBlockSizeException", e);
/*     */ 
/*     */     }
/*     */     catch (BadPaddingException e)
/*     */     {
/*  62 */       throw new CryptException("BadPaddingException", e);
/*     */     }
/*     */   }
/*     */   
/*     */   public String decrypt(String key, String encStr)
/*     */     throws CryptException
/*     */   {
/*  69 */     SecretKey deskey = new SecretKeySpec(pwd, "DES");
/*     */     
/*     */ 
/*     */     try
/*     */     {
/*  74 */       Cipher cipher = Cipher.getInstance("DES");
/*  75 */       cipher.init(2, deskey);
/*     */       
/*     */ 
/*     */ 
/*  79 */       byte[] sourceByte = SourcePassBase64Encoder.base64ToByteArray(encStr);
/*     */       
/*  81 */       return new String(cipher.doFinal(sourceByte));
/*     */ 
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/*     */ 
/*  87 */       throw new CryptException("NoSuchAlgorithmException", e);
/*     */ 
/*     */     }
/*     */     catch (NoSuchPaddingException e)
/*     */     {
/*  92 */       throw new CryptException("NoSuchPaddingException", e);
/*     */ 
/*     */     }
/*     */     catch (InvalidKeyException e)
/*     */     {
/*  97 */       throw new CryptException("InvalidKeyException", e);
/*     */ 
/*     */     }
/*     */     catch (IllegalStateException e)
/*     */     {
/* 102 */       throw new CryptException("IllegalStateException", e);
/*     */ 
/*     */     }
/*     */     catch (IllegalBlockSizeException e)
/*     */     {
/* 107 */       throw new CryptException("IllegalBlockSizeException", e);
/*     */     }
/*     */     catch (BadPaddingException e)
/*     */     {
/* 111 */       throw new CryptException("BadPaddingException", e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String byte2hex(byte[] b)
/*     */   {
/* 122 */     String hs = "";
/* 123 */     String stmp = "";
/*     */     
/* 125 */     for (int n = 0; n < b.length; n++) {
/* 126 */       stmp = Integer.toHexString(b[n] & 0xFF);
/* 127 */       if (stmp.length() == 1) hs = hs + "0" + stmp; else
/* 128 */         hs = hs + stmp;
/* 129 */       if (n < b.length - 1) hs = hs + ",0x";
/*     */     }
/* 131 */     return hs.toUpperCase();
/*     */   }
/*     */ }


/* Location:              F:\1 人力资源开发服务公司\53 集团人力系统管理员管维方案\SHR二开资料\单点登录-8.2及以上版本等\demo示例\第三方应用集成s-HR\sso_web\WEB-INF\lib\shr_sso_client_820.jar!\com\kingdee\shr\sso\client\util\SourceEncryptUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */