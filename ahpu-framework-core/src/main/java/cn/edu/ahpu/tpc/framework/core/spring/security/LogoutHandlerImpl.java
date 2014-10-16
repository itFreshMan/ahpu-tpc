//$Id$
/**
* 工程名: 	usi-framework-core
* 文件名: 	LogoutSuccessHandler.java
* 创建人:  	wang.xiaoyan
* 创建时间: 	2013-3-15 下午5:59:54
* 版权所有：	Copyright (c) 2013 苏州科大恒星信息技术有限公司  
* -----------------------------变更记录 ----------------------------- 
* 日期        		变更人      		版本号  		变更描述  
* ------------------------------------------------------------------  
* 2013-3-15     		wang.xiaoyan   			1.0.0       	first created  
*/
package cn.edu.ahpu.tpc.framework.core.spring.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import cn.edu.ahpu.tpc.framework.web.model.admin.LoginLog;
import cn.edu.ahpu.tpc.framework.web.service.admin.LoginLogService;

/**
 * @since usi-framework-core 1.0.0
 * @version 1.0.0 2013-3-15
 * @author wang.xiaoyan
 */
public class LogoutHandlerImpl implements LogoutHandler{
	
	private static final Logger logger = LoggerFactory.getLogger(LogoutHandlerImpl.class);

	@Autowired
	private LoginLogService loginLogService;
	/**
	* @param request
	* @param response
	* @param authentication
	* @see org.springframework.security.web.authentication.logout.LogoutHandler#logout(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	*/
	@Override
	public void logout(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) {
		String sessionId = request.getSession().getId();
		LoginLog loginLog = loginLogService.queryLoginLogBySessionId(sessionId);
		if(loginLog==null){
			logger.info("sessionId = "+sessionId+",can not find LoginLog");
		}else{
			loginLog.setLogoutDate(new Date());
			loginLogService.updateEntity(loginLog);
		}
		
	}

	

}
