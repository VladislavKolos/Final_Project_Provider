package org.example.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Application lifecycle listener.
 */
@Slf4j
@Component
public class ProviderLifecycleListener {

    /**
     * This method is called after the application has successfully launched.
     * It logs a message to a log indicating the current application startup time in milliseconds.
     *
     * @param event Application launch event
     */
    @EventListener
    public void handleApplicationReady(ApplicationReadyEvent event) {
        log.info("Application started at: " + LocalDateTime.now());
    }

    /**
     * This method is called before the Spring context is stopped (for example, when the application terminates).
     * It logs a message to a log indicating the current time the application stopped in milliseconds.
     *
     * @param event Context stop event
     */
    @EventListener
    public void handleContextClosed(ContextClosedEvent event) {
        log.info("Application is stopping at: " + LocalDateTime.now());
    }

    /**
     * This method is called if the application has failed.
     * It logs the error message to a log, including the current time in milliseconds that the application crashed and the exception associated with the error.
     *
     * @param event Application crash event
     */
    @EventListener
    public void handleApplicationFailed(ApplicationFailedEvent event) {
        log.error("Application failed at: " + LocalDateTime.now(), event.getException());
    }
}
