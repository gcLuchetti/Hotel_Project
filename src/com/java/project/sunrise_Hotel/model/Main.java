package com.java.project.sunrise_Hotel.model;

import java.util.HashMap;
import java.util.Scanner;

import com.java.project.sunrise_Hotel.dao.CartDaoCollectionImpl;
import com.java.project.sunrise_Hotel.dao.MenuItemDaoCollectionImpl;
import com.java.project.sunrise_Hotel.util.DateUtil;

public class Main {

	public static void showOptions() {
		Scanner scanner = new Scanner(System.in);

		try {
			int op;
			do {
				do {
					System.out.println("1 - Admin list\n2 - Costumer list\n3 - Cart list\n0 - Leave\n\n:::");
					op = scanner.nextInt();
				}while(op < 0 && op > 3);

				switch (op) {
				case 1: {
					showAdminList();
					creatingMenuItem();
					break;
				}
				case 2: {
					showCostumerList();
					break;
				}
				case 3: {
					showCartMenuItem();
					CartItem();
					break;
				}
				}
			}while(op != 0);
		} catch (Exception e) {
			System.err.println("Error! " + e.getMessage() + "\n\n\n" + e.getLocalizedMessage() + "\n\n\n" + e.getStackTrace());
		}
		scanner.close();
	}

	public static void showAdminList() {
		System.out.println(new MenuItemDaoCollectionImpl().getMenuItemListAdmin());
	}

	public static void creatingMenuItem() {
		Scanner scanner = new Scanner(System.in);
		try {
			String op;
			System.out.println("Wanna create some item? Yes/No\n:::");
			op = scanner.nextLine();
			System.out.println(op);
			op = op.toUpperCase();
			switch (op) {
			case "YES":
				MenuItem menuItem = new MenuItem();

				System.out.println("Insert the menu item name: ");
				String name = scanner.nextLine();
				menuItem.setName(name);

				System.out.println("Insert the menu item price: EX: 12.1");
				float price = Float.parseFloat(scanner.nextLine());
				menuItem.setPrice(price);

				System.out.println("The menu item is active? Y(es)/N(o) :::");
				String active = scanner.nextLine();
				if(active.toUpperCase().equals("Y"))
					menuItem.setActive(true);
				else
					menuItem.setActive(false);

				System.out.println("Date of the launch: format(dd/MM/yyyy)");
				String date = scanner.nextLine();
				menuItem.setDateOfLaunch(DateUtil.convertToDate(date));

				System.out.println("Insert the menu item category: ");
				String category = scanner.nextLine();
				menuItem.setCategory(category);

				System.out.println("The menu item is free delivery? Y(es)/N(o) :::");
				String freeDelivery = scanner.nextLine();
				if(freeDelivery.toUpperCase().equals("Y"))
					menuItem.setFreeDelivery(true);
				else
					menuItem.setFreeDelivery(false);

				System.out.println("\n\tMenu Item filled");
				new MenuItemDaoCollectionImpl().Create(menuItem);
				System.out.println("\tMenu Item inserted");

				break;
			case "NO":
				updatingMenuItem();
				break;
			default:
				creatingMenuItem();
				break;
			}
		} catch (Exception e) {
			System.err.println("Error! " + e.getMessage());
		}
	}

