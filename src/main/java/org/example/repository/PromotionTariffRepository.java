package org.example.repository;

import org.example.model.PromotionTariff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionTariffRepository extends JpaRepository<PromotionTariff, Integer> {

    @Query("SELECT pt FROM PromotionTariff pt JOIN pt.promotion p ORDER BY p.discountPercentage")
    Page<PromotionTariff> findAllWithPromotionSorting(Pageable pageable);
}
