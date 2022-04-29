package ro.unibuc.cookup.persistence;

import ro.unibuc.cookup.domain.Delivery;
import ro.unibuc.cookup.domain.products.Product;

import java.util.ArrayList;

public class DeliveryRepository implements GenericRepository<Delivery> {
    private static ArrayList<Delivery> storage = new ArrayList<Delivery>();
    @Override
    public void add(Delivery entity) {
        storage.add(entity);
    }

    @Override
    public Delivery get(int id) {
        return storage.get(id);
    }

    @Override
    public ArrayList<Delivery> getAll() {
        return storage;
    }

    @Override
    public Delivery findById(int id) {
        return storage.stream().filter(delivery -> delivery.getDeliveryId() == id).findFirst().get();
    }

    @Override
    public void update(Delivery oldEntity, Delivery newEntity) {
        storage.set(storage.indexOf(oldEntity), newEntity);
    }

    @Override
    public void delete(Delivery entity) {
        storage.remove(entity);
    }

    @Override
    public int getSize() {
        return storage.size();
    }

}
