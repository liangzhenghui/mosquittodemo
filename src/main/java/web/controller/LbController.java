package web.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import common.model.Dictionary;
import common.model.User;
import web.service.LbService;
import web.service.ProductService;

/**
 * @author: liangzhenghui
 * @blog: http://my.oschina.net/liangzhenghui/blog
 * @email:715818885@qq.com
 * 2015年9月14日 下午11:27:19
 */
@Controller
public class LbController {
	@Resource
	private LbService lbService;
	@Resource
	private ProductService productService;
	/**
	 * 创建产品类别
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/newProductLb")
	public ModelAndView newProductLb(HttpServletRequest request){
		ModelAndView model = new ModelAndView();
		String data = request.getParameter("data");
		JSONObject json = JSONObject.parseObject(data);
		String lb = json.getString("name");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		boolean islbExits = lbService.isLbExists(lb,user.getUserid());
		int result = 0;
		if(islbExits){
			result = 2;
		}else{
			result = lbService.createPrductLb(lb,user.getUserid());
		}
		model.addObject("result",result);
		return model;
	}
	/**
	 * 根据用户查询产品列表
	 * @param page
	 * @param rows
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/product-lb-list")
	public ModelAndView getProductLbList(@RequestParam("page") String page,
			@RequestParam("rows") String rows,HttpServletRequest request){
		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		List list = lbService.getLbByUserId(Integer.parseInt(page),Integer.parseInt(rows),user.getUserid());
		int count = lbService.getLbCountByUserId(user.getUserid());
		model.addObject("rows", list);
		model.addObject("total", count);
		return model;
	}
	
	@RequestMapping(value="/lb-delete")
	public ModelAndView ProductLbDelete(@RequestParam("id") String id){
		ModelAndView model = new ModelAndView();
		int count = lbService.deleteProductLb(id);
		model.addObject("result", count);
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value="/get-lb-list")
	public List GetProductLb(@RequestParam("productId") String id){
		return lbService.getLb();
	}
	/**
	 * 根据用户查询用户创建的产品类别
	 * @param id
	 * @param request
	 * @param resp
	 * @throws Exception
	 */
/*	@RequestMapping(value="/get-product-lb-by-user")
	public void GetProductLb(HttpServletRequest request,HttpServletResponse resp) throws Exception{
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		List lbList = lbService.getLbByUserId(user.getUserid());
		List<Dictionary> dataList = new ArrayList<Dictionary>();
		Dictionary data = null;
		for (Object object : lbList) {
			Map map = (Map) object;
			data = new Dictionary();
			data.setCode((String) map.get("code"));
			data.setDetail((String) map.get("detail"));
			dataList.add(data);
		}
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(JSON.toJSONString(dataList));
	}*/
	
	@RequestMapping(value = "/get-product-lb-by-user")
	public void getLbEdit(HttpServletRequest request,HttpServletResponse resp)
			throws IOException {
		String productId = request.getParameter("productId");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		List lbList = lbService.getLbByUserId(user.getUserid());
		Map productMap = null;
		if(StringUtils.isNotBlank(productId)){
			productMap = productService.getProductById(productId);
		}
		List<Dictionary> dataList = new ArrayList<Dictionary>();
		Dictionary data = null;
		for (Object object : lbList) {
			Map map = (Map) object;
			data = new Dictionary();
			if (null!=productMap&&null!=map) {
				String id = (String)map.get("code");
				String lb_id = (String)productMap.get("lb_id");
				if(StringUtils.isNotBlank(id)&&id.equals(lb_id)){
					data.setSelected(true);
				}
			}
			data.setCode((String) map.get("code"));
			data.setDetail((String) map.get("detail"));
			dataList.add(data);
		}
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(JSON.toJSONString(dataList));
	}
	
	@RequestMapping(value="/editProductLb")
	public ModelAndView editProductLb(HttpServletRequest request){
		ModelAndView model = new ModelAndView();
		String data = request.getParameter("data");
		JSONObject json = JSONObject.parseObject(data);
		String lb = json.getString("name");
		String id = json.getString("id");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		boolean islbExits = lbService.isLbExists(lb,user.getUserid());
		int result = 0;
		if(islbExits){
			result = 2;
		}else{
			result = lbService.EditPrductLb(lb,id);
		}
		model.addObject("result",result);
		return model;
	}
}
