package ru.itis.inheritance.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "pet_id")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@SuperBuilder
@Getter
public class Pet extends Animal {
    private String name;


}
