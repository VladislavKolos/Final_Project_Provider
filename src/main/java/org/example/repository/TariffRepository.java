package org.example.repository;

import org.example.model.Tariff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

/**
 * Repository for working with the `Tariff` entity.
 */
@Repository
public interface TariffRepository extends JpaRepository<Tariff, Integer> {

    boolean existsByName(String name);

    Page<Tariff> findAll(@NonNull Pageable pageable);
}
