//$Id$
/**
 * 工程名: 	usi-framework-core
 * 文件名: 	LogDao.java
 * 创建人:  	wang.xiaoyan
 * 创建时间: 	2013-3-14 上午10:51:41
 * 版权所有：	Copyright (c) 2013 苏州科大恒星信息技术有限公司  
 * -----------------------------变更记录 ----------------------------- 
 * 日期        		变更人      		版本号  		变更描述  
 * ------------------------------------------------------------------  
 * 2013-3-14     		wang.xiaoyan   			1.0.0       	first created  
 */
package cn.edu.ahpu.tpc.framework.web.dao.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.edu.ahpu.tpc.framework.web.model.admin.LoginLog;

import cn.edu.ahpu.common.dao.hibernate4.HibernateBaseDaoImpl;

/**
 * @since usi-framework-core 1.0.0
 * @version 1.0.0 2013-3-14
 * @author wang.xiaoyan
 */
@Repository
public class LoginLogDao extends HibernateBaseDaoImpl<LoginLog, Long> {

	@Autowired
	private JdbcTemplate jdbcTemplete;

	public List<Map<String, Object>> queryLoginLogByUserCode(String userCode) {
		String sql ="";
		List<Map<String, Object>> rs = new ArrayList<Map<String, Object>>();
		if(StringUtils.hasText(userCode)){
			sql = "SELECT ORG_NAME AS orgName, " +
					"USER_CODE AS userCode," +
					"CLIENT_TYPE AS clientType," +
					"IP AS ip ,LOGIN_DATE AS loginDate," +
					"LOGOUT_DATE AS logoutDate," +
					"LOGIN_STATUS AS loginStatus " +
					"FROM TPC_LOGIN_LOG WHERE USER_CODE ='?'";
			rs = jdbcTemplete.queryForList(sql, userCode);
		}else{
			sql = "SELECT ORG_NAME AS orgName, " +
					"USER_CODE AS userCode," +
					"CLIENT_TYPE AS clientType," +
					"IP AS ip ,LOGIN_DATE AS loginDate," +
					"LOGOUT_DATE AS logoutDate," +
					"LOGIN_STATUS AS loginStatus " +
					"FROM TPC_LOGIN_LOG";
			rs = jdbcTemplete.queryForList(sql);
		}
		return rs;	
	}
	
	
	public LoginLog queryLoginLogBySessionId(String sessionId){
		String hql = "from LoginLog where sessionId = ?";
		List<LoginLog> list = this.findByHQL(hql, sessionId);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
}
