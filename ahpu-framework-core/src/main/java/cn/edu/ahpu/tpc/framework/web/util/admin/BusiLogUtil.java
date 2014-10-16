package cn.edu.ahpu.tpc.framework.web.util.admin;

import java.util.Date;

import cn.edu.ahpu.tpc.framework.core.spring.SpringApplicationContextHolder;
import cn.edu.ahpu.tpc.framework.web.dao.admin.BusiLogDao;
import cn.edu.ahpu.tpc.framework.web.model.admin.UnteckBusiLog;

/**
 * 业务日志工具类
 * @author zhuzengpeng
 * @date 2013-8-9 上午10:04:37
 */
public class BusiLogUtil {

	private static BusiLogDao busiLogDao = SpringApplicationContextHolder.getBean(BusiLogDao.class);
	
	/**
	 * 
	 * @param optType 操作类型 1:增加  2:修改  3:删除 4:查询
	 * @param optUser 操作人
	 * @param optTime 操作时间
	 * @param optContent 操作内容,格式：【系统管理员】在【项目管理模块】增加了【教育局】
	 */
	public static void saveBusiLog(Integer optType, Long optUser, Date optTime, String optContent) {
		UnteckBusiLog busiLog = new UnteckBusiLog();
		busiLog.setOptType(optType);
		busiLog.setOptUser(optUser);
		busiLog.setOptTime(optTime);
		busiLog.setOptContent(optContent);
		busiLogDao.save(busiLog);
	}
}
