package ro.unibuc.cookup.domain.products;

import java.text.SimpleDateFormat;
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

    @Override
    public String toString() {
        // Write to CSV format
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s",
                getName(),
                getDescription(),
                getPrice(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getManufacturingDate()),
                getBrand(),
                getMaterial(),
                getCategory(),
                getProfit());
    }
}
