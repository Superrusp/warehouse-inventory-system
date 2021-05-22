package com.kpi.springlabs.backend.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class ExecutionTimeAdvice {

    @Around("@annotation(com.kpi.springlabs.backend.aop.TrackExecutionTime)")
    public Object trackExecutionTime(ProceedingJoinPoint point) {
        StopWatch watch = new StopWatch();
        watch.start();
        Object object = null;
        try {
            object = point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println(throwable.getMessage());
        }
        long executionTime = watch.getLastTaskTimeMillis();
        LOG.debug(String.format("Class Name: %s. Method Name: %s. Time taken for execution is: [%s] ms",
                point.getSignature().getDeclaringType(),
                point.getSignature().getName(),
                executionTime));
        return object;
    }
}
