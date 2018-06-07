package org.starrier.dreamwar.repository;


import org.springframework.stereotype.Repository;
import org.starrier.dreamwar.model.entity.User;


import java.util.List;

/**
 * @author Xiaoyue Xiao
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
