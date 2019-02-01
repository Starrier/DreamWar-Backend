package org.starrier.dreamwar.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *  Customer Exception called {@link UserRegisterException} method,
 *  and this class must extends {@link RuntimeException}.
 *
 * @author Starrier
 * @date 2019/1/8.
 */
@Getter
@Setter
@AllArgsConstructor
public class UserRegisterException extends RuntimeException {

    private static final long serialVersionUID = 4262243725980810686L;

    private Integer code;

    private String message;

}
