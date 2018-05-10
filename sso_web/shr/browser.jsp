<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.kingdee.shr.sso.client.util.SSOUtil"%>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
	<meta http-equiv="pragma" content="no-cache" />
    <title>金蝶s-HR</title>
    <link rel="stylesheet" href="styles/browser.css"/>
</head>

<body>
    <div class="main">
        <div class="logo"></div>
        <div class="caption2">请安装HR系统插件（安装目录C:\），安装成功后请重新登录s-HR系统！</div>
        <!--div class="caption2">请解压缩到C盘根目录下</div--> 
    </div>
    <div class="download">
        <div class="browsers">
            <div class="browser-box">
                <!--div class="browser-logo">
                    <img src="chrome-icon.png" width="60px">
                </div>
                <div class="browser-name">
                    	 浏览器插件
                </div-->
                <div class="address">
                  <!--a class="official" href="http://www.google.cn/intl/zh-CN/chrome/browser/">官方下载</a>
                  <a class="local" href="/easweb/newBrowser/Chrome_Final_27.exe">本地下载</a>
				  <a class="official" href="/easweb/newBrowser/ChromePortable.zip">下载</a-->
				  <a href="<%=SSOUtil.getServerUrl(request)%>/newBrowser/ChromePortable.exe">安装插件</a>
                </div>
            </div>
        </div>
    </div>
</body>
