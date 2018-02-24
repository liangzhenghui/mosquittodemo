package common.service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

import common.dao.JdbcService;
import common.model.Function;
import common.model.Menu;
import common.model.MenuTree;
import common.model.Role;
import common.model.RoleMenu;
import common.model.User;
import common.util.SpringUtil;
import common.util.UserUtil;

/**
 * @author liangzhenghui
 * @date Aug 19, 2013 3:35:47 PM
 */
public class MenuService {
	private JdbcService jdbcService;
	private FunctionService functionService;

	/**
	 * 使用迪米特法则,也称为最小知识原则，一个对象应该对其他的对象有最少的了解
	 * 不应该直接在Jsp或者servlet中使用SpringUtil.getBean("xxxx")获取这个bean的实例
	 * 而是通过MenuService.getInstance()获取这个bean的实例
	 * 
	 * @return
	 */
	public static final MenuService getInstance() {
		return (MenuService) SpringUtil.getBean("menuService");
	}

	public List getAllMenu() {
		String sql = "select * from s_framework_menu where delete_flag='0'";
		Object[] args = new Object[] {};
		return jdbcService.queryForList(sql, args, new Menu());
	}

	public List getMenuByPage(int page, int size) {
		String sql = "select * from (select * from s_framework_menu where delete_flag='0') t limit ?,?";
		Object[] args = new Object[] { (page - 1) * size, size };
		return jdbcService.queryForList(sql, args, new Menu());
	}

	public int getCount() {
		String sql = "select count(*) from s_framework_menu where delete_flag='0'";
		Object[] args = new Object[] {};
		return jdbcService.count(sql, args);
	}

	public List getChildrenMenu(String parentMenu) {
		String sql = "select * from menu where parentmenu = ?";
		Object[] args = new Object[] { parentMenu };
		return jdbcService.queryForList(sql, args, new Menu());
	}

	public void createMenu(String menuName, String parentMenu, String functionId) {
		String sql = "insert into menu (menuid, menuname, parentmenu, functionid ) values(?,?,?,?)";
		Object[] args = new Object[] { UUID.randomUUID(), menuName, parentMenu,
				functionId };
		int[] argTypes = new int[] { Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR };
		jdbcService.update(sql, args, argTypes);
	}

	/**
	 * @param menuId
	 */
	public Menu getMenuById(String menuId) {
		String sql = "select * from s_framework_menu t where t.id = ? and delete_flag='0'";
		Object[] args = new Object[] { menuId };
		List list = jdbcService.queryForList(sql, args, new Menu());
		Menu menu = null;
		if (list != null && list.size() > 0) {
			menu = (Menu) list.get(0);
		}
		return menu;
	}

	public void editMenu(String menuId, String functionId, String menuName,
			String parentMenu) {
		String sql = "update menu  set  functionid = ? , menuname = ? ,parentmenu = ? where menuid = '"
				+ menuId + "'";
		Object[] args = new Object[] { functionId, menuName, parentMenu };
		int[] argTypes = new int[] { Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR };
		jdbcService.update(sql, args, argTypes);
	}

	/**
	 * 应该删除所有的子菜单
	 * 
	 * @param menuId
	 */
	/*
	 * public void deleteMenu(String menuId) { String sql =
	 * "delete from menu where menuid = ? "; Object[] args = new Object[] {
	 * menuId }; int[] argTypes = new int[] { Types.VARCHAR };
	 * jdbcService.update(sql, args, argTypes); //
	 * 删除一条菜单的同时,应该将menu_role表中包含该菜单的数据删掉,避免脏数据或者因为id缺失导致的错误。 sql =
	 * "delete from role_menu where menuid = ?"; args = new Object[] { menuId };
	 * argTypes = new int[] { Types.VARCHAR }; jdbcService.update(sql, args,
	 * argTypes); sql = "select * from menu where parentmenu = ?"; List list =
	 * jdbcService.queryForList(sql, args, new Menu()); if (list != null &&
	 * list.size() > 0) { for (Object object : list) { Menu menu = (Menu)
	 * object; if (menu != null) { deleteMenu(menu.getMenuId()); } } } }
	 */

