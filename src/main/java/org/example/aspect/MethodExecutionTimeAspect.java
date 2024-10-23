package org.example.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.exception.ProviderMethodExecutionException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging the execution time of methods.
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class MethodExecutionTimeAspect {

    private final MessageSource messageSource;

    /**
     * Intercepts methods annotated with the `@ExecutionTime` annotation.
     * This method uses the `@Around` annotation to intercept the execution of a method marked with the `@ExecutionTime` annotation.
     * It records the start time of the execution, calls the target method, handles any exceptions that are raised, and records the end time of the execution.
     * It then calculates and records the total execution time.
     *
     * @param proceedingJoinPoint An aspect connection point that provides information about the intercepted method.
     * @return The result of executing the target method.
     */
    @Around("@annotation(org.example.annotation.ExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint proceedingJoinPoint) {
        long startTime = System.currentTimeMillis();
        log.info("Execution of " + proceedingJoinPoint.getSignature() + " started at: " + startTime);

        Object result;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            log.error("Failed to execute method: " + proceedingJoinPoint.getSignature(), e);
            throw new ProviderMethodExecutionException(messageSource.getMessage("error.execution.fail",
                    null,
                    LocaleContextHolder.getLocale()), e);
        }

        long endTime = System.currentTimeMillis();
        log.info("Execution of " + proceedingJoinPoint.getSignature() + " ended at: " + endTime);

        long duration = endTime - startTime;
        log.info("Execution of " + proceedingJoinPoint.getSignature() + " took " + duration + " ms");

        return result;
    }
}
