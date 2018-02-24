package common.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import common.model.Response;

/**
 * Created by yiyonglin on 2017/3/21.
 */
public class ResponseUtil {
	/**
	 * 成功情况下返回结果
	 * 
	 * @param result
	 * @return
	 */
	public static Response createSuccessResponse(Object result) {
		Response response = new Response();
		response.setError(false);
		response.setResult(result);
		return response;
	}

	/**
	 * 失败情况下返回结果
	 * 
	 * @param type
	 * @param errorMessage
	 * @return
	 */
	public static Response createErrorResponse(String type, String errorMessage) {
		Response response = new Response();
		response.setError(true);
		response.setErrorType(type);
		response.setErrorMessage(errorMessage);
		return response;
	}

	public static boolean isSuccess(String result) {
		JSONObject jsonObject = JSON.parseObject(result);
		String errcode = jsonObject.getString("errcode");
		if (StringUtils.isNotEmpty(errcode)) {
			if ("0".equals(errcode)) {
				return true;
			}
			return false;
		}
		return true;
	}

	/**
	 * 以JSON格式渲染 HttpServeletResponse <br>
	 * <i>默认以UTF-8渲染</i>
	 *
	 * @param jsonStr
	 *            目标字符串
	 * @param response
	 *            HttpServletResponse对象
	 * @param charset
	 *            目标渲染字符集
	 */
	public static void renderJson(String jsonStr, HttpServletResponse response,
								  String charset) {
		try {
			if(StringUtils.isBlank(charset)){
				charset="UTF-8";
			}
			response.setContentType("application/json;charset=" + charset);
			response.getWriter().write(JSON.toJSONString(jsonStr));
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
