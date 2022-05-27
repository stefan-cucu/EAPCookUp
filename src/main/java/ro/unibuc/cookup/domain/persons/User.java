package ro.unibuc.cookup.domain.persons;

public class User extends Person {
    // User fields
    private String password;
    private PaymentCard paymentCard;

    // Constructor
    public User(String firstName, String lastName, String email, String phone, String address, String password, PaymentCard paymentCard) {
        super(firstName, lastName, email, phone, address);
        this.password = password;
        this.paymentCard = paymentCard;
    }

    public User(String firstName, String lastName, String email, String phone, String address, String password) {
        super(firstName, lastName, email, phone, address);
        this.password = password;
    }

    public User(int personId, String firstName, String lastName, String email, String phone, String address, String password) {
        super(personId, firstName, lastName, email, phone, address);
        this.password = password;
    }

    public User(int personId, String firstName, String lastName, String email, String phone, String address, String password, PaymentCard paymentCard) {
        super(personId, firstName, lastName, email, phone, address);
        this.password = password;
        this.paymentCard = paymentCard;
    }

    // Getters and setters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PaymentCard getPaymentCard() {
        return paymentCard;
    }

    public void setPaymentCard(PaymentCard paymentCard) {
        this.paymentCard = paymentCard;
    }

    @Override
    public String toString() {
        // Write in CSV format
        return String.format("%s,%s,%s,%s,%s,%s",
                getFirstName(), getLastName(), getEmail(), getPhone(), getAddress(), password);
    }
}
