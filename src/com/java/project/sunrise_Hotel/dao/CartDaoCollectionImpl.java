package com.java.project.sunrise_Hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.java.project.sunrise_Hotel.customExceptions.CartEmptyException;
import com.java.project.sunrise_Hotel.model.Cart;
import com.java.project.sunrise_Hotel.model.MenuItem;

public class CartDaoCollectionImpl implements CartDao{

	private static HashMap<Long, Cart> userCarts = new HashMap<>();

	public HashMap<Long, Cart> getUserCarts() {
		return userCarts;
	}

	public void getData() {
		userCarts = new HashMap<>();

		Connection con = ConnectionHandler.getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement("SELECT * FROM CartMenuItem;");
			ResultSet resultSQL = stmt.executeQuery();

			List<Integer> listOfValues = new LinkedList<>();
			int i = 0;
			while(resultSQL.next()) {
				listOfValues.add(i, resultSQL.getInt("cartId"));
				i +=1;
			}
			Set<Integer> iVar = new HashSet<>(listOfValues);
			listOfValues.clear();
			listOfValues = new LinkedList<>(iVar);
			for (Integer integer : listOfValues) {
				stmt = con.prepareStatement("SELECT * FROM CartMenuItem WHERE cartId = ?;");
				stmt.setInt(1, integer);
				resultSQL = stmt.executeQuery();

				List<MenuItem> menuItemList = new LinkedList<>();
				MenuItemDaoCollectionImpl menuItem = new MenuItemDaoCollectionImpl();
				float price = 0;
				while(resultSQL.next()) {
					MenuItem m = menuItem.getMenuItem(resultSQL.getInt("menuItemId"));
					menuItemList.add(m);
					price += m.getPrice();
				}

				userCarts.put((long) integer, new Cart(menuItemList, price));
				price = 0;
			}
		} catch (Exception e) {
			System.err.println("Error! getData" + e.getMessage());
		}finally {
			ConnectionHandler.closeConnection(con, stmt);
		}
	}

	@Override
	public void addCartItem(long userId, long menuItemId) {
		MenuItemDaoCollectionImpl menuItemDao = new MenuItemDaoCollectionImpl();
        MenuItem menuItem = menuItemDao.getMenuItem(menuItemId);

        if(menuItem == null)
        	return;

		Connection con = ConnectionHandler.getConnection();
		PreparedStatement stmt = null;

		try {
			Cart cart = null;
			getData();

	        if (userCarts.containsKey(userId)) {
	            cart = userCarts.get(userId);
	        } else {
				stmt = con.prepareStatement("INSERT INTO Cart(total) VALUES(?);");
				stmt.setDouble(1, 0);
				stmt.executeUpdate();
	        }

	        if(cart != null) {

	        	stmt = con.prepareStatement("INSERT INTO CartMenuItem(cartId, menuItemId) VALUES(?, ?);");
				stmt.setInt(1, (int) userId);
				stmt.setInt(2, (int) menuItemId);
				stmt.executeUpdate();

				return;
	        }else {

	        	stmt = con.prepareStatement("SELECT * FROM Cart WHERE id NOT IN(SELECT cartId FROM CartMenuItem);");

		        ResultSet resultSQL = stmt.executeQuery();
		        resultSQL.next();

		        userId = resultSQL.getInt("id");
		        stmt = con.prepareStatement("INSERT INTO CartMenuItem(cartId, menuItemId) VALUES(?, ?);");
				stmt.setInt(1, (int) userId);
				stmt.setInt(2, (int) menuItemId);
				stmt.executeUpdate();
	        }

			getData();

		} catch (SQLException e) {
			System.err.println("Error! addCartItem" + e.getMessage());
		}finally {
			ConnectionHandler.closeConnection(con, stmt);
		}
	}

	@Override
	public List<MenuItem> getAllCartItems(long userId) {
		Connection con = ConnectionHandler.getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement("SELECT * FROM MenuItem WHERE Id IN(SELECT menuItemId FROM CartMenuItem WHERE cartId = ?);");

			stmt.setInt(1, (int) userId);

			ResultSet resultSQL = stmt.executeQuery();

			List<MenuItem> menuItemList = new LinkedList<>();
			double priceSum = 0;
			while(resultSQL.next()) {
				MenuItem menuItem = new MenuItem(resultSQL.getInt("id"), resultSQL.getString("name"), resultSQL.getFloat("price"), resultSQL.getBoolean("isActive"), resultSQL.getDate("dateOfLaunch"), resultSQL.getString("category"), resultSQL.getBoolean("isFreeDelivery"));
				menuItemList.add(menuItem);
				priceSum += menuItem.getPrice();
			}

			if(menuItemList.isEmpty())
				throw new CartEmptyException("The cart is empty!");

			updateTotalPriceCart(userId, priceSum);

			return menuItemList;
		} catch (Exception e){
			System.err.println("Error! getAllCartItems" + e.getMessage());
			return new LinkedList<>();
		}finally {
			ConnectionHandler.closeConnection(con, stmt);
		}
	}

	@Override
	public void removeCartItem(long userId, long menuItemId) {
		Connection con = ConnectionHandler.getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement("DELETE FROM CartMenuItem WHERE cartId = ? AND menuItemId = ?;");
			stmt.setInt(1, (int) userId);
			stmt.setInt(2, (int) menuItemId);

			if(stmt.executeUpdate() > 0)
				getAllCartItems(userId);
			else
				System.err.println("\n\tThat cart or that menu item wasn't linked.");
		} catch (Exception e) {
			System.err.println("Error! removeCartItem" + e.getMessage());
			e.getStackTrace();
		}finally {
			ConnectionHandler.closeConnection(con, stmt);
		}

	}

	public void updateTotalPriceCart(long userId, double total) {
		Connection con = ConnectionHandler.getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement("UPDATE Cart SET total = ? WHERE id = ?;");

			stmt.setDouble(1, total);
			stmt.setInt(2, (int) userId);

			System.out.println(stmt.executeUpdate() > 0 ? "\n\tTotal price updated" : "\n\tPrice not updated");
		} catch (Exception e) {
			System.err.println("Error! updateTotalPriceCart" + e.getMessage());
			e.getStackTrace();
		}finally {
			ConnectionHandler.closeConnection(con, stmt);
		}
	}

}
