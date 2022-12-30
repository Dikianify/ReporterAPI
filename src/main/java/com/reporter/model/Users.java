package com.reporter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="_id")
    private Long _id;
	
	@Column(name="api_user")
	private String api_user;
	
	@Column(name="api_pass")
	private String api_pass;
	
	@Column(name="shpt_user")
	private String shpt_user;
	
	@Column(name="shpt_pass")
	private String shpt_pass;
	
	
	public Long get_id() {
		return _id;
	}

	public void set_id(Long _id) {
		this._id = _id;
	}

	public String getApi_user() {
		return api_user;
	}

	public void setApi_user(String api_user) {
		this.api_user = api_user;
	}

	public String getApi_pass() {
		return api_pass;
	}

	public void setApi_pass(String api_pass) {
		this.api_pass = api_pass;
	}

	public String getShpt_user() {
		return shpt_user;
	}

	public void setShpt_user(String shpt_user) {
		this.shpt_user = shpt_user;
	}

	public String getShpt_pass() {
		return shpt_pass;
	}

	public void setShpt_pass(String shpt_pass) {
		this.shpt_pass = shpt_pass;
	}


}