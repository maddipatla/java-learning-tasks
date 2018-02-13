/**
 *
 */
package com.learningbydoing.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Maddipatla Chandra Babu
 * @date 08-Feb-2018
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface DateOfBirth {
    Class<? extends Object> type();
}
