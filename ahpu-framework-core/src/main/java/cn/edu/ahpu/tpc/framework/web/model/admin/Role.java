package cn.edu.ahpu.tpc.framework.web.model.admin;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cn.edu.ahpu.common.model.BaseEntity;
/**
 * Spring Security权限的扩展，对于角色需要实现GrantedAuthority接口
 *
 * @datetime 2010-8-8 下午08:01:43
 * @author libinsong1204@gmail.com
 */
@Entity
@Table(name="TPC_ROLE")
public class Role extends BaseEntity implements GrantedAuthority {
	private static final long serialVersionUID = 1L;
	//~ Instance fields ================================================================================================
    private String code;
    private String name;
    private String descn;
    private String enabled;
    @Transient
    private String enabled_Name;
	
    @ManyToMany(mappedBy="authorities", fetch=FetchType.LAZY)
    private Set<User> users = new HashSet<User>();
    
    @ManyToMany(targetEntity=Resource.class,fetch=FetchType.LAZY)
    @JoinTable(name="TPC_ROLE_RESOURCE",
            joinColumns={@JoinColumn(name="ROLE_ID")},
            inverseJoinColumns={@JoinColumn(name="RESOURCE_ID")}
    )
    private Set<Resource> resources = new HashSet<Resource>();
    
    //角色关联指定资源的个数，用于判断资源是否与角色关联
	@Transient
	private Long counter = 0L;

    //~ Constructors ===================================================================================================
    public Role() {
    }
    
    public Role(long id, String code, String name) {
        Assert.hasText(code, "A granted authority textual representation is required");
        this.setId(id);
        this.code = code;
        this.name = name;
    }

    //~ Methods ========================================================================================================
    public String getAuthority() {
		return this.code;
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

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public Long getCounter() {
		return counter;
	}

	public void setCounter(Long counter) {
		this.counter = counter;
	}

	@JsonIgnore
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	@JsonIgnore
	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	public String getEnabled_Name() {
		return enabled_Name;
	}

	public void setEnabled_Name(String enabledName) {
		enabled_Name = enabledName;
	}

	public int hashCode() {
        return this.code.hashCode();
    }

	public String toString() {
        return this.code + "," + this.name;
    }
    
    public boolean equals(Object obj) {
        if (obj instanceof String) {
            return obj.equals(this.code);
        }

        if (obj instanceof GrantedAuthority) {
            GrantedAuthority attr = (GrantedAuthority) obj;

            return this.code.equals(attr.getAuthority());
        }

        return false;
    }
}