package ro.unibuc.cookup.persistence;

import ro.unibuc.cookup.domain.persons.User;

import java.util.ArrayList;

public class UserRepository implements GenericRepository<User> {
    private static ArrayList<User> storage = new ArrayList<User>();
    @Override
    public void add(User entity) {
        storage.add(entity);
    }

    @Override
    public User get(int id) {
        return storage.get(id);
    }

    @Override
    public ArrayList<User> getAll() {
        return storage;
    }

    @Override
    public User findById(int id) {
        return storage.stream().filter(entity -> entity.getPersonId() == id).findFirst().get();
    }

    public boolean userExists(int id) {
        return storage.stream().anyMatch(entity -> entity.getPersonId() == id);
    }

    @Override
    public void update(User oldEntity, User newEntity) {
        storage.set(storage.indexOf(oldEntity), newEntity);
    }

    @Override
    public void delete(User entity) {
        storage.remove(entity);
    }

    @Override
    public int getSize() {
        return storage.size();
    }
}
