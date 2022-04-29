package ro.unibuc.cookup.service.csv;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public interface GenericCSV<T> {
    ArrayList<T> load(String fileName) throws FileNotFoundException;

    void add(String fileName, T content);

    default void audit(String fileName, String operation) {
        FileWriter csvWriter = null;
        try {
            csvWriter = new FileWriter(fileName, true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            csvWriter.append(operation + ',' + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
            csvWriter.append("\n");
            csvWriter.flush();
            csvWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    };
}


