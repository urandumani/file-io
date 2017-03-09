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

    @Before("execution(* com.ekipa.service.impl.FileServiceImpl.* (..))")
    public void logBefore(JoinPoint joinPoint) {
        Object[] arguments = joinPoint.getArgs();
        if (arguments.length == 0) {
            logger.info("Entering method " + joinPoint.getSignature().getName());
        } else {
            Object argumentId = arguments[0].toString();
            logger.info("Entering method " + joinPoint.getSignature().getName() + " with path id " + argumentId);
        }
    }

    @After("execution(* com.ekipa.service.impl.FileServiceImpl.* (..))")
    public void logAfter(JoinPoint joinPoint) {
        Object[] arguments = joinPoint.getArgs();
        if (arguments.length == 0) {
            logger.info("Exiting method " + joinPoint.getSignature().getName());
        } else {
            Object argumentId = arguments[0].toString();
            logger.info("Exiting method " + joinPoint.getSignature().getName() + " with path id " + argumentId);
        }
    }
}