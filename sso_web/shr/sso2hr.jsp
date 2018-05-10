<%--
sso单点登录跳转至shr
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.kingdee.shr.sso.client.util.SSOUtil"%>

<%
	String url = SSOUtil.generateUrl(request);	
	System.out.println("url: " + url);
	if (url != null) {
		response.sendRedirect(url);
	}
%>

请求的sso url为空，请联系系统管理员。