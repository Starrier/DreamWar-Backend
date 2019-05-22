package org.starrier.dreamwar.repository.repository;


import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.starrier.dreamwar.model.vo.User;

/**
 * @Author Starrier
 * @Time 2018/6/5.
 */

@Repository
@CacheConfig(cacheNames = "user")
public interface UserRepository extends CrudRepository<User,Long> {

    User findByUsername(String username);

    User findUserByEmail(String email);

    User findUserById(Long id);



//    @CachePut(key = "#p0",value = "username")
  //  UserBO selectUserByUsername(String username);

    //List<UserBO> selectAllUsers();

    //Integer insertUser(UserBO user);

   // Integer updateUserOnPasswordById(UserBO user);

   // Integer deleteUserById(Long id);

}
