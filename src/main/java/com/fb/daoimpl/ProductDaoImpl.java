package com.fb.daoimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fb.dao.ProductDao;
import com.fb.document.Product;
import com.fb.repository.ProductRepo;
@Component
public class ProductDaoImpl implements ProductDao {
	@Autowired
	ProductRepo productRepo;
	
	@Override
	public List<Product> getProducts() {
		return productRepo.findAll();
		//return null;
	}

	@Override
	public List<Product> getByProductsCategory(String category) {
		//return productRepo.findByCategory(category);
		return null;
	}

	@Override
	public Optional<Product> getByProductsId(String id) {
		//return productRepo.findById(id);
		return null;
	}
	
}
