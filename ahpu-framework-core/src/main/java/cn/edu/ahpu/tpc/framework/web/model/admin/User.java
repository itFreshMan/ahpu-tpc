package cn.edu.ahpu.tpc.framework.web.model.admin;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cn.edu.ahpu.common.model.BaseEntity;

@Entity
@Table(name="TPC_USER")
public class User extends BaseEntity implements UserDetails{
	private static final long serialVersionUID = 1L;
	//用于判断角色是否与用户关联
	@Transient
	private Long counter = 2L;
	public Long getCounter() {
		return counter;
	}
	public void setCounter(Long counter) {
		this.counter = counter;
	}
	
	@Column(name="USER_NAME")
	private String userName; 
	@Column(name="USER_CODE")
	private String userCode;   
	@Column(name="PASSWORD")
	private String password;   
	@Column(name="SALT")
	private String salt;
	@Column(name="PASSWD_INVAL_TIME")
	private Date passwdInvalTime;
	@Column(name="AREA_ID")
	private Long areaId;
	@Column(name="ORG_ID")
	private Long orgId;
	@Formula("(select org.NAME from TPC_ORGANIZATION org where org.ID= ORG_ID)")
	private String orgName;
	@Formula("(select org.ORGSEQ from TPC_ORGANIZATION org where org.ID= ORG_ID)")
	private String orgSeq;
	@Column(name="LOCK_TIME")
	private Date lockTime;	
	@Column(name="LAST_LOGIN_TIME")
	private Date lastLoginTime;
	@Column(name="ERROR_COUNT")
	private int errorCount;
	private String email;
	private char gender;
	@Transient
	private String gender_Name;
	private String birthday;   
	@Column(name="PHONE_NO")
	private String phoneNo;
	@Column(name="MPHONE_NO")
	private String mPhoneNo;
	
	@ManyToMany(targetEntity=Role.class,fetch=FetchType.LAZY)
    @JoinTable(name="TPC_USER_ROLE",
            joinColumns={@JoinColumn(name="USER_ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID")}
    )
    @org.hibernate.annotations.LazyCollection(
    	org.hibernate.annotations.LazyCollectionOption.EXTRA
    )
    private Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
	
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public Date getPasswdInvalTime() {
		return passwdInvalTime;
	}
	public void setPasswdInvalTime(Date passwdInvalTime) {
		this.passwdInvalTime = passwdInvalTime;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public Date getLockTime() {
		return lockTime;
	}
	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public int getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOrgSeq() {
		return orgSeq;
	}
	public void setOrgSeq(String orgSeq) {
		this.orgSeq = orgSeq;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public String getGender_Name() {
		return gender_Name;
	}
	public void setGender_Name(String gender_Name) {
		this.gender_Name = gender_Name;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getmPhoneNo() {
		return mPhoneNo;
	}
	public void setmPhoneNo(String mPhoneNo) {
		this.mPhoneNo = mPhoneNo;
	}
	@JsonIgnore
	@Override
	public Set<GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	//------------------UserDetails接口方法--------------------------------------
	@JsonIgnore
	public String getUsername() {
		return this.userCode;
	}
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}
}
