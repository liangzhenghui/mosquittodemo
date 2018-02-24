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

import common.service.FunctionService;

/**
 * 功能管理
 * 
 * @author liangzhenghui
 * 
 */
@Controller
public class FunctionController {
	
	private Logger logger = Logger.getLogger(FunctionController.class);
	
	@Resource
	private FunctionService functionService;

	/**
	 * 根据第几页和每页显示几条记录返回功能列表
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/functionList")
	public ModelAndView functionList(@RequestParam("page") String page,
			@RequestParam("rows") String rows) {
		ModelAndView model = new ModelAndView();
		List functionList = functionService.getFunctionByPage(Integer.parseInt(page),
				Integer.parseInt(rows));
		int total = functionService.getCount();
		model.addObject("rows", functionList);
		model.addObject("total", total);
		return model;
	}

	@RequestMapping(value = "/functionCreate")
	public ModelAndView functionCreate(HttpServletRequest req,
			@RequestParam("data") String data) {
		JSONObject json = JSONObject.parseObject(data);
		ModelAndView model = new ModelAndView();
		boolean isExits = false;
		String functionName = json.getString("functionName");
		String url = json.getString("url");
		isExits = functionService.functionIsExits(functionName);
		int result = 0;
		if(!isExits){
			result = functionService.createFunction(functionName,url,req);
		}
		if (result == 1) {
			model.addObject("result", true);
		} else {
			model.addObject("result", false);
		}
		model.addObject("isExits", isExits);
		return model;
	}

	@RequestMapping(value = "/functionEdit")
	public ModelAndView functionEdit(HttpServletRequest req,
			@RequestParam("data") String data) {
		JSONObject json = JSONObject.parseObject(data);
		ModelAndView model = new ModelAndView();
		String functionName = json.getString("functionName");
		String url = json.getString("url");
		String id = json.getString("id");
		int result = functionService.editFunction(id,functionName,url);
		if (result == 1) {
			model.addObject("result", true);
		} else {
			model.addObject("result", false);
		}
		return model;
	}

	@RequestMapping(value = "/functionDelete")
	public ModelAndView userDelete(@RequestParam("id") String id) {
		ModelAndView model = new ModelAndView();
		int result = functionService.deleteFunction(id);
		if (result == 1) {
			model.addObject("result", true);
		} else {
			model.addObject("result", false);
		}
		return model;
	}
}
