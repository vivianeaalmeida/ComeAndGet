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

/**
 * Service to interact with the external API for municipalities and manage cache functionality.
 * This class handles the retrieval, caching, and storage of municipality data.
 */
@Service
public class MunicipalityWebClient {

    /**
     * WebClient.Builder used for building WebClient instances for making HTTP requests.
     * This is typically injected and used to configure the client for making external API calls.
     */
    private final WebClient webClient;

    /**
     * A list that stores municipalities either fetched from the external API or loaded from the file.
     * This acts as a cache for the municipality data to avoid repeated API calls.
     */
    private List<String> municipalitiesCache;

    /**
     * The path to the municipalities data file where the fetched municipality data is stored.
     * If the cache is expired or empty, the data will be reloaded either from this file or fetched again from the external API.
     */
    private final String MUNICIPALITIES_FILE = "src/main/java/org/upskill/springboot/files/municipalities.json";

    /**
     * Constructor that sets up the WebClient builder and initializes the municipalities cache.
     *
     * @param webClientBuilder The WebClient builder to use for making API calls.
     */
    @Autowired
    public MunicipalityWebClient(WebClient.Builder webClientBuilder) {
            // Create the WebClient instance using the builder
            this.webClient = webClientBuilder
                    .baseUrl("https://json.geoapi.pt")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();
            municipalitiesCache = new ArrayList<>();
    }

    /**
     * Retrieves the list of municipalities. If the cache is empty or expired, it reloads the data from file or fetches
     * new data from the external API.
     *
     * @return A list of municipalities.
     */
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

    /**
     * Checks if the cache has expired. The cache is considered expired if the file last modified time is older than 30 days.
     *
     * @return true if the cache is expired, false otherwise.
     */
    private boolean isCacheExpired() {
        // Check if municipalities file exists and its last modified time is older than 30 days
        File municipalitiesFile = new File(this.MUNICIPALITIES_FILE);
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

    /**
     * Fetches the list of municipalities from the external API.
     *
     * @return A list of municipalities.
     */
    private List<String> fetchMunicipalities() {
        try {
            // Using the WebClient instance to fetch municipalities from the API
            return List.of(Objects.requireNonNull(webClient.get()
                    .uri("/municipios")
                    .retrieve()
                    .bodyToMono(String[].class)
                    .block()));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error accessing GEO API");
        }
    }

    /**
     * Saves the list of municipalities to a file for future use.
     *
     * @param municipalities A list of municipalities to save.
     */
    private void saveMunicipalitiesToFile(List<String> municipalities) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File municipalitiesFile = new File(MUNICIPALITIES_FILE);
            objectMapper.writeValue(municipalitiesFile, municipalities);
        } catch (IOException e) {
            throw new RuntimeException("Error saving municipalities data to file", e);
        }
    }

    /**
     * Loads the list of municipalities from the file.
     *
     * @return A list of municipalities from the file, or null if the file doesn't exist or can't be read.
     */
    private List<String> loadMunicipalitiesFromFile() {
        if (Files.exists(Path.of(MUNICIPALITIES_FILE))) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.readValue(new File(MUNICIPALITIES_FILE), List.class);
            } catch (IOException e) {
                throw new RuntimeException("Error reading municipalities data from file", e);
            }
        }
        return null;
    }

    /**
     * Retrieves a municipality DTO by its designation.
     * If the municipality is found in the cache, it is returned as a DTO.
     * If not, a MunicipalityNotFound exception is thrown.
     *
     * @param designation The name of the municipality to search for.
     * @return The MunicipalityDTO if found.
     * @throws MunicipalityNotFound if the municipality is not found.
     */
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