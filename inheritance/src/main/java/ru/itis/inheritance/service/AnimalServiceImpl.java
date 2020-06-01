package ru.itis.inheritance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.inheritance.dto.AnimalDto;
import ru.itis.inheritance.dto.PetDto;
import ru.itis.inheritance.entity.Animal;
import ru.itis.inheritance.entity.Pet;
import ru.itis.inheritance.repository.AnimalRepository;

import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {
    private AnimalRepository<Animal> animalRepository;
    private AnimalRepository<Pet> petRepository;

    @Autowired
    public AnimalServiceImpl(AnimalRepository<Animal> animalRepository, AnimalRepository<Pet> petRepository) {
        this.animalRepository = animalRepository;
        this.petRepository = petRepository;
    }

    @Override
    public List<AnimalDto> getAllAnimals() {
        return AnimalDto.from(animalRepository.findAll());
    }

    @Override
    public List<PetDto> getAllPets() {
        List<PetDto> list = PetDto.fromPets(petRepository.findAll());
        return list;
    }
}
