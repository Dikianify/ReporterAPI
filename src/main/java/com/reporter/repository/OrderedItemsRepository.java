package com.reporter.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.reporter.model.OrderedItems;

@Repository
public interface OrderedItemsRepository extends JpaRepository<OrderedItems, Long> {
	
	List<OrderedItems> findByUserid(Long user_id);
	
	List<OrderedItems> findByFax(Long fax);
	
	List<OrderedItems> findByStatus(String status);
	
	@Query("SELECT u from OrderedItems u WHERE u.date > ?1 and u.userid = ?2")
	List<OrderedItems> findByDate(Date date, Long user_id);
	
	@Query("SELECT u from OrderedItems u WHERE u.status = 'failed' and u.date > ?1 and u.userid = ?2")
	List<OrderedItems> findByFailed(Date date, Long user_id);
	
	@Query("SELECT u from OrderedItems u WHERE u.status = 'sent' and u.userid = ?1 and u.fax = ?2")
	List<OrderedItems> findByFaxSent(Long fax, Long user_id);
	
	@Query("SELECT u from OrderedItems u WHERE u.status = 'sent' and u.fax = ?1")
	OrderedItems findSentByFax(Long fax);
}