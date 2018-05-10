<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>金蝶s-HR</title>
	<script type="text/javascript" src="scripts/jquery-1.7.2-mini.js"></script>
	<script type="text/javascript">		
		$.ajax({
			dataType: 'html',
			url: '/shr/sso2hr.jsp?redirectTo=%2Fhome.do',
			success: function(xhr) {
				console.log(xhr.responseText);
			}
		});
		
		$.ajax({
			url: '/shr/sso2hr.jsp?redirectTo=%2Fshr%2Fmsf%2Fservice.do%3Fpage%3D1%26rows%3D100%26total%3D100%26category%3D%26method%3DcallService%26serviceName%3DgetUnProcessAssign',
			success: function() {
				console.log(xhr.responseText);
			}
		})
	</script>
</head>
<body>

</body>
</html>