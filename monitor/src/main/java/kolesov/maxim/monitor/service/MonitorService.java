package kolesov.maxim.monitor.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonitorService {

    private final LaunchService launchService;

    @Value("${heartbeat.file}")
    private Path heartbeatPath;

    @SneakyThrows
    @Scheduled(cron = "${heartbeat.period}")
    public void monitor() {
        log.debug("Check server status");
        try {
            int count = 5;
            do {
                List<String> list = Files.readAllLines(heartbeatPath);
                if (list.isEmpty()) {
                    SECONDS.sleep(1);
                    continue;
                }
                Files.writeString(heartbeatPath, "",
                        StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
                break;
            } while (--count > 0);

            if (count == 0) {
                log.info("Server is down! Restarting");
                launchService.launch();
            }
        } catch (IOException e) {
            log.info("Failed to read file! Restarting");
            launchService.launch();
        }
    }

    @EventListener(ApplicationStartedEvent.class)
    public void start() {
        launchService.launch();
    }

}
