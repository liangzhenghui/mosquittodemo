package common.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author liangzhenghui
 * @date NOV 26, 2013 21:35:40 PM 菜单树实体
 */
public class DepartmentTree {

	// 本身id
	private String id;
	// 父亲id
	private String pid;
	// 显示在ztree树上面的字段
	private String name;
	// 勾选状态
	private Boolean checked = false;

	/**
	 * @param id
	 * @param pid
	 * @param name
	 */
	public DepartmentTree(String id, String pid, String name) {
		this.id = id;
		this.pid = pid;
		this.name = name;
	}

	/**
	 * 
	 */
	public DepartmentTree() {
		// TODO Auto-generated constructor stub
	}

	@JSONField(name = "id")
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

	@JSONField(name = "checked")
	public Boolean getChecked() {
		return checked;
	}


	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	@JSONField(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