	/**
	 * 取出所有的菜单，并将菜单放进MenuTree以List<MenuTree> 的List形式返回去
	 * 
	 * @return
	 */

	public List<MenuTree> getMenuTree() {
		List menuList = getAllMenu();
		List<MenuTree> menuTreeList = new ArrayList<MenuTree>();
		for (Object object : menuList) {
			Menu menu = (Menu) object;
			if(null!=menu){
				MenuTree menuTree = new MenuTree(menu.getId(), menu.getParentId(),
						menu.getMenuName());
				menuTreeList.add(menuTree);
			}
		}
		return menuTreeList;
	}

	/**
	 * @param roleId
	 * @param menuIds
	 */
	public void grantMenuToRole(String roleId, String[] menuIds) {
		String sql = "";
		// 每次为角色添加权限的时候都先将改角色原来所有的权限全部删掉
		sql = "update  s_framework_role_menu set delete_flag='1' where role_id = ?";
		Object[] args1 = new Object[] { roleId };
		int[] argTypes1 = new int[] { Types.VARCHAR };
		jdbcService.update(sql, args1, argTypes1);
		for (String menuId : menuIds) {
			sql = "insert into s_framework_role_menu (id, role_id, menu_id) values(?, ?, ?)";
			Object[] args = new Object[] { UUID.randomUUID(), roleId, menuId };
			int[] argTypes = new int[] { Types.VARCHAR, Types.VARCHAR,
					Types.VARCHAR };
			jdbcService.update(sql, args, argTypes);
		}
	}

	public List getMenusByRoleId(String roleId) {
		String sql = "select * from s_framework_role_menu where role_id = ? and delete_flag='0'";
		Object[] args = new Object[] { roleId };
		List roleMenus = jdbcService.queryForList(sql, args, new RoleMenu());
		List<Menu> menuList = new ArrayList<Menu>();
		for (Object object : roleMenus) {
			RoleMenu roleMenu = (RoleMenu) object;
			if (null != roleMenu) {
				menuList.add(getMenuById(roleMenu.getMenuId()));
			}
		}
		return menuList;
	}

