package org.upskill.springboot.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.upskill.springboot.DTOs.MunicipalityDTO;
import org.upskill.springboot.Exceptions.MunicipalityNotFound;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    private String municipalitiesFile = "municipalities.json";

    @Autowired
    public MunicipalityWebClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder.baseUrl("https://json.geoapi.pt");
    }

    public List<String> getMunicipalities() {
        if (municipalitiesCache == null || isCacheExpired()) {
            municipalitiesCache = loadMunicipalitiesFromFile();

            // If no file found or cache is expired, fetch new data
            if (municipalitiesCache == null || municipalitiesCache.isEmpty()) {
                municipalitiesCache = fetchMunicipalities();
                saveMunicipalitiesToFile(municipalitiesCache);
                lastUpdated = LocalDate.now();
            }
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

    private void saveMunicipalitiesToFile(List<String> municipalities) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File municipalitiesFile = new File("municipalities.json");
            objectMapper.writeValue(municipalitiesFile, municipalities);
        } catch (IOException e) {
            throw new RuntimeException("Error saving municipalities data to file", e);
        }
    }

    private List<String> loadMunicipalitiesFromFile() {
        if (Files.exists(Path.of(municipalitiesFile))) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.readValue(new File(municipalitiesFile), List.class);
            } catch (IOException e) {
                throw new RuntimeException("Error reading municipalities data from file", e);
            }
        }
        return null;
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