package ro.unibuc.cookup.domain;

import ro.unibuc.cookup.domain.persons.Courier;
import ro.unibuc.cookup.domain.persons.User;
import ro.unibuc.cookup.domain.products.Product;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class Delivery {
    // Delivery enum
    private enum DeliveryStatus {
        IN_PROGRESS,
        DELIVERED,
        CANCELLED
    }

    // Static id counter
    private static int _id = 1000;
    private final int deliveryId;

    // Delivery fields
    private final User user;
    private Courier courier;
    private Date date;
    private Supermarket supermarket;
    private HashMap<Product, Integer> products;
    private DeliveryStatus deliveryStatus;

    // Constructor
    public Delivery(User user, Courier courier, Date date, String deliveryStatus, HashMap<Product, Integer> products, Supermarket supermarket) {
        this.user = user;
        this.courier = courier;
        this.date = date;
        this.deliveryStatus = DeliveryStatus.valueOf(deliveryStatus);
        this.products = products;
        this.supermarket = supermarket;
        this.deliveryId = ++_id;
    }

    // Getters and setters
    public int getDeliveryId() {
        return deliveryId;
    }

    public User getUser() {
        return user;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Supermarket getSupermarket() {
        return supermarket;
    }

    public void setSupermarket(Supermarket supermarket) {
        this.supermarket = supermarket;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = DeliveryStatus.valueOf(deliveryStatus);
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<Product, Integer> products) {
        this.products = products;
    }
}
