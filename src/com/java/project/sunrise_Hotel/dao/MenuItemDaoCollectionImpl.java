package com.java.project.sunrise_Hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.java.project.sunrise_Hotel.model.MenuItem;
import com.java.project.sunrise_Hotel.util.DateUtil;

public class MenuItemDaoCollectionImpl implements MenuItemDao{
	private List<MenuItem> menuItemList = new LinkedList<>();

	public boolean Create(MenuItem menuItem) {
		Connection con = ConnectionHandler.getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement("INSERT INTO MenuItem(name, price, isActive, dateOfLaunch, category, isFreeDelivery) VALUES(?, ?, ?, ?, ?, ?);");

			stmt.setString(1, menuItem.getName());
			stmt.setFloat(2, menuItem.getPrice());
			stmt.setBoolean(3, menuItem.isActive());
			stmt.setDate(4, new java.sql.Date(menuItem.getDateOfLaunch().getTime()));
			stmt.setString(5, menuItem.getCategory());
			stmt.setBoolean(6, menuItem.isFreeDelivery());

			return stmt.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Error! Create" + e.getMessage());
			return false;
		}finally {
			ConnectionHandler.closeConnection(con, stmt);
		}
	}

	public boolean Update(int i, MenuItem menuItem) {
		Connection con = ConnectionHandler.getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement("UPDATE MenuItem SET name = ?, price = ?, isActive = ?, dateOfLaunch = ?, category = ?, isFreeDelivery = ? WHERE id = ?;");

			stmt.setString(1, menuItem.getName());
			stmt.setFloat(2, menuItem.getPrice());
			stmt.setBoolean(3, menuItem.isActive());
			stmt.setDate(4, new java.sql.Date(menuItem.getDateOfLaunch().getTime()));
			stmt.setString(5, menuItem.getCategory());
			stmt.setBoolean(6, menuItem.isFreeDelivery());
			stmt.setInt(7, (int) menuItemList.get(i).getId());

			return stmt.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Error! Update" + e.getMessage());
			return false;
		}finally {
			ConnectionHandler.closeConnection(con, stmt);
		}
	}

	@Override
	public List<MenuItem> getMenuItemListAdmin() {
		Connection con = ConnectionHandler.getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement("SELECT * FROM MenuItem;");

			ResultSet resultSQL = stmt.executeQuery();

			while(resultSQL.next()) {
				MenuItem menuItem = new MenuItem(resultSQL.getInt("id"), resultSQL.getString("name"), resultSQL.getFloat("price"), resultSQL.getBoolean("isActive"), resultSQL.getDate("dateOfLaunch"), resultSQL.getString("category"), resultSQL.getBoolean("isFreeDelivery"));
				menuItemList.add(menuItem);
			}

			return menuItemList;
		} catch (Exception e) {
			System.err.println("Error! getMenuItemListAdmin" + e.getMessage());
			return null;
		}finally {
			ConnectionHandler.closeConnection(con, stmt);
		}
	}

	@Override
	public List<MenuItem> getMenuItemListCustomer() {
		Date d = new Date();
		menuItemList = getMenuItemListAdmin();

		if(menuItemList.isEmpty())
			return menuItemList;

		List<MenuItem> newMenuItemList = new LinkedList<>();
		for (MenuItem menuItem : menuItemList) {
			if((DateUtil.compareDate(menuItem.getDateOfLaunch()) || menuItem.getDateOfLaunch().before(d)) && menuItem.isActive()) {
				newMenuItemList.add(menuItem);
			}
		}
		return newMenuItemList;
	}

	@Override
	public void modifyMenuItem(MenuItem menuItem) {
		menuItemList = getMenuItemListAdmin();
		int sizeMenuItem = menuItemList.size();
		boolean verify = false;
		for(int i = 0; i < sizeMenuItem; i++) {
			if(menuItemList.get(i).equals(menuItem)) {
				verify = Update(i, menuItem);
			}
		}
		if(verify)
			System.out.println("\n\t\tItem finded and updated");
	}

	@Override
	public MenuItem getMenuItem(long menuItemId) {
		menuItemList = getMenuItemListAdmin();
		for (MenuItem menuItem : menuItemList) {
			if(menuItem.getId() == menuItemId)
				return menuItem;
		}
		return null;
	}

}
