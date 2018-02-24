package common.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import common.model.Department;
import common.model.DepartmentTree;
import common.model.Dictionary;
import common.service.DepartmentService;

/**
 * @author: liangzhenghui
 * @blog: http://my.oschina.net/liangzhenghui/blog
 * @email:715818885@qq.com 2014年12月6日 下午2:51:31
 */
@Controller
public class DepartmentController {
	@Resource
	private DepartmentService departmentService;
	
	
	@RequestMapping(value = "/departmentList")
	public ModelAndView functionList(@RequestParam("page") String page,
			@RequestParam("rows") String rows) {
		ModelAndView model = new ModelAndView();
		List functionList = departmentService.getDepartmentByPage(Integer.parseInt(page),
				Integer.parseInt(rows));
		int total = departmentService.getCount();
		model.addObject("rows", functionList);
		model.addObject("total", total);
		return model;
	}
	
	@RequestMapping(value = "/departmentCreate")
	public ModelAndView DepartmentCreate(HttpServletRequest req,
			@RequestParam("data") String data) {
		JSONObject json = JSONObject.parseObject(data);
		ModelAndView model = new ModelAndView();
		boolean isExits = false;
		String department_name = json.getString("department_name");
		isExits = departmentService.departmentIsExits(department_name);
		int result = 0;
		if (!isExits) {
			result = departmentService.createDepartment(json, req);
		}
		if (result == 1) {
			model.addObject("result", true);
		} else {
			model.addObject("result", false);
		}
		model.addObject("isExits", isExits);
		return model;
	}

	@RequestMapping(value = "/getParentDepartmentSelectWhileCreate")
	public void getParentDepartmentSelectWhileCreate(HttpServletResponse resp)
			throws IOException {
		departmentService.createRootDepartment();
		List departmentList = departmentService.getAllDepartment();
		List<Dictionary> dataList = new ArrayList<Dictionary>();
		Dictionary data = null;
		for (Object object : departmentList) {
			Department department = (Department) object;
			data = new Dictionary();
			data.setCode(department.getId());
			data.setDetail(department.getName());
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
	
	@RequestMapping(value = "/departmentTree")
	public void DepartmentTree(HttpServletResponse resp) throws IOException {
		List<DepartmentTree> departmentreeList = departmentService.getAllDepartments();
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		String departmentTree = JSON.toJSONString(departmentreeList);
		out.print(departmentTree);
	}
}
