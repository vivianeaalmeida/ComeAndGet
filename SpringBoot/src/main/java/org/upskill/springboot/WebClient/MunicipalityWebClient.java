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
import java.nio.file.attribute.FileTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MunicipalityWebClient {

    private final WebClient.Builder webClientBuilder;

    // variable to store municipalities in cache
    private List<String> municipalitiesCache;

    private String municipalitiesFile = "municipalities.json";

    @Autowired
    public MunicipalityWebClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder.baseUrl("https://json.geoapi.pt");
        municipalitiesCache = new ArrayList<>();
    }

    public List<String> getMunicipalities() {
        // If cache empty, reload from file or fetch new data
        if (municipalitiesCache.isEmpty()) {
            municipalitiesCache = loadMunicipalitiesFromFile();

            // if municipalitiesCache is null or cache has expired (more than 30 days)
            if (municipalitiesCache == null || isCacheExpired()) {
                municipalitiesCache = fetchMunicipalities(); // fetch municipalities in external api
                saveMunicipalitiesToFile(municipalitiesCache); // save results into file
            }
        }
        return municipalitiesCache;
    }

    private boolean isCacheExpired() {
        // Check if municipalities file exists and its last modified time is older than 30 days
        File municipalitiesFile = new File(this.municipalitiesFile);
        if (municipalitiesFile.exists()) {
            try {
                FileTime fileTime = Files.getLastModifiedTime(municipalitiesFile.toPath());
                LocalDate lastModifiedDate = fileTime.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                LocalDate currentDate = LocalDate.now();

                // Check if the last modified date is before 30 days ago
                LocalDate thirtyDaysAgo = currentDate.minusDays(30);

                // If the last modified date is before 30 days ago, the cache is expired
                return lastModifiedDate.isBefore(thirtyDaysAgo);
            } catch (IOException e) {
                throw new RuntimeException("Error checking file last modified time", e);
            }
        }
        return true; // If the file doesn't exist, consider the cache expired
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
        getMunicipalities();

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