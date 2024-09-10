package com.xandr.pep_aston.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.xandr.pep_aston.log.Logger.setLogg;
import static com.xandr.pep_aston.log.LoggerType.*;

@Slf4j
@Aspect
@Order(0)
@Component
public class AspectCommon {

    private static final String FILTER_BEGIN_END = "isRepositoryTarget() || " +
                                                   "isRestController() || " +
                                                   "isService() || " +
                                                   "isMapper()";
    private static final String FILTER_RESULT = "isRestController() || " +
                                                "isService()";

    @Pointcut("target(org.springframework.data.jpa.repository.JpaRepository)")
    private void isRepositoryTarget() { // Ctrl + Alt + Shift + C (Мне это пока надо)
    }

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    private void isRestController() {
    }

    @Pointcut("@within(org.springframework.stereotype.Service)")
    private void isService() {
    }

    @Pointcut("within(com.xandr.pep_aston.mapper.*MapperImpl)")
    private void isMapper() {
    }

    @AfterReturning(value = FILTER_RESULT, returning = "result")
    private void before(JoinPoint joinPoint, Object result) {

        setLogg(RETURN, joinPoint.getTarget().getClass().getSimpleName(), result.toString());

    }

    @Before(FILTER_BEGIN_END)
    private void beforeMethod(JoinPoint joinPoint) {

        setLogg(BEGIN,
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName());

    }

    @After(FILTER_BEGIN_END)
    private void afterMethod(JoinPoint joinPoint) {

        setLogg(END,
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName());

    }
}
