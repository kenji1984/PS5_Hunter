package com.example.model;

public class Product {

    private String name;
    private String url;
    private int price;
    private boolean instock;

    public String getName() {
	return name;
    }

    public String getUrl() {
	return url;
    }

    public int getPrice() {
	return price;
    }

    public boolean isInstock() {
	return instock;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public void setPrice(int price) {
	this.price = price;
    }

    public void setInstock(boolean instock) {
	this.instock = instock;
    }

    public String toString() {
	return "<a href='" + getUrl() + "'>" + getName() + "</a><br>" + getPrice() + "<br>"
		+ (isInstock() ? "Available" : "Out of stock");
    }
}
