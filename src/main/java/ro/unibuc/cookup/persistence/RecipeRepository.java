package ro.unibuc.cookup.persistence;

import ro.unibuc.cookup.domain.products.Product;
import ro.unibuc.cookup.domain.products.Purchasable;
import ro.unibuc.cookup.domain.products.Recipe;

import java.util.ArrayList;

public class RecipeRepository implements GenericRepository<Recipe> {
    private static ArrayList<Recipe> storage = new ArrayList<Recipe>();

    @Override
    public void add(Recipe entity) {
        storage.add(entity);
    }

    @Override
    public Recipe get(int id) {
        return storage.get(id);
    }

    @Override
    public ArrayList<Recipe> getAll() {
        return storage;
    }

    @Override
    public Recipe findById(int id) {
        return storage.stream().filter(entity -> entity.getRecipeId() == id).findFirst().get();
    }

    @Override
    public void update(Recipe oldEntity, Recipe newEntity) {
        storage.set(storage.indexOf(oldEntity), newEntity);
    }

    @Override
    public void delete(Recipe entity) {
        storage.remove(entity);
    }

    @Override
    public int getSize() {
        return storage.size();
    }
}
