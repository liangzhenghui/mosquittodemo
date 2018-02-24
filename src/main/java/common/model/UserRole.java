package common.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author liangzhenghui
 * @date Aug 27, 2013    12:49:04 PM
 */
public class UserRole implements RowMapper {

	private String id;
	private String userId;
	private String roleId;
	private String userName;
	private String roleName;
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		UserRole userRole = new UserRole();
		userRole.setId(rs.getString("id"));
		userRole.setRoleId(rs.getString("role_id"));
		userRole.setUserId(rs.getString("user_id"));
		userRole.setRoleName(rs.getString("role_name"));
		userRole.setUserName(rs.getString("user_name"));
		return userRole;
	}
	public String getId() {
		return id;
	}
	public String getUserId() {
		return userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getUserName() {
		return userName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
