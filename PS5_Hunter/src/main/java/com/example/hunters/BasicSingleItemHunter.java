package com.example.hunters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.model.Product;

/**
 * Simple implemention to check whether the URL given contains any Add To Cart button. The URL must point
 * to a product page, and not a list page.
 */
public class BasicSingleItemHunter implements ItemHunter {

    @Override
    public Product hunt(String url) throws IOException {
	Product product = new Product();
	product.setUrl(url);

	Document doc = Jsoup.connect(url).get();

	Elements buttons = doc.getElementsByTag("button");
	for (Element button : buttons) {
	    if (button.text().equalsIgnoreCase("ADD TO CART")) {
		product.setInstock(true);
		
		return product;
	    }
	}
	return null;
    }

    @Override
    public List<Product> hunt(String... urls) throws IOException {
	List<Product> products = new ArrayList<>();
	for (String url : urls) {
	    products.add(hunt(url));
	}
	return products;
    }

}
