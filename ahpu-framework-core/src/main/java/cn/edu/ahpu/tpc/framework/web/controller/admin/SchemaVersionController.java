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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.ahpu.tpc.framework.web.controller.BaseController;
import cn.edu.ahpu.tpc.framework.web.model.admin.SchemaVersion;
import cn.edu.ahpu.tpc.framework.web.service.admin.SchemaVersionService;

import cn.edu.ahpu.common.dao.support.Pagination;
import cn.edu.ahpu.common.dao.support.PaginationRequest;

/**
 * @since usi-framework-core 1.0.0
 * @version 1.0.0 2013-3-14
 * @author wang.xiaoyan
 */
@Controller
@RequestMapping("/schemaVersion")
public class SchemaVersionController extends BaseController {

	@Autowired
	private SchemaVersionService schemaVersionService;

	@RequestMapping("/index")
	public String index() {
		return "admin/schemaVersion";
	}

	/**
	 * 查询数据库版本信息
	 */
	@RequestMapping("/pageQuerySchemaVersions")
	@ResponseBody
	public Pagination<SchemaVersion> pageQuerySchemaVersions(
			@RequestParam("start") int offset,
			@RequestParam("limit") int limit, String version,
			@RequestParam(required = false) String sort,
			@RequestParam(required = false) String dir) {
		PaginationRequest<SchemaVersion> paginationRequest = new PaginationRequest<SchemaVersion>(
				offset / limit, offset, limit);

		if (StringUtils.hasText(sort) && StringUtils.hasText(dir))
			paginationRequest.addOrder(sort, dir);

		if (StringUtils.hasText(version))
			paginationRequest.addLikeCondition("version", "%" + version + "%");
		Pagination<SchemaVersion> page = schemaVersionService
				.findPage(paginationRequest);
		return page;
	}
}
