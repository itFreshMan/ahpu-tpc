package cn.edu.ahpu.tpc.framework.web.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.ahpu.tpc.framework.core.util.SecurityContextUtil;
import cn.edu.ahpu.tpc.framework.web.dao.admin.MenuDao;
import cn.edu.ahpu.tpc.framework.web.model.admin.Menu;

import cn.edu.ahpu.common.dao.hibernate4.HibernateBaseDao;
import cn.edu.ahpu.common.dao.service.BaseServiceImpl;

/**
 *
 * @datetime 2010-8-8 下午04:44:42
 * @author libinsong1204@gmail.com
 */
@Service
public class MenuService extends BaseServiceImpl<Menu, Long> {
	//~ Instance fields ================================================================================================
	@Autowired
	private MenuDao menuDao;

	//~ Methods ========================================================================================================
	@Override
	public HibernateBaseDao<Menu, Long> getHibernateBaseDao() {
		return this.menuDao;
	}
	
	/**
	 * 保存指定节点下，所有直接子节点的顺序。
	 * 
	 * @param parentId 父节点
	 * @param childIds 所有子节点ID
	 */
	@Transactional
	public void saveMenuOrder(Long parentId, Long[] childIds) {
		int index=1;
		for(Long id :  childIds) {
			Menu menu = menuDao.get(id);
			menu.setParentId(parentId);
			menu.setTheSort(index++);
		}
	}
	
	/**
	 * 查询用户拥有权限查看的菜单。用户与角色关联，角色与资源关联，资源与菜单关联
	 * 
	 * @param parentId 父菜单ID
	 */
	public List<Menu> queryMenus4User(Long parentId) {
		Long userId = SecurityContextUtil.getCurrentUser().getId();
		return menuDao.queryMenus4User(userId, parentId);
	}
}
