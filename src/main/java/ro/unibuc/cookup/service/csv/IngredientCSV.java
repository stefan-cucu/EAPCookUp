package ro.unibuc.cookup.service.csv;

import ro.unibuc.cookup.domain.products.Ingredient;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class IngredientCSV implements GenericCSV<Ingredient> {
    private static final IngredientCSV INSTANCE = new IngredientCSV();

    private IngredientCSV() {
    }

    public static IngredientCSV getInstance() {
        return INSTANCE;
    }

    @Override
    public ArrayList<Ingredient> load(String fileName) throws FileNotFoundException {
        BufferedReader csvReader = new BufferedReader(new FileReader(fileName));
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        try {
            String line = csvReader.readLine();
            while (line != null) {
                String[] data = line.split(",");
                Ingredient ingredient = new Ingredient(data[0], data[1], Float.parseFloat(data[2]),
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data[3]), data[4], data[5],
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data[6]), data[7]);
                ingredients.add(ingredient);
                line = csvReader.readLine();
            }
            csvReader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        audit("./src/main/resources/csv//audit.csv", "load_ingredients");
        return ingredients;
    }

    @Override
    public void add(String fileName, Ingredient content) {
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
        audit("./src/main/resources/csv/audit.csv", "add_ingredient");
    }
}
