package org.example.util;

import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class RecipientCurrentClientUtil {
    public static int getCurrentClientId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        log.info("Current Client ID successfully retrieved");

        return user.getId();
    }
}
