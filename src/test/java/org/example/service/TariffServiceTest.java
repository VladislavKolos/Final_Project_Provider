package org.example.service;

import org.example.dto.requestdto.CreateTariffRequestDTO;
import org.example.dto.responsedto.TariffResponseDTO;
import org.example.mapper.TariffMapper;
import org.example.model.Tariff;
import org.example.repository.TariffRepository;
import org.example.service.impl.TariffServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TariffServiceTest {

    @InjectMocks
    private TariffServiceImpl tariffService;

    @Mock
    private TariffRepository tariffRepository;

    @Mock
    private TariffMapper tariffMapper;

    private static final CreateTariffRequestDTO requestDto = createTariffRequestDto(BigDecimal.valueOf(7.99));
    private static final Tariff tariff = createTariff(BigDecimal.valueOf(7.99));
    private static final TariffResponseDTO responseDto = createTariffResponseDto(BigDecimal.valueOf(7.99));

    @Test
    public void testGetTariffEntityByIdSuccess() {
        int tariffId = 1;

        Tariff expectedTariff = new Tariff();
        expectedTariff.setId(tariffId);

        when(tariffRepository.findById(tariffId)).thenReturn(Optional.of(expectedTariff));

        Tariff actualTariff = tariffService.getTariffEntityById(tariffId);

        assertEquals(expectedTariff, actualTariff);
    }

    @Test
    public void testGetTariffEntityByIdNotFound() {
        int tariffId = new Random().nextInt(Integer.MAX_VALUE);

        when(tariffRepository.findById(tariffId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> tariffService.getTariffEntityById(tariffId));
    }

    @Test
    public void testCreateTariffSuccess() {
        when(tariffMapper.toTariffForCreate(requestDto)).thenReturn(tariff);
        when(tariffRepository.save(tariff)).thenReturn(tariff);
        when(tariffMapper.toTariffResponseDTO(tariff)).thenReturn(responseDto);

        TariffResponseDTO result = tariffService.createTariff(requestDto);

        assertEquals(responseDto, result);
        assertNotNull(result);
    }

    @Test
    public void testCreateTariffError() {
        when(tariffMapper.toTariffForCreate(requestDto)).thenReturn(tariff);
        when(tariffRepository.save(tariff)).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> tariffService.createTariff(requestDto));
    }

    private static CreateTariffRequestDTO createTariffRequestDto(BigDecimal monthlyCost) {

        return CreateTariffRequestDTO.builder()
                .name("Night Owl")
                .description("Extra data during nighttime hours at reduced rates")
                .monthlyCost(monthlyCost)
                .dataLimit(7000)
                .voiceLimit(700)
                .build();
    }

    private static Tariff createTariff(BigDecimal monthlyCost) {

        return Tariff.builder()
                .name("Night Owl")
                .description("Extra data during nighttime hours at reduced rates")
                .monthlyCost(monthlyCost)
                .dataLimit(7000)
                .voiceLimit(700)
                .build();
    }

    private static TariffResponseDTO createTariffResponseDto(BigDecimal monthlyCost) {

        return TariffResponseDTO.builder()
                .name("Night Owl")
                .description("Extra data during nighttime hours at reduced rates")
                .monthlyCost(monthlyCost)
                .dataLimit(7000)
                .voiceLimit(700)
                .build();
    }
}
