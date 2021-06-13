package com.fb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.fb.document.Product;
public interface ProductRepo extends MongoRepository<Product, String> {
	public Optional<Product> findById(String id);
	public List<Product> findByStatus(String status);
	public List<Product> findByCategory(String status);
}
