package org.example.repository;

import org.example.model.PromotionTariff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

/**
 * Repository for working with the `PromotionTariff` entity.
 */
@Repository
public interface PromotionTariffRepository extends JpaRepository<PromotionTariff, Integer> {
    Page<PromotionTariff> findAll(@NonNull Pageable pageable);
}
