package common.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import common.model.Dictionary;
import common.model.MenuTree;
import common.model.Role;
import common.service.MenuService;
import common.service.RoleService;

/**
 * 角色管理
 * 
 * @author liangzhenghui
 * 
 */
@Controller
public class RoleController {

	private Logger logger = Logger.getLogger(RoleController.class);

	@Resource
	private RoleService roleService;
	@Resource
	private MenuService menuService;

	/**
	 * 根据第几页和每页显示几条记录返回用户列表
	 * 
	 * @param page
	 * @param rows
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/menuTreeForOneRole")
	public void menuTreeForOneRole(HttpServletResponse resp,
			@RequestParam("roleId") String roleId) throws IOException {
		List<MenuTree> menuTreeList = null;
		if (StringUtils.isNotBlank(roleId)) {
			menuTreeList = menuService.getMenuTreeByRoleId(roleId);
		}
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		String menuTree = JSON.toJSONString(menuTreeList);
		out.print(menuTree);
	}

	@RequestMapping(value = "/roleCreate")
	public ModelAndView userCreate(HttpServletRequest req,
			@RequestParam("data") String data) {
		boolean isExits = false;
		JSONObject json = JSONObject.parseObject(data);
		ModelAndView model = new ModelAndView();
		String roleName = json.getString("roleName");

		isExits = roleService.roleNameIsExits(roleName);
		int result = 0;
		if (!isExits) {
			result = roleService.createRole(roleName, req);
		}
		if (result == 1) {
			model.addObject("result", true);
		} else {
			model.addObject("result", false);
		}
		model.addObject("isExits", isExits);
		return model;
	}
	
	@RequestMapping(value = "/role-delete")
	public ModelAndView userDelete(@RequestParam("roleId") String roleId) {
		ModelAndView model = new ModelAndView();
		boolean result = false;
		roleService.deleteRole(roleId);
		result = true;
		model.addObject("result", result);
		return model;
	}

	@RequestMapping(value = "/roleGrantUser")
	public ModelAndView roleGrantUser(@RequestParam("data") String data){
		JSONObject json = JSONObject.parseObject(data);
		String userId = json.getString("userId");
		String roleId = json.getString("roleId");
		String roleName = json.getString("roleName");
		String userName = json.getString("userName");
		ModelAndView model = new ModelAndView();
		boolean hasTheRole = false;
		hasTheRole = roleService.userHasTheRole(userId, roleId);
		int result = 0;
		if (!hasTheRole) {
			result = roleService.roleGrantUser(roleId, userId,roleName,userName);
		}
		if (result == 1) {
			model.addObject("result", true);
		} else {
			model.addObject("result", false);
		}
		model.addObject("hasTheRole", hasTheRole);
		return model;
	}

	@RequestMapping(value = "/getAllRoles")
	public void getAllRoles(HttpServletResponse resp)
			throws IOException {
		List RolesList = roleService.getAllRoles();
		List<Dictionary> dataList = new ArrayList<Dictionary>();
		Dictionary data = null;
		for (Object object : RolesList) {
			Role role = (Role) object;
			if (role != null) {
				data = new Dictionary();
				data.setCode(role.getRoleId());
				data.setDetail(role.getRoleName());
				dataList.add(data);
			}
		}
		data = new Dictionary();
		data.setCode("");
		data.setDetail("");
		data.setSelected(true);
		dataList.add(data);
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.write(JSONObject.toJSONString(dataList));
	}
	/*
	 * @RequestMapping(value = "/userEdit") public ModelAndView
	 * userEdit(HttpServletRequest req,
	 * 
	 * @RequestParam("data") String data) { JSONObject json =
	 * JSONObject.parseObject(data); ModelAndView model = new ModelAndView();
	 * String userid = json.getString("userid"); String username =
	 * json.getString("username"); String mobile = json.getString("mobile");
	 * String id = json.getString("id"); int result = userService.editUser(id,
	 * userid, username, mobile); if (result == 1) { model.addObject("result",
	 * true); } else { model.addObject("result", false); } return model; }
	 * 
	 * @RequestMapping(value = "/userDelete") public ModelAndView
	 * userDelete(@RequestParam("id") String id) { ModelAndView model = new
	 * ModelAndView(); int result = userService.deleteUser(id); if (result == 1)
	 * { model.addObject("result", true); } else { model.addObject("result",
	 * false); } return model; }
	 */
}
