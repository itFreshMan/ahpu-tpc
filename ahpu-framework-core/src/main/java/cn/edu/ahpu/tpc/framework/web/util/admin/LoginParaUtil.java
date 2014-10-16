//$Id$
/**
* 工程名: 	usi-framework-core
* 文件名: 	LoginParaUtil.java
* 创建人:  	fang.fang
* 创建时间: 	2013-4-13 上午11:43:45
* 版权所有：	Copyright (c) 2013 苏州科大国创信息技术有限公司  
* -----------------------------变更记录 ----------------------------- 
* 日期        		变更人      		版本号  		变更描述  
* ------------------------------------------------------------------  
* 2013-4-13     	fang.fang		1.0.0      	first created  
*/
package cn.edu.ahpu.tpc.framework.web.util.admin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

/**
 * @since usi-framework-core 1.0.0
 * @version 1.0.0 2013-4-13
 * @author fang.fang@ustcinfo.com
 */
public class LoginParaUtil {
	@Value("${login.registerHidden}")
	private String register;
	
	private static String registerValue;
	
	@Value("${login.autoLoginHidden}")
	private String autoLogin;
	private static String autoLoginValue;
	
	@PostConstruct
	public void init(){
		registerValue = register;
		autoLoginValue = autoLogin;
	}
	
	public static String getRegisterValue(){
		return registerValue;
	}
	
	public static String getAutoLoginValue(){
		return autoLoginValue;
	}
	
	public void setRegister(String register) {
		this.register = register;
	}

	public void setAutoLogin(String autoLogin) {
		this.autoLogin = autoLogin;
	}

	public String getRegister() {
		return register;
	}

	public String getAutoLogin() {
		return autoLogin;
	}

	
	
}
