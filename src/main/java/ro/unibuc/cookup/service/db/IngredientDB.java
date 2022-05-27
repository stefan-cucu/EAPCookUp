package ro.unibuc.cookup.service.db;

import ro.unibuc.cookup.domain.products.Ingredient;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IngredientDB implements GenericDB<Ingredient> {
    private DatabaseConnection connection;

    public IngredientDB(DatabaseConnection connection) {
        this.connection = connection;
    }

    @Override
    public ArrayList<Ingredient> load() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM ingredients");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Ingredient> result = new ArrayList<>();

        while(resultSet.next()){
            Ingredient temp = new Ingredient(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("brand"),
                    resultSet.getFloat("price"),
                    resultSet.getDate("manufacturing_date"),
                    resultSet.getString("storage_type"),
                    resultSet.getDate("manufacturing_date"),
                    resultSet.getString("country_of_origin")
            );
            result.add(temp);
        }

        audit("load");
        return result;
    }

    @Override
    public void add(Ingredient content) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO ingredients VALUES(?,?,?,?,?,?,?,?,?)");

        statement.setInt(1, content.getProductId());
        statement.setString(2, content.getName());
        statement.setString(3, content.getDescription());
        statement.setString(4, content.getBrand());
        statement.setDouble(5, content.getPrice());
        statement.setDate(6, new Date(content.getManufacturingDate().getTime()));
        statement.setString(7, String.valueOf(content.getStorageType()));
        statement.setDate(8, new Date(content.getExpirationDate().getTime()));
        statement.setString(9, content.getCountryOfOrigin());

        statement.execute();
        audit("add");
    }

    @Override
    public void update(int id, Ingredient content) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE ingredients " +
                "SET id=?, name=?, description=?, brand=?, price=?, manufacturing_date=?, storage_type=?," +
                "expiration_date=?, country_of_origin=? WHERE id=?");

        statement.setInt(1, content.getProductId());
        statement.setString(2, content.getName());
        statement.setString(3, content.getDescription());
        statement.setString(4, content.getBrand());
        statement.setDouble(5, content.getPrice());
        statement.setDate(6, new Date(content.getManufacturingDate().getTime()));
        statement.setString(7, String.valueOf(content.getStorageType()));
        statement.setDate(8, new Date(content.getExpirationDate().getTime()));
        statement.setString(9, content.getCountryOfOrigin());

        statement.setInt(10, id);
        statement.execute();
        audit("update");
    }

    @Override
    public void delete(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM ingredients where id=?");
        statement.setInt(1, id);
        statement.execute();
        audit("delete");
    }

    @Override
    public void audit(String operation) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO audit(operation, `table`, datetime) VALUES(?,?,?)");
        statement.setString(1, operation);
        statement.setString(2, "ingredients");
        statement.setDate(3, new Date(new java.util.Date().getTime()));
        statement.execute();
    }
}
