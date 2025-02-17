package org.upskill.springboot.WebClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.upskill.springboot.DTOs.MunicipalityDTO;
import org.upskill.springboot.Exceptions.MunicipalityNotFound;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MunicipalityWebClient {

    private final WebClient.Builder webClientBuilder;

    // variable to store municipalities in cache
    private List<String> municipalitiesCache;
    private LocalDate lastUpdated;

    @Autowired
    public MunicipalityWebClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder.baseUrl("https://json.geoapi.pt");
    }

    public List<String> getMunicipalities() {
        // Check if cache is expired
        if (municipalitiesCache == null || isCacheExpired()) {
            // Update municipalities cache if cache does not exist or is expired
            municipalitiesCache = fetchMunicipalities();
            lastUpdated = LocalDate.now();
        }
        return municipalitiesCache;
    }

    private boolean isCacheExpired() {
        // Check if last update is before 30 days ago
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        return lastUpdated.isBefore(oneMonthAgo) || lastUpdated.isEqual(oneMonthAgo);
    }

    private List<String> fetchMunicipalities() {
        try {
            WebClient client = webClientBuilder
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();

            return List.of(Objects.requireNonNull(client.get()
                    .uri("/municipios")
                    .retrieve()
                    .bodyToMono(String[].class)
                    .block()));
        } catch (Exception e) {
            throw new RuntimeException("Error accessing GEO API");
        }
    }

    // Method to get municipality DTO by designation
    public MunicipalityDTO getMunicipalityByDesignation(String designation) {
        Optional<String> municipalityDesignation = municipalitiesCache.stream()
                .filter(municipality -> municipality.equalsIgnoreCase(designation))
                .findFirst();

        if (municipalityDesignation.isPresent()) {
            // return new MunicipalityDTO(municipalityDesignation.get());
            MunicipalityDTO municipalityDTO = new MunicipalityDTO();
            municipalityDTO.setDesignation(municipalityDesignation.get());
            return municipalityDTO;
        } else {
            throw new MunicipalityNotFound("Municipality not found");
        }
    }
}