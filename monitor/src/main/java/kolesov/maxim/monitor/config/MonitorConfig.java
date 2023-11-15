package kolesov.maxim.monitor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class MonitorConfig {

    private static final String OUTPUT_NAME = "serverOutput-%s.txt";
    private static final String ERROR_NAME = "serverError-%s.txt";

    @Bean
    public ProcessBuilder processBuilder(@Value("${server.jar-file-path}") String jarPath,
                                         @Value("${server.url}") String url,
                                         @Value("${heartbeat.file}") String filePath,
                                         @Value("${heartbeat.period}") String period) {
        String port = url.split(":")[1];

        File outputFile = new File(String.format(OUTPUT_NAME, filePath.replace("./", "").split("\\.")[0]));
        File errorFile = new File(String.format(ERROR_NAME, filePath.replace("./", "").split("\\.")[0]));

        ProcessBuilder builder = new ProcessBuilder("java", "-jar", "-Dserver.port=" + port,
                "-Dheartbeat.file=" + filePath,
                "-Dheartbeat.period=" + period
                ,jarPath);
        builder.redirectOutput(outputFile);
        builder.redirectError(errorFile);

        return builder;
    }

}
