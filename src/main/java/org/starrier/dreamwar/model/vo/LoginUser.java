package org.starrier.dreamwar.model.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * @author Starrier
 * @date 2019/1/7
 * */
@Data
public class LoginUser implements Serializable {

    private String username;
    private String password;
}
