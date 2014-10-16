/**
 * Copyright (c) 2012,USTC E-BUSINESS TECHNOLOGY CO.LTD All Rights Reserved.
 */

package cn.edu.ahpu.tpc.framework.core.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

/**
 * @author bsli123@starit.com.cn
 * @date 2012-2-3 下午3:11:24
 */
public class AjaxUtil {
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String requestedWith = request.getHeader("X-Requested-With");
		
		return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
	}
	
	public static String getRemoteAddr(HttpServletRequest request) {  
	    String remoteIp = request.getHeader("X-Real-IP"); //nginx反向代理  
	    if (StringUtils.hasText(remoteIp)) {  
	        return remoteIp;  
	    } else {  
	        remoteIp = request.getHeader("x-forwarded-for");//apache反射代理  
	        if (StringUtils.hasText(remoteIp)) {  
	            String[] ips = remoteIp.split(",");  
	            for (String ip : ips) {  
	                if (!"null".equalsIgnoreCase(ip)) {  
	                    return ip;  
	                }  
	            }  
	        }  
	        return request.getRemoteAddr();  
	    }  
	}  

	private AjaxUtil() {}
}
