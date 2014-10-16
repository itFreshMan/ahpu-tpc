package cn.edu.ahpu.tpc.framework.web.controller.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import cn.edu.ahpu.tpc.framework.web.model.admin.Organization;
import cn.edu.ahpu.tpc.framework.web.model.admin.User;
import cn.edu.ahpu.tpc.framework.web.service.admin.OrganizationService;
import cn.edu.ahpu.tpc.framework.web.service.admin.UserService;

import cn.edu.ahpu.common.dao.support.Pagination;
import cn.edu.ahpu.common.dao.support.PaginationRequest;

/**
 *
 * @datetime 2010-8-17 下午09:59:59
 * @author libinsong1204@gmail.com
 */
@Controller
@RequestMapping("/org")
public class OrganizationController extends BaseController {
	//~ Instance fields ================================================================================================
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private UserService userService;
	
	//~ Methods ========================================================================================================
	@RequestMapping("/index")
	public String index(){
		return "admin/org";
	}

	@RequestMapping("/indexNew")
	public String indexNew(){
		return "admin/orgMgr";
	}
	
	/**
	 * 根据父机构ID，查找所有子机构信息
	 * 
	 * @param node 父机构ID
	 * @return 所有子机构
	 */
	@RequestMapping("/queryOrgs")
	@ResponseBody
	public List<Organization> queryOrgs(Long node, @RequestParam(required=false)String areaCode,@RequestParam(required=false)Long id) {
		String userCode = SecurityContextUtil.getCurrentUser().getUserCode();
		if(userCode.endsWith("admin") || userCode.startsWith("01")){//以admin结尾和01打头的都默认可以查看所有的组织机构
			if(node == null)node=0L;
			return organizationService.findByNamedParamAndOrder("parentId", node, Order.asc("theSort"));
		}
		Long orgId = SecurityContextUtil.getCurrentUser().getOrgId();
		if(node == null || node == 0L){
			return organizationService.findByNamedParamAndOrder("parentId", orgId, Order.asc("theSort"));
		}
		
		if(id!=null){//orgId
			List<Organization> list = organizationService.findByNamedParamAndOrder("id", id, Order.asc("theSort"));
			if(list!=null)
				areaCode = list.get(0).getAreaCode();
		}
		
		String[] propertyNames = {"parentId","areaCode"};
		Object[] values = {node,areaCode};
		return organizationService.findByNamedParamAndOrder(propertyNames, values, Order.asc("theSort"));
	}
	
	/**
	 * 根据父机构ID，查找所有子机构信息以及机构下的人员信息
	 * 
	 * @param node 父机构ID
	 * @return 所有子机构
	 */
	@RequestMapping("/queryOrgAndPersons")
	@ResponseBody
	public List<Map<String, Object>> queryOrgAndPersons(Long node) {
		if(node == null)
			node = 0L;
		return organizationService.queryOrgAndPersons(node);
	}
	
	/**
	 * 保存指定节点下，所有直接子节点的顺序。
	 * 
	 * @param parentId 父节点
	 * @param childIds 所有子节点ID
	 * @return
	 */
	@RequestMapping("/saveOrgOrder")
	@ResponseBody
	public ResponseData saveOrganizationOrder(Long parentId, Long[] childIds) {
		organizationService.saveOrganizationOrder(parentId, childIds);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 插入机构信息
	 * 
	 * @param organization
	 * @return
	 */
	@RequestMapping(value="/insertOrg", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData insertOrganization(Organization organization) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		organization.setCreateUser(userDetails.getUsername());
		organization.setCreateTime(new Date());
		organizationService.insertEntity(organization);
		
		String orgSeq = "";
		if(organization.getParentId()==0){
			orgSeq = organization.getId() + ".";
		}else{
			orgSeq = organizationService.getEntity(organization.getParentId()).getOrgSeq() + organization.getId() + ".";
		}
		organization.setOrgSeq(orgSeq);
		organizationService.updateEntity(organization);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 更新机构信息
	 * 
	 * @param organization
	 * @return
	 */
	@RequestMapping(value="/updateOrg", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData updateOrganization(Organization organization) {
		organizationService.updateEntity(organization);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 加载机构信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/loadOrg", method=RequestMethod.POST)
	@ResponseBody
	public Organization loadOrganization(Long id) {
		return organizationService.getEntity(id);
	}
	
	/**
	 * 删除机构
	 * 
	 * @param parentId 父节点
	 * @param childIds 所有子节点ID有逗号连接的字符串
	 * @return
	 */
	@RequestMapping("/deleteOrg")
	@ResponseBody
	public ResponseData deleteOrganization(Long id) {
		organizationService.deleteEntity(id);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 查询机构下的用户
	 * 
	 * @param orgId 机构ID
	 * @return
	 */
	@RequestMapping("/queryUsers4Org")
	@ResponseBody
	public Pagination<User> queryUsers4Org(@RequestParam("start")int offset, 
			@RequestParam("limit")int limit, @RequestParam(required=false)Long orgId, 
			@RequestParam(required = false)String sort, 
			@RequestParam(required = false)String dir) {
		PaginationRequest<User> paginationRequest = new PaginationRequest<User>(offset/limit, offset, limit);
		if(orgId != null){
			paginationRequest.addCondition("orgId", orgId);
		}else{
			orgId = SecurityContextUtil.getCurrentUser().getOrgId();
			paginationRequest.addCondition("orgId", orgId);
		}
		
		if(StringUtils.hasText(sort) && StringUtils.hasText(dir))
			paginationRequest.addOrder(sort, dir);
		
		Pagination<User> page = userService.findPage(paginationRequest);
		DictionaryHolder.transfercoder(page.getResult(), "CORE.GENDER", "getGender");
		return page;
	}
	
	/**
	 * 查询机构下及层级子机构下的用户
	 * 
	 * @param orgId 机构ID
	 * @return
	 */
	@RequestMapping("/queryUsers4CascadeOrg")
	@ResponseBody
	public Pagination<User> queryUsers4CascadeOrg(@RequestParam("start")int offset, 
			@RequestParam("limit")int limit, @RequestParam(required=false)Long orgId, 
			@RequestParam(required = false)String sort, 
			@RequestParam(required = false)String dir) {
		PaginationRequest<User> paginationRequest = new PaginationRequest<User>(offset/limit, offset, limit);
		
		if(orgId == null) orgId = SecurityContextUtil.getCurrentUser().getOrgId();
		String orgSeq = organizationService.getEntity(orgId).getOrgSeq();
		paginationRequest.addLikeCondition("orgSeq", orgSeq + "%");
		if(StringUtils.hasText(sort) && StringUtils.hasText(dir))
			paginationRequest.addOrder(sort, dir);
		paginationRequest.addCondition("delFlag", 0);
		Pagination<User> page = userService.findPage(paginationRequest);
		DictionaryHolder.transfercoder(page.getResult(), "CORE.GENDER", "getGender");
		return page;
		
	}
}
