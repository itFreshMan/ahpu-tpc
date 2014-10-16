package cn.edu.ahpu.tpc.framework.web.model.admin;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cn.edu.ahpu.common.model.BaseEntity;

/**
 *
 * @datetime 2010-8-9 下午05:23:33
 * @author libinsong1204@gmail.com
 */
@Entity
@Table(name="TPC_RESOURCE")
public class Resource extends BaseEntity {
	private static final long serialVersionUID = 1L;
	//~ Instance fields ================================================================================================
	private String name;
	private String type;
	@Transient
	private String type_Name;
	private int priority;
	private String action;
	private String descn;
	private String module;
	@Transient
	private String module_Name;
	private String enabled;
	@Transient
	private String enabled_Name;
	
	@ManyToMany(mappedBy="resources", fetch=FetchType.LAZY)
	private Set<Role> roles = new HashSet<Role>();
	
	//资源关联指定角色的个数，用于判断角色是否与资源关联
	@Transient
	private Long counter = 0L;

	//~ Methods ========================================================================================================
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public Long getCounter() {
		return counter;
	}
	public void setCounter(Long counter) {
		this.counter = counter;
	}
	@JsonIgnore
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getType_Name() {
		return type_Name;
	}
	public void setType_Name(String typeName) {
		type_Name = typeName;
	}
	public String getEnabled_Name() {
		return enabled_Name;
	}
	public void setEnabled_Name(String enabledName) {
		enabled_Name = enabledName;
	}
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Resource other = (Resource) obj;
		if (this.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!this.getId().equals(other.getId()))
			return false;
		return true;
	}

	public static enum ResourceType {
		METHOD("method"), URL("url"), MENU("menu");
		
		private ResourceType(String type){   
           this.type = type;
        }   
		
		private String type;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String toString(){
            return type;   
		}   
	}
}
