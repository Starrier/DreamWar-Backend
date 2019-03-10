package org.starrier.dreamwar.user.entity;

import lombok.Data;
import org.starrier.dreamwar.user.entity.User;

import java.util.Date;

/**
 * UsrDto，while user register and login,{@link User}
 *
 * @author Starrier
 * @date  2019/1/10
 * */
@Data
public class UserDto {

    private String username;
    private String password;
    private String email;
    private int status;
    private String mobile;
    private Date update_time;
    private Date create_time;
    private String avatar;

    private String validateCode;

}
