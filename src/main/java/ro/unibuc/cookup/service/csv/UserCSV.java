package ro.unibuc.cookup.service.csv;

import ro.unibuc.cookup.domain.persons.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class UserCSV implements GenericCSV<User> {
    private static final UserCSV INSTANCE = new UserCSV();

    private UserCSV() {
    }

    @Override
    public ArrayList<User> load(String fileName) throws FileNotFoundException {
        BufferedReader csvReader = new BufferedReader(new FileReader(fileName));
        ArrayList<User> users = new ArrayList<User>();
        try {
            String line = csvReader.readLine();
            while (line != null) {
                String[] data = line.split(",");
                User user = new User(data[0], data[1], data[2], data[3], data[4], data[5]);
                users.add(user);
                line = csvReader.readLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        audit("./src/main/resources/csv/audit.csv", "load_users");
        return users;
    }

    @Override
    public void add(String fileName, User content) {
        FileWriter csvWriter = null;
        try {
            csvWriter = new FileWriter(fileName, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            csvWriter.append(content.toString());
            csvWriter.append("\n");
            csvWriter.flush();
            csvWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        audit("./src/main/resources/csv/audit.csv", "add_user");
    }

    public static UserCSV getInstance() {
        return INSTANCE;
    }
}
