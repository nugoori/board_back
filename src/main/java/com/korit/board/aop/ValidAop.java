package com.korit.board.aop;

import com.korit.board.exception.ValidException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.HashMap;
import java.util.Map;

/**
 * APO는 필터와 같은 역할을 한다
 * (filter(req, resp, chain 객체)) .proceed = chain.dofilter
 * */

@Aspect
@Component
public class ValidAop {

    /*
    * execution(접근지정자[기본값=public] 반환타입 경로(매개 변수 '.. = 있어도 되고 없어도 됨')) -> 많은 부분에 한번에 적용할 때 편함
    * annotation
    * */

//    @Pointcut("execution(* com.korit.board.controller.*.*(..))")
    @Pointcut("@annotation(com.korit.board.aop.annotation.ValidAop)")
    private void pointCut() {}

    @Around("pointCut()")/* 포인트 컷 - 오타 나면 안됨, 다양한 방법이 있지만 이 방법으로 다 할 수 있음 */
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{


        // proceedingJoinPoint.getSignature(); // class 정보?
        Object[] args = proceedingJoinPoint.getArgs();
        BeanPropertyBindingResult bindingResult = null;
        for(Object arg : args) {
            // System.out.println(arg.getClass()); // class com.korit.board.dto.SignupReqDto class org.springframework.validation.BeanPropertyBindingResult
            if(arg.getClass() == BeanPropertyBindingResult.class) {
                bindingResult = (BeanPropertyBindingResult) arg;
                break;
            }
        }

        if(bindingResult == null) {
            return proceedingJoinPoint.proceed();
        }

        if(bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            });
            throw new ValidException(errorMap);
        }

        System.out.println("전처리");
        Object target = proceedingJoinPoint.proceed(); // 메소드의 body (return 값)
        System.out.println("후처리");

        return target;
    }
}
