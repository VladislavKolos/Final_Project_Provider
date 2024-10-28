package org.example.validator.subscriptionvalidator;

import lombok.RequiredArgsConstructor;
import org.example.repository.SubscriptionRepository;
import org.example.util.ProviderConstantUtil;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionValidator {

    private final SubscriptionRepository subscriptionRepository;

    public boolean checkUniqueSubscription(Integer userId) {
        return subscriptionRepository.existsByUserIdAndStatus(userId, ProviderConstantUtil.SUBSCRIPTION_STATUS_SIGNED);
    }
}
