package com.reporter.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reporter.config.JwtTokenUtil;
import com.reporter.model.JwtResponse;
import com.reporter.model.Users;
import com.reporter.service.UserService;


@RestController
@RequestMapping("/api")
public class UserController {
        @Autowired
        UserService userService;
        
    	@Autowired
    	private JwtTokenUtil jwtTokenUtil;
        

        @RequestMapping(value="/register", method=RequestMethod.POST)
        public ResponseEntity<?> createUser(@RequestBody Users user) {
        	Users newUser = userService.createUser(user);
        	if (newUser != null) {
        		final String token = jwtTokenUtil.generateToken(newUser.getUsername());
        		return ResponseEntity.ok(new JwtResponse(token));
        	}
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }
        
        // change user details. sends back a bad request if username is already in use.

        @RequestMapping(value="/users", method=RequestMethod.PUT)
        public ResponseEntity<?> updateUser(@RequestBody Users userDetails, HttpServletRequest request) {
        	final String requestTokenHeader = request.getHeader("Authorization");
        	String jwtToken = requestTokenHeader.substring(7);
        	String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        	String msg = userService.updateUser(username, userDetails);
        	if (msg == "Success") {
        		final String token = jwtTokenUtil.generateToken(userDetails.getUsername());
        		return ResponseEntity.ok(new JwtResponse(token));
        	}
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        @RequestMapping(value="/users/{userId}", method=RequestMethod.DELETE)
        public void deleteEmployees(@PathVariable(value = "userId") Long id) {
            userService.deleteUser(id);
        }
        
        
}