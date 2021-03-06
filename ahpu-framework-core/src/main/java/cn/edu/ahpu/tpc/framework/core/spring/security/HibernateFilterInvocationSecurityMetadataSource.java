package cn.edu.ahpu.tpc.framework.core.spring.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import cn.edu.ahpu.tpc.framework.core.cache.CacheNames;
import cn.edu.ahpu.tpc.framework.core.util.AntUrlPathMatcher;
import cn.edu.ahpu.tpc.framework.core.util.UrlMatcher;
import cn.edu.ahpu.tpc.framework.web.model.admin.Resource;
import cn.edu.ahpu.tpc.framework.web.model.admin.Role;

/**
 * FilterInvocationSecurityMetadataSource Hibernate实现。从数据库中获取资源权限信息。资源与角色关联
 *
 * @datetime 2010-8-9 下午05:34:11
 * @author libinsong1204@gmail.com
 */
public class HibernateFilterInvocationSecurityMetadataSource extends HibernateDaoSupport
		implements FilterInvocationSecurityMetadataSource{

	//~ Static fields ==================================================================================================
	public static final String CACHE_KEY = "FilterInvoSecMetaKey";

	//~ Instance fields ================================================================================================
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CacheManager cacheManager;
	
	private Map<Object, Collection<ConfigAttribute>> httpMap = null;

	private UrlMatcher urlMatcher = new AntUrlPathMatcher(true);

    private boolean stripQueryStringFromUrls;
	
	//~ Constructors ===================================================================================================

	//~ Methods ========================================================================================================
    
    /**
     * 初始化，加载资源权限 
     */
    @SuppressWarnings("unchecked")
	public void loadSecurityMetadataSource() throws Exception {
    	logger.info("Loading Security Metadata Source...");
    	
    	httpMap = new LinkedHashMap<Object, Collection<ConfigAttribute>>();
    	
    	Session session = getHibernateTemplate().getSessionFactory().openSession();
    	Query query = session.createQuery("from Resource as res left outer join fetch res.roles " +
    				"where res.enabled = :enabled and res.type <> :type order by res.priority");
    	
    	query.setCharacter("enabled", 'Y');
    	//不等于method类型
    	query.setString("type", Resource.ResourceType.METHOD.getType());
		List<Resource> resources = query.list();
		
		for(Resource resource : resources) {
			String pattern = resource.getAction();
			
			List<ConfigAttribute> attrs = new ArrayList<ConfigAttribute>();
			Set<Role> roles = resource.getRoles();
	    	
			for(Role role : roles) {
				attrs.add(new SecurityConfig(role.getCode()));
			}
			httpMap.put(urlMatcher.compile(pattern), attrs);

			if (logger.isDebugEnabled()) {
	            logger.debug("Added URL pattern: " + pattern + "; attributes: " + attrs);
	        }
		}
		session.close();
		
		Cache cache = cacheManager.getCache(CacheNames.RESOURCE_CACHE_NAME);
		cache.put(CACHE_KEY, httpMap);
	}
	
	/**
	 * 
	 */
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	/**
	 * 通过FilterInvocation对象查找适合的ConfigAttribute
	 * 
	 * @param object
	 * @return
	 */
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		if ((object == null) || !this.supports(object.getClass())) {
            throw new IllegalArgumentException("Object must be a FilterInvocation");
        }

        String url = ((FilterInvocation) object).getRequestUrl();

        return lookupAttributes(url);
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	public final Collection<ConfigAttribute> lookupAttributes(String url) {
        if (stripQueryStringFromUrls) {
            // Strip anything after a question mark symbol, as per SEC-161. See also SEC-321
            int firstQuestionMarkIndex = url.indexOf("?");

            if (firstQuestionMarkIndex != -1) {
                url = url.substring(0, firstQuestionMarkIndex);
            }
        }

        if (urlMatcher.requiresLowerCaseUrl()) {
            url = url.toLowerCase();

            if (logger.isDebugEnabled()) {
                logger.debug("Converted URL to lowercase, from: '" + url + "'; to: '" + url + "'");
            }
        }

        // Obtain the map of request patterns to attributes for this method and lookup the url.
        Cache cache = cacheManager.getCache(CacheNames.RESOURCE_CACHE_NAME);
        Object obj = cache.get(CACHE_KEY);
        if(obj == null) {
    		try {
				loadSecurityMetadataSource();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        Collection<ConfigAttribute> attributes = extractMatchingAttributes(url, httpMap);

        return attributes;
    }
	
	/**
	 * 
	 * @param url
	 * @param map
	 * @return
	 */
	private Collection<ConfigAttribute> extractMatchingAttributes(String url, Map<Object, Collection<ConfigAttribute>> map) {

        final boolean debug = logger.isDebugEnabled();

        for (Map.Entry<Object, Collection<ConfigAttribute>> entry : map.entrySet()) {
            Object p = entry.getKey();
            boolean matched = urlMatcher.pathMatchesUrl(entry.getKey(), url);

            if (debug) {
                logger.debug("Candidate is: '" + url + "'; pattern is " + p + "; matched=" + matched);
            }

            if (matched) {
                return entry.getValue();
            }
        }
        return null;
    }

	/**
	 * 
	 */
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}
}
