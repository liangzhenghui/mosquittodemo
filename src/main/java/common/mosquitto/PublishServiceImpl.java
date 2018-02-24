package common.mosquitto;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

/**
 *  推送服务类
 *  @date   2018年2月7日
 *	@author liangzhenghui
 */
public class PublishServiceImpl implements Ipublish,MqttCallback {
	boolean quietMode 	= false;
	//Quality of Service
	/** from https://mosquitto.org/man/mqtt-7.html
	 *  MQTT defines three levels of Quality of Service (QoS). The QoS defines how hard the broker/client will try to ensure that a message is received. Messages may be sent at any QoS level, and clients may attempt to subscribe to topics at any QoS level. This means that the client chooses the maximum QoS it will receive. For example, if a message is published at QoS 2 and a client is subscribed with QoS 0, the message will be delivered to that client with QoS 0. If a second client is also subscribed to the same topic, but with QoS 2, then it will receive the same message but with QoS 2. For a second example, if a client is subscribed with QoS 2 and a message is published on QoS 0, the client will receive it on QoS 0.

		Higher levels of QoS are more reliable, but involve higher latency and have higher bandwidth requirements.
		
		0: The broker/client will deliver the message once, with no confirmation.
		
		1: The broker/client will deliver the message at least once, with confirmation required.
		
		2: The broker/client will deliver the message exactly once by using a four step handshake.
		 至多一次”，消息发布完全依赖底层 TCP/IP 网络。会发生消息丢失或重复。这一级别可用于如下情况，环境传感器数据，丢失一次读记录无所谓，因为不久后还会有第二次发送。
		“至少一次”，确保消息到达，但消息重复可能会发生。
		“只有一次”，确保消息到达一次。这一级别可用于如下情况，在计费系统中，消息重复或丢失会导致不正确的结果。
		为了满足不同的场景，MQTT支持三种不同级别的服务质量（Quality of Service，QoS）为不同场景提供消息可靠性：
		级别0：尽力而为。消息发送者会想尽办法发送消息，但是遇到意外并不会重试。
		级别1：至少一次。消息接收者如果没有知会或者知会本身丢失，消息发送者会再次发送以保证消息接收者至少会收到一次，当然可能造成重复消息。
		级别2：恰好一次。保证这种语义肯定会减少并发或者增加延时，不过丢失或者重复消息是不可接受的时候，级别2是最合适的。
		服务质量是个老话题了。级别2所提供的不重不丢很多情况下是最理想的，不过往返多次的确认一定对并发和延迟带来影响。级别1提供的至少一次语义在日志处理这种场景下是完全OK的，所以像Kafka这类的系统利用这一特点减少确认从而大大提高了并发。级别0适合鸡肋数据场景，食之无味弃之可惜，就这么着吧。
	 	结论是使用2
	 */
	int qos = 2;
	/**
	 * The MqttClient class (MQTTClient_create or MQTTAsync_create in C) sets the client identifier. 
	 * Call the class constructor to set the client identifier as a parameter, 
	 * or return a randomly generated client identifier. The client identifier must be unique across all clients that connect to the server, 
	 * and must not be the same as the queue manager name on the server. 
	 * All clients must have a client identifier, even if it is not used for identity checking.
	 *  See Client identifier.
	 */		
	boolean ssl = false;
	// Private instance variables
	private MqttClient 			client;
	private MqttConnectOptions 	conOpt;
	private String brokerUrl="";
	private int port=1883;
	private String clientId="";
	/**
	 * 把证书加入到客户端受信任的keystore中
	 * keytool -import -alias serverkey -file server.crt -keystore tclient.keystore
	 * Constructs an instance of the sample client wrapper
	 * @param brokerUrl the url of the server to connect to
	 * @param clientId the client id to connect with
	 * @param cleanSession clear state at end of connection or not (durable or non-durable subscriptions)
	 * @param quietMode whether debug should be printed to standard out
     * @param userName the username to connect with
     * @param password the password for the user
	 * @throws MqttException
	 */
	public PublishServiceImpl(String brokerUrl, String clientId, boolean cleanSession, boolean quietMode, String userName, String password,boolean ssl){
		try {
			conOpt = new MqttConnectOptions();
			String protocol = "tcp://";
			System.out.println("---------------enter construct method----------------");
		    if (ssl) {
		      protocol = "ssl://";
		      port=8883;
		      //for jsk format
		      /*System.getProperties().put("javax.net.ssl.keyStore","D:\\liangzhenghui\\fsl\\target\\classes\\client.crt");
		      System.getProperties().put("javax.net.ssl.keyStorePassword", "85574999");
		      System.getProperties().put("javax.net.ssl.trustStore","D:\\liangzhenghui\\fsl\\target\\classes\\cas.crt");*/
		     /* final String caCrtFile, final String crtFile, final String keyFile, 
              final String password*/
		      conOpt.setSocketFactory(SslUtil.getSocketFactory());
		    }
		    this.clientId=clientId;
		    brokerUrl = protocol + brokerUrl + ":" + port;
		    this.brokerUrl=brokerUrl;
	    		// Construct the connection options object that contains connection parameters
	    		// such as cleanSession and LWT
		    	conOpt.setCleanSession(cleanSession);
		    	if(StringUtils.isNotBlank(password)) {
		    	  conOpt.setPassword(password.toCharArray());
		    	}
		    	if(StringUtils.isNotBlank(userName)) {
		    	  conOpt.setUserName(userName);
		    	}
	    		// Construct an MQTT blocking mode client
				//client = new MqttClient(brokerUrl,clientId, dataStore);
				//java callback可以参照android里面的写法
				// Set this wrapper as the callback handler
		    	//client.setCallback(this);
		} catch (Exception e) {
			e.printStackTrace();
			log("Unable to set up client: "+e.toString());
		}
	}
	@Override
	public void publish(String topicName, int qos, byte[] payload) throws MqttException {
		//This sample stores in a temporary directory... where messages temporarily
    	// stored until the message has been delivered to the server.
    	//..a real application ought to store them somewhere
    	// where they are not likely to get deleted or tampered with
    	String tmpDir = System.getProperty("java.io.tmpdir");
    	MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);
		// Construct an MQTT blocking mode client
		client = new MqttClient(brokerUrl,clientId, dataStore);
		//java callback可以参照android里面的写法
		// Set this wrapper as the callback handler
    	client.setCallback(this);
		// Connect to the MQTT server
    	if(!client.isConnected()){
    		log("Connecting to "+brokerUrl + " with client ID "+client.getClientId());
    		client.connect(conOpt);
    		log("Connected");
    	}
    	String time = new Timestamp(System.currentTimeMillis()).toString();
    	log("Publishing at: "+time+ " to topic \""+topicName+"\" qos "+qos);

