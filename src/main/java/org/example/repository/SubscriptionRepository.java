package org.example.repository;

import org.example.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for working with the `Subscription` entity.
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    Optional<Subscription> findByUserIdAndStatus(Integer userId, String status);

    boolean existsByUserIdAndPlanIdAndStatus(Integer userId, Integer planId, String status);

    boolean existsByUserIdAndStatus(Integer userId, String status);

    boolean existsByUserId(Integer userId);
}
