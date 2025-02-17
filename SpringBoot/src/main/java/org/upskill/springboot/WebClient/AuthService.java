package org.upskill.springboot.WebClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.upskill.springboot.DTOs.UserDTO;

@Service
public class AuthService {
    private final WebClient webClient;

    public AuthService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:5016/api/v1").build();
    }

    /*public String authenticate(String email, String password) {
        LoginRequest request = new LoginRequest(email, password);

        LoginResponse response = webClient.post()
                .uri("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(LoginResponse.class)
                .block(); // Espera a resposta de forma síncrona

        return response != null ? response.getToken():null;}
        */
}