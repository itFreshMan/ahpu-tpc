/**        
 * Copyright (c) 2013 by 苏州科大国创信息技术有限公司.    
 *
 * CookieAuthenticationFilter.java Create on 2013-5-8 下午10:17:36 
 */
package cn.edu.ahpu.tpc.framework.core.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StringUtils;

import cn.edu.ahpu.tpc.framework.core.util.SecurityContextUtil;

/**
 * 
 * @author <a href="mailto:bsli@ustcinfo.com">li.binsong</a>
 * 
 */
public class CookieAuthenticationFilter extends
		AbstractAuthenticationProcessingFilter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CookieAuthenticationFilter.class);
	
	public CookieAuthenticationFilter() {
		super(null);
	}

	protected CookieAuthenticationFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
	}

	public static final String COOKIE_NAME_USER = "USISSOCookieNameUser";
	
	private UserDetailsService userDetailsService;
	
	private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.
	 * AbstractAuthenticationProcessingFilter
	 * #attemptAuthentication(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException,
			IOException, ServletException {
		
		Cookie cookieNameUser = getCookie(request, COOKIE_NAME_USER);
		String username = cookieNameUser.getValue();

		UserDetails user = userDetailsService.loadUserByUsername(username);
		
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(user,
                null, authoritiesMapper.mapAuthorities(user.getAuthorities()));
		
		cookieNameUser.setMaxAge(0);
	    response.addCookie(cookieNameUser);
	    
	    LOGGER.info("Cookie sso success, user: ",  username);
		return result;
	}
	
	@Override
    public void afterPropertiesSet() {
    }

	@Override
	protected boolean requiresAuthentication(HttpServletRequest request,
			HttpServletResponse response) {
		//避免循环调用
		if(SecurityContextUtil.getCurrentUser() != null)
			return false;
		
		Cookie cookieNameUser = getCookie(request, COOKIE_NAME_USER);
		String username = null;

		if (cookieNameUser != null) {
			username = cookieNameUser.getValue();
		}
		
		if(StringUtils.hasText(username))
			return true;
		else
			return false;
	}

	private static Cookie getCookie(HttpServletRequest request, final String name) {
		final Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				final Cookie cookie = cookies[i];

				if (cookie.getName().equals(name)) {
					return cookie;
				}
			}
		}

		return null;
	}

	/**
	 * @return the userDetailsService
	 */
	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	/**
	 * @param userDetailsService the userDetailsService to set
	 */
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

}
