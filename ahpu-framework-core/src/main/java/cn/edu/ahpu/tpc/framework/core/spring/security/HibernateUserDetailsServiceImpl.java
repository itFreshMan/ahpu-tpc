package cn.edu.ahpu.tpc.framework.core.spring.security;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class HibernateUserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private SessionFactory sessionFactory;

	//~ Methods ========================================================================================================
	@SuppressWarnings("rawtypes")
	public UserDetails loadUserByUsername(String userCode)
			throws UsernameNotFoundException, DataAccessException {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from User as res left outer join fetch res.authorities where res.delFlag =0 and res.userCode = ?");
		query.setString(0, userCode);
		List results = query.list();
		
		if (results.size() < 1) {
            throw new UsernameNotFoundException(userCode + " not found");
        }
		return (UserDetails) results.get(0);
	}
}
