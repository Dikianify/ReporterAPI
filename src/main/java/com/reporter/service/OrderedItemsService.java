package com.reporter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reporter.model.OrderedItems;
import com.reporter.repository.OrderedItemsRepository;

import java.util.List;

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
        
        public List<OrderedItems> getItemFromStatus(Long status) {
            return itemsRepository.findByStatus(status);
        }
        
        public List<OrderedItems> getItemFromFax(Long fax) {
            return itemsRepository.findByFax(fax);
        }        
        
        public List<OrderedItems> getItemsFromUser(Long user_id) {
            return itemsRepository.findByUserid(user_id);
        }
        
        public OrderedItems getItem(Long itemId) {
        	OrderedItems item = itemsRepository.findById(itemId).get();
        	return item;
        }
     

        // DELETE
        public void deleteItem(Long itemId) {
        	itemsRepository.deleteById(itemId);
        }
        
}
