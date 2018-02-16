package com.xmg.p2p.base.util;

import com.xmg.p2p.base.domain.Logininfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * 用于存放当前用户的上下文
 * @author Administrator
 */
public class UserContext {

	public static final String USER_IN_SESSION = "logininfo";
	public static final String VERIFYCODE_IN_SESSION = "verifycode_in_session";


	/**反向获取request的方法,请查看RequestContextListener.requestInitialized打包过程*/
	private static HttpSession getSession() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
	}

	/**先得到session,并把current放到session中*/
	public static void putCurrent(Logininfo current) {
		getSession().setAttribute(USER_IN_SESSION, current);
	}

	/**先得到session,从session中获取current*/
	public static Logininfo getCurrent() {
		return (Logininfo) getSession().getAttribute(USER_IN_SESSION);
	}

	/*public static void putVerifyCode(VerifyCodeVO vc) {
		getSession().setAttribute(VERIFYCODE_IN_SESSION, vc);
	}

	*//**得到当前的短信验证码*//*
	public static VerifyCodeVO getCurrentVerifyCode() {
		return (VerifyCodeVO) getSession().getAttribute(VERIFYCODE_IN_SESSION);
	}*/

}
