package oit.is.offline.jinrou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class JinrouApplication {

	public static void main(String[] args) {
		SpringApplication.run(JinrouApplication.class, args);
	}

}
