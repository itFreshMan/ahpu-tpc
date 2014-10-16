package cn.edu.ahpu.tpc.framework.web.service.admin;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.ahpu.tpc.framework.web.dao.admin.BusiLogDao;

import cn.edu.ahpu.common.dao.support.Pagination;

/**
 * @author zhuzengpeng
 * @date 2013-8-9 下午2:32:27
 */
@Service
public class BusiLogService {

	@Autowired
	private BusiLogDao busiLogDao;
	
	public Pagination<Object> getBusiLogList(Integer start, Integer limit, String optType, String optUser, String startDate, String endDate) throws ParseException {
		return busiLogDao.getBusiLogList(start, limit, optType, optUser, startDate, endDate);
	}
}
