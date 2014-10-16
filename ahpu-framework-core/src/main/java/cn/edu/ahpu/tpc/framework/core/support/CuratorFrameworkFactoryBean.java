/**        
 * Copyright (c) 2013 by 苏州科大国创信息技术有限公司.    
 *
 * CuratorFrameworkFactoryBean.java Create on 2013-5-18 下午4:45:17 
 */    
package cn.edu.ahpu.tpc.framework.core.support;

import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.retry.RetryNTimes;

/**    
 * 创建 CuratorFramework
 * 
 * @author <a href="mailto:bsli@ustcinfo.com">li.binsong</a>
 *  
 */
public class CuratorFrameworkFactoryBean implements FactoryBean<CuratorFramework>, InitializingBean, DisposableBean {
	private CuratorFramework curatorFramework;
	
	private String zkAddress;
	
	private String namespace;
	
	/**
	 * 初始化的节点路径
	 */
	private String[] nodePaths;
	
	private int connectionTimeout = 1000;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.hasText(zkAddress, "zookeeper address 不能为空");
		Assert.hasText(namespace, "namespace 不能为空");
		
		curatorFramework = CuratorFrameworkFactory.builder()
				.connectString(zkAddress).namespace(namespace)
				.connectionTimeoutMs(connectionTimeout)
				.retryPolicy(new RetryNTimes(3, 10000))
				.build();
		
		curatorFramework.start();
		
		initNodePath();
	}

	/**
	 * 初始化节点路径
	 * 
	 * @throws Exception
	 */
	private void initNodePath() throws Exception {
		if(nodePaths == null)
			return;
		
		for(String path : nodePaths) {
			int fromIndex = 1;
			int index = 1;
			
			while(true) {
				index = path.indexOf("/", fromIndex);
				if(index == -1) {
					Stat stat = curatorFramework.checkExists().forPath(path);
					
					if(stat == null) {
						curatorFramework.create().forPath(path);
					}
					break;
				}
					
				fromIndex = index + 1;
				
				String __path = path.substring(0, index);
				Stat stat = curatorFramework.checkExists().forPath(__path);
				
				if(stat == null) {
					curatorFramework.create().forPath(__path);
				}
			}
		}
	}
	
	@Override
	public CuratorFramework getObject() throws Exception {
		 return curatorFramework;
	}

	@Override
	public Class<CuratorFramework> getObjectType() {
		return CuratorFramework.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void destroy() throws Exception {
		if(curatorFramework != null)
			curatorFramework.close();
	}

	public String getZkAddress() {
		return zkAddress;
	}

	public void setZkAddress(String zkAddress) {
		this.zkAddress = zkAddress;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String[] getNodePaths() {
		return nodePaths;
	}

	public void setNodePaths(String[] nodePaths) {
		this.nodePaths = nodePaths;
	}
	
}
