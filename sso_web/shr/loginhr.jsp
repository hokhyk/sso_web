<%@ page contentType="text/html;charset
<html>=UTF-8" %>
<!DOCTYPE html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>金蝶s-HR</title>
	<script type="text/javascript" src="scripts/jquery-1.7.2-mini.js"></script>
	<script type="text/javascript" src="scripts/common/shr.window.js"></script>
	<script type="text/javascript">		
		function loginHR() {
			var tokenUrl = '<%=request.getContextPath()%>/shr/sso2hrUrl.jsp';
			var redirectTo = shr.getRedirectTo();
			//alert(tokenUrl);
			//alert("redirectTo:"+redirectTo);
			shr.loginHR(tokenUrl, redirectTo);
		}
		
		loginHR();
	</script>
</head>
<body>
	<div id="main" style="display: none;">
		<!--input type = "button" value = "进入金蝶HR" onclick="loginHR();"/-->
		<font style="font-weight: bold;color: red;">系统检测到浏览器未设置信任站点或ActiveX控件未启用，请进行以下设置！</font><br><br>
		<font style="font-weight: bold;">1、将当前地址设置为（如：http://wwww.kingdee.com）设置为信任站点，在IE浏览器菜单->工具->Internet选项：</font><br><br>
		<img src="images/site.png">
		<br><br>

		<font style="font-weight: bold;">2、设置ActiveX控件启用：</font><br><br>
		<img src="images/activex.png">
	</div>
</body>
</html>