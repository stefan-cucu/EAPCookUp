package ro.unibuc.cookup.domain.products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Recipe implements Purchasable {
    // Static id counter
    private static int _id = 1000;
    private final int recipeId;

    // Recipe fields
    private HashMap<Ingredient, Integer> ingredients;
    private String name;
    private String description;
    private float timeEstimate;
    private float profitRate;

    // Constructor
    public Recipe(HashMap<Ingredient, Integer> ingredients, String name, String description, float timeEstimate, float profitRate) {
        this.ingredients = ingredients;
        this.name = name;
        this.description = description;
        this.timeEstimate = timeEstimate;
        this.profitRate = profitRate;
        this.recipeId = ++_id;
    }

    // Getters and setters
    public int getRecipeId() {
        return recipeId;
    }

    public HashMap<Ingredient, Integer> getIngredients() {
        return ingredients;
    }

    public void setIngredients(HashMap<Ingredient, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getTimeEstimate() {
        return timeEstimate;
    }

    public void setTimeEstimate(float timeEstimate) {
        this.timeEstimate = timeEstimate;
    }

    // Purchasable methods implementation
    @Override
    public float getPrice() {
        float price = 0;
        for (Ingredient ingredient : ingredients.keySet()) {
            price += ingredient.getPrice() * ingredients.get(ingredient);
        }
        return (1 + profitRate) * price;
    }

    @Override
    public float getProfit() {
        return getPrice() - getPrice() / (1 + profitRate);
    }
}
