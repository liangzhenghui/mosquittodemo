package common.util;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author liangzhenghui
 * @date Aug 15, 2013 5:39:23 PM
 */
public class SpringUtil implements ApplicationContextAware{
	
	private static Logger log = Logger.getLogger(SpringUtil.class); 
	private static final String[] CONTEXT_PATHS = {
 		"classpath:spring/applicationContext.xml",
 		"classpath:spring/applicationContext-datasource.xml",
         "classpath:spring/applicationContext-service.xml",
         "classpath:spring/applicationContext-aop.xml",
         "classpath:spring/applicationContext-tx.xml"
     };
	
	 private static ApplicationContext ctx;
	 /**
	  * just for Spring init web application context
	  */
	public void setApplicationContext(ApplicationContext webApplicationContext)
			throws BeansException {
		ctx = webApplicationContext;
		log.info("webApplicationContext is set!");
	}
	
	 public static ApplicationContext getApplicationContext() {
	        if (null == ctx) {
	            ctx = new ClassPathXmlApplicationContext(CONTEXT_PATHS);
	        }
	        return ctx;
	 }
	 
	 public static Object getBean(String name) {
	        return getApplicationContext().getBean(name);
	 }

}
