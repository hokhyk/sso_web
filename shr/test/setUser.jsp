<%
	String userName = request.getParameter("userName");
	if (userName == null) {
		userName = "ljj";
	}
	request.getSession().setAttribute("userName", userName);
%>

userName: <%=userName%>