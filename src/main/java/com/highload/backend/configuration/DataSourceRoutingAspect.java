package com.highload.backend.configuration;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Aspect
@Component
public class DataSourceRoutingAspect {

    @Pointcut("@within(org.springframework.transaction.annotation.Transactional) " +
        "|| @annotation(org.springframework.transaction.annotation.Transactional)")
    public void transactionalMethods() {
    }

    @Before("transactionalMethods()")
    public void setContext() {
        TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
        if (status.isReadOnly()) {
            DataSourceContextHolder.setMode("READ");
        } else {
            DataSourceContextHolder.setMode("WRITE");
        }
    }

    @After("@within(org.springframework.transaction.annotation.Transactional) " +
        "|| @annotation(org.springframework.transaction.annotation.Transactional)")
    public void clearContext() {
        DataSourceContextHolder.clear();
    }
}
