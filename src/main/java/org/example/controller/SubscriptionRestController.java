package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Subscriptions", description = "Operations related to managing subscriptions (Admin adn Client)")
public class SubscriptionRestController {
    private final SubscriptionService subscriptionService;

    @ExecutionTime
    @GetMapping("/admin/subscriptions")
    @Operation(summary = "Get all subscriptions for Admin", description = "Retrieves a list of all subscriptions (for Admin)")
    public ResponseEntity<List<SubscriptionResponseDTO>> getAllSubscriptions() {
        List<SubscriptionResponseDTO> subscriptions = subscriptionService.getAllSubscriptions();

        log.info("Subscriptions successfully received");

        return ResponseEntity.ok(subscriptions);
    }

    @ExecutionTime
    @GetMapping("/admin/subscriptions/{id}")
    @Validated
    @Operation(summary = "Get a subscription by ID for Admin", description = "Retrieves a subscription by its unique identifier (for Admin)")
    @Parameter(name = "id", description = "Unique identifier of the subscription")
    public ResponseEntity<SubscriptionResponseDTO> getSubscriptionByIdForAdmin(@NotNull @PathVariable Integer id) {
        SubscriptionResponseDTO subscriptionResponseDTO = subscriptionService.getSubscriptionById(id);

        log.info("Subscription: " + id + " for Admin successfully received");

        return ResponseEntity.ok(subscriptionResponseDTO);
    }

    @PostMapping("/admin/subscriptions")
    @Operation(summary = "Create a new subscription (Admin)", description = "Creates a new subscription (for Admin)")
    public ResponseEntity<SubscriptionResponseDTO> createSubscription(@Valid @RequestBody CreateSubscriptionRequestDTO createSubscriptionRequestDTO) {
        SubscriptionResponseDTO subscriptionResponseDTO = subscriptionService.createSubscription(
                createSubscriptionRequestDTO);

        log.info("Subscription: " + createSubscriptionRequestDTO + " created successfully");

        return ResponseEntity.ok(subscriptionResponseDTO);
    }

    @PutMapping("/admin/subscriptions/{id}")
    @Validated
    @Operation(summary = "Update a subscription (Admin)", description = "Updates a subscription by its ID (for Admin)")
    @Parameter(name = "id", description = "Unique identifier of the subscription")
    public ResponseEntity<SubscriptionResponseDTO> updateSubscription(@NotNull @PathVariable Integer id,
                                                                      @Valid @RequestBody UpdateSubscriptionRequestDTO updateSubscriptionRequestDTO) {

        SubscriptionResponseDTO subscriptionResponseDTO = subscriptionService.updateSubscription(id,
                updateSubscriptionRequestDTO);

        log.info("The subscription: " + id + " has successfully changed");

        return ResponseEntity.ok(subscriptionResponseDTO);
    }

    @DeleteMapping("/admin/subscriptions/{id}")
    @Validated
    @Operation(summary = "Delete a subscription (Admin)", description = "Deletes a subscription by its ID (for Admin)")
    @Parameter(name = "id", description = "Unique identifier of the subscription")
    public ResponseEntity<Void> deleteSubscription(@NotNull @PathVariable Integer id) {
        subscriptionService.deleteSubscription(id);

        log.info("The subscription: " + id + " has successfully deleted");

        return ResponseEntity.noContent().build();
    }

    @ExecutionTime
    @GetMapping("/client/subscriptions/me")
    @Operation(summary = "Get subscription for Client", description = "Retrieves the client's subscription based on their ID and status")
    public ResponseEntity<SubscriptionResponseDTO> getSubscriptionForClientByClientIdAndStatus() {
        int clientId = RecipientCurrentClientUtil.getCurrentClientId();

        SubscriptionResponseDTO subscriptionResponseDTO = subscriptionService.getSubscriptionByClientIdAndStatus(
                clientId);

        log.info("Subscription for Client: " + clientId + " successfully received");

        return ResponseEntity.ok(subscriptionResponseDTO);
    }

    @PostMapping("/client/subscriptions/subscribe/plan/{planId}")
    @Validated
    @Operation(summary = "Subscribe to a plan (Client)", description = "Subscribes the client to a specific plan")
    @Parameter(name = "planId", description = "Unique identifier of the plan to subscribe to")
    public ResponseEntity<SubscriptionResponseDTO> subscribeToPlan(@Valid @NotNull @ExistPlanId @UniqueSubscription @PathVariable Integer planId) {
        int userId = RecipientCurrentClientUtil.getCurrentClientId();

        SubscriptionResponseDTO subscriptionResponseDTO = subscriptionService.subscribeToPlan(userId, planId);

        log.info("Client: " + userId + " successfully subscribed to plan: " + planId);

        return ResponseEntity.ok(subscriptionResponseDTO);
    }

    @PostMapping("/client/subscriptions/update/subscription/plan/{newPlanId}")
    @Validated
    @Operation(summary = "Update subscription (Client)", description = "Updates the client's subscription to a new plan")
    @Parameter(name = "newPlanId", description = "Unique identifier of the new plan")
    public ResponseEntity<SubscriptionResponseDTO> updateSubscription(@Valid @NotNull @ExistPlanId @UniqueSubscription @PathVariable Integer newPlanId) {
        Integer userId = RecipientCurrentClientUtil.getCurrentClientId();

        SubscriptionResponseDTO subscriptionResponseDTO = subscriptionService.updateSubscriptionForClient(userId,
                newPlanId);

        log.info("Client: " + userId + " successfully updated subscription, changed the plan to: " + newPlanId);

        return ResponseEntity.ok(subscriptionResponseDTO);
    }

    @PutMapping("/client/subscriptions/cancel/subscription")
    @Operation(summary = "Cancel subscription (Client)", description = "Cancels the client's subscription")
    public ResponseEntity<String> cancelSubscription() {
        Integer userId = RecipientCurrentClientUtil.getCurrentClientId();

        subscriptionService.cancelSubscription(userId);

        log.info("Client: " + userId + " successfully canceled subscription");

        return ResponseEntity.ok("Subscription successfully canceled");
    }
}
