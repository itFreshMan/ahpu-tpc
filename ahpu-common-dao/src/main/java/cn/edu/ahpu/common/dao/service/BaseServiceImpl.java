package cn.edu.ahpu.common.dao.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.ahpu.common.dao.hibernate4.HibernateBaseDao;
import cn.edu.ahpu.common.dao.support.Pagination;
import cn.edu.ahpu.common.dao.support.PaginationRequest;

/**
 * 服务层，公共类
 * 
 * @datetime 2010-8-9 上午09:15:19
 * @author libinsong1204@gmail.com
 */
abstract public class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {
	//~ Instance fields ================================================================================================
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	//~ Constructors ===================================================================================================
	
	//~ Methods ========================================================================================================
	abstract public HibernateBaseDao<T, ID> getHibernateBaseDao();
	
	@Transactional(readOnly=true)
	public T getEntity(ID id) {
		return this.getHibernateBaseDao().get(id);
	}
	
	@Transactional(readOnly=true)
	public List<T> findAllEntity() {
		return this.getHibernateBaseDao().loadAll();
	}
	
	@Transactional
	public ID insertEntity(T entity) {
		return this.getHibernateBaseDao().save(entity);
	}
	
	@Transactional
	public void updateEntity(T entity) {
		this.getHibernateBaseDao().update(entity);
	}
	
	@Transactional
	public void createOrUpdate(T entity) {
		this.getHibernateBaseDao().saveOrUpdate(entity);
	}
	
	@Transactional
	public T deleteEntity(ID id) {
		return this.getHibernateBaseDao().delete(id);
	}
	
	@Transactional
	public void bulkDeleteEntity(ID[] ids) {
		for(ID id : ids) {
			this.deleteEntity(id);
		}
	}
	
	@Transactional(readOnly=true)
	public List<T> loadEntities() {
		return this.getHibernateBaseDao().loadAll();
	}
	
	@Transactional(readOnly=true)
	public List<T> findByNamedParam(String propertyName, Object value) {
		return this.getHibernateBaseDao().findByNamedParam(propertyName, value);
	}
	
	@Transactional(readOnly=true)
	public List<T> findByNamedParam(String[] propertyNames, Object[] values) {
		return this.getHibernateBaseDao().findByNamedParam(propertyNames, values);
	}
	
	@Transactional(readOnly=true)
	public List<T> findByNamedParamAndOrder(String propertyName, Object value, Order order) {
		return this.getHibernateBaseDao().findByNamedParamAndOrder(propertyName, value, order);
	}
	
	@Transactional(readOnly=true)
	public List<T> findByNamedParamAndOrder(String[] propertyNames, Object[] values, Order order) {
		return this.getHibernateBaseDao().findByNamedParamAndOrder(propertyNames, values, new Order[]{order});
	}
	
	@Transactional(readOnly=true)
	public List<T> findByNamedParamAndOrder(String joinEntity, String propertyName, Object value, Order order) {
		return this.findByNamedParamAndOrder(new String[]{joinEntity}, propertyName, value, order);
	}
	
	@Transactional(readOnly=true)
	public List<T> findByNamedParamAndOrder(String[] joinEntitys, String propertyName, Object value, Order order) {
		return this.getHibernateBaseDao().findByNamedParamAndOrder(joinEntitys, new String[]{propertyName}, 
				new Object[]{value}, new Order[]{order});
	}
	
	@Transactional(readOnly=true)
	public List<T> findByNamedParamAndOrder(String[] joinEntitys, String[] propertyNames, Object[] values, Order order) {
		return this.getHibernateBaseDao().findByNamedParamAndOrder(joinEntitys, propertyNames, values, new Order[]{order});
	}
	
	@Transactional(readOnly=true)
	public Pagination<T> findPage(PaginationRequest<T> paginationRequest) {
		return this.getHibernateBaseDao().findPage(paginationRequest);
	}
}
