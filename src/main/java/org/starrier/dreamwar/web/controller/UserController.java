package org.starrier.dreamwar.web.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.starrier.dreamwar.service.UserService;

import javax.annotation.Resource;

/**
 * @Author Starrier
 * @Time 2018/6/7.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping(value = "/login")
    public String login(String username, String password) {
        return "login";
    }

    @PostMapping(value = "/register")
    public String Register(String username, String password) {
        return "hello";
    }
}
