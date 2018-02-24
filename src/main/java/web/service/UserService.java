package web.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import common.dao.JdbcService;
import web.model.User;
import web.model.UserAuth;

public class UserService {
	private JdbcService jdbcService;

	public User getUser(String username) {
		String sql = "select * from user where login_name=?";
		Map map = (Map) jdbcService.queryForSingleRow(sql, new Object[] { username });
		User user = null;
		if (map != null) {
			user = new User();
			//USER_ID=1, LOGIN_NAME=admin, PASSWORD=123456, GMT_CREATE=2018-01-15 17:44:39.0
			user.setUser_id((long)map.get("USER_ID"));
			user.setLogin_name((String) map.get("LOGIN_NAME"));
			user.setPassword((String) map.get("PASSWORD"));
		}
		return user;
	}

	public List<UserAuth> queryAuthList(long user_id) {
		String sql = "select * from user_auth where user_id=?";
		@SuppressWarnings("unchecked")
		List<UserAuth> list = jdbcService.queryForList(sql, new Object[] { user_id }, new RowMapper<UserAuth>() {
			@Override
			public UserAuth mapRow(ResultSet rs, int index) throws SQLException {
				UserAuth userAuth = new UserAuth();
				userAuth.setAuth_code(rs.getString("AUTH_CODE"));
				userAuth.setUser_id(rs.getLong("USER_ID"));
				userAuth.setUser_auth_id(rs.getLong("USER_AUTH_ID"));
				return userAuth;
			}
		});
		return list;
	}

	public JdbcService getJdbcService() {
		return jdbcService;
	}

	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}
}
