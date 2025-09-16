package com.ektaara.open_gem_gem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class OpenGemGemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenGemGemApplication.class, args);
	}

}
