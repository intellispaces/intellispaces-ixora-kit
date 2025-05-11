package tech.intellispaces.ixora.rdb.annotation;

import tech.intellispaces.reflections.framework.annotation.Dataset;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Dataset
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Projection {

  String query() default "";
}
