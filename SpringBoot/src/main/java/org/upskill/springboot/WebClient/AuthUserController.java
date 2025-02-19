package org.upskill.springboot.WebClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class AuthUserController {

    private final WebClient webClient;

    public AuthUserController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:5016/api/v1").build(); // URL do backend .NET
    }

    @GetMapping("/admin")
    public Mono<String> getAdminData(@RequestHeader("Authorization") String authorization) {
        return webClient.get()
                .uri("/admin")
                .header("Authorization", authorization) // Encaminha o token recebido
                .retrieve()
                .bodyToMono(String.class);
    }


    @GetMapping("/user")
    public Mono<String> getUserData(@RequestHeader("Authorization") String authorization) {
        return webClient.get()
                .uri("/user")
                .header("Authorization", authorization)
                .retrieve()
                .bodyToMono(String.class);
    }
}
