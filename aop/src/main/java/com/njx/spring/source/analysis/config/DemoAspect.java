package com.njx.spring.source.analysis.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DemoAspect {
    @Pointcut("execution(* com.njx.spring.source.analysis.dao.*.*(..))")
    public void pointCutExecution() {
    }

    @Pointcut("within(com.njx.spring.source.analysis.dao.*)")
    public void pointCutWithin() {

    }

    @Pointcut("args(java.lang.Integer)")
    public void pointCutArgs() {
    }

    @Pointcut("@annotation(com.njx.spring.source.analysis.annotation.AopAnnotation)")
    public void pointCutAnnotation() {

    }

    @Pointcut("this(com.njx.spring.source.analysis.dao.Dao)")
    public void pointCutThis() {

    }

    @Pointcut("target(com.njx.spring.source.analysis.dao.IndexDao)")
    public void pointCutTarget() {

    }

    /**
     * 位置
     * 逻辑代码
     */
    @Before("pointCutThis()")
    public void before() {
        System.out.println("before");
    }

    @After("pointCutThis()")
    public void after(JoinPoint jp) {
        System.out.println(jp.getArgs());
        System.out.println("after");
    }

    @Around("pointCutThis()")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        Object proceed = pjp.proceed();

        System.out.println("around");
    }


}
