package com.fb.dao;

import java.util.List;
import java.util.Optional;

import com.fb.document.Product;

public interface ProductDao {
	public List<Product> getProducts();
	public List<Product> getByProductsCategory(String category);
	public Optional<Product> getByProductsId(String id);
}
