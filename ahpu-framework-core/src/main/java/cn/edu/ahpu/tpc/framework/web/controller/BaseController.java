package cn.edu.ahpu.tpc.framework.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import cn.edu.ahpu.tpc.framework.core.util.AjaxUtil;
import cn.edu.ahpu.tpc.framework.core.util.ResponseData;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @datetime 2010-8-9 下午01:17:20
 * @author libinsong1204@gmail.com
 */
abstract public class BaseController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	public final static String EXCEPTION_MESSAGE = "EXCEPTION_MESSAGE";
	
	protected final ObjectMapper mapper = new ObjectMapper();

	//~ Instance fields ================================================================================================
	
	//~ Methods ========================================================================================================
	@InitBinder
	public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
	
	@ExceptionHandler()
	public void handleException(Exception exception, HttpServletRequest request, HttpServletResponse response) {
		//服务端处理失败
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		request.setAttribute(EXCEPTION_MESSAGE, exception);
		
		logger.error(exception.getMessage(), exception);
		
		if(!AjaxUtil.isAjaxRequest(request)) {
			RequestDispatcher rd =  request.getSession().getServletContext().getRequestDispatcher("/WEB-INF/views/error.jsp"); 
            try {
				rd.forward(request, response);
			} catch (Exception e) {
				//
			}
		} else {
			ResponseData data = new ResponseData(false, exception.getClass() + ": " + exception.getMessage());
			data.setRequestURI(request.getRequestURI());
			data.setExecptionTrace(ExceptionUtils.getFullStackTrace(exception));
			
			try {
				String json = mapper.writeValueAsString(data);
				response.setContentType("application/json;charset=UTF-8");
				response.getOutputStream().print(json);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}
