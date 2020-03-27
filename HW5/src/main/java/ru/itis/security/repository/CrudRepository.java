package ru.itis.security.repository;

public interface CrudRepository<T, E> {
    void save(E entity);

}
