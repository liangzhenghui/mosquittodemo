package web.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class ProductImage implements RowMapper{
	private String id;
	private String originName;
	private String type;
	private String title;
	private String description;
	private String fileName;
	//判断是否为滚动图片,默认为0,1为滚动图片
	private String rollingImage;
	//判断是否为封面图片,默认为0,1位封面图片
	private String coverImage;
	//判断是否为首页底部图片,默认为0,1位封面图片
	private String homeImage;
	public String getId() {
		return id;
	}

	public String getOriginName() {
		return originName;
	}

	public String getType() {
		return type;
	}

	public String getFileName() {
		return fileName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		ProductImage file= new ProductImage();
		file.setId(rs.getString("id"));
		file.setOriginName(rs.getString("origin_name"));
		file.setType(rs.getString("type"));
		file.setFileName(rs.getString("file_name"));
		file.setTitle(rs.getString("title"));
		file.setDescription(rs.getString("description"));
		file.setCoverImage(rs.getString("cover_image"));
		file.setRollingImage(rs.getString("rolling_image"));
		file.setHomeImage(rs.getString("home_image"));
		return file;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRollingImage() {
		return rollingImage;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public void setRollingImage(String rollingImage) {
		this.rollingImage = rollingImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}

	public String getHomeImage() {
		return homeImage;
	}

	public void setHomeImage(String homeImage) {
		this.homeImage = homeImage;
	}

}
