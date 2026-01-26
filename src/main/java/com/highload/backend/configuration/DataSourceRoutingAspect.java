package com.highload.backend.configuration;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceRoutingAspect {

    @Pointcut("@within(com.highload.backend.configuration.ReadOnly) " +
        "|| @annotation(com.highload.backend.configuration.ReadOnly)" +
    "|| @within(com.highload.backend.configuration.Write) " +
        "|| @annotation(com.highload.backend.configuration.Write)")
    public void transactionalMethods() {
    }

    @Before("transactionalMethods() && @within(com.highload.backend.configuration.ReadOnly) " +
        "|| @annotation(com.highload.backend.configuration.ReadOnly)" )
    public void setContextRead() {
        DataSourceContextHolder.setMode("READ");
    }

    @Before("transactionalMethods() && @within(com.highload.backend.configuration.Write) " +
        "|| @annotation(com.highload.backend.configuration.Write)" )
    public void setContextWrite() {
        DataSourceContextHolder.setMode("WRITE");
    }

    @After("transactionalMethods()")
    public void clearContext() {
        DataSourceContextHolder.clear();
    }
}
