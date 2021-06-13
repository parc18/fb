package com.fb.daoimpl;

import org.springframework.stereotype.Component;

import com.fb.dao.Order;
import com.fb.dto.CartDto;
@Component
public class OrderImpl implements Order{

	@Override
	public boolean addToCard(CartDto cartDto) {
		// TODO Auto-generated method stub
		return true;
	}

}
