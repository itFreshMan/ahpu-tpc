package cn.edu.ahpu.common.dao.jdbc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import cn.edu.ahpu.common.dao.support.Pagination;

/**
 * @author bsli@ustcinfo.com
 * @date 2013-1-29 下午2:07:39
 */
public class NamedParameterJdbcPager {
	
	private SqlBuilder builder;
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public NamedParameterJdbcPager(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public Pagination<Map<String, Object>> queryPage(String sql, 
			int offset, int limit) {
		return this.queryPage(sql, offset, limit, null);
	}

	public Pagination<Map<String, Object>> queryPage(String sql, 
			int offset, int limit, Map<String, Object> paramMap) {
		String countSql = builder.countSql(sql);
		String limitSql = builder.limitSql(sql, offset, limit);
		
		long totalRecords = jdbcTemplate.queryForObject(countSql, paramMap, Long.class);
		List<Map<String, Object>> items = jdbcTemplate.queryForList(limitSql, paramMap);
		
		double totalPages = Math.ceil(totalRecords * 1d / limit);
		Pagination<Map<String, Object>> page = new Pagination<Map<String, Object>>((long)totalPages, offset, limit, totalRecords, items);
		return page;
	}

	/**
	 * 针对DB2的SQL分页方法,在DRUID未支持DB2分页前暂时使用此方法
	 */
	public Pagination<Map<String, Object>> queryPageDb2(String sql, 
			int offset, int limit, Map<String, Object> paramMap) {	
		String countSql = builder.countSql(sql);
		String limitSql = builder.limitSql(sql, offset, limit);
		//原先DRUID自带的分页工具带ORDER BY有BUG，临时按下面方式处理下
		if(limitSql.indexOf("FETCH FIRST ") != -1 && limitSql.indexOf("ORDER BY") != -1) {
			int startK = limitSql.indexOf("FETCH FIRST ")+12;
			int endK = limitSql.indexOf(" ROWS ONLY");
			String keyNum = limitSql.substring(startK, endK);
			String keyStr = "FETCH FIRST " + keyNum + " ROWS ONLY";
			limitSql = limitSql.split(keyStr)[0] + limitSql.split(keyStr)[1] + " " + keyStr;			
		}
		long totalRecords = jdbcTemplate.queryForObject(countSql, paramMap, Long.class);
		List<Map<String, Object>> items = jdbcTemplate.queryForList(limitSql, paramMap);
		
		double totalPages = Math.ceil(totalRecords * 1d / limit);
		Pagination<Map<String, Object>> page = new Pagination<Map<String, Object>>((long)totalPages, offset, limit, totalRecords, items);
		return page;
	}
	
	public SqlBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(SqlBuilder builder) {
		this.builder = builder;
	}
	
}
