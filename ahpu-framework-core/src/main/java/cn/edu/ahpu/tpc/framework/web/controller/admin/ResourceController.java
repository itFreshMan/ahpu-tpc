package cn.edu.ahpu.tpc.framework.web.controller.admin;

import java.util.Set;

import javax.annotation.Resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.ahpu.tpc.framework.core.util.DictionaryHolder;
import cn.edu.ahpu.tpc.framework.core.util.ResponseData;
import cn.edu.ahpu.tpc.framework.web.controller.BaseController;
import cn.edu.ahpu.tpc.framework.web.model.admin.Resource;
import cn.edu.ahpu.tpc.framework.web.model.admin.Role;
import cn.edu.ahpu.tpc.framework.web.service.admin.ResourceService;

import cn.edu.ahpu.common.dao.support.Pagination;
import cn.edu.ahpu.common.dao.support.PaginationRequest;





/**
 * 资源Controller
 *
 * @datetime 2010-8-8 下午04:47:03
 * @author libinsong1204@gmail.com
 */
@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController {
	//~ Instance fields ================================================================================================
	@Autowired
	private ResourceService resourceService;
	
	//~ Constructors ===================================================================================================
	
	//~ Methods ========================================================================================================
	@RequestMapping("/index")
	public String index(){
		return "admin/resource";
	}
	
	@RequestMapping("/indexNew")
	public String indexNew(){
		return "admin/resourceMgr";
	}
	
	/**
	 * 资源管理，查询资源信息，按照资源优先级降序排序
	 * 
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/pageQueryResources")
	@ResponseBody
	public Pagination<Resource> pageQueryResources(@RequestParam("start")int offset, 
			@RequestParam("limit")int limit, Resource resource, @RequestParam(required = false)String sort, 
			@RequestParam(required = false)String dir) {
		PaginationRequest<Resource> paginationRequest = new PaginationRequest<Resource>(offset/limit, offset, limit);
		
		if(StringUtils.hasText(sort) && StringUtils.hasText(dir))
			paginationRequest.addOrder(sort, dir);
		else
			paginationRequest.addOrder("priority", true);
		
		if(StringUtils.hasText(resource.getType()))
			paginationRequest.addCondition("type", resource.getType());
		if(StringUtils.hasText(resource.getEnabled()))
			paginationRequest.addCondition("enabled", resource.getEnabled());
		if(StringUtils.hasText(resource.getModule()))
			paginationRequest.addCondition("module", resource.getModule());
		
		if(StringUtils.hasText(resource.getName()))
			paginationRequest.addLikeCondition("name", "%" + resource.getName() + "%");
		
		Pagination<Resource> page = resourceService.findPage(paginationRequest);
		DictionaryHolder.transfercoder(page.getResult(), "CORE.ENABLED", "getEnabled");
		DictionaryHolder.transfercoder(page.getResult(), "CORE.RESOURCE.TYPE", "getType");
		DictionaryHolder.transfercoder(page.getResult(), "CORE.MODULE", "getModule");
		return page;
	}
	
	/**
	 * 查询菜单类型资源信息，按照资源优先级降序排序
	 * 
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/pageQueryResourcesByMenu")
	@ResponseBody
	public Pagination<Resource> pageQueryResourcesByMenu(@RequestParam("start")int offset, 
			@RequestParam("limit")int limit, Resource resource) {
		PaginationRequest<Resource> paginationRequest = new PaginationRequest<Resource>(offset/limit, offset, limit);
		paginationRequest.addOrder("priority", true);
		paginationRequest.addCondition("type", Resource.ResourceType.MENU.getType());
		paginationRequest.addCondition("enabled", "Y");
		return resourceService.findPage(paginationRequest);
	}
	
	/**
	 * 保存资源, 只接受POST请求
	 * 
	 * @param resource
	 * @return ResponseData
	 */
	@RequestMapping(value="/insertResource", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData insertResource(Resource resource) {
		resourceService.insertEntity(resource);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 更新资源, 只接受POST请求
	 * 
	 * @param resource
	 * @return ResponseData
	 */
	@RequestMapping(value="/updateResource", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData updateResource(Resource resource) {
		resourceService.updateResource(resource);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 加载资源, 只接受POST请求
	 * 
	 * @param id
	 * @return Resource
	 */
	@RequestMapping(value="/loadResource", method=RequestMethod.POST)
	@ResponseBody
	public Resource loadResource(Long id) {
		return resourceService.getEntity(id);
	}
	
	/**
	 * 删除资源, 只接受POST请求
	 * 
	 * @param id
	 * @return ResponseData
	 */
	@RequestMapping(value="/deleteResources", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData deleteResources(Long[] ids) {
		resourceService.deleteResources(ids);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 查询资源关联的所有角色, 只接受POST请求
	 * 
	 * @param resId 资源ID
	 * @return List<Role> 资源关联的所有角色
	 */
	@RequestMapping(value="/queryRoles4Res", method=RequestMethod.POST)
	@ResponseBody
	public Set<Role> queryRoles4Res(Long resId) {
		return resourceService.queryRoles4Res(resId);
	}
	
	/**
	 * 查询所有资源信息，同时关联查询指定的角色信息
	 *
	 * @param roleId
	 */
	@RequestMapping(value="/pageQueryRoles4Res", method=RequestMethod.POST)
	@ResponseBody
	public Pagination<Role> pageQueryRoles4Res(@RequestParam("start")int offset, 
			@RequestParam("limit")int limit, Long resId)  {
		PaginationRequest<Role> pageRequest = new PaginationRequest<Role>(offset/limit, offset, limit);
		return resourceService.pageQueryRoles4Res(pageRequest, resId);
	}
	
	/**
	 * 给资源绑定角色, 只接受POST请求
	 * 
	 * @param resId 资源ID
	 * @param ids 角色ID
	 * @return ResponseData
	 */
	@RequestMapping(value="/bindRole", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData bindRole(Long resId, Long[] ids) {
		resourceService.bindRole(resId, ids);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 给资源取消绑定角色, 只接受POST请求
	 * 
	 * @param resId 资源ID
	 * @param ids 角色ID
	 * @return ResponseData
	 */
	@RequestMapping(value="/unBindRole", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData unBindRole(Long resId, Long[] ids) {
		resourceService.unBindRole(resId, ids);
		return ResponseData.SUCCESS_NO_DATA;
	}
}
