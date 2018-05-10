/*    */ package com.kingdee.test.https;
/*    */ 
/*    */ import java.security.cert.CertificateException;
/*    */ import java.security.cert.X509Certificate;
/*    */ import javax.net.ssl.SSLContext;
/*    */ import javax.net.ssl.TrustManager;
/*    */ import javax.net.ssl.X509TrustManager;
/*    */ import org.apache.http.conn.ClientConnectionManager;
/*    */ import org.apache.http.conn.scheme.Scheme;
/*    */ import org.apache.http.conn.scheme.SchemeRegistry;
/*    */ import org.apache.http.conn.ssl.SSLSocketFactory;
/*    */ import org.apache.http.impl.client.DefaultHttpClient;
/*    */ 
/*    */ public class SSLClient extends DefaultHttpClient
/*    */ {
/*    */   public SSLClient() throws Exception
/*    */   {
/* 18 */     SSLContext ctx = SSLContext.getInstance("TLS");
/* 19 */     X509TrustManager tm = new X509TrustManager()
/*    */     {
/*    */       public void checkClientTrusted(X509Certificate[] chain, String authType)
/*    */         throws CertificateException
/*    */       {}
/*    */       
/*    */ 
/*    */       public void checkServerTrusted(X509Certificate[] chain, String authType)
/*    */         throws CertificateException
/*    */       {}
/*    */       
/*    */       public X509Certificate[] getAcceptedIssuers()
/*    */       {
/* 32 */         return null;
/*    */       }
/* 34 */     };
/* 35 */     ctx.init(null, new TrustManager[] { tm }, null);
/* 36 */     SSLSocketFactory ssf = new SSLSocketFactory(ctx, 
/* 37 */       SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
/* 38 */     ClientConnectionManager ccm = getConnectionManager();
/* 39 */     SchemeRegistry sr = ccm.getSchemeRegistry();
/* 40 */     sr.register(new Scheme("https", 443, ssf));
/*    */   }
/*    */ }


/* Location:              F:\1 人力资源开发服务公司\53 集团人力系统管理员管维方案\SHR二开资料\单点登录-8.2及以上版本等\demo示例\第三方应用集成s-HR\sso_web\WEB-INF\lib\shr_sso_client_820.jar!\com\kingdee\test\https\SSLClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */