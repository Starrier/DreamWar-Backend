package org.starrier.dreamwar.user.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.starrier.dreamwar.user.entity.User;
import org.starrier.dreamwar.user.repository.UserDao;

/**
 * @author Starrier
 * @date 2019/1/10.
 */
@Mapper
public interface UserMapper extends UserDao {


    /**
     * Find user by the current user's email.
     *
     * @param email
     * @return user
     * */
    @Override
    User findUserByEmail(String email);

    /**
     * update user by passed user.
     *
     * @param user which is going to update.
     * @return the user which has been updated.
     * */
    @Override
    User updateUser(User user);
}
