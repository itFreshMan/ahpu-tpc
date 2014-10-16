//$Id$
/**
* 工程名: 	usi-framework-core
* 文件名: 	FilterChainProxyFactoryBean.java
* 创建人:  	fang.fang
* 创建时间: 	2013-4-8 上午10:30:11
* 版权所有：	Copyright (c) 2013 苏州科大国创信息技术有限公司  
* -----------------------------变更记录 ----------------------------- 
* 日期        		变更人      		版本号  		变更描述  
* ------------------------------------------------------------------  
* 2013-4-8     	fang.fang		1.0.0      	first created  
*/
package cn.edu.ahpu.tpc.framework.core.spring.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @since usi-framework-core 1.0.0
 * @version 1.0.2 2013-4-8
 * @author fang.fang@ustcinfo.com
 */
@SuppressWarnings("unchecked")
public class FilterChainProxyFactoryBean implements FactoryBean<FilterChainProxy>, ApplicationContextAware{
	
	private FilterChainProxy chainProxy;
	
	/**
	* @param applicationContext
	* @throws BeansException
	* @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	*/
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		List<SecurityFilterChain> filterChains = null;
		if(applicationContext.containsBean("filterChain")){
			filterChains = (List<SecurityFilterChain>)applicationContext.getBean("filterChain");
		}else{
			filterChains = new ArrayList<SecurityFilterChain>();
		}
		filterChains.addAll((List<SecurityFilterChain>)applicationContext.getBean("innerFilterChain"));
		
		chainProxy = new FilterChainProxy(filterChains);
	}

	/**
	* @return
	* @throws Exception
	* @see org.springframework.beans.factory.FactoryBean#getObject()
	*/
	@Override
	public FilterChainProxy getObject() throws Exception {
		return chainProxy;
	}

	/**
	* @return
	* @see org.springframework.beans.factory.FactoryBean#getObjectType()
	*/
	@Override
	public Class<?> getObjectType() {
		return FilterChainProxy.class;
	}

	/**
	* @return
	* @see org.springframework.beans.factory.FactoryBean#isSingleton()
	*/
	@Override
	public boolean isSingleton() {
		return true;
	}


}
