package cn.edu.ahpu.common.dao.jdbc;

/**
 * @author bsli@ustcinfo.com
 * @date 2013-2-3 下午2:20:46
 */
public interface SqlBuilder {
	
	/**
	 * 创建count统计sql语句
	 * 
	 * @param sql
	 * @return
	 */
	public String countSql(String sql);
	
	/**
	 * 创建limit sql语句
	 * 
	 * @param sql
	 * @param offset
	 * @param count
	 * @return
	 */
	public String limitSql(String sql, int offset, int count);
}
