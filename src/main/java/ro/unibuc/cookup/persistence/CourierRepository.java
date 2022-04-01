package ro.unibuc.cookup.persistence;

import ro.unibuc.cookup.domain.persons.Courier;

import java.util.ArrayList;

public class CourierRepository implements GenericRepository<Courier> {
    private ArrayList<Courier> storage = new ArrayList<Courier>();

    @Override
    public void add(Courier entity) {
        storage.add(entity);
    }

    @Override
    public Courier get(int id) {
        return storage.get(id);
    }

    @Override
    public ArrayList<Courier> getAll() {
        return storage;
    }

    @Override
    public Courier findById(int id) {
        return storage.stream().filter(courier -> courier.getPersonId() == id).findFirst().get();
    }

    @Override
    public void update(Courier oldEntity, Courier newEntity) {
        storage.set(storage.indexOf(oldEntity), newEntity);
    }

    @Override
    public void delete(Courier entity) {
        storage.remove(entity);
    }

    @Override
    public int getSize() {
        return storage.size();
    }

    public Courier getAvailableCourier(){
        try {
            return storage.stream().filter(courier -> courier.getStatus() == Courier.CourierStatus.AVAILABLE).findFirst().get();
        } catch (Exception e) {
            return null;
        }
    }
}
