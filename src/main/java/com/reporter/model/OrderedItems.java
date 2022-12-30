package com.reporter.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "ordereditems")
public class OrderedItems {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="_id")
    private Long _id;
	
	@Column(name="userid")
	private Long userid;
	
	@Column(name="indentifier")
	private String identifier;
	
	@Column(name="fax")
	private Long fax;
	
	@Column(name="email")
	private String email;
	
	@Column(name="message")
	private String message;
	
	@Column(name="attachments")
	private String attachments;
	
	@Column(name="status")
	private String status;
	
	@Column(name="status_message")
	private String statusMessage;
	
	@Column(name="retries")
	private String retries;
	
	@Column(name="date")
	private Date date;
	
	public Long get_id() {
		return _id;
	}

	public void set_id(Long _id) {
		this._id = _id;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long user_id) {
		this.userid = user_id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Long getFax() {
		return fax;
	}

	public void setFax(Long fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getAttachments() {
		List<String> attachmentList = new ArrayList<String>(Arrays.asList(attachments.split(",")));
		return attachmentList;
	}

	public void setAttachments(List<String> attachments) {
		this.attachments = StringUtils.join(attachments, ",");
	}

}