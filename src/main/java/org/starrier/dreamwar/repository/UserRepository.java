package org.starrier.dreamwar.repository;


import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.starrier.dreamwar.entity.User;

/**
 * @Author Starrier
 * @Time 2018/6/5.
 */

@Repository
@CacheConfig(cacheNames = "user")
public interface UserRepository extends CrudRepository<User,Long> {

    User findByUsername(String username);

    User findUserById(Long id);

//    @CachePut(key = "#p0",value = "username")
  //  User selectUserByUsername(String username);

    //List<User> selectAllUsers();

    //Integer insertUser(User user);

   // Integer updateUserOnPasswordById(User user);

   // Integer deleteUserById(Long id);

}
