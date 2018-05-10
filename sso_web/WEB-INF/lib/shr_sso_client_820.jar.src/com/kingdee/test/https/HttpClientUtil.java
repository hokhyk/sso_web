/*    */ package com.kingdee.test.https;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ import java.util.Set;
/*    */ import org.apache.http.HttpEntity;
/*    */ import org.apache.http.HttpResponse;
/*    */ import org.apache.http.NameValuePair;
/*    */ import org.apache.http.client.HttpClient;
/*    */ import org.apache.http.client.entity.UrlEncodedFormEntity;
/*    */ import org.apache.http.client.methods.HttpPost;
/*    */ import org.apache.http.util.EntityUtils;
/*    */ 
/*    */ public class HttpClientUtil
/*    */ {
/*    */   public String doPost(String url, Map<String, String> map, String charset)
/*    */   {
/* 21 */     HttpClient httpClient = null;
/* 22 */     HttpPost httpPost = null;
/* 23 */     String result = null;
/*    */     try {
/* 25 */       httpClient = new SSLClient();
/* 26 */       httpPost = new HttpPost(url);
/*    */       
/* 28 */       List<NameValuePair> list = new ArrayList();
/* 29 */       Iterator iterator = map.entrySet().iterator();
/* 30 */       while (iterator.hasNext()) {
/* 31 */         Map.Entry<String, String> elem = (Map.Entry)iterator.next();
/* 32 */         list.add(new org.apache.http.message.BasicNameValuePair((String)elem.getKey(), (String)elem.getValue()));
/*    */       }
/* 34 */       if (list.size() > 0) {
/* 35 */         UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
/* 36 */         httpPost.setEntity(entity);
/*    */       }
/* 38 */       HttpResponse response = httpClient.execute(httpPost);
/* 39 */       if (response != null) {
/* 40 */         HttpEntity resEntity = response.getEntity();
/* 41 */         if (resEntity != null) {
/* 42 */           result = EntityUtils.toString(resEntity, charset);
/*    */         }
/*    */       }
/*    */     } catch (Exception ex) {
/* 46 */       ex.printStackTrace();
/*    */     }
/* 48 */     return result;
/*    */   }
/*    */ }


/* Location:              F:\1 人力资源开发服务公司\53 集团人力系统管理员管维方案\SHR二开资料\单点登录-8.2及以上版本等\demo示例\第三方应用集成s-HR\sso_web\WEB-INF\lib\shr_sso_client_820.jar!\com\kingdee\test\https\HttpClientUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */