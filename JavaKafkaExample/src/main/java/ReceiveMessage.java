import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;


/**
 * 消费者
 */
public class ReceiveMessage {

    public final static String TOPIC = "TestTopic";
    private KafkaConsumer<String, String> consumer;

    public void startConnect() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "TestGroup");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
//        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1000);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList(TOPIC));
    }

    public void receiveMessage() {

        try {
            while (true) {
                ConsumerRecords<String, String> msgList = consumer.poll(1000);
                for (TopicPartition partition : msgList.partitions()) {
                    List<ConsumerRecord<String, String>> records = msgList.records(partition);
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.println("receive message : " + record.toString());
                    }
                    long lastOffset = records.get(records.size() - 1).offset();
                    consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));
                }
            }
        } finally {
            stop();
        }

    }

    public void stop() {
        consumer.close();
    }


    public static void main(String[] args) {
        ReceiveMessage receiveMessage = new ReceiveMessage();
        receiveMessage.startConnect();
        // 接收消息
        receiveMessage.receiveMessage();
    }

}
