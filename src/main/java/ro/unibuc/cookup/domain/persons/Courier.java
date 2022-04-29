package ro.unibuc.cookup.domain.persons;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Courier extends Person{
    // CourierStatus enum
    public enum CourierStatus {
        AVAILABLE,
        BUSY,
    }
    // Courier fields
    private float salary;
    private String nationality;
    private Date employmentDate;
    private CourierStatus status;

    // Constructor
    public Courier(String firstName, String lastName, String email, String phone, String address, float salary, String nationality, Date employmentDate) {
        super(firstName, lastName, email, phone, address);
        this.salary = salary;
        this.nationality = nationality;
        this.employmentDate = employmentDate;
        this.status = CourierStatus.AVAILABLE;
    }

    // Getters and setters
    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public CourierStatus getStatus() {
        return status;
    }

    public void setStatus(CourierStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s\n",
                getFirstName(),
                getLastName(),
                getEmail(),
                getPhone(),
                getAddress(),
                getSalary(),
                getNationality(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getEmploymentDate()));
    }
}

