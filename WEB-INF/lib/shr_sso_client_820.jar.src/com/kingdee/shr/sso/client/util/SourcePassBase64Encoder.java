/*     */ package com.kingdee.shr.sso.client.util;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
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
/*     */ public class SourcePassBase64Encoder
/*     */ {
/*     */   public static String byteArrayToBase64(byte[] a)
/*     */   {
/*  21 */     return byteArrayToBase64(a, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String byteArrayToAltBase64(byte[] a)
/*     */   {
/*  31 */     return byteArrayToBase64(a, true);
/*     */   }
/*     */   
/*     */   private static String byteArrayToBase64(byte[] a, boolean alternate) {
/*  35 */     int aLen = a.length;
/*  36 */     int numFullGroups = aLen / 3;
/*  37 */     int numBytesInPartialGroup = aLen - 3 * numFullGroups;
/*  38 */     int resultLen = 4 * ((aLen + 2) / 3);
/*  39 */     StringBuffer result = new StringBuffer(resultLen);
/*  40 */     char[] intToAlpha = alternate ? intToAltBase64 : intToBase64;
/*     */     
/*     */ 
/*  43 */     int inCursor = 0;
/*  44 */     for (int i = 0; i < numFullGroups; i++) {
/*  45 */       int byte0 = a[(inCursor++)] & 0xFF;
/*  46 */       int byte1 = a[(inCursor++)] & 0xFF;
/*  47 */       int byte2 = a[(inCursor++)] & 0xFF;
/*  48 */       result.append(intToAlpha[(byte0 >> 2)]);
/*  49 */       result.append(intToAlpha[(byte0 << 4 & 0x3F | byte1 >> 4)]);
/*  50 */       result.append(intToAlpha[(byte1 << 2 & 0x3F | byte2 >> 6)]);
/*  51 */       result.append(intToAlpha[(byte2 & 0x3F)]);
/*     */     }
/*     */     
/*     */ 
/*  55 */     if (numBytesInPartialGroup != 0) {
/*  56 */       int byte0 = a[(inCursor++)] & 0xFF;
/*  57 */       result.append(intToAlpha[(byte0 >> 2)]);
/*  58 */       if (numBytesInPartialGroup == 1) {
/*  59 */         result.append(intToAlpha[(byte0 << 4 & 0x3F)]);
/*  60 */         result.append("==");
/*     */       }
/*     */       else {
/*  63 */         int byte1 = a[(inCursor++)] & 0xFF;
/*  64 */         result.append(intToAlpha[(byte0 << 4 & 0x3F | byte1 >> 4)]);
/*  65 */         result.append(intToAlpha[(byte1 << 2 & 0x3F)]);
/*  66 */         result.append('=');
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  71 */     return result.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  79 */   private static final char[] intToBase64 = {
/*  80 */     'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 
/*  81 */     'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 
/*  82 */     'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 
/*  83 */     'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 
/*  84 */     '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  94 */   private static final char[] intToAltBase64 = {
/*  95 */     '!', '"', '#', '$', '%', '&', '\'', '(', ')', ',', '-', '.', ':', 
/*  96 */     ';', '<', '>', '@', '[', ']', '^', '`', '_', '{', '|', '}', '~', 
/*  97 */     'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 
/*  98 */     'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 
/*  99 */     '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '?' };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] base64ToByteArray(String s)
/*     */   {
/* 110 */     return base64ToByteArray(s, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] altBase64ToByteArray(String s)
/*     */   {
/* 122 */     return base64ToByteArray(s, true);
/*     */   }
/*     */   
/*     */   private static byte[] base64ToByteArray(String s, boolean alternate) {
/* 126 */     byte[] alphaToInt = alternate ? altBase64ToInt : base64ToInt;
/* 127 */     int sLen = s.length();
/* 128 */     int numGroups = sLen / 4;
/* 129 */     if (4 * numGroups != sLen)
/* 130 */       throw new IllegalArgumentException(
/* 131 */         "String length must be a multiple of four.");
/* 132 */     int missingBytesInLastGroup = 0;
/* 133 */     int numFullGroups = numGroups;
/* 134 */     if (sLen != 0) {
/* 135 */       if (s.charAt(sLen - 1) == '=') {
/* 136 */         missingBytesInLastGroup++;
/* 137 */         numFullGroups--;
/*     */       }
/* 139 */       if (s.charAt(sLen - 2) == '=')
/* 140 */         missingBytesInLastGroup++;
/*     */     }
/* 142 */     byte[] result = new byte[3 * numGroups - missingBytesInLastGroup];
/*     */     
/*     */ 
/* 145 */     int inCursor = 0;int outCursor = 0;
/* 146 */     for (int i = 0; i < numFullGroups; i++) {
/* 147 */       int ch0 = base64toInt(s.charAt(inCursor++), alphaToInt);
/* 148 */       int ch1 = base64toInt(s.charAt(inCursor++), alphaToInt);
/* 149 */       int ch2 = base64toInt(s.charAt(inCursor++), alphaToInt);
/* 150 */       int ch3 = base64toInt(s.charAt(inCursor++), alphaToInt);
/* 151 */       result[(outCursor++)] = ((byte)(ch0 << 2 | ch1 >> 4));
/* 152 */       result[(outCursor++)] = ((byte)(ch1 << 4 | ch2 >> 2));
/* 153 */       result[(outCursor++)] = ((byte)(ch2 << 6 | ch3));
/*     */     }
/*     */     
/*     */ 
/* 157 */     if (missingBytesInLastGroup != 0) {
/* 158 */       int ch0 = base64toInt(s.charAt(inCursor++), alphaToInt);
/* 159 */       int ch1 = base64toInt(s.charAt(inCursor++), alphaToInt);
/* 160 */       result[(outCursor++)] = ((byte)(ch0 << 2 | ch1 >> 4));
/*     */       
/* 162 */       if (missingBytesInLastGroup == 1) {
/* 163 */         int ch2 = base64toInt(s.charAt(inCursor++), alphaToInt);
/* 164 */         result[(outCursor++)] = ((byte)(ch1 << 4 | ch2 >> 2));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 169 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int base64toInt(char c, byte[] alphaToInt)
/*     */   {
/* 180 */     int result = alphaToInt[c];
/* 181 */     if (result < 0)
/* 182 */       throw new IllegalArgumentException("Illegal character " + c);
/* 183 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 193 */   private static final byte[] base64ToInt = {
/* 194 */     -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
/* 195 */     -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
/* 196 */     -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 
/* 197 */     55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 
/* 198 */     5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 
/* 199 */     24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 
/* 200 */     35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51 };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 207 */   private static final byte[] altBase64ToInt = {
/* 208 */     -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
/* 209 */     -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 
/* 210 */     2, 3, 4, 5, 6, 7, 8, -1, 62, 9, 10, 11, -1, 52, 53, 54, 55, 56, 57, 
/* 211 */     58, 59, 60, 61, 12, 13, 14, -1, 15, 63, 16, -1, -1, -1, -1, -1, -1, 
/* 212 */     -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
/* 213 */     -1, -1, -1, 17, -1, 18, 19, 21, 20, 26, 27, 28, 29, 30, 31, 32, 33, 
/* 214 */     34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 
/* 215 */     51, 22, 23, 24, 25 };
/*     */   
/*     */   public static void main(String[] args)
/*     */   {
/* 219 */     int numRuns = Integer.parseInt(args[0]);
/* 220 */     int numBytes = Integer.parseInt(args[1]);
/* 221 */     Random rnd = new Random();
/* 222 */     for (int i = 0; i < numRuns; i++) {
/* 223 */       for (int j = 0; j < numBytes; j++) {
/* 224 */         byte[] arr = new byte[j];
/* 225 */         for (int k = 0; k < j; k++) {
/* 226 */           arr[k] = ((byte)rnd.nextInt());
/*     */         }
/* 228 */         String s = byteArrayToBase64(arr);
/* 229 */         byte[] b = base64ToByteArray(s);
/* 230 */         if (!Arrays.equals(arr, b)) {
/* 231 */           System.out.println("Dismal failure!");
/*     */         }
/* 233 */         s = byteArrayToAltBase64(arr);
/* 234 */         b = altBase64ToByteArray(s);
/* 235 */         if (!Arrays.equals(arr, b)) {
/* 236 */           System.out.println("Alternate dismal failure!");
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              F:\1 人力资源开发服务公司\53 集团人力系统管理员管维方案\SHR二开资料\单点登录-8.2及以上版本等\demo示例\第三方应用集成s-HR\sso_web\WEB-INF\lib\shr_sso_client_820.jar!\com\kingdee\shr\sso\client\util\SourcePassBase64Encoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */