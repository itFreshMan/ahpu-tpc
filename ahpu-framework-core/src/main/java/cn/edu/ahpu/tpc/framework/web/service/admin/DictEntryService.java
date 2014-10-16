package cn.edu.ahpu.tpc.framework.web.service.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.ahpu.tpc.framework.core.util.DictionaryHolder;
import cn.edu.ahpu.tpc.framework.web.dao.admin.DictEntryDao;
import cn.edu.ahpu.tpc.framework.web.dao.admin.DictTypeDao;
import cn.edu.ahpu.tpc.framework.web.model.admin.DictEntry;
import cn.edu.ahpu.tpc.framework.web.model.admin.DictType;

import cn.edu.ahpu.common.dao.hibernate4.HibernateBaseDao;
import cn.edu.ahpu.common.dao.service.BaseServiceImpl;

/**
 *
 * @datetime 2010-8-21 上午08:09:41
 * @author libinsong1204@gmail.com
 */
@Service
public class DictEntryService extends BaseServiceImpl<DictEntry, Long> {
	//~ Instance fields ================================================================================================
	@Autowired
	private DictEntryDao dictEntryDao;
	@Autowired
	private DictTypeDao dictTypeDao;
	
	//~ Methods ========================================================================================================
	@Override
	public HibernateBaseDao<DictEntry, Long> getHibernateBaseDao() {
		return this.dictEntryDao;
	}
	
	/**
	 * 根据字典业务类型，查询指定字典业务类型的所有子字典项
	 * 为了提高效率，增加缓存处理。
	 * 
	 * @param dictTypeId
	 * @return List<Dictionary>
	 */
	@Transactional
	public List<DictEntry> queryDictionarys(String dictTypeCode) {
		List<DictEntry> dictionaries = DictionaryHolder.getDictionaries(dictTypeCode);
		if(dictionaries == null) {
			List<DictEntry> newDictionaries = new ArrayList<DictEntry>();
			DictType dictionaryType = this.dictTypeDao.findByNamedParam("code", dictTypeCode).get(0);
			if(dictionaryType != null) {
				dictionaries = dictionaryType.getDictEntries();
				for(int i = 0;i<dictionaries.size();i++){
					DictEntry dictEntry = dictionaries.get(i);
					char enabled = dictEntry.getEnabled();
					if('Y' == enabled){
						newDictionaries.add(dictEntry);
					}
				}
			}
			DictionaryHolder.putDictionaries(dictTypeCode, newDictionaries);
			return newDictionaries;
		}else{
			return dictionaries;
		}
	}
}
