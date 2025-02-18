package org.upskill.springboot.AuthConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;
import java.util.Collection;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)  // Habilita segurança com anotações @PreAuthorize
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF para APIs stateless
                .authorizeHttpRequests(auth -> auth
                        // Permite acesso sem autenticação para as rotas públicas
//                        .requestMatchers(
//                                "/swagger-ui.html",
//                                "/swagger-ui/**",
//                                "/v3/api-docs/**",
//                                "/webjars/**",
//                                "/swagger-resources/**",
//                                "/h2-console/**",
//                                "/categories",      // Permite acesso sem autenticação
//                                "/categories/**"    // Permite acesso sem autenticação
//                        ).permitAll()  // Permite acesso sem autenticação

                        // Protege rotas de admin (Exemplo: /api/v1/admin/**)
//                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
//
//                        // Protege rotas de user (Exemplo: /api/v1/user/**)
//                        .requestMatchers("/api/v1/user/**").hasAnyRole("USER", "ADMIN")

                        // Qualquer outra rota precisa de autenticação
                        .anyRequest().permitAll()
                )
                // Aplicar a autenticação JWT apenas nas rotas protegidas
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.decoder(jwtDecoder())) // Configura a validação do JWT
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable()) // Desabilita X-Frame-Options para o H2 Console
                );
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        // Use a mesma chave secreta que você usa no .NET
        String secretKey = "656867SecretKeyWithLots46_9.OfCharacters!@ThatIsLongEnoughToKeepItSecure(*@#$)";
        return NimbusJwtDecoder.withSecretKey(new SecretKeySpec(secretKey.getBytes(), "HmacSHA256")).build();
    }


    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();

        // Define o prefixo para as roles
        authoritiesConverter.setAuthorityPrefix("ROLE_");

        // Define o claim onde as roles estão armazenadas
        authoritiesConverter.setAuthoritiesClaimName("http://schemas.microsoft.com/ws/2008/06/identity/claims/role");

        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

        return converter;
    }
}

