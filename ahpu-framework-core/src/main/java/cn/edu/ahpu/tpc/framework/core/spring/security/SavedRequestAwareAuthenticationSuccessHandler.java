/**        
 * Copyright (c) 2013 by 苏州科大国创信息技术有限公司.    
 *
 * SavedRequestAwareAuthenticationSuccessHandler.java Create on 2013-6-7 下午5:36:57 
 */    
package cn.edu.ahpu.tpc.framework.core.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

/**    
 * 针对cookie登录，登录成功以后，继续访问页面。而不是跳转到固定页面。
 *    
 * @author <a href="mailto:bsli@ustcinfo.com">li.binsong</a>
 *  
 */
public class SavedRequestAwareAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    protected final Log logger = LogFactory.getLog(this.getClass());

    private PortResolver portResolver = new PortResolverImpl();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
    	DefaultSavedRequest savedRequest = new DefaultSavedRequest(request, portResolver);


        clearAuthenticationAttributes(request);

        // Use the DefaultSavedRequest URL
        String targetUrl = savedRequest.getRedirectUrl();
        logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
