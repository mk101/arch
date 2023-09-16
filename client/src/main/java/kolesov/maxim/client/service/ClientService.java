package kolesov.maxim.client.service;

import feign.FeignException;
import kolesov.maxim.client.client.Client;
import kolesov.maxim.client.config.ClientConfig;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final Client client;
    private final ClientConfig config;

    @SneakyThrows
    @EventListener(ApplicationStartedEvent.class)
    public void sendMessage() {
        log.info("Trying to send message '{}'", config.getMessage());

        int count = config.getTryCount();
        do {
            try {
                client.produceMessage(config.getMessage());
                log.info("Done");
                return;
            } catch (FeignException e) {
                log.error("Failed: {}", e.getMessage());
                TimeUnit.SECONDS.sleep(config.getTryDelay());
            }
        } while (--count > 0);

        log.info("Failed to send data. Try later");
    }

}
