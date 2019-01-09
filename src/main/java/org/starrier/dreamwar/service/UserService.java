package org.starrier.dreamwar.service;



import org.starrier.dreamwar.entity.User;
import org.starrier.dreamwar.entity.UserDto;

import java.util.List;

public interface UserService {

    /**
     * Insert new User.
     *
     * @param user user.
     * @return the result with user whether the process executed successfully or not.
     * */
    User save(UserDto user);

    /**
     * Fetch all the users.
     *
     * @return the users with list.
     * */
    List<User> findAll();

    /**
     *  Delete the user.
     *
     * @param id which is the unique identify of user when the ADMIN delete the user or
     *           the current user wants to delete itself.
     * */
    void delete(long id);

    /**
     * Find the specified user.
     *
     * @param username which the ADMIN query through the passed parameter.
     * @return the user which has found out.
     * */
    User findOne(String username);

    /**
     * Find user by user's id
     *
     * @param id
     * @return the found user.
     * */
    User findById(Long id);
}
