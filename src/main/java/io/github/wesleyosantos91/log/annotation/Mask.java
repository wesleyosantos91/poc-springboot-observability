package io.github.wesleyosantos91.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Mask {

    MaskType type() default MaskType.MIDDLE;
    int length() default 1;
    String symbol() default "*";

    enum MaskType {
        LEFT, RIGHT, MIDDLE
    }
}
