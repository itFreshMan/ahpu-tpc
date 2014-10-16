package cn.edu.ahpu.tpc.framework.core.util;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.util.StringUtils;

import cn.edu.ahpu.tpc.framework.core.cache.CacheNames;
import cn.edu.ahpu.tpc.framework.core.spring.SpringApplicationContextHolder;
import cn.edu.ahpu.tpc.framework.web.model.admin.DictEntry;
import cn.edu.ahpu.tpc.framework.web.service.admin.DictEntryService;

/**
 * 
 * @author  libinsong1204@gmail.com
 * @date    2011-1-18 上午11:03:34
 * @version 
 */
public class DictionaryHolder {
	/** 缓存业务字典项  */
	private static Logger logger = LoggerFactory.getLogger(DictionaryHolder.class);
	
	private static DictEntryService dictEntryService;
	
	private static CacheManager cacheManager;
	
	public static void putDictionaries(String dictTypeCode, List<DictEntry> dictionaries) {
		if(cacheManager == null)
			cacheManager = SpringApplicationContextHolder.getBean(CacheManager.class);
		if(dictionaries != null)
			cacheManager.getCache(CacheNames.DICT_CACHE_NAME).put(dictTypeCode, dictionaries);
	}
	
	@SuppressWarnings("unchecked")
	public static List<DictEntry> getDictionaries(String dictTypeCode) {
		if(cacheManager == null)
			cacheManager = SpringApplicationContextHolder.getBean(CacheManager.class);
		ValueWrapper vw =  cacheManager.getCache(CacheNames.DICT_CACHE_NAME).get(dictTypeCode);
		if(vw==null){
			return null;
		}else{
			return (List<DictEntry>)vw.get();
		}
	}
	
	public static void cleanDictionaries(String dictTypeCode) {
		if(cacheManager == null)
			cacheManager = SpringApplicationContextHolder.getBean(CacheManager.class);
		cacheManager.getCache(CacheNames.DICT_CACHE_NAME).evict(dictTypeCode);
	}
	
	/**
	 * 转换业务字典项编码对应业务字典项值，设置给field_Name属性。
	 * 
	 * @param <T> 实体
	 * @param list 待装换实体list集合
	 * @param dictTypeId 业务字典项
	 * @param getMethod 字段get方法
	 */
	@SuppressWarnings("unchecked")
	public static <T> void transfercoder(List<T> list, String dictTypeCode, String getMethod) {
		if(dictEntryService == null)
			dictEntryService = SpringApplicationContextHolder.getBean(DictEntryService.class);
		if(cacheManager == null)
			cacheManager = SpringApplicationContextHolder.getBean(CacheManager.class);
		
		List<DictEntry> dictionaries = null;
		ValueWrapper vw =  cacheManager.getCache(CacheNames.DICT_CACHE_NAME).get(dictTypeCode);
		if(vw != null)
			dictionaries = (List<DictEntry>) vw.get();
		if(dictionaries == null) {
			logger.info("relead {} data", dictTypeCode);
			dictionaries = dictEntryService.queryDictionarys(dictTypeCode);
			cacheManager.getCache(CacheNames.DICT_CACHE_NAME).put(dictTypeCode, dictionaries);
		}
		
		String[] args = getMethod.split("\\.");
		int len = args.length;
		getMethod = args[(len-1)];
		for(T t : list) {
			try {
				Object entity = t;
				for(int i=0; i<(len-1); i++) {
					String name = org.apache.commons.lang.StringUtils.capitalize(args[i]);
					Method method = t.getClass().getMethod("get" + name);
					entity = method.invoke(t);
				}
				Method method = entity.getClass().getMethod(getMethod);
				String obj = String.valueOf(method.invoke(entity));
				String value = "";
				for(DictEntry dic : dictionaries) {
					if(dic.getCode().equals(obj)) {
						value = dic.getName();
						break;
					}
				}
				if(StringUtils.hasText(value)) {
					method = entity.getClass().getMethod(getMethod.replace("get", "set")+"_Name", String.class);
					method.invoke(entity, value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> void transfercoderForMap(List<Map<String, Object>> list, String dictTypeCode, String field) {
		if(dictEntryService == null)
			dictEntryService = SpringApplicationContextHolder.getBean(DictEntryService.class);
		if(cacheManager == null)
			cacheManager = SpringApplicationContextHolder.getBean(CacheManager.class);
		
		List<DictEntry> dictionaries =  null;
		ValueWrapper vw =  cacheManager.getCache(CacheNames.DICT_CACHE_NAME).get(dictTypeCode);
		if(vw != null)
			dictionaries = (List<DictEntry>) vw.get();
		if(dictionaries == null) {
			logger.info("relead {} data", dictTypeCode);
			dictionaries = dictEntryService.queryDictionarys(dictTypeCode);
			cacheManager.getCache(CacheNames.DICT_CACHE_NAME).put(dictTypeCode, dictionaries);
		}
		
		for(Map<String, Object> map : list) {
			String key = String.valueOf(map.get(field));
			String value = "";
			for(DictEntry dic : dictionaries) {
				if(dic.getCode().equals(key)) {
					value = dic.getName();
					break;
				}
			}
			
			map.put(field + "_Name", value);
		}
	}
}
