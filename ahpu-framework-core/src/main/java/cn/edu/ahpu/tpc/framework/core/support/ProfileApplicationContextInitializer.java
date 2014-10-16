/**
 * Copyright (c) 2012,USTC E-BUSINESS TECHNOLOGY CO.LTD All Rights Reserved.
 */

package cn.edu.ahpu.tpc.framework.core.support;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * @author bsli@starit.com.cn
 * @date 2012-3-1 下午1:20:50
 */
public class ProfileApplicationContextInitializer implements
		ApplicationContextInitializer<ConfigurableApplicationContext> {
	
	private static final Logger _logger = LoggerFactory.getLogger(ProfileApplicationContextInitializer.class);
	public static String casProfile;
	
	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		try {
			ClassPathResource resource = new ClassPathResource("META-INF/res/profile.properties");
			Properties properties = PropertiesLoaderUtils.loadProperties(resource);
			
			String profile = properties.getProperty("spring.profiles.active");
			casProfile = properties.getProperty("security.profiles.active");
			
			if(profile==null){
				profile = "development";
			}
			if(casProfile==null){
				casProfile = "nocas";
			}

			applicationContext.getEnvironment().setActiveProfiles(new String[]{profile,casProfile});
			_logger.info("spring profile {}", profile);

			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
