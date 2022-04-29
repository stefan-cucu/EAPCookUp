package ro.unibuc.cookup.service.csv;

import ro.unibuc.cookup.domain.persons.Courier;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CourierCSV implements GenericCSV<Courier> {
    private static final CourierCSV INSTANCE = new CourierCSV();

    private CourierCSV() {
    }

    @Override
    public ArrayList<Courier> load(String fileName) throws FileNotFoundException {
        BufferedReader csvReader = new BufferedReader(new FileReader(fileName));
        ArrayList<Courier> couriers = new ArrayList<Courier>();
        try {
            String line = csvReader.readLine();
            while (line != null) {
                if(line.equals("")) {
                    line = csvReader.readLine();
                    continue;
                }
                String[] data = line.split(",");
                Courier courier = new Courier(data[0], data[1], data[2], data[3], data[4], Float.parseFloat(data[5]),
                        data[6], new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data[7]));
                couriers.add(courier);
                line = csvReader.readLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        audit("./csv/audit.csv", "load_couriers");
        return couriers;
    }

    @Override
    public void add(String fileName, Courier content) {
        FileWriter csvWriter = null;
        try {
            csvWriter = new FileWriter(fileName, true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            csvWriter.append(content.toString());
            csvWriter.append("\n");
            csvWriter.flush();
            csvWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        audit("./csv/audit.csv", "add_courier");
    }

    public static CourierCSV getInstance() {
        return INSTANCE;
    }
}
