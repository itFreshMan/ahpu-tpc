package cn.edu.ahpu.tpc.framework.core.spring.session;

import javax.servlet.http.HttpSessionEvent;

import org.springframework.security.web.session.HttpSessionEventPublisher;

import cn.edu.ahpu.tpc.framework.core.util.SecurityContextUtil;
import cn.edu.ahpu.tpc.framework.web.model.admin.User;

/**  
 * 扩展的HttpSessionEventPublisher  
 * 支持在线人数统计  
 *  
 * @datetime 2011-04-10 下午07:55:22
 * @author libinsong1204@gmail.com
 */   
public class EnhancedHttpSessionEventPublisher extends HttpSessionEventPublisher {

	/**
	 * session 注销时，清除当前用户。
	 */
    @Override   
    public void sessionDestroyed(HttpSessionEvent event) {   
        // 将用户从在线用户列表中移除   
    	User user = SecurityContextUtil.getCurrentUser();
    	if(user != null)
    		OnlineUserHolder.removeUser(user.getUserCode());
        super.sessionDestroyed(event);   
    }
  
}   