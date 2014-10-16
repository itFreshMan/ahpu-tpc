package cn.edu.ahpu.common.dao.key;


import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.type.Type;

import cn.edu.ahpu.common.dao.utils.PrimaryKeyUtil;

/**
 * 通过第三方表生成主键
 * 
 * DateTime 2010-4-28 下午08:04:56
 * 
 * @author libinsong1204@gmail.com
 * @version 1.0
 */
public class SequenceGenerator implements IdentifierGenerator, Configurable {
	//private static final Logger logger = LoggerFactory.getLogger(SequenceGenerator.class);
	private String tableName;

	@Override
	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {
		return getNext(session);
	}

	@Override
	public void configure(Type type, Properties params, Dialect d)
			throws MappingException {
		tableName = params.getProperty("table");
		if (tableName==null) 
			tableName = params.getProperty(PersistentIdentifierGenerator.TABLE);
	}

	private Long getNext(SessionImplementor session) throws HibernateException {
		return PrimaryKeyUtil.getPrimaryKey(tableName);
	}
}
