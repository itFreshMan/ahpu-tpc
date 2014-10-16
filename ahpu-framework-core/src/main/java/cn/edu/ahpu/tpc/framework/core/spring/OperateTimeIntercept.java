/**
 * Copyright (c) 2011, SuZhou USTC Star Information Technology CO.LTD
 * All Rights Reserved.
 */

package cn.edu.ahpu.tpc.framework.core.spring;

import java.lang.reflect.Method;
import java.util.Date;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import cn.edu.ahpu.tpc.framework.core.util.SecurityContextUtil;

import cn.edu.ahpu.common.model.BaseEntity;

/**
 * 当调用BaseServiceImpl类中的insertEntity和updateEntity方法时，Intercept设置保存或者更新时间. 操作人信息
 *
 * @author   bsli@starit.com.cn
 * @Date	 2011-7-27 上午11:37:11
 */
public class OperateTimeIntercept implements MethodInterceptor {

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String name = invocation.getMethod().getName();
		if("insertEntity".equals(name)) {
			Class parentClazz = (Class) invocation.getArguments()[0].getClass().getGenericSuperclass();
			if(BaseEntity.class == parentClazz) {
				Object obj = invocation.getArguments()[0];
				Method method = parentClazz.getMethod("setCreateTime", Date.class);
				method.invoke(obj, new Date());
				method = parentClazz.getMethod("setCreateUser", String.class);
				method.invoke(obj, SecurityContextUtil.getLoginUserCode());
			}
		} else if("updateEntity".equals(name)) {
			Class parentClazz = (Class) invocation.getArguments()[0].getClass().getGenericSuperclass();
			if(BaseEntity.class == parentClazz) {
				Object obj = invocation.getArguments()[0];
				Method method = parentClazz.getMethod("setUpdateTime", Date.class);
				method.invoke(obj, new Date());
				method = parentClazz.getMethod("setUpdateUser", String.class);
				method.invoke(obj, SecurityContextUtil.getLoginUserCode());
			}
		}
		return invocation.proceed();
	}

}

