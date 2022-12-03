package com.InhaTc.Deview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DeviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviewApplication.class, args);
	}

}
