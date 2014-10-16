//$Id$
/**
 * 工程名: 	usi-framework-core
 * 文件名: 	AuthenticationFailureHandlerImpl.java
 * 创建人:  	wang.xiaoyan
 * 创建时间: 	2013-3-18 上午11:55:24
 * 版权所有：	Copyright (c) 2013 苏州科大恒星信息技术有限公司  
 * -----------------------------变更记录 ----------------------------- 
 * 日期        		变更人      		版本号  		变更描述  
 * ------------------------------------------------------------------  
 * 2013-3-18     		wang.xiaoyan   			1.0.0       	first created  
 */
package cn.edu.ahpu.tpc.framework.core.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 * @since usi-framework-core 1.0.0
 * @version 1.0.0 2013-3-18
 * @author wang.xiaoyan
 */
public class AuthenticationFailureHandlerImpl extends
		SimpleUrlAuthenticationFailureHandler {

	public static final String SPRING_SECURITY_LAST_USERNAME_KEY = "SPRING_SECURITY_LAST_USERNAME";

	/**
	 * 保存用户名
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		request.getSession().setAttribute(SPRING_SECURITY_LAST_USERNAME_KEY, 
				exception.getAuthentication().getPrincipal());

		super.onAuthenticationFailure(request, response, exception);
	}

}
