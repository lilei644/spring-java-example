import java.util.Date;

/**
 * kafka示例
 */
public class KafkaExample {

    public final static String TOPIC = "TestTopic";

    public static void main(String[] args) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.startConnect();

        ReceiveMessage receiveMessage = new ReceiveMessage();
        receiveMessage.startConnect();
        // 接收消息
        receiveMessage.receiveMessage();


        for (int i = 0; i < 100; i++) {
            // 发送消息
            sendMessage.sendMessage("hello " + i + " -- " + new Date());
        }
        sendMessage.stop();
    }

}
