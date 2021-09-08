package service;

import model.Product;

import java.util.List;

public interface IService<E> {
    List<E> findAll();
    E findById(int id);
    void save(E e);
    void update(E e, int id);
    void delete(int id);
}
