package org.example.repository;

import org.example.model.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for working with the `EmailToken` entity.
 */
@Repository
public interface EmailTokenRepository extends JpaRepository<EmailToken, Integer> {

    Optional<EmailToken> findByToken(String token);
}
