//$Id$
/**
* 工程名: 	usi-framework-core
* 文件名: 	LogService.java
* 创建人:  	wang.xiaoyan
* 创建时间: 	2013-3-14 上午9:33:38
* 版权所有：	Copyright (c) 2013 苏州科大恒星信息技术有限公司  
* -----------------------------变更记录 ----------------------------- 
* 日期        		变更人      		版本号  		变更描述  
* ------------------------------------------------------------------  
* 2013-3-14     		wang.xiaoyan   			1.0.0       	first created  
*/
package cn.edu.ahpu.tpc.framework.web.service.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.ahpu.tpc.framework.core.util.SecurityContextUtil;
import cn.edu.ahpu.tpc.framework.web.dao.admin.LoginLogDao;
import cn.edu.ahpu.tpc.framework.web.model.admin.LoginLog;

import cn.edu.ahpu.common.dao.hibernate4.HibernateBaseDao;
import cn.edu.ahpu.common.dao.service.BaseServiceImpl;

/**
 * @since usi-framework-core 1.0.0
 * @version 1.0.0 2013-3-14
 * @author wang.xiaoyan
 */
@Service
public class LoginLogService extends BaseServiceImpl<LoginLog, Long>{
	private static final Logger _logger = LoggerFactory.getLogger(LoginLogService.class);

	@Autowired
	private LoginLogDao loginLogDao;
	/**
	* @return
	* @see cn.edu.ahpu.common.dao.service.BaseServiceImpl#getHibernateBaseDao()
	*/
	@Override
	public HibernateBaseDao<LoginLog, Long> getHibernateBaseDao() {
		return loginLogDao;
	}
	
	@Transactional(readOnly=true)
    public List<Map<String, Object>> queryLoginLogByUserCode(String usercode){
    	return  loginLogDao.queryLoginLogByUserCode(usercode);
    }
	
	/**
	 * 保存登录日志, 只接受POST请求
	 * 
	 * @param loginLog
	 * @return ResponseData
	 */
	@Transactional
	public void insertLoginLog(String userCode,String sessionId,String ip,String clientType,String loginStatus) {
		LoginLog loginLog = new LoginLog();
		String orgName = "";
		if(loginStatus=="SUCCESS"){
			orgName = SecurityContextUtil.getCurrentUser().getOrgName();
		}
		loginLog.setSessionId(sessionId);
		loginLog.setOrgName(orgName);
		loginLog.setUserCode(userCode);
		loginLog.setClientType(clientType);
		loginLog.setIp(ip);
		loginLog.setLoginDate(new Date());
		loginLog.setLoginStatus(loginStatus);
		
		loginLogDao.save(loginLog);
	}
	
	public LoginLog queryLoginLogBySessionId(String sessionId){
		return loginLogDao.queryLoginLogBySessionId(sessionId);
	}
}
