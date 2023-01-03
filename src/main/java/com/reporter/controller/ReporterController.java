package com.reporter.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reporter.model.OrderedItems;
import com.reporter.model.Users;
import com.reporter.service.OrderedItemsService;
import com.reporter.service.UserService;

//import utils.Sharepoint;
//import utils.SendEmail;

@RestController
@RequestMapping("/api")
public class ReporterController {
        @Autowired
        UserService userService;
        
        @Autowired
        OrderedItemsService ordereditemsservice;
        

        @RequestMapping(value="/users", method=RequestMethod.POST)
        public Users createUser(@RequestBody Users user) {
            return userService.createUser(user);
        }
        
        @RequestMapping(value="/users", method=RequestMethod.GET)
        public List<Users> readUsers() {
            return userService.getUsers();
        }

        @RequestMapping(value="/users/{userId}", method=RequestMethod.PUT)
        public Users readEmployees(@PathVariable(value = "userId") Long id, @RequestBody Users userDetails) {
            return userService.updateUser(id, userDetails);
        }

        @RequestMapping(value="/users/{userId}", method=RequestMethod.DELETE)
        public void deleteEmployees(@PathVariable(value = "userId") Long id) {
            userService.deleteUser(id);
        }
        
        @RequestMapping(value = "/queueemail", method=RequestMethod.POST)
        public OrderedItems queueEmail(@PathVariable(value = "itemId") Long itemId, @RequestBody OrderedItems itemDetails) throws Exception, IOException {
        	// creates queued item and order from item details and item id
        	return ordereditemsservice.createItem(itemDetails);
        }
        
        @RequestMapping(value = "/requeueemail", method=RequestMethod.POST)
        public OrderedItems requeueEmail(@PathVariable(value = "itemId") Long itemId) throws Exception {
        	OrderedItems itemDetails = ordereditemsservice.getItem(itemId);
        	return ordereditemsservice.createItem(itemDetails);
//        	Users user = userService.getUser(itemDetails.getUser_id());
//        	List<String> attachments = Sharepoint.sharepoint(user, itemDetails.getAttachments());
//        	SendEmail.sendEmail(itemDetails, attachments);
        }
        
        @RequestMapping(value="/login", method=RequestMethod.POST)
        public void readStatus(@PathVariable(value = "username") String username, @PathVariable(value = "userPass") String pass) {
            Users user = userService.getUserFromUsername(username);
            
        }
}