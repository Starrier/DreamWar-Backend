package org.starrier.dreamwar.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.starrier.dreamwar.model.entity.User;

import java.util.Optional;

/**
 * @author Xiaoyue Xiao
 */

public interface UserService extends UserDetailsService {

    Optional<User> getUserById(Long id);

    boolean saveUser(User user);

    boolean modifyUserOnPasswordById(User user);

    boolean deleteUserById(Long id);

}
