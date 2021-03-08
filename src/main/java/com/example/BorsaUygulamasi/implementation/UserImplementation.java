package com.example.BorsaUygulamasi.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.BorsaUygulamasi.model.User;
import com.example.BorsaUygulamasi.model.Hisse;
import com.example.BorsaUygulamasi.repository.UsertRepository;

@Component
public class UserImplementation {

	User sonuc;
	
	@Autowired
	public UsertRepository productRepository;
	
	public List<User> getProducts(){
		return productRepository.findAll();
	}
	
	public void saveProducts(User products ) {
		productRepository.save(products);
	}
	
	public void deleteProductById(Integer id) {
		productRepository.deleteById(id);
	}
	
	public User getProduct(Integer id) {
		return productRepository.findById(id).get();
	}
	
	public User login(String username,String password) {
		 sonuc= productRepository.findByUsernameAndPassword(username, password);
		//System.err.println(u.getId());
		return sonuc;
	}
	
}
