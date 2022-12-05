package com.your.packages.admin.config;

import com.hccake.ballcat.common.redis.prefix.IRedisPrefixConverter;
import com.hccake.ballcat.common.redis.serialize.PrefixJdkRedisSerializer;
import com.hccake.ballcat.common.redis.serialize.PrefixStringRedisSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis 配置类，临时修复 Tianai 验证码导致的 redis 全局前缀不生效的问题
 *
 * @author hccake
 */
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class RedisConfiguration {

	private final RedisConnectionFactory redisConnectionFactory;

	@Bean
	public StringRedisTemplate stringRedisTemplate(IRedisPrefixConverter redisPrefixConverter) {
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(redisConnectionFactory);
		template.setKeySerializer(new PrefixStringRedisSerializer(redisPrefixConverter));
		return template;
	}

	@Bean
	public RedisTemplate<Object, Object> redisTemplate(IRedisPrefixConverter redisPrefixConverter) {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		template.setKeySerializer(new PrefixJdkRedisSerializer(redisPrefixConverter));
		return template;
	}

}