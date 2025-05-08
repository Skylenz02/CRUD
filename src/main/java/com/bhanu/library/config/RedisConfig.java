package com.bhanu.library.config;

import com.bhanu.library.model.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.*;

@Configuration
public class RedisConfig {

    @Bean
    public ReactiveRedisTemplate<String, Book> bookReactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        RedisSerializationContext.SerializationPair<String> keyPair =
                RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer());

        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer();
        @SuppressWarnings("unchecked")
        RedisSerializationContext.SerializationPair<Book> valuePair =
                (RedisSerializationContext.SerializationPair<Book>) (RedisSerializationContext.SerializationPair<?>) RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer);

        RedisSerializationContext<String, Book> context = RedisSerializationContext
                .<String, Book>newSerializationContext(new StringRedisSerializer())
                .key(keyPair)
                .value(valuePair)
                .build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
