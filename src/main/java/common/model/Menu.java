package common.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author liangzhenghui
 * @date Aug 20, 2013    3:32:12 PM
 */
public class Menu implements RowMapper{
	private String id;
	private String menuName;
	private String parentId;
	private String functionId;
	private String menuCode;
	private String menuType;
	private String menuTypeZw;
	private String functionZw;
	private String parentMenuZw;

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Menu menu = new Menu();
		menu.setId(rs.getString("id"));
		menu.setMenuName(rs.getString("menu_name"));
		menu.setParentId(rs.getString("parent_id"));
		menu.setFunctionId(rs.getString("function_id"));
		menu.setMenuType(rs.getString("menu_type"));
		menu.setMenuCode(rs.getString("menu_code"));
		menu.setParentMenuZw(rs.getString("parent_menu_zw"));
		menu.setFunctionZw(rs.getString("function_zw"));
		menu.setMenuTypeZw(rs.getString("menu_type_zw"));
		return  menu;
	}
	
	public String getId() {
		return id;
	}

	public String getMenuName() {
		return menuName;
	}

	public String getFunctionId() {
		return functionId;
	}


	public String getMenuType() {
		return menuType;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}


	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuTypeZw() {
		return menuTypeZw;
	}

	public String getFunctionZw() {
		return functionZw;
	}

	public String getParentMenuZw() {
		return parentMenuZw;
	}

	public void setMenuTypeZw(String menuTypeZw) {
		this.menuTypeZw = menuTypeZw;
	}

	public void setFunctionZw(String functionZw) {
		this.functionZw = functionZw;
	}

	public void setParentMenuZw(String parentMenuZw) {
		this.parentMenuZw = parentMenuZw;
	}

}
