package com.cl.sbw.dao.pojo;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class TestPOJO  implements Serializable {
	
	private static final long serialVersionUID = -3258839839160856613L;

	private String id;
	private String value;
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
