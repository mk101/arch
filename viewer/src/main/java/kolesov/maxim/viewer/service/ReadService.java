package kolesov.maxim.viewer.service;

import kolesov.maxim.viewer.client.ServerClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.Console;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
@Profile("read")
public class ReadService {

    private final ServerClient serverClient;

    @EventListener(ApplicationStartedEvent.class)
    public void started() {
        Console console = System.console();
        String login = console.readLine("Login: ");
        String password = new String(console.readPassword("Password: "));
        String token = getToken(login, password);

        List<String> roles = serverClient.getRoles(token);
        if (roles.isEmpty()) {
            return;
        }

        for (String r : roles) {
            System.out.println(r);
        }

        while (true) {
            String message = console.readLine("> ");
            if (isFile(message)) {
                if (!roles.contains("ROLE_IMAGE")) {
                    System.out.println("No rights");
                    continue;
                }
                sendFile(message, token);
                continue;
            }

            if (!roles.contains("ROLE_TEXT")) {
                System.out.println("No rights");
                continue;
            }
            sendText(message, token);
        }
    }

    private void sendText(String text, String token) {
        serverClient.produceMessage(text, token);
    }

    @SneakyThrows
    private void sendFile(String filePath, String token) {
        Path path = Paths.get(filePath);
        String name = path.toFile().getName();
        String contentType = "application/octet-stream";
        byte[] content = Files.readAllBytes(path);
        MultipartFile body = new MockMultipartFile(name, name, contentType, content);
        serverClient.produceFile(body, token);
    }

    private boolean isFile(String message) {
        return Files.exists(Paths.get(message));
    }

    private String getToken(String login, String password) {
        String base64 = Base64.getEncoder().encodeToString((login + ":" + password).getBytes());

        return "Basic " + base64;
    }

}
