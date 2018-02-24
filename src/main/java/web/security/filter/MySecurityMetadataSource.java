package web.security.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private static Map<RequestMatcher, Collection<ConfigAttribute>> resourceMap = null;

	public MySecurityMetadataSource() {
		loadResourceDefine();
	}

	// 设置访问资源和权限
	private void loadResourceDefine() {
		//TODO初始化的时候从数据库里面查询放到redis里面。
		resourceMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();
		Collection<ConfigAttribute> atts3 = new ArrayList<ConfigAttribute>();
		ConfigAttribute ca3 = new SecurityConfig("ROLE_ANONYMOUS");
		atts3.add(ca3);
		resourceMap.put(new AntPathRequestMatcher("/login.do*"), atts3);
		resourceMap.put(new AntPathRequestMatcher("/loginFail.do*"), atts3);

		Collection<ConfigAttribute> atts1 = new ArrayList<ConfigAttribute>();
		ConfigAttribute ca = new SecurityConfig("ADMIN");
		atts1.add(ca);
		resourceMap.put(new AntPathRequestMatcher("/admin*"), atts1);
		/*
		 * ConfigAttribute ca1 = new SecurityConfig("USER"); atts2.add(ca1);
		 * resourceMap.put(new AnyRequestMatcher(), atts2);
		 */
		//{Ant [pattern='/login.do*']=[ROLE_ANONYMOUS], Ant [pattern='/loginFail.do*']=[ROLE_ANONYMOUS], Ant [pattern='/admin*']=[ADMIN]}
		System.out.println(resourceMap);
	}

	// 获取访问资源权限
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		HttpServletRequest request = ((FilterInvocation) object).getRequest();
		System.out.println(((FilterInvocation) object).getRequestUrl());
		//TODO resoourceMap
		//Ant [pattern='/login.do*']=[ROLE_ANONYMOUS]
		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
			if (entry.getKey().matches(request))
				return entry.getValue();
		}
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
