package com.fb.dao;

import com.fb.dto.CartDto;

public interface Order {
	public boolean addToCard(CartDto cartDto);
}
