package web.service;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import common.dao.JdbcService;
import common.util.UUIDGenerator;

/**
 * @author: liangzhenghui
 * @blog: http://my.oschina.net/liangzhenghui/blog
 * @email:715818885@qq.com
 * 2015年7月26日 下午11:39:09
 */
public class LbService {

	private JdbcService jdbcService;

	public JdbcService getJdbcService() {
		return jdbcService;
	}

	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}

	public int createPrductLb(String lb, String userid) {
		String id = UUIDGenerator.generateUUID();
		String sql = "insert into s_lb(id,name,creator) values(?,?,?)";
		return jdbcService.update(sql, new Object[]{id,lb,userid}, new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR});
	}
	/**
	 * 根据用户查询用户创建的产品类别
	 * @param userId
	 * @return
	 */
	public List getLbByUserId(String userId){
		String sql = "select t.id as code ,t.name as detail from s_lb t where delete_flag='0' and creator=?  order by t.create_time asc";
		return jdbcService.queryForList(sql, new Object[] {userId});
	}
	
	/**
	 * 判断该用户是否已经有该类别
	 * @param lb
	 * @param userid
	 * @return
	 */
	public boolean isLbExists(String lb, String userid) {
		String sql = "select count(*) from s_lb where name=? and creator=? and delete_flag='0'";
		return jdbcService.count(sql, new Object[]{lb,userid})>0?true:false;
	}

	public List getLbByUserId(int page, int size, String userid) {
		String sql = "select * from s_lb where delete_flag='0' and creator=?   limit ?,?";
		Object[] args = new Object[] { userid,(page - 1) * size, size };
		return jdbcService.queryForList(sql, args);
	}
	
	public List getLb() {
		String sql = "select t.id as code ,t.name as detail from s_lb t where delete_flag='0' order by t.create_time asc";
		return jdbcService.queryForList(sql, new Object[] {});
	}

	public int getLbCountByUserId(String userid) {
		String sql = "select count(*) from s_lb where delete_flag='0' and creator=?";
		Object[] args = new Object[] { userid};
		return jdbcService.count(sql, args);
	}

	public int deleteProductLb(String id) {
		String sql = "delete from s_lb where id=?";
		return jdbcService.update(sql, new Object[]{id}, new int[]{Types.VARCHAR});
	}

	public Map getProductLb(String id) {
		String sql = "select * from s_ap_classification where id=? and delete_flag='0'";
		return jdbcService.queryForSingleRow(sql, new Object[]{id});
	}

	public int EditPrductLb(String lb, String id) {
		String sql = "update  s_lb set name=? where id=?";
		return jdbcService.update(sql, new Object[]{lb,id}, new int[]{Types.VARCHAR,Types.VARCHAR});
	}
}
