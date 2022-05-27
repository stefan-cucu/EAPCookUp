package ro.unibuc.cookup.service.db;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GenericDB<T> {
    ArrayList<T> load() throws SQLException;

    void add(T content) throws SQLException;

    void update(int id, T content) throws SQLException;

    void delete(int id) throws SQLException;

    void audit(String operation) throws SQLException;
}
