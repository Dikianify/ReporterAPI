package com.reporter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reporter.model.OrderedItems;
import com.reporter.repository.OrderedItemsRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderedItemsService {
        
        @Autowired
        	OrderedItemsRepository itemsRepository;

        // CREATE 
        public OrderedItems createItem(OrderedItems item) {
            return itemsRepository.save(item);
        }
        

        // READ
        public List<OrderedItems> getItems() {
            return itemsRepository.findAll();
        }
        
        public List<OrderedItems> getItemFromStatus(String status) {
            return itemsRepository.findByStatus(status);
        }
        
        public List<OrderedItems> getItemFromFax(Long fax) {
            return itemsRepository.findByFax(fax);
        }        
        
        public List<OrderedItems> getItemsFromUser(Long user_id) {
            return itemsRepository.findByUserid(user_id);
        }
        
        public String getStatus(Long userId, Long itemId) {
        	Optional<OrderedItems> item = itemsRepository.findById(itemId);
        	if (item.isPresent()) {
        		Long itemUserId = item.get().getUserid();
        		if (itemUserId == userId) {
        			return item.get().getStatus();
        		}
        		return "User cannot access item";
        	}
        	return "Invalid item ID";
        }
        
        public List<OrderedItems> getMultiStatus(Date date, Long userId) {
        	return itemsRepository.findByDate(date, userId);
        }
        
        public List<OrderedItems> requeueFromDate(Date date, Long userId) {
        	return itemsRepository.findByFailed(date, userId);
        }
        
        public OrderedItems getSentByFax(long fax) {
        	return itemsRepository.findSentByFax(fax);
        }
       
        
        public OrderedItems getItem(Long itemId) {
        	return itemsRepository.findById(itemId).get();
        }
        

        // DELETE
        public void deleteItem(Long itemId) {
        	itemsRepository.deleteById(itemId);
        }
        
}

