package org.starrier.dreamwar.repository;


import org.springframework.stereotype.Repository;
import org.starrier.dreamwar.model.entity.User;


import java.util.List;

/**
 * @Author Starrier
 * @Time 2018/6/5.
 */

@Repository
public interface UserRepository {

    User selectUserById(Long id);

    User selectUserByUsername(String username);

    List<User> selectAllUsers();

    Integer insertUser(User user);

    Integer updateUserOnPasswordById(User user);

    Integer deleteUserById(Long id);

}
