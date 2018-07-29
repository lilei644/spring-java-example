import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 接收消息的类
 */
public class ReceiveMessage {

    private Connection connection;
    private Channel channel;

    public ReceiveMessage() throws Exception {
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
     * 开始监听
     *
     * @throws Exception
     */
    public void startReceive() throws Exception {
        //声明一个随机名字的队列
//        String queueName = channel.queueDeclare().getQueue();
//        //绑定队列到路由器上
//        channel.queueBind(queueName, EXCHANGE_NAME, "black.*");
//        channel.queueBind(queueName, EXCHANGE_NAME, "orange.*");

        // b 为true开启队列持久化
        channel.queueDeclare(RabbitmqExample.QUEUE_NAME, true, false, false, null);

        // 绑定队列到路由上
        channel.queueBind(RabbitmqExample.QUEUE_NAME, RabbitmqExample.EXCHANGE_NAME, "black.*");
        //    channel.queueBind(RabbitmqExample.QUEUE_NAME, RabbitmqExample.EXCHANGE_NAME, "orange.*");

        // 消费者每次只接受一条消息，回复成功了之后再发送第二条
        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) {
                String message = null;
                try {
                    message = new String(body, "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                    // 休眠模拟耗时任务
                    //    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 制造报错
//                System.out.println(1/0);

                // 回传接收处理成功
                try {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        // 第二个参数为true的话则只发送，为false则需要手动回复成功，为了确保消息准确送达
        channel.basicConsume(RabbitmqExample.QUEUE_NAME, false, consumer);
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
