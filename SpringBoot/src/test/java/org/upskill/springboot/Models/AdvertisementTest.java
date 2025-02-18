package org.upskill.springboot.Models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

/**
 * Test class for the Advertisement model.
 *  This class contains unit tests for the method closeIfExpired() of the Advertisement class.
 */
class AdvertisementTest {

    /**
     * Tests for when advertisement is expired (more than 30 days old)
     */
    @Test
    void testCloseIfExpired_AdvertisementExpired() {
        Advertisement advertisement = new Advertisement();
        advertisement.setStatus(Advertisement.AdvertisementStatus.ACTIVE);
        advertisement.setDate(LocalDate.now().minusDays(31));

        boolean result = advertisement.closeIfExpired();
        assertTrue(result);
        assertEquals(Advertisement.AdvertisementStatus.CLOSED, advertisement.getStatus());
    }

    /**
     *  Tests for when advertisement is not expired (less than 30 days old)
     */
    @Test
    void testCloseIfExpired_AdvertisementNotExpired() {
        Advertisement advertisement = new Advertisement();
        advertisement.setStatus(Advertisement.AdvertisementStatus.ACTIVE);
        advertisement.setDate(LocalDate.now().minusDays(29));

        boolean result = advertisement.closeIfExpired();

        assertFalse(result);
        assertEquals(Advertisement.AdvertisementStatus.ACTIVE, advertisement.getStatus());
    }
}
