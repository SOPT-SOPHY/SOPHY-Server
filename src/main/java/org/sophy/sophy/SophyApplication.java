package org.sophy.sophy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SophyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SophyApplication.class, args);
	}

}
