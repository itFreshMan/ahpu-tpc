package cn.edu.ahpu.tpc.framework.web.service.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.ahpu.tpc.framework.core.cache.CacheNames;
import cn.edu.ahpu.tpc.framework.web.dao.admin.ResourceDao;
import cn.edu.ahpu.tpc.framework.web.dao.admin.RoleDao;
import cn.edu.ahpu.tpc.framework.web.dao.admin.UserDao;
import cn.edu.ahpu.tpc.framework.web.model.admin.Resource;
import cn.edu.ahpu.tpc.framework.web.model.admin.Role;
import cn.edu.ahpu.tpc.framework.web.model.admin.User;

import cn.edu.ahpu.common.dao.hibernate4.HibernateBaseDao;
import cn.edu.ahpu.common.dao.service.BaseServiceImpl;
import cn.edu.ahpu.common.dao.support.Pagination;
import cn.edu.ahpu.common.dao.support.PaginationRequest;

/**
 * 
 * @datetime 2010-8-8 下午04:44:42
 * @author libinsong1204@gmail.com
 */
@Service
public class RoleService extends BaseServiceImpl<Role, Long> {
	// ~ Instance fields
	// ================================================================================================
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// ~ Methods
	// ========================================================================================================
	@Override
	public HibernateBaseDao<Role, Long> getHibernateBaseDao() {
		return this.roleDao;
	}

	/**
	 * 批量删除用户数据
	 * 
	 * @param resIds
	 */
	@Transactional
	public void deleteRoles(Long[] roleIds) {
		String usersql = "SELECT user_id FROM TPC_user_role WHERE role_id=?";
		String resourcesql = "SELECT RESOURCE_ID FROM TPC_role_resource WHERE role_id=?";
		List<Map<String, Object>> list;
		List<Long> idList = new ArrayList<Long>();
		for (Long id : roleIds) {
			// delete related user_role
			list = jdbcTemplate.queryForList(usersql, id);
			for (Map<String, Object> map : list) {
				idList.add(Long.parseLong(map.get("USER_ID").toString()));
			}
			if (idList.size() != 0)
				this.unBindUser(id, (Long[]) idList.toArray(new Long[] {}));
			list = null;
			idList.clear();

			// delete related resource_role
			list = jdbcTemplate.queryForList(resourcesql, id);
			for (Map<String, Object> map : list) {
				idList.add(Long.parseLong(map.get("RESOURCE_ID").toString()));
			}
			if (idList.size() != 0)
				this.unBindResource(id, (Long[]) idList.toArray(new Long[] {}));

			roleDao.delete(id);
		}
	}

	/**
	 * 查询所有资源信息，同时关联查询指定的角色信息
	 * 
	 * @param pageRequest
	 * @param roleId
	 */
	public Pagination<Resource> queryResources4Role(
			PaginationRequest<Resource> paginationRequest, Long roleId) {
		Pagination<Resource> page = this.roleDao
				.queryResources4Role(paginationRequest);
		List<Resource> list = page.getResult();
		List<Resource> newList = new ArrayList<Resource>();
		List<Long> ids = new ArrayList<Long>();
		for (Resource resource : list) {
			if (!ids.contains(resource.getId())) {
				ids.add(resource.getId());
				for (Role role : resource.getRoles()) {
					if (role.getId().longValue() == roleId.longValue()) {
						resource.setCounter(1L);
						resource.getRoles().clear();
						break;
					}
				}
				newList.add(resource);
			}
		}

		page.setResult(newList);
		return page;
	}

	/**
	 * 查询所有用户信息，同时关联查询指定的角色信息
	 * 
	 * @param pageRequest
	 * @param roleId
	 */
	public Pagination<User> queryUsers4Role(
			PaginationRequest<User> paginationRequest, Role role) {
		Pagination<User> page = this.roleDao.queryUsers4Role(paginationRequest);
		List<User> list = page.getResult();
		List<User> newList = new ArrayList<User>();
		List<Long> ids = new ArrayList<Long>();
		for (User user : list) {
			if (!ids.contains(user.getId())) {
				ids.add(user.getId());
				for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
					if (role.getCode().equals(grantedAuthority.getAuthority())) {
						user.setCounter(1L);
						break;
					}
				}
				newList.add(user);
			}
		}
		page.setResult(newList);
		return page;
	}

	/**
	 * 查询所有用户信息，同时关联查询指定的角色信息(查询条件包括含了是否授权）
	 * 
	 * @param pageRequest
	 * @param roleId
	 * @param counter
	 */
	public Pagination<Map<String, Object>> queryUsers4Role(User user,
			Long roleId, int offset, int limit) {

		List<Map<String, Object>> list = roleDao.queryUsers4RoleWithAuth(user,
				roleId);

		// 构建pagination
		long totalRecords = list.size();
		long totalPages = (long) Math.ceil(totalRecords * 1d / limit);
		if (totalPages == 0)
			totalPages = 1;
		int last = offset + limit;
		if (last > totalRecords)
			last = (int) totalRecords;
		List<Map<String, Object>> items = list.subList(offset, last);
		Pagination<Map<String, Object>> page = new Pagination(totalPages,
				offset, limit, totalRecords, items);
		return page;

	}

	/**
	 * 给资源绑定角色
	 * 
	 * @param roleId
	 *            角色ID
	 * @param ids
	 *            资源ID
	 * @return ResponseData
	 */
	@Transactional
	public void bindResource(Long roleId, Long[] ids) {
		List<Resource> list = resourceDao.queryResourcesByIds(ids);
		Role role = this.roleDao.get(roleId);
		role.getResources().addAll(list);
		this.roleDao.update(role);

		Cache cache = cacheManager.getCache(CacheNames.RESOURCE_CACHE_NAME);
		cache.evict(ResourceService.FILTER_INVO_SEC_META_KEY);
	}

	/**
	 * 给资源取消绑定角色
	 * 
	 * @param roleId
	 *            角色ID
	 * @param ids
	 *            资源ID，多个值用逗号分隔
	 * @return ResponseData
	 */
	@Transactional
	public void unBindResource(Long roleId, Long[] ids) {
		List<Resource> list = resourceDao.queryResourcesByIds(ids);
		Role role = this.roleDao.get(roleId);
		role.getResources().removeAll(list);
		this.roleDao.update(role);

		Cache cache = cacheManager.getCache(CacheNames.RESOURCE_CACHE_NAME);
		cache.evict(ResourceService.FILTER_INVO_SEC_META_KEY);
	}

	/**
	 * 给用户绑定角色
	 * 
	 * @param roleId
	 *            角色ID
	 * @param ids
	 *            用户ID
	 * @return ResponseData
	 */
	@Transactional
	public void bindUser(Long roleId, Long[] ids) {
		List<User> list = userDao.queryUsersByIds(ids);
		Role role = this.roleDao.get(roleId);
		for (User user : list) {
			user.getAuthorities().add(role);
			this.userDao.update(user);
		}
	}

	/**
	 * 给用户取消绑定角色
	 * 
	 * @param roleId
	 *            角色ID
	 * @param ids
	 *            用户ID，多个值用逗号分隔
	 * @return ResponseData
	 */
	@Transactional
	public void unBindUser(Long roleId, Long[] ids) {
		List<User> list = userDao.queryUsersByIds(ids);
		Role role = this.roleDao.get(roleId);
		for (User user : list) {
			user.getAuthorities().remove(role);
			this.userDao.update(user);
		}
	}
}
