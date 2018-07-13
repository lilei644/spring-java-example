
/**
 * rabbitmq 示例
 */
public class RabbitmqExample {

    public final static String QUEUE_NAME = "TestQueue";
    public final static String EXCHANGE_NAME = "TestExchange";
    public final static String USER_NAME = "admin";
    public final static String USER_PASS = "lilei@123789";


    public static void main(String[] args) throws Exception {
        SendMessage sendMessage = new SendMessage();
        ReceiveMessage receiveMessage = new ReceiveMessage();

        // 开始监听
        receiveMessage.startReceive();

        // 循环发送消息
        for (int i = 0; i < 200; i++) {
            sendMessage.sendMessage("Hello world" + i);
        }
        sendMessage.stopConnect();

    }

}
