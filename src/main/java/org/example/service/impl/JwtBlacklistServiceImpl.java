package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.service.JwtBlacklistService;
import org.example.util.ProviderConstantUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Service for managing the blacklist of revoked JWT tokens.
 */
@Service
@RequiredArgsConstructor
public class JwtBlacklistServiceImpl implements JwtBlacklistService {

    private final StringRedisTemplate redisTemplate;

    /**
     * Add JWT token to blacklist.
     *
     * @param token JWT token to add to the blacklist.
     */
    public void addTokenToBlacklist(String token) {
        redisTemplate.opsForValue().set(token, "blacklisted", ProviderConstantUtil.TIME_OUT, TimeUnit.HOURS);
    }

    /**
     * Check if the JWT token is on the blacklist.
     *
     * @param token JWT token for verification.
     * @return true - if the token is found in the black list, false - otherwise.
     */
    public boolean isTokenBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }
}
