package org.upskill.springboot.WebClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Service
public class MunicipalityWebClient {

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public MunicipalityWebClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder.baseUrl("https://json.geoapi.pt");
    }

    public List<String> getMunicipalities() {
        WebClient client = webClientBuilder
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return List.of(Objects.requireNonNull(client.get()
                .uri("/municipios")
                .retrieve()
                .bodyToMono(String[].class)
                .block()));
    }

}