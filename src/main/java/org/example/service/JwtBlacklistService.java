package org.example.service;

import org.springframework.stereotype.Component;

/**
 * This interface provides methods for adding tokens to the blacklist and checking if a token
 * is present in the blacklist.
 */
@Component
public interface JwtBlacklistService {
    void addTokenToBlacklist(String token);

    boolean isTokenBlacklisted(String token);
}
