package web.security.entrypoint;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.ELRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import common.util.ResponseUtil;

/**
 * 针对认证,如果没认证就访问资源,就通过这个来处理。否则通过BasicAuthenticationEntryPoint,就是直接弹出prompt
 * @author liangzhenghui
 *
 */
public class AppAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

	 private static final RequestMatcher requestMatcher = new ELRequestMatcher(
	            "hasHeader('X-Requested-With','XMLHttpRequest')");


    /**
     * Performs the redirect (or forward) to the login form URL.
     */
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
    	  //判断是否为ajax请求，默认不是  
    	/*boolean isAjaxRequest = false;  
        if(!StringUtils.isBlank(request.getHeader("X-Requested-With")) && request.getHeader("X-Requested-With").equals("XMLHttpRequest")){  
            isAjaxRequest = true;  
        }*/
        if (isRestRequest(request)) {
        	//加上这一句就是开启http 基本认证,会弹出对验证对话框 
        	//response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
             //PrintWriter writer = response.getWriter();
             //writer.println("HTTP Status 401" + authException.getMessage());
             ResponseUtil.renderJson(authException.getMessage(), response, null);
        }else{
        	//重置到登录页面
        	response.sendRedirect(request.getContextPath()+"/403");
        }
    	
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("Baeldung");
        super.afterPropertiesSet();
    }
    /**
     * Checks if it is a rest request
     * @param request
     * @return
     */
    protected boolean isRestRequest(HttpServletRequest request) {
        return requestMatcher.matches(request);
    }

}
