package cn.edu.ahpu.tpc.framework.web.service.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.ahpu.tpc.framework.core.util.ResponseData;
import cn.edu.ahpu.tpc.framework.core.util.SecurityContextUtil;
import cn.edu.ahpu.tpc.framework.web.dao.admin.RoleDao;
import cn.edu.ahpu.tpc.framework.web.dao.admin.UserDao;
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
public class UserService extends BaseServiceImpl<User, Long> {
	//~ Instance fields ================================================================================================
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;

	//~ Constructors ===================================================================================================
	
	//~ Methods ========================================================================================================
	@Override
	public HibernateBaseDao<User, Long> getHibernateBaseDao() {
		return this.userDao;
	}
	
	/**
	 * 批量删除用户数据
	 * 
	 * @param userIds
	 */
	@Transactional
	public void deleteUsers(Long[] userIds) {
		for(Long id : userIds) 
			this.userDao.deleteUser(id);
	}
	
	/**
	 * 查询用户关联的所有角色, 只接受POST请求
	 * 
	 * @param id 用户ID
	 * @return List<Role> 资源关联的所有角色
	 */
	public Set<GrantedAuthority> queryRoles4User(Long id) {
		User user = userDao.get(id);
		return user.getAuthorities();
	}
	
	/**
	 * 查询所有角色信息，同时关联查询指定的用户信息
	 *
	 * @param pageRequest
	 * @param id
	 * @return Page<Role>
	 */
	public Pagination<Role> pageQueryRoles4User(PaginationRequest<Role> paginationRequest, Long id) {
		Pagination<Role> page = roleDao.queryRoles(paginationRequest);
		List<Role> list = page.getResult();
		List<Role> newList = new ArrayList<Role>();
		List<Long> ids = new ArrayList<Long>();
		for(Role role : list) {
			if(!ids.contains(role.getId())) {
				ids.add(role.getId());
				for(User user : role.getUsers()) {
					if(user.getId().longValue() == id.longValue()) {
						role.setCounter(1L);
						role.getResources().clear();
						break;
					}
				}
				newList.add(role);
			}
		}
 		
		page.setResult(newList);
		return page;
	}
	
	/**
	 * 给资源绑定角色
	 * 
	 * @param id
	 * @param ids 资源ID，多个值用逗号分隔
	 * @return ResponseData
	 */
	@Transactional
	public void bindRole(Long id, Long[] ids) {
		List<Role> list = roleDao.queryRolesByIds(ids);
		User user = this.userDao.get(id);
		user.getAuthorities().addAll(list);
		this.userDao.update(user);
	}
	
	/**
	 * 给资源取消绑定角色
	 * 
	 * @param id
	 * @param ids 资源ID，多个值用逗号分隔R
	 * @return ResponseData
	 */
	@Transactional
	public void unBindRole(Long id, Long[] ids) {
		List<Role> list = roleDao.queryRolesByIds(ids);
		User user = this.userDao.get(id);
		user.getAuthorities().removeAll(list);
		this.userDao.update(user);
	}
	
	/**
	 * 记录用户登录成功信息
	 * 
	 * @param userCode
	 * @param ip
	 */
	@Transactional
	public void recordLoginSuccess(String userCode, String ip) {
		List<User> list = userDao.findByNamedParam("userCode", userCode);
		if(list.size() > 0) {
			User user = list.get(0);
			user.setLastLoginTime(new Date());
			userDao.update(user);
		}
	}
	
	/**
	 * 重置用户密码
	 * @param userId
	 * @return
	 */
	@Transactional
	public void resetPwd(Long userId) {
		User user = userDao.get(userId);
		//初始密码：000000
		String salt = SecurityContextUtil.generateRandomSalt();
		user.setSalt(salt);
		user.setPassword(SecurityContextUtil.encodePassword("000000", salt));
		userDao.update(user);
	}
}
