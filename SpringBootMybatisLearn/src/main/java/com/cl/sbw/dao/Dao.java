package com.cl.sbw.dao;

import java.util.List;
import java.util.Map;


public interface Dao<T> {
	public boolean insert(Object obj);
	public boolean insert(Object obj,String table);
	public boolean insert(Map<String,Object> search);
	public boolean insert(Map<String,Object> search,String table);
	
	public boolean delete(Object obj);
	public boolean delete(Object obj,String table);
	public boolean delete(Map<String,Object> search);
	public boolean delete(Map<String,Object> search,String table);
	
	
	public boolean update(Object obj);
	public boolean update(Object obj,String table);
	public boolean update(Map<String,Object> search);
	public boolean update(Map<String,Object> search,String table);
 
	public List<T> list(Object obj);
	public List<T> list(Object obj,String table);
	public List<T> list(Map<String,Object> search);
	public List<T> list(Map<String,Object> search ,String table);
	
	public T findOne(Object obj);
	public T selectOne(Object obj);
	public T selectOne(Object obj,String table);
	public T selectOne(Map<String,Object> search);
	public T selectOne(Map<String,Object> search ,String table);
}
