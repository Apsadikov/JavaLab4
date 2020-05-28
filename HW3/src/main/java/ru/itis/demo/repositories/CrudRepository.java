package ru.itis.demo.repositories;

public interface CrudRepository<T, E> {
    void save(E entity);

}
