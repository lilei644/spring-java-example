import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 生产者
 */
public class SendMessage {

    private KafkaProducer<String, String> producer;

    public void startConnect() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producer = new KafkaProducer<String, String>(props);
    }

    public void sendMessage(String message) {
        producer.send(new ProducerRecord<String, String>(KafkaExample.TOPIC, "MessageKey", message),
                new Callback() {
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        if (e != null) {
                            System.err.println(">>>>>发送消息失败");
                            e.printStackTrace();
                        } else {
                            System.out.println(">>>>发送消息成功：" + recordMetadata.toString());
                        }
                    }
                });
    }

    public void stop() {
        producer.close();
    }

}
