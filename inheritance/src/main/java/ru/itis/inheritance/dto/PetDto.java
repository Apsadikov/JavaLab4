package ru.itis.inheritance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.itis.inheritance.entity.Pet;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@PrimaryKeyJoinColumn(name = "pet_id")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@SuperBuilder
@Getter
public class PetDto extends AnimalDto {
    private String name;

    public static PetDto fromPet(Pet pet) {
        return PetDto.builder()
                .name(pet.getName())
                .species(pet.getSpecies())
                .id(pet.getId())
                .build();
    }

    public static List<PetDto> fromPets(List<Pet> pets) {
        return pets.stream().map(PetDto::fromPet).collect(Collectors.toList());
    }
}
