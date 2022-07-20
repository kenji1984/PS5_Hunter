package com.example.hunters;

import java.io.IOException;
import java.util.List;

import com.example.model.Product;

public interface ListItemHunter {

    /**
     * @return available products from all the urls within the price range.
     */
    List<Product> hunt(String... urls) throws IOException;

    /**
     * @return only available products on this page within the price range.
     */
    List<Product> hunt(String url) throws IOException;

    /**
     * @returns all products on this page within the price range.
     */
    List<Product> listProducts(String url) throws IOException;

}