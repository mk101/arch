package kolesov.maxim.monitor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class MonitorConfig {

    private static final String OUTPUT_NAME = "serverOutput.txt";
    private static final String ERROR_NAME = "serverError.txt";

    @Bean
    public ProcessBuilder processBuilder(@Value("${server.jar-file-path}") String jarPath) {
        File outputFile = new File(OUTPUT_NAME);
        File errorFile = new File(ERROR_NAME);

        ProcessBuilder builder = new ProcessBuilder("java", "-jar", "-Dserver.port=80", jarPath);
        builder.redirectOutput(outputFile);
        builder.redirectError(errorFile);

        return builder;
    }

}
