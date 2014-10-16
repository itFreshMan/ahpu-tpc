package cn.edu.ahpu.tpc.framework.web.dao.admin;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import cn.edu.ahpu.tpc.framework.web.model.admin.User;

import cn.edu.ahpu.common.dao.hibernate4.HibernateBaseDaoImpl;
import cn.edu.ahpu.common.dao.hibernate4.HibernateCallback;

/**
 *
 * @datetime 2010-8-8 下午04:42:05
 * @author libinsong1204@gmail.com
 */
@Repository
public class UserDao extends HibernateBaseDaoImpl<User, Long> {
	/**
	 * 通过多个用户ID，查询出相应的用户数据
	 * 
	 * @param ids 多个用户ID
	 * @return List<User>
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<User> queryUsersByIds(final Long[] ids) {
		return doExecute(new HibernateCallback<List<User>>() {
			public List<User> doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery("from User where id in(:idList)");
				query.setParameterList("idList", ids);
				return query.list();
			}
		});
	}
	
	public void deleteUser(Long id) {
		String hql = "update User t set t.delFlag = '1' where t.id = ?";
		this.bulkUpdate(hql, id);
	}
}
