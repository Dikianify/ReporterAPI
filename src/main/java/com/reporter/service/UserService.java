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
        public Users createUser(Users user) {
			try {
				user.setPassword(bcryptEncoder.encode(user.getPassword()));
			} catch (Exception e) {
				e.printStackTrace();
			}
            return userRepository.save(user);
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
        public Users updateUser(Long userId, Users userDetails) {
                Users user = userRepository.findById(userId).get();
                user.setUsername(userDetails.getUsername());
                try {
					user.setPassword(bcryptEncoder.encode(userDetails.getPassword()));
				} catch (Exception e) {
					e.printStackTrace();
				}
                
                return userRepository.save(user);                                
        }

}


