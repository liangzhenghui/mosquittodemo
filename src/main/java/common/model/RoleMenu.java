package common.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author liangzhenghui
 * @date Aug 27, 2013    12:55:59 PM
 */
public class RoleMenu implements RowMapper {

	private String id;
	private String roleId;
	private String menuId;
	
	public String getId() {
		return id;
	}

	public String getRoleId() {
		return roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		RoleMenu roleMenu = new RoleMenu();
		roleMenu.setId(rs.getString("id"));
		roleMenu.setMenuId(rs.getString("menu_id"));
		roleMenu.setRoleId(rs.getString("role_id"));
		return roleMenu;
	}

}
