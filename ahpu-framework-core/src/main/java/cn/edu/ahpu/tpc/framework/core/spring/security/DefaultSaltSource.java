package cn.edu.ahpu.tpc.framework.core.spring.security;

import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UserDetails;

import cn.edu.ahpu.tpc.framework.web.model.admin.User;

/**
 * 
 * @datetime 2013-3-10 下午07:55:22
 * @author libinsong1204@gmail.com
 */
public class DefaultSaltSource implements SaltSource {

	@Override
	public Object getSalt(UserDetails userDetails) {
		User user = (User)userDetails;
		return user.getSalt();
	}
}
