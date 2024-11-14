package org.example.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TransactionalAuditAspect {

    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object logTransactionalAudit(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        log.info("Executing method: {} with arguments: {}",
                proceedingJoinPoint.getSignature().getName(),
                proceedingJoinPoint.getArgs());

        try {
            Object result = proceedingJoinPoint.proceed();

            log.info("Method {} executed successfully with result: {}",
                    proceedingJoinPoint.getSignature().getName(),
                    result);

            return result;

        } catch (Throwable e) {
            log.info("Method {} threw an exception: {}",
                    proceedingJoinPoint.getSignature().getName(),
                    e.getMessage(),
                    e);
            throw e;
        }
    }
}
