package org.example.repository;

import org.example.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for working with the `Status` entity.
 */
@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {

    Optional<Status> findByName(String name);

    boolean existsByName(String name);

}
