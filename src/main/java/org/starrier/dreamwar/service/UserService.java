package org.starrier.dreamwar.service;



import org.starrier.dreamwar.model.entity.User;
import org.starrier.dreamwar.model.entity.UserDto;

import java.util.List;

public interface UserService {

    User save(UserDto user);
    List<User> findAll();
    void delete(long id);
    User findOne(String username);

    User findById(Long id);
}
