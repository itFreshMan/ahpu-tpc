package cn.edu.ahpu.tpc.framework.web.dao.admin;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.edu.ahpu.tpc.framework.web.model.admin.UnteckBusiLog;

import cn.edu.ahpu.common.dao.hibernate4.HibernateBaseDaoImpl;
import cn.edu.ahpu.common.dao.support.Pagination;

/**
 * @author zhuzengpeng
 * @date 2013-8-9 上午10:05:09
 */
@Repository
public class BusiLogDao extends HibernateBaseDaoImpl<UnteckBusiLog, Integer>{

	public Pagination<Object> getBusiLogList(Integer start, Integer limit, String optType, String optUser, String startDate, String endDate) throws ParseException {
		List<String> listPropertyNames = new ArrayList<String>();
		List<Object> listValues = new ArrayList<Object>();
		StringBuffer rowHql = new StringBuffer("select new Map(t.logId as logId, t.optType as optType, t.optContent as optContent," +
				"t.optUser as optUser, t.optTime as optTime, b.userName as userName) " +
				"from UnteckBusiLog t,User b where t.optUser = b.id");
		StringBuffer countHql = new StringBuffer("select count(t.logId) from UnteckBusiLog t,User b where t.optUser = b.id");		
		if(StringUtils.hasText(optUser)) {
			listPropertyNames.add("optUser");
			listValues.add("%" + optUser + "%");
			rowHql.append(" and b.userName like :optUser");
			countHql.append(" and b.userName like :optUser");
		}
		if(StringUtils.hasText(optType)) {
			listPropertyNames.add("optType");
			listValues.add(optType);
			rowHql.append(" and t.optType = :optType");
			countHql.append(" and t.id.acctNo = :optType");
		}	
		if(StringUtils.hasText(startDate)) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = df.parse(startDate);
			listPropertyNames.add("startDate");
			listValues.add(date);
			rowHql.append(" and t.optTime >= :startDate");
			countHql.append(" and t.optTime >= :startDate");
		}
		if(StringUtils.hasText(endDate)) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = df.parse(endDate);
			listPropertyNames.add("endDate");
			listValues.add(date);
			rowHql.append(" and t.optTime <= :endDate");
			countHql.append(" and t.optTime <= :endDate");
		}		
		rowHql.append(" order by t.optTime desc");
		String[] propertyNames = (String[]) listPropertyNames.toArray(new String[listPropertyNames.size()]);
		Object[] values = listValues.toArray();
		return this.findPageByHQL(rowHql.toString(), countHql.toString(), start, limit, propertyNames, values);
	}
}
