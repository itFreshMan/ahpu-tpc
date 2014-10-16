package cn.edu.ahpu.tpc.framework.core.spring.session;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import cn.edu.ahpu.tpc.framework.web.model.admin.User;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**  
 * 存储在线用户数  
 *  
 * @datetime 2011-04-10 下午07:55:22
 * @author libinsong1204@gmail.com
 */  
public class OnlineUserHolder {
	//Session 超时时间，暂时设置固定。 保持与web.xml中session超时时间一致。
	private static final int SESSION_DURATION = 30;
	
	private static Cache<String, User> onlineUserCache = 
			CacheBuilder.newBuilder().expireAfterWrite(SESSION_DURATION, TimeUnit.MINUTES).build();
	
	public static void putUser(String key, User user) {
		onlineUserCache.put(key, user);
	}
	
	public static void removeUser(String key) {
		onlineUserCache.invalidate(key);
	}
	
	public static Collection<User> findOnlineUsers() {
		return onlineUserCache.asMap().values();
	}
}
