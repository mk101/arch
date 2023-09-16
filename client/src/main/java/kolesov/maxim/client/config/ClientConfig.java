package kolesov.maxim.client.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ClientConfig {

    @Value("${server.url}")
    private String host;
    @Value("${message}")
    private String message;

    @Value("${connection.try-count}")
    private Integer tryCount;

    @Value("${connection.try-delay}")
    private Integer tryDelay;

}
