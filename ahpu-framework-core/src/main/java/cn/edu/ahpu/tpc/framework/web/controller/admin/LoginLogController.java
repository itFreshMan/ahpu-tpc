//$Id$
/**
 * 工程名: 	usi-framework-core
 * 文件名: 	LogController.java
 * 创建人:  	wang.xiaoyan
 * 创建时间: 	2013-3-14 上午9:29:24
 * 版权所有：	Copyright (c) 2013 苏州科大恒星信息技术有限公司  
 * -----------------------------变更记录 ----------------------------- 
 * 日期        		变更人      		版本号  		变更描述  
 * ------------------------------------------------------------------  
 * 2013-3-14     		wang.xiaoyan   			1.0.0       	first created  
 */
package cn.edu.ahpu.tpc.framework.web.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.ahpu.tpc.framework.web.controller.BaseController;
import cn.edu.ahpu.tpc.framework.web.model.admin.LoginLog;
import cn.edu.ahpu.tpc.framework.web.service.admin.LoginLogService;

import cn.edu.ahpu.common.dao.support.Pagination;
import cn.edu.ahpu.common.dao.support.PaginationRequest;

/**
 * @since usi-framework-core 1.0.0
 * @version 1.0.0 2013-3-14
 * @author wang.xiaoyan
 */
@Controller
@RequestMapping("/loginLog")
public class LoginLogController extends BaseController {

	@Autowired
	private LoginLogService loginLogService;

	@RequestMapping("/index")
	public String index() {
		return "admin/loginLog";
	}

	/**
	 * 查询日志信息
	 */
	@RequestMapping("/pageQueryLogs")
	@ResponseBody
	public Pagination<LoginLog> pageQueryLogs(@RequestParam("start") int offset,
			@RequestParam("limit") int limit, String userCode,
			@RequestParam(required = false) String sort,
			@RequestParam(required = false) String dir) {
		PaginationRequest<LoginLog> paginationRequest = new PaginationRequest<LoginLog>(
				offset / limit, offset, limit);
		paginationRequest.addOrder("loginDate", "desc");
//		if (StringUtils.hasText(sort) && StringUtils.hasText(dir))
//			paginationRequest.addOrder(sort, dir);
		if (StringUtils.hasText(userCode))
			paginationRequest
					.addLikeCondition("userCode", "%" + userCode + "%");

		Pagination<LoginLog> page = loginLogService.findPage(paginationRequest);
		return page;
	}

	@RequestMapping("/export.xls")
	public void exportLogs(@RequestParam("userCode") String userCode,
			ModelMap map) {
		List<Map<String, Object>> loglist = loginLogService
				.queryLoginLogByUserCode(userCode);
		String exportFileName = "日志记录.xls";
		String templateFileName = "loginLog.xls";
		map.addAttribute("loglist", loglist);
		map.put("ExcelExportFileName", exportFileName);
		map.put("ExcelTemplateFileName", templateFileName);
	}
}
