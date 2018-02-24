package common.service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;

import common.dao.JdbcService;
import common.model.Role;
import common.model.User;
import common.model.UserRole;
import common.util.SpringUtil;
import common.util.UserUtil;

/**
 * @author liangzhenghui
 * @date Aug 24, 2013 10:08:08 PM
 */
public class UserService {

	private JdbcService jdbcService;
	
	private RoleService roleService;

	/**
	 * 使用迪米特法则,也称为最小知识原则，一个对象应该对其他的对象有最少的了解
	 * 不应该直接在Jsp或者servlet中使用SpringUtil.getBean("xxxx")获取这个bean的实例
	 * 而是通过UserService.getInstance()获取这个bean的实例
	 * 假如在jsp中使用了SpringUtil.getBean("xxxx")获取bean实例,万一有一天换了一种实现spring作用的框架;
	 * 那么就得在jsp或者servlet中大量的修改SpringUtil.getBean("xxxx")语句;
	 * 相反,假如使用xxxxService.getInstance()就不用修改。
	 * 
	 * @return
	 */
	public static final UserService getInstance() {
		return (UserService) SpringUtil.getBean("userService");
	}

	public User getUserById(String id) {
		String sql = "select * from user u where u.id = ?";
		Object[] args = new Object[] { id };
		List list = jdbcService.queryForList(sql, args, new User());
		User user = null;
		if (list != null && list.size() > 0) {
			user = (User) list.get(0);
		}
		return user;
	}

	public User getUserByUserid(String userid) {
		String sql = "select * from s_framework_user  where user_id = ? and delete_flag='0'";
		Object[] args = new Object[] { userid };
		List list = jdbcService.queryForList(sql, args, new User());
		User user = null;
		if (list != null) {
			user = (User) list.get(0);
		}
		return user;
	}

	public List getUsers() {
		String sql = "select * from s_framework_user where delete_flag='0'";
		Object[] args = new Object[] {};
		return jdbcService.queryForList(sql, args, new User());
	}

	public int createUser(String userid, String username, String mobile,
			HttpServletRequest req) {
		User user = UserUtil.getLoginUser(req);
		String sql = "insert into s_framework_user (id,user_id,user_name,password,mobile,creator,create_time) values(?,?,?,?,?,?,?)";
		String password = "123456";
		password = DigestUtils.md5Hex(password);
		Object[] args = new Object[] { UUID.randomUUID(), userid, username,
				password, mobile,user.getUserid(),new Date() };
		int[] argTypes = new int[] { Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.TIMESTAMP };
		return jdbcService.update(sql, args, argTypes);
	}

	public int createUser(String userid, String password) {
		String sql = "insert into s_framework_user (id,user_id,password) values(?,?,?)";
		password = DigestUtils.md5Hex(password);
		Object[] args = new Object[] { UUID.randomUUID(), userid, password };
		int[] argTypes = new int[] { Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR };
		return jdbcService.update(sql, args, argTypes);
	}
	/**
	 * 
	 * @param id
	 * @param userid
	 * @param username
	 * @param mobile
	 * @return
	 */
	public int editUser(String id, String userid, String username,
			String mobile) {
		String sql = "update s_framework_user  set user_id = ?,user_name = ?,mobile = ? where id = ? ";
		Object[] args = new Object[] { userid, username, mobile, id };
		int[] argTypes = new int[] { Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR };
		return jdbcService.update(sql, args, argTypes);
	}
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	public int deleteUser(String id) {
		String sql = "update s_framework_user set delete_flag='1' where id=? ";
		Object[] args = new Object[] { id };
		int[] argTypes = new int[] { Types.VARCHAR };
		return jdbcService.update(sql, args, argTypes);
	}
	
	public int deleteUserRole(String id) {
		String sql = "update s_framework_user_role set delete_flag='1' where id=?";
		Object[] args = new Object[] { id };
		int[] argTypes = new int[] { Types.VARCHAR };
		return jdbcService.update(sql, args, argTypes);
	}

	/**
	 * 判断账号是否已经存在
	 * 
	 * @param userid
	 */
	public Boolean isExists(String userid) {
		String sql = "select count(*) from s_framework_user where user_id = ? and delete_flag='0'";
		Object[] args = new Object[] { userid };
		return jdbcService.count(sql, args)>0?true:false;

	}

	/**
	 * 判断账号和姓名是否已经存在
	 * 
	 * @param userid
	 */
	public Boolean isExists(String userid, String password) {
		String sql = "select count(*) from s_framework_user where user_id = ? and password = ? and delete_flag='0'";
		password = DigestUtils.md5Hex(password);
		Object[] args = new Object[] { userid, password };
		int count = jdbcService.count(sql, args);
		if (count > 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * @param username
	 * @return
	 */
	public User getUserByUsername(String username) {
		String sql = "select * from user where username = ? ";
		Object[] args = new Object[] { username };
		List list = jdbcService.queryForList(sql, args, new User());
		User user = null;
		if (list != null && list.size() != 0) {
			return (User) list.get(0);
		}
		return user;
	}

	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}

	public JdbcService getJdbcService() {
		return jdbcService;
	}

	/**
	 * 查询用户列表
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public List getUserByPage(int page, int size) {
		String sql = "select * from (select * from s_framework_user where delete_flag='0') t limit ?,?";
		Object[] args = new Object[] { (page - 1) * size, size };
		List list = jdbcService.queryForList(sql, args, new User());
		List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
		for(Object object:list){
			User user = (User)object;
			if(null!=user){
				Map map = new HashMap();
				map.put("id",user.getId());
				map.put("userid", user.getUserid());
				map.put("mobile", user.getMobile());
				map.put("username",user.getUsername());
				List roleList = roleService.getRolesByUserId(user.getId());
				StringBuilder strBuilder = new StringBuilder();
				for(Object roleObject:roleList){
					Role role = (Role)roleObject;
					strBuilder.append(role.getRoleName()).append("  ");
				}
				map.put("roles", strBuilder.toString());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	/**
	 * 查询用户角色列表
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public List getUserRoleByPage(int page, int size,String userId) {
		String sql = "select * from (select * from s_framework_user_role where user_id = ? and delete_flag='0') t limit ?,?";
		Object[] args = new Object[] { userId,(page - 1) * size, size };
		return jdbcService.queryForList(sql, args, new UserRole());
	}

	public int getCount() {
		String sql = "select count(*) from s_framework_user where delete_flag='0'";
		Object[] args = new Object[] {};
		return jdbcService.count(sql, args);
	}
	
	public int getUserRoleCount() {
		String sql = "select count(*) from s_framework_user_role where delete_flag='0'";
		Object[] args = new Object[] {};
		return jdbcService.count(sql, args);
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
}
