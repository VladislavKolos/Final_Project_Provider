package org.example.repository;

import org.example.model.PromotionTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionTariffRepository extends JpaRepository<PromotionTariff, Integer> {
}
