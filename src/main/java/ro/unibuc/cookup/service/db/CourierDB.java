package ro.unibuc.cookup.service.db;

import ro.unibuc.cookup.domain.persons.Courier;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourierDB implements GenericDB<Courier> {
    private DatabaseConnection connection;

    public CourierDB(DatabaseConnection connection) {
        this.connection = connection;
    }

    @Override
    public ArrayList<Courier> load() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM couriers");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Courier> result = new ArrayList<>();

        while(resultSet.next()){
            Courier temp = new Courier(
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"),
                    resultSet.getString("phone"),
                    resultSet.getString("address"),
                    resultSet.getFloat("salary"),
                    resultSet.getString("nationality"),
                    resultSet.getDate("employment_date")
            );
            result.add(temp);
        }

        audit("load");
        return result;
    }

    @Override
    public void add(Courier content) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO couriers VALUES(?,?,?,?,?,?,?,?,?)");

        statement.setInt(1, content.getPersonId());
        statement.setString(2, content.getFirstName());
        statement.setString(3, content.getLastName());
        statement.setString(4, content.getEmail());
        statement.setString(5, content.getAddress());
        statement.setString(6, content.getPhone());
        statement.setDouble(7, content.getSalary());
        statement.setString(8, content.getNationality());
        statement.setDate(9, new Date(content.getEmploymentDate().getTime()));

        statement.execute();
        audit("add");
    }

    @Override
    public void update(int id, Courier content) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE couriers " +
                "SET id=?, first_name=?, last_name=?, email=?, address=?, phone=?,  salary=?, nationality=?, employment_date=?" +
                "WHERE id=?");

        statement.setInt(1, content.getPersonId());
        statement.setString(2, content.getFirstName());
        statement.setString(3, content.getLastName());
        statement.setString(4, content.getEmail());
        statement.setString(5, content.getAddress());
        statement.setString(6, content.getPhone());
        statement.setDouble(7, content.getSalary());
        statement.setString(8, content.getNationality());
        statement.setDate(9, new Date(content.getEmploymentDate().getTime()));
        statement.setInt(10, id);

        statement.execute();
        audit("update");
    }

    @Override
    public void delete(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM couriers where id=?");
        statement.setInt(1, id);
        statement.execute();
        audit("delete");
    }

    @Override
    public void audit(String operation) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO audit(operation, `table`, datetime) VALUES(?,?,?)");
        statement.setString(1, operation);
        statement.setString(2, "couriers");
        statement.setDate(3, new Date(new java.util.Date().getTime()));
        statement.execute();
    }
}
