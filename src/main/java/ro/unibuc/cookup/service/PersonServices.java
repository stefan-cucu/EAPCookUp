package ro.unibuc.cookup.service;

import com.mysql.cj.jdbc.exceptions.SQLError;
import ro.unibuc.cookup.domain.persons.Courier;
import ro.unibuc.cookup.domain.persons.PaymentCard;
import ro.unibuc.cookup.domain.persons.Person;
import ro.unibuc.cookup.domain.persons.User;
import ro.unibuc.cookup.persistence.CourierRepository;
import ro.unibuc.cookup.persistence.UserRepository;
import ro.unibuc.cookup.service.csv.CourierCSV;
import ro.unibuc.cookup.service.csv.UserCSV;
import ro.unibuc.cookup.service.db.CourierDB;
import ro.unibuc.cookup.service.db.DatabaseConnection;
import ro.unibuc.cookup.service.db.UserDB;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class PersonServices {
    private final UserRepository userRepository = new UserRepository();
    private final CourierRepository courierRepository = new CourierRepository();

    private final CourierCSV courierCSV = CourierCSV.getInstance();
    private final UserCSV userCSVService = UserCSV.getInstance();

    private final CourierDB courierDB;
    private final UserDB userDB;

    private final String mailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private final String USER_CSV_PATH = "./csv/users.csv";
    private final String COURIERS_CSV_PATH = "./csv/couriers.csv";

    public PersonServices(DatabaseConnection connection) throws SQLException, ClassNotFoundException {
        this.courierDB = new CourierDB(connection);
        this.userDB = new UserDB(connection);

        try {
            for(User user : userDB.load())
                userRepository.add(user);
            for(Courier courier : courierDB.load())
                courierRepository.add(courier);
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
            throw new Error("Database unreachable");
        }

    }

    public int createUser(String firstName, String lastName, String email, String phone, String address, String password) {
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
        try {
            userDB.add(user);
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
            System.out.println(exception.getMessage());
            System.out.println(exception.getSQLState());
        }
        userRepository.add(user);
        return user.getPersonId();
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
        try {
            courierDB.add(courier);
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
            System.out.println(exception.getSQLState());
        }
        courierRepository.add(courier);
    }

    public void addCardToUser(int id, String number, String name, String expiryDate, int cvv){
        if(number == null || name == null || expiryDate == null){
            throw new IllegalArgumentException("All fields must be filled");
        }
        if(number.trim().isEmpty() || name.trim().isEmpty() || expiryDate.trim().isEmpty()){
            throw new IllegalArgumentException("All fields must be filled");
        }
        User user = userRepository.findById(id);
        user.setPaymentCard(new PaymentCard(number, name, expiryDate, cvv));
        try {
            userDB.update(id, user);
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
            System.out.println(exception.getMessage());
            System.out.println(exception.getSQLState());
        }
    }

    public void updateCourier(int id, String firstName, String lastName, String email, String phone, String address, float salary, String nationality, Date employmentDate){
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

        Courier courier = new Courier(id, firstName, lastName, email, phone, address, salary, nationality, employmentDate);
        try {
            courierDB.update(id, courier);
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
            System.out.println(exception.getSQLState());
        }
        courierRepository.update(courierRepository.findById(id), courier);
    }

    public void updateUser(int id, String firstName, String lastName, String email, String phone, String address, String password) {
        if(firstName == null || lastName == null || email == null || phone == null || address == null || password == null) {
            throw new IllegalArgumentException("All fields must be filled");
        }
        if(firstName.trim().isEmpty() || lastName.trim().isEmpty() || email.trim().isEmpty() || phone.trim().isEmpty() || address.trim().isEmpty() || password.trim().isEmpty()) {
            throw new IllegalArgumentException("All fields must be filled");
        }
        if (!email.matches(mailRegex)) {
            throw new IllegalArgumentException("Invalid email");
        }
        User user = new User(id, firstName, lastName, email, phone, address, password);
        try {
            userDB.update(id, user);
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
            System.out.println(exception.getMessage());
            System.out.println(exception.getSQLState());
        }
        userRepository.update(userRepository.findById(id), user);
    }

    public void deleteCourier(int id) {
        try {
            courierDB.delete(id);
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
            System.out.println(exception.getSQLState());
        }
        courierRepository.delete(courierRepository.findById(id));
    }

    public void deleteUser(int id) {
        try {
            userDB.delete(id);
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
            System.out.println(exception.getSQLState());
        }
        userRepository.delete(userRepository.findById(id));
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
