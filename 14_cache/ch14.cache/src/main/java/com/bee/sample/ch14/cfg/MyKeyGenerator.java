package com.bee.sample.ch14.cfg;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

/**
 * @author linhx
 * @date 2022-10-27 15:12
 * @description 自定义生成key的方式
 */
public class MyKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object o, Method method, Object... objects) {
        return null;
    }
}
