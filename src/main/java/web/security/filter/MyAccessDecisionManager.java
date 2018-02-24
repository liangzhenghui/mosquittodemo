package web.security.filter;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class MyAccessDecisionManager implements AccessDecisionManager {

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
		if(configAttributes == null) {
			return;
		}
		
		System.out.println("MyAccessDecisionManager decide object : " + object.toString());
		
		for(ConfigAttribute ca : configAttributes) {
			String needRole = ((SecurityConfig)ca).getAttribute();
			if(needRole.equals("ROLE_ANONYMOUS")) {
				return;
			}
			
			for(GrantedAuthority ga : authentication.getAuthorities()) {
				if(needRole.equals(ga.getAuthority())) {
					return;
				}
			}
		}
		
		throw new AccessDeniedException("Intercept!!!!!");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
	
}
