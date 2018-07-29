import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * 消息发布者
 */
public class MessageSender {

    public static final String HOST = "tcp://192.168.5.114:1883";
    public static final String TOPIC = "test-topic";
    private static final String clientid = "server";

    private MqttClient client;
    private MqttTopic topic;
    private String userName = "admin";
    private String passWord = "password";

    private MqttMessage message;

    public MessageSender() throws MqttException {
        // MemoryPersistence设置clientid的保存形式，默认为以内存保存
        client = new MqttClient(HOST, clientid, new MemoryPersistence());
        connect();
    }

    private void connect() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
        options.setCleanSession(true);
        // 设置超时时间
        options.setConnectionTimeout(10);
        options.setAutomaticReconnect(true);
        // 设置会话心跳时间
        options.setKeepAliveInterval(20);
        try {
            client.setCallback(new PushCallback());
            client.connect(options);
            topic = client.getTopic(TOPIC);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publish(MqttTopic topic, MqttMessage message) throws MqttPersistenceException,
            MqttException {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
        System.out.println("message is published completely! "
                + token.isComplete());
    }

    public static void main(String[] args) throws MqttException {
        MessageSender sender = new MessageSender();
        sender.message = new MqttMessage();
        sender.message.setQos(2);
        sender.message.setRetained(true);

        for (int i = 0; i < 10; i++) {
            String message = "发送消息：" + i;
            sender.message.setPayload(message.getBytes());
            sender.publish(sender.topic, sender.message);
        }


        System.out.println(sender.message.isRetained() + "------ratained状态");
    }


    class PushCallback implements MqttCallback {

        public void connectionLost(Throwable cause) {
            // 连接丢失后，一般在这里面进行重连
            System.out.println("连接断开，可以做重连");
        }

        public void deliveryComplete(IMqttDeliveryToken token) {
            System.out.println("deliveryComplete---------" + token.isComplete());
        }

        public void messageArrived(String topic, MqttMessage message) throws Exception {
            // subscribe后得到的消息会执行到这里面
            System.out.println("接收消息主题 : " + topic);
            System.out.println("接收消息Qos : " + message.getQos());
            System.out.println("接收消息内容 : " + new String(message.getPayload()));
        }

    }

}
