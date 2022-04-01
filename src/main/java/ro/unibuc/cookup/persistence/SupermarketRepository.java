package ro.unibuc.cookup.persistence;

import ro.unibuc.cookup.domain.Supermarket;
import ro.unibuc.cookup.domain.products.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class SupermarketRepository implements GenericRepository<Supermarket> {
    private ArrayList<Supermarket> storage = new ArrayList<Supermarket>();

    @Override
    public void add(Supermarket entity) {
        storage.add(entity);
    }

    @Override
    public Supermarket get(int id) {
        return storage.get(id);
    }

    @Override
    public ArrayList<Supermarket> getAll() {
        return storage;
    }

    @Override
    public Supermarket findById(int id) {
        return storage.stream().filter(entity -> entity.getSupermarketId() == id).findFirst().get();
    }

    @Override
    public void update(Supermarket oldEntity, Supermarket newEntity) {
        storage.set(storage.indexOf(oldEntity), newEntity);
    }

    @Override
    public void delete(Supermarket entity) {
        storage.remove(entity);
    }

    @Override
    public int getSize() {
        return storage.size();
    }

    public Supermarket findSupermarketWithProducts(HashMap<Product, Integer> products) {
        for (Supermarket supermarket : storage) {
            boolean found = true;
            for(Product product : products.keySet()) {
                if(!supermarket.getProducts().containsKey(product)) {
                    found = false;
                    break;
                }
                if(!supermarket.containsProduct(product, products.get(product))) {
                    found = false;
                    break;
                }
            }
            if(found) {
                for(Product product : products.keySet()) {
                    supermarket.removeProduct(product, products.get(product));
                }
                return supermarket;
            }
        }
        return null;
    }
}

