package ro.unibuc.cookup.domain.products;

import java.util.Date;
import java.util.Objects;

public abstract class Product implements Comparable<Product> {
    // Static id counter
    private static int _id = 1000;
    protected final int productId;

    // Product fields
    protected String name;
    protected String description;
    protected String brand;
    protected float price;
    protected Date manufacturingDate;

    // Constructor
    public Product(String name, String description, float price, Date manufacturingDate, String brand) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.manufacturingDate = manufacturingDate;
        this.brand = brand;
        this.productId = ++_id;
    }

    public Product(int productId, String name, String description, String brand, float price, Date manufacturingDate) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.price = price;
        this.manufacturingDate = manufacturingDate;

        if(productId > _id)
            _id = productId;
    }

    // Getters and setters
    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(Date manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    // Object methods implementation
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getProductId() == product.getProductId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId());
    }

    @Override
    public int compareTo(Product o) {
        return this.getName().compareTo(o.getName());
    }
}
