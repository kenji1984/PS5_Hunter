package com.example.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.hunters.ItemHunter;
import com.example.hunters.ItemHunterFactory;
import com.example.hunters.ListItemHunter;
import com.example.model.Product;

@Service
public class ShopItemChecker {

    @Value("${shop.productList.file.path}")
    private String productListFilePath;
    @Value("${shop.product.file.path}")
    private String productFilePath;

    @Autowired
    private EmailService emailService;

    @Scheduled(fixedRateString = "${spring.schedule.fixedRate.milliseconds}")
    public void check() {
	try {
	    List<Product> products = new ArrayList<>();
	    for (String listUrl : read(productListFilePath)) {
		System.out.println("Hunting: " + listUrl);
		ListItemHunter listItemHunter = ItemHunterFactory.createListItemHunter(getDomain(listUrl));
		products.addAll(listItemHunter.hunt(listUrl));
	    }
	    for (String itemUrl : read(productFilePath)) {
		System.out.println("Hunting: " + itemUrl);
		ItemHunter itemHunter = ItemHunterFactory.createItemHunter(getDomain(itemUrl));
		Product product = itemHunter.hunt(itemUrl);
		if (product != null) {
		    products.add(product);
		}
	    }
	    System.out.println("Found " + products.size() + " products in stock.");
	    products.stream().map(Product::getName).forEach(System.out::println);
	    
	    String content = toEmailContent(products);
	    if (!content.isEmpty()) {
		content = "<b><u>Product List</u></b>:<br><br>" + content;
		emailService.send(content);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    try {
		emailService.setSubject("PS5 Hunter has encountered a problem.");
		emailService.send("Unable to check shops. " + e.getMessage());
	    } catch (MessagingException e1) {
		e1.printStackTrace();
	    }
	}
	System.out.println("Searching completed: " + new Date());
    }

    private List<String> read(String filePath) throws IOException {
	List<String> urls = new ArrayList<>();
	try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	    String line;
	    while ((line = br.readLine()) != null) {
		if (!line.trim().isEmpty()) {
		    urls.add(line);
		}
	    }
	}
	return urls;
    }

    private String toEmailContent(List<Product> products) {
	StringBuilder content = new StringBuilder();
	for (Product product : products) {
	    content.append(product + "<br>");
	    content.append("<hr>");
	}
	return content.toString();
    }

    private String getDomain(String url) {
	int start = url.indexOf("https://") + "https://".length();
	int end = url.indexOf("/", start);
	return url.substring(start, end);
    }

}
