package cn.edu.ahpu.tpc.framework.web.model.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import cn.edu.ahpu.common.model.BaseEntity;

@Entity
@Table(name="TPC_ORGANIZATION")
public class Organization extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Column(name="level_")
	private int level;
	private String name;
	@Column(name="AREACODE")
	private String areaCode;
	private long parentId;
	private int theSort;
	private String orgSeq;
	private String descn;
	
	@JsonProperty("text")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setText(String text) {
		if(StringUtils.hasText(text))
			this.name = text;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public String getOrgSeq() {
		return orgSeq;
	}
	public void setOrgSeq(String orgSeq) {
		this.orgSeq = orgSeq;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public int getTheSort() {
		return theSort;
	}
	public void setTheSort(int theSort) {
		this.theSort = theSort;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
}