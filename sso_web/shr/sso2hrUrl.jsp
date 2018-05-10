<%--
获得sso单点登录跳转至shr的url
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.kingdee.shr.sso.client.util.SSOUtil"%>

<%

	String userName = "huyao";
	session.setAttribute("username", userName);
	String url = SSOUtil.generateUrl(request);
	System.out.println("url: " + url);
	response.setHeader("Access-Control-Allow-Origin", "http://10.1.1.205");
%>
<%=url%>