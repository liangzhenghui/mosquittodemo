package web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import common.controller.AppExceptionController;
import common.model.Response;
import common.util.ResponseUtil;
import web.service.WqService;

/**
 * 审批信息
 * 
 * @author liangzhenghui
 *
 */
@RequestMapping("mobile/wq")
@Controller
public class WqController extends AppExceptionController {
	@Resource
	private WqService wqService;

	@RequestMapping(value = "/bm")
	@ResponseBody
	public Response bm(@RequestBody Map<String, Object> map) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(wqService.isExists(map)){
			result.put("isExists", true);
		}else{
			wqService.bm(map);
		}
		return ResponseUtil.createSuccessResponse(result);
	}
}
