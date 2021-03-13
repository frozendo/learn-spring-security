package com.frozendo.learn.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class RestConfig {

    @Autowired
    private OAuth2AuthorizedClientService clientService;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate rest = new RestTemplate();
        rest.getInterceptors().add(this::interceptorConfig);
        return rest;
    }

    private ClientHttpResponse interceptorConfig(org.springframework.http.HttpRequest request, byte[] body, org.springframework.http.client.ClientHttpRequestExecution execution) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return execution.execute(request, body);
        }

        OAuth2AuthenticationToken oauthToken =
                (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient client =
                clientService.loadAuthorizedClient(
                        oauthToken.getAuthorizedClientRegistrationId(),
                        oauthToken.getName());

        request.getHeaders().add("Authorization", "Bearer " + client.getAccessToken().getTokenValue());
        return execution.execute(request, body);
    }

}
