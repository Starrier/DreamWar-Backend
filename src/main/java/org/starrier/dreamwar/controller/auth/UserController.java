package org.starrier.dreamwar.controller.auth;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.starrier.dreamwar.utils.common.Result;
import org.starrier.dreamwar.utils.common.enums.ResultCode;
import org.starrier.dreamwar.model.vo.User;
import org.starrier.dreamwar.utils.common.enums.ExchangeEnum;
import org.starrier.dreamwar.utils.common.enums.TopicEnum;
import org.starrier.dreamwar.service.interfaces.RabbitmqService;
import org.starrier.dreamwar.service.interfaces.UserService;
import org.starrier.dreamwar.utils.FastJsonConvertUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * UserBO Controller.
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
    public ResponseEntity<?>  saveUser(@RequestBody @Valid User user, BindingResult bindingResult){
        boolean userExist = userService.userExist(user);
        if (userExist) {
            String message = "username has been exist!";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        List<ObjectError> allErrors;
        if (bindingResult.hasErrors()) {
            allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError : allErrors) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Validate process occurs errors:[{}]", objectError);
                }
            }
            String message = String.format("Register Fail,More details[%s]", bindingResult.getFieldError().getDefaultMessage());
            return  ResponseEntity
                    .status(HttpStatus.PARTIAL_CONTENT)
                    .body(allErrors);
        }
        try {
            sendRegisterEmail(user);
            userService.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Article have got error:[{}]", e.getMessage());
            }
            return ResponseEntity.badRequest().build();
        }

    }

    private void sendRegisterEmail(User user) {

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("New UserBO:[{}] has been registered", user);
            LOGGER.info("RabbitMQ Queue Has Receive Message:{}", user);
        }

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitmqService.userRegisterSendAndAck(ExchangeEnum.USER_REGISTER_TOPIC_EXCHANGE, TopicEnum.USER_REGISTER.getTopicRouteKey(), FastJsonConvertUtil.toJsonObject(user), correlationData);
    }

    @RequestMapping(value = "/validate", method = {RequestMethod.POST, RequestMethod.GET})
    public Result registerValidate(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("The request action is : [{}]", action);
        }
        String email = request.getParameter("email");
        String validateCode = request.getParameter("validateCode");
        try {
            userService.processActivate(email, validateCode);
            return Result.success();
        } catch (Exception e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("The process has failed:[{}]", e.getMessage());
            }
            return Result.error(ResultCode.RESULE_DATA_NONE, "failed");
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
    public Result deleteUserById(@PathVariable("id") Long id) {
        try {
            userService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success();
    }

}
