package com.mkyong.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mkyong.customer.service.CustomerService;

import web.service.TestService;
import web.service.UserService;


public class App 
{
    public static void main( String[] args )
    {
    	ApplicationContext context = 
    		new ClassPathXmlApplicationContext(new String[] {"Spring-AutoScan.xml","spring-database.xml"});

    	UserService cust = (UserService)context.getBean("userService");
    	System.out.println(cust);
    	
    }
}
