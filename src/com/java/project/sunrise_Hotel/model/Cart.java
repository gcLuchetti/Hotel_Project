package com.java.project.sunrise_Hotel.model;

import java.util.LinkedList;
import java.util.List;

public class Cart {
	private long id;
	private List<MenuItem> menuItem = new LinkedList<>();
	private double total;

	public Cart(List<MenuItem> menuItem, double total) {
		super();
		this.menuItem = menuItem;
		this.total = total;
	}

	public Cart() {}

	public long getId() {
		return id;
	}
	public List<MenuItem> getMenuItem() {
		return menuItem;
	}
	public void setMenuItem(List<MenuItem> menuItem) {
		this.menuItem = menuItem;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
}
