package com.eshop;

public class Product {
	
	private String title;
	private String price;
	private String itemLink;
	
	public Product(String title, String price, String itemLink) {
		setTitle(title);
		setPrice(price);
		setItemLink(itemLink);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getItemLink() {
		return itemLink;
	}

	public void setItemLink(String itemLink) {
		this.itemLink = itemLink;
	}
	
}
