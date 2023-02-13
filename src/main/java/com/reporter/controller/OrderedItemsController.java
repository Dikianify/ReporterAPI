package com.reporter.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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
		private String lastEmailId = "4324234";
	
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
        	checkQueued();
        	return ordereditemsservice.createItem(itemDetails).get_id();
        }
 
        
        
        @RequestMapping(value = "/requeue/{itemId}", method=RequestMethod.POST)
        public ResponseEntity<OrderedItems> requeueEmail(@PathVariable(value = "itemId") Long itemId, HttpServletRequest request) throws Exception {
        	final String requestTokenHeader = request.getHeader("Authorization");
        	String jwtToken = requestTokenHeader.substring(7);
        	String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        	Long userId = userService.getUserFromUsername(username).get_id();
        	
        	OrderedItems itemDetails = ordereditemsservice.getItem(itemId);
        	if (userId != itemDetails.getUserid()) {
        		return ResponseEntity.badRequest().body(null);
        	}
        	checkQueued();
        	return ResponseEntity.ok().body(makeNewItem(itemDetails));
        }
        
        
        @RequestMapping(value = "/requeueFromDate/{strDate}", method=RequestMethod.POST)
        public List<OrderedItems> requeueFromDate (@PathVariable(value = "strDate") String strDate, HttpServletRequest request) throws Exception {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        	Date date = formatter.parse(strDate);
        	final String requestTokenHeader = request.getHeader("Authorization");
        	String jwtToken = requestTokenHeader.substring(7);
        	String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        	Long userId = userService.getUserFromUsername(username).get_id();

        	List<OrderedItems> itemDetails = ordereditemsservice.requeueFromDate(date, userId);
        	List<OrderedItems> newItemDetails = new ArrayList<>();
        	for (int i = 0; i < itemDetails.size(); i++) {
        		newItemDetails.add(makeNewItem(itemDetails.get(i)));
        	}
        	checkQueued();
        	return newItemDetails;
        }
        
        
        
        @RequestMapping(value = "/status/{itemId}", method=RequestMethod.GET)
        public String status(@PathVariable(value = "itemId") Long itemId, HttpServletRequest request) throws Exception, IOException {
        	// creates queued item and order from item details and item id
        	final String requestTokenHeader = request.getHeader("Authorization");
        	String jwtToken = requestTokenHeader.substring(7);
        	String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        	Long userId = userService.getUserFromUsername(username).get_id();
        	return ordereditemsservice.getStatus(userId, itemId);
        }
        
        
        
        @RequestMapping(value = "/multistatus/{strDate}", method=RequestMethod.GET)
        public List<OrderedItems> multistatus(@PathVariable(value = "strDate") String strDate, HttpServletRequest request) throws Exception, IOException {
        	// creates queued item and order from item details and item id
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        	Date date = formatter.parse(strDate);
        	final String requestTokenHeader = request.getHeader("Authorization");
        	String jwtToken = requestTokenHeader.substring(7);
        	String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        	Long userId = userService.getUserFromUsername(username).get_id();
        	return ordereditemsservice.getMultiStatus(date, userId);
        }
        

        
        public OrderedItems makeNewItem(OrderedItems oldItem) {
        	oldItem.setStatus("requeued");
        	ordereditemsservice.createItem(oldItem); //just saves the item 
        	
        	OrderedItems newItem = new OrderedItems();
        	newItem.setUserid(oldItem.getUserid());
        	newItem.setAttachments(oldItem.getAttachments());
        	newItem.setEmail(oldItem.getEmail());
        	newItem.setFax(oldItem.getFax());
        	newItem.setIdentifier(oldItem.getIdentifier());
        	newItem.setMessage(oldItem.getMessage());
    
        	return ordereditemsservice.createItem(newItem);
        }
        
        
        @Scheduled(fixedRate=100)
        public void checkFaxStatus() {
        	long fax = 12345;
        	String status = "fail";
        	// get fax number and status logic goes here
        	
        	OrderedItems item = ordereditemsservice.getSentByFax(fax);
        	// status logic goes here
        	if (status == "fail") {
        		Integer retries = item.getRetries();
        		if (retries < 3) {
        		item.setRetries(item.getRetries()+1);
        		}
        		else {
        			item.setStatus("failed");
        		}
        	}
        	else if (status == "success") {
        		item.setStatus("received");
        	}

        	checkQueued();
        	
        }
        
        public void checkQueued() {
        	List<OrderedItems> toSend = new ArrayList<>();
        	List<OrderedItems> queuedItems = ordereditemsservice.getItemFromStatus("queued");
        	
        	for (int i = 0; i<queuedItems.size();i++) {
        		if (ordereditemsservice.getSentByFax(queuedItems.get(i).getFax()) == null) {
        			toSend.add(queuedItems.get(i));
        		}
        	sendMessages(toSend);
        	}
        	
        }       
        
        
        public void sendMessages(List<OrderedItems> toSend) {
        	for (int i = 0; i<toSend.size();i++) {
        		toSend.get(i).setStatus("sent");     
        	}
        }
}