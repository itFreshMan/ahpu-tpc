package cn.edu.ahpu.tpc.framework.web.dao.admin;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import cn.edu.ahpu.tpc.framework.web.model.admin.Resource;

import cn.edu.ahpu.common.dao.hibernate4.HibernateBaseDaoImpl;
import cn.edu.ahpu.common.dao.hibernate4.HibernateCallback;

/**
 *
 * @datetime 2010-8-8 下午04:42:05
 * @author libinsong1204@gmail.com
 */
@Repository
public class ResourceDao extends HibernateBaseDaoImpl<Resource, Long> {
	
	/**
	 * 通过多个资源ID，查询出相应的资源数据
	 * 
	 * @param ids 多个资源ID
	 * @return List<Role>
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<Resource> queryResourcesByIds(final Long[] ids) {
		return doExecute(new HibernateCallback<List<Resource>>() {
			public List<Resource> doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery("from Resource where id in(:idList)");
				query.setParameterList("idList", ids);
				return query.list();
			}
		});
	}
}
