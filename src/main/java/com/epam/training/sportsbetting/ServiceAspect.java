package com.epam.training.sportsbetting;

import java.util.Arrays;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAspect.class);

    @Pointcut("execution (* com.epam.training.sportsbetting.service.SportBettingService.*(..))")
    private void serviceMethod() {
    }

    @Before("serviceMethod()")
    public void logParametersBefore(final JoinPoint joinPoint) {
        LOGGER.info(joinPoint.getSignature().getName() + " called with args: " + Arrays.toString(joinPoint.getArgs()));
    }

    @Around("serviceMethod()")
    public Object writeExecutionTime(final ProceedingJoinPoint joinPoint) throws Throwable {
        long startedNanoTime = System.nanoTime();
        LOGGER.warn(joinPoint.getSignature().getName() + " STARTED: " + new Date());
        Object result = joinPoint.proceed();
        LOGGER.warn(joinPoint.getSignature().getName() + " ENDED: " + new Date());
        LOGGER.warn(joinPoint.getSignature().getName() + " LASTED (sec): " + (System.nanoTime() - startedNanoTime) / 1000000000.0);
        return result;

    }

    @AfterReturning(value = "serviceMethod()", returning = "returnValue")
    public void logReturnValueAfter(final JoinPoint joinPoint, Object returnValue) {
        LOGGER.info(joinPoint.getSignature().getName() + " returned with: " + (returnValue == null ? "no return value" : returnValue.toString()));
    }

}
