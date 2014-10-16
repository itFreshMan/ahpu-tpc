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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.ahpu.tpc.framework.web.dao.admin.SchemaVersionDao;
import cn.edu.ahpu.tpc.framework.web.model.admin.SchemaVersion;

import cn.edu.ahpu.common.dao.hibernate4.HibernateBaseDao;
import cn.edu.ahpu.common.dao.service.BaseServiceImpl;

/**
 * @since usi-framework-core 1.0.0
 * @version 1.0.0 2013-3-14
 * @author wang.xiaoyan
 */
@Service
public class SchemaVersionService extends BaseServiceImpl<SchemaVersion, Long>{
	private static final Logger _logger = LoggerFactory.getLogger(SchemaVersionService.class);

	@Autowired
	private SchemaVersionDao schemaVersionDao;
	/**
	* @return
	* @see cn.edu.ahpu.common.dao.service.BaseServiceImpl#getHibernateBaseDao()
	*/
	@Override
	public HibernateBaseDao<SchemaVersion, Long> getHibernateBaseDao() {
		return schemaVersionDao;
	}
	
	
}
