package common.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author liangzhenghui
 * @date Aug 26, 2013    9:14:26 AM
 */
public class Role implements RowMapper{
	private String roleId;
	private String roleName;
	

	public String getRoleId() {
		return roleId;
	}


	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Role role = new Role();
		role.setRoleId(rs.getString("id"));
		role.setRoleName(rs.getString("role_name"));
		return role;
	}

}
