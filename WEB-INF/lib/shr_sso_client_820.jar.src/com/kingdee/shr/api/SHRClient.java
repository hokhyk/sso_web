/*     */ package com.kingdee.shr.api;
/*     */ 
/*     */ import com.kingdee.shr.sso.client.ltpa.LtpaToken;
/*     */ import com.kingdee.shr.sso.client.ltpa.LtpaTokenManager;
/*     */ import com.kingdee.shr.sso.client.util.SSOUtil;
/*     */ import com.kingdee.shr.sso.client.util.UrlUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.httpclient.Cookie;
/*     */ import org.apache.commons.httpclient.HttpClient;
/*     */ import org.apache.commons.httpclient.HttpException;
/*     */ import org.apache.commons.httpclient.HttpState;
/*     */ import org.apache.commons.httpclient.methods.GetMethod;
/*     */ import org.apache.commons.httpclient.methods.PostMethod;
/*     */ import org.apache.commons.httpclient.params.HttpClientParams;
/*     */ import org.apache.commons.httpclient.params.HttpMethodParams;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SHRClient
/*     */ {
/*     */   private static final String ALLOW_CIRCULAR_REDIRECTS = "http.protocol.allow-circular-redirects";
/*     */   private static final String JSESSIONID_KEY = "JSESSIONID";
/*     */   private static final String SERVICE_PATH = "/shr/msf/service.do";
/*     */   private static final String SERVICE_NAME = "serviceName";
/*     */   private static final String METHOD = "method";
/*     */   private static final String SHR_SSO_TOKEN = "_shr_sso_token";
/*     */   
/*     */   public Token login(HttpServletRequest request)
/*     */     throws HttpException, IOException
/*     */   {
/*  41 */     return login(request, null);
/*     */   }
/*     */   
/*     */   public Token login(HttpServletRequest request, String serverUrl)
/*     */     throws HttpException, IOException
/*     */   {
/*  47 */     String url = SSOUtil.generateUrl(request, serverUrl);
/*     */     
/*  49 */     return loginShr(request, url);
/*     */   }
/*     */   
/*     */   public Token loginByUser(HttpServletRequest request)
/*     */     throws HttpException, IOException
/*     */   {
/*  55 */     return loginByUser(request, null);
/*     */   }
/*     */   
/*     */   public Token loginByUser(HttpServletRequest request, String serverUrl)
/*     */     throws HttpException, IOException
/*     */   {
/*  61 */     String loginUrl = SSOUtil.getLoginUrl(request, serverUrl);
/*  62 */     Map parameters = new HashMap();
/*     */     
/*  64 */     parameters.put("username", "user");
/*  65 */     SSOUtil.assembleLoginParameters(request, parameters);
/*  66 */     String url = UrlUtil.assembleUrl(loginUrl, parameters);
/*  67 */     return loginShr(request, url);
/*     */   }
/*     */   
/*     */   public Response executeService(String serverUrl, String serviceName, Map<String, Object> param)
/*     */     throws HttpException, IOException
/*     */   {
/*  73 */     HttpClient client = new HttpClient();
/*  74 */     loginShrByUser(serverUrl, client);
/*  75 */     return executeOSFService(serverUrl, serviceName, param, client);
/*     */   }
/*     */   
/*     */   public Response executeOSFService(String serverUrl, String serviceName, Map<String, Object> param, HttpClient client) throws HttpException, IOException
/*     */   {
/*  80 */     String loginUrl = serverUrl + "/shr/msf/service.do";
/*  81 */     Map parameters = new HashMap();
/*  82 */     parameters.put("method", "callService");
/*  83 */     parameters.put("serviceName", serviceName);
/*     */     
/*  85 */     String url = UrlUtil.assembleUrl(loginUrl, parameters);
/*     */     
/*  87 */     client.getParams().setParameter("http.protocol.allow-circular-redirects", Boolean.valueOf(true));
/*  88 */     PostMethod method = new PostMethod(url);
/*     */     
/*  90 */     if ((param != null) && (param.size() > 0)) {
/*  91 */       Iterator iter = param.entrySet().iterator();
/*  92 */       while (iter.hasNext()) {
/*  93 */         Map.Entry entry = (Map.Entry)iter.next();
/*     */         
/*  95 */         method.setParameter(entry.getKey().toString(), entry.getValue().toString());
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 100 */     method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
/*     */     
/* 102 */     method.setRequestHeader("Referer", loginUrl);
/*     */     
/* 104 */     String cookieString = UrlUtil.cookiesToString(client.getState().getCookies());
/*     */     
/* 106 */     method.setRequestHeader("Cookie", cookieString);
/*     */     
/*     */ 
/* 109 */     int status = client.executeMethod(method);
/* 110 */     Response response = new Response();
/* 111 */     if (status == 200) {
/* 112 */       String content = method.getResponseBodyAsString();
/* 113 */       response.setData(content);
/*     */     } else {
/* 115 */       System.err.println("Method failed: " + method.getStatusLine());
/* 116 */       System.err.println("response: " + method.getResponseBodyAsString());
/* 117 */       throw new HttpException("status: " + status + ", request url: " + url + " failed");
/*     */     }
/* 119 */     method.releaseConnection();
/*     */     
/* 121 */     return response;
/*     */   }
/*     */   
/*     */   public Token loginShrByUser(String serverUrl, HttpClient client) throws IOException, HttpException
/*     */   {
/* 126 */     String loginUrl = serverUrl + "/OTP2sso.jsp";
/* 127 */     Map parameters = new HashMap();
/* 128 */     parameters.put("username", "user");
/* 129 */     String password = LtpaTokenManager.generate((String)parameters.get("username"), LtpaTokenManager.getDefaultLtpaConfig(), "otp").toString();
/* 130 */     parameters.put("password", password);
/* 131 */     parameters.put("userAuthPattern", "otp");
/* 132 */     String url = UrlUtil.assembleUrl(loginUrl, parameters);
/* 133 */     client.getParams().setParameter("http.protocol.allow-circular-redirects", Boolean.valueOf(true));
/* 134 */     GetMethod method = new GetMethod(url);
/* 135 */     method.getParams().setParameter("http.protocol.single-cookie-header", Boolean.valueOf(true));
/* 136 */     method.getParams().setParameter("http.protocol.cookie-policy", "compatibility");
/*     */     
/* 138 */     method.setFollowRedirects(true);
/* 139 */     int status = client.executeMethod(method);
/* 140 */     Token token = null;
/* 141 */     if (status != 200) {
/* 142 */       System.err.println("Method failed: " + method.getStatusLine());
/* 143 */       throw new HttpException("login shr fail, status: " + status);
/*     */     }
/* 145 */     token = getToken(client.getState().getCookies());
/*     */     
/* 147 */     return token;
/*     */   }
/*     */   
/*     */   private Token loginShr(HttpServletRequest request, String loginUrl) throws IOException, HttpException
/*     */   {
/* 152 */     HttpClient client = new HttpClient();
/* 153 */     client.getParams().setParameter("http.protocol.allow-circular-redirects", Boolean.valueOf(true));
/* 154 */     GetMethod method = new GetMethod(loginUrl);
/* 155 */     int status = client.executeMethod(method);
/* 156 */     Token token = null;
/* 157 */     if (status != 200) {
/* 158 */       System.err.println("Method failed: " + method.getStatusLine());
/* 159 */       throw new HttpException("login shr fail, status: " + status);
/*     */     }
/* 161 */     token = getToken(client.getState().getCookies());
/*     */     
/* 163 */     method.releaseConnection();
/* 164 */     request.getSession().setAttribute("_shr_sso_token", token);
/*     */     
/* 166 */     return token;
/*     */   }
/*     */   
/*     */   private Token getToken(Cookie[] cookies) throws HttpException {
/* 170 */     Cookie cookie = null;
/* 171 */     for (int i = 0; i < cookies.length; i++) {
/* 172 */       cookie = cookies[i];
/* 173 */       if (("JSESSIONID".equalsIgnoreCase(cookie.getName())) && (cookie.getPath().indexOf("sso") == -1)) {
/* 174 */         Token token = new Token();
/* 175 */         token.setDomain(cookie.getDomain());
/* 176 */         token.setPath(cookie.getPath());
/* 177 */         token.setValue(cookie.getValue());
/* 178 */         return token;
/*     */       }
/*     */     }
/*     */     
/* 182 */     throw new HttpException("getToken fail, cookies: " + cookieToString(cookies));
/*     */   }
/*     */   
/*     */   private String cookieToString(Cookie[] cookies) {
/* 186 */     Cookie cookie = null;
/* 187 */     StringBuffer sb = new StringBuffer();
/* 188 */     for (int i = 0; i < cookies.length; i++) {
/* 189 */       cookie = cookies[i];
/* 190 */       if (i != 0) {
/* 191 */         sb.append(",");
/*     */       }
/* 193 */       sb.append("[name=");
/* 194 */       sb.append(cookie.getName());
/* 195 */       sb.append(", path=");
/* 196 */       sb.append(cookie.getPath());
/* 197 */       sb.append(", value=");
/* 198 */       sb.append(cookie.getValue());
/* 199 */       sb.append("]");
/*     */     }
/* 201 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public Response execute(HttpServletRequest request, String serverUrl, String serviceName, Map<String, Object> parameters)
/*     */     throws IOException
/*     */   {
/* 207 */     if ((serverUrl == null) || (serverUrl.length() == 0)) {
/* 208 */       throw new HttpException("serverUrl is null");
/*     */     }
/*     */     
/* 211 */     if (serviceName == null) {
/* 212 */       throw new HttpException("serviceName is null");
/*     */     }
/*     */     
/* 215 */     Token token = (Token)request.getSession().getAttribute("_shr_sso_token");
/* 216 */     if (token == null) {
/* 217 */       token = login(request);
/*     */     }
/*     */     
/* 220 */     HttpClient client = new HttpClient();
/* 221 */     addToken(client, token);
/* 222 */     client.getParams().setParameter("http.protocol.allow-circular-redirects", Boolean.valueOf(true));
/* 223 */     String url = generateRequestUrl(serverUrl, serviceName, parameters);
/* 224 */     GetMethod method = new GetMethod(url);
/* 225 */     int status = client.executeMethod(method);
/* 226 */     Response response = new Response();
/* 227 */     if (status == 200) {
/* 228 */       String content = method.getResponseBodyAsString();
/* 229 */       response.setData(content);
/*     */     } else {
/* 231 */       System.err.println("Method failed: " + method.getStatusLine());
/* 232 */       throw new HttpException("status: " + status + ", request url: " + url + " failed");
/*     */     }
/* 234 */     method.releaseConnection();
/*     */     
/* 236 */     return response;
/*     */   }
/*     */   
/*     */   public Response execute(HttpServletRequest request, String serviceName, Map<String, Object> parameters)
/*     */     throws IOException
/*     */   {
/* 242 */     String serverUrl = SSOUtil.getServerUrl(request);
/* 243 */     return execute(request, serverUrl, serviceName, parameters);
/*     */   }
/*     */   
/*     */   private void addToken(HttpClient client, Token token) {
/* 247 */     Cookie cookie = new Cookie();
/* 248 */     cookie.setName("JSESSIONID");
/* 249 */     cookie.setDomain(token.getDomain());
/* 250 */     cookie.setPath(token.getPath());
/* 251 */     cookie.setValue(token.getValue());
/* 252 */     client.getState().addCookie(cookie);
/*     */   }
/*     */   
/*     */   private String generateRequestUrl(String serverUrl, String serviceName, Map<String, Object> parameters) {
/* 256 */     if (parameters == null) {
/* 257 */       parameters = new HashMap();
/*     */     }
/* 259 */     parameters.put("serviceName", serviceName);
/* 260 */     parameters.put("method", "callService");
/*     */     
/* 262 */     String url = serverUrl + "/shr/msf/service.do";
/*     */     
/* 264 */     return UrlUtil.assembleUrl(url, parameters);
/*     */   }
/*     */   
/*     */   public static void main(String[] args) throws HttpException, IOException {
/* 268 */     SHRClient client = new SHRClient();
/* 269 */     Map param = new HashMap();
/* 270 */     param.put("billId", "dghEiOF+TUW4h5Wr9gVQKaDzlng=");
/* 271 */     param.put("auditFlag", "auditpass");
/* 272 */     client.executeService(" http://localhost:6888/shr ", "auditLeave_new", param);
/*     */   }
/*     */ }


/* Location:              F:\1 人力资源开发服务公司\53 集团人力系统管理员管维方案\SHR二开资料\单点登录-8.2及以上版本等\demo示例\第三方应用集成s-HR\sso_web\WEB-INF\lib\shr_sso_client_820.jar!\com\kingdee\shr\api\SHRClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */