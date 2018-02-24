package web.service;

import java.math.BigDecimal;
import java.sql.Types;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

import common.dao.JdbcService;

public class ProductService {
	private JdbcService jdbcService;

	public int deleteClassification(String id) {
		String sql = "update s_ap_classification set delete_flag='1' where id=? ";
		Object[] args = new Object[] { id };
		int result = 0;
		int[] argTypes = new int[] { Types.VARCHAR };
		jdbcService.update(sql, args, argTypes);
		List list = getProductByLbId(id);
		for (Object object : list) {
			Map map = (Map) object;
			String productId = (String) map.get("id");
			if (StringUtils.isNotBlank(productId)) {
				deleteImagesOfProduct(productId);
			}
		}
		// 先干掉儿子，再干掉父亲
		deleteProductByLb(id);
		result = 1;
		return result;
	}

	public int deleteImagesOfProduct(String productId) {
		String sql = "delete from s_file  where bussiness_id=?";
		return jdbcService.update(sql, new Object[] { productId },
				new int[] { Types.VARCHAR });
	}

	public int deleteProductByLb(String lbId) {
		String sql = "update s_ap_product set delete_flag='1' where lb_id=?";
		return jdbcService.update(sql, new Object[] { lbId },
				new int[] { Types.VARCHAR });
	}
	
	

	public int createProduct(String id, JSONObject json) throws ParseException {
		String product_lb_id = json
				.getString("product_lb_id");
		String product_name = json.getString("product_name");
		String product_description = json.getString("product_description");
		BigDecimal price = json.getBigDecimal("price");
		BigDecimal kc = json.getBigDecimal("kc");
		String sql = "insert into b_product(id,lb_id,kc,price,product_name,product_description,create_time) values(?,?,?,?,?,?,?)";
		return jdbcService.update(sql, new Object[] { id,
				product_lb_id,kc, price,
				product_name, product_description,new Date()  }, new int[] { Types.VARCHAR, Types.VARCHAR,Types.DECIMAL,
				Types.DECIMAL, Types.VARCHAR, Types.VARCHAR,Types.TIMESTAMP});

	}

	public int EditProduct(String id, JSONObject json) {
		String product_lb_id = json.getString("lb_id");
		String product_name = json.getString("product_name");
		String product_description = json.getString("product_description");
		BigDecimal price = json.getBigDecimal("price");
		BigDecimal kc = json.getBigDecimal("kc");
		String sql = "update b_product set lb_id=?,price=?,kc=?,product_name=?,product_description=?,update_time=? where id=?";
		return jdbcService.update(sql, new Object[] {
				product_lb_id, price,kc,
				product_name, product_description, new Date(), id }, new int[] {
				Types.VARCHAR,  Types.NUMERIC,Types.NUMERIC, Types.VARCHAR,
				Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR });
	}

	public List getProductByLb(String lb) {
		String sql = "select t.* from s_ap_product t where t.delete_flag='0' and t.lb_zw=? order by t.create_time asc";
		return jdbcService.queryForList(sql, new Object[] { lb });
	}

	public List getProductByLbId(String lbId) {
		String sql = "select t.* from s_ap_product t where t.delete_flag='0' and t.lb_id=?";
		return jdbcService.queryForList(sql, new Object[] { lbId });
	}

	public Map getProductById(String productId) {
		String sql = "select t.* from b_product t where t.delete_flag='0' and t.id=?";
		Map map = jdbcService
				.queryForSingleRow(sql, new Object[] { productId });
		return map;
	}

	public List getProductByPage(int page, int size) {
		String sql = "select * from (select t1.*,t2.name as lbZw from b_product t1  left join b_classification t2 on t1.lb_id=t2.id where t1.delete_flag='0' order by t1.create_time desc) t limit ?,?";
		Object[] args = new Object[] { (page - 1) * size, size };
		return jdbcService.queryForList(sql, args);
	}

	public int getProductCount() {
		String sql = "select count(*) from b_product where delete_flag='0'";
		Object[] args = new Object[] {};
		return jdbcService.count(sql, args);
	}

	public int deleteProduct(String id) {
		String sql = "update b_product set delete_flag='1' where id=?";
		jdbcService.update(sql, new Object[] { id },
				new int[] { Types.VARCHAR });
		deleteImagesOfProduct(id);
		return 1;
	}

	public JdbcService getJdbcService() {
		return jdbcService;
	}

	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}

	public String setCoverImage(String productId) {
		String sql = "select * from s_file t where t.bussiness_id=? and delete_flag='0'";
		List productImages = jdbcService.queryForList(sql, new Object[]{productId});
		int count=0;
		String result = "";
		for(int i=0;i<productImages.size();i++){
			Map map = (Map)productImages.get(i);
			String cover_image = (String)map.get("cover_image");
			if(cover_image.equals("1")){
				count++;
				result=cover_image;
				break;
			}
		}
		if(count==0&&productImages.size()>0){
			Map map = (Map)productImages.get(0);
			result = (String)map.get("file_name");
		}
		return result;
	}
}
