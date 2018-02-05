package com.cl.sbw.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cl.sbw.dao.Dao;
import com.cl.sbw.db.annotation.Master;
import com.cl.sbw.db.annotation.Slave;


@Repository
public class DaoImpl<T> implements Dao<T> {
	@Autowired
	SqlSessionFactory sqlSessionFactory;

	@Override
	public boolean insert(Object obj) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			boolean result =  session.insert(getOName(obj) + ".insert", obj) > 0;
			return result;
		}finally {
			session.close();
		}
	}

	@Override
	public boolean insert(Object obj, String table) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			boolean result =session.insert(getOName(obj) + "." + table, obj) > 0;
			return result;
		}finally {
			session.close();
		}
	}

	@Override
	public boolean insert(Map<String, Object> search) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return session.insert(search.get("table") + ".insert", search) > 0;
		}finally {
			session.close();
		}
	
	}

	@Override
	public boolean insert(Map<String, Object> search, String table) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return session.insert(search.get("table") + "." + table, search) > 0;
		}finally {
			session.close();
		}
	}

	@Override
	public boolean delete(Object obj) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return  session.delete(getOName(obj) + ".delete", obj) > 0;
		}finally {
			session.close();
		}
		
	}

	@Override
	public boolean delete(Object obj, String table) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return session.delete(getOName(obj) + "." + table, obj) > 0;
		}finally {
			session.close();
		}
	}

	@Override
	public boolean delete(Map<String, Object> search) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return session.delete(search.get("table") + ".delete", search) > 0;
		}finally {
			session.close();
		}
	}

	@Override
	public boolean delete(Map<String, Object> search, String table) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return session.delete(search.get("table") + "." + table, search) > 0;
		}finally {
			session.close();
		}
	}

	@Override
	public boolean update(Object obj) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return session.update(getOName(obj) + ".update", obj) > 0;
		}finally {
			session.close();
		}
	}

	@Override
	public boolean update(Object obj, String table) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return session.update(getOName(obj) + "." + table, obj) > 0;
		}finally {
			session.close();
		}
	}

	@Override
	public boolean update(Map<String, Object> search) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return session.update(search.get("table") + ".update", search) > 0;
		}finally {
			session.close();
		}
	}

	@Override
	public boolean update(Map<String, Object> search, String table) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return session.update(search.get("table") + "." + table, search) > 0;
		}finally {
			session.close();
		}
		
	}
	@Override
	@Slave
	public List<T> list(Object obj) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return session.selectList(getOName(obj) + ".list" , obj);
		}finally {
			session.close();
		}
	}
	@Override
	public List<T> list(Object obj, String table) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return session.selectList(getOName(obj) + "."+table , obj);
		}finally {
			session.close();
		}
	}

	@Override
	public List<T> list(Map<String, Object> search) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return session.selectList(search.get("table") + ".list" , search);
		}finally {
			session.close();
		}
	}

	@Override
	public List<T> list(Map<String, Object> search, String table) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return session.selectList(search.get("table") + "."+table , search);
		}finally {
			session.close();
		}
	}

	@Override
	public T selectOne(Object obj) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return session.selectOne(getOName(obj) + ".find" , obj);
		} finally {
			session.close();
		}
	}
 

	@Override
	public T selectOne(Object obj, String table) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return session.selectOne(getOName(obj) + "."+table , obj);
		}finally {
			session.close();
		}
	}

	@Override
	@Master
	public T selectOne(Map<String, Object> search) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return session.selectOne(search.get("table") + ".fing" , search);
		}finally {
			session.close();
		}
	}

	@Override
	public T selectOne(Map<String, Object> search, String table) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return session.selectOne(search.get("table") + "."+table , search);
		}finally {
			session.close();
		}
	}

	private String getOName(Object obj) {
		String str = obj.getClass().getName();
		return str.substring(str.lastIndexOf(".") + 1);
	}

	@Override
	@Slave
	public T findOne(Object obj) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return session.selectOne(getOName(obj) + ".find" , obj);
		}finally {
			session.close();
		}
	}
}
