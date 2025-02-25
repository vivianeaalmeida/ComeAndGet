package org.upskill.springboot.WebClient;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * This service class is responsible for making HTTP requests to the .Net API using WebClient.
 * It provides methods to interact with user-related data via a REST API.
 * The class uses reactive programming principles with Project Reactor's Mono to handle asynchronous HTTP requests.
 * It communicates with an external service running at `http://localhost:5016/api/v1` and sends Authorization headers
 * to authenticate the user.
 */
@Service
public class AuthUserWebClient {

    private final WebClient webClient;

    /**
     * Constructor that initializes the WebClient with a base URL for the external service.
     *
     * @param webClientBuilder A builder to create the WebClient instance.
     */
    public AuthUserWebClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:5016/api/v1").build();
    }


    /**
     * Retrieves admin data from the external service. The request includes the Authorization header with the JWT token.
     *
     * @param authorization The JWT token to be included in the Authorization header.
     * @return A Mono containing the response body as a string.
     */
    public Mono<String> getAdminData(String authorization) {
        return webClient.get()
                .uri("/admin")
                .header("Authorization", authorization) // Encaminha o token JWT
                .retrieve()
                .bodyToMono(String.class);
    }

    /**
     * Retrieves user data from the external service. The request includes the Authorization header with the JWT token.
     *
     * @param authorization The JWT token to be included in the Authorization header.
     * @return A Mono containing the response body as a string.
     */
    public Mono<String> getUserData(String authorization) {
        return webClient.get()
                .uri("/users")
                .header("Authorization", authorization)
                .retrieve()
                .bodyToMono(String.class);
    }

    /**
     * Retrieves the user ID from the external service. The request includes the Authorization header with the JWT token.
     * This method is blocking and will wait for the response before returning the user ID.
     *
     * @param authorization The JWT token to be included in the Authorization header.
     * @return The user ID as a string.
     */
    public String getUserId(String authorization) { // @RequestHeader
        return webClient.get()
                .uri("/users/token")
                .header("Authorization", authorization)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // Blocks to wait for the synchronous response
    }
}

