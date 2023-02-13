package com.reporter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.reporter.model.Users;
import com.reporter.repository.UsersRepository;

import java.util.List;

@Service
public class UserService {
        
        @Autowired
        UsersRepository userRepository;
        
    	@Autowired
    	private PasswordEncoder bcryptEncoder;
  
     // CREATE 
        public Users createUser(Users newUser) {
        	Users oldUser = getUserFromUsername(newUser.getUsername());
        	if (oldUser == null) {
				newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
	            return userRepository.save(newUser);
        	}
        	return null;
        }

        // READ
        public List<Users> getUsers() {
            return userRepository.findAll();
        }
        
        public Users getUser(Long userId) {
        	return userRepository.findById(userId).get();
        }
        
        public Users getUserFromUsername(String username) {
        	return userRepository.findByUsername(username);
        }

        // DELETE
        public void deleteUser(Long userId) {
            userRepository.deleteById(userId);
        }
        
     // UPDATE
        public String updateUser(String username, Users userDetails) {
        		Users user = getUserFromUsername(username);
        		Users oldUser = getUserFromUsername(userDetails.getUsername());
        		if (oldUser == null || user.getUsername() == oldUser.getUsername()) {
	                user.setUsername(userDetails.getUsername());
					user.setPassword(bcryptEncoder.encode(userDetails.getPassword()));
					userRepository.save(user);
	                return "Success";
                }
        		return "Username is taken";
        }

}


