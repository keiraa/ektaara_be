package com.ektaara.open_gem_gem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class OpenGemGemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenGemGemApplication.class, args);
		System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", "/Users/ravi/Desktop/Ektaara/open-gem-gem/src/main/resources/poised-conduit-471220-b4-a1c06bd33301.json");
	}

}
