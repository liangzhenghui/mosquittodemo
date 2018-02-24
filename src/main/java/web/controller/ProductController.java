/**
 * 2018年1月29日
 * liangzhenghui
 */
package web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import web.service.ProductService;

@Controller
public class ProductController {
	@Resource
	private ProductService productService;

	@RequestMapping(value = "/product-list", method = RequestMethod.POST)
	public ModelAndView productList(@RequestParam("page") String page, @RequestParam("rows") String rows) {
		ModelAndView model = new ModelAndView();
		List productList = productService.getProductByPage(Integer.parseInt(page), Integer.parseInt(rows));
		int total = productService.getProductCount();
		model.addObject("rows", productList);
		model.addObject("total", total);
		return model;
	}

	@RequestMapping(value = "/product-list", method = RequestMethod.GET)
	public ModelAndView productList() {
		ModelAndView model = new ModelAndView();
		model.setViewName("product-list");
		return model;
	}
}
