/*    */ package com.kingdee.shr.api;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Token implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -7212631525740921239L;
/*    */   private String value;
/*    */   private String domain;
/*    */   private String path;
/*    */   
/*    */   public String getValue()
/*    */   {
/* 14 */     return this.value;
/*    */   }
/*    */   
/* 17 */   public void setValue(String value) { this.value = value; }
/*    */   
/*    */   public String getDomain() {
/* 20 */     return this.domain;
/*    */   }
/*    */   
/* 23 */   public void setDomain(String domain) { this.domain = domain; }
/*    */   
/*    */   public String getPath() {
/* 26 */     return this.path;
/*    */   }
/*    */   
/* 29 */   public void setPath(String path) { this.path = path; }
/*    */   
/*    */   public String toString()
/*    */   {
/* 33 */     return this.value;
/*    */   }
/*    */ }


/* Location:              F:\1 人力资源开发服务公司\53 集团人力系统管理员管维方案\SHR二开资料\单点登录-8.2及以上版本等\demo示例\第三方应用集成s-HR\sso_web\WEB-INF\lib\shr_sso_client_820.jar!\com\kingdee\shr\api\Token.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */