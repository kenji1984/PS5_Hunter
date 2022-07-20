package com.example.hunters;

public class ItemHunterFactory {

    public static ListItemHunter createListItemHunter(String domain) throws ClassNotFoundException {
	String domainName = getDomain(domain);
	if (domainName.equals("bestbuy")) {
	    return new BestBuyListItemHunter();
	}
	throw new ClassNotFoundException("List Item Hunter - No implementation available for " + domainName);
    }

    private static String getDomain(String domain) {
	String[] domainParts = domain.split("\\.");
	String domainName = domainParts[0];
	if (domainParts.length == 3) {
	    domainName = domainParts[1];
	}
	return domainName.toLowerCase();
    }

    public static ItemHunter createItemHunter(String domain) {
	// insert logic for different implementation based on domain here...
	return new BasicSingleItemHunter();
    }
}
