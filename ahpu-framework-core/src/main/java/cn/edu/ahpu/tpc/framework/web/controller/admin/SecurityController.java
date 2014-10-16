package cn.edu.ahpu.tpc.framework.web.controller.admin;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.ahpu.tpc.framework.core.spring.security.AuthenticationFailureHandlerImpl;
import cn.edu.ahpu.tpc.framework.core.spring.session.OnlineUserHolder;
import cn.edu.ahpu.tpc.framework.core.util.BrowserUtils;
import cn.edu.ahpu.tpc.framework.core.util.ResponseData;
import cn.edu.ahpu.tpc.framework.core.util.SecurityContextUtil;
import cn.edu.ahpu.tpc.framework.web.controller.BaseController;
import cn.edu.ahpu.tpc.framework.web.model.admin.User;
import cn.edu.ahpu.tpc.framework.web.service.admin.LoginLogService;
import cn.edu.ahpu.tpc.framework.web.service.admin.UserService;

/**
 * 安全认证和授权Controller。
 * 
 * @datetime 2010-8-8 上午10:38:27
 * @author libinsong1204@gmail.com
 */
@Controller
@RequestMapping("/security")
public class SecurityController extends BaseController {
	// ~ Instance fields
	// ================================================================================================
	public static final String SPRING_SECURITY_LAST_EXCEPTION_KEY = WebAttributes.AUTHENTICATION_EXCEPTION;

	public static final String SPRING_SECURITY_LAST_USERNAME_KEY = AuthenticationFailureHandlerImpl.SPRING_SECURITY_LAST_USERNAME_KEY;
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private LoginLogService loginLogService;

	// ~ Methods
	// ========================================================================================================
	/**
	 * Spring Security认证成功后，继续调用该方法
	 * 
	 * @return
	 */
	@RequestMapping("/loginSuccess")
	@ResponseBody
	public ResponseData loginSuccess(HttpServletRequest request) {
		String userCode = SecurityContextUtil.getLoginUserCode();
		userService.recordLoginSuccess(userCode, request.getRemoteAddr());
		
		String ip = request.getRemoteAddr();
		/*String clientType = request.getHeader("user-agent");*/
		String clientType=BrowserUtils.checkBrowse(request);
		
		String sessionId = request.getSession().getId();
		String loginStatus = "SUCCESS";
		loginLogService.insertLoginLog(userCode,sessionId,ip,clientType,loginStatus);
		
		return ResponseData.SUCCESS_NO_DATA;
	}

	/**
	 * Spring Security认证失败后，继续调用该方法
	 * 
	 * @return
	 */
	@RequestMapping("/loginFailure")
	@ResponseBody
	public ResponseData loginFailure(HttpServletRequest request) {
		AuthenticationException failed = (AuthenticationException) request.getAttribute(SPRING_SECURITY_LAST_EXCEPTION_KEY);
		
		String userCode = (String) request.getSession().getAttribute(SPRING_SECURITY_LAST_USERNAME_KEY);
		String ip = request.getRemoteAddr();
		String clientType=BrowserUtils.checkBrowse(request);
		String sessionId = request.getSession().getId();
		String loginStatus = "FAILURE";
		loginLogService.insertLoginLog(userCode,sessionId,ip,clientType,loginStatus);
		
		if (failed instanceof UsernameNotFoundException) {
			return new ResponseData(false, "UsernameNotFound", "用户【" + userCode + "】不存在.");
		} else if (failed instanceof BadCredentialsException) {
			return new ResponseData(false, "BadCredentials", "密码不正确，请重新输入.");
		} else {
			logger.error("login failure, " + failed.getMessage(), failed);
			return ResponseData.FAILED_NO_DATA;
		}
	}
	
	@RequestMapping("/findOnlineUsers")
	@ResponseBody
	public Collection<User> findOnlineUsers() {
		return OnlineUserHolder.findOnlineUsers();
	}

}
