package kolesov.maxim.viewer.service;

import kolesov.maxim.viewer.client.ServerClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InitializeService {

    private final ServerClient client;

    @EventListener(ApplicationStartedEvent.class)
    public void onStarted() {
        log.info("Load old messages");
        client.getAll().forEach(log::info);
        log.info("Complete");
    }

}
