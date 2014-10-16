package cn.edu.ahpu.tpc.framework.web.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.ahpu.tpc.framework.web.dao.admin.DictTypeDao;
import cn.edu.ahpu.tpc.framework.web.model.admin.DictType;

import cn.edu.ahpu.common.dao.hibernate4.HibernateBaseDao;
import cn.edu.ahpu.common.dao.service.BaseServiceImpl;

/**
 *
 * @datetime 2010-8-20 下午10:20:41
 * @author libinsong1204@gmail.com
 */
@Service
public class DictTypeService extends BaseServiceImpl<DictType, Long> {
	//~ Instance fields ================================================================================================
	@Autowired
	private DictTypeDao dictTypeDao;

	//~ Methods ========================================================================================================
	@Override
	public HibernateBaseDao<DictType, Long> getHibernateBaseDao() {
		return this.dictTypeDao;
	}

}
