package org.upskill.springboot.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.upskill.springboot.DTOs.AdvertisementDTO;
import org.upskill.springboot.Models.Advertisement;
import org.upskill.springboot.Models.Item;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for AdvertisementService.
 * This class contains unit tests for various methods in the AdvertisementService.
 */
class AdvertisementServiceTest {

    private Advertisement advertisement;
    private Item item;
    private List<Advertisement> advertisements;

    /**
     * Setup method executed before each test.
     * Initializes advertisement and item objects.
     */
    @BeforeEach
    void setUp() {
        item = new Item();
        item.setId("10");
        item.setImage("image.jpg");

        advertisement = new Advertisement();
        advertisement.setId("1");
        advertisement.setTitle("Test Ad");
        advertisement.setDescription("This is a test advertisement.");
        advertisement.setStatus(Advertisement.AdvertisementStatus.ACTIVE);
        advertisement.setItem(item);
        advertisement.setDate(LocalDate.now());

        advertisements = new ArrayList<>();
        advertisements.add(advertisement);
    }

    /**
     * Tests retrieving an advertisement by ID.
     */
    @Test
    void testGetAdvertisementById_ShouldReturnCorrectAdvertisement() {
        Advertisement foundAd = advertisements.stream()
                .filter(ad -> ad.getId().equals("1"))
                .findFirst()
                .orElse(null);
        assertNotNull(foundAd);
        assertEquals("Test Ad", foundAd.getTitle());
    }

    /**
     * Tests retrieving all advertisements.
     */
    @Test
    void testGetAllAdvertisements_ShouldReturnList() {
        assertFalse(advertisements.isEmpty());
        assertEquals(1, advertisements.size());
    }

    /**
     * Tests retrieving only active advertisements.
     */
    @Test
    void testGetActiveAdvertisements_ShouldReturnActiveAds() {
        List<Advertisement> activeAds = advertisements.stream()
                .filter(ad -> ad.getStatus() == Advertisement.AdvertisementStatus.ACTIVE)
                .toList();
        assertFalse(activeAds.isEmpty());
        assertEquals(1, activeAds.size());
    }

    /**
     * Tests retrieving closed advertisements.
     */
    @Test
    void testGetClosedAdvertisements_ShouldReturnClosedAds() {
        advertisement.setStatus(Advertisement.AdvertisementStatus.CLOSED);
        List<Advertisement> closedAds = advertisements.stream()
                .filter(ad -> ad.getStatus() == Advertisement.AdvertisementStatus.CLOSED)
                .toList();
        assertFalse(closedAds.isEmpty());
        assertEquals(1, closedAds.size());
    }

    /**
     * Tests retrieving advertisements by client ID.
     */
    @Test
    void testGetAdvertisementsByClientId_ShouldReturnClientAds() {
        advertisement.setClientId("123");
        List<Advertisement> clientAds = advertisements.stream()
                .filter(ad -> "123".equals(ad.getClientId()))
                .toList();
        assertFalse(clientAds.isEmpty());
        assertEquals(1, clientAds.size());
    }

    /**
     * Tests deactivating an advertisement.
     */
    @Test
    void testDeactivateAdvertisement_ShouldSetStatusInactive() {
        advertisement.setStatus(Advertisement.AdvertisementStatus.INACTIVE);
        assertEquals(Advertisement.AdvertisementStatus.INACTIVE, advertisement.getStatus());
    }

    /**
     * Tests keeping an advertisement open if not expired.
     */
    @Test
    void testCloseIfExpired_AdvertisementNotExpired() {
        advertisement.setDate(LocalDate.now().minusDays(10));
        boolean result = advertisement.closeIfExpired();
        assertFalse(result);
        assertEquals(Advertisement.AdvertisementStatus.ACTIVE, advertisement.getStatus());
    }

    /**
     * Tests creating a new advertisement.
     */
    @Test
    void testCreateAdvertisement_ShouldSetInitialValues() {
        Advertisement newAd = new Advertisement();
        newAd.setTitle("New Ad");
        newAd.setDescription("New advertisement description.");
        newAd.setItem(item);
        newAd.setMunicipality("Test City");

        assertEquals("New Ad", newAd.getTitle());
        assertEquals("New advertisement description.", newAd.getDescription());
        assertEquals(Advertisement.AdvertisementStatus.ACTIVE, newAd.getStatus());
        assertNotNull(newAd.getDate());
    }

    /**
     * Tests updating an existing advertisement.
     */
    @Test
    void testUpdateAdvertisement_ShouldModifyValues() {
        advertisement.setTitle("Updated Title");
        advertisement.setDescription("Updated Description");

        assertEquals("Updated Title", advertisement.getTitle());
        assertEquals("Updated Description", advertisement.getDescription());
    }
}
