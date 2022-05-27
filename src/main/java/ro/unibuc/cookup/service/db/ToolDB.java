package ro.unibuc.cookup.service.db;

import ro.unibuc.cookup.domain.products.Tool;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ToolDB implements GenericDB<Tool>{
    private DatabaseConnection connection;

    public ToolDB(DatabaseConnection connection) {
        this.connection = connection;
    }

    @Override
    public ArrayList<Tool> load() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tools");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Tool> result = new ArrayList<>();

        while(resultSet.next()){
            Tool temp = new Tool(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("brand"),
                    resultSet.getFloat("price"),
                    resultSet.getDate("manufacturing_date"),
                    resultSet.getString("material"),
                    resultSet.getString("category"),
                    resultSet.getFloat("profitRate")
            );
            result.add(temp);
        }

        audit("load");
        return result;
    }

    @Override
    public void add(Tool content) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO tools VALUES(?,?,?,?,?,?,?,?,?)");

        statement.setInt(1, content.getProductId());
        statement.setString(2, content.getName());
        statement.setString(3, content.getDescription());
        statement.setString(4, content.getBrand());
        statement.setDouble(5, content.getPrice());
        statement.setDate(6, new Date(content.getManufacturingDate().getTime()));
        statement.setString(7, content.getMaterial());
        statement.setString(8, content.getCategory());
        statement.setDouble(9, content.getProfitRate());

        statement.execute();
        audit("add");
    }

    @Override
    public void update(int id, Tool content) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE tools " +
                "SET id=?, name=?, description=?, brand=?, price=?, manufacturing_date=?, material=?," +
                "category=?, profitRate=? WHERE id=?");

        statement.setInt(1, content.getProductId());
        statement.setString(2, content.getName());
        statement.setString(3, content.getDescription());
        statement.setString(4, content.getBrand());
        statement.setDouble(5, content.getPrice());
        statement.setDate(6, new Date(content.getManufacturingDate().getTime()));
        statement.setString(7, content.getMaterial());
        statement.setString(8, content.getCategory());
        statement.setDouble(9, content.getProfitRate());

        statement.setInt(10, id);
        statement.execute();
        audit("update");
    }

    @Override
    public void delete(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM tools where id=?");
        statement.setInt(1, id);
        statement.execute();
        audit("delete");
    }

    @Override
    public void audit(String operation) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO audit(operation, `table`, datetime) VALUES(?,?,?)");
        statement.setString(1, operation);
        statement.setString(2, "tools");
        statement.setDate(3, new Date(new java.util.Date().getTime()));
        statement.execute();
    }
}
