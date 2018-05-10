/*    */ package com.kingdee.shr.sso.client.util;
/*    */ 
/*    */ 
/*    */ public class BASE64Decoder
/*    */ {
/*  6 */   static String base64_alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static byte[] decodeBuffer(String data)
/*    */   {
/* 16 */     String Base64Code = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
/* 17 */     int page = data.length() / 4;
/* 18 */     byte[] outMessage = new byte[page * 3];
/* 19 */     char[] message = data.toCharArray();
/* 20 */     for (int i = 0; i < page; i++)
/*    */     {
/* 22 */       byte[] instr = new byte[4];
/* 23 */       instr[0] = ((byte)Base64Code.indexOf(message[(i * 4)]));
/* 24 */       instr[1] = ((byte)Base64Code.indexOf(message[(i * 4 + 1)]));
/* 25 */       instr[2] = ((byte)Base64Code.indexOf(message[(i * 4 + 2)]));
/* 26 */       instr[3] = ((byte)Base64Code.indexOf(message[(i * 4 + 3)]));
/* 27 */       byte[] outstr = new byte[3];
/* 28 */       outstr[0] = ((byte)(instr[0] << 2 ^ (instr[1] & 0x30) >> 4));
/* 29 */       if (instr[2] != 64)
/*    */       {
/* 31 */         outstr[1] = ((byte)(instr[1] << 4 ^ (instr[2] & 0x3C) >> 2));
/*    */       }
/*    */       else
/*    */       {
/* 35 */         outstr[2] = 0;
/*    */       }
/* 37 */       if (instr[3] != 64)
/*    */       {
/* 39 */         outstr[2] = ((byte)(instr[2] << 6 ^ instr[3]));
/*    */       }
/*    */       else
/*    */       {
/* 43 */         outstr[2] = 0;
/*    */       }
/* 45 */       outMessage[(3 * i)] = outstr[0];
/* 46 */       if (outstr[1] != 0)
/* 47 */         outMessage[(3 * i + 1)] = outstr[1];
/* 48 */       if (outstr[2] != 0) {
/* 49 */         outMessage[(3 * i + 2)] = outstr[2];
/*    */       }
/*    */     }
/* 52 */     int remove = 0;
/* 53 */     if (outMessage[(outMessage.length - 1)] == 0) {
/* 54 */       remove++;
/*    */     }
/*    */     
/* 57 */     if (outMessage[(outMessage.length - 2)] == 0) {
/* 58 */       remove++;
/*    */     }
/*    */     
/* 61 */     if (remove > 0) {
/* 62 */       return copyOf(outMessage, outMessage.length - remove);
/*    */     }
/*    */     
/* 65 */     return outMessage;
/*    */   }
/*    */   
/*    */   private static byte[] copyOf(byte[] result, int length)
/*    */   {
/* 70 */     byte[] newResult = new byte[length];
/* 71 */     for (int i = 0; i < length; i++) {
/* 72 */       newResult[i] = result[i];
/*    */     }
/*    */     
/* 75 */     return newResult;
/*    */   }
/*    */ }


/* Location:              F:\1 人力资源开发服务公司\53 集团人力系统管理员管维方案\SHR二开资料\单点登录-8.2及以上版本等\demo示例\第三方应用集成s-HR\sso_web\WEB-INF\lib\shr_sso_client_820.jar!\com\kingdee\shr\sso\client\util\BASE64Decoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */