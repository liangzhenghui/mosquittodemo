package common.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author liangzhenghui
 * @date Aug 20, 2013    3:32:12 PM
 */
public class Function implements RowMapper{
	private String id;
	
	private String functionName;
	
	private String url;

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Function function= new Function();
		function.setId(rs.getString("id"));
		function.setFunctionName(rs.getString("function_name"));
		function.setUrl(rs.getString("url"));
		return function;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
