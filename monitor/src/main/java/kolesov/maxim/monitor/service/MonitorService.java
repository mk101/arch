package kolesov.maxim.monitor.service;

import feign.FeignException;
import kolesov.maxim.monitor.client.ServerClient;
import kolesov.maxim.monitor.dto.ServerHealthDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonitorService {

    private final ServerClient client;
    private final LaunchService launchService;

    @Scheduled(cron = "${ping-time}")
    public void monitor() {
        log.debug("Check server status");
        try {
            ServerHealthDto health = client.health();
            if (!health.getStatus().equals(ServerHealthDto.UP)) {
                log.info("Server is down! Restarting");
                launchService.launch();
            }
        } catch (FeignException e) {
            log.info("Failed to connect to server! Restarting");
            launchService.launch();
        }
    }

    @EventListener(ApplicationStartedEvent.class)
    public void start() {
        launchService.launch();
    }

}
