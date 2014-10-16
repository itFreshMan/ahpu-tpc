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

import org.springframework.stereotype.Repository;

import cn.edu.ahpu.tpc.framework.web.model.admin.SchemaVersion;

import cn.edu.ahpu.common.dao.hibernate4.HibernateBaseDaoImpl;

/**
 * @since usi-framework-core 1.0.0
 * @version 1.0.0 2013-3-14
 * @author wang.xiaoyan
 */
@Repository
public class SchemaVersionDao extends HibernateBaseDaoImpl<SchemaVersion, Long> {

}
