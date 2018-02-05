package com.cl.sbw.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.cl.sbw.dao.pojo.TestPOJO;

@Service
public class MongodbDao {

	 @Autowired
	 private MongoTemplate mongoTemplate;
	 
	 public void saveTest(TestPOJO testInfo) {
		 mongoTemplate.save(testInfo);
	 }

}
