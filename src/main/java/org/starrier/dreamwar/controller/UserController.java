package org.starrier.dreamwar.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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


}
