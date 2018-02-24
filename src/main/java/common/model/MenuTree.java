package common.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author liangzhenghui
 * @date Aug 24, 2013    7:51:40 PM
 * 菜单树实体
 */
public class MenuTree {
	//本身id
	private String id;
	//父亲id
	private String parentMenu;
	//显示在ztree树上面的字段
	private String menuName;
	//菜单对应的url
	private String url;
	//勾选状态
	private Boolean  checked = false;
	
	
	/**
	 * @param menuId
	 * @param parentMenu
	 * @param menuName
	 */
	public MenuTree(String menuId, String parentMenu, String menuName) {
		this.id = menuId;
		this.parentMenu = parentMenu;
		this.menuName = menuName;
	}
	/**
	 * 
	 */
	public MenuTree() {
		// TODO Auto-generated constructor stub
	}
	@JSONField(name = "id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JSONField(name = "pId")
	public String getParentMenu() {
		return parentMenu;
	}
	public void setParentMenu(String parentMenu) {
		this.parentMenu = parentMenu;
	}
	@JSONField(name = "name")
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	@JSONField(name = "functionUrl")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@JSONField(name = "checked")
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
}
