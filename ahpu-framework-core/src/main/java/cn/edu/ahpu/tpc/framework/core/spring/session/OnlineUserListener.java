package cn.edu.ahpu.tpc.framework.core.spring.session;

import java.util.Date;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import cn.edu.ahpu.tpc.framework.core.util.SecurityContextUtil;
import cn.edu.ahpu.tpc.framework.web.model.admin.User;

/**  
 * 当创建用户登录创建session时，保存用户信息。
 *  
 * @datetime 2011-04-10 下午07:55:22
 * @author libinsong1204@gmail.com
 */   
public class OnlineUserListener implements HttpSessionAttributeListener {

	public void attributeAdded(HttpSessionBindingEvent event) {
	    if(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY.endsWith(event.getName())) {   
	        User user = SecurityContextUtil.getCurrentUser();
	        if(user != null) {
	        	user.setLastLoginTime(new Date());
	        	OnlineUserHolder.putUser(user.getUserCode(), user);
	        }
	    }   
    }

	@Override
	public void attributeRemoved(HttpSessionBindingEvent arg0) {
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent arg0) {
	}   
}
