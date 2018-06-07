package org.starrier.dreamwar.model.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Xiaoyue Xiao
 */
@Accessors(chain = true)
public class Error implements Serializable {

    private static final long serialVersionUID = 7660756960387438399L;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *  Error  code

     */

    private int code;


    public void setCode(int code) {
        this.code = code;
    }

    /**
     *   Error Message
     * */
    private String message;

}
