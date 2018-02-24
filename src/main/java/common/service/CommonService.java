package common.service;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import common.dao.JdbcService;


/**
 * @author: liangzhenghui
 * @blog: http://my.oschina.net/liangzhenghui/blog
 * @email:715818885@qq.com
 * 2015年5月9日 下午7:02:29
 */
public class CommonService {
	
	private JdbcService jdbcService;
	
	/**将query_参数开头的参数的query_部分截取掉,比如query_demo，截取掉之后就是demo
	 * return queryParametersMap for sql
	 * @param req
	 * @return
	 */
	public Map getQueryParametersMap(HttpServletRequest req){
		Enumeration paramNames = req.getParameterNames();  
		Map<String,String> queryMap = new HashMap<String,String>();
        while (paramNames.hasMoreElements()) {  
            String paramName = (String) paramNames.nextElement();  
            String[] paramValues = req.getParameterValues(paramName);  
            if (paramValues.length == 1&&paramName.startsWith("query_")) {  
            	String paramValue = null;
            	if(StringUtils.isNotBlank(paramValues[0])){
            		paramValue = paramValues[0];
            	}
            	paramName = paramName.substring(6, paramName.length());
                queryMap.put(paramName, paramValue);  
            }  
        } 
        return queryMap;
	}
	
	public List queryByPage(String sql,int page,int size,Map<String,String> map,RowMapper rowMapper){
		Object[] args = null;
		if(map.size() > 0) {
			String condition = " where ";
			List<String> list = new ArrayList<String>();
			for(Map.Entry<String, String> entry : map.entrySet()){
				condition += "("+entry.getKey()+"="+"? or ? is null) and ";
				list.add(entry.getValue());
				list.add(entry.getValue());
			}
			int listSize = list.size();
			args = new Object[listSize+2];
			for(int i = 0;i<listSize;i++){
				args[i] = list.get(i);
			}
			args[listSize] = (page - 1) * size;
			args[listSize+1] = size;
			int index = condition.lastIndexOf("and");
			condition = condition.substring(0, index-1);
			sql = sql+condition+" limit ?,?  ";
		}
		else{
			sql +=" limit ?,?";
			args = new Object[]{(page - 1) * size,size};
		}
		return jdbcService.queryForList(sql, args, rowMapper);
	}
	
	public int queryTotalCount(String sql, Map<String,String> map) {
		Object[] args = null;
		if(map.size() > 0) {
			String condition = " where ";
			List<String> list = new ArrayList<String>();
			for(Map.Entry<String, String> entry : map.entrySet()){
				condition += "("+entry.getKey()+"="+"? or ? is null) and ";
				list.add(entry.getValue());
				list.add(entry.getValue());
			}
			int listSize = list.size();
			args = new Object[listSize];
			for(int i = 0;i<listSize;i++){
				args[i] = list.get(i);
			}
			int index = condition.lastIndexOf("and");
			condition = condition.substring(0, index-1);
			sql = sql+condition;
		}
		else{
			args = new Object[]{};
		}
		return jdbcService.count(sql, args);
	}
	
	public JdbcService getJdbcService() {
		return jdbcService;
	}

	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}

}
