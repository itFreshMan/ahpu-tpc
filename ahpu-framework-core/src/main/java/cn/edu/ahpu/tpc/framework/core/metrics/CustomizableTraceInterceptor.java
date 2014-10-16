/**
 * Copyright (c) 2011, SuZhou USTC Star Information Technology CO.LTD
 * All Rights Reserved.
 */

package cn.edu.ahpu.tpc.framework.core.metrics;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.edu.ahpu.tpc.framework.core.metrics.support.HttpRequestManager;
import cn.edu.ahpu.tpc.framework.core.metrics.support.HttpRequestStat;
import cn.edu.ahpu.tpc.framework.core.util.SecurityContextUtil;
import cn.edu.ahpu.tpc.framework.web.controller.BaseController;

/**
 * 取消BaseControlle$handleException方法中异常信息的打印。执行Controller中方法跑出异常，统一在afterCompletion方法输出。
 * 
 * @see BaseController
 * @author   bsli@starit.com.cn
 * @Date	 2011-8-2 上午09:57:25
 */
public class CustomizableTraceInterceptor extends
		HandlerInterceptorAdapter {
	private final static Logger LOGGER = LoggerFactory.getLogger("com.unteck.request.log");
	
	private static final ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
	
	//访问用户占位符
	public static final String PLACEHOLDER_USER_CODE = "$[userCode]";
	
	//客户端访问地址占位符
	public static final String PLACEHOLDER_REMOTE_ADDR = "$[remoteAddr]";
	
	//请求URI占位符
	public static final String PLACEHOLDER_REQUEST_URI = "$[requestURI]";
	
	//访问浏览器占位符
	public static final String PLACEHOLDER_USER_AGENT = "$[userAgent]";
	
	//请求时间占位符
	public static final String PLACEHOLDER_REQUEST_METHOD = "$[requestMethod]";
	
	//CPU时间占位符
	public static final String PLACEHOLDER_CPU_TIME = "$[cpuTime]";
	
	//请求参数占位符
	public static final String PLACEHOLDER_REQUEST_PARAMETER = "$[requestParameter]";
	
	//请求时间占位符
	public static final String PLACEHOLDER_REQUEST_TIME = "$[requestTime]";
	
	//执行Controller类的方法信息占位符
	public static final String PLACEHOLDER_HANDLER_METHOD = "$[handlerMethod]";
	
	//异常信息占位符
	public static final String PLACEHOLDER_EXCEPTION = "$[exception]";
	
	//Pattern被使用匹配占位符
	private static final Pattern PATTERN = Pattern.compile("\\$\\[\\p{Alpha}+\\]");
	
	private final static String LOG_START_TIME = "LOG_START_TIME";
	
	private PathMatcher pathMatcher = new AntPathMatcher();
	
	/**
	 * 排除请求的URI，请求地址的前缀是否在其中
	 */
	private String[] excludeFilters;
	
	private String message = "$[userCode] - $[remoteAddr] - [$[requestMethod], cpuTime:$[cpuTime], requestTime:$[requestTime]]$[requestURI] - [$[requestParameter]]";
	private String excetionEessage = "$[userCode] - $[remoteAddr] - [$[requestMethod], cpuTime:$[cpuTime], requestTime:$[requestTime]]$[requestURI] - [$[requestParameter]]\n$[exception]";
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		long startTime = System.nanoTime();
		request.setAttribute(LOG_START_TIME, startTime);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String requestURI = request.getRequestURI();
		int index = requestURI.indexOf("?");
		if(index != -1)
			requestURI = requestURI.substring(0, index);
		
        if(excludeFilter(request, requestURI)) {
        	replacePlaceholders(request, ex, requestURI, handler.toString());
        }
	}
	
	public boolean excludeFilter(HttpServletRequest request, String requestURI) {
		for(String str : excludeFilters) {
			if(pathMatcher.matchStart(request.getContextPath() + str, requestURI)) {
				return false;
			}
		}
		
		return true;
	}
	
	protected void replacePlaceholders(HttpServletRequest request, Exception ex, String requestURI, 
			String handlerMethod) {

		String userCode = SecurityContextUtil.getLoginUserCode();
        String remoteAddr = request.getRemoteAddr();
        String agent = request.getHeader("user-agent");
        String requestMethod = request.getMethod();
        
        long startTime = (Long)request.getAttribute(LOG_START_TIME);
		long endTime = System.nanoTime();
		long requestTime = endTime - startTime;
		
		long cupTime = mxBean.getCurrentThreadCpuTime() / (1000 * 1000);
		
		String info = (String)request.getAttribute(BaseController.EXCEPTION_MESSAGE);
		Matcher matcher = null;
		boolean errorMessage = false;
		if(ex != null || info != null) {
			matcher = PATTERN.matcher(excetionEessage);
			errorMessage = true;
		} else
			matcher = PATTERN.matcher(message);

		StringBuffer output = new StringBuffer();
		while (matcher.find()) {
			String match = matcher.group();
			if (PLACEHOLDER_USER_CODE.equals(match)) {
				matcher.appendReplacement(output, userCode);
			} else if (PLACEHOLDER_REMOTE_ADDR.equals(match)) {
				matcher.appendReplacement(output, remoteAddr);
			} else if (PLACEHOLDER_REQUEST_URI.equals(match)) {
				matcher.appendReplacement(output, requestURI);
			} else if (PLACEHOLDER_USER_AGENT.equals(match)) {
				matcher.appendReplacement(output, agent);
			} else if (PLACEHOLDER_REQUEST_METHOD.equals(match)) {
				matcher.appendReplacement(output, requestMethod);
			} else if (PLACEHOLDER_REQUEST_TIME.equals(match)) {
				matcher.appendReplacement(output, String.valueOf(requestTime / (1000 * 1000)));
			} else if (PLACEHOLDER_CPU_TIME.equals(match)) {
                matcher.appendReplacement(output, String.valueOf(cupTime));
			} else if (PLACEHOLDER_HANDLER_METHOD.equals(match)) {
				matcher.appendReplacement(output, Matcher.quoteReplacement(handlerMethod));
			} else if (PLACEHOLDER_REQUEST_PARAMETER.equals(match)) { 
				matcher.appendReplacement(output, getRequestParameter(request));
			} else if (PLACEHOLDER_EXCEPTION.equals(match)) { 
				if(ex != null)
					matcher.appendReplacement(output, ExceptionUtils.getFullStackTrace(ex));
				else {
					matcher.appendReplacement(output, Matcher.quoteReplacement(info));
				}
			}
		}
		matcher.appendTail(output);
		
		if(errorMessage)
			LOGGER.error(output.toString());
		else
			LOGGER.info(output.toString());
		
		statisticsHttpRequests(requestURI, requestTime, ex);
	}
	
	private void statisticsHttpRequests(String requestURI, long requestTime, Exception ex) {
		HttpRequestStat httpRequestStat = new HttpRequestStat(requestURI);
		HttpRequestStat __httpRequestStat = 
        	HttpRequestManager.getInstance().getHttpRequests().putIfAbsent(requestURI, httpRequestStat);
		if(__httpRequestStat != null)
			httpRequestStat = __httpRequestStat;
        
        httpRequestStat.setExecuteLastStartTime(System.currentTimeMillis());
        if(ex != null)
        	httpRequestStat.error(ex);
        else
        	httpRequestStat.incrementExecuteSuccessCount();
        httpRequestStat.addExecuteTime(requestTime);
	}
	
	/**
	 * 组装所有请求参数为一个字符串。
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String getRequestParameter(HttpServletRequest request) {
		Enumeration enumeration = request.getParameterNames();
		List<String> list = new ArrayList<String>();
		while(enumeration.hasMoreElements()) {
			String key = (String)enumeration.nextElement();
			if(!BaseController.EXCEPTION_MESSAGE.equals(key) && !"REQUEST_MODE".equals(key)) {
				String value = request.getParameter(key);
				if("password".equals(key))
					list.add(key + "=******");
				else
					list.add(key + "=" + value);
			}
		}
		String parameter = StringUtils.join(list, ", ");
		return parameter;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getExcetionEessage() {
		return excetionEessage;
	}

	public void setExcetionEessage(String excetionEessage) {
		this.excetionEessage = excetionEessage;
	}

	public String[] getExcludeFilters() {
		return excludeFilters;
	}

	public void setExcludeFilters(String[] excludeFilters) {
		this.excludeFilters = excludeFilters;
	}
}

