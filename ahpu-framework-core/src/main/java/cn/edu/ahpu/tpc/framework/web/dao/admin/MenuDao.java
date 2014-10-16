package cn.edu.ahpu.tpc.framework.web.dao.admin;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.edu.ahpu.tpc.framework.web.model.admin.Menu;

import cn.edu.ahpu.common.dao.hibernate4.HibernateBaseDaoImpl;

/**
 *
 * @datetime 2010-8-8 下午04:42:05
 * @author libinsong1204@gmail.com
 */
@Repository
public class MenuDao extends HibernateBaseDaoImpl<Menu, Long> {

	/**
	 * 查询用户拥有权限查看的菜单。用户与角色关联，角色与资源关联，资源与菜单关联
	 * 
	 * @param userId 用户ID，主键
	 * @param parentId 父菜单ID
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> queryMenus4User(Long userId, Long parentId) {
		String queryString = "select distinct menu from Menu menu left join fetch menu.resource res left join res.roles role " +
			"where exists (select r.id from Role r left join r.users u where role.id=r.id and menu.parentId = ? and u.id= ?) " +
			"order by menu.theSort";
		
		return this.findByHQL(queryString, parentId, userId);
	}
}
