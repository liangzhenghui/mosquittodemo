package common.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import common.model.Role;
import common.model.User;
import common.service.RoleService;
import common.service.UserManager;
import common.service.UserService;

@Controller
public class LoginController {
	
	private Logger logger = Logger.getLogger(LoginController.class);
	
	@Resource
	private UserManager userManager;
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;

	@RequestMapping(value = "/login")
	public ModelAndView login(@RequestParam("userId") String userId,
			@RequestParam("password") String password, HttpServletRequest req) {
		String role = req.getParameter("role");
		ModelAndView model = new ModelAndView();
		Boolean result = userManager.isExists(userId, password);
		if (result) {
			//如果是登陆商家客户端,就对账号进行角色判断，看是否有权限登录
			if(StringUtils.isNotBlank(role)&&role.equals("seller")){
				List roleList = roleService.getAllRoles();
				int count = 0;
				for(Object object : roleList){
					Role roleObject = (Role)object;
					if(roleObject.getRoleName().equals("商家")){
						count++;
					};
				}
				//如果没有商家角色
				if(count==0){
					model.addObject("result", 2);
					return model;
				}
			}
			HttpSession session = req.getSession(true);
			User user = userService.getUserByUserid(userId);
			session.setAttribute("user", user);
			session.setAttribute("sessionId", session.getId());
			model.addObject("userid",user.getUserid());
		}
		model.addObject("result", result);
		return model;
	}
	
	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest req) {
		ModelAndView model = new ModelAndView();
		HttpSession session = req.getSession(true);
		session.setAttribute("user", null);
		model.addObject("result", true);
		return model;
	}

	@RequestMapping(value = "/register")
	public ModelAndView register(@RequestParam("userId") String userId,
			@RequestParam("password") String password, HttpServletRequest req) {
		ModelAndView model = new ModelAndView();
		boolean isExits = userService.isExists(userId);
		if(!isExits){
			int result = userService.createUser(userId, password);
			model.addObject("result", result);
		}else{
			//3表示存在相同的用户名
			model.addObject("result", 3);
		}
		return model;
	}
	/**
	 * 判断session是否过期
	 * @param sessionId
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/session-expired")
	public ModelAndView login(@RequestParam("sessionId") String sessionId,
			HttpServletRequest req) {
		ModelAndView model = new ModelAndView();
		boolean result = false;
		//如果没有session就返回null
		HttpSession session = req.getSession(false);
		if(session!=null){
			String id = session.getId();
			if(StringUtils.isNotBlank(sessionId)&&!sessionId.equals(id)){
				model.addObject("result", true);
			}
		}else{
			//session过期了则为true
			model.addObject("result", true);
		}
		model.addObject("result", result);
		return model;
	}
	
	@RequestMapping(value = "/loginPage")
	public ModelAndView loginPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("login");
		return model;
	}
}
