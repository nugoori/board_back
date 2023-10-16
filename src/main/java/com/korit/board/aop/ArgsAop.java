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
public class ArgsAop {
    // 매개 변수 정보 출력용
    @Pointcut("@annotation(com.korit.board.aop.annotation.ArgsAop)")
    private void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        Signature signature =  proceedingJoinPoint.getSignature();
        CodeSignature codeSignature = (CodeSignature) signature;

        String[] argNames = codeSignature.getParameterNames();
        Object[] args = proceedingJoinPoint.getArgs();

        System.out.println("==========================");
        System.out.println("className: " + argNames + ", method: " + args);
        for(int i = 0; i < argNames.length; i++) {
            System.out.println(argNames[i] + ": " + args[i]);
        }
        System.out.println("=============================================");

        return proceedingJoinPoint.proceed();
    }
}
