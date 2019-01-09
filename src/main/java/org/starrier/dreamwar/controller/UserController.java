package org.starrier.dreamwar.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.starrier.dreamwar.common.ResponseCode;
import org.starrier.dreamwar.entity.User;
import org.starrier.dreamwar.entity.UserDto;
import org.starrier.dreamwar.enums.ExchangeEnum;
import org.starrier.dreamwar.enums.TopicEnum;
import org.starrier.dreamwar.service.RabbitmqService;
import org.starrier.dreamwar.service.UserService;

import java.util.List;
import java.util.UUID;

/**
 * User Controller.
 *
 * @author Starrier
 * @date 2019/1/9
 * */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final RabbitmqService rabbitmqService;

    @Autowired
    public UserController(UserService userService, RabbitmqService rabbitmqService) {
        this.userService = userService;
        this.rabbitmqService = rabbitmqService;
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

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        try {
            rabbitmqService.userRegisterSendAndAck(ExchangeEnum.USER_REGISTER_TOPIC_EXCHANGE, TopicEnum.USER_REGISTER.getTopicRouteKey(), user, correlationData);
            LOGGER.info("RabbitMQ Queue Has Receive Message:{}", user);
            return userService.save(user);
        }catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Article have got error:[{}]", e.getMessage());
            }
            return userService.save(user);
        }

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
