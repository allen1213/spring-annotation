package com.debug.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logger {

    @Pointcut("execution(* com.debug.service.*.*(..))")
    public void pointCut() {}

    @Before("pointCut()")
    public void before() {
        System.out.println("- beforeLog -");
    }

    @After("pointCut()")
    public void after() {
        System.out.println("- afterLog -");
    }

    @AfterThrowing("pointCut()")
    public void afterThrowing() {
        System.out.println("- afterThrowing -");
    }

    @AfterReturning(value = "pointCut()")
    public void afterReturning(JoinPoint joinPoint) {

        System.out.println("- afterReturning -");
    }

    /*@Around("pointCut()")
    public void around() {
        System.out.println("- aroundLog -");
    }*/

}

