package ro.unibuc.cookup.service;

import ro.unibuc.cookup.domain.products.Ingredient;
import ro.unibuc.cookup.domain.products.Product;
import ro.unibuc.cookup.domain.products.Recipe;
import ro.unibuc.cookup.domain.products.Tool;
import ro.unibuc.cookup.persistence.ProductRepository;
import ro.unibuc.cookup.persistence.RecipeRepository;
import ro.unibuc.cookup.service.csv.IngredientCSV;
import ro.unibuc.cookup.service.csv.ToolCSV;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ProductServices {
    private ProductRepository productRepository = new ProductRepository();
    private RecipeRepository recipeRepository = new RecipeRepository();

    private IngredientCSV ingredientCSVService = IngredientCSV.getInstance();
    private ToolCSV toolCSVService = ToolCSV.getInstance();

    private final String INGREDIENT_CSV_PATH = "./csv/ingredients.csv";
    private final String TOOL_CSV_PATH = "./csv/tools.csv";

    public ProductServices() {
        try {
            for(Ingredient ingredient : ingredientCSVService.load(INGREDIENT_CSV_PATH)){
                productRepository.add(ingredient);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try {
            for(Tool tool : toolCSVService.load(TOOL_CSV_PATH)){
                productRepository.add(tool);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addIngredient(String name, String description, float price, Date manufacturingDate, String brand, String storageType, Date expirationDate, String countryOfOrigin){
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if(description == null || description.isEmpty()){
            throw new IllegalArgumentException("Description cannot be empty");
        }
        if(price <= 0){
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if(manufacturingDate == null){
            throw new IllegalArgumentException("Manufacturing date cannot be null");
        }
        if(brand == null || brand.isEmpty()){
            throw new IllegalArgumentException("Brand cannot be empty");
        }
        if(storageType == null || storageType.isEmpty()){
            throw new IllegalArgumentException("Storage type cannot be empty");
        }
        if(expirationDate == null){
            throw new IllegalArgumentException("Expiration date cannot be null");
        }
        if(countryOfOrigin == null || countryOfOrigin.isEmpty()){
            throw new IllegalArgumentException("Country of origin cannot be empty");
        }
        Ingredient ingredient = new Ingredient(name, description, price, manufacturingDate, brand, storageType, expirationDate, countryOfOrigin);
        productRepository.add(ingredient);
        ingredientCSVService.add(INGREDIENT_CSV_PATH, ingredient);
    }

    public void addTool(String name, String description, float price, Date manufacturingDate, String brand, String material, String category, float profitRate) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if(description == null || description.isEmpty()){
            throw new IllegalArgumentException("Description cannot be empty");
        }
        if(price <= 0){
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if(manufacturingDate == null){
            throw new IllegalArgumentException("Manufacturing date cannot be null");
        }
        if(brand == null || brand.isEmpty()){
            throw new IllegalArgumentException("Brand cannot be empty");
        }
        if(material == null || material.isEmpty()){
            throw new IllegalArgumentException("Material cannot be empty");
        }
        if(category == null || category.isEmpty()){
            throw new IllegalArgumentException("Category cannot be empty");
        }
        if(profitRate <= 0 || profitRate > 1){
            throw new IllegalArgumentException("Profit rate must be between 0 and 1");
        }
        Tool tool = new Tool(name, description, price, manufacturingDate, brand, material, category, profitRate);
        productRepository.add(tool);
        toolCSVService.add(TOOL_CSV_PATH, tool);
    }

    public void addRecipe(HashMap<Ingredient, Integer> ingredients, String name, String description, float timeEstimate, float profitRate){
        if(ingredients == null || ingredients.isEmpty()){
            throw new IllegalArgumentException("Ingredients cannot be empty");
        }
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if(description == null || description.isEmpty()){
            throw new IllegalArgumentException("Description cannot be empty");
        }
        if(timeEstimate <= 0){
            throw new IllegalArgumentException("Time estimate cannot be negative");
        }
        if(profitRate <= 0 || profitRate > 1){
            throw new IllegalArgumentException("Profit rate must be between 0 and 1");
        }
        recipeRepository.add(new Recipe(ingredients, name, description, timeEstimate, profitRate));
    }

    public Product getProduct(int id){
        if(productRepository.findById(id) == null)
            throw new IllegalArgumentException("Product with id " + id + " does not exist");
        return productRepository.findById(id);
    }

    public Ingredient getIngredient(int id){
        if(!(productRepository.findById(id) instanceof Ingredient)){
            throw new IllegalArgumentException("Product with id " + id + " is not an ingredient");
        }
        return (Ingredient) productRepository.findById(id);
    }

    public Tool getTool(int id){
        if(!(productRepository.findById(id) instanceof Tool)){
            throw new IllegalArgumentException("Product with id " + id + " is not a tool");
        }
        return (Tool) productRepository.findById(id);
    }

    public Recipe getRecipe(int id){
        if(recipeRepository.findById(id) == null){
            throw new IllegalArgumentException("Product with id " + id + " is not a recipe");
        }
        return recipeRepository.findById(id);
    }

    public ArrayList<Product> getAllProducts(){
        return productRepository.getAll();
    }

    public ArrayList<Recipe> getAllRecipes(){
        return recipeRepository.getAll();
    }
}
