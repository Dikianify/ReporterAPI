package com.reporter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reporter.model.Users;
import com.reporter.repository.UsersRepository;

import java.util.List;

@Service
public class UserService {
        
        @Autowired
        	UsersRepository userRepository;

     // CREATE 
        public Users createUser(Users user) {
            return userRepository.save(user);
        }

        // READ
        public List<Users> getUsers() {
            return userRepository.findAll();
        }
        
        public Users getUser(Long userId) {
        	return userRepository.findById(userId).get();
        }

        // DELETE
        public void deleteUser(Long userId) {
            userRepository.deleteById(userId);
        }
        
     // UPDATE
        public Users updateUser(Long userId, Users userDetails) {
                Users user = userRepository.findById(userId).get();
                user.setApi_user(userDetails.getApi_user());
                user.setApi_pass(userDetails.getApi_pass());
                user.setShpt_user(userDetails.getShpt_user());
                user.setShpt_pass(userDetails.getShpt_pass());
                
                return userRepository.save(user);                                
        }

}


