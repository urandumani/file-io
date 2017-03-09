package com.ekipa.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = Logger.getLogger(LoggingAspect.class);

    @Before("execution(* com.ekipa.service.impl.FileServiceImpl.* (java.lang.String)) && args(id)")
    public void logBefore(JoinPoint joinPoint, String id) {
        logger.info("Entering method " + joinPoint.getSignature().getName() + " with path id " + id);
    }

    @After("execution(* com.ekipa.service.impl.FileServiceImpl.* (java.lang.String)) && args(id)")
    public void logAfter(JoinPoint joinPoint, String id) {
        logger.info("Exiting method " + joinPoint.getSignature().getName() + " with path id " + id);
    }
}