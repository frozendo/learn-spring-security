package com.frozendo.learn.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DefaultProvidersApplication {

	public static void main(String[] args) {
		SpringApplication.run(DefaultProvidersApplication.class, args);
	}

	@RequestMapping("/")
	public String index(OAuth2AuthenticationToken authentication) {
		return "Welcome " + authentication.getPrincipal().getAttribute("name");
	}

}
