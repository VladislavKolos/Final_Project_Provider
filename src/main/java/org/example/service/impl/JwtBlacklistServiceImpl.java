package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.service.JwtBlacklistService;
import org.example.util.ProviderConstantUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Service for managing the blacklist of revoked JWT tokens.
 */
@Service
@RequiredArgsConstructor
public class JwtBlacklistServiceImpl implements JwtBlacklistService {

    private final Map<String, Long> tokenBlacklist = new ConcurrentHashMap<>();

    /**
     * Add JWT token to blacklist with an expiration time.
     *
     * @param token JWT token to add to the blacklist.
     */
    @Override
    @Transactional
    public void addTokenToBlacklist(String token) {
        long expirationTime = System.currentTimeMillis() + TimeUnit.HOURS.toMillis(ProviderConstantUtil.TIME_OUT);
        tokenBlacklist.put(token, expirationTime);
    }

    /**
     * Check if the JWT token is on the blacklist.
     *
     * @param token JWT token for verification.
     * @return true - if the token is found in the black list, false - otherwise.
     */
    @Override
    public boolean isTokenBlacklisted(String token) {
        Long expirationTime = tokenBlacklist.get(token);

        if (expirationTime != null && expirationTime < System.currentTimeMillis()) {
            tokenBlacklist.remove(token);
            return false;
        }
        return expirationTime != null;
    }
}
