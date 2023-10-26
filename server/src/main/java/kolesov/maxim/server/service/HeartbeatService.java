package kolesov.maxim.server.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Slf4j
@Service
public class HeartbeatService {

    private static final String MESSAGE = "PING";

    @Value("${heartbeat.file}")
    private Path filePath;

    @SneakyThrows
    @Scheduled(cron = "${heartbeat.period}")
    public void ping() {
        log.debug("Ping");
        Files.writeString(filePath, MESSAGE,
            StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
    }

}
