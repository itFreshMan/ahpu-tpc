package cn.edu.ahpu.common.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @datetime 2010-8-12 下午05:15:55
 * @author libinsong1204@gmail.com
 */
@MappedSuperclass
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ID")
    @GeneratedValue(generator = "tableGenerator")     
	@GenericGenerator(name = "tableGenerator", strategy="cn.edu.ahpu.common.dao.key.SequenceGenerator")
    private Long id;
	
	@Column(name="CREATE_USER", updatable=false)
	private String createUser;
	@Column(name="UPDATE_USER", insertable=false)
	private String updateUser;
	
	@Temporal(TemporalType.TIMESTAMP)//不用set,hibernate会自动把当前时间写入  
    @Column(name="CREATE_TIME", updatable = false)  
	private Date createTime;
	
	@Temporal(TemporalType.TIMESTAMP)//不用set,hibernate会自动把当前时间写入  
	@Column(name="UPDATE_TIME", insertable = false)  
	private Date updateTime;
	
	@Column(name="DEL_FLAG")
	private Integer delFlag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	
}
