package kolesov.maxim.viewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties
public class ViewerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ViewerApplication.class, args);
	}

}
