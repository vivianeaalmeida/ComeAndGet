package org.upskill.springboot.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * Configuration class for setting up CORS (Cross-Origin Resource Sharing) in the application.
 * This allows the application to specify which origins, methods, and headers are allowed
 * for cross-origin requests.
 */
@Configuration
public class CorsConfig {

    /**
     * Bean for configuring CORS filtering.
     * Sets up allowed origins, methods, headers, and credentials for cross-origin requests.
     *
     * @return A CorsFilter bean to apply CORS configuration across the application.
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost:5016"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
