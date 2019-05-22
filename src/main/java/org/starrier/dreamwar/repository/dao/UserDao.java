package org.starrier.dreamwar.repository.dao;

import org.springframework.stereotype.Repository;
import org.starrier.dreamwar.model.vo.User;

/**
 * @author Starrier
 * @date 2019/1/10.
 */
@Repository
public interface UserDao {

    User findUserByEmail(String email);

    User updateUser(User user);


}
