package kolesov.maxim.monitor.service;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
@Service
@RequiredArgsConstructor
public class LaunchService {

    private final ProcessBuilder processBuilder;

    private Process serverProcess;

    @SneakyThrows
    public void launch() {
        destroyProcess();

        log.info("Start server...");
        serverProcess = processBuilder.start();
        SECONDS.sleep(2);
        if (!serverProcess.isAlive()) {
            throw new IllegalStateException("Server is not started");
        }
        log.info("Complete");
    }

    @PreDestroy
    public void destroyProcess() {
        if (serverProcess != null && serverProcess.isAlive()) {
            log.info("Showdown server");
            serverProcess.destroy();
        }
    }

}
