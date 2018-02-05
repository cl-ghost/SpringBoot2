package com.cl.sbw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cl.sbw.service.inter.TestService;

@RestController
public class SayHai {

	@Autowired
	TestService testService;
	
	@RequestMapping("hai")
	public String hai() {
		testService.selectTest();
		return "hai, how are you!";
	}
}
