/*
 * (c) copyright, 2017 
 */
package com.dbm.app.entity;

import java.io.Serializable;

/**
 * This is main class of our application.
 * This is the model class of our application.
 * 
 * @author ravi ranjan kumar
 * @since 2017-10-01
 *
 */
public class Users implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String city;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}


}
