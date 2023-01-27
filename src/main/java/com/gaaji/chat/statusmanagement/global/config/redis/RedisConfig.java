package com.gaaji.chat.statusmanagement.global.config.redis;

import com.gaaji.chat.statusmanagement.global.config.redis.converter.MemberIdConversion;
import com.gaaji.chat.statusmanagement.global.config.redis.converter.RoomIdConversion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.port}")
    private int redisPort;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisHost, redisPort);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public RedisCustomConversions redisCustomConversions() {
        List<Converter> memberIdConversions = MemberIdConversion.conversions();
        List<Converter> roomIdConversions = RoomIdConversion.conversions();

        List<Converter> converters = Stream.of(memberIdConversions, roomIdConversions)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return new RedisCustomConversions(converters);
    }

}
