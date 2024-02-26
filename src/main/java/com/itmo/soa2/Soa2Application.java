package com.itmo.soa2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)

public class Soa2Application {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Soa2Application.class);
		application.setAdditionalProfiles("ssl");
		application.run(args);
	}

}
