package mosquittojavaclienttest;

import javax.annotation.Resource;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import common.mosquitto.PublishServiceImpl;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:applicationContext-service.xml",
		"classpath:spring-aop.xml",
		"classpath:spring-database.xml"})
public class TestMosquittoClient extends AbstractTransactionalJUnit4SpringContextTests {
	@Resource
	private PublishServiceImpl publishServiceImpl;
	@Test
	public void publish(){
		String topicName="pubTopic/";
		int qos=2;
		
		String message1="0000000000000  2018 is coming";
		try {
			for(int i=0;i<1000;i++){
				String message="pubTopic  2018 is coming"+i;
				publishServiceImpl.publish(topicName+i, qos, message.getBytes());
				System.out.println(i+"---------------");
			}
			//publishServiceImpl.disconnect();
			//publishServiceImpl.publish("0000000000000", qos, message1.getBytes());
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
