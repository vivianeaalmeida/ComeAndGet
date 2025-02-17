package org.upskill.springboot.WebClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class to set up a WebClient bean in the Spring context.
 * WebClient is used to make HTTP requests in a reactive, non-blocking manner.
 */
@Configuration
public class WebClientConfig {

    /**
     * Creates and provides a WebClient.Builder bean.
     * The builder can be used to customize and build instances of WebClient.
     *
     * @return A WebClient.Builder instance that can be further configured and used.
     */
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
