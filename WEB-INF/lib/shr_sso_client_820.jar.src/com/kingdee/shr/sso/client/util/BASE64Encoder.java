/*    */ package com.kingdee.shr.sso.client.util;
/*    */ 
/*    */ public class BASE64Encoder
/*    */ {
/*    */   public static String encodeBuffer(byte[] bytes) {
/*  6 */     char[] Base64Code = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
/*  7 */       'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
/*  8 */       'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', 
/*  9 */       '8', '9', '+', '/', '=' };
/* 10 */     byte empty = 0;
/* 11 */     StringBuffer outmessage = new StringBuffer();
/* 12 */     int messageLen = bytes.length;
/* 13 */     int page = messageLen / 3;
/* 14 */     int use = 0;
/* 15 */     if ((use = messageLen % 3) > 0)
/*    */     {
/*    */ 
/* 18 */       byte[] newbyte = new byte[messageLen + 3 - use];
/*    */       
/* 20 */       for (int i = 0; i < messageLen; i++) {
/* 21 */         newbyte[i] = bytes[i];
/*    */       }
/*    */       
/* 24 */       for (int i = 0; i < 3 - use; i++) {
/* 25 */         newbyte[(messageLen + i)] = empty;
/*    */       }
/* 27 */       page++;
/* 28 */       bytes = newbyte;
/*    */     }
/* 30 */     outmessage = new StringBuffer(page * 4);
/* 31 */     for (int i = 0; i < page; i++)
/*    */     {
/* 33 */       byte[] instr = new byte[3];
/* 34 */       instr[0] = bytes[(i * 3)];
/* 35 */       instr[1] = bytes[(i * 3 + 1)];
/* 36 */       instr[2] = bytes[(i * 3 + 2)];
/* 37 */       int[] outstr = new int[4];
/* 38 */       outstr[0] = (instr[0] >>> 2 & 0x3F);
/* 39 */       outstr[1] = ((instr[0] & 0x3) << 4 ^ instr[1] >>> 4 & 0xF);
/* 40 */       if (instr[1] != empty) {
/* 41 */         outstr[2] = ((instr[1] & 0xF) << 2 ^ instr[2] >>> 6 & 0x3);
/*    */       } else
/* 43 */         outstr[2] = 64;
/* 44 */       if (instr[2] != empty) {
/* 45 */         outstr[3] = (instr[2] & 0x3F);
/*    */       } else {
/* 47 */         outstr[3] = 64;
/*    */       }
/* 49 */       outmessage.append(Base64Code[outstr[0]]);
/* 50 */       outmessage.append(Base64Code[outstr[1]]);
/* 51 */       outmessage.append(Base64Code[outstr[2]]);
/* 52 */       outmessage.append(Base64Code[outstr[3]]);
/*    */     }
/* 54 */     return outmessage.toString();
/*    */   }
/*    */ }


/* Location:              F:\1 人力资源开发服务公司\53 集团人力系统管理员管维方案\SHR二开资料\单点登录-8.2及以上版本等\demo示例\第三方应用集成s-HR\sso_web\WEB-INF\lib\shr_sso_client_820.jar!\com\kingdee\shr\sso\client\util\BASE64Encoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */