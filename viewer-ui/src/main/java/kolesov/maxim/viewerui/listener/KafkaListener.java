package kolesov.maxim.viewerui.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public class KafkaListener {

    private final Consumer<String> callback;

    private boolean isStop = false;

    public void listen() {
        Thread thread = new Thread(() -> {
            Properties props = new Properties();
            props.setProperty("bootstrap.servers", "localhost:29092");
            props.setProperty("group.id", UUID.randomUUID().toString());
            props.setProperty("enable.auto.commit", "true");
            props.setProperty("auto.commit.interval.ms", "1000");
            props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
                consumer.subscribe(List.of("messages"));
                while (!isStop) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                    for (ConsumerRecord<String, String> record : records) {
                        callback.accept(record.value());
                    }
                }
            }
        });
        thread.setName("kafka-listener");
        thread.setDaemon(true);
        thread.start();
    }

    public void stop() {
        isStop = true;
    }

}
