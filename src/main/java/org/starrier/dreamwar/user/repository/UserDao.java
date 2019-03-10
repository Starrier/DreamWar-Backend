package org.starrier.dreamwar.user.repository;

import org.springframework.stereotype.Repository;
import org.starrier.dreamwar.user.entity.User;

/**
 * @author Starrier
 * @date 2019/1/10.
 */
@Repository
public interface UserDao {

    User findUserByEmail(String email);

    User updateUser(User user);


}