	public static void updatingMenuItem() {
		Scanner scanner = new Scanner(System.in);
		try {
			String op;
			System.out.println("Wanna update some item? YES/NO\n:::");
			op = scanner.nextLine();
			op = op.toUpperCase();
			switch (op) {
			case "YES":
				System.out.println("Insert the menu item id that you want to update:\n\t:::");
				int i = Integer.parseInt(scanner.nextLine());
				MenuItem menuItem = new MenuItemDaoCollectionImpl().getMenuItem(i);

				System.out.println(menuItem.toString());

				do {
					do {
						System.out.println("What you wanna to change?\n1 - Name\n2 - Price\n3 - Active\n4 - Date of launch\n5 - Category\n6 - Free delivery\n\n0 - Leave");
						i = Integer.parseInt(scanner.nextLine());
					}while(i < 0 && i > 6);

					switch (i) {
					case 1:
						System.out.println("\n\t" + menuItem.getName());
						System.out.println("\nInsert the menu item name: ");
						String name = scanner.nextLine();
						menuItem.setName(name);
						break;

					case 2:
						System.out.println("\n\t" + menuItem.getPrice());
						System.out.println("Insert the menu item price: EX: 12.1");
						float price = Float.parseFloat(scanner.nextLine());
						menuItem.setPrice(price);

						break;

					case 3:
						menuItem.setActive(!menuItem.isActive());

						break;

					case 4:
						System.out.println("\n\t" + menuItem.getDateOfLaunch());
						System.out.println("Date of the launch: format(dd/MM/yyyy)");
						String date = scanner.nextLine();
						menuItem.setDateOfLaunch(DateUtil.convertToDate(date));
						break;

					case 5:
						System.out.println("\n\t" + menuItem.getCategory());
						System.out.println("Insert the menu item category: ");
						String category = scanner.nextLine();
						menuItem.setCategory(category);
						break;

					case 6:
						menuItem.setFreeDelivery(!menuItem.isFreeDelivery());

						break;

					default:
						break;
					}
				}while(i != 0);

				new MenuItemDaoCollectionImpl().modifyMenuItem(menuItem);

				break;
			case "NO":
				break;
			default:
				updatingMenuItem();
				break;
			}
		} catch (Exception e) {
			System.err.println("Error! " + e.getMessage());
		}
	}

	public static void showCostumerList() {
		System.out.println(new MenuItemDaoCollectionImpl().getMenuItemListCustomer());
	}

	public static void showCartMenuItem() {
		CartDaoCollectionImpl cart = new CartDaoCollectionImpl();
		cart.getData();
		HashMap<Long, Cart> data = new HashMap<>();
		data = cart.getUserCarts();
		for (Long i : data.keySet()) {
			System.out.println("\n\nCart " + i);
			System.out.print("\t" + data.get(i).getMenuItem());
			System.out.print("Cart total price: " + data.get(i).getTotal());
		}
	}

	public static void CartItem() {
		Scanner scanner = new Scanner(System.in);
		try {
			int op;
			do {
				CartDaoCollectionImpl cart = new CartDaoCollectionImpl();
				System.out.println("\n\n1 - Insert a new item: \n2 - Create a new cart: \n3 - Remove cart item\n\n\n0 - Leave:::");
				op  = scanner.nextInt();
				switch (op) {
				case 1:
					System.out.println("\n\nInsert one Menu Item id:");
					showAdminList();
					System.out.println("\n:::");
					int menuItemId = scanner.nextInt();
					System.out.println("\n\nNow insert the Cart id:\n\n:::");
					int cartId = scanner.nextInt();
					cart.addCartItem(cartId, menuItemId);
					break;
				case 2:
					System.out.println("\n\nInsert the Menu Item id that you want to add:");
					showAdminList();
					System.out.println("\n:::");
					menuItemId = scanner.nextInt();
					cart.addCartItem(-3, menuItemId);
					break;
				case 3:
					System.out.println("\n\nInsert the Menu Item id to remove:");
					showAdminList();
					System.out.println("\n:::");
					menuItemId = scanner.nextInt();
					System.out.println("\n\nNow insert the Cart id:\n\n:::");
					showCartMenuItem();
					int cartIdRem = scanner.nextInt();
					cart.removeCartItem(cartIdRem, menuItemId);
					break;
				}
			}while(op != 0);
		} catch (Exception e) {
			System.err.println("Error CartItem" + e.getMessage());
		}
	}

	public static void main(String[] args) {
		showOptions();
	}
}
