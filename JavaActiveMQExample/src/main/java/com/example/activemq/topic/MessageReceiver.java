package com.example.activemq.topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 点对点queue方式消费者
 */
public class MessageReceiver {

    // tcp 地址
    public static final String BROKER_URL = "tcp://localhost:61616";
    // 目标，在ActiveMQ管理员控制台创建 http://localhost:8161/admin/queues.jsp
    public static final String DESTINATION = "test.mq.queue";

    private void startConnect() throws Exception {
        TopicConnection connection = null;
        TopicSession session = null;
        try {
            // 创建链接工厂
            TopicConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);
            // 通过工厂创建一个连接
            connection = factory.createTopicConnection();
            // 启动连接
            connection.start();
            // 创建一个session会话
            session = connection.createTopicSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 创建一个消息队列
            Topic topic = session.createTopic(DESTINATION);
            // 创建消息消费者
            TopicSubscriber subscriber = session.createSubscriber(topic);

            receiveMessage(subscriber);
//            // 提交会话
//            session.commit();

        } catch (Exception e) {
            throw e;
        } finally {
//            // 关闭释放资源
//            if (session != null) {
//                session.close();
//            }
//            if (connection != null) {
//                connection.close();
//            }
        }
    }


    private void receiveMessage(TopicSubscriber subscriber) throws Exception {
        subscriber.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                MapMessage map = (MapMessage) message;
                try {
                    System.out.println(map.getLong("time") + "接收#" + map.getString("text"));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static void main(String[] ars) throws Exception {
        MessageReceiver messageSender = new MessageReceiver();
        messageSender.startConnect();
    }

}
