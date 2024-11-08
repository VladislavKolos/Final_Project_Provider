package org.example.service;

import org.example.dto.responsedto.*;
import org.example.mapper.SubscriptionMapper;
import org.example.model.*;
import org.example.repository.SubscriptionRepository;
import org.example.service.impl.SubscriptionServiceImpl;
import org.example.util.ProviderConstantUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceTest {

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private SubscriptionMapper subscriptionMapper;

    @Mock
    private UserService userService;

    @Mock
    private PlanService planService;

    private static final Subscription subscription = createSubscription(createUser(),
            createPlan(BigDecimal.valueOf(29.99)));
    private static final SubscriptionResponseDTO subscriptionDto = createSubscriptionResponseDto(createUserResponseDto(),
            createPlanResponseDto(BigDecimal.valueOf(29.99)));

    @Test
    public void testSubscribeToPlanSuccess() {
        User user = createUser();
        Plan plan = createPlan(BigDecimal.valueOf(29.99));

        when(userService.getUserEntityById(user.getId())).thenReturn(user);
        when(planService.getPlanEntityById(plan.getId())).thenReturn(plan);

        Subscription subscription = createSubscription(user, plan);

        when(subscriptionRepository.save(subscription)).thenReturn(subscription);
        when(subscriptionMapper.toSubscriptionResponseDTO(subscription)).thenReturn(subscriptionDto);

        SubscriptionResponseDTO result = subscriptionService.subscribeToPlan(user.getId(), plan.getId());

        ArgumentCaptor<Subscription> subscriptionCaptor = ArgumentCaptor.forClass(Subscription.class);
        verify(subscriptionRepository).save(subscriptionCaptor.capture());

        Subscription savedSubscription = subscriptionCaptor.getValue();

        assertEquals(subscriptionDto, result);
        assertEquals(subscriptionDto.getUser(), result.getUser());
        assertEquals(subscriptionDto.getPlan(), result.getPlan());
        assertEquals(subscriptionDto.getStatus(), result.getStatus());
        assertNotNull(result);
        assertNotNull(savedSubscription);
    }

    @Test
    public void testCancelSubscriptionSuccess() {
        int userId = 5;

        when(subscriptionRepository.findByUserIdAndStatus(userId,
                ProviderConstantUtil.SUBSCRIPTION_STATUS_SIGNED)).thenReturn(
                Optional.of(subscription));

        subscriptionService.cancelSubscription(userId);

        assertEquals(ProviderConstantUtil.SUBSCRIPTION_STATUS_NOT_SIGNED, subscription.getStatus());
        verify(subscriptionRepository).save(subscription);
    }


    private static SubscriptionResponseDTO createSubscriptionResponseDto(UserResponseDTO userDto,
                                                                         PlanResponseDTO planDto) {
        return SubscriptionResponseDTO.builder()
                .user(userDto)
                .plan(planDto)
                .status(ProviderConstantUtil.SUBSCRIPTION_STATUS_SIGNED)
                .build();
    }

    private static PlanResponseDTO createPlanResponseDto(BigDecimal monthlyCost) {
        TariffResponseDTO tariffResponseDTO = new TariffResponseDTO();
        tariffResponseDTO.setName("Premium Tariff");
        tariffResponseDTO.setDescription("High data and voice limits for premium users");
        tariffResponseDTO.setMonthlyCost(monthlyCost);
        tariffResponseDTO.setDataLimit(50000);
        tariffResponseDTO.setVoiceLimit(5000);

        PlanResponseDTO dto = new PlanResponseDTO();
        dto.setId(8);
        dto.setName("Data Boost Plan");
        dto.setDescription("Boost your data limits for a month");
        dto.setStartDate(LocalDate.ofEpochDay(2024 - 10 - 5));
        dto.setEndDate(LocalDate.ofEpochDay(2024 - 11 - 5));
        dto.setTariff(tariffResponseDTO);

        return dto;
    }

    private static UserResponseDTO createUserResponseDto() {
        RoleResponseDTO roleResponseDTO = new RoleResponseDTO();
        roleResponseDTO.setName("ROLE_CLIENT");

        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
        statusResponseDTO.setName("active");

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(2);
        dto.setUsername("AlexJay");
        dto.setEmail("Alex1509@gmail.com");
        dto.setPhone("+375336789133");
        dto.setRole(roleResponseDTO);
        dto.setStatus(statusResponseDTO);

        return dto;
    }

    private static Subscription createSubscription(User user, Plan plan) {
        return Subscription.builder()
                .user(user)
                .plan(plan)
                .status(ProviderConstantUtil.SUBSCRIPTION_STATUS_SIGNED)
                .build();
    }

    private static Plan createPlan(BigDecimal monthlyCost) {
        Tariff tariff = new Tariff();
        tariff.setName("Premium Tariff");
        tariff.setDescription("High data and voice limits for premium users");
        tariff.setMonthlyCost(monthlyCost);
        tariff.setDataLimit(50000);
        tariff.setVoiceLimit(5000);

        Plan plan = new Plan();
        plan.setId(8);
        plan.setName("Data Boost Plan");
        plan.setDescription("Boost your data limits for a month");
        plan.setStartDate(LocalDate.ofEpochDay(2024 - 10 - 5));
        plan.setEndDate(LocalDate.ofEpochDay(2024 - 11 - 5));
        plan.setTariff(tariff);

        return plan;
    }

    private static User createUser() {
        Role role = new Role();
        role.setName("ROLE_CLIENT");

        Status status = new Status();
        status.setName("active");

        User user = new User();
        user.setId(2);
        user.setUsername("AlexJay");
        user.setPassword("$2a$10$bm9c/QYwZFbn9JOsb99dz.WiE1KLjkclK8zeFyyRrOG106mr9uyoS");
        user.setEmail("Alex1509@gmail.com");
        user.setPhone("+375336789133");
        user.setRole(role);
        user.setStatus(status);

        return user;
    }
}
