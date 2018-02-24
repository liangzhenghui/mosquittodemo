package common.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import common.model.User;
import common.util.SpringUtil;

/**
 * @author liangzhenghui
 * @date Aug 25, 2013    12:26:16 AM
 */
public class UserManager{

	private UserService userService;
	private RoleService roleService;
	
	public static final UserManager getInstance() {
		return (UserManager)SpringUtil.getBean("userManager");
	}
	/**
	 * @param username
	 * @param password
	 */
	public Boolean isExists(String userid, String password) {
		return userService.isExists(userid, password);
	}
	
	public User getLoginUser(HttpServletRequest req){
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute("user");
		return user;
	}
	
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

}
