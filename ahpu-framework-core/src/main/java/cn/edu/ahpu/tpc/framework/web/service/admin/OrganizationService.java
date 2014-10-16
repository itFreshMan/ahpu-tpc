package cn.edu.ahpu.tpc.framework.web.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.ahpu.tpc.framework.web.dao.admin.OrganizationDao;
import cn.edu.ahpu.tpc.framework.web.model.admin.Organization;

import cn.edu.ahpu.common.dao.hibernate4.HibernateBaseDao;
import cn.edu.ahpu.common.dao.service.BaseServiceImpl;

/**
 *
 * @datetime 2010-8-17 下午09:55:18
 * @author libinsong1204@gmail.com
 */
@Service
public class OrganizationService extends BaseServiceImpl<Organization, Long> {
	//~ Instance fields ================================================================================================
	@Autowired
	private OrganizationDao organizationDao;

	//~ Methods ========================================================================================================
	@Override
	public HibernateBaseDao<Organization, Long> getHibernateBaseDao() {
		return this.organizationDao;
	}
	
	/**
	 * 保存指定节点下，所有直接子节点的顺序。
	 * 
	 * @param parentId 父节点
	 * @param childIds 所有子节点ID
	 */
	@Transactional
	public void saveOrganizationOrder(Long parentId, Long[] childIds) {
		int index=1;
		for(Long id :  childIds) {
			Organization organization = organizationDao.get(id);
			organization.setParentId(parentId);
			organization.setTheSort(index++);
		}
	}
	
	public List<Map<String, Object>> queryOrgAndPersons(Long orgId) {
		List<Map<String, Object>> list = organizationDao.queryOrgAndPersons(orgId);
		for(Map<String, Object> map : list) {
			if("1".equals(String.valueOf(map.get("type")))) {
				Object id = map.get("id");
				map.put("id", "person_"+id);
			}
		}
		
		return list; 
	}
}
