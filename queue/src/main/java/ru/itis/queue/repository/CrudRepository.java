package ru.itis.queue.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<E, T> {
    List<E> findAll();

    Optional<E> findById(T id);

    void delete(T id);

    void save(E entity);
}
