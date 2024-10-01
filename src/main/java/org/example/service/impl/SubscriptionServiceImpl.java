package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.requestdto.CreateSubscriptionRequestDTO;
import org.example.dto.requestdto.UpdateSubscriptionRequestDTO;
import org.example.dto.responsedto.SubscriptionResponseDTO;
import org.example.exception.ProviderNotFoundException;
import org.example.mapper.SubscriptionMapper;
import org.example.model.Plan;
import org.example.model.Subscription;
import org.example.model.User;
import org.example.repository.SubscriptionRepository;
import org.example.service.PlanService;
import org.example.service.SubscriptionService;
import org.example.service.UserService;
import org.example.util.ProviderConstantUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for working with subscriptions.
 * This service provides methods to retrieve, create, update, and delete subscriptions.
 * It interacts with the subscription repository (`SubscriptionRepository`), the mapper (`SubscriptionMapper`) for transforming objects, the `UserService` service for working with users and
 * the `PlanService` service for working with plans.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final SubscriptionMapper subscriptionMapper;

    private final UserService userService;

    private final PlanService planService;

    private final MessageSource messageSource;

    /**
     * This method fetches all subscriptions from the `subscriptionRepository` and
     * transforms them into a list of `SubscriptionResponseDTO` objects using the
     * `subscriptionMapper`.
     *
     * @return A list of `SubscriptionResponseDTO` objects representing all subscriptions.
     */
    @Transactional(readOnly = true)
    public List<SubscriptionResponseDTO> getAllSubscriptions() {
        return subscriptionRepository.findAll()
                .stream()
                .map(subscriptionMapper::toSubscriptionResponseDTO)
                .toList();
    }

    /**
     * This method searches for a subscription in the `subscriptionRepository` using the provided ID.
     * If found, the subscription is mapped to a `SubscriptionResponseDTO` and returned.
     * Otherwise, a `ProviderNotFoundException` is thrown.
     *
     * @param id Subscription ID
     * @return The `SubscriptionResponseDTO` representing the found subscription.
     */
    @Transactional(readOnly = true)
    public SubscriptionResponseDTO getSubscriptionById(Integer id) {
        return subscriptionRepository.findById(id)
                .map(subscriptionMapper::toSubscriptionResponseDTO)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage(
                        "subscription.error.not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));
    }

    /**
     * This method searches for a subscription in the `subscriptionRepository` based on
     * the provided client ID and status. If found, the subscription is mapped to a
     * `SubscriptionResponseDTO` and returned. Otherwise, a `ProviderNotFoundException` is thrown.
     *
     * @param id The ID of the Client.
     * @return The `SubscriptionResponseDTO` representing the found subscription.
     */
    @Transactional(readOnly = true)
    public SubscriptionResponseDTO getSubscriptionByClientIdAndStatus(Integer id) {
        return subscriptionRepository.findByUserIdAndStatus(id, ProviderConstantUtil.SUBSCRIPTION_STATUS_SIGNED)
                .map(subscriptionMapper::toSubscriptionResponseDTO)
                .orElseThrow();
    }

    /**
     * This method constructs a new subscription based on the provided `CreateSubscriptionRequestDTO`,
     * persists it to the database, and returns a `SubscriptionResponseDTO` representing the created subscription.
     *
     * @param createSubscriptionRequestDTO createSubscriptionRequestDTO A DTO containing the details for the new subscription.
     * @return The created subscription as a response DTO.
     */
    @Transactional
    public SubscriptionResponseDTO createSubscription(CreateSubscriptionRequestDTO createSubscriptionRequestDTO) {
        Subscription subscription = buildSubscription(createSubscriptionRequestDTO.getStatus(),
                userService.getUserEntityById(createSubscriptionRequestDTO.getUserId()),
                planService.getPlanEntityById(createSubscriptionRequestDTO.getPlanId()));

        return Optional.of(subscription)
                .map(subscriptionRepository::save)
                .map(subscriptionMapper::toSubscriptionResponseDTO)
                .orElseThrow();
    }

    /**
     * This method retrieves an existing subscription by its ID, updates its properties based on the
     * provided `UpdateSubscriptionRequestDTO`, and persists the updated entity.
     *
     * @param id                           Subscription ID
     * @param updateSubscriptionRequestDTO A DTO containing the updated subscription details.
     * @return The updated subscription as a response DTO.
     */
    @Transactional
    public SubscriptionResponseDTO updateSubscription(Integer id,
                                                      UpdateSubscriptionRequestDTO updateSubscriptionRequestDTO) {

        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage(
                        "subscription.error.not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));

        setSubscription(subscription, updateSubscriptionRequestDTO);

        return Optional.of(subscription)
                .map(subscriptionRepository::save)
                .map(subscriptionMapper::toSubscriptionResponseDTO)
                .orElseThrow();
    }

    /**
     * his method deletes the subscription with the specified ID from the database.
     *
     * @param id Subscription ID
     */
    @Transactional
    public void deleteSubscription(Integer id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage(
                        "subscription.error.not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));

        subscriptionRepository.delete(subscription);
    }

    /**
     * This method creates a new subscription for a user with the specified `userId` and
     * `planId`. It builds a new `Subscription` entity with the provided status
     * (typically `ProviderConstantUtil.SUBSCRIPTION_STATUS_SIGNED`), retrieves the corresponding
     * `User` and `Plan` entities using services, and saves the new subscription to the database.
     * The saved subscription is then converted to a `SubscriptionResponseDTO` and returned.
     *
     * @param userId The ID of the User to subscribe.
     * @param planId The ID of the plan to subscribe to.
     * @return The created subscription as a response DTO.
     */
    @Transactional
    public SubscriptionResponseDTO subscribeToPlan(Integer userId, Integer planId) {
        Subscription subscription = buildSubscription(ProviderConstantUtil.SUBSCRIPTION_STATUS_SIGNED,
                userService.getUserEntityById(userId),
                planService.getPlanEntityById(planId));

        return Optional.of(subscription)
                .map(subscriptionRepository::save)
                .map(subscriptionMapper::toSubscriptionResponseDTO)
                .orElseThrow();
    }

    /**
     * This method updates an existing subscription for a user with the specified `userId`.
     * It first finds the active subscription (status: `ProviderConstantUtil.SUBSCRIPTION_STATUS_SIGNED`)
     * for the user and throws an exception if not found. Then, it marks the current subscription
     * as inactive (status: `ProviderConstantUtil.SUBSCRIPTION_STATUS_NOT_SIGNED`) and saves it.
     * Finally, it creates a new subscription with the provided `newPlanId` and the active status,
     * saves it, and returns the saved subscription as a response DTO.
     *
     * @param userId    The ID of the user whose subscription needs to be updated.
     * @param newPlanId The ID of the new plan to subscribe to.
     * @return The updated subscription as a response DTO.
     */
    @Transactional
    public SubscriptionResponseDTO updateSubscriptionForClient(Integer userId, Integer newPlanId) {
        Subscription currentSubscription = subscriptionRepository.findByUserIdAndStatus(userId,
                        ProviderConstantUtil.SUBSCRIPTION_STATUS_SIGNED)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage(
                        "subscription.error.current_not_found.for_client",
                        new Object[]{userId},
                        LocaleContextHolder.getLocale())));

        currentSubscription.setStatus(ProviderConstantUtil.SUBSCRIPTION_STATUS_NOT_SIGNED);
        subscriptionRepository.save(currentSubscription);

        Subscription subscription = buildSubscription(ProviderConstantUtil.SUBSCRIPTION_STATUS_SIGNED,
                userService.getUserEntityById(userId),
                planService.getPlanEntityById(newPlanId));

        return Optional.of(subscription)
                .map(subscriptionRepository::save)
                .map(subscriptionMapper::toSubscriptionResponseDTO)
                .orElseThrow();
    }

    /**
     * This method cancels the active subscription (status: `ProviderConstantUtil.SUBSCRIPTION_STATUS_SIGNED`)
     * for a user with the specified `userId`. It finds the subscription and throws an exception if not found.
     * Then, it marks the subscription as inactive (status: `ProviderConstantUtil.SUBSCRIPTION_STATUS_NOT_SIGNED`)
     * and saves it to the database.
     *
     * @param userId userId The ID of the user whose subscription needs to be cancelled.
     */
    @Transactional
    public void cancelSubscription(Integer userId) {
        Subscription subscription = subscriptionRepository.findByUserIdAndStatus(userId,
                        ProviderConstantUtil.SUBSCRIPTION_STATUS_SIGNED)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage(
                        "subscription.error.current_not_found.for_client",
                        new Object[]{userId},
                        LocaleContextHolder.getLocale())));

        subscription.setStatus(ProviderConstantUtil.SUBSCRIPTION_STATUS_NOT_SIGNED);
        subscriptionRepository.save(subscription);
    }

    /**
     * This private helper method creates a new `Subscription` object using the builder pattern.
     * It sets the status, user, and plan of the subscription based on the provided arguments.
     *
     * @param status The subscription status (e.g., "SIGNED", "NOT_SIGNED").
     * @param user   The user entity associated with the subscription.
     * @param plan   The plan entity associated with the subscription.
     * @return The newly created Subscription object.
     */
    private Subscription buildSubscription(String status, User user, Plan plan) {
        return Subscription.builder()
                .status(status)
                .user(user)
                .plan(plan)
                .build();
    }

    /**
     * This private helper method updates the status, user, and plan of an existing `Subscription` object
     * based on the provided `UpdateSubscriptionRequestDTO`. It retrieves the user and plan entities using services.
     *
     * @param subscription                 The subscription entity to be updated.
     * @param updateSubscriptionRequestDTO The DTO containing the update information.
     */
    private void setSubscription(Subscription subscription, UpdateSubscriptionRequestDTO updateSubscriptionRequestDTO) {
        subscription.setStatus(updateSubscriptionRequestDTO.getStatus());
        subscription.setUser(userService.getUserEntityById(updateSubscriptionRequestDTO.getUserId()));
        subscription.setPlan(planService.getPlanEntityById(updateSubscriptionRequestDTO.getPlanId()));
    }


}
