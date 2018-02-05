package com.cl.sbw.db.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.cl.sbw.dao.pojo.TestPOJO;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 缓存配置
 * @author cl
 *
 */
@Configuration
public class RedisCatchConfig extends CachingConfigurerSupport  {
	@Value("${spring.redis.host}")
    private String host;
    
    @Value("${spring.redis.port}")
    private int port;
    
    @Value("${spring.redis.timeout}")
    private int timeout;
    
    @Value("${spring.redis.database}")
    private int database;
    
    @Value("${spring.redis.password}")
    private String password;

    /**
     * 连接redis的工厂类
     * @return
     */
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(host);
        factory.setPort(port);
        factory.setTimeout(timeout);
        factory.setPassword(password);
   //     factory.setDatabase(database);
        return factory;
    }

    
    
    /**
     * 配置RedisTemplate
     * 设置添加序列化器
     * key 使用string序列化器
     * value 使用Json序列化器
     * 还有一种简答的设置方式，改变defaultSerializer对象的实现。
     * @return
     */
    @Bean(name="redisTemplate00")
    public RedisTemplate<String, Object> redisTemplate0() {
        //StringRedisTemplate的构造方法中默认设置了stringSerializer
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //set key serializer
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        //set value serializer
        template.setDefaultSerializer(jackson2JsonRedisSerializer);
        JedisConnectionFactory jedisConnectFact = jedisConnectionFactory();
//        JedisConnectionFactory factory = new JedisConnectionFactory();
//        factory.setHostName(host);
//        factory.setPort(port);
//        factory.setTimeout(timeout);
//        factory.setPassword(password);
        jedisConnectFact.setDatabase(0);
        jedisConnectFact.afterPropertiesSet();
        template.setConnectionFactory(jedisConnectFact);
        template.afterPropertiesSet();
        return template;
    }
    
    
    @Bean(name="redisTemplate01")
    public RedisTemplate<String, Object> redisTemplate1() {
        //StringRedisTemplate的构造方法中默认设置了stringSerializer
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //set key serializer
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        //set value serializer
        template.setDefaultSerializer(jackson2JsonRedisSerializer);
       JedisConnectionFactory jedisConnectFact = jedisConnectionFactory();
//        JedisConnectionFactory factory = new JedisConnectionFactory();
//        factory.setHostName(host);
//        factory.setPort(port);
//        factory.setTimeout(timeout);
//        factory.setPassword(password);
//        factory.setDatabase(0);
        jedisConnectFact.setDatabase(1);
        jedisConnectFact.afterPropertiesSet();
        template.setConnectionFactory(jedisConnectFact);
        template.afterPropertiesSet();
        return template;
    }
  

    
    @Bean(name="test1")
    public TestPOJO getTest1() {
    	TestPOJO a = new TestPOJO();
    	a.setId("1");
    	a.setValue("1");
    	return a;
    }
    
    @Bean(name="test2")
    public TestPOJO getTest2() {
    	TestPOJO a = new TestPOJO();
    	a.setId("2");
    	a.setValue("2");
    	return a;
    }
}
