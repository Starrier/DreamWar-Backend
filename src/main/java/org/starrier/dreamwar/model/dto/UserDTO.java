package org.starrier.dreamwar.model.dto;

import lombok.Data;
import org.starrier.dreamwar.model.vo.User;

import java.util.Date;

/**
 * UsrDto，while user register and login,{@link User}
 *
 * @author Starrier
 * @date  2019/1/10
 * */
@Data
public class UserDTO {

    private String username;
    private String password;
    private String email;
    private Integer status;
    private String mobile;
    private Date update_time;
    private Date create_time;
    private String avatar;

    private String validateCode;

}
