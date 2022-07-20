package com.example.hunters;

import java.io.IOException;
import java.util.List;

import com.example.model.Product;

public interface ItemHunter {

    Product hunt(String url) throws IOException;

    List<Product> hunt(String... urls) throws IOException;

}