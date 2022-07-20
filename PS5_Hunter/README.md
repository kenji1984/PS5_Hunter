Parse URLs to see if the page contains 'Add to Cart' button.

Added generic implementation to parse product URL page for "Add to Cart" button.
Added implementation implementation to parse URL containing a list of products.

Implementation is using Jsoup, which does not work with dom elements that are loaded by Javascript. This includes a lot of the BestBuy URLs.
