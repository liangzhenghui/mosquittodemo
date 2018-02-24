package common.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * 部门实体类
 * @author liangzhenghui
 *
 */
public class Department implements RowMapper{
	private String id;
	private String pid;
	private String name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Department department = new Department();
		department.setId(rs.getString("id"));
		department.setPid(rs.getString("parent_id"));
		department.setName(rs.getString("department_name"));
		return department;
	}
}
