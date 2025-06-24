package com.itheima.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(4)
//@Aspect
@Component
public class MyAspect1 {
    @Pointcut("execution(* com.itheima.service.impl.*.*(..))")
    private void pt(){

    }

    //前置通知 - 目标方法运行前通知
    @Before("pt()")
    public void before() {
        log.info("MyAspect1.before()");
    }
    //环绕通知 - 目标方法运行前后都通知
    @Around("pt()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("MyAspect1.around() - 前置通知");
        Object result = pjp.proceed();
        log.info("MyAspect1.around() - 后置通知");
        return result;
    }
    //后置通知 - 目标方法运行后通知,无论是否异常都执行
    @After("pt()")
    public void after() {
        log.info("MyAspect1.after()");
    }
    //返回后通知 - 目标方法正常返回后通知,如果出现异常不会运行
    @AfterReturning("pt()")
    public void afterReturning() {
        log.info("MyAspect1.afterReturning()");
    }
    //异常后通知 - 目标方法出现异常后通知,只有出现异常才会执行
    @AfterThrowing("pt()")
    public void afterThrowing() {
        log.info("MyAspect1.afterThrowing()");
    }

}
