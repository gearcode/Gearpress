package com.gearcode.gearpress.annotation;

import java.lang.annotation.*;

@Documented
@Inherited
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {

}