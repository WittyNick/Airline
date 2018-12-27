package by.gstu.airline.dao;

import java.util.List;

public interface GenericDao<T> {
    int create(T entity);
    T readById(int id);
    List<T> readAll();
    void update(T entity);
    void delete(T entity);
}
