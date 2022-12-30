package com.reporter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reporter.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

}