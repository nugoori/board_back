package com.korit.board.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ReturnAop {

    @Pointcut("@annotation(com.korit.board.aop.annotation.ReturnAop)")
    private void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object target = proceedingJoinPoint.proceed();

//        Object result = proceedingJoinPoint.getArgs();
        System.out.println(proceedingJoinPoint);

        return target;
    }
}
