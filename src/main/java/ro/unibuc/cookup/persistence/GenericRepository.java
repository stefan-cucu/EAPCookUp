package ro.unibuc.cookup.persistence;

import java.util.ArrayList;

public interface GenericRepository<T> {

    public void add(T entity);

    public T get(int id);

    public ArrayList<T> getAll();

    public T findById(int id);

    public void update(T oldEntity, T newEntity);

    public void delete(T entity);

    public int getSize();

}