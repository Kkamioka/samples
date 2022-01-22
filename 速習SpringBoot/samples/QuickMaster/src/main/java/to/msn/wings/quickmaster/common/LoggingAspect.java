package to.msn.wings.quickmaster.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    // @Before("execution(* *..*Controller.*(..))")
    // @Before("within(*..*Controller)")
    public void beforeLog(JoinPoint jp) {
        System.out.println("［Before］ " + jp.getSignature());
    }
  
    //@AfterReturning(value="execution(* *..*Controller.*(..))",
    //   returning="result")
    public void afterReturnLog(JoinPoint jp, String result) {
        System.out.println("［AfterReturning］ " + jp.getSignature());
    }
  
    //@AfterThrowing(value="execution(* *..*Controller.*(..))",
    //  throwing="ex")
    public void afterThrowLog(JoinPoint jp, Exception ex) {
        System.out.println("［AfterThrowing］ " + jp.getSignature());    
    }
  
    //@Around("execution(* *..*Controller.*(..))")
    //public void AroundLog(JoinPoint jp) {
    public void AroundLog(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("［Before］ " + jp.getSignature());
        try {
            Object result = jp.proceed();
            System.out.println("［After］ " + jp.getSignature() + " -> " + result);
        } catch (Exception e) {
            System.out.println("［Error］ " + jp.getSignature());
        }
    }
}