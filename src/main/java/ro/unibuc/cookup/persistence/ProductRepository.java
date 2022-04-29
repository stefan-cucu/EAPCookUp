package ro.unibuc.cookup.persistence;

import ro.unibuc.cookup.domain.products.Product;
import ro.unibuc.cookup.domain.products.Purchasable;
import ro.unibuc.cookup.domain.products.Tool;

import java.util.ArrayList;

public class ProductRepository implements GenericRepository<Product> {
    private static ArrayList<Product> storage = new ArrayList<Product>();

    @Override
    public void add(Product entity) {
        storage.add(entity);
    }

    @Override
    public Product get(int id) {
        return storage.get(id);
    }

    @Override
    public ArrayList<Product> getAll() {
        return storage;
    }

    @Override
    public Product findById(int id) {
        return storage.stream().filter(entity -> entity.getProductId() == id).findFirst().get();
    }

    @Override
    public void update(Product oldEntity, Product newEntity) {
        storage.set(storage.indexOf(oldEntity), newEntity);
    }

    @Override
    public void delete(Product entity) {
        storage.remove(entity);
    }

    @Override
    public int getSize() {
        return storage.size();
    }

}
