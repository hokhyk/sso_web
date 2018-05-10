/*    */ package com.kingdee.shr.sso.client.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BASE64Util
/*    */ {
/*    */   public static BASE64Encoder getBASE64Encoder()
/*    */   {
/* 11 */     BASE64Encoder encoder = new BASE64Encoder();
/* 12 */     return encoder;
/*    */   }
/*    */   
/*    */   public static BASE64Decoder getBASE64Decoder()
/*    */   {
/* 17 */     BASE64Decoder decoder = new BASE64Decoder();
/* 18 */     return decoder;
/*    */   }
/*    */   
/*    */ 
/*    */   public static String decodeAsString(String input)
/*    */   {
/* 24 */     BASE64Decoder decoder = getBASE64Decoder();
/* 25 */     String decoded = "";
/*    */     
/*    */ 
/*    */ 
/* 29 */     byte[] buf = BASE64Decoder.decodeBuffer(input);
/* 30 */     decoded = new String(buf);
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 36 */     return decoded;
/*    */   }
/*    */   
/*    */ 
/*    */   public static byte[] decodeAsBytes(String input)
/*    */   {
/* 42 */     BASE64Decoder decoder = getBASE64Decoder();
/* 43 */     byte[] buf = null;
/*    */     
/*    */ 
/*    */ 
/* 47 */     buf = BASE64Decoder.decodeBuffer(input);
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 53 */     return buf;
/*    */   }
/*    */   
/*    */ 
/*    */   public static String encode(byte[] buf)
/*    */   {
/* 59 */     BASE64Encoder encoder = getBASE64Encoder();
/* 60 */     String encoded = "";
/*    */     
/* 62 */     encoded = BASE64Encoder.encodeBuffer(buf);
/* 63 */     return encoded;
/*    */   }
/*    */ }


/* Location:              F:\1 人力资源开发服务公司\53 集团人力系统管理员管维方案\SHR二开资料\单点登录-8.2及以上版本等\demo示例\第三方应用集成s-HR\sso_web\WEB-INF\lib\shr_sso_client_820.jar!\com\kingdee\shr\sso\client\util\BASE64Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */