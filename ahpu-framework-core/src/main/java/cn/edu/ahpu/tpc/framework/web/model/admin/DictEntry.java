package cn.edu.ahpu.tpc.framework.web.model.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cn.edu.ahpu.common.model.BaseEntity;

/**
*
* @datetime 2010-8-20 下午10:09:06
* @author libinsong1204@gmail.com
*/
@Entity
@Table(name="TPC_DICT_ENTRY")
public class DictEntry extends BaseEntity {
	private static final long serialVersionUID = 1L;
	//~ Instance fields ================================================================================================
	private String code;
	private String name;
	@Column(name="ORDER_INDEX")
	private Long orderIndex;
	private char enabled;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity=DictType.class)   
	@JoinColumn(name="DICT_TYPE_ID", nullable=false)      
	private DictType dictType;

	//~ Methods ========================================================================================================
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

	public Long getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Long orderIndex) {
		this.orderIndex = orderIndex;
	}

	public char getEnabled() {
		return enabled;
	}

	public void setEnabled(char enabled) {
		this.enabled = enabled;
	}

	@JsonIgnore
	public DictType getDictType() {
		return dictType;
	}

	public void setDictType(DictType dictType) {
		this.dictType = dictType;
	}	
}
