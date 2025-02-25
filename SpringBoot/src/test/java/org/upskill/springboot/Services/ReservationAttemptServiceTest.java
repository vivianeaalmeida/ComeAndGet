package org.upskill.springboot.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.upskill.springboot.DTOs.ReservationAttemptDTO;
import org.upskill.springboot.DTOs.ReservationAttemptResponseDTO;
import org.upskill.springboot.DTOs.ReservationAttemptStatusDTO;
import org.upskill.springboot.Exceptions.AdvertisementNotFoundException;
import org.upskill.springboot.Exceptions.ReservationAttemptNotFoundException;
import org.upskill.springboot.Models.Advertisement;
import org.upskill.springboot.Models.Category;
import org.upskill.springboot.Models.Item;
import org.upskill.springboot.Models.ReservationAttempt;
import org.upskill.springboot.Repositories.ReservationAttemptRepository;
import org.upskill.springboot.WebClient.AuthUserWebClient;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationAttemptServiceTest {

    @Mock
    private ReservationAttemptRepository reservationAttemptRepository;

    @Mock
    private AuthUserWebClient userWebClient;

    @Mock
    private AdvertisementService advertisementService;

    @InjectMocks
    private ReservationAttemptService reservationAttemptService;

    private ReservationAttempt reservationAttempt;
    private Advertisement advertisement;
    private Item item;
    private Category category;

    @BeforeEach
    void setUp() {
        advertisement = new Advertisement();
        advertisement.setId("ad1");
        advertisement.setClientId("client123");

        reservationAttempt = new ReservationAttempt();
        reservationAttempt.setId("res1");
        reservationAttempt.setStatus(ReservationAttempt.ReservationAttemptStatus.PENDING);
        reservationAttempt.setAdvertisement(advertisement);
        reservationAttempt.setClientId("client456");
    }

    @Test
    void testGetReservationAttemptById_ShouldReturnDTO() {
        when(reservationAttemptRepository.findById("res1")).thenReturn(Optional.of(reservationAttempt));

        ReservationAttemptResponseDTO result = reservationAttemptService.getReservationAttemptById("res1");

        assertNotNull(result);
        verify(reservationAttemptRepository, times(1)).findById("res1");
    }

    @Test
    void testGetReservationAttemptById_ShouldThrowException_WhenNotFound() {
        when(reservationAttemptRepository.findById("invalid_id")).thenReturn(Optional.empty());

        assertThrows(ReservationAttemptNotFoundException.class, () -> reservationAttemptService.getReservationAttemptById("invalid_id"));
    }

    @Test
    void testGetReservationAttemptsByAdvertisement_ShouldReturnList() {
        when(reservationAttemptRepository.findByAdvertisement_Id("ad1"))
                .thenReturn(List.of(reservationAttempt));

        List<ReservationAttemptResponseDTO> results = reservationAttemptService.getReservationAttemptsByAdvertisement("ad1");

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    void testUpdateReservationAttemptStatus_ShouldModifyValues() {
        ReservationAttemptStatusDTO statusDTO = new ReservationAttemptStatusDTO();
        statusDTO.setStatus("ACCEPTED");

        when(reservationAttemptRepository.findById("res1")).thenReturn(Optional.of(reservationAttempt));
        when(userWebClient.getUserId(anyString())).thenReturn("client123");
        when(reservationAttemptRepository.save(any(ReservationAttempt.class))).thenReturn(reservationAttempt);

        ReservationAttemptResponseDTO updatedAttempt = reservationAttemptService.updateReservationAttemptStatus("res1", "authToken", statusDTO);

        assertNotNull(updatedAttempt);
        assertEquals("ACCEPTED", updatedAttempt.getStatus());
        verify(userWebClient, times(1)).getUserId(anyString());
        verify(reservationAttemptRepository, times(1)).save(any(ReservationAttempt.class));
    }

    @Test
    void shouldThrowException_WhenAdvertisementNotFound() {
        //dados de entrada
        ReservationAttemptDTO reservationAttemptDTO = new ReservationAttemptDTO();
        reservationAttemptDTO.setAdvertisementId("invalid_ad");
        reservationAttemptDTO.setStatus("PENDING");
        String authorization = "Bearer token123";

        //mock - não localiza o anuncio
        when(advertisementService.getAdvertisementById("invalid_ad")).thenThrow(new AdvertisementNotFoundException("Advertisement not found"));

        //asserts
        assertThrows(AdvertisementNotFoundException.class, () -> reservationAttemptService.createReservationAttempt(reservationAttemptDTO, authorization));

        //verifica chamadas dos mocks
        verify(reservationAttemptRepository, times(0)).save(any(ReservationAttempt.class));  // O save não deve ser chamado
    }
}
