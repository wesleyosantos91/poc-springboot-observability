package io.github.wesleyosantos91.metric.aspects;

import io.github.wesleyosantos91.metric.annotations.CountExecution;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CountExecutionAspect {

    private final MeterRegistry meterRegistry;

    @Autowired
    public CountExecutionAspect(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Pointcut("@annotation(countExecution)")
    public void countExecutionPointcut(CountExecution countExecution) {
    }

    @Around("countExecutionPointcut(countExecution)")
    public Object countExecutionAdvice(ProceedingJoinPoint joinPoint, CountExecution countExecution) throws Throwable {
        Counter successCounter = Counter.builder(countExecution.successCounter())
                .tags(countExecution.successTags())
                .description("Successful executions")
                .register(meterRegistry);

        Counter errorCounter = Counter.builder(countExecution.errorCounter())
                .tags(countExecution.errorTags())
                .description("Executions with errors")
                .register(meterRegistry);

        try {
            Object result = joinPoint.proceed();
            successCounter.increment();
            return result;
        } catch (Throwable throwable) {
            errorCounter.increment();
            throw throwable;
        }
    }
}
