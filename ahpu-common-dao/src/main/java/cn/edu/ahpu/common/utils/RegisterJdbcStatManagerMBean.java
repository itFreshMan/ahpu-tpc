/**
 * Copyright (c) 2012,USTC E-BUSINESS TECHNOLOGY CO.LTD All Rights Reserved.
 */

package cn.edu.ahpu.common.utils;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import com.alibaba.druid.stat.JdbcStatManager;

/**
 * @author bsli@starit.com.cn
 * @date 2012-7-28 下午2:05:56
 */
public class RegisterJdbcStatManagerMBean {

	public void register() {
		try {
			ManagementFactory.getPlatformMBeanServer().registerMBean(JdbcStatManager.getInstance(), 
					new ObjectName("com.alibaba.druid:type=JdbcStatManager"));
		} catch (InstanceAlreadyExistsException e) {
			e.printStackTrace();
		} catch (MBeanRegistrationException e) {
			e.printStackTrace();
		} catch (NotCompliantMBeanException e) {
			e.printStackTrace();
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
}
