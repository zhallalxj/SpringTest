package org.zh.auth;

import java.lang.annotation.*;

/**
 * @author Li ShaoQing
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authentication {
    String permission() default "login";
}
