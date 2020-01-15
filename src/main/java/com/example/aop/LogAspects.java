package com.example.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

@Aspect
public class LogAspects {

    /**
     * 抽取公共的切入点表达式
     *   -1-  若在本类中使用  则@Before("pointCut()")
     *   -2-  若在其他的切面类中使用  则使用全名  @Before("com.example.aop.LogAspects#pointCut()")
     */
    @Pointcut("execution(public int com.example.aop.MathCalculator.*(..))")
    public void pointCut() {

    }

    //@Before("public int com.example.aop.MathCalculator.div(int,int)")
    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature().getName() + " is running,the param is "+ Arrays.asList(joinPoint.getArgs()));
    }

    @After("pointCut()")
    public void logEnd(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature().getName() +  " is end");
    }

    @AfterReturning(value = "pointCut()",returning = "result")
    public void logReturn(JoinPoint joinPoint,Object result) {
        System.out.println(joinPoint.getSignature().getName() + " is running successful, the result is "+result);
    }

    @AfterThrowing(value = "pointCut()",throwing = "e")
    public void logException(JoinPoint joinPoint,Exception e) {
        System.out.println(joinPoint.getSignature().getName() + " is running with a  Exception : " + e);
    }

}
