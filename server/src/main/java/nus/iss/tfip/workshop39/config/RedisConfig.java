package nus.iss.tfip.workshop39.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    private Integer redisDB = 0;

    // value redis host from appln.properties
    @Value("${spring.redis.host}")
    private String redisHost;

    // value redis port from appln.properties
    @Value("${spring.redis.port}")
    private Optional<Integer> redisPort;

    @Value("${spring.redis.username}")
    private String redisUsername;

    @Value("${spring.redis.password}")
    private String redisPassword;

    // define the return redis template bean as single Object
    // throughout the runtime.
    // Return the RedisTemplate
    @Bean
    @Scope("singleton")
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();

        config.setHostName(redisHost);
        config.setPort(redisPort.get());

        if (!redisUsername.isEmpty() && !redisPassword.isEmpty()) {
            config.setUsername(redisUsername);
            config.setPassword(redisPassword);
        }

        config.setDatabase(redisDB);

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisFac);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }

}
