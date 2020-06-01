package ru.itis.inheritance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.itis.inheritance.entity.Animal;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class AnimalDto {
    private long id;
    private String species;

    public static AnimalDto from(Animal animal) {
        return AnimalDto.builder()
                .id(animal.getId())
                .species(animal.getSpecies())
                .build();
    }

    public static List<AnimalDto> from(List<Animal> animals) {
        return animals.stream().map(AnimalDto::from).collect(Collectors.toList());
    }
}
