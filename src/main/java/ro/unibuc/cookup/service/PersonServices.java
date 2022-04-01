package ro.unibuc.cookup.service;

import ro.unibuc.cookup.domain.persons.Courier;
import ro.unibuc.cookup.domain.persons.PaymentCard;
import ro.unibuc.cookup.domain.persons.User;
import ro.unibuc.cookup.persistence.CourierRepository;
import ro.unibuc.cookup.persistence.UserRepository;

import java.util.ArrayList;
import java.util.Date;

public class PersonServices {
    private final UserRepository userRepository = new UserRepository();
    private final CourierRepository courierRepository = new CourierRepository();

    private final String mailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    public void createUser(String firstName, String lastName, String email, String phone, String address, String password) {
        if(firstName == null || lastName == null || email == null || phone == null || address == null || password == null) {
            throw new IllegalArgumentException("All fields must be filled");
        }
        if(firstName.trim().isEmpty() || lastName.trim().isEmpty() || email.trim().isEmpty() || phone.trim().isEmpty() || address.trim().isEmpty() || password.trim().isEmpty()) {
            throw new IllegalArgumentException("All fields must be filled");
        }
        if (!email.matches(mailRegex)) {
            throw new IllegalArgumentException("Invalid email");
        }
        User user = new User(firstName, lastName, email, phone, address, password);
        userRepository.add(user);
    }

    public void createCourier(String firstName, String lastName, String email, String phone, String address, float salary, String nationality, Date employmentDate) {
        if(firstName == null || lastName == null || email == null || phone == null || address == null || nationality == null || employmentDate == null ) {
            throw new IllegalArgumentException("All fields must be filled");
        }
        if(firstName.trim().isEmpty() || lastName.trim().isEmpty() || email.trim().isEmpty() || phone.trim().isEmpty() || address.trim().isEmpty() || nationality.trim().isEmpty()) {
            throw new IllegalArgumentException("All fields must be filled");
        }
        if (!email.matches(mailRegex)) {
            throw new IllegalArgumentException("Invalid email");
        }
        if(salary <= 0){
            throw new IllegalArgumentException("Salary must be positive");
        }
        Courier courier = new Courier(firstName, lastName, email, phone, address, salary, nationality, employmentDate);
        courierRepository.add(courier);
    }

    public void addCardToUser(int id, String number, String name, String expiryDate, String cvv){
        if(number == null || name == null || expiryDate == null || cvv == null){
            throw new IllegalArgumentException("All fields must be filled");
        }
        if(number.trim().isEmpty() || name.trim().isEmpty() || expiryDate.trim().isEmpty() || cvv.trim().isEmpty()){
            throw new IllegalArgumentException("All fields must be filled");
        }
        if(id < 0 || id >= userRepository.getSize()){
            throw new IllegalArgumentException("Invalid id");
        }
        User user = userRepository.get(id);
        user.setPaymentCard(new PaymentCard(number, name, expiryDate, cvv));
    }

    public Courier getCourier(int id) {
        return courierRepository.findById(id);
    }

    public User getUser(int id) {
        return userRepository.findById(id);
    }

    public Courier getCourierByIndex(int index) {
        return courierRepository.get(index);
    }

    public User getUserByIndex(int index) {
        return userRepository.get(index);
    }

    public int getUsersSize() {
        return userRepository.getSize();
    }

    public int getCouriersSize() {
        return courierRepository.getSize();
    }

    public Courier getAvailableCourier() {
        Courier courier = courierRepository.getAvailableCourier();
        if(courier == null) {
            throw new IllegalArgumentException("No courier available");
        }
        courier.setStatus(Courier.CourierStatus.BUSY);
        return courier;
    }

    public void setCourierAvailable(int id) {
        Courier courier = courierRepository.findById(id);
        courier.setStatus(Courier.CourierStatus.AVAILABLE);
    }

    public boolean checkUserExists(int id) {
        return userRepository.userExists(id);
    }

    public ArrayList<Courier> getCouriers() {
        return courierRepository.getAll();
    }

    public ArrayList<User> getUsers() {
        return userRepository.getAll();
    }
}
