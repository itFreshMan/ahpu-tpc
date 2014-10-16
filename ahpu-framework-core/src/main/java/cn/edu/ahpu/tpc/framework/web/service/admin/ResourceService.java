package cn.edu.ahpu.tpc.framework.web.service.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.ahpu.tpc.framework.core.cache.CacheNames;
import cn.edu.ahpu.tpc.framework.web.dao.admin.ResourceDao;
import cn.edu.ahpu.tpc.framework.web.dao.admin.RoleDao;
import cn.edu.ahpu.tpc.framework.web.model.admin.Resource;
import cn.edu.ahpu.tpc.framework.web.model.admin.Role;

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
@Transactional
public class ResourceService extends BaseServiceImpl<Resource, Long> {
	public static final String FILTER_INVO_SEC_META_KEY = "FilterInvoSecMetaKey";
	
	//~ Instance fields ================================================================================================
	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private CacheManager cacheManager;

	//~ Methods ========================================================================================================
	@Override
	public HibernateBaseDao<Resource, Long> getHibernateBaseDao() {
		return this.resourceDao;
	}
	
	/**
	 * 批量删除资源数据
	 * 
	 * @param resIds
	 */
	@Transactional
	public void deleteResources(Long[] resIds) {
		for(Long id : resIds) 
			this.deleteResource(id);
	}
	
	/**
	 * 删除资源信息
	 * 
	 * @param Long 资源ID
	 */
	private void deleteResource(Long id) {
		Resource resource = this.resourceDao.get(id);
		Set<Role> list = resource.getRoles();
		for(Role role : list) {
			role.getResources().remove(resource);
		}
		this.deleteEntity(id);
		
		Cache cache = cacheManager.getCache(CacheNames.RESOURCE_CACHE_NAME);
		cache.evict(FILTER_INVO_SEC_META_KEY);
	}
	
	/**
	 * 更新资源信息
	 * 
	 * @param resource
	 */
	@Transactional
	public void updateResource(Resource resource) {
		this.updateEntity(resource);
		
		Cache cache = cacheManager.getCache(CacheNames.RESOURCE_CACHE_NAME);
		cache.evict(FILTER_INVO_SEC_META_KEY);
	}
	
	/**
	 * 查询资源关联的所有角色, 只接受POST请求
	 * 
	 * @param resId 资源ID
	 * @return List<Role> 资源关联的所有角色
	 */
	@Transactional(readOnly=true)
	public Set<Role> queryRoles4Res(Long resId) {
		Resource resource = resourceDao.get(resId);
		return resource.getRoles();
	}
	
	/**
	 * 查询所有角色信息，同时关联查询指定的资源信息
	 *
	 * @param pageRequest
	 * @param roleId
	 * @return Page<Role>
	 */
	@Transactional(readOnly=true)
	public Pagination<Role> pageQueryRoles4Res(PaginationRequest<Role> paginationRequest, Long resId) {
		Pagination<Role> page = this.roleDao.queryRoles(paginationRequest);
		List<Role> list = page.getResult();
		List<Role> newList = new ArrayList<Role>();
		List<Long> ids = new ArrayList<Long>();
		for(Role role : list) {
			if(!ids.contains(role.getId())) {
				ids.add(role.getId());
				for(Resource resource : role.getResources()) {
					if(resource.getId().longValue() == resId.longValue()) {
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
	 * @param resId 资源ID
	 * @param ids 资源ID
	 * @return ResponseData
	 */
	@Transactional
	public void bindRole(Long resId, Long[] ids) {
		List<Role> list = roleDao.queryRolesByIds(ids);
		Resource resource = this.resourceDao.get(resId);
		for(Role role : list) {
			role.getResources().add(resource);
		}

		Cache cache = cacheManager.getCache(CacheNames.RESOURCE_CACHE_NAME);
		cache.evict(FILTER_INVO_SEC_META_KEY);
	}
	
	/**
	 * 给资源取消绑定角色
	 * 
	 * @param resId 资源ID
	 * @param ids 资源ID
	 * @return ResponseData
	 */
	@Transactional
	public void unBindRole(Long resId, Long[] ids) {
		List<Role> list = roleDao.queryRolesByIds(ids);
		Resource resource = this.resourceDao.get(resId);
		for(Role role : list) {
			role.getResources().remove(resource);
		}

		Cache cache = cacheManager.getCache(CacheNames.RESOURCE_CACHE_NAME);
		cache.evict(FILTER_INVO_SEC_META_KEY);
	}
}
