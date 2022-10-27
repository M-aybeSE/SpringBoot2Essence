package com.bee.sample.ch14.cfg;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
/**
 * 配置缓存
 * @author xiandafu
 *
 */
//@Configuration
public class RedisCacheManagerCustomizer {

    //	@Bean
	public RedisCacheManager getRedisCacheManager(RedisConnectionFactory connectionFactory) {
		RedisCacheWriter cacheWriter = RedisCacheWriter.lockingRedisCacheWriter(connectionFactory);
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer(loader);
		SerializationPair<Object> pair = SerializationPair.fromSerializer(jdkSerializer);

		RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
		cacheConfig = cacheConfig.entryTtl(Duration.ofSeconds(3600));//设置所有的超时时间

		//设置单个缓存的超时时间

		Map<String, RedisCacheConfiguration> initialCacheConfigurations = new HashMap<>();
		initialCacheConfigurations.put("user",cacheConfig.entryTtl(Duration.ofSeconds(60)));


		RedisCacheManager cacheManager = new RedisCacheManager(cacheWriter, cacheConfig,initialCacheConfigurations);

		return cacheManager;
	}

	public CacheManagerCustomizer<RedisCacheManager> cacheManagerCustomizer() {
		return new CacheManagerCustomizer<RedisCacheManager>() {
			@Override
			public void customize(RedisCacheManager cacheManager) {
				Map<String, Long> expires = new HashMap<>();
				// 设置menu缓存，一分钟过期
				expires.put("menu", 60L);
//				cacheManager.setExpires(expires);
			}
		};
	}
}
