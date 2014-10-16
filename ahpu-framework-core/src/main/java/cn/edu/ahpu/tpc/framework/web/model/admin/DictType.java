package cn.edu.ahpu.tpc.framework.web.model.admin;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cn.edu.ahpu.common.model.BaseEntity;

/**
*
* @datetime 2010-8-20 下午10:09:06
* @author libinsong1204@gmail.com
*/
@Entity
@Table(name="TPC_DICT_TYPE")
public class DictType extends BaseEntity {
	private static final long serialVersionUID = 1L;
	//~ Instance fields ================================================================================================
	private String module;
	@Transient
	private String module_Name;
	private String code;
	private String name;
	private String memo;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "dictType", fetch = FetchType.LAZY)   
	@OrderBy("orderIndex ASC")
	private List<DictEntry> dictEntries = new ArrayList<DictEntry>();
	
	//~ Methods ========================================================================================================
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getModule_Name() {
		return module_Name;
	}
	public void setModule_Name(String module_Name) {
		this.module_Name = module_Name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@JsonIgnore
	public List<DictEntry> getDictEntries() {
		return dictEntries;
	}
	public void setDictEntries(List<DictEntry> dictEntries) {
		this.dictEntries = dictEntries;
	}
}