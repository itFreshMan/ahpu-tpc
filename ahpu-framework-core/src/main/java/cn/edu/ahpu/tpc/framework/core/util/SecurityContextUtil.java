package cn.edu.ahpu.tpc.framework.core.util;

import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import cn.edu.ahpu.tpc.framework.core.spring.SpringApplicationContextHolder;
import cn.edu.ahpu.tpc.framework.web.model.admin.User;

import cn.edu.ahpu.common.uuid.RandomUniqueIdGenerator;
/**
 * SecurityContextHolder 帮助类
 *
 * @datetime 2010-8-12 下午01:45:25
 * @author libinsong1204@gmail.com
 */
public class SecurityContextUtil {

	/**
	 * 获取当前登录用户编码
	 * 
	 * @return String 用户编码
	 */
	public static String getLoginUserCode() {
		if(SecurityContextHolder.getContext().getAuthentication() == null)
			return "anonymous";
		else {
			String userCode = SecurityContextHolder.getContext().getAuthentication().getName();
			return userCode;
		}
	}
	
	/**
	 * 获取当前登录用户
	 * 
	 * @return
	 */
	public static User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null)
			return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		else
			return null;
	}
	
	/**
	 * 密码加密
	 * 
	 * @return
	 */
	public static String encodePassword(String rawPass, String salt) {
		Assert.hasText(salt);
		PasswordEncoder passwordEncoder = SpringApplicationContextHolder.getBean(PasswordEncoder.class);
		return passwordEncoder.encodePassword(rawPass, salt);
	}
	
	/**
	 * 生成密码Salt
	 */
	public static String generateRandomSalt() {
		return RandomUniqueIdGenerator.getNewString().toLowerCase();
	}
	
	/**
	 * 生成随机密码
	 */
	public static String generateRandomPassWord(int pwRandomLength) {
		String pwd = RandomUniqueIdGenerator.getNewString(pwRandomLength);
		return pwd.toLowerCase();
	}

}
