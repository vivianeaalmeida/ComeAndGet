package org.upskill.springboot.WebClient;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthUserWebClient {

    private final WebClient webClient;

    public AuthUserWebClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:5016/api/v1").build();
    }

    public Mono<String> getAdminData(String authorization) { // Removido @RequestHeader
        return webClient.get()
                .uri("/admin")
                .header("Authorization", authorization) // Encaminha o token JWT
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getUserData(String authorization) { // Removido @RequestHeader
        return webClient.get()
                .uri("/user")
                .header("Authorization", authorization)
                .retrieve()
                .bodyToMono(String.class);
    }

    public String getUserId(String authorization) { // @RequestHeader
        return webClient.get()
                .uri("/user/id")
                .header("Authorization", authorization)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // Bloqueia para aguardar a resposta síncrona
    }
}

