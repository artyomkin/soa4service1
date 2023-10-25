package com.itmo.soa2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Soa2Application {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Soa2Application.class);
		application.setAdditionalProfiles("ssl");
		application.run(args);
	}

}
