package com.example.model;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class BestBuyProduct extends Product {

    private static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

    private String salesDesc;

    public void setPrice(String priceDesc) {
	int idx = 0;
	while (idx < priceDesc.length() && priceDesc.charAt(idx++) != '$');
	if (idx == priceDesc.length()) {
	    salesDesc = priceDesc;
	    return;
	}
	int startIdx = idx-1;
	while (!Character.isLetter(priceDesc.charAt(++idx)));
	try {
	    setPrice(currencyFormatter.parse(priceDesc.substring(startIdx, idx)).intValue());
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	salesDesc = priceDesc.substring(idx);
    }

    public String toString() {
	return "<a href='" + getUrl() + "'>" + getName() + "</a><br>" + currencyFormatter.format(getPrice()) + "<br>"
		+ this.salesDesc + "<br>" + (isInstock() ? "Available" : "Out of stock");
    }

}
