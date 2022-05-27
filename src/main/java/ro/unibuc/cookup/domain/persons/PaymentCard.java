package ro.unibuc.cookup.domain.persons;

import java.util.Objects;

public class PaymentCard {
    // PaymentCard fields
    private String number;
    private String name;
    private String expiryDate;
    private int cvv;

    // Constructor
    public PaymentCard(String number, String name, String expiryDate, int cvv) {
        this.number = number;
        this.name = name;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    // Getters and setters
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    // Object methods implementation
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentCard)) return false;
        PaymentCard that = (PaymentCard) o;
        return getNumber().equals(that.getNumber()) && getName().equals(that.getName()) && getExpiryDate().equals(that.getExpiryDate()) && (getCvv() == that.getCvv());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getName(), getExpiryDate(), getCvv());
    }
}
