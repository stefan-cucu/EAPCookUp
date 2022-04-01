package ro.unibuc.cookup.domain;

import ro.unibuc.cookup.domain.products.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class Supermarket {
    // Static id counter
    private static int _id = 1000;
    private final int supermarketId;

    // Supermarket fields
    private String name;
    private String address;
    private HashMap<Product, Integer> products;

    // Constructors
    public Supermarket(String name, String address) {
        this.name = name;
        this.address = address;
        this.supermarketId = ++_id;
        products = new HashMap<Product, Integer>();
    }

    public Supermarket(String name, String address, HashMap<Product, Integer> products) {
        this.name = name;
        this.address = address;
        this.supermarketId = ++_id;
        this.products = products;
    }

    // Getters and setters
    public int getSupermarketId() {
        return supermarketId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    // Product methods
    public void setProducts(HashMap<Product, Integer> products) {
        this.products = products;
    }

    public void addProduct(Product product, int quantity) {
        products.put(product, quantity);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public void removeProduct(Product product, int quantity) {
        // subtract quantity from the current quantity
        int currentQuantity = products.get(product);
        if(currentQuantity - quantity < 0) {
            throw new IllegalArgumentException("Not enough products in the supermarket");
        }
        products.put(product, currentQuantity - quantity);
    }

    public void clearProducts() {
        products.clear();
    }

    public Product getProduct(int index) {
        return products.keySet().toArray(new Product[products.size()])[index];
    }

    public int getProductQuantity(Product product) {
        return products.get(product);
    }

    public int getProductCount() {
        return products.size();
    }

    public boolean containsProduct(Product product, int quantity) {
        return products.containsKey(product) && products.get(product) >= quantity;
    }
}
