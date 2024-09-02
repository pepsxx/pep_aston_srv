package com.xandr.pep_aston.aop;

import com.xandr.pep_aston.dto.BankAccountDto;
import com.xandr.pep_aston.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.xandr.pep_aston.log.Logger.setLogg;
import static com.xandr.pep_aston.log.LoggerType.*;

@Slf4j
@Aspect
@Order(1)
@Component
public class AspectFirst {

    @Pointcut("@within(org.springframework.stereotype.Service) && " +
              "execution(public * createBankAccount(..))")
    private void createBankAccount() {
    }

    @Before("createBankAccount() && args(userDto)")
    private void before(JoinPoint joinPoint, UserDto userDto) {

        if (userDto != null) {
            setLogg(NONE, joinPoint.getTarget().getClass().getSimpleName(), "Begin create new BankAccount for %s".formatted(userDto.getName()));
        }

    }

    @AfterReturning(value = "createBankAccount()", returning = "result")
    private void afterReturning(JoinPoint joinPoint, Optional<BankAccountDto> result) {

        if (result.isPresent()) {
            setLogg(OK, joinPoint.getTarget().getClass().getSimpleName(),
                    "The BankAccount for %s was created successfully. NumberBankAccount %s.".formatted(result.get().getName(), result.get().getNumberAccount()));
        } else {
            setLogg(BAD, joinPoint.getTarget().getClass().getSimpleName(), "The BankAccount for Gek do not created.");
        }

    }
}