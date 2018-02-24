package common.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JdbcService {

	private JdbcTemplate jdbcTemplate;
	private static Logger log = Logger.getLogger(JdbcService.class);

	public int update(String sql, Object[] args, int[] argTypes) {
		return jdbcTemplate.update(sql, args, argTypes);
	}
	
	public Map queryForSingleRow(String sql, Object[] args) {
		Map map = null;
		List list = queryForList(sql,args);
		if(null!=list&&list.size()>0){
			map = (Map)list.get(0);
		}
		return map;
	}

	public List queryForList(String sql, Object[] args, RowMapper rowMapper) {
		return jdbcTemplate.query(sql, args, rowMapper);
	}

	public List queryForList(String sql, Object[] args) {
		return jdbcTemplate.queryForList(sql, args);
	}

	public Object queryForObject(String sql, RowMapper rowMapper) {
		return jdbcTemplate.queryForObject(sql, rowMapper);
	}
	// 返回记录的条数
	public int count(String sql, Object[] args) {
		return jdbcTemplate.queryForObject(sql,args,Integer.class);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		/*log.info("jdbcTemplate is set!");*/
	}
}
