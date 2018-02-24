package common.service;

import java.util.List;

import common.dao.JdbcService;
import common.model.Dictionary;
import common.util.SpringUtil;

/**
 * 
 * @author liangzhenghui (http://my.oschina.net/liangzhenghui)
 * @email 715818885@qq.com
 * @date 2014年3月23日 下午4:09:44
 */
public class DictionaryService {

	private JdbcService jdbcService;

	/**
	 * 使用迪米特法则,不应该直接在Jsp或者servlet中使用SpringUtil.getBean("xxxx")获取这个bean的实例
	 * 而是通过DepartmentService.getInstance()获取这个bean的实例
	 * 
	 * @return
	 */
	public static final DictionaryService getInstance() {
		return (DictionaryService) SpringUtil.getBean("dictionaryService");
	}

	public List getDictionaryByKind(String kind) {
		String sql = "select * from s_framework_dictionary where kind=?";
		Object[] args = new Object[] { kind };
		return jdbcService.queryForList(sql, args, new Dictionary());
	}
	

	public Dictionary getDictionaryByKindAndCode(String kind, String code) {
		String sql = "select * from s_framework_dictionary where kind=? and code = ?";
		Object[] args = new Object[] { kind, code };
		List list = jdbcService.queryForList(sql, args,new Dictionary());
		Dictionary dictionary = null;
		if (null != list && list.size() > 0) {
			dictionary = (Dictionary) list.get(0);
		}
		return dictionary;
	}

	public JdbcService getJdbcService() {
		return jdbcService;
	}

	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}

}
