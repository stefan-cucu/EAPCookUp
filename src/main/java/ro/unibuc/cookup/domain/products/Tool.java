package ro.unibuc.cookup.domain.products;

import java.util.ArrayList;
import java.util.Date;

public class Tool extends Product implements Purchasable {
    // Tool fields
    private String material;
    private String category;
    private float profitRate;

    // Constructor
    public Tool(String name, String description, float price, Date manufacturingDate, String brand, String material, String category, float profitRate) {
        super(name, description, price, manufacturingDate, brand);
        this.material = material;
        this.category = category;
        this.profitRate = profitRate;
    }

    // Getters and setters
    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Purchasable methods
    @Override
    public float getProfit() {
        return getPrice() * profitRate;
    }
}
