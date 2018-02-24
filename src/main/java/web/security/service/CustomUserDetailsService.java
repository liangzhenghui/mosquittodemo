package web.security.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import web.model.User;
import web.model.UserAuth;
import web.service.UserService;
public class CustomUserDetailsService extends JdbcDaoImpl {
	private static Logger LOG = Logger.getLogger(CustomUserDetailsService.class);
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.getUser(username);
		if (user == null) {
			throw new UsernameNotFoundException("user not found");
		}
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		List<UserAuth> userAuthList = userService.queryAuthList(user.getUser_id());
		if (userAuthList != null) {
			for (UserAuth userAuth : userAuthList) {
				authorities.add(new SimpleGrantedAuthority("ROLE_" + userAuth.getAuth_code()));
				LOG.info("loginName:{},authCode:{}"+user.getLogin_name()+userAuth.getAuth_code());
			}
		}
		return new org.springframework.security.core.userdetails.User(user.getLogin_name(), user.getPassword(),
				authorities);
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
