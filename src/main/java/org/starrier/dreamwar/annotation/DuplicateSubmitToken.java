package org.starrier.dreamwar.annotation;

import java.lang.annotation.*;

/**
 * @author Starrier
 * @date 2018/12/9.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DuplicateSubmitToken {

    /**
     * Save duplicate commit tags,default to true
     * */
    boolean save() default true;

}
