package org.upskill.springboot.WebClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.upskill.springboot.DTOs.UserDTO;

@Service
public class UserWebClient {
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public UserWebClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder.baseUrl("http://localhost:5016/api/");
    }

    // Método para consumir a API externa e obter as informações
    public UserDTO getUserDetails(String token) {
            return webClientBuilder.build().get()
                    .uri("/user", token)
                    .retrieve()
                    .bodyToMono(UserDTO.class)
                    .block();
    }

}
