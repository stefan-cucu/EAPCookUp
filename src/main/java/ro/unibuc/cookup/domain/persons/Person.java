package ro.unibuc.cookup.domain.persons;

import java.util.Objects;

public abstract class Person {
    // Static Id counter
    private static int _id = 1000;
    private final int personId;

    // Person fields
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;

    // Constructor
    public Person(String firstName, String lastName, String email, String phone, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.personId = ++_id;
    }

    // Getters and setters
    public int getPersonId() {
        return personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Object methods implementation
    @Override
    public String toString() {
        return String.format("%s %s", firstName, lastName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return getPersonId() == person.getPersonId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPersonId());
    }
}
