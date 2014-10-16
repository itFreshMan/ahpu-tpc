package cn.edu.ahpu.tpc.framework.web.controller.admin;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.ahpu.tpc.framework.core.util.DictionaryHolder;
import cn.edu.ahpu.tpc.framework.core.util.ResponseData;
import cn.edu.ahpu.tpc.framework.core.util.SecurityContextUtil;
import cn.edu.ahpu.tpc.framework.web.controller.BaseController;
import cn.edu.ahpu.tpc.framework.web.model.admin.Role;
import cn.edu.ahpu.tpc.framework.web.model.admin.User;
import cn.edu.ahpu.tpc.framework.web.service.admin.UserService;

import cn.edu.ahpu.common.dao.support.Pagination;
import cn.edu.ahpu.common.dao.support.PaginationRequest;

/**
 * 用户Controller
 *
 * @datetime 2010-8-8 下午04:47:03
 * @author libinsong1204@gmail.com
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	//~ Instance fields ================================================================================================
	@Autowired
	private UserService userService;
	
	//~ Methods ========================================================================================================
	@RequestMapping("/index")
	public String index(){
		return "admin/user";
	}
	
	@RequestMapping("/indexNew")
	public String indexNew(){
		return "admin/userMgr";
	}
	
	/**
	 * 用户管理，查询用户信息，按照用户优先级降序排序
	 * 
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/pageQueryUsers")
	@ResponseBody
	public Pagination<User> pageQueryUsers(@RequestParam("start")int offset, 
			@RequestParam("limit")int limit, User user, @RequestParam(required = false)String sort, 
			@RequestParam(required = false)String dir) {
		PaginationRequest<User> paginationRequest = new PaginationRequest<User>(offset/limit, offset, limit);
		
		if(StringUtils.hasText(sort) && StringUtils.hasText(dir))
			paginationRequest.addOrder(sort, dir);
		
		if(StringUtils.hasText(user.getUserName()))
			paginationRequest.addLikeCondition("userName", "%" + user.getUserName() + "%");
		
		if(StringUtils.hasText(user.getUserCode()))
			paginationRequest.addLikeCondition("userCode", "%" + user.getUserCode() + "%");
		if(user.getOrgId() != null)
			paginationRequest.addCondition("orgId", user.getOrgId());
		paginationRequest.addCondition("delFlag", 0);
		Pagination<User> page = userService.findPage(paginationRequest);
		DictionaryHolder.transfercoder(page.getResult(), "CORE.GENDER", "getGender");
		return page;
	}
	
	/**
	 * 保存用户, 只接受POST请求
	 * 
	 * @param user
	 * @return ResponseData
	 */
	@RequestMapping(value="/insertUser", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData insertUser(User user) {
		//初始密码：000000
		String salt = SecurityContextUtil.generateRandomSalt();
		user.setSalt(salt);
		user.setPassword(SecurityContextUtil.encodePassword("000000", salt));
		user.setDelFlag(0);
		userService.insertEntity(user);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 注册用户。
	 * 
	 * @param user
	 * @return ResponseData
	 */
	@RequestMapping(value="/registerUser", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData registerUser(User user) {
		user.setOrgId(1L);
		String salt = SecurityContextUtil.generateRandomSalt();
		user.setSalt(salt);
		user.setPassword(SecurityContextUtil.encodePassword(user.getPassword(), salt));
		Long id = userService.insertEntity(user);
		
		userService.bindRole(id, new Long[]{2L});
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	* 1.主页注册-检测用户登陆账号是否重复<br>
	* 2.用户管理界面-用于检测添加的新用户及更新用户时,用户登陆账号是否重复<br>
	* @param userCode
	* @param id 用于用户管理界面
	* @return
	*/
	@RequestMapping(value="/isExistUser", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData isExistUser(String userCode, @RequestParam(required=false)Long id) {
		String[] propertyNames = {"userCode", "delFlag"};
		Object[] values = {userCode, 0};
		List<User> list = userService.findByNamedParam(propertyNames, values);

		if(id == null){//register
			if(list.size() > 0)
				return ResponseData.SUCCESS_NO_DATA;
			else
				return ResponseData.FAILED_NO_DATA;
		}
		
		if(id == -1){//add user
			if(list.size() > 0)
				return ResponseData.SUCCESS_NO_DATA;//repeated usercode
			else
				return ResponseData.FAILED_NO_DATA;
		}else{//update user
			if(list.size() > 0 && list.get(0).getId().longValue() != id.longValue())
				return ResponseData.SUCCESS_NO_DATA;
			else if(list.size() > 1)
				return ResponseData.SUCCESS_NO_DATA;
			else
				return ResponseData.FAILED_NO_DATA;
		}
	}
	
	/**
	 * 更新用户, 只接受POST请求
	 * 
	 * @param user
	 * @return ResponseData
	 */
	@RequestMapping(value="/updateUser", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData updateUser(User user) {
		User oldUser = userService.getEntity(user.getId());
		oldUser.setUserName(user.getUserName());
		oldUser.setUserCode(user.getUserCode());
		oldUser.setOrgId(user.getOrgId());
		oldUser.setGender(user.getGender());
		oldUser.setPhoneNo(user.getPhoneNo());
		oldUser.setmPhoneNo(user.getmPhoneNo());
		oldUser.setEmail(user.getEmail());
		oldUser.setBirthday(user.getBirthday());
		userService.updateEntity(oldUser);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 更新当前登录用户信息, 只接受POST请求
	 * 
	 * @param user
	 * @return ResponseData
	 */
	@RequestMapping(value="/updateCurrentUser", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData updateCurrentUser(User user) {
		Long id = SecurityContextUtil.getCurrentUser().getId();
		String userCode = SecurityContextUtil.getLoginUserCode();
		user.setId(id);
		User oldUser = userService.getEntity(id);
		oldUser.setUserName(user.getUserName());
		oldUser.setUserCode(user.getUserCode());
		oldUser.setGender(user.getGender());
		oldUser.setEmail(user.getEmail());
		oldUser.setPhoneNo(user.getPhoneNo());
		oldUser.setmPhoneNo(user.getmPhoneNo());
		oldUser.setBirthday(user.getBirthday());
		oldUser.setUpdateUser(userCode);
		oldUser.setUpdateTime(new Date());
		userService.updateEntity(oldUser);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 更新当前登录用户密码, 只接受POST请求
	 * 
	 * @param password
	 * @return ResponseData
	 */
	@RequestMapping(value="/updateCurrentUserPasswd", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData updateCurrentUserPasswd(String password) {
		Long id = SecurityContextUtil.getCurrentUser().getId();
		User user = userService.getEntity(id);
		String salt = SecurityContextUtil.generateRandomSalt();
		user.setSalt(salt);
		user.setPassword(SecurityContextUtil.encodePassword(password, salt));
		userService.updateEntity(user);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 加载用户, 只接受POST请求
	 * 
	 * @param id
	 * @return User
	 */
	@RequestMapping(value="/loadUser", method=RequestMethod.POST)
	@ResponseBody
	public User loadUser(Long id) {
		return userService.getEntity(id);
	}
	
	/**
	 * 加载当前登录用户, 只接受POST请求
	 * 
	 * @param id
	 * @return User
	 */
	@RequestMapping(value="/loadCurrentUser", method=RequestMethod.POST)
	@ResponseBody
	public User loadCurrentUser() {
		Long id = SecurityContextUtil.getCurrentUser().getId();
		return userService.getEntity(id);
	}
	
	/**
	 * 删除用户, 只接受POST请求
	 * 
	 * @param id
	 * @return ResponseData
	 */
	@RequestMapping(value="/deleteUsers", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData deleteUsers(Long[] ids) {
		userService.deleteUsers(ids);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 查询用户关联的所有角色, 只接受POST请求
	 * 
	 * @param operatorId 用户ID
	 * @return List<Role> 用户关联的所有角色
	 */
	@RequestMapping(value="/queryRoles4User", method=RequestMethod.POST)
	@ResponseBody
	public Set<GrantedAuthority> queryRoles4User(Long operatorId) {
		return userService.queryRoles4User(operatorId);
	}
	
	/**
	 * 查询所有用户信息，同时关联查询指定的角色信息
	 *
	 * @param operatorId
	 */
	@RequestMapping(value="/pageQueryRoles4User", method=RequestMethod.POST)
	@ResponseBody
	public Pagination<Role> pageQueryRoles4User(@RequestParam("start")int offset, 
			@RequestParam("limit")int limit, Long operatorId)  {
		PaginationRequest<Role> pageRequest = new PaginationRequest<Role>(offset/limit, offset, limit);
		return userService.pageQueryRoles4User(pageRequest, operatorId);
	}
	
	/**
	 * 给用户绑定角色, 只接受POST请求
	 * 
	 * @param operatorId 用户ID
	 * @param ids 角色ID
	 * @return ResponseData
	 */
	@RequestMapping(value="/bindRole", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData bindRole(Long operatorId, Long[] ids) {
		userService.bindRole(operatorId, ids);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 给用户取消绑定角色, 只接受POST请求
	 * 
	 * @param operatorId 用户ID
	 * @param ids 角色ID
	 * @return ResponseData
	 */
	@RequestMapping(value="/unBindRole", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData unBindRole(Long operatorId, Long[] ids) {
		userService.unBindRole(operatorId, ids);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 重置用户密码
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/resetPwd", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData resetPwd(Long userId) {
		userService.resetPwd(userId);
		return ResponseData.SUCCESS_NO_DATA;
	}
}
