/**        
 * Copyright (c) 2013 by 苏州科大国创信息技术有限公司.    
 */    
package cn.edu.ahpu.tpc.framework.core.support;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;

import ch.qos.logback.classic.db.SQLBuilder;

import cn.edu.ahpu.common.dao.jdbc.SqlBuilder;
import cn.edu.ahpu.common.dao.jdbc.support.Db2SqlBuilder;
import cn.edu.ahpu.common.dao.jdbc.support.MysqlSqlBuilder;
import cn.edu.ahpu.common.dao.jdbc.support.NotSupportSqlBuilder;
import cn.edu.ahpu.common.dao.jdbc.support.OracleSqlBuilder;

/**
 * Create on @2013-7-19 @下午2:51:11 
 * @author bsli@ustcinfo.com
 */
public class SqlBuilderFactoryBean implements FactoryBean<SqlBuilder> {
	
	@Value("${jdbc.url}")
	private String jdbcUrl;

	@Override
	public SqlBuilder getObject() throws Exception {
		if(jdbcUrl.trim().startsWith("jdbc:mysql:"))
			return new MysqlSqlBuilder();
		else if(jdbcUrl.trim().startsWith("jdbc:oracle:"))
			return new OracleSqlBuilder();
		else if(jdbcUrl.trim().startsWith("jdbc:db2:"))
			return new Db2SqlBuilder();
		else
			return new NotSupportSqlBuilder();
	}

	@Override
	public Class<SQLBuilder> getObjectType() {
		return SQLBuilder.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
