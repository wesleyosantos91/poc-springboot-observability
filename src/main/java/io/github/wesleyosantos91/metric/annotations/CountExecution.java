package io.github.wesleyosantos91.metric.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CountExecution {
    String successCounter();
    String errorCounter();
    String[] successTags() default {};
    String[] errorTags() default {};
}
