package web.model;

public class UserAuth {
	private long user_auth_id;
	private long user_id;
	private String auth_code;
	private String gmt_create;
	public String getAuth_code() {
		return auth_code;
	}
	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}
	public String getGmt_create() {
		return gmt_create;
	}
	public void setGmt_create(String gmt_create) {
		this.gmt_create = gmt_create;
	}
	public long getUser_auth_id() {
		return user_auth_id;
	}
	public void setUser_auth_id(long user_auth_id) {
		this.user_auth_id = user_auth_id;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
}
