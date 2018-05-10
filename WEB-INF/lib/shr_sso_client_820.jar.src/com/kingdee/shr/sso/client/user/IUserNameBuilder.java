package com.kingdee.shr.sso.client.user;

import javax.servlet.http.HttpServletRequest;

public abstract interface IUserNameBuilder
{
  public abstract String getUserName(HttpServletRequest paramHttpServletRequest);
}


/* Location:              F:\1 人力资源开发服务公司\53 集团人力系统管理员管维方案\SHR二开资料\单点登录-8.2及以上版本等\demo示例\第三方应用集成s-HR\sso_web\WEB-INF\lib\shr_sso_client_820.jar!\com\kingdee\shr\sso\client\user\IUserNameBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */