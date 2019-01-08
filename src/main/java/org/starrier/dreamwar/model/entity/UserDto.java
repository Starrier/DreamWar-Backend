package org.starrier.dreamwar.model.entity;

import lombok.Data;

import java.util.Date;

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

}
