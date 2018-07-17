package com.example.activemq.queue;

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
        QueueConnection connection = null;
        QueueSession session = null;
        try {
            // 创建链接工厂
            QueueConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);
            // 通过工厂创建一个连接
            connection = factory.createQueueConnection();
            // 启动连接
            connection.start();
            // 创建一个session会话
            session = connection.createQueueSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 创建一个消息队列
            Queue queue = session.createQueue(DESTINATION);
            // 创建消息消费者
            QueueReceiver receiver = session.createReceiver(queue);

            receiveMessage(receiver);
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


    private void receiveMessage(QueueReceiver receiver) throws Exception  {
        receiver.setMessageListener(new MessageListener() {
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
