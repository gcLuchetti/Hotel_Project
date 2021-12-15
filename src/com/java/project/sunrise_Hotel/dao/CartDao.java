package com.java.project.sunrise_Hotel.dao;

import java.util.List;

import com.java.project.sunrise_Hotel.model.MenuItem;

public interface CartDao {
	public void addCartItem(long userId, long menuItemId);
	public List<MenuItem> getAllCartItems(long userId);
	public void removeCartItem(long userId, long menuItemId);
}