    	// Create and configure a message
   		MqttMessage message = new MqttMessage(payload);
    	message.setQos(qos);

    	// Send the message to the server, control is not returned until
    	// it has been delivered to the server meeting the specified
    	// quality of service.
    	client.publish(topicName, message);
    	// Disconnect the client
    	client.disconnect();
    	log("Disconnected");
	}
	

    /**
     * Utility method to handle logging. If 'quietMode' is set, this method does nothing
     * @param message the message to log
     */
    private void log(String message) {
    	if (!quietMode) {
    		System.out.println(message);
    	}
    }

	@Override
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// Called when a message arrives from the server that matches any
		// subscription made by the client
		String time = new Timestamp(System.currentTimeMillis()).toString();
		System.out.println("Time:\t" +time +
                           "  Topic:\t" + topic +
                           "  Message:\t" + new String(message.getPayload()) +
                           "  QoS:\t" + message.getQos());
		log("messageArrived");
		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// Called when a message has been delivered to the
				// server. The token passed in here is the same one
				// that was passed to or returned from the original call to publish.
				// This allows applications to perform asynchronous
				// delivery without blocking until delivery completes.
				//
				// This sample demonstrates asynchronous deliver and
				// uses the token.waitForCompletion() call in the main thread which
				// blocks until the delivery has completed.
				// Additionally the deliveryComplete method will be called if
				// the callback is set on the client
				//
				// If the connection to the server breaks before delivery has completed
				// delivery of a message will complete after the client has re-connected.
				// The getPendingTokens method will provide tokens for any messages
				// that are still to be delivered.
		log("deliveryComplete");
	}
/*	@Override
	public void disconnect() {
		try {
			client.disconnect();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}*/

}
