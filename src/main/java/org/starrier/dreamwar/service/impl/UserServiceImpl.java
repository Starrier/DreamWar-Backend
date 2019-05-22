package org.starrier.dreamwar.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.starrier.dreamwar.model.vo.User;
import org.starrier.dreamwar.repository.dao.UserDao;
import org.starrier.dreamwar.repository.repository.UserRepository;
import org.starrier.dreamwar.service.interfaces.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  UserBO Service.
 *
 * @author Starrier
 * @date 2019/1/10
 * */
@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder bcryptEncoder;

	private final UserDao userDao;

	@Autowired
	public UserServiceImpl(@Qualifier("userRepository") UserRepository userRepository, BCryptPasswordEncoder bcryptEncoder, UserDao userDao) {
		this.userRepository = userRepository;
		this.bcryptEncoder = bcryptEncoder;
		this.userDao = userDao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
	}

	private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
		});
		return authorities;
		//return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	@Override
	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userRepository.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User findOne(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findById(Long id) {
		return userRepository.findById(id).get();
	}

	/**
	 * Find user by email.
	 *
	 * @param email user'email
	 * @return the user
	 */
	@Override
	public User findByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}

	/**
	 * Activate register user.
	 *
	 *  1. Determine whether the user exists or not.
	 *  2. Determine whether the user has been activated.
	 *  3. Activation if the user is not active.
	 *  4. Verify that the links has expired.
	 *  5.
	 *
	 * @param email
	 * @param validateCode
	 * */
	@Override
	public void processActivate(String email, String validateCode){
		User user = userDao.findUserByEmail(email);


		/**
		 * 1. 用户是否存在
		 * 2. 用户是否已经激活
		 * 3. 如果没有激活，进行激活。
		 * 4.
		 * */
		if (user != null) {
			if (user.getStatus() == 0) {
				if (validateCode== "11") {
					if (LOGGER.isInfoEnabled()) {
						LOGGER.info("The user,[{}],has been registered.", user.getUsername());
					}
					user.setStatus(1);
					userDao.updateUser(user);
				}
			}
		}
	}

	/**
	 * Determine whether new register user already exists or not.
	 *
	 * @param user new register user
	 * @return the boolean, indicates whether the user already exists or not.
	 *         <p>
	 *             if the user exist, them method will return true,otherwise,false.
	 *         </p>
	 * */
	@Override
	public boolean userExist(User user) {
		return userRepository.findByUsername(user.getUsername()) != null;
	}

	@Override
    public User save(User user) {
		new User();
		User newUser = User.builder()
				.username(user.getUsername())
				.password(bcryptEncoder.encode(user.getPassword()))
				.email(user.getEmail())
				.status(0)
				.validateCode(user.getId())
				.build();

		return userRepository.save(newUser);
    }


}
