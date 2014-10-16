/**        
 * Copyright (c) 2013 by 苏州科大国创信息技术有限公司.    
 */    
package cn.edu.ahpu.common.dao.jdbc.support;

import cn.edu.ahpu.common.dao.jdbc.SqlBuilder;

/**
 * Create on @2013-7-19 @下午2:57:06 
 * @author bsli@ustcinfo.com
 */
public class NotSupportSqlBuilder implements SqlBuilder {

	@Override
	public String countSql(String sql) {
		throw new RuntimeException("SQL分之只支持mysql和oracle");
	}

	@Override
	public String limitSql(String sql, int offset, int count) {
		throw new RuntimeException("SQL分之只支持mysql和oracle");
	}

}
