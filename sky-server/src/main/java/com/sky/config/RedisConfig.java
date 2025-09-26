package com.sky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author YueYang
 * Created on 2025/9/26 14:15
 * @version 1.0
 */
@Configuration
@Slf4j
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("开始设置redis模板对象... ");
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        //设置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //设置key的序列化器
        //大key使用字符串序列化，值使用json序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());


        //设置hash键和值的序列话
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;

    }
}
