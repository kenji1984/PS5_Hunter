package com.example.hunters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.model.BestBuyProduct;
import com.example.model.Product;

/**
 * Most shop site search result are very bad. However, BestBuy list item is very
 * good (for PS5 at least). The list items only contains PS5 console and console
 * bundles, nothing else.
 */
public class BestBuyListItemHunter implements ListItemHunter {

    private int minPrice;
    private int maxPrice;

    public ListItemHunter priceRange(int min, int max) {
	this.minPrice = min;
	this.maxPrice = max;
	return this;
    }

    private boolean isWithinPriceRange(int price) {
	if (maxPrice == 0) {
	    return true;
	}
	return price >= minPrice && price <= maxPrice;
    }

    /**
     * @return available products from all the urls within the price range.
     */
    @Override
    public List<Product> hunt(String... urls) throws IOException {
	List<Product> products = new ArrayList<>();
	for (String url : urls) {
	    products.addAll(hunt(url));
	}
	return products;
    }

    /**
     * @return only available products on this page within the price range.
     */
    @Override
    public List<Product> hunt(String url) throws IOException {
	List<Product> products = listProducts(url);
	return products.stream().filter(Product::isInstock).collect(Collectors.toList());
    }

    /**
     * @returns all products on this page within the price range.
     */
    @Override
    public List<Product> listProducts(String url) throws IOException {
	List<Product> products = new ArrayList<>();

	Document doc = Jsoup.connect(url).get();
	Elements elements = doc.getElementsByClass("list-item");
	for (Element element : elements) {
	    BestBuyProduct product = new BestBuyProduct();

	    Elements priceBlock = element.getElementsByClass("pricing-price");
	    if (priceBlock.isEmpty()) {
		continue;
	    }
	    Element price = priceBlock.get(0);
	    product.setPrice(price.text());
	    if (!isWithinPriceRange(product.getPrice())) {
		continue;
	    }

	    Element information = element.getElementsByClass("information").get(0);
	    Element anchor = information.getElementsByTag("a").get(0);
	    product.setName(anchor.text());
	    product.setUrl(anchor.absUrl("href"));

	    Elements buttons = element.getElementsByTag("button");
	    for (Element button : buttons) {
		if (button.text().equalsIgnoreCase("ADD TO CART")) {
		    product.setInstock(true);
		    break;
		}
	    }
	    products.add(product);
	}
	return products;
    }

}
