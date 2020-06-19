package ru.itis.demo.repository;

import ru.itis.demo.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<E extends Entity, T> {
    Optional<List<E>> findAll();

    Optional<E> findById(T id);

    void save(E entity);

    void delete(T id);
}
