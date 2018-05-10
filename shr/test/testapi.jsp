<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.kingdee.shr.api.SHRClient"%>
<%@ page import="com.kingdee.shr.api.Token"%>
<%@ page import="com.kingdee.shr.api.Response"%>
<%@ page import="java.util.Map, java.util.HashMap"%>

<%
	SHRClient client = new SHRClient();
	
	Response count = client.execute(request, "getUnProcessAssignCount", null);
	
	Map<String, Object> parameter = new HashMap<String, Object>();
	parameter.put("page", 1);
	parameter.put("rows", 10);
	parameter.put("total", 100);
	Response list = client.execute(request, "getUnProcessAssign", parameter);
%>

count: <%=count.getData()%><br><br>
getUnProcessAssign: <%=list.getData()%>