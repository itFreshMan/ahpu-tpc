package cn.edu.ahpu.tpc.framework.web.dao.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.edu.ahpu.tpc.framework.web.model.admin.Organization;

import cn.edu.ahpu.common.dao.hibernate4.HibernateBaseDaoImpl;

/**
 *
 * @datetime 2010-8-17 下午09:52:20
 * @author libinsong1204@gmail.com
 */
@Repository
public class OrganizationDao extends HibernateBaseDaoImpl<Organization, Long> {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Map<String, Object>> queryOrgAndPersons(Long orgId) {
		String sql = "select a.orgId as id,name as text,2 as type,false as leaf from st_organization a where a.parentId=? "+
			"union "+
			"select b.operatorId as id, b.operatorName as text,1 as type,true as leaf from st_user b where b.orgId=?";
		return this.jdbcTemplate.queryForList(sql, orgId, orgId);
	}
}

