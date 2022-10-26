package com.bee.sample.ch12;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Reids -> REmote DIctionary Server 内存存储的数据结构服务器
 * key -- value
 * 支持字符串、列表、集合、有序集合、位图、地理空间信息等数据
 * 同时也可以作为高速缓存和消息队列代理
 */
@SpringBootApplication
public class Ch12RedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ch12RedisApplication.class, args);
	}
}
