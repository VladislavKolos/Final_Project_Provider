package org.example.util;

import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utility class to get the current client ID.
 */
@Slf4j
public class RecipientCurrentClientUtil {

    private RecipientCurrentClientUtil() {

    }

    /**
     * Get the current client ID.
     *
     * @return Current client ID.
     */
    public static int getCurrentClientId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        log.info("Current Client ID for Client successfully retrieved");

        return user.getId();
    }
}
