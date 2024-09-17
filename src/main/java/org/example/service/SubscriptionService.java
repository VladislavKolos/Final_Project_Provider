package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.requestdto.CreateSubscriptionRequestDTO;
import org.example.dto.requestdto.UpdateSubscriptionRequestDTO;
import org.example.dto.responsedto.SubscriptionResponseDTO;
import org.example.exception.ProviderNotFoundException;
import org.example.mapper.SubscriptionMapper;
import org.example.model.Subscription;
import org.example.repository.SubscriptionRepository;
import org.example.util.ProviderConstantUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final SubscriptionMapper subscriptionMapper;

    private final UserService userService;

    private final PlanService planService;

    @Transactional(readOnly = true)
    public List<SubscriptionResponseDTO> getAllSubscriptions() {
        return subscriptionRepository.findAll()
                .stream()
                .map(subscriptionMapper::toSubscriptionResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public SubscriptionResponseDTO getSubscriptionById(Integer id) {
        return subscriptionRepository.findById(id)
                .map(subscriptionMapper::toSubscriptionResponseDTO)
                .orElseThrow(() -> new ProviderNotFoundException("Subscription: " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public SubscriptionResponseDTO getSubscriptionByClientIdAndStatus(Integer id) {
        return subscriptionRepository.findByUserIdAndStatus(id, ProviderConstantUtil.SUBSCRIPTION_STATUS_SIGNED)
                .map(subscriptionMapper::toSubscriptionResponseDTO)
                .orElseThrow();
    }

    @Transactional
    public SubscriptionResponseDTO createSubscription(CreateSubscriptionRequestDTO createSubscriptionRequestDTO) {
        Subscription subscription = Subscription.builder()
                .status(createSubscriptionRequestDTO.getStatus())
                .user(userService.getUserEntityById(createSubscriptionRequestDTO.getUserId()))
                .plan(planService.getPlanEntityById(createSubscriptionRequestDTO.getPlanId()))
                .build();

        return Optional.of(subscription)
                .map(subscriptionRepository::save)
                .map(subscriptionMapper::toSubscriptionResponseDTO)
                .orElseThrow();
    }

    @Transactional
    public SubscriptionResponseDTO updateSubscription(Integer id,
                                                      UpdateSubscriptionRequestDTO updateSubscriptionRequestDTO) {

        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException("Subscription: " + id + " not found"));

        subscription.setStatus(updateSubscriptionRequestDTO.getStatus());
        subscription.setUser(userService.getUserEntityById(updateSubscriptionRequestDTO.getUserId()));
        subscription.setPlan(planService.getPlanEntityById(updateSubscriptionRequestDTO.getPlanId()));

        return Optional.of(subscription)
                .map(subscriptionRepository::save)
                .map(subscriptionMapper::toSubscriptionResponseDTO)
                .orElseThrow();
    }

    @Transactional
    public void deleteSubscription(Integer id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException("Subscription: " + id + " not found"));

        subscriptionRepository.delete(subscription);
    }

    @Transactional
    public SubscriptionResponseDTO subscribeToPlan(Integer userId, Integer planId) {
        Subscription subscription = Subscription.builder()
                .status(ProviderConstantUtil.SUBSCRIPTION_STATUS_SIGNED)
                .user(userService.getUserEntityById(userId))
                .plan(planService.getPlanEntityById(planId))
                .build();

        return Optional.of(subscription)
                .map(subscriptionRepository::save)
                .map(subscriptionMapper::toSubscriptionResponseDTO)
                .orElseThrow();
    }

    @Transactional
    public SubscriptionResponseDTO updateSubscriptionForClient(Integer userId, Integer newPlanId) {
        Subscription currentSubscription = subscriptionRepository.findByUserIdAndStatus(userId,
                        ProviderConstantUtil.SUBSCRIPTION_STATUS_SIGNED)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ProviderNotFoundException("Current subscription for Client: " + userId + " not found"));

        currentSubscription.setStatus(ProviderConstantUtil.SUBSCRIPTION_STATUS_NOT_SIGNED);
        subscriptionRepository.save(currentSubscription);

        Subscription subscription = Subscription.builder()
                .status(ProviderConstantUtil.SUBSCRIPTION_STATUS_SIGNED)
                .user(userService.getUserEntityById(userId))
                .plan(planService.getPlanEntityById(newPlanId))
                .build();

        return Optional.of(subscription)
                .map(subscriptionRepository::save)
                .map(subscriptionMapper::toSubscriptionResponseDTO)
                .orElseThrow();
    }

    @Transactional
    public void cancelSubscription(Integer userId) {
        Subscription subscription = subscriptionRepository.findByUserIdAndStatus(userId,
                        ProviderConstantUtil.SUBSCRIPTION_STATUS_SIGNED)
                .orElseThrow(() -> new ProviderNotFoundException("Current subscription for Client: " + userId + " not found"));

        subscription.setStatus(ProviderConstantUtil.SUBSCRIPTION_STATUS_NOT_SIGNED);
        subscriptionRepository.save(subscription);
    }

}
