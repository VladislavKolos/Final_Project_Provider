package org.example.service;

import org.example.dto.requestdto.CreateSubscriptionRequestDTO;
import org.example.dto.requestdto.UpdateSubscriptionRequestDTO;
import org.example.dto.responsedto.SubscriptionResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This interface defines methods for creating, retrieving, updating, deleting and canceling subscriptions.
 * It provides various operations to manage subscriptions based on user IDs and plan IDs.
 */
@Component
public interface SubscriptionService {
    List<SubscriptionResponseDTO> getAllSubscriptions();

    SubscriptionResponseDTO getSubscriptionById(Integer id);

    SubscriptionResponseDTO getSubscriptionByClientIdAndStatus(Integer id);

    SubscriptionResponseDTO createSubscription(CreateSubscriptionRequestDTO createSubscriptionRequestDTO);

    SubscriptionResponseDTO updateSubscription(Integer id,
                                               UpdateSubscriptionRequestDTO updateSubscriptionRequestDTO);

    void deleteSubscription(Integer id);

    SubscriptionResponseDTO subscribeToPlan(Integer userId, Integer planId);

    SubscriptionResponseDTO updateSubscriptionForClient(Integer userId, Integer newPlanId);

    void cancelSubscription(Integer userId);
}
