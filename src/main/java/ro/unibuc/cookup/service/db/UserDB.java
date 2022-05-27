package ro.unibuc.cookup.service.db;

import ro.unibuc.cookup.domain.persons.PaymentCard;
import ro.unibuc.cookup.domain.persons.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDB implements GenericDB<User>{
    private DatabaseConnection connection;

    public UserDB(DatabaseConnection connection) {
        this.connection = connection;
    }

    @Override
    public ArrayList<User> load() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<User> result = new ArrayList<>();

        while(resultSet.next()){
            PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM payment_cards WHERE user_id=?");
            statement1.setInt(1, resultSet.getInt("id"));

            ResultSet cardsSet = statement1.executeQuery();
            User temp;

            if(cardsSet.next())
                temp = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("address"),
                        resultSet.getString("password"),
                        new PaymentCard(
                                cardsSet.getString("number"),
                                cardsSet.getString("name"),
                                cardsSet.getString("expiry_date"),
                                cardsSet.getInt("cvv")
                        )
                );
            else
                temp = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("address"),
                        resultSet.getString("password")
                );
            result.add(temp);
        }

        audit("load");
        return result;
    }

    @Override
    public void add(User content) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO users VALUES (?,?,?,?,?,?,?)");

        statement.setInt(1, content.getPersonId());
        statement.setString(2, content.getFirstName());
        statement.setString(3, content.getLastName());
        statement.setString(4, content.getEmail());
        statement.setString(6, content.getAddress());
        statement.setString(5, content.getPhone());
        statement.setString(7, content.getPassword());

        statement.execute();

        if(content.getPaymentCard() != null)
            add_card(content.getPersonId(), content.getPaymentCard());

        audit("add");
    }

    @Override
    public void update(int id, User content) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE users " +
                "SET id=?, first_name=?, last_name=?, email=?, address=?, phone=?, password=?" +
                "WHERE id=?");

        statement.setInt(1, content.getPersonId());
        statement.setString(2, content.getFirstName());
        statement.setString(3, content.getLastName());
        statement.setString(4, content.getEmail());
        statement.setString(5, content.getAddress());
        statement.setString(6, content.getPhone());
        statement.setString(7, content.getPassword());
        statement.setInt(8, id);
        statement.execute();

        PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM payment_cards WHERE user_id=?");
        statement1.setInt(1, id);
        ResultSet cardSet = statement1.executeQuery();

        if(cardSet.next()){
            if (content.getPaymentCard() == null)
                delete_card(id);
            else update_card(id, content.getPaymentCard());
        }
        else {
            if (content.getPaymentCard() != null)
                add_card(id, content.getPaymentCard());
        }

        audit("update");
    }

    @Override
    public void delete(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM users where id=?");
        statement.setInt(1, id);
        statement.execute();
        audit("delete");
    }

    public void add_card(int id, PaymentCard paymentCard) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO payment_cards VALUES (?,?,?,?,?)");

        statement.setString(1, paymentCard.getNumber());
        statement.setString(2, paymentCard.getName());
        statement.setString(3, paymentCard.getExpiryDate());
        statement.setInt(4, paymentCard.getCvv());
        statement.setInt(5, id);
        statement.execute();

        audit("add_card");
    }

    public void update_card(int id, PaymentCard paymentCard) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE payment_cards SET number=?, name=?," +
                "expiry_date=?, cvv=?, user_id=? WHERE user_id=?");
        System.out.println(statement);
        statement.setString(1, paymentCard.getNumber());
        statement.setString(2, paymentCard.getName());
        statement.setString(3, paymentCard.getExpiryDate());
        statement.setInt(4, paymentCard.getCvv());
        statement.setInt(5, id);
        statement.setInt(6, id);
        statement.execute();

        audit("update_card");
    }

    public void delete_card(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM payment_cards WHERE user_id=?");

        statement.setInt(1, id);
        statement.execute();

        audit("delete_card");
    }

    @Override
    public void audit(String operation) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO audit (operation, `table`, datetime) VALUES (?,?,?)");
        statement.setString(1, operation);
        statement.setString(2, "couriers");
        statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
        statement.execute();
    }
}
