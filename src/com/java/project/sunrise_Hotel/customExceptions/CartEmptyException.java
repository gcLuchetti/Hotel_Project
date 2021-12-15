package com.java.project.sunrise_Hotel.customExceptions;

public class CartEmptyException extends Exception{

	private static final long serialVersionUID = 3221898639640444776L;

	public CartEmptyException() {
		super();
	}

	public CartEmptyException(String message) {
		super(message);
	}

}
