package cn.edu.ahpu.tpc.framework.web.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.ahpu.tpc.framework.core.util.DictionaryHolder;
import cn.edu.ahpu.tpc.framework.core.util.ResponseData;
import cn.edu.ahpu.tpc.framework.web.controller.BaseController;
import cn.edu.ahpu.tpc.framework.web.model.admin.DictEntry;
import cn.edu.ahpu.tpc.framework.web.model.admin.DictType;
import cn.edu.ahpu.tpc.framework.web.service.admin.DictEntryService;
import cn.edu.ahpu.tpc.framework.web.service.admin.DictTypeService;

import cn.edu.ahpu.common.dao.support.Pagination;
import cn.edu.ahpu.common.dao.support.PaginationRequest;

/**
 * 业务字典Controller
 * 
 * @datetime 2010-8-19 下午10:01:44
 * @author libinsong1204@gmail.com
 */
@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {

	//~ Instance fields ================================================================================================
	@Autowired
	private DictTypeService dictTypeService;
	@Autowired
	private DictEntryService dictEntryService;
	
	//~ Methods ========================================================================================================
	@RequestMapping("/index")
	public String index(){
		return "admin/dict";
	}
	
	@RequestMapping("/indexNew")
	public String indexNew(){
		return "admin/dictMgr";
	}
	
	/**
	 * 字典管理，查询字典业务类型信息
	 * 
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/pageQueryDictTypes")
	@ResponseBody
	public Pagination<DictType> pageQueryDictTypes(@RequestParam("start")int offset, 
			@RequestParam("limit")int limit, DictType businType, @RequestParam(required = false)String sort, 
			@RequestParam(required = false)String dir) {
		PaginationRequest<DictType> paginationRequest = new PaginationRequest<DictType>(offset/limit, offset, limit);
		
		if(StringUtils.hasText(sort) && StringUtils.hasText(dir))
			paginationRequest.addOrder(sort, dir);
		
		if(StringUtils.hasText(businType.getModule()))
			paginationRequest.addLikeCondition("module", "%" + businType.getModule() + "%");
		
		if(StringUtils.hasText(businType.getName()))
			paginationRequest.addLikeCondition("name", "%" + businType.getName() + "%");
		
		Pagination<DictType> page = dictTypeService.findPage(paginationRequest);
		DictionaryHolder.transfercoder(page.getResult(), "CORE.MODULE", "getModule");
		
		return page;
	}
	
	/**
	 * 保存业务字典类型, 只接受POST请求
	 * 
	 * @param resource
	 * @return ResponseData
	 */
	@RequestMapping(value="/insertDictType", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData insertDictType(DictType businType) {
		dictTypeService.insertEntity(businType);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 更新字典业务类型, 只接受POST请求
	 * 
	 * @param resource
	 * @return ResponseData
	 */
	@RequestMapping(value="/updateDictType", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData updateDictType(DictType businType) {
		dictTypeService.updateEntity(businType);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 加载字典业务类型, 只接受POST请求
	 * 
	 * @param resource
	 * @return ResponseData
	 */
	@RequestMapping(value="/loadDictType", method=RequestMethod.POST)
	@ResponseBody
	public DictType loadDictType(Long id) {
		return dictTypeService.getEntity(id);
	}
	
	/**
	 * 删除字典业务类型, 只接受POST请求
	 * 
	 * @param resource
	 * @return ResponseData
	 */
	@RequestMapping(value="/deleteDictType", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData deleteDictType(Long id) {
		DictionaryHolder.cleanDictionaries(dictTypeService.getEntity(id).getCode());
		dictTypeService.deleteEntity(id);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	@RequestMapping(value="/deleteDictTypes", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData deleteDictTypes(Long[] ids) {
		for(Long id : ids){
			DictionaryHolder.cleanDictionaries(dictTypeService.getEntity(id).getCode());
			dictTypeService.deleteEntity(id);
		}
		return ResponseData.SUCCESS_NO_DATA;
	}

	//---------------------------------------字典项-------------------------------------------
	
	/**
	 * 根据父字典项ID和字典业务类型ID，查找所有子父字典项信息
	 * 
	 * @param dictTypeId 
	 * @return 所有子字典项
	 */
	@RequestMapping("/queryDictEntries")
	@ResponseBody
	public List<DictEntry> queryDictEntries(String dictTypeCode) {
		return dictEntryService.queryDictionarys(dictTypeCode);
	}
	
	/**
	 * 插入字典项信息
	 * 
	 * @param dictionary
	 * @return
	 */
	@RequestMapping(value="/insertDictEntry", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData insertDictEntry(DictEntry dictionary) {
		dictionary.setEnabled('Y');
		dictEntryService.insertEntity(dictionary);
		
		DictType dictionaryType = dictTypeService.getEntity(dictionary.getDictType().getId());
		DictionaryHolder.cleanDictionaries(dictionaryType.getCode());
		
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 更新字典项信息
	 * 
	 * @param dictionary
	 * @return
	 */
	@RequestMapping(value="/updateDictEntry", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData updateDictEntry(DictEntry dictionary) {
		dictionary.setEnabled('Y');
		dictEntryService.updateEntity(dictionary);
		
		DictType dictionaryType = dictTypeService.getEntity(dictionary.getDictType().getId());
		DictionaryHolder.cleanDictionaries(dictionaryType.getCode());
		
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 加载字典项信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/loadDictEntry", method=RequestMethod.POST)
	@ResponseBody
	public DictEntry loadDictEntry(Long id) {
		return dictEntryService.getEntity(id);
	}
	
	/**
	 * 删除字典项
	 * 
	 * @param parentId 父节点
	 * @param childIds 所有子节点ID有逗号连接的字符串
	 * @return
	 */
	@RequestMapping("/deleteDictEntry")
	@ResponseBody
	public ResponseData deleteDictEntry(Long id) {
		DictEntry dictionary = dictEntryService.getEntity(id);
		DictType dictionaryType = dictTypeService.getEntity(dictionary.getDictType().getId());
		DictionaryHolder.cleanDictionaries(dictionaryType.getCode());
		
		dictEntryService.deleteEntity(id);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 删除一个或者多个字典项, 只接受POST请求
	 * 
	 * @param ids
	 * @return ResponseData
	 */
	@RequestMapping(value="/deleteDictEntries", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData deleteDictEntries(Long[] ids) {
		for(Long id:ids){
			DictEntry dictionary = dictEntryService.getEntity(id);
			DictType dictionaryType = dictTypeService.getEntity(dictionary.getDictType().getId());
			DictionaryHolder.cleanDictionaries(dictionaryType.getCode());
			
			dictEntryService.deleteEntity(id);
		}
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 查询字典类型的字典属性
	 * 
	 * @param dictTypeId 字典类型ID
	 * @return
	 */
	@RequestMapping("/pageQueryDictEntries")
	@ResponseBody
	public Pagination<DictEntry> pageQueryDictEntries(@RequestParam("start")int offset, 
			@RequestParam("limit")int limit, Long dictTypeId, @RequestParam(required = false)String sort, 
			@RequestParam(required = false)String dir) {
		PaginationRequest<DictEntry> paginationRequest = new PaginationRequest<DictEntry>(offset/limit, offset, limit);
		if(dictTypeId != null)
			paginationRequest.addCondition("dictType.id", dictTypeId);
		
		if(StringUtils.hasText(sort) && StringUtils.hasText(dir))
			paginationRequest.addOrder(sort, dir);
		
		Pagination<DictEntry> page = dictEntryService.findPage(paginationRequest);
		return page;
	}
}
