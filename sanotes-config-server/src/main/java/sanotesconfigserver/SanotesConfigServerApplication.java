package sanotesconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class SanotesConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SanotesConfigServerApplication.class, args);
	}

}
