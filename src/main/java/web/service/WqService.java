package web.service;

import java.sql.Types;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import common.dao.JdbcService;
import common.util.UUIDGenerator;
@Service
public class WqService {
	@Resource
	private JdbcService jdbcService;
	/**
	 * 密码默认123456,未加密
	 * @param map
	 */
	public void bm(Map<String, Object> map) {
		/*String ld = (String) map.get("ld");
		String fh = (String) map.get("fh");
		String rs = (String) map.get("rs");
		String sjh = (String) map.get("sjh");
		String xm = (String) map.get("xm");
		String sql = "insert into users(id,userid,username,fh,ld,sjh,rs,password) values(?,?,?,?,?,?,?,?)";
		jdbcService.update(sql, new Object[] { UUIDGenerator.generateUUID(), sjh, xm, fh, ld, sjh, rs,"123456" },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,
						Types.VARCHAR });*/
	}
	
	public boolean isExists(Map<String, Object> map){
		/*String ld = (String) map.get("ld");
		String fh = (String) map.get("fh");
		String rs = (String) map.get("rs");
		String sjh = (String) map.get("sjh");
		String xm = (String) map.get("xm");
		String sql = "select * from users where ld=? and fh=?";
		Map resultMap = jdbcService.queryForSingleRow(sql, new Object[]{ld,fh});
		return resultMap==null?false:true;*/
		return false;
	}
}
