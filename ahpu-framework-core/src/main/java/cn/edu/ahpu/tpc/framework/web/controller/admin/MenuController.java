package cn.edu.ahpu.tpc.framework.web.controller.admin;

import java.util.List;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.ahpu.tpc.framework.core.util.ResponseData;
import cn.edu.ahpu.tpc.framework.web.controller.BaseController;
import cn.edu.ahpu.tpc.framework.web.model.admin.Menu;
import cn.edu.ahpu.tpc.framework.web.service.admin.MenuService;

/**
 * 菜单Controller
 *
 * @datetime 2010-8-8 下午04:47:03
 * @author libinsong1204@gmail.com
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {
	//~ Instance fields ================================================================================================
	@Autowired
	private MenuService menuService;
	
	//~ Constructors ===================================================================================================
	
	//~ Methods ========================================================================================================
	@RequestMapping("/index")
	public String index(){
		return "admin/menu";
	}
	
	/**
	 * 根据父菜单ID和用户权限，查找所有子菜单信息
	 * 
	 * @param node 父菜单ID
	 * @return 所有子菜单
	 */
	@RequestMapping("/queryMenus4User")
	@ResponseBody
	public List<Menu> queryMenus4User(@RequestParam(required = false)Long node) {
		if(node == null)
			node = 0L;
		return menuService.queryMenus4User(node);
	}
	
	/**
	 * 根据父菜单ID，查找所有子菜单信息
	 * 
	 * @param node 父菜单ID
	 * @return 所有子菜单
	 */
	@RequestMapping("/queryMenus")
	@ResponseBody
	public List<Menu> queryMenus(Long node) {
		if(node == null)
			node = 0L;
		return menuService.findByNamedParamAndOrder("resource", "parentId", node, Order.asc("theSort")); 
	}
	
	/**
	 * 保存指定节点下，所有直接子节点的顺序。
	 * 
	 * @param parentId 父节点
	 * @param childIds 所有子节点ID
	 * @return
	 */
	@RequestMapping("/saveMenuOrder")
	@ResponseBody
	public ResponseData saveMenuOrder(Long parentId, Long[] childIds) {
		menuService.saveMenuOrder(parentId, childIds);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 插入菜单信息
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping(value="/insertMenu", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData insertMenu(Menu menu) {
		if(menu.getResource().getId() == null)
			menu.setResource(null);
		menuService.insertEntity(menu);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 更新菜单信息
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping(value="/updateMenu", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData updateMenu(Menu menu) {
		if(menu.getResource().getId() == null)
			menu.setResource(null);
		menuService.updateEntity(menu);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 加载菜单信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/loadMenu", method=RequestMethod.POST)
	@ResponseBody
	public Menu loadMenu(Long id) {
		return menuService.getEntity(id);
	}
	
	/**
	 * 删除菜单
	 * 
	 * @param parentId 父节点
	 * @param childIds 所有子节点ID有逗号连接的字符串
	 * @return
	 */
	@RequestMapping("/deleteMenu")
	@ResponseBody
	public ResponseData deleteMenu(long id) {
		menuService.deleteEntity(id);
		return ResponseData.SUCCESS_NO_DATA;
	}
}
