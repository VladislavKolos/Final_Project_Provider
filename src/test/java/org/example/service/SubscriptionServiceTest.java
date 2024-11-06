package org.example.service;

import org.example.dto.responsedto.PlanResponseDTO;
import org.example.dto.responsedto.SubscriptionResponseDTO;
import org.example.dto.responsedto.UserResponseDTO;
import org.example.mapper.SubscriptionMapper;
import org.example.model.Plan;
import org.example.model.Subscription;
import org.example.model.User;
import org.example.repository.SubscriptionRepository;
import org.example.service.impl.SubscriptionServiceImpl;
import org.example.util.ProviderConstantUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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

    private static final Subscription subscription = createSubscription(createUser(), createPlan());
    private static final SubscriptionResponseDTO subscriptionDto = createSubscriptionResponseDto(createUserResponseDto(),
            createPlanResponseDto());

    @Test
    public void testSubscribeToPlanSuccess() {
        int userId = 5;
        int planId = 9;

        when(userService.getUserEntityById(userId)).thenReturn(createUser());
        when(planService.getPlanEntityById(planId)).thenReturn(createPlan());
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription);
        when(subscriptionMapper.toSubscriptionResponseDTO(subscription)).thenReturn(subscriptionDto);

        SubscriptionResponseDTO result = subscriptionService.subscribeToPlan(userId, planId);

        assertEquals(subscriptionDto, result);
        assertNotNull(result);
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

    private static PlanResponseDTO createPlanResponseDto() {
        PlanResponseDTO dto = new PlanResponseDTO();
        dto.setId(9);

        return dto;
    }

    private static UserResponseDTO createUserResponseDto() {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(5);

        return dto;
    }

    private static Subscription createSubscription(User user, Plan plan) {
        return Subscription.builder()
                .user(user)
                .plan(plan)
                .status(ProviderConstantUtil.SUBSCRIPTION_STATUS_SIGNED)
                .build();
    }

    private static Plan createPlan() {
        Plan plan = new Plan();
        plan.setId(9);

        return plan;
    }

    private static User createUser() {
        User user = new User();
        user.setId(5);

        return user;
    }
}
