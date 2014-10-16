package cn.edu.ahpu.common.dao.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Order;

import cn.edu.ahpu.common.dao.support.Pagination;
import cn.edu.ahpu.common.dao.support.PaginationRequest;

/**
 *
 * @datetime 2010-8-9 上午09:14:46
 * @author libinsong1204@gmail.com
 */
public interface BaseService<T, ID extends Serializable> {
	
	public T getEntity(ID id);
	
	public ID insertEntity(T entity);
	
	public void updateEntity(T entity);
	
	public void createOrUpdate(T entity);
	
	public T deleteEntity(ID id);
	
	public void bulkDeleteEntity(ID[] ids);
	
	public List<T> loadEntities();
	
	public List<T> findByNamedParam(String propertyName, Object value);
	
	public List<T> findByNamedParam(String[] propertyNames, Object[] values);
	
	public List<T> findByNamedParamAndOrder(String propertyName, Object value, Order order);
	
	public List<T> findByNamedParamAndOrder(String[] propertyNames, Object[] values, Order order);
	
	public List<T> findByNamedParamAndOrder(String joinEntity, String propertyName, Object value, Order order);
	
	public List<T> findByNamedParamAndOrder(String[] joinEntitys, String propertyName, Object value, Order order);
	
	public List<T> findByNamedParamAndOrder(String[] joinEntitys, String[] propertyNames, Object[] values, Order order);
	
	public Pagination<T> findPage(PaginationRequest<T> paginationRequest);
}
