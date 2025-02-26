package com.djccnt15.spring_board.annotations;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// this annotation is for annotating type of component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component  // register this class and implements as a spring bean
public @interface Property {
    
    @AliasFor(annotation = Component.class)
    String value() default "";
}
