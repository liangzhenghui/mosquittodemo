package common.service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

import common.dao.JdbcService;
import common.model.Department;
import common.model.DepartmentTree;
import common.model.User;
import common.util.SpringUtil;
import common.util.UserUtil;
/**
 * 
 * @author liangzhenghui 2013-11-23
 */
public class DepartmentService {

	private JdbcService jdbcService;

	/**
	 * 使用迪米特法则,不应该直接在Jsp或者servlet中使用SpringUtil.getBean("xxxx")获取这个bean的实例
	 * 而是通过DepartmentService.getInstance()获取这个bean的实例
	 * 
	 * @return
	 */
	public static final DepartmentService getInstance() {
		return (DepartmentService) SpringUtil.getBean("departmentService");
	}

	public List getAllDepartments() {
		String sql = "select * from s_framework_department where delete_flag='0'";
		Object[] args = new Object[] {};
		return jdbcService.queryForList(sql, args, new Department());
	}

	public Department getDepartmentById(String id) {
		String sql = "select * from department where id = '" + id + "'";
		List list = jdbcService.queryForList(sql, new Object[] {},
				new Department());
		Department department = null;
		if (list != null && list.size() != 0) {
			department = (Department) list.get(0);
		}
		return department;
	}

	public void createDepartment(String pid, String name) {
		String sql = "insert into department (id, pid, name) values(?,?,?)";
		Object[] args = new Object[] { UUID.randomUUID(), pid, name };
		int[] argTypes = new int[] { Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR };
		jdbcService.update(sql, args, argTypes);

	}

	/**
	 * 是否删除一个机构的时候也将下面的孩子机构全部删掉呢？
	 * 
	 * @param id
	 */
	public void deleteDepartment(String id) {
		String sql = "delete  from department where id = ? ";
		Object[] args = new Object[] { id };
		int[] argTypes = new int[] { Types.VARCHAR };
		jdbcService.update(sql, args, argTypes);
		sql = "select * from department where pid = ?";
		List list = jdbcService.queryForList(sql, args, new Department());
		if (list != null && list.size() > 0) {
			for (Object object : list) {
				Department department = (Department) object;
				if (department != null) {
					deleteDepartment(department.getId());
				}
			}
		}

	}

	public void editDepartment(String id, String pid, String name) {
		String sql = "update department set pid = ? , name = ? where id = '"
				+ id + "'";
		Object[] args = new Object[] { pid, name };
		int[] argTypes = new int[] { Types.VARCHAR, Types.VARCHAR };
		jdbcService.update(sql, args, argTypes);
	}

	/**
	 * 取出所有的菜单，并将菜单放进DepartmentTree以List<DepartmentTree> 的List形式返回去
	 * 
	 * @return
	 */
	public List<DepartmentTree> getDepartmentTree() {
		List departmentList = getAllDepartments();
		List<DepartmentTree> departmentTreeList = new ArrayList<DepartmentTree>();
		for (Object object : departmentList) {
			Department department = (Department) object;
			DepartmentTree departmentTree = new DepartmentTree(
					department.getId(), department.getPid(),
					department.getName());
			departmentTreeList.add(departmentTree);
		}
		return departmentTreeList;
	}

	public void createRootDepartment() {
		String sql = "select count(*) from s_framework_department where id='root_department'";
		int count = jdbcService.count(sql, new Object[] {});
		if (count == 0) {
			String sql1 = "insert into s_framework_department(id,department_name) values(?,?)";
			jdbcService.update(sql1,
					new Object[] { "root_department", "无上级机构" }, new int[] {
							Types.VARCHAR, Types.VARCHAR });
		}
	}

	public List getAllDepartment() {
		String sql = "select * from s_framework_department where delete_flag = '0'";
		return jdbcService.queryForList(sql, new Object[] {}, new Department());
	}

	public boolean departmentIsExits(String department_name) {
		String sql = "select count(*) from s_framework_department where department_name=? and delete_flag='0'";
		int count = jdbcService.count(sql, new Object[] { department_name });
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	public int createDepartment(JSONObject json, HttpServletRequest req) {
		User user = UserUtil.getLoginUser(req);
		String department_name = json.getString("department_name");
		String parentId = json.getString("parentId");
		String parentDepartmentZw = json.getString("parentDepartmentZw");
		String sql = " insert into s_framework_department(id, department_name, parent_id,creator,create_time,parent_department_zw) values(?,?,?,?,?,?)";
		Object[] args = new Object[] { UUID.randomUUID(), department_name,
				parentId, user.getUserid(), new Date(), parentDepartmentZw };
		int[] argTypes = new int[] { Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR };
		return jdbcService.update(sql, args, argTypes);
	}
	
	public List getDepartmentByPage(int page, int size) {
		String sql = "select * from (select * from s_framework_department where delete_flag='0') t limit ?,?";
		Object[] args = new Object[] { (page - 1) * size, size };
		return jdbcService.queryForList(sql, args, new Department());
	}

	public int getCount() {
		String sql = "select count(*) from s_framework_department where delete_flag='0'";
		Object[] args = new Object[] {};
		return jdbcService.count(sql, args);
	}

	public JdbcService getJdbcService() {
		return jdbcService;
	}

	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}
}
