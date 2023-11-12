package kolesov.maxim.viewer.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageListener {

    @KafkaListener(containerFactory = "kafkaListenerContainerFactory", topics = "messages")
    public void onMessage(String message) {
        log.info("Received message: {}", message);
    }

}
