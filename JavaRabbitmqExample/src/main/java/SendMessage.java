import com.rabbitmq.client.*;

import java.io.IOException;


/**
 * 发送消息的类
 */
public class SendMessage {

    private Connection connection;
    private Channel channel;

    public SendMessage() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername(RabbitmqExample.USER_NAME);
        factory.setPassword(RabbitmqExample.USER_PASS);
        connection = factory.newConnection();
        channel = connection.createChannel();

        // true开启路由持久化
        channel.exchangeDeclare(RabbitmqExample.EXCHANGE_NAME, BuiltinExchangeType.TOPIC, true);

    }


    /**
     * 开始发送消息
     */
    public void sendMessage(String message) {
        try {

            // PERSISTENT_TEXT_PLAIN发送持久化消息到路由
            channel.basicPublish(RabbitmqExample.EXCHANGE_NAME, "black.xxxx", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 断开连接
     *
     * @throws Exception
     */
    public void stopConnect() throws Exception {
        channel.close();
        connection.close();
    }

}
