package ru.itis.inheritance.service;

import org.springframework.stereotype.Service;
import ru.itis.inheritance.dto.AnimalDto;
import ru.itis.inheritance.dto.PetDto;
import ru.itis.inheritance.entity.Animal;

import java.util.List;

@Service
public interface AnimalService {
    List<AnimalDto> getAllAnimals();
    List<PetDto> getAllPets();
}
