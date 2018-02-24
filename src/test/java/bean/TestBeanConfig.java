package bean;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import common.dao.JdbcService;
import web.service.UserService;

public class TestBeanConfig {
	@Test
	public void getBean(){
		  ApplicationContext ctx = new ClassPathXmlApplicationContext(
		            new String[]{"mvc-dispatcher-servlet.xml","spring-database.xml","spring-aop.xml"});
		  UserService userService=(UserService) ctx.getBean("userService");
		  System.out.println("--------------");
	}
}
