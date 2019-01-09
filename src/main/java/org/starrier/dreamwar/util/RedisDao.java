/*
package org.starrier.dreamwar.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.UnknownHostException;

*/
/**
 * This is the service class to operation.
 *
 * @author Starrier
 * @date 2018/12/30
 * *//*

@Service
public class RedisDao {

	private final StringRedisTemplate stringRedisTemplate;

	@Resource(name = "stringRedisTemplate")
    ValueOperations<String, String> valOpsStr;

	@Autowired
	public RedisDao(final StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	public void saveString(final String key, final String value) {
		valOpsStr.set(key, value);
	}

	public String getString(final String key) {
		return valOpsStr.get(key);
	}

	public void deleteString(final String key) {
		stringRedisTemplate.delete(key);
	}


}
*/
