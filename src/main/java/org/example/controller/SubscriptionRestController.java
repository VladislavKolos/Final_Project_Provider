package org.example.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.ExecutionTime;
import org.example.annotation.customannotation.ExistPlanId;
import org.example.annotation.customannotation.UniqueSubscription;
import org.example.dto.requestdto.CreateSubscriptionRequestDTO;
import org.example.dto.requestdto.UpdateSubscriptionRequestDTO;
import org.example.dto.responsedto.SubscriptionResponseDTO;
import org.example.service.SubscriptionService;
import org.example.util.RecipientCurrentClientUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing subscriptions.
 * This class provides an API for managing subscriptions, including getting, creating, updating, canceling,
 * and getting information about the current subscription for a client.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SubscriptionRestController {
    private final SubscriptionService subscriptionService;

    @ExecutionTime
    @GetMapping("/admin/subscriptions")
    public ResponseEntity<List<SubscriptionResponseDTO>> getAllSubscriptions() {
        List<SubscriptionResponseDTO> subscriptions = subscriptionService.getAllSubscriptions();

        log.info("Subscriptions successfully received");

        return ResponseEntity.ok(subscriptions);
    }

    @ExecutionTime
    @GetMapping("/admin/subscriptions/{id}")
    @Validated
    public ResponseEntity<SubscriptionResponseDTO> getSubscriptionByIdForAdmin(@NotNull @PathVariable Integer id) {
        SubscriptionResponseDTO subscriptionResponseDTO = subscriptionService.getSubscriptionById(id);

        log.info("Subscription: " + id + " for Admin successfully received");

        return ResponseEntity.ok(subscriptionResponseDTO);
    }

    @PostMapping("/admin/subscriptions")
    public ResponseEntity<SubscriptionResponseDTO> createSubscription(@Valid @RequestBody CreateSubscriptionRequestDTO createSubscriptionRequestDTO) {
        SubscriptionResponseDTO subscriptionResponseDTO = subscriptionService.createSubscription(
                createSubscriptionRequestDTO);

        log.info("Subscription: " + createSubscriptionRequestDTO + " created successfully");

        return ResponseEntity.ok(subscriptionResponseDTO);
    }

    @PutMapping("/admin/subscriptions/{id}")
    @Validated
    public ResponseEntity<SubscriptionResponseDTO> updateSubscription(@NotNull @PathVariable Integer id,
                                                                      @Valid @RequestBody UpdateSubscriptionRequestDTO updateSubscriptionRequestDTO) {

        SubscriptionResponseDTO subscriptionResponseDTO = subscriptionService.updateSubscription(id,
                updateSubscriptionRequestDTO);

        log.info("The subscription: " + id + " has successfully changed");

        return ResponseEntity.ok(subscriptionResponseDTO);
    }

    @DeleteMapping("/admin/subscriptions/{id}")
    @Validated
    public ResponseEntity<Void> deleteSubscription(@NotNull @PathVariable Integer id) {
        subscriptionService.deleteSubscription(id);

        log.info("The subscription: " + id + " has successfully deleted");

        return ResponseEntity.noContent().build();
    }

    @ExecutionTime
    @GetMapping("/client/subscriptions/me")
    public ResponseEntity<SubscriptionResponseDTO> getSubscriptionForClientByClientIdAndStatus() {
        int clientId = RecipientCurrentClientUtil.getCurrentClientId();

        SubscriptionResponseDTO subscriptionResponseDTO = subscriptionService.getSubscriptionByClientIdAndStatus(
                clientId);

        log.info("Subscription for Client: " + clientId + " successfully received");

        return ResponseEntity.ok(subscriptionResponseDTO);
    }

    @PostMapping("/client/subscriptions/subscribe/plan/{planId}")
    @Validated
    public ResponseEntity<SubscriptionResponseDTO> subscribeToPlan(@NotNull @ExistPlanId @PathVariable Integer planId) {
        int userId = RecipientCurrentClientUtil.getCurrentClientId();

        SubscriptionResponseDTO subscriptionResponseDTO = subscriptionService.subscribeToPlan(userId, planId);

        log.info("Client: " + userId + " successfully subscribed to plan: " + planId);

        return ResponseEntity.ok(subscriptionResponseDTO);
    }

    @PostMapping("/client/subscriptions/update/subscription/plan/{newPlanId}")
    @Validated
    public ResponseEntity<SubscriptionResponseDTO> updateSubscription(@NotNull @UniqueSubscription @PathVariable Integer newPlanId) {
        Integer userId = RecipientCurrentClientUtil.getCurrentClientId();

        SubscriptionResponseDTO subscriptionResponseDTO = subscriptionService.updateSubscriptionForClient(userId,
                newPlanId);

        log.info("Client: " + userId + " successfully updated subscription, changed the plan to: " + newPlanId);

        return ResponseEntity.ok(subscriptionResponseDTO);
    }

    @PutMapping("/client/subscriptions/cancel/subscription")
    public ResponseEntity<String> cancelSubscription() {
        Integer userId = RecipientCurrentClientUtil.getCurrentClientId();

        subscriptionService.cancelSubscription(userId);

        log.info("Client: " + userId + " successfully canceled subscription");

        return ResponseEntity.ok("Subscription successfully canceled");
    }
}
