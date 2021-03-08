package com.example.BorsaUygulamasi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BorsaUygulamasi.model.User;


public interface UsertRepository extends JpaRepository<User,Integer>{
	
	User findByUsernameAndPassword(String username,String password);

}
