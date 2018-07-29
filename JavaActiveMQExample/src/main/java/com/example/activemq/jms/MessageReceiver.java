package com.example.activemq.jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * jms方式消费者
 */
public class MessageReceiver {

    // tcp 地址
    public static final String BROKER_URL = "tcp://localhost:61616";
    // 目标，在ActiveMQ管理员控制台创建 http://localhost:8161/admin/queues.jsp
    public static final String DESTINATION = "test.mq.queue";

    private void startConnect() throws Exception {
        Connection connection = null;
        Session session = null;
        try {
            // 创建链接工厂
            ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);
            // 通过工厂创建一个连接
            connection = factory.createConnection();
            // 启动连接
            connection.start();
            // 创建一个session会话
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 创建一个消息队列
            Destination destination = session.createQueue(DESTINATION);
            // 创建消息消费者
            MessageConsumer consumer = session.createConsumer(destination);

            receiveMessage(consumer);
            // 提交会话
            session.commit();

        } catch (Exception e) {
            throw e;
        } finally {
            // 关闭释放资源
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }


    private void receiveMessage(MessageConsumer consumer) throws Exception {
        while (true) {
            // 接收数据的时间（等待） 100 ms
            Message message = consumer.receive(1000 * 100);

            TextMessage text = (TextMessage) message;
            if (text != null) {
                System.out.println("接收：" + text.getText());
            }
            Thread.sleep(1000);
        }
    }


    public static void main(String[] ars) throws Exception {
        MessageReceiver messageSender = new MessageReceiver();
        messageSender.startConnect();
    }

}
