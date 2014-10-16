package cn.edu.ahpu.tpc.framework.web.dao.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.edu.ahpu.tpc.framework.web.model.admin.Resource;
import cn.edu.ahpu.tpc.framework.web.model.admin.Role;
import cn.edu.ahpu.tpc.framework.web.model.admin.User;

import cn.edu.ahpu.common.dao.hibernate4.HibernateBaseDaoImpl;
import cn.edu.ahpu.common.dao.hibernate4.HibernateCallback;
import cn.edu.ahpu.common.dao.support.Pagination;
import cn.edu.ahpu.common.dao.support.PaginationRequest;

/**
 *
 * @datetime 2010-8-8 下午04:42:05
 * @author libinsong1204@gmail.com
 */
/**
 * @author Administrator
 * 
 */
@Repository
public class RoleDao extends HibernateBaseDaoImpl<Role, Long> {
	// ~ Instance fields
	// ================================================================================================
	@Autowired
	private ResourceDao resourceDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// ~ Methods
	// ========================================================================================================
	/**
	 * 查询所有资源信息，同时关联查询指定的角色信息
	 * 
	 * @param pageRequest
	 * @return Page<Resource>
	 */
	public Pagination<Resource> queryResources4Role(
			PaginationRequest<Resource> paginationRequest) {
		paginationRequest.addCondition("enabled", "Y");
		paginationRequest.addOrder("priority", true);
		return resourceDao.findPage(paginationRequest);
	}

	/**
	 * 查询所有用户信息，同时关联查询指定的角色信息
	 * 
	 * @param pageRequest
	 * @return Page<Resource>
	 */
	public Pagination<User> queryUsers4Role(
			PaginationRequest<User> paginationRequest) {
		return userDao.findPage(paginationRequest);
	}

	/**
	 * 通过多个角色ID，查询出相应的角色
	 * 
	 * @param idList
	 *            多个角色ID。通过List类型参数传入
	 * @return List<Role>
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<Role> queryRolesByIds(final Long[] idList) {
		return doExecute(new HibernateCallback<List<Role>>() {
			public List<Role> doInHibernate(Session session)
					throws HibernateException {
				Query query = session
						.createQuery("from Role where id in(:idList)");
				query.setParameterList("idList", idList);
				return query.list();
			}
		});
	}

	/**
	 * 分页查询所有有效角色信息
	 * 
	 * @param pageRequest
	 * @return Page<Role>
	 */
	public Pagination<Role> queryRoles(PaginationRequest<Role> paginationRequest) {
		paginationRequest.addCondition("enabled", "Y");
		return this.findPage(paginationRequest);
	}

	/**
	 * 
	 * 查询所有用户信息，同时关联指定角色是否授权信息
	 * 
	 * @param pageRequest
	 * @return Page<Object>
	 */
	public List<Map<String, Object>> queryUsers4RoleWithAuth(User user,
			Long roleId) {

		StringBuilder sql = new StringBuilder(
				"SELECT * FROM (SELECT user1.ID id,user1.USER_CODE userCode,user1.USER_NAME userName,user1.GENDER gender,sub.ROLE_ID roleId, (case when sub.ROLE_ID is not null then 1 else 0 end) as counter, org.ID orgId,org.NAME orgName, user1.del_flag as del_flag FROM TPC_user user1 LEFT JOIN (SELECT * FROM TPC_user_role role WHERE role.ROLE_ID =?) sub ON user1.ID=sub.USER_ID left join TPC_organization org on org.ID = user1.ORG_ID) temp");     
		ArrayList<Object> values = new ArrayList<Object>();
		values.add(roleId);
		sql.append(" where del_flag = '0'");
		if (StringUtils.hasText(user.getUserName())) {
			sql.append(" and userName like '%").append(user.getUserName()).append("%'");
		}

		if (StringUtils.hasText(user.getUserCode())) {
			sql.append(" and userCode like '%").append(user.getUserCode()).append("%'");
		}
		if (user.getOrgId() != null) {
			sql.append(" and orgId = ?");
			values.add(user.getOrgId());
		}
		if (user.getCounter() != null && user.getCounter() != 2L) {
			sql.append(" and counter = ?");
			values.add(user.getCounter());
		}
		return jdbcTemplate.queryForList(sql.toString(), values.toArray());
	}

}
