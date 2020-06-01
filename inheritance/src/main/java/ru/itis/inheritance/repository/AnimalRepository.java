package ru.itis.inheritance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.inheritance.entity.Animal;

@Repository
public interface AnimalRepository<T extends Animal> extends JpaRepository<T, Long> {
}
