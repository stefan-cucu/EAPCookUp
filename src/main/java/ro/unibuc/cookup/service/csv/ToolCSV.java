package ro.unibuc.cookup.service.csv;

import ro.unibuc.cookup.domain.products.Tool;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ToolCSV implements GenericCSV<Tool> {
    private static final ToolCSV INSTANCE = new ToolCSV();

    private ToolCSV() {
    }

    public static ToolCSV getInstance() {
        return INSTANCE;
    }

    @Override
    public ArrayList<Tool> load(String fileName) throws FileNotFoundException {
        BufferedReader csvReader = new BufferedReader(new FileReader(fileName));
        ArrayList<Tool> tools = new ArrayList<>();
        try {
            String line = csvReader.readLine();
            while (line != null) {
                String[] data = line.split(",");
                Tool tool = new Tool(data[0], data[1], Float.parseFloat(data[2]),
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data[3]),
                        data[4], data[5], data[6], Float.parseFloat(data[7]));
                tools.add(tool);
                line = csvReader.readLine();
            }
            csvReader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        audit("./src/main/resources/csv/audit.csv", "load_tools");
        return tools;
    }

    @Override
    public void add(String fileName, Tool content) {
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
        audit("./src/main/resources/csv/audit.csv", "add_tool");
    }
}
