package org.zh.auth;

import java.lang.annotation.*;

/**
 * @author ZhaoHang
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authentication {
}
