<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
 <head>
  <title>登陆</title>
 </head>
 <body>
  <form method="POST" name="frmLogin" action="LoginServlet">
   <h1 align="center">用户登录</h1><br />
   <center>
    <table  style="width:400px; height: 200px; background-color: gray;">
     <tr>
      <td style="text-align: right;">用户名：</td>
      <td>
       <input type="text" name="username" value="Your name" size="20" maxlength="20" onfocus="if (this.value=='Your name')  this.value='';" />
      </td>
     </tr>
     <tr>
      <td style="text-align: right;">密&nbsp;&nbsp;码：</td>
      <td>
       <input type="password" name="password" value="Your password" size="20" maxlength="20" onfocus="if (this.value=='Your password')  this.value='';" />
      </td>
     </tr>
     <tr>
      <td colspan="2">
      <center>
       <input type="submit" name="Submit" value="提交" onClick="return validateLogin()" />
     	&nbsp;&nbsp;&nbsp;&nbsp;
       <input type="reset" name="Reset" value="重置" />
       </center>
      </td>
     </tr>
    </table>
   </center>
  </form>
  <script language="javascript">
   function validateLogin(){
    var sUserName = document.frmLogin.username.value ;
    var sPassword = document.frmLogin.password.value ;
    if ((sUserName =="") || (sUserName=="Your name")){
     alert("请输入用户名!");
     return false ;
    }
    
    if ((sPassword =="") || (sPassword=="Your password")){
     alert("请输入密码!");
     return false ;
    }
   }
  </script>
 </body>
</html>