package common.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * 作为easy-ui的Combobox的数据返回类
 * @author liangzhenghui (http://my.oschina.net/liangzhenghui)
 * @email 715818885@qq.com
 * @date 2014年3月6日  下午10:36:47
 */
public class Dictionary implements RowMapper{
	private String code;
	private String detail;
	private Boolean selected = false;
	public Boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	@Override
	public Object mapRow(ResultSet rs, int arg) throws SQLException {
		Dictionary dictionary = new Dictionary();
		dictionary.setCode(rs.getString("code"));
		dictionary.setDetail(rs.getString("detail"));
		return  dictionary;
	}
}