	public Boolean hasChildren(String menuId) {
		String sql = "select count(*) from s_framework_menu where parent_id = ? and delete_flag='0'";
		Object[] args = new Object[] { menuId };
		int count = jdbcService.count(sql, new Object[] { args });
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean menuIsExits(String menuName) {
		String sql = "select count(*) from s_framework_menu where menu_name=? and delete_flag='0'";
		int count = jdbcService.count(sql, new Object[] { menuName });
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	public int createMenu(JSONObject json, HttpServletRequest req) {
		User user = UserUtil.getLoginUser(req);
		String menuName = json.getString("menuName");
		String parentId = json.getString("parentId");
		String parentMenuZw = json.getString("parentMenuZw");
		String function_id = json.getString("functionId");
		String functionZw = json.getString("functionZw");
		String menu_code = json.getString("menuCode");
		String menu_type = json.getString("menuType");
		String menuTypeZw = json.getString("menuTypeZw");
		String sql = " insert into s_framework_menu(id, menu_name, parent_id,function_id,menu_code,menu_type,creator,create_time,parent_menu_zw,function_zw,menu_type_zw) values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] args = new Object[] { UUID.randomUUID(), menuName, parentId,
				function_id, menu_code, menu_type, user.getUserid(),
				new Date(), parentMenuZw, functionZw, menuTypeZw };
		int[] argTypes = new int[] { Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR };
		return jdbcService.update(sql, args, argTypes);
	}

	public List getMenuType() {
		String sql = "select * from s_framework_dictionary where kind='menu_type' and delete_flag='0'";
		return jdbcService.queryForList(sql, new Object[] {});
	}

	public int UpdateMenu(JSONObject json) {
		String id = json.getString("id");
		String menuName = json.getString("menuName");
		String parentId = json.getString("parentId");
		String parentMenuZw = json.getString("parentMenuZw");
		String function_id = json.getString("functionId");
		String functionZw = json.getString("functionZw");
		String menu_code = json.getString("menuCode");
		String menu_type = json.getString("menuType");
		String menuTypeZw = json.getString("menuTypeZw");
		String sql = "update s_framework_menu set menu_name=?,parent_id=?,parent_menu_zw=?,function_id=?,function_zw=?,menu_type=?,menu_type_zw=?,menu_code=? where id=?";
		Object[] args = new Object[] { menuName, parentId, parentMenuZw,
				function_id, functionZw, menu_type, menuTypeZw, menu_code, id };
		int[] argTypes = new int[] { Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR };
		return jdbcService.update(sql, args, argTypes);
	}

	public int deleteMenu(String id) {
		String sql = "update s_framework_menu set delete_flag='1' where id=? ";
		Object[] args = new Object[] { id };
		int[] argTypes = new int[] { Types.VARCHAR };
		return jdbcService.update(sql, args, argTypes);
	}

	/**
	 * @param roleList
	 * @return
	 */
	public List<MenuTree> getMenuTreeByRoles(List roleList) {
		List<MenuTree> menuTrees = new ArrayList<MenuTree>();
		for (Object object : roleList) {
			Role role = (Role) object;
			if(null!=role){
				List menuList = getMenusByRoleId(role.getRoleId());
				for (Object object1 : menuList) {
					Menu menu = (Menu) object1;
					MenuTree menuTree1 = new MenuTree();
					menuTree1.setId(menu.getId());
					menuTree1.setMenuName(menu.getMenuName());
					menuTree1.setParentMenu(menu.getParentMenuZw());
					String url = ""; //
					if (hasChildren(menu.getId())) {
						url = "#";
					} else {
						if (StringUtils.isNotBlank(menu.getFunctionId())) {
							Function function = functionService
									.getFunctionById(menu.getFunctionId());
							if(null!=function){
								url = function.getUrl();
							}
						}
					}
					menuTree1.setUrl(url);
					menuTrees.add(menuTree1);
				}
			}
		}
		return menuTrees;
	}

	// 根据角色ID查找该角色下的所有菜单
	public List<MenuTree> getMenuTreeByRoleId(String roleId) {
		List<MenuTree> menuTrees = new ArrayList<MenuTree>();
		List menuList = getMenusByRoleId(roleId);
		for (Object object1 : menuList) {
			Menu menu = (Menu) object1;
			if(null!=menu){
				MenuTree menuTree1 = new MenuTree();
				menuTree1.setId(menu.getId());
				menuTree1.setMenuName(menu.getMenuName());
				menuTree1.setParentMenu(menu.getParentId());
				String url = "#";
				if (StringUtils.isNotBlank(menu.getFunctionId())) {
					Function function = functionService.getFunctionById(menu
							.getFunctionId());
					if(null!=function){
						url = function.getUrl();
					}
				}
				menuTree1.setUrl(url);
				menuTrees.add(menuTree1);
			}
		}
		return menuTrees;
	}

	public void createRootMenu() {
		String sql = "select count(*) from s_framework_menu where id='root_menu'";
		int count = jdbcService.count(sql, new Object[] {});
		if (count == 0) {
			String sql1 = "insert into s_framework_department(id,menu_name) values(?,?)";
			jdbcService.update(sql1, new Object[] {
					UUID.randomUUID().toString(), "无上级菜单" }, new int[] {
					Types.VARCHAR, Types.VARCHAR });
		}
	}

	public JdbcService getJdbcService() {
		return jdbcService;
	}

	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}

	public FunctionService getFunctionService() {
		return functionService;
	}

	public void setFunctionService(FunctionService functionService) {
		this.functionService = functionService;
	}
}
