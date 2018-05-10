/*    */ package com.kingdee.shr;
/*    */ 
/*    */ import java.io.PrintWriter;
/*    */ 
/*    */ public class CryptException extends Exception
/*    */ {
/*    */   private Throwable nested;
/*    */   
/*    */   public CryptException() {}
/*    */   
/*    */   public CryptException(String s) {
/* 12 */     super(s);
/*    */   }
/*    */   
/*    */   public CryptException(String s, Throwable ex)
/*    */   {
/* 17 */     super(s);
/* 18 */     this.nested = ex;
/*    */   }
/*    */   
/*    */   public Throwable getNested()
/*    */   {
/* 23 */     return this.nested;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 27 */     if (this.nested == null)
/*    */     {
/* 29 */       return super.getMessage();
/*    */     }
/*    */     
/* 32 */     return 
/*    */     
/* 34 */       super.getMessage() + "\nnested exception is: \n\t" + this.nested.toString();
/*    */   }
/*    */   
/*    */ 
/*    */   public void printStackTrace(java.io.PrintStream ps)
/*    */   {
/* 40 */     if (this.nested == null)
/*    */     {
/* 42 */       super.printStackTrace(ps);
/*    */     }
/*    */     else
/*    */     {
/* 46 */       synchronized (ps) {
/* 47 */         ps.println(this);
/* 48 */         this.nested.printStackTrace(ps);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public void printStackTrace()
/*    */   {
/* 55 */     printStackTrace(System.err);
/*    */   }
/*    */   
/*    */   public void printStackTrace(PrintWriter pw)
/*    */   {
/* 60 */     if (this.nested == null)
/*    */     {
/* 62 */       super.printStackTrace(pw);
/*    */     }
/*    */     else
/*    */     {
/* 66 */       synchronized (pw) {
/* 67 */         pw.println(this);
/* 68 */         this.nested.printStackTrace(pw);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              F:\1 人力资源开发服务公司\53 集团人力系统管理员管维方案\SHR二开资料\单点登录-8.2及以上版本等\demo示例\第三方应用集成s-HR\sso_web\WEB-INF\lib\shr_sso_client_820.jar!\com\kingdee\shr\CryptException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */