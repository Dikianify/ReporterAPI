package com.reporter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reporter.model.OrderedItems;

@Repository
public interface OrderedItemsRepository extends JpaRepository<OrderedItems, Long> {
	List<OrderedItems> findByUserid(Long user_id);
	List<OrderedItems> findByFax(Long fax);
	List<OrderedItems> findByStatus(Long status);
}