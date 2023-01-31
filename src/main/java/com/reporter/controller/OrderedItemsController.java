package com.reporter.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reporter.config.JwtTokenUtil;
import com.reporter.model.OrderedItems;
import com.reporter.service.OrderedItemsService;
import com.reporter.service.UserService;


@RestController
@RequestMapping("/api")
public class OrderedItemsController {
        @Autowired
        UserService userService;
        
        @Autowired
        OrderedItemsService ordereditemsservice;
        
    	@Autowired
    	private JwtTokenUtil jwtTokenUtil;
    	
        @RequestMapping(value = "/queue", method=RequestMethod.POST)
        public Long queueEmail(@RequestBody OrderedItems itemDetails, HttpServletRequest request) throws Exception, IOException {
        	// creates queued item and order from item details and item id
        	final String requestTokenHeader = request.getHeader("Authorization");
        	String jwtToken = requestTokenHeader.substring(7);
        	String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        	Long userId = userService.getUserFromUsername(username).get_id();
        	itemDetails.setUserid(userId);
        	return ordereditemsservice.createItem(itemDetails).get_id();
        }
 
        
        
        @RequestMapping(value = "/requeue/{itemId}", method=RequestMethod.POST)
        public Long requeueEmail(@PathVariable(value = "itemId") Long itemId, HttpServletRequest request) throws Exception {
        	final String requestTokenHeader = request.getHeader("Authorization");
        	String jwtToken = requestTokenHeader.substring(7);
        	String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        	Long userId = userService.getUserFromUsername(username).get_id();
        	
        	OrderedItems itemDetails = ordereditemsservice.getItem(itemId);
        	OrderedItems newItem = makeNewItem(userId, itemDetails);
        	return ordereditemsservice.createItem(newItem).get_id();
//        	Users user = userService.getUser(itemDetails.getUser_id());
//        	List<String> attachments = Sharepoint.sharepoint(user, itemDetails.getAttachments());
//        	SendEmail.sendEmail(itemDetails, attachments);
        }
        
        
        
        @RequestMapping(value = "/status", method=RequestMethod.GET)
        public String status(@PathVariable(value = "itemId") Long itemId, HttpServletRequest request) throws Exception, IOException {
        	// creates queued item and order from item details and item id
        	final String requestTokenHeader = request.getHeader("Authorization");
        	String jwtToken = requestTokenHeader.substring(7);
        	String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        	Long userId = userService.getUserFromUsername(username).get_id();
        	return ordereditemsservice.getStatus(itemId, userId);
        }
        
        
        
        @RequestMapping(value = "/multistatus", method=RequestMethod.GET)
        public List<OrderedItems> multistatus(@PathVariable(value = "date") String date, HttpServletRequest request) throws Exception, IOException {
        	// creates queued item and order from item details and item id
        	final String requestTokenHeader = request.getHeader("Authorization");
        	String jwtToken = requestTokenHeader.substring(7);
        	String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        	Long userId = userService.getUserFromUsername(username).get_id();
        	return ordereditemsservice.getMultiStatus(date, userId);
        }
        
        
        
        public OrderedItems makeNewItem(Long userId, OrderedItems oldItem) {
        	OrderedItems newItem = new OrderedItems();
        	newItem.setUserid(userId);
        	newItem.setAttachments(oldItem.getAttachments());
        	newItem.setEmail(oldItem.getEmail());
        	newItem.setFax(oldItem.getFax());
        	newItem.setIdentifier(oldItem.getIdentifier());
        	newItem.setMessage(oldItem.getMessage());
        	return newItem;
        }
}