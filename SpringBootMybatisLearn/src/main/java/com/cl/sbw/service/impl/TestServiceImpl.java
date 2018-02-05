package com.cl.sbw.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cl.sbw.dao.Dao;
import com.cl.sbw.dao.pojo.TestPOJO;
import com.cl.sbw.db.annotation.Master;
import com.cl.sbw.db.annotation.Slave;
import com.cl.sbw.service.inter.TestService;



@Service
public class TestServiceImpl implements TestService {
	@Resource
	Dao dao;
	
	
	@Master
	public void selectTest() {
		// TODO Auto-generated method stub
		TestPOJO test = new TestPOJO();
		test.setId("zy");
		TestPOJO b = (TestPOJO) this.dao.selectOne(test);
		System.out.println(b.getValue());
		
		TestPOJO c = (TestPOJO) this.dao.findOne(test);
		System.out.println(c.getValue());
	}
	
	@Slave
	public void selectTest2() {
		TestPOJO test = new TestPOJO();
		test.setId("zy");
		TestPOJO b = (TestPOJO) this.dao.selectOne(test);
		System.out.println(b.getValue());
	}

}
