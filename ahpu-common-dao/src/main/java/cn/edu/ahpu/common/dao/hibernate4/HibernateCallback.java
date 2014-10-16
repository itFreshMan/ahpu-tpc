/**
 * Copyright (c) 2012,USTC E-BUSINESS TECHNOLOGY CO.LTD All Rights Reserved.
 */

package cn.edu.ahpu.common.dao.hibernate4;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * @author bsli@starit.com.cn
 * @date 2012-7-26 下午1:04:48
 */
public interface HibernateCallback<T> {

	T doInHibernate(Session session) throws HibernateException, SQLException;

}

