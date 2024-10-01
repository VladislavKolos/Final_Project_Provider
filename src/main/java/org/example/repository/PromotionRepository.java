package org.example.repository;

import org.example.model.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

/**
 * Repository for working with the `Promotion` entity.
 */
@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

    Page<Promotion> findAll(@NonNull Pageable pageable);
}
