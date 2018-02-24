package common.service;

import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import common.dao.JdbcService;
import common.model.Function;
import common.model.User;
import common.util.SpringUtil;
import common.util.UserUtil;

/**
 * @author liangzhenghui
 * @date Aug 20, 2013 2:53:21 PM
 */
public class FunctionService {

	private JdbcService jdbcService;

	/**
	 * 使用迪米特法则,也称为最小知识原则，一个对象应该对其他的对象有最少的了解
	 * 不应该直接在Jsp或者servlet中使用SpringUtil.getBean("xxxx")获取这个bean的实例
	 * 而是通过FunctionService.getInstance()获取这个bean的实例
	 * 
	 * @return
	 */
	public static final FunctionService getInstance() {
		return (FunctionService) SpringUtil.getBean("functionService");
	}

	public List getFunctionByPage(int page, int size) {
		String sql = "select * from (select * from s_framework_function where delete_flag='0') t limit ?,?";
		Object[] args = new Object[] { (page - 1) * size, size };
		return jdbcService.queryForList(sql, args, new Function());
	}

	public List getAllFunction() {
		String sql = "select * from s_framework_function where delete_flag='0'";
		Object[] args = new Object[] {};
		return jdbcService.queryForList(sql, args, new Function());
	}

	public int getCount() {
		String sql = "select count(*) from s_framework_function where delete_flag='0'";
		Object[] args = new Object[] {};
		return jdbcService.count(sql, args);
	}

	public boolean functionIsExits(String functionName) {
		String sql = "select count(*) from s_framework_function where function_name=? and delete_flag='0'";
		int count = jdbcService.count(sql, new Object[] { functionName });
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	public int createFunction(String functionName, String url,
			HttpServletRequest req) {
		User user = UserUtil.getLoginUser(req);
		String sql = " insert into s_framework_function(id, function_name, url,creator,create_time) values(?,?,?,?,?)";
		Object[] args = new Object[] { UUID.randomUUID(), functionName, url,
				user.getUserid(), new Date() };
		int[] argTypes = new int[] { Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP };
		return jdbcService.update(sql, args, argTypes);
	}

	public Function getFunctionById(String functionId) {
		String sql = "select * from s_framework_function where id = ? and delete_flag='0'";
		Object[] args = new Object[] { functionId };
		List list = jdbcService.queryForList(sql, args, new Function());
		Function function = null;
		if (list != null && list.size() > 0) {
			function = (Function) list.get(0);
		}
		return function;
	}

	/**
	 * @param id
	 * @param functionName
	 * @param url
	 * @return
	 */
	public int editFunction(String id, String functionName, String url) {
		String sql = "update s_framework_function set function_name = ?, url = ? where id = ? and delete_flag='0'";
		Object[] args = new Object[] { functionName, url, id };
		int[] argTypes = new int[] { Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR };
		return jdbcService.update(sql, args, argTypes);
	}

	public int deleteFunction(String id) {
		String sql = "update s_framework_function set delete_flag='1' where id=? ";
		Object[] args = new Object[] { id };
		int[] argTypes = new int[] { Types.VARCHAR };
		return jdbcService.update(sql, args, argTypes);
	}

	public JdbcService getJdbcService() {
		return jdbcService;
	}

	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}

}
