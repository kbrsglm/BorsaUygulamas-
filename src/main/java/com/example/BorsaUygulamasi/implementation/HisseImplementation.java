package com.example.BorsaUygulamasi.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.BorsaUygulamasi.model.User;
import com.example.BorsaUygulamasi.model.Hisse;
import com.example.BorsaUygulamasi.repository.UsertRepository;
import com.example.BorsaUygulamasi.repository.HisseRepository;

@Component
public class HisseImplementation {
	
	@Autowired
	private  HisseRepository userRepository;
	
	public List<Hisse> getUsers(){
		return userRepository.findAll();
	}

	public  void saveUser(Hisse user ) {
		userRepository.save(user);
	}
	
	public Hisse getUser(Integer id) {
		return userRepository.findById(id).get();
	}
	
	public void deleteUserById(Integer id) {
		userRepository.deleteById(id);
	}

	
}
