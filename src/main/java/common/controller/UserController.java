package common.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import common.service.UserService;

/**
 * 用户管理
 * 
 * @author liangzhenghui
 * 
 */
@Controller
public class UserController {
	
	private Logger logger = Logger.getLogger(UserController.class);
	
	@Resource
	private UserService userService;

	/**
	 * 根据第几页和每页显示几条记录返回用户列表
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/userList")
	public ModelAndView userList(@RequestParam("page") String page,
			@RequestParam("rows") String rows) {
		ModelAndView model = new ModelAndView();
		List userList = userService.getUserByPage(Integer.parseInt(page),
				Integer.parseInt(rows));
		int total = userService.getCount();
		model.addObject("rows", userList);
		model.addObject("total", total);
		return model;
	}

	@RequestMapping(value = "/userCreate")
	public ModelAndView userCreate(HttpServletRequest req,
			@RequestParam("data") String data) {
		JSONObject json = JSONObject.parseObject(data);
		ModelAndView model = new ModelAndView();
		String userid = json.getString("userid");
		String username = json.getString("username");
		String mobile = json.getString("mobile");
		int result = userService.createUser(userid, username, mobile, req);
		if (result == 1) {
			model.addObject("result", true);
		} else {
			model.addObject("result", false);
		}
		return model;
	}

	@RequestMapping(value = "/userEdit")
	public ModelAndView userEdit(HttpServletRequest req,
			@RequestParam("data") String data) {
		JSONObject json = JSONObject.parseObject(data);
		ModelAndView model = new ModelAndView();
		String userid = json.getString("userid");
		String username = json.getString("username");
		String mobile = json.getString("mobile");
		String id = json.getString("id");
		int result = userService.editUser(id, userid, username, mobile);
		if (result == 1) {
			model.addObject("result", true);
		} else {
			model.addObject("result", false);
		}
		return model;
	}

	@RequestMapping(value = "/userDelete")
	public ModelAndView userDelete(@RequestParam("id") String id) {
		ModelAndView model = new ModelAndView();
		int result = userService.deleteUser(id);
		if (result == 1) {
			model.addObject("result", true);
		} else {
			model.addObject("result", false);
		}
		return model;
	}
	
	@RequestMapping(value = "/user-role-delete")
	public ModelAndView userRoleDelete(@RequestParam("id") String id) {
		ModelAndView model = new ModelAndView();
		int result = userService.deleteUserRole(id);
		if (result == 1) {
			model.addObject("result", true);
		} else {
			model.addObject("result", false);
		}
		return model;
	}
	
	@RequestMapping(value = "/user-role-list")
	public ModelAndView userRoleList(@RequestParam("page") String page,
			@RequestParam("rows") String rows,@RequestParam("userId") String userId) {
		ModelAndView model = new ModelAndView();
		List userRoleList = userService.getUserRoleByPage(Integer.parseInt(page),
				Integer.parseInt(rows),userId);
		int total = userService.getUserRoleCount();
		model.addObject("rows", userRoleList);
		model.addObject("total", total);
		return model;
	}
}
