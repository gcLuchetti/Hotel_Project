package com.java.project.sunrise_Hotel.model;

import java.util.Date;
import java.util.Objects;

public class MenuItem {
	private long id;
	private String name;
	private float price;
	private boolean isActive;
	private Date dateOfLaunch;
	private String category;
	private boolean isFreeDelivery;


	public MenuItem(String name, float price, boolean isActive, Date dateOfLaunch, String category,
			boolean isFreeDelivery) {
		this.name = name;
		this.price = price;
		this.isActive = isActive;
		this.dateOfLaunch = dateOfLaunch;
		this.category = category;
		this.isFreeDelivery = isFreeDelivery;
	}

	public MenuItem(long id, String name, float price, boolean isActive, Date dateOfLaunch, String category,
			boolean isFreeDelivery) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.isActive = isActive;
		this.dateOfLaunch = dateOfLaunch;
		this.category = category;
		this.isFreeDelivery = isFreeDelivery;
	}

	public MenuItem() {}

	@Override
	public String toString() {
		return "---MenuItem [\n\tid=" + id + "\n\tname=" + name + "\n\tprice=" + price + "\n\tisActive=" + isActive
				+ "\n\tdateOfLaunch=" + dateOfLaunch + "\n\tcategory=" + category + "\n\tisFreeDelivery=" + isFreeDelivery
				+ "\n]\n\n";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		MenuItem other = (MenuItem) obj;
		return id == other.id;
	}


	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public Date getDateOfLaunch() {
		return dateOfLaunch;
	}
	public void setDateOfLaunch(Date dateOfLaunch) {
		this.dateOfLaunch = dateOfLaunch;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public boolean isFreeDelivery() {
		return isFreeDelivery;
	}
	public void setFreeDelivery(boolean isFreeDelivery) {
		this.isFreeDelivery = isFreeDelivery;
	}
}
