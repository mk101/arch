package kolesov.maxim.client.service;

import feign.FeignException;
import kolesov.maxim.client.client.Client;
import kolesov.maxim.client.config.ClientConfig;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final Client client;
    private final ClientConfig config;

    @Value("${login:#{null}}")
    private String login;
    @Value("${password:#{null}}")
    private String password;

    @SneakyThrows
    @EventListener(ApplicationStartedEvent.class)
    public void sendMessage() {
        if (login == null || password == null) {
            log.error("Login and password expected");
            return;
        }

        log.info("Trying to send message '{}'", config.getMessage());

        int count = config.getTryCount();
        do {
            try {
                if (isFile()) {
                    sendFile();
                } else {
                    sendText();
                }
                log.info("Done");
                return;
            } catch (FeignException.Unauthorized e) {
                log.error("UNAUTHORIZED");
                return;
            } catch (FeignException.Forbidden e) {
                log.error("FORBIDDEN");
                return;
            } catch (FeignException e) {
                log.error("Failed: {}", e.getMessage());
                TimeUnit.SECONDS.sleep(config.getTryDelay());
            }
        } while (--count > 0);

        log.info("Failed to send data. Try later");
    }

    private void sendText() {
        client.produceMessage(config.getMessage(), getAuthHeader());
    }

    @SneakyThrows
    private void sendFile() {
        Path path = Paths.get(config.getMessage());
        String name = path.toFile().getName();
        String contentType = "application/octet-stream";
        byte[] content = Files.readAllBytes(path);
        MultipartFile body = new MockMultipartFile(name, name, contentType, content);
        client.produceFile(body, getAuthHeader());
    }

    private boolean isFile() {
        return Files.exists(Paths.get(config.getMessage()));
    }

    private String getAuthHeader() {
        String base64 = Base64.getEncoder().encodeToString((login + ":" + password).getBytes());

        return "Basic " + base64;
    }

}
