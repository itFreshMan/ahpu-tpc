package cn.edu.ahpu.tpc.framework.web.controller.admin;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.ahpu.tpc.framework.core.util.DictionaryHolder;
import cn.edu.ahpu.tpc.framework.core.util.ResponseData;
import cn.edu.ahpu.tpc.framework.core.util.SecurityContextUtil;
import cn.edu.ahpu.tpc.framework.web.controller.BaseController;
import cn.edu.ahpu.tpc.framework.web.model.admin.Resource;
import cn.edu.ahpu.tpc.framework.web.model.admin.Role;
import cn.edu.ahpu.tpc.framework.web.model.admin.User;
import cn.edu.ahpu.tpc.framework.web.service.admin.RoleService;

import cn.edu.ahpu.common.dao.support.Pagination;
import cn.edu.ahpu.common.dao.support.PaginationRequest;

/**
 * 角色Controller
 *
 * @datetime 2010-8-8 下午04:47:03
 * @author libinsong1204@gmail.com
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
	//~ Instance fields ================================================================================================
	@Autowired
	private RoleService roleService;
	
	//~ Constructors ===================================================================================================
	
	//~ Methods ========================================================================================================
	@RequestMapping("/index")
	public String index(){
		return "admin/role";
	}
	
	@RequestMapping("/indexNew")
	public String indexNew(){
		return "admin/roleMgr";
	}
	
	/**
	 * 角色管理，查询角色信息
	 * 
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/pageQueryRoles")
	@ResponseBody
	public Pagination<Role> pageQueryRoles(@RequestParam("start")int offset, 
			@RequestParam("limit")int limit, Role role, @RequestParam(required = false)String sort, 
			@RequestParam(required = false)String dir) {
		PaginationRequest<Role> paginationRequest = new PaginationRequest<Role>(offset/limit, offset, limit);
		
		if(StringUtils.hasText(sort) && StringUtils.hasText(dir))
			paginationRequest.addOrder(sort, dir);
		
		if(StringUtils.hasText(role.getEnabled()))
			paginationRequest.addCondition("enabled", role.getEnabled());
		
		if(StringUtils.hasText(role.getName()))
			paginationRequest.addLikeCondition("name", "%" + role.getName() + "%");
			
		Pagination<Role> page = roleService.findPage(paginationRequest);
		DictionaryHolder.transfercoder(page.getResult(), "CORE.ENABLED", "getEnabled");
		return page;
	}
	
	/**
	*
	* 1.角色管理界面-用于检测添加角色及更新角色时,角色编码是否重复<br>
	* @param code
	* @param id 用于角色管理界面
	* @return
	*/
	@RequestMapping(value="/isExistRole", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData isExistRole(String code, @RequestParam(required=false)Long id) {
		List<Role> list = roleService.findByNamedParam("code",code);
		
		if(id == -1){//add user
			if(list.size() > 0)
				return ResponseData.SUCCESS_NO_DATA;//repeated code
			else
				return ResponseData.FAILED_NO_DATA;
		}else{//update user
			if(list.size() > 0 && list.get(0).getId().longValue() != id.longValue())
				return ResponseData.SUCCESS_NO_DATA;
			else if(list.size() > 1)
				return ResponseData.SUCCESS_NO_DATA;
			else
				return ResponseData.FAILED_NO_DATA;
		}
	}
	
	/**
	 * 保存角色, 只接受POST请求
	 * 
	 * @param resource
	 * @return ResponseData
	 */
	@RequestMapping(value="/insertRole", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData insertRole(Role role) {
		roleService.insertEntity(role);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 更新角色, 只接受POST请求
	 * 
	 * @param resource
	 * @return ResponseData
	 */
	@RequestMapping(value="/updateRole", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData updateRole(Role role) {
		Role entity = roleService.getEntity(role.getId());
		entity.setName(role.getName());
		entity.setCode(role.getCode());
		entity.setEnabled(role.getEnabled());
		entity.setDescn(role.getDescn());
		roleService.updateEntity(entity);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 加载角色, 只接受POST请求
	 * 
	 * @param resource
	 * @return ResponseData
	 */
	@RequestMapping(value="/loadRole", method=RequestMethod.POST)
	@ResponseBody
	public Role loadRole(Long id) {
		return roleService.getEntity(id);
	}
	
	/**
	 * 删除角色, 只接受POST请求
	 * 
	 * @param resource
	 * @return ResponseData
	 */
	@RequestMapping(value="/deleteRoles", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData deleteRoles(Long[] ids) {
		Set<GrantedAuthority> roles = SecurityContextUtil.getCurrentUser().getAuthorities();
		for(GrantedAuthority role : roles){
			for(Long id:ids){
				String code = roleService.getEntity(id).getCode();
				if(role.getAuthority().equals(code)) return ResponseData.FAILED_DEL_OWNROLE;
			}
		}
		
		roleService.deleteRoles(ids);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 查询所有资源信息，同时关联查询指定的角色信息
	 *
	 * @param roleId
	 */
	@RequestMapping(value="/queryResources4Role", method=RequestMethod.POST)
	@ResponseBody
	public Pagination<Resource> queryResources4Role(@RequestParam("start")int offset, 
			@RequestParam("limit")int limit, Long roleId)  {
		PaginationRequest<Resource> pageRequest = new PaginationRequest<Resource>(offset/limit, offset, limit);
		return roleService.queryResources4Role(pageRequest, roleId);
	}
	
	/**
	 * 查询所有用户信息，同时关联查询指定的角色信息
	 *
	 * @param roleId
	 */
	@RequestMapping(value="/queryUsers4Role", method=RequestMethod.POST)
	@ResponseBody
	public Pagination<Map<String, Object>> queryUsers4Role(@RequestParam("start")int offset, 
			@RequestParam("limit")int limit,  User user, Long roleId, Long flag)  {
		
		return roleService.queryUsers4Role(user, roleId, offset, limit);
		
		}
	
	
	/**
	 * 给资源绑定角色, 只接受POST请求
	 * 
	 * @param roleId 角色ID
	 * @param ids 资源ID
	 * @return ResponseData
	 */
	@RequestMapping(value="/bindResource", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData bindResource(Long roleId, Long[] ids) {
		roleService.bindResource(roleId, ids);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 给资源取消绑定角色, 只接受POST请求
	 * 
	 * @param roleId 角色ID
	 * @param ids 资源ID
	 * @return ResponseData
	 */
	@RequestMapping(value="/unBindResource", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData unBindResource(Long roleId, Long[] ids) {
		roleService.unBindResource(roleId, ids);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 给用户绑定角色, 只接受POST请求
	 * 
	 * @param roleId 角色ID
	 * @param ids 资源ID
	 * @return ResponseData
	 */
	@RequestMapping(value="/bindUser", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData bindUser(Long roleId, Long[] ids) {
		roleService.bindUser(roleId, ids);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 给用户取消绑定角色, 只接受POST请求
	 * 
	 * @param roleId 角色ID
	 * @param ids 资源ID
	 * @return ResponseData
	 */
	@RequestMapping(value="/unBindUser", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData unBindUser(Long roleId, Long[] ids) {
		roleService.unBindUser(roleId, ids);
		return ResponseData.SUCCESS_NO_DATA;
	}
}
