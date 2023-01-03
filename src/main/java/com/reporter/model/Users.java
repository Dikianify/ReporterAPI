package com.reporter.model;

import utils.SecurePasswordGenerator;
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
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="salt")
	private String salt;
	
	
	
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Long get_id() {
		return _id;
	}

	public void set_id(Long _id) {
		this._id = _id;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws Exception {
        String salt = SecurePasswordGenerator.getNewSalt();
        String encryptedPassword = SecurePasswordGenerator.getEncryptedPassword(password, salt);
        setSalt(salt);
		this.password = encryptedPassword;
	}

    public boolean authenticateUser(String inputPass) throws Exception {
            String salt = getSalt();
            String calculatedHash = SecurePasswordGenerator.getEncryptedPassword(inputPass, salt);
            if (calculatedHash.equals(getPassword())) {
                return true;
            } else {
                return false;
            }
    }
 
}