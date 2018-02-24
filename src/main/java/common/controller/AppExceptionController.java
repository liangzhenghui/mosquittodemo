package common.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import common.constant.ErrorCode;
import common.exceptions.BizException;
import common.model.Response;

/**
 * 因为ExceptionFilter会捕获到系统的所以错误,处理方式不符合APP的形式，
 * 所以通过@ExceptionHandler在Controller级别就捕获了错误来处理 专门处理app错误信息返回 errorCode=0正常
 * errorCode=-1系统错误
 * 
 * @author liangzhenghui
 *
 */
public abstract class AppExceptionController {
	private static final Logger log = Logger.getLogger(AppExceptionController.class.getName());

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Response handleException(Exception ex,HttpServletRequest req) {
		String queryString=req.getQueryString();
		String url="";
		if(StringUtils.isNotBlank(queryString)){
			url=req.getRequestURI()+"?"+req.getQueryString();
		}else{
			url=req.getRequestURI();
		}
		Response kyResp = new Response();
		if (ex instanceof BizException) {
			kyResp.setError(Boolean.TRUE);
			kyResp.setErrorType(((BizException) ex).getCode());
			// 用于前台提示
			kyResp.setErrorMessage(((BizException) ex).getMsg());
			// 用于查看错误信息
			kyResp.setErrorDetail(((BizException) ex).getMsg());
			// spring validation校验出错
		} else if (ex instanceof MethodArgumentNotValidException) {
			BindingResult bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
			//String errorMesssage = "Invalid Request:\n";
			String errorMesssage = "";
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errorMesssage += fieldError.getDefaultMessage() + "\n";
			}
			kyResp.setError(Boolean.TRUE);
			kyResp.setErrorType(ErrorCode.PARAM_INVALID);
			// 用于前台提示
			kyResp.setErrorMessage(errorMesssage);
			// 用于查看错误信息
			kyResp.setErrorDetail(errorMesssage);
		} else {
			kyResp.setError(Boolean.TRUE);
			kyResp.setErrorType("-1");
			// 用于前台提示
			kyResp.setErrorMessage("系统出错了,请联系管理员");
			// 用于查看错误信息
			kyResp.setErrorDetail("系统出错了,请联系管理员");
		}
		log.error("执行类:"+this.getClass().getSimpleName()+"|请求接口:"+url,ex);
		ex.printStackTrace();
		return kyResp;
	}
}
