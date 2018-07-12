import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * rabbitmq 示例
 */
public class RabbitmqExample {

    private final static String QUEUE_NAME = "TestQueue";
    private final static String EXCHANGE_NAME = "TestExchange";
    private Connection connection;
    private Channel channel;
    private int count = 0;

    /**
     * 开始连接
     *
     * @throws Exception
     */
    public void startConnect() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("lilei@123789");
        connection = factory.newConnection();
        channel = connection.createChannel();

        // true开启路由持久化
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC, true);



        // 开始监听
        startReceive();
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
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        // 绑定队列到路由上
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "black.*");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "orange.*");

        // 消费者每次只接受一条消息，回复成功了之后再发送第二条
        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) {
                String message = null;
                try {
                    message = new String(body, "UTF-8");
                    System.out.println(" [x] Received '" + message + "' ---- " + ++count);
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        // 第二个参数为true的话则只发送，为false则需要手动回复成功，为了确保消息准确送达
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }

    /**
     * 开始发送消息
     */
    public void sendMessage(String message) {
        try {

            // PERSISTENT_TEXT_PLAIN发送持久化消息到路由
            channel.basicPublish(EXCHANGE_NAME, "black.xxxx", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
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


    public static void main(String[] args) throws Exception {
        RabbitmqExample rabbitmqExample = new RabbitmqExample();
        rabbitmqExample.startConnect();

        for (int i = 0; i < 2000; i++) {
            rabbitmqExample.sendMessage("Hello world");
        }

    }

}
