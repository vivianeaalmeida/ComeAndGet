package org.upskill.springboot.Config.AuthConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

/**
 * Security configuration class for handling authentication and authorization in the application.
 * This class configures security settings using Spring Security, including JWT authentication.
 * It also enables method-level security with annotations like {@code @PreAuthorize}.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)  // Enables method-level security with @PreAuthorize annotations
public class SecurityConfig {

    /**
     * Configures the security filter chain for HTTP requests.
     *
     * @param http The HttpSecurity object to configure security settings.
     * @return A configured SecurityFilterChain bean.
     * @throws Exception If an error occurs while configuring security.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disables CSRF protection for stateless APIs
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                // Applies JWT authentication only to protected routes
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.decoder(jwtDecoder())) // Configures JWT validation
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable()) // Disables X-Frame-Options for H2 Console
                );
        return http.build();
    }

    /**
     * Configures the JWT decoder to validate JWT tokens using an HMAC SHA-256 secret key.
     *
     * @return A JwtDecoder bean for decoding JWT tokens.
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        // Uses the same secret key as in the .NET backend
        String secretKey = "656867SecretKeyWithLots46_9.OfCharacters!@ThatIsLongEnoughToKeepItSecure(*@#$)";
        return NimbusJwtDecoder.withSecretKey(new SecretKeySpec(secretKey.getBytes(), "HmacSHA256")).build();
    }

    /**
     * Configures the JWT authentication converter to extract user roles from JWT claims.
     *
     * @return A JwtAuthenticationConverter bean that maps JWT claims to Spring Security authorities.
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();

        // Sets the prefix for roles
        authoritiesConverter.setAuthorityPrefix("ROLE_");

        // Specifies the claim where roles are stored
        authoritiesConverter.setAuthoritiesClaimName("http://schemas.microsoft.com/ws/2008/06/identity/claims/role");

        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

        return converter;
    }
}

