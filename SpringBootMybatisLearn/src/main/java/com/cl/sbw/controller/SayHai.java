package com.cl.sbw.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cl.sbw.service.inter.TestService;


@RestController
public class SayHai {

	@Autowired
	TestService testService;
	@Resource(name="redisTemplate00")
	private RedisTemplate<String,Object> redisTemplate00;
	
	@RequestMapping("hai")
	public String hai() {
		testService.selectTest();
		Logger log = LoggerFactory.getLogger(this.getClass());
		System.out.println(redisTemplate00.opsForValue().get("aaaa"));
		return "hai, how are you!";
	}
}
