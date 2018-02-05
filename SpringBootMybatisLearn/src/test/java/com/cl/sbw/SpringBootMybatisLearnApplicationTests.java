package com.cl.sbw;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.cl.sbw.dao.impl.MongodbDao;
import com.cl.sbw.dao.pojo.TestPOJO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMybatisLearnApplicationTests {
	
	@Resource(name="redisTemplate00")
	private RedisTemplate<String,Object> redisTemplate00;
	
	
	@Resource(name="redisTemplate01")
	private RedisTemplate<String,Object> redisTemplate01;

	
	@Resource(name="test1")
	private TestPOJO test1;
	
	@Resource(name="test2")
	private TestPOJO test2;
	
	
	@Resource
	MongodbDao mongodb;
	
	
	@Test
	public void stringTest(){
        ValueOperations<String,Object> valueOperations = redisTemplate00.opsForValue();
        valueOperations.set("aaaa", "aaaa");
        System.out.println("useRedisDao = " + valueOperations.get("aaaa") + "   " + redisTemplate00);

        
        ValueOperations<String,Object> valueOperationsafter = redisTemplate01.opsForValue();
        valueOperationsafter.set("bbbb", "bbbb");
        System.out.println("useRedisDao = " + valueOperationsafter.get("bbbb") + "    " +  redisTemplate01);

        
        System.out.println(test1.getId());
        System.out.println(test2.getId());
    }

	@Test
	public void contextLoads() {
	}
	
	
	@Test
	public void mongdb() {
		TestPOJO save = new TestPOJO();
		save.setId("110");
		save.setValue("value110");
		this.mongodb.saveTest(save);
	}

}
