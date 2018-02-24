package web.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

import common.dao.JdbcService;
import common.util.Im4Java;
import common.util.UUIDGenerator;
import web.model.ProductImage;

public class FileService {
	private JdbcService jdbcService;

	public int saveFile(String bussiness_id, String origin_name, String fileName, String type) {
		String id = UUID.randomUUID().toString();
		String sql = "insert into s_file(id,bussiness_id,origin_name,file_name,type,create_time) values(?,?,?,?,?,?)";
		return jdbcService.update(sql, new Object[] { id, bussiness_id, origin_name, fileName, type, new Date() },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
						Types.TIMESTAMP });
	}

	public void saveImageInfoToDB(byte[] bytes) {
		String sql = "insert into s_file(id,file_name,content,create_time) values(?,?,?,?)";
		jdbcService.update(sql, new Object[] { UUIDGenerator.generateUUID(), "test", bytes, new Date() },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.BLOB, Types.TIMESTAMP });

	}

	/**
	 * 根据输入流保存文件
	 * 
	 * @param inputStream
	 * @param filePath
	 * @param fileName
	 */
	public void saveInputStreamToFile(InputStream inputStream, String filePath, String fileName) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePath + File.separator + fileName);

			byte[] buffer = new byte[1024];
			int byteread = 0;
			while ((byteread = inputStream.read(buffer)) != -1) {
				fos.write(buffer, 0, byteread);
				fos.flush();
			}
			fos.close();
			inputStream.close();
			fos.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void saveThumbnail(int width, int height, String src, String dest) {
		Im4Java.resizeImage(width, height, src, dest);
	}

	public Map getContentByBussinessId(String bussinessId) {
		String sql = "select t.content from s_file t where t.bussiness_id=? and t.delete_flag='0' order by t.create_time asc";
		List list = jdbcService.queryForList(sql, new Object[] { bussinessId });
		Map map = null;
		if (null != list && list.size() > 0) {
			map = (Map) list.get(0);
		}
		return map;
	}

	/**
	 * 根据产品id获取产品对应的封面图片
	 * 
	 * @param bussinessId
	 * @return
	 */
	public Map getCoverImageByBussinessId(String bussinessId) {
		String sql = "select t.content from s_file t where t.bussiness_id=?  and t.cover_image='1' and t.delete_flag='0' ";
		return jdbcService.queryForSingleRow(sql, new Object[] { bussinessId });
	}

	public Map getContentByFileId(String imgId) {
		String sql = "select t.content from s_file t where t.id=? and t.delete_flag='0' order by t.create_time asc";
		List list = jdbcService.queryForList(sql, new Object[] { imgId });
		Map map = null;
		if (null != list && list.size() > 0) {
			map = (Map) list.get(0);
		}
		return map;
	}

	public List getFilesByBussinessId(String id) {
		String sql = "select t.id,t.title,t.description from s_file t where t.bussiness_id=? and t.delete_flag='0' order by t.create_time asc";
		List list = jdbcService.queryForList(sql, new Object[] { id });
		return list;
	}

	public List getImagesOfProductByPage(int page, int size, String productId) {
		String sql = "select * from (select * from s_file where bussiness_id=? and delete_flag='0' order by create_time asc) t limit ?,?";
		Object[] args = new Object[] { productId, (page - 1) * size, size };
		return jdbcService.queryForList(sql, args, new ProductImage());
	}

	public int getImagesOfProductCount(String productId) {
		String sql = "select count(*) from s_file where bussiness_id = ? and delete_flag='0'";
		Object[] args = new Object[] { productId };
		return jdbcService.count(sql, args);
	}

	public Map getFileById(String imageId) {
		String sql = "select * from s_file where id=? and delete_flag='0'";
		return jdbcService.queryForSingleRow(sql, new Object[] { imageId });
	}

	public int editImage(JSONObject json) {
		int result = 0;
		String rollingImage = json.getString("rollingImage");
		String coverImage = json.getString("coverImage");
		String homeImage = json.getString("homeImage");
		String title = json.getString("title");
		String productId = json.getString("productId");
		String imageId = json.getString("imageId");
		String description = json.getString("description");
		if (StringUtils.isNotBlank(coverImage) && coverImage.equals("1")) {
			cancelAllCoverImagesOfProductId(productId);
		}
		String sql = "update s_file t set t.title=?,t.description=?,home_image=?,rolling_image=?,cover_image=? where t.id=? and t.delete_flag='0'";
		result = jdbcService.update(sql,
				new Object[] { title, description, homeImage, rollingImage, coverImage, imageId },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR });
		return result;
	}

	public int cancelAllCoverImagesOfProductId(String productId) {
		String sql = "update s_file t set t.cover_image='0' where t.bussiness_id=? and t.delete_flag='0'";
		return jdbcService.update(sql, new Object[] { productId }, new int[] { Types.VARCHAR });
	}

	public List getAllScrollingImages() {
		String sql = "select id,bussiness_id from s_file where  rolling_image='1' and delete_flag='0'";
		Object[] args = new Object[] {};
		return jdbcService.queryForList(sql, args);
	}

	public List getAllHomeImages() {
		String sql = "select id,bussiness_id from s_file where  home_image='1' and delete_flag='0'";
		Object[] args = new Object[] {};
		return jdbcService.queryForList(sql, args);
	}

	public int deleteImage(String id) {
		String sql = "delete from s_file where id= ?";
		return jdbcService.update(sql, new Object[] { id }, new int[] { Types.VARCHAR });
	}

	public JdbcService getJdbcService() {
		return jdbcService;
	}

	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}

	/**
	 * 获取产品的所有图片
	 * 
	 * @param productId
	 * @return
	 */
	public List getImagesOfProduct(String productId) {
		String sql = "select * from s_file where bussiness_id=? and delete_flag='0' order by create_time asc";
		Object[] args = new Object[] { productId };
		return jdbcService.queryForList(sql, args, new ProductImage());
	}

	public Map getProductImageInfo(String imgId) {
		String sql = "select * from s_file where bussiness_id=? and delete_flag='0' order by create_time asc";
		return jdbcService.queryForSingleRow(sql, new Object[] { imgId });
	}
	/**
	 * 保存缩略图
	 * @param filePath
	 * @param fileName
	 */
	public void saveThumbnail(String filePath, String fileName) {
		// 压缩到small medium large 3个文件夹中
		File smallDestFile = new File(filePath + File.separator + "small");
		File mediumDestFile = new File(filePath + File.separator + "medium");
		File largeDestFile = new File(filePath + File.separator + "large");
		if (!smallDestFile.exists()) {
			smallDestFile.mkdir();
		}
		if (!mediumDestFile.exists()) {
			mediumDestFile.mkdir();
		}
		if (!largeDestFile.exists()) {
			largeDestFile.mkdir();
		}
		String srcPath = filePath + File.separator + fileName;
		String smallDestFilePath = smallDestFile.getAbsolutePath() + File.separator + fileName;
		String mediumDestFilePath = mediumDestFile.getAbsolutePath() + File.separator + fileName;
		String largeDestFilePath = largeDestFile.getAbsolutePath() + File.separator + fileName;
		Map<String, Object> imageInfo = Im4Java.getImageInfo(srcPath);
		int width = (int) imageInfo.get("width");
		int height = (int) imageInfo.get("height");
		saveThumbnail(150, 150, srcPath, smallDestFilePath);
		if (width > 300 || height > 300) {
			saveThumbnail(300, 300, srcPath, mediumDestFilePath);
		}
		if (width > 1024 || height > 1024) {
			saveThumbnail(1024, 1024, srcPath, largeDestFilePath);
		}
	}

}
