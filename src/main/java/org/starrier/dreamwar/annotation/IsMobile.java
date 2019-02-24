package org.starrier.dreamwar.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 *  Information: this {@link IsMobile} annotation has been {@link Deprecated}
 *                since {@link @version 0.0.1}
 *
 * @author Starrier
 * @date 2019/1/7.
 *
 * @version 0.0.1
 */
@Documented
@Target({
        ElementType.METHOD,
        ElementType.FIELD
})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {IsMobileValidator.class})
public @interface IsMobile {

    /**
     * {@value} the default value is false;
     *  check failure,output {@param message}
     * @return  the {@value} with {@link Boolean} type.
     * */
    boolean required() default false;

    /**
     * Define the message which will return the detailed message
     * when the validator has failed.
     * @return the message.
     * */
    String message() default "手机格式校验失败，请核对手机格式";
}
