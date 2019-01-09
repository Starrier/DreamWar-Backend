package org.starrier.dreamwar.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.starrier.dreamwar.common.ResponseCode;
import org.starrier.dreamwar.entity.User;
import org.starrier.dreamwar.entity.UserDto;
import org.starrier.dreamwar.service.UserService;

import java.util.List;

/**
 * User Controller.
 *
 * @author Starrier
 * @date 2019/1/9
 * */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value="/users")
    public List<User> listUser(){
        return userService.findAll();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/users/{id}")
    public User getOne(@PathVariable(value = "id") Long id){
        return userService.findById(id);
    }

    @PostMapping(value="/signup")
    public User saveUser(@RequestBody UserDto user){
        return userService.save(user);
    }

    @ResponseBody
    @GetMapping("/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> findUserByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.findOne(username));
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseCode deleteUserById(@PathVariable("id") Long id) {
        try {
            userService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseCode.success();
    }

}
