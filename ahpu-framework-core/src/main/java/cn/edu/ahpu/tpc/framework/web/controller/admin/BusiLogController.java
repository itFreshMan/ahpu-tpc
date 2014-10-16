package cn.edu.ahpu.tpc.framework.web.controller.admin;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.ahpu.tpc.framework.web.controller.BaseController;
import cn.edu.ahpu.tpc.framework.web.service.admin.BusiLogService;

import cn.edu.ahpu.common.dao.support.Pagination;

/**
 * @author zhuzengpeng
 * @date 2013-8-9 下午2:32:04
 */
@Controller
@RequestMapping(value = "/busiLog")
public class BusiLogController extends BaseController{

	@Autowired
	private BusiLogService busiLogService;
	
	@RequestMapping(value = "/index")
	public String openPage() {	
		return "/admin/busiLog";
	}
	
	@RequestMapping(value = "/getBusiLogList", method = RequestMethod.POST)
	@ResponseBody
	public Pagination<Object> getBusiLogList(Integer start, Integer limit, String optType, String optUser, String startDate, String endDate) throws ParseException {
		return busiLogService.getBusiLogList(start, limit, optType, optUser, startDate, endDate);
	}
}
