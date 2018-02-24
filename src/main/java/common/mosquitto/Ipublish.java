package common.mosquitto;

import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * 发布接口
 * @author liangzhenghui
 *
 */
public interface Ipublish {
	 public void publish(String topicName, int qos, byte[] payload) throws MqttException;
	 //public void disconnect(); 
}
