package common.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import common.model.Dictionary;
import common.model.Function;
import common.model.Menu;
import common.model.MenuTree;
import common.model.User;
import common.service.FunctionService;
import common.service.MenuService;
import common.service.RoleService;

/**
 * 菜单管理
 * 
 * @author liangzhenghui
 * 
 */
@Controller
public class MenuController {

	private Logger logger = Logger.getLogger(MenuController.class);

	@Resource
	private MenuService menuService;

	@Resource
	private FunctionService functionService;

	@Resource
	private RoleService roleService;

	/**
	 * 根据第几页和每页显示几条记录返回菜单列表
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/menuList")
	public ModelAndView menuList(@RequestParam("page") String page,
			@RequestParam("rows") String rows) {
		ModelAndView model = new ModelAndView();
		List menuList = menuService.getMenuByPage(Integer.parseInt(page),
				Integer.parseInt(rows));
		int total = menuService.getCount();
		model.addObject("rows", menuList);
		model.addObject("total", total);
		return model;
	}

	@RequestMapping(value = "/menuDelete")
	public ModelAndView menuDelete(@RequestParam("id") String id) {
		ModelAndView model = new ModelAndView();
		int result = menuService.deleteMenu(id);
		if (result == 1) {
			model.addObject("result", true);
		} else {
			model.addObject("result", false);
		}
		return model;
	}

	@RequestMapping(value = "/menuCreate")
	public ModelAndView menuCreate(HttpServletRequest req,
			@RequestParam("data") String data) {
		JSONObject json = JSONObject.parseObject(data);
		ModelAndView model = new ModelAndView();
		boolean isExits = false;
		String menuName = json.getString("menuName");
		isExits = menuService.menuIsExits(menuName);
		int result = 0;
		if (!isExits) {
			result = menuService.createMenu(json, req);
		}
		if (result == 1) {
			model.addObject("result", true);
		} else {
			model.addObject("result", false);
		}
		model.addObject("isExits", isExits);
		return model;
	}

	@RequestMapping(value = "/menuEdit")
	public ModelAndView menuEdit(HttpServletRequest req,
			@RequestParam("data") String data) {
		JSONObject json = JSONObject.parseObject(data);
		ModelAndView model = new ModelAndView();
		int result = menuService.UpdateMenu(json);
		if (result == 1) {
			model.addObject("result", true);
		} else {
			model.addObject("result", false);
		}
		return model;
	}

	@RequestMapping(value = "/menuTree")
	public void menuTree(HttpServletResponse resp) throws IOException {
		List<MenuTree> menuTreeList = menuService.getMenuTree();
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(JSON.toJSONString(menuTreeList));
	}

	@RequestMapping(value = "/menuGrantRole")
	public ModelAndView menuGrantRole(@RequestParam("menuId") String[] menuIds,
			@RequestParam("roleId") String roleId) throws IOException {
		ModelAndView model = new ModelAndView();
		boolean result = false;
		menuService.grantMenuToRole(roleId, menuIds);
		result = true;
		model.addObject("result", result);
		return model;
	}

	@RequestMapping(value = "/getParentMenuSelectWhileCreate")
	public void getParentMenuWhileCreate(HttpServletResponse resp)
			throws IOException {
		menuService.createRootMenu();
		List menuList = menuService.getAllMenu();
		List<Dictionary> dataList = new ArrayList<Dictionary>();
		Dictionary data = null;
		for (Object object : menuList) {
			Menu menu = (Menu) object;
			data = new Dictionary();
			data.setCode(menu.getId());
			data.setDetail(menu.getMenuName());
			dataList.add(data);
		}
		data = new Dictionary();
		data.setCode("");
		data.setDetail("");
		data.setSelected(true);
		dataList.add(data);
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(JSON.toJSONString(dataList));
	}

	@RequestMapping(value = "/getParentMenuSelectWhileEdit")
	public void getParentMenuWhileEdit(HttpServletResponse resp,
			@RequestParam("pid") String pid) throws IOException {
		List menuList = menuService.getAllMenu();
		List<Dictionary> dataList = new ArrayList<Dictionary>();
		Dictionary data = null;
		for (Object object : menuList) {
			Menu menu = (Menu) object;
			data = new Dictionary();
			if (pid.equals(menu.getId())) {
				data.setSelected(true);
			}
			data.setCode(menu.getId());
			data.setDetail(menu.getMenuName());
			dataList.add(data);
		}
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(JSON.toJSONString(dataList));
	}

	@RequestMapping(value = "/getMenuTypeWhileCreate")
	public void getMenuTypeWhileCreate(HttpServletResponse resp)
			throws IOException {
		List menuTypeList = menuService.getMenuType();
		List<Dictionary> dataList = new ArrayList<Dictionary>();
		Dictionary data = null;
		for (Object object : menuTypeList) {
			Map map = (Map) object;
			data = new Dictionary();
			data.setCode((String) map.get("code"));
			data.setDetail((String) map.get("detail"));
			dataList.add(data);
		}
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(JSON.toJSONString(dataList));
	}

	@RequestMapping(value = "/getMenuTypeWhileEdit")
	public void getMenuTypeWhileEdit(HttpServletResponse resp,
			@RequestParam("menuId") String menuId) throws IOException {
		List menuTypeList = menuService.getMenuType();
		Menu menu = menuService.getMenuById(menuId);
		String menuType = menu.getMenuType();
		List<Dictionary> dataList = new ArrayList<Dictionary>();
		Dictionary data = null;
		for (Object object : menuTypeList) {
			Map map = (Map) object;
			if (null != map) {
				String code = (String) map.get("code");
				data = new Dictionary();
				if (StringUtils.isNotBlank(code) && code.equals(menuType)) {
					data.setSelected(true);
				}
				data.setCode((String) map.get("code"));
				data.setDetail((String) map.get("detail"));
				dataList.add(data);
			}
		}
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(JSON.toJSONString(dataList));
	}

	@RequestMapping(value = "/getFunctionSelectWhileCreate")
	public void getFunctionSelectWhileCreate(HttpServletResponse resp)
			throws IOException {
		List functionList = functionService.getAllFunction();
		List<Dictionary> dataList = new ArrayList<Dictionary>();
		Dictionary data = null;
		for (Object object : functionList) {
			Function function = (Function) object;
			if (function != null) {
				data = new Dictionary();
				data.setCode(function.getId());
				data.setDetail(function.getFunctionName());
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

	@RequestMapping(value = "/getFunctionSelectWhileEdit")
	public void getFunctionSelectWhileEdit(HttpServletResponse resp,
			@RequestParam("menuId") String menuId) throws IOException {
		List functionList = functionService.getAllFunction();
		Menu menu = menuService.getMenuById(menuId);
		List<Dictionary> dataList = new ArrayList<Dictionary>();
		Dictionary data = null;
		for (Object object : functionList) {
			Function function = (Function) object;
			if (function != null) {
				data = new Dictionary();
				if (function.getId().equals(menu.getFunctionId())) {
					data.setSelected(true);
				}
				data.setCode(function.getId());
				data.setDetail(function.getFunctionName());
				dataList.add(data);
			}
		}
		data = new Dictionary();
		data.setCode("");
		data.setDetail("");
		dataList.add(data);
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.write(JSONObject.toJSONString(dataList));
	}

	@RequestMapping(value = "/systemMenuTree")
	public void SystemMenuTree(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		List<MenuTree> menuTreeList = null;
		if (user != null) {
			List roleList = roleService.getRolesByUserId(user.getId());
			menuTreeList = menuService.getMenuTreeByRoles(roleList);
		}
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		String menuTree = JSON.toJSONString(menuTreeList);
		out.print(menuTree);
	}

	@RequestMapping(value = "/editMenusOfRole")
	public void EditMenusOfRole(@RequestParam("roleId") String roleId,
			HttpServletResponse resp) throws IOException {
		List<MenuTree> menuTreeListOfOneRole = null;
		List<MenuTree> menuTreeOfAll = null;
		if (StringUtils.isNotBlank(roleId)) {
			menuTreeListOfOneRole = menuService.getMenuTreeByRoleId(roleId);
		}
		// 取得所有的菜单,并且将菜单都放到List<MenuTree>中返回来
		menuTreeOfAll = menuService.getMenuTree();
		for (MenuTree menuTree : menuTreeOfAll) {
			for (MenuTree menuTreeOfOneRole : menuTreeListOfOneRole) {
				if (menuTree.getId().equals(menuTreeOfOneRole.getId())) {
					menuTree.setChecked(true);
				}
			}
		}
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		String tree = JSON.toJSONString(menuTreeOfAll);
		out.print(tree);
	}
}
