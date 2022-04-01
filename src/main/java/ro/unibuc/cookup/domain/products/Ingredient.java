package ro.unibuc.cookup.domain.products;

import java.util.Date;

public class Ingredient extends Product{
    private enum StorageType {
        FRIDGE, FREEZER, WARMER
    }
    private StorageType storageType;
    private Date expirationDate;
    private String countryOfOrigin;

    public Ingredient(String name, String description, float price, Date manufacturingDate, String brand, String storageType, Date expirationDate, String countryOfOrigin) {
        super(name, description, price, manufacturingDate, brand);
        this.storageType = StorageType.valueOf(storageType);
        this.expirationDate = expirationDate;
        this.countryOfOrigin = countryOfOrigin;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public void setStorageType(StorageType storageType) {
        this.storageType = storageType;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }
}
